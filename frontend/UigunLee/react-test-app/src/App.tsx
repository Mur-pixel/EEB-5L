import React from "react";

import "./index.css";
import HelloPropsdTest from "./basics/HelloPropsdTest.tsx";
import SimpleCounter from "./basics/SimpleCounter.tsx";
import TodoListTest from "./basics/TodoListTest.tsx";
import ReduxCounterTest from "./basics/ReduxCounterTest.tsx";
import {Provider} from "react-redux";
import {store} from "./state/stort.ts";
import PostList from "./basics/PostList.tsx";

const App = () => (
    <Provider  store={store}>
    <div className="mt-10 text-3xl mx-auto max-w-6xl">
        <HelloPropsdTest/>
        <div>Name: navigation-bar-app</div>
        <div>Framework: react-18</div>
        <SimpleCounter/>
        <TodoListTest/>
        <ReduxCounterTest/>
        <PostList/>
    </div>
    </Provider>
);

export default App