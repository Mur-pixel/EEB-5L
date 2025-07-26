import React from "react";
import KakaoLoginImage from "../../assets/kakao_login.png";

// 어떤 소셜 로그인들을 지원할 것인지 리스팅
type Provider = "google" | "kakao" | "github";

// 속성들을 props(properties) 형태로 props라고 표현한다고 했는데
// 실제로 SocialLoginButton 컴포넌트가 사용하는 파라미터를 Props 형태로 표현하기 위해 작성
interface Props {
    provider: Provider;
    // 입력이 없고, 리턴도 없는 함수가 onClick 입니다라는 뜻
    // void (*)(void);
    onClick: () => void;
}

// 위의 Provider에서 지정한 kakao, google, github 등이 오면
// 아래의 정해진 label, color, icon 값을 가지게 만듭니다.
const providerInfo: Record<Provider, {
    label: string;
    color: string;
    icon: React.ReactNode;
}> = {
    kakao: { label: "Kakao 로그인", color: "", icon: null },   // 사용하지 않음
};

// 파라미터 2개(provider와 onClick 함수) 를 받아옴
const SocialLoginButton: React.FC<Props> = ({ provider, onClick }) => {
    // 위쪽의 label, color, icon 정보를 획득함 -> 현재 provider는 kakao 입니다.
    const info = providerInfo[provider];

    if (!info.label) return null; // 유효하지 않은 provider 방어 처리

    // 하단의 onClick이 핵심
    // 현재 onClick이 사실상 AuthenticationPage의 handleKakaoLogin() 과 같은 녀석이라는 것에 주의!
    return (
        <img
            src={KakaoLoginImage}
            alt="Kakao Login"
            onClick={onClick}
            style={{
                cursor: "pointer",
                maxWidth: "100%",     // 부모 컨테이너 크기까지만 줄어듦
                height: "auto",       // 원본 비율 유지
                display: "block",
                margin: "16px auto",  // 가운데 정렬
            }}
        />
    );
};

export default SocialLoginButton;