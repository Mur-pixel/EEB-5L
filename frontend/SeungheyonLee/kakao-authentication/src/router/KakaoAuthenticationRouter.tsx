import React, { Suspense } from "react";
import {Routes, Route, Navigate} from "react-router-dom";
import KakaoAuthenticationPage from "../pages/KakaoAuthenticationPage.tsx";
import KakaoAuthenticationCallback from "../redirection/KakaoAuthenticationCallback.tsx";

const KakaoAuthenticationRouter = () => {
    return (
        <Suspense fallback={<div>로딩중 ........</div>}>
            <Routes>
                <Route path="/" element={<Navigate to="login" replace/>}/>
                <Route path="/login" element={<KakaoAuthenticationPage/>}/>
                <Route path="/callback" element={<KakaoAuthenticationCallback />} />
            </Routes>
        </Suspense>
    );
};

export default KakaoAuthenticationRouter;