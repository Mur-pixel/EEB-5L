import React, { useState } from "react";
import KakaoIcon from "../../assets/kakao_logo.png";
import GoogleIcon from "../../assets/google_logo.png";
import NaverIcon from "../../assets/naver_logo.png";
import GithubIcon from "../../assets/github_logo.png";
import MetaIcon from "../../assets/meta_logo.png";

type Provider = "kakao" | "google" | "naver" | "github" | "meta";

interface Props {
    provider: Provider;
    onClick?: () => void;
}

const providerConfig: Record<
    Provider,
    {
        src: string;
        label: string;
        baseBgColor: string;
        textColor: string;
        border: string;
        hoverBgColor: string;
    }
> = {
    kakao: {
        src: KakaoIcon,
        label: "카카오 로그인",
        baseBgColor: "#FEE500",
        textColor: "text-black",
        border: "border border-yellow-400",
        hoverBgColor: "#FFD600",
    },
    google: {
        src: GoogleIcon,
        label: "Google 로그인",
        baseBgColor: "#FFFFFF",
        textColor: "text-gray-900",
        border: "border border-gray-300",
        hoverBgColor: "#F5F5F5",
    },
    naver: {
        src: NaverIcon,
        label: "네이버 로그인",
        baseBgColor: "#03C75A",
        textColor: "text-white",
        border: "border border-green-600",
        hoverBgColor: "#02B14B",
    },
    github: {
        src: GithubIcon,
        label: "GitHub 로그인",
        baseBgColor: "#6E40C9",
        textColor: "text-white",
        border: "border border-purple-900",
        hoverBgColor: "#5A2EB9",
    },
    meta: {
        src: MetaIcon,
        label: "Facebook 로그인",
        baseBgColor: "#2563EB",
        textColor: "text-white",
        border: "border border-blue-700",
        hoverBgColor: "#1E40AF",
    },
};

const SocialLoginButton: React.FC<Props> = ({ provider, onClick }) => {
    const { src, label, baseBgColor, textColor, border, hoverBgColor } = providerConfig[provider];

    const [hover, setHover] = useState(false);

    return (
        <button
            onClick={onClick}
            onMouseEnter={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
            className={`flex items-center gap-3 w-full px-4 py-2 rounded-md transition ${textColor} ${border} shadow-sm`}
            style={{ backgroundColor: hover ? hoverBgColor : baseBgColor }}
        >
            <img src={src} alt={`${provider} icon`} className="h-6 w-6 object-contain" />
            <span className="text-sm font-semibold">{label}</span>
        </button>
    );
};

export default SocialLoginButton;
