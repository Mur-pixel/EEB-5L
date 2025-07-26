import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";
import GoogleAuthenticationRouter from "./router/GoogleAuthenticationRouter.tsx";

const App = () => {
    console.log("✅ AuthenticationApp 렌더링됨");

    return (
        <GoogleAuthenticationRouter/>
    );
}

export default App