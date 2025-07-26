import {createApp, h} from 'vue'
import type { App as VueApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'

import 'vuetify/styles' // Vuetify의 기본 스타일
import '@mdi/font/css/materialdesignicons.css' // (선택) 아이콘 폰트

import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import * as labsComponents from 'vuetify/labs/components'

import { createVuetify } from 'vuetify/lib/framework.mjs'

let app: VueApp<Element> | null = null;

export const vuetifyTailwindBoardAppMount = (el: string | Element) => {
    loadFonts().then(() => {
        const vuetify = createVuetify({
            components: {
                ...components,
                ...labsComponents,
            },
            directives: {
                ...directives,
            },
        });

        app = createApp({
            render: () => h(App),
        });

        // app.use(vuetify).use(boardModule).use(router);
        app.use(vuetify)
        app.mount(el);
    });
};

export const vuetifyTailwindBoardAppUnmount = () => {
    if (app) {
        app.unmount();
        app = null;
    }
};
