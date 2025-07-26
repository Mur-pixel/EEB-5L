import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";
import HelloPropsTest from "./basics/HelloPropsTest.tsx";
import SimpleCounter from "./basics/SimpleCounter.tsx";
import ConditionalRenderTest from "./basics/ConditionalRenderTest.tsx";
import TodoListTest from "./basics/TodoListTest.tsx";
import { Provider } from "react-redux";
import {store} from "./state/store.ts";
import ReduxCounterTest from "./basics/ReduxCounterTest.tsx";
import PostList from "./basics/PostList.tsx";

const App = () => (
    <Provider store={store}>
        <div className="mt-10 text-3xl mx-auto max-w-6xl">
            <HelloPropsTest />
            <div>Name: react-test-app</div>
            <div>Framework: react-18</div>
            <SimpleCounter />
            <ConditionalRenderTest />
            <TodoListTest />
            <ReduxCounterTest/>
            <PostList/>
        </div>
    </Provider>
);

export default App