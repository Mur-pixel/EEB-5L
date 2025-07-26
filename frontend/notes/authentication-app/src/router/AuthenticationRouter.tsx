import React, { Suspense } from "react";
import {Routes, Route, Navigate} from "react-router-dom";
import SocialAuthenticationButtons from "../ui/components/SocialAuthenticationButtons.tsx";
import SignupAgreementPage from "../pages/SignupAgreementPage.tsx";
import SignupSummaryPage from "../pages/SignupSummaryPage.tsx";

const AuthenticationRouter = () => {
    return (
        <Suspense fallback={<div>로딩중 ........</div>}>
            <Routes>
                {/*<Route path="/" element={<Navigate to="authentication" replace/>}/>*/}
                <Route path="/" element={<SocialAuthenticationButtons/>}/>
                <Route path="/signup-agreement" element={<SignupAgreementPage/>}/>
                <Route path="/signup-summary" element={<SignupSummaryPage />} />
            </Routes>
        </Suspense>
    );
};

export default AuthenticationRouter;