import React, { useState, useRef, useEffect, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { privacyText } from "./PrivacyText";
import { termsText } from "./TermsText";

const SignupAgreementPage: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { user, loginType, temporaryUserToken } = location.state || {};

    const [privacyAgreed, setPrivacyAgreed] = useState(false);
    const [termsAgreed, setTermsAgreed] = useState(false);
    const [allAgreed, setAllAgreed] = useState(false);
    const [showPrivacy, setShowPrivacy] = useState(true);
    const [showTerms, setShowTerms] = useState(true);

    const [isHovered, setIsHovered] = useState(false);
    const buttonRef = useRef<HTMLButtonElement>(null);
    const canProceed = privacyAgreed && termsAgreed;

    useEffect(() => {
        setAllAgreed(privacyAgreed && termsAgreed);
    }, [privacyAgreed, termsAgreed]);

    const handleAllAgree = () => {
        const newVal = !allAgreed;
        setPrivacyAgreed(newVal);
        setTermsAgreed(newVal);
        setAllAgreed(newVal);

        if (newVal) {
            setTimeout(() => {
                buttonRef.current?.scrollIntoView({ behavior: "smooth", block: "center" });
            }, 100);
        }
    };

    const toggleView = (type: string) => {
        if (type === "privacy") setShowPrivacy(prev => !prev);
        if (type === "terms") setShowTerms(prev => !prev);
    };

    const onAgree = (type: string) => {
        if (type === "privacy" && privacyAgreed) setShowPrivacy(false);
        if (type === "terms" && termsAgreed) setShowTerms(false);
    };

    const goToLogin = () => {
        if (canProceed) {
            sessionStorage.setItem("agreed", "true");
            navigate("/authentication/signup-summary", {
                state: {
                    user,
                    loginType,
                    temporaryUserToken,
                }
            });
        }
    };

    const goBack = () => {
        navigate("/");
    };

    const loginButtonStyle = useMemo(() => {
        const baseStyle = {
            width: "100%",
            maxWidth: "20rem",
            margin: "1.5rem auto 0 auto",
            padding: "0.75rem 1.5rem",
            borderRadius: "0.375rem",
            fontWeight: 600,
            transition: "background-color 0.3s ease",
            display: "block",
            color: canProceed ? "#000" : "#888",
            backgroundColor: "#facc15", // yellow-400
            cursor: canProceed ? "pointer" : "not-allowed",
        };

        if (!canProceed) {
            return {
                ...baseStyle,
                backgroundColor: "#e5e7eb", // gray-300
                color: "#9ca3af", // gray-500
            };
        }

        if (isHovered) {
            return {
                ...baseStyle,
                backgroundColor: "#fbbf24", // yellow-500
            };
        }

        return baseStyle;
    }, [canProceed, isHovered]);

    if (!user || !loginType || !temporaryUserToken) {
        return (
            <div style={{
                display: "flex",
                flexDirection: "column",
                minHeight: "100vh",
                justifyContent: "center",
                alignItems: "center"
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
                    onClick={goBack}
                >
                    홈으로 돌아가기
                </button>
            </div>
        );
    }

    return (
        <div style={{
            maxWidth: "48rem",
            width: "90%",
            margin: "2.5rem auto 0 auto",
            padding: "1.5rem",
            borderRadius: "0.75rem",
            border: "1px solid #e5e7eb",
            backgroundColor: "white"
        }}>
            <h2 style={{ fontSize: "1.5rem", fontWeight: 700, marginBottom: "1.5rem" }}>약관 동의</h2>

            {/* 전체 약관 동의 */}
            <section style={{ marginBottom: "2rem" }}>
                <label style={{ fontWeight: 700, display: "flex", alignItems: "center", gap: "0.5rem" }}>
                    <input
                        type="checkbox"
                        checked={allAgreed}
                        onChange={handleAllAgree}
                    />
                    전체 약관에 동의합니다
                </label>
            </section>

            {/* 개인정보 처리방침 */}
            <section style={{ marginBottom: "2rem" }}>
                <label style={{ fontWeight: 500, display: "flex", alignItems: "center", gap: "0.5rem" }}>
                    <input
                        type="checkbox"
                        checked={privacyAgreed}
                        onChange={() => {
                            setPrivacyAgreed(!privacyAgreed);
                            onAgree("privacy");
                        }}
                    />
                    개인정보처리방침에 동의합니다
                </label>

                {showPrivacy && (
                    <div
                        style={{
                            fontSize: "0.875rem",
                            color: "#374151",
                            marginTop: "0.5rem",
                            padding: "1rem",
                            border: "1px solid #d1d5db",
                            borderRadius: "0.375rem",
                            backgroundColor: "#f9fafb",
                            maxHeight: "16rem",
                            overflowY: "auto",
                            whiteSpace: "pre-line"
                        }}
                        dangerouslySetInnerHTML={{ __html: privacyText }}
                    />
                )}

                <div style={{ marginTop: "0.5rem", textAlign: "left" }}>
                    <button
                        style={{
                            fontSize: "0.875rem",
                            color: "#2563eb",
                            textDecoration: "underline",
                            background: "none",
                            border: "none",
                            padding: 0,
                            cursor: "pointer"
                        }}
                        onClick={() => toggleView("privacy")}
                    >
                        {showPrivacy ? "내용 접기" : "자세히 보기"}
                    </button>
                </div>
            </section>

            <hr style={{ borderColor: "#d1d5db", margin: "1.5rem 0" }} />

            {/* 이용약관 */}
            <section style={{ marginBottom: "2rem" }}>
                <label style={{ fontWeight: 500, display: "flex", alignItems: "center", gap: "0.5rem" }}>
                    <input
                        type="checkbox"
                        checked={termsAgreed}
                        onChange={() => {
                            setTermsAgreed(!termsAgreed);
                            onAgree("terms");
                        }}
                    />
                    이용약관에 동의합니다
                </label>

                {showTerms && (
                    <div
                        style={{
                            fontSize: "0.875rem",
                            color: "#374151",
                            marginTop: "0.5rem",
                            padding: "1rem",
                            border: "1px solid #d1d5db",
                            borderRadius: "0.375rem",
                            backgroundColor: "#f9fafb",
                            maxHeight: "16rem",
                            overflowY: "auto",
                            whiteSpace: "pre-line"
                        }}
                        dangerouslySetInnerHTML={{ __html: termsText }}
                    />
                )}

                <div style={{ marginTop: "0.5rem", textAlign: "left" }}>
                    <button
                        style={{
                            fontSize: "0.875rem",
                            color: "#2563eb",
                            textDecoration: "underline",
                            background: "none",
                            border: "none",
                            padding: 0,
                            cursor: "pointer"
                        }}
                        onClick={() => toggleView("terms")}
                    >
                        {showTerms ? "내용 접기" : "자세히 보기"}
                    </button>
                </div>
            </section>

            {/* 버튼 */}
            <button
                ref={buttonRef}
                onClick={goToLogin}
                onMouseEnter={() => setIsHovered(true)}
                onMouseLeave={() => setIsHovered(false)}
                disabled={!canProceed}
                style={loginButtonStyle}
            >
                간편 로그인으로 계속하기
            </button>

            <p
                onClick={goBack}
                style={{
                    marginTop: "1rem",
                    fontSize: "0.875rem",
                    textAlign: "center",
                    color: "#4b5563",
                    textDecoration: "underline",
                    cursor: "pointer"
                }}
            >
                돌아가기
            </p>
        </div>
    );
};

export default SignupAgreementPage;
