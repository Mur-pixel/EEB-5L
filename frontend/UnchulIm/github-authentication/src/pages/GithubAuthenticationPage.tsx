import React from "react";
import { Box, Typography } from "@mui/material";
import SocialLoginButton from "../ui/components/SocialLoginButton";
import { useNavigate } from "react-router-dom";
import env from "../env.ts";

const GithubAuthenticationPage: React.FC = () => {
    const navigate = useNavigate();

    const handleGithubLogin = () => {
        const githubAuthUrl = env.api.GITHUB_AUTHENTICATION_URL;
        const popup = window.open(githubAuthUrl, '_blank', 'width=500,height=600');

        if (!popup) {
            alert('팝업이 차단되었습니다. 팝업 허용 후 다시 시도해주세요.');
            return;
        }

        const receiveMessage = (event: MessageEvent) => {
            if (!event.origin.startsWith("http://localhost")) return;

            const { accessToken, user } = event.data;
            if (!accessToken) return;

            localStorage.setItem("userToken", accessToken);
            window.dispatchEvent(new Event("user-token-changed"));

            try {
                popup.close();
            } catch (e) {
                console.warn("팝업 닫기 실패", e);
            }

            window.removeEventListener("message", receiveMessage);
            setTimeout(() => navigate("/"), 100);
        };

        window.addEventListener("message", receiveMessage);
    };

    return (
        <Box sx={{ p: 4, maxWidth: 400, margin: "0 auto" }}>
            <Typography variant="h5" gutterBottom>
                GitHub 로그인
            </Typography>
            <SocialLoginButton provider="github" onClick={handleGithubLogin} />
        </Box>
    );
};

export default GithubAuthenticationPage;
