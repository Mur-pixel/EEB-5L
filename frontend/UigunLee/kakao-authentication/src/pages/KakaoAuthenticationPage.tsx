import React from "react";
import { Box, Typography } from "@mui/material";
import SocialLoginButton from "../ui/components/SocialLoginButton";

import {useNavigate} from "react-router-dom";
import env from "../env.ts";

const KakaoAuthenticationPage: React.FC = () => {
    //우리가 Routes로 감싸 놓은 Router 설정을 활용할 수 있게 지원함
    const navigate = useNavigate();

    const handleKakaoLogin = () => {
        const kakaoAuthenticationUrl = env.api.KAKAO_AUTHENTICATION_URL;
        // 팝업 형식의 로그인 창을 띄웁니다.
        const popup = window.open(kakaoAuthenticationUrl, '_blank', 'width=500,height=600');

        if (!popup) {
            alert('팝업 차단되어 있습니다. 팝업 허용 후 다시 시도하세요.');
            return;
        }

        const receiveMessage = (event: MessageEvent) => {
            console.log('📨 받은 메시지:', event.origin, event.data);

            // origin 검사 완화
            if (!event.origin.startsWith('http://localhost')) {
                console.warn('❌ 허용되지 않은 origin:', event.origin);
                return;
            }

            const { accessToken, user } = event.data;
            if (!accessToken) {
                console.warn('❌ accessToken 없음');
                return;
            }

            // F12 눌렀을 때 Application 파트에 localstorage 가 보입니다.
            // 해당 파트에 보면 userToken이 생산되어 있는 것을 볼 수 있는데,
            // 아래 파트에서 작업하는 것이라 보면 되겠습니다.
            localStorage.setItem('userToken', accessToken);
            window.dispatchEvent(new Event("user-token-changed"));

            window.removeEventListener('message', receiveMessage);

            // 팝업 닫기
            try {
                popup.close();
            } catch (e) {
                console.warn('팝업 닫기 실패:', e);
            }

            // navigate 딜레이
            setTimeout(() => {
                navigate('/');
            }, 100);
        };
        // 팝업으로 메시지가 전달되면  "receiveMessage() 니가 받으렴"

        window.addEventListener('message', receiveMessage);
    };

    // 항상 컴포넌트의 return에 표현되는 것이 UI에 해당하니다.
    return (
        // BOX는 React MUI (Vue Vuetify) 에 해당하는 UI 템플릿입니다.
        // <div> 태그에 해당하며 스타일을 조금 더 편리하게 지정할 수 있도록 도와줍니다.
        // p: 4 <- 내부 여백(padding)을 1당 8px로 4이므로 32px
        // maxWidth: 400 <- 쵀대 너비는 400px로 제한
        // margin: "0 auto" <- 가운데 정렬
        <Box sx={{ p: 4, maxWidth: 400, margin: "0 auto" }}>
            {/*
                Typography는 텍스트 표현하는 필드
                variant="h5" <- HTML의 <H5> 처럼 스타일링 하세요.
                gutterBottom <- 아래쪽에 적절한 여백을 만들어 줍니다.
            */}
            <Typography variant="h5" gutterBottom>
                로그인
            </Typography>
            {/*
                SocialLoginButton 은 사실상 또 다른 컴포넌트
                컴포넌트 내부에서 또 다른 컴포넌트를 사용할 수 있다.
                provider는 실제 SocialLoginButton이 사용하는 파라미터입니다.
                onClick도 SocialLoginButton이 사용하는 파라미터입니다.
                onClick의 경우 클릭 했을 때 무엇을 할 것인지 외부에서 주입하기 위함입니다.

            */}
            <SocialLoginButton provider="kakao" onClick={handleKakaoLogin} />
        </Box>
    );
};

export default KakaoAuthenticationPage;