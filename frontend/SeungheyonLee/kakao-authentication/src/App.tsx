import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";
import KakaoAuthenticationRouter from "./router/KakaoAuthenticationRouter.tsx";

const App: React.FC = () => {
    console.log("✅ AuthenticationApp 렌더링됨");

    return (
        <KakaoAuthenticationRouter/>
    );
}

export default App;

// npm install @emotion/react@^11.14.0 \
//             @emotion/styled@^11.14.0 \
//             @mui/icons-material@^7.0.1 \
//             @mui/material@^7.0.1 \
//             react-router-dom@^6.30.0