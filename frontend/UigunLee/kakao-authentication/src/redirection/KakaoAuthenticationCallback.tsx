import { useEffect } from "react";

const KakaoAuthenticationCallback = () => {
    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const accessToken = params.get("userToken");

        if (accessToken && window.opener) {
            window.opener.postMessage(
                {
                    accessToken,
                    user: { name: "테스트유저" }, // 실제 유저 데이터라면 여기 넘기기
                },
                "http://localhost"
            );
            window.close();
        }
    }, []);

    return <div>로그인 처리 중...</div>;
};

export default KakaoAuthenticationCallback;