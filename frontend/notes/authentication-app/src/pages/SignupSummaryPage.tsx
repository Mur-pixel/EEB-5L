import React, { useState, useMemo } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axiosInstance from "../utility/AxiosInst.ts";

const SignupSummaryPage: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { user, loginType, temporaryUserToken } = location.state || {};

    if (!user || !loginType || !temporaryUserToken) {
        return (
            <div style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                minHeight: "100vh",
            }}>
                <p style={{ fontSize: "1.125rem", fontWeight: 600 }}>잘못된 접근입니다.</p>
                <button
                    style={{
                        marginTop: "1rem",
                        padding: "0.5rem 1rem",
                        backgroundColor: "#3b82f6",
                        color: "white",
                        borderRadius: "0.375rem",
                    }}
                    onClick={() => navigate("/")}
                >
                    홈으로 돌아가기
                </button>
            </div>
        );
    }

    const [nickname, setNickname] = useState(user.nickname || user.name || "");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [isHovered, setIsHovered] = useState(false);

    const handleSignup = async () => {
        setError(null);

        if (!nickname.trim()) {
            setError("닉네임을 입력해주세요.");
            return;
        }

        try {
            setLoading(true);

            const response = await axiosInstance.springAxiosInst.post("/account/register", {
                email: user.email,
                nickname: nickname.trim(),
                loginType,
                temporaryUserToken,
            });

            const createdUserToken = response.data;

            localStorage.setItem("userToken", createdUserToken);
            localStorage.setItem("userEmail", user.email);
            localStorage.setItem("userNickname", nickname.trim());
            window.dispatchEvent(new Event("user-token-changed"));

            navigate("/", { replace: true });
        } catch (e: any) {
            setError(
                e.response?.data?.message ||
                "회원가입 중 오류가 발생했습니다. 다시 시도해주세요."
            );
        } finally {
            setLoading(false);
        }
    };

    const buttonStyle = useMemo(() => {
        const base = {
            width: "100%",
            padding: "0.75rem",
            fontWeight: 600,
            borderRadius: "0.375rem",
            color: "white",
            transition: "background-color 0.3s ease",
            border: "none",
            cursor: loading ? "not-allowed" : "pointer",
            backgroundColor: "#facc15", // 기본색 (yellow-400)
        };

        if (loading) {
            return {
                ...base,
                backgroundColor: "#d1d5db", // gray-300
                color: "#9ca3af", // gray-500
            };
        }

        if (isHovered) {
            return {
                ...base,
                backgroundColor: "#fbbf24", // yellow-500
            };
        }

        return base;
    }, [loading, isHovered]);

    return (
        <div style={{
            maxWidth: "36rem",
            margin: "5rem auto 0 auto",
            padding: "1.5rem",
            border: "1px solid #e5e7eb",
            borderRadius: "0.75rem",
            backgroundColor: "white",
            boxShadow: "0 1px 2px rgba(0,0,0,0.1)"
        }}>
            <h1 style={{ fontSize: "1.5rem", fontWeight: 700, marginBottom: "1.5rem" }}>
                회원 정보 확인 및 신청
            </h1>

            <div style={{ marginBottom: "1rem" }}>
                <p style={{ color: "#374151", fontWeight: 500 }}>이메일 주소</p>
                <p style={{ fontSize: "1.125rem" }}>{user.email || "알 수 없음"}</p>
            </div>

            <div style={{ marginBottom: "1.5rem" }}>
                <label htmlFor="nickname" style={{
                    color: "#374151",
                    fontWeight: 500,
                    display: "block",
                    marginBottom: "0.25rem"
                }}>
                    닉네임
                </label>
                <input
                    id="nickname"
                    type="text"
                    value={nickname}
                    onChange={(e) => setNickname(e.target.value)}
                    style={{
                        width: "100%",
                        padding: "0.5rem",
                        border: "1px solid #d1d5db",
                        borderRadius: "0.375rem",
                        outline: "none",
                        fontSize: "1rem",
                    }}
                />
            </div>

            {error && (
                <p style={{ marginBottom: "1rem", color: "#dc2626" }}>
                    {error}
                </p>
            )}

            <button
                onClick={handleSignup}
                onMouseEnter={() => setIsHovered(true)}
                onMouseLeave={() => setIsHovered(false)}
                disabled={loading}
                style={buttonStyle}
            >
                {loading ? "처리 중..." : "회원 신청"}
            </button>
        </div>
    );
};

export default SignupSummaryPage;
