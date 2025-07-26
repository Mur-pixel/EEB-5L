import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";
// import SocialAuthenticationButton from "./ui/components/SocialAuthenticationButton.tsx";
import AuthenticationRouter from "./router/AuthenticationRouter.tsx"

// const App = () => (
//   <div className="mt-10 text-3xl mx-auto max-w-6xl">
//     <div>Name: authentication-app</div>
//     <div>Framework: react-18</div>
//     {/*<SocialAuthenticationButton/>*/}
//     <AuthenticationRouter/>
//   </div>
// );

const App = () => {
    console.log("✅ Authentication Integration App Rendering");

    return (
        <AuthenticationRouter/>
    );
}

export default App