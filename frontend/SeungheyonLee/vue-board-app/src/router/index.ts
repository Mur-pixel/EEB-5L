import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

import VueBoardList from "../pages/list/VueBoardList.vue";
import VueBoardRegister from "../pages/register/VueBoardRegister.vue";
import VueBoardRead from "../pages/read/VueBoardRead.vue";
import VueBoardUpdate from "../pages/update/VueBoardUpdate.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/list'
    },
    {
        path: '/list',
        name: 'VueBoardList',
        component: VueBoardList,
    },
    {
        path: '/register',
        name: 'VueBoardRegister',
        component: VueBoardRegister,
    },
    {
        path: '/read/:boardId',
        name: 'VueBoardRead',
        components: { default: VueBoardRead },
        props: { default: true },
    },
    {
        path: '/update/:boardId',
        name: 'VueBoardUpdate',
        components: { default: VueBoardUpdate },
        props: { default: true },
    },
]

const router = createRouter({
    history: createWebHistory('/vue-board/'),
    routes,
})

export default router