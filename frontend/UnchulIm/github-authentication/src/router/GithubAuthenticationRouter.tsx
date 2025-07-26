import React, { Suspense } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import GithubAuthenticationPage from "../pages/GithubAuthenticationPage.tsx";

const GithubAuthenticationRouter = () => {
    return (
        <Suspense fallback={<div>로딩중...</div>}>
            <Routes>
                <Route path="/" element={<Navigate to="login" replace />} />
                <Route path="/login" element={<GithubAuthenticationPage />} />
            </Routes>
        </Suspense>
    );
};

export default GithubAuthenticationRouter;
