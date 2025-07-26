import React, {useState} from "react";
import { AppBar, Toolbar, Typography, Button } from "@mui/material";
import {Link} from "react-router-dom";

import HomeIcon from "@mui/icons-material/Home";
import CodeIcon from "@mui/icons-material/Code";
import JavascriptIcon from "@mui/icons-material/Javascript";
import ForumIcon from "@mui/icons-material/Forum";
import SportsGymnasticsIcon from '@mui/icons-material/SportsGymnastics';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';

const App: React.FC = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleAuthClick = () => {
        setIsLoggedIn(isLoggedIn => !isLoggedIn);
    };

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    EDDI
                </Typography>
                <Button
                    color="inherit"
                    component={Link}
                    to="/"
                    startIcon={<HomeIcon />}
                />
                <Button
                    color="inherit"
                    component={Link}
                    to="/html-css-test"
                    startIcon={<CodeIcon />}
                >
                    HTML
                </Button>
                <Button
                    color="inherit"
                    component={Link}
                    to="/js-test"
                    startIcon={<JavascriptIcon />}
                >
                    JS
                </Button>
                {/*<Button*/}
                {/*    color="inherit"*/}
                {/*    component={Link}*/}
                {/*    to="/board/list"*/}
                {/*    startIcon={<ForumIcon />}*/}
                {/*>*/}
                {/*    R게시판*/}
                {/*</Button>*/}
                <Button
                    color="inherit"
                    component={Link}
                    to="/vue-board/list"
                    startIcon={<ForumIcon />}
                >
                    V게시판
                </Button>
                <Button
                    color="inherit"
                    component={Link}
                    to="/react-test"
                    startIcon={<SportsGymnasticsIcon />}
                >
                    React
                </Button>
                {/* 로그인 / 로그아웃 버튼 */}
                <Button
                    color="inherit"
                    onClick={handleAuthClick}
                    startIcon={isLoggedIn ? <LogoutIcon /> : <LoginIcon />}
                >
                </Button>
            </Toolbar>
        </AppBar>
    );
};

export default App