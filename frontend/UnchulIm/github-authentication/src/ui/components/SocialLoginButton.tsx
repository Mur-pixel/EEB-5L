import React from "react";
import { Button } from "@mui/material";
import { GitHub } from "@mui/icons-material"; // GitHub 아이콘 추가 가능

type Provider = "google" | "kakao" | "github";

interface Props {
    provider: Provider;
    onClick: () => void;
}

const providerInfo: Record<Provider, {
    label: string;
    color: string;
    icon: React.ReactNode;
}> = {
    google: { label: "Google 로그인", color: "#DB4437", icon: null },
    kakao: { label: "", color: "", icon: null },   // 사용하지 않음
    github: { label: "GitHub 로그인", color: "#000", icon: <GitHub /> }, // 추가
};
const SocialLoginButton: React.FC<Props> = ({ provider, onClick }) => {
    const info = providerInfo[provider];

    if (!info.label) return null; // 유효하지 않은 provider 방어 처리

    return (
        <Button
            onClick={onClick}
            startIcon={info.icon}
            sx={{
                mt: 1,
                mb: 1,
                backgroundColor: info.color,
                color: provider === "kakao" ? "#000" : "#fff",
                "&:hover": {
                    opacity: 0.9,
                    backgroundColor: info.color,
                },
            }}
            fullWidth
            variant="contained"
        >
            {info.label}
        </Button>
    );
};

export default SocialLoginButton