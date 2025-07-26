import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

import GameChipList from "../pages/list/GameChipList.vue";
import GameChipRegister from "../pages/register/GameChipRegister.vue";
import GameChipRead from "../pages/read/GameChipRead.vue";
import GameChipUpdate from "../pages/update/GameChipUpdate.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/list'
    },
    {
        path: '/list',
        name: 'GameChipList',
        component: GameChipList,
    },
    {
        path: '/register',
        name: 'GameChipRegister',
        component: GameChipRegister,
    },
    {
        path: '/read/:id',
        name: 'GameChipRead',
        components: { default: GameChipRead },
        props: { default: true },
    },
    {
        path: '/update/:id',
        name: 'GameChipUpdate',
        components: { default: GameChipUpdate },
        props: { default: true },
    },
]

const router = createRouter({
    history: createWebHistory('/game-chip/'),
    routes,
})

export default router