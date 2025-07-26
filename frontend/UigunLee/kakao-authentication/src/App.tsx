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