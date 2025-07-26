import React, {lazy, Suspense, useEffect, useState} from "react";
import ReactDOM from "react-dom/client";

import {CircularProgress} from "@mui/material";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {RecoilRoot,useRecoilState} from "recoil";

import styled, { ThemeProvider, createGlobalStyle } from "styled-components";

import {screenModState} from "../../shared-state/atoms.ts"
import DiceGameAppWrapper from "./DiceGameAppWrapper.tsx";

import mitt from 'mitt';
import VueBoardAppWrapper from "./VueBoardAppWrapper.tsx";

const eventBus = mitt();

const NavigationBarApp = lazy(() => import("navigationBarApp/App"));
const KakaoAuthenticationApp = lazy(() => import("kakaoAuthenticationApp/App"));
const PracticeApp = lazy(() => import("practiceApp/App"));
const GoogleAuthenticationApp = lazy(() => import("googleAuthenticationApp/App"));
const GithubAuthenticationApp = lazy(() => import("githubAuthenticationApp/App"))

const InnerApp  = () => {
    const [isNavigationBarLoaded, setIsNavigationBarLoaded] = useState(false);
    const [isDarkMode, setIsDarkMode] = useRecoilState(screenModState);
    const theme = isDarkMode ? darkTheme : lightTheme;

    useEffect(() => {
        import("navigationBarApp/App")
            .then(() => setIsNavigationBarLoaded(true))
            .catch((err) => console.error("Failed to load navigation bar:", err));
    }, []);

    return (
        <ThemeProvider theme={theme}>
            <GlobalStyle />
            <BrowserRouter>
                <Suspense fallback={<CircularProgress />}>
                    <NavigationBarApp />
                    <ToggleButton onClick={() => setIsDarkMode((prev) => !prev)}>
                        {isDarkMode ? "🌙 다크 모드 해제" : "🌞 다크 모드 설정"}
                    </ToggleButton>
                    <Wrapper>
                        <ScreenWrap>
                        <Routes>
                            <Route path="/" element={<div>Home Page</div>} />
                            <Route path="/kakao-authentication/*" element={<KakaoAuthenticationApp />} />
                            <Route path="/practice" element={<PracticeApp />} />
                            <Route path="/google-authentication/*" element={<GoogleAuthenticationApp />} />
                            <Route path="/dice-game" element={<DiceGameAppWrapper />} />
                            <Route path="/vue-board/*" element={<VueBoardAppWrapper eventBus={eventBus}/>} />
<Route path="/github-authentication/*" element={<GithubAuthenticationRouter />} />                        </Routes>
                        </ScreenWrap>
                    </Wrapper>
                </Suspense>
            </BrowserRouter>
        </ThemeProvider>
    );
};
const App = () => (
    <RecoilRoot>
        <InnerApp />
    </RecoilRoot>
);

export default App;

const container = document.getElementById("app") as HTMLElement;
if (!container) {
    throw new Error("Root container #app not found");
}

const root = ReactDOM.createRoot(container);
root.render(<App/>);

const lightTheme = {
    background: "#ffffff",
    text: "#000000",
};

const darkTheme = {
    background: "#121212",
    text: "gold",
};

const GlobalStyle = createGlobalStyle`
  body {
    background-color: ${({ theme }) => theme.background};
    color: ${({ theme }) => theme.text};
    margin: 0;
    transition: background-color 0.3s, color 0.3s;
    font-family: sans-serif;
  }
`;

const Wrapper = styled.div`
  padding-top: 64px;
    display: flex;
    justify-content: center;
    align-items: center;
`;

const ScreenWrap = styled.div`
  width: 95vw;
`;

const ToggleButton = styled.button`
  position: fixed;
  top: 70px;
  right: 20px;
  padding: 8px 16px;
  border: 2px solid ${({ theme }) => theme.text};
  background: none;
  color: ${({ theme }) => theme.text};
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  z-index: 1000;

  &:hover {
    background: ${({ theme }) => theme.text};
    color: ${({ theme }) => theme.background};
  }
`;