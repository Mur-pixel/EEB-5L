import React from "react";
import { Box, Typography } from "@mui/material";
import SocialLoginButton from "../ui/components/SocialLoginButton";

import {useNavigate} from "react-router-dom";
import env from "../env.ts";

const KakaoAuthenticationPage: React.FC = () => {
    const navigate = useNavigate();

    const handleKakaoLogin = async () => {
        try {
            const kakaoAuthenticationUrl = env.api.KAKAO_AUTHENTICATION_URL;
            const popup = window.open(kakaoAuthenticationUrl, '_blank', 'width=500,height=600');

            if (!popup) {
                alert('팝업 차단되어 있습니다. 팝업 허용 후 다시 시도하세요.');
                return;
            }

            const receiveMessage = (event: MessageEvent) => {
                console.log('📨 받은 메시지:', event.origin, event.data);

                if (!event.origin.startsWith(env.origin)) {
                    console.warn('❌ 허용되지 않은 origin:', event.origin);
                    return;
                }

                const { accessToken, user } = event.data;
                if (!accessToken) {
                    console.warn('❌ accessToken 없음');
                    return;
                }

                localStorage.setItem('userToken', accessToken);
                window.dispatchEvent(new Event("user-token-changed"));

                window.removeEventListener('message', receiveMessage);

                try {
                    popup.close();
                } catch (e) {
                    console.warn('팝업 닫기 실패:', e);
                }

                setTimeout(() => {
                    navigate('/');
                }, 100);
            };
            window.addEventListener('message', receiveMessage);
        } catch (e) {
            console.error('❌ 에러 발생:', e);
            alert('로그인 실패: 네트워크 에러');
        }
    };

    // 항상 컴포넌트의 return에 표현되는 것이 UI에 해당합니다.
    return (
        // Box는 React MUI (Vue Vuetify) 에 해당하는 UI 템플릿입니다.
        // <div> 태그에 해당하며 스타일을 조금 더 편리하게 지정 할 수 있도록 도와줍니다.
        // p: 4 <- 내부 여백(padding)을 1당 8px로 4이므로 32px
        // maxWidth: 400 <- 최대 너비는 400px로 제한
        // margin: "0 auto" <- 좌우 관점에서 가운데 정렬
        <Box sx={{ p: 4, maxWidth: 400, margin: "0 auto" }}>
            {/*
                Typography는 텍스트 표현하는 필드
                variant="h5" <- HTML의 <h5> 처럼 스타일링 하세요.
                gutterBottom <- 아래쪽에 적절한 여백을 만들어줍니다.
             */}
            <Typography variant="h5" gutterBottom>
                로그인
            </Typography>
            {/*
                SocialLoginButton 은 사실상 또 다른 컴포넌트
                컴포넌트 내부에서 또 다른 컴포넌트를 사용 할 수 있다.
                provider는 실제 SocialLoginButton이 사용하는 파라미터입니다.
                provider를 사용하는 이유는 "kakao" 로그인임을 명시하기 위함입니다.
                onClick도 SocialLoginButton이 사용하는 파라미터입니다.
                onClick의 경우 클릭 했을 때 무엇을 할 것인지 외부에서 주입하기 위함입니다.
            */}
            <SocialLoginButton provider="kakao" onClick={handleKakaoLogin} />
        </Box>
    );
};

export default KakaoAuthenticationPage;