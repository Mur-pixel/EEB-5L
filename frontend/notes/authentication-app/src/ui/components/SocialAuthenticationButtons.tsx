import React from "react";
import { useNavigate } from "react-router-dom";
import SocialLoginButton from "./SocialLoginButton";
import env from "../../env.ts";

type Provider = "kakao" | "google" | "naver" | "github" | "meta";

const getAuthUrl = (provider: Provider): string | undefined => {
    switch (provider) {
        case "kakao": return env.api.KAKAO_AUTH_URL;
        case "google": return env.api.GOOGLE_AUTH_URL;
        case "naver": return env.api.NAVER_AUTH_URL;
        case "github": return env.api.GITHUB_AUTH_URL;
        case "meta": return env.api.META_AUTH_URL;
    }
};

const SocialAuthenticationButtons: React.FC = () => {
    const navigate = useNavigate();

    const handleLogin = (provider: Provider) => () => {
        const authUrl = getAuthUrl(provider);
        if (!authUrl) {
            alert(`${provider} 로그인 URL이 설정되어 있지 않습니다.`);
            return;
        }

        const popup = window.open(authUrl, "_blank", "width=500,height=600");
        if (!popup) {
            alert("팝업이 차단되었습니다. 허용 후 다시 시도하세요.");
            return;
        }

        const allowedOrigins = env.origins[provider];

        const receiveMessage = (event: MessageEvent) => {
            if (!allowedOrigins.some(origin => event.origin.startsWith(origin))) {
                console.warn("허용되지 않은 origin:", event.origin);
                return;
            }

            // const { accessToken } = event.data;
            // if (!accessToken) return;
            //
            // localStorage.setItem("userToken", accessToken);
            // window.dispatchEvent(new Event("user-token-changed"));
            //
            // window.removeEventListener("message", receiveMessage);
            //
            // try {
            //     popup.close();
            // } catch {}
            const {
                accessToken,
                newUser,
                user,
                loginType,
                temporaryUserToken,
            } = event.data;

            console.log("[Message] Data received:", {
                accessToken,
                newUser,
                user,
                loginType,
                temporaryUserToken,
            });

            window.removeEventListener("message", receiveMessage);
            try {
                popup?.close();
            } catch {}

            if (accessToken) {
                localStorage.setItem("userToken", accessToken);
                window.dispatchEvent(new Event("user-token-changed"));
                setTimeout(() => navigate("/"), 100);
                return;
            }

            if (newUser) {
                // user에는 email, name 등이 담겨 있을 수 있음 (백엔드에서 넘겨준 경우)
                setTimeout(() => navigate("/authentication/signup-agreement", {
                    state: {
                        user,
                        loginType,
                        temporaryUserToken,
                    }
                }), 100);
                return;
            }

            alert("로그인에 실패했습니다. 다시 시도해주세요.");

            setTimeout(() => navigate("/"), 100);
        };

        window.addEventListener("message", receiveMessage);
    };

    return (
        <div
            className="flex flex-col w-full max-w-xs mx-auto"
            style={{
                minHeight: `calc(100vh - 64px)`,
                gap: '1rem',
                justifyContent: 'center',
                alignItems: 'center',
                display: 'flex',
                flexDirection: 'column',
            }}
        >
            <SocialLoginButton provider="kakao" onClick={handleLogin("kakao")} />
            <SocialLoginButton provider="google" onClick={handleLogin("google")} />
            <SocialLoginButton provider="naver" onClick={handleLogin("naver")} />
            <SocialLoginButton provider="github" onClick={handleLogin("github")} />
            <SocialLoginButton provider="meta" onClick={handleLogin("meta")} />
        </div>
    );
};

export default SocialAuthenticationButtons;
