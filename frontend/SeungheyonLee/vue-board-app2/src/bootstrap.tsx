import { createApp, h } from 'vue' // Vue 애플리케이션 생성 및 렌더링 헬퍼 함수를 가져옵니다.
import type { App as VueApp } from 'vue' // Vue 애플리케이션 타입 정의를 가져옵니다.
import App from './App.vue' // 최상위 App 컴포넌트를 가져옵니다.
// import vuetify from './plugins/vuetify' // Vuetify 플러그인 설정을 가져옵니다.
import { loadFonts } from './plugins/webfontloader' // 웹폰트 로더 함수를 가져옵니다.

import { aliases } from 'vuetify/iconsets/mdi' // MDI 아이콘 별칭 설정을 가져옵니다.
import * as mdi from '@mdi/js' // MDI 아이콘 경로 데이터를 가져옵니다.

import * as components from 'vuetify/components' // Vuetify 일반 컴포넌트를 가져옵니다.
import * as directives from 'vuetify/directives' // Vuetify 디렉티브를 가져옵니다.
import * as labsComponents from 'vuetify/labs/components' // Vuetify 실험실 컴포넌트를 가져옵니다.

import { createVuetify } from 'vuetify/lib/framework.mjs' // Vuetify 인스턴스 생성 함수를 가져옵니다.
import { createPinia } from 'pinia' // Pinia 상태 관리 라이브러리를 가져옵니다.
// import router from './router' // Vue 라우터 설정을 가져옵니다.

let app: VueApp<Element> | null = null; // 전역 Vue 앱 인스턴스를 저장할 변수입니다.

/**
 * Vue 앱을 Shadow DOM에 마운트합니다.
 * @param el - 마운트할 HTMLElement 선택자 또는 요소입니다.
 * @param eventBus - 컴포넌트 간 이벤트 통신 버스 객체입니다.
 */
export const vueBoardAppMount = (el: string | Element, eventBus: EventBus) => {
    console.log('Vue 앱을 마운트하는 중입니다.');

    // 1. 컨테이너 요소를 찾습니다.
    const container = typeof el === 'string' ? document.querySelector(el) : el;
    if (!container) return; // 요소가 없으면 종료합니다.

    // 2. 이전 마운트를 위해 내부 내용을 초기화합니다.
    container.innerHTML = '';

    // 3. Shadow DOM을 오픈 모드로 생성합니다.
    const shadowRoot = container.attachShadow({ mode: 'open' });

    // 4. Shadow DOM에 Vuetify 및 MDI 스타일 시트를 주입합니다.
    injectVuetifyCssIntoShadow(shadowRoot);

    // 5. Shadow DOM 내부에 Vue 앱 루트 요소를 생성합니다.
    const shadowAppRoot = document.createElement('div');
    shadowRoot.appendChild(shadowAppRoot);

    // 6. 웹폰트 로딩 후 Vue 앱을 설정하고 마운트합니다.
    loadFonts().then(() => {
        const vuetifyInstance = createVuetify({
            components: { ...components, ...labsComponents }, // 컴포넌트 등록.
            directives: { ...directives }, // 디렉티브 등록.
            icons: { defaultSet: 'mdi', aliases, sets: { mdi } }, // 아이콘 설정.
        });

        // Vue 앱 인스턴스를 생성합니다.
        app = createApp({ render: () => h(App, { eventBus }) });

        // 플러그인 및 라우터, Pinia를 앱에 적용합니다.
        const pinia = createPinia();
        // app.use(vuetifyInstance).use(router).use(pinia);
        app.use(vuetifyInstance).use(pinia);
        // eventBus 객체를 전역 프로바이더로 등록합니다.
        app.provide('eventBus', eventBus);

        // eventBus로부터 라우팅 이벤트를 수신합니다.
        eventBus.on('vue-board-routing-event', (path: string) => {
            // 현재 경로와 다를 때만 라우터를 이동합니다.
            if (router.currentRoute.value.fullPath !== path) {
                router.push(path);
            }
        });

        // Vue 앱을 Shadow DOM 루트에 마운트합니다.
        app.mount(shadowAppRoot);
    });
};

/**
 * Vuetify 및 MDI 스타일 시트를 Shadow DOM에 주입합니다.
 * @param shadowRoot - 스타일을 주입할 ShadowRoot 객체입니다.
 */
function injectVuetifyCssIntoShadow(shadowRoot: ShadowRoot) {
    const vuetifyLink = document.createElement('link');
    vuetifyLink.rel = 'stylesheet';
    vuetifyLink.href = 'https://cdn.jsdelivr.net/npm/vuetify@3.x/dist/vuetify.min.css';

    const mdiLink = document.createElement('link');
    mdiLink.rel = 'stylesheet';
    mdiLink.href = 'https://cdn.jsdelivr.net/npm/@mdi/font@7.x/css/materialdesignicons.min.css';

    // 링크 태그를 ShadowRoot에 추가합니다.
    shadowRoot.append(vuetifyLink, mdiLink);
}

/**
 * EventBus 인터페이스 정의.
 * 컴포넌트 간 느슨한 결합을 위한 이벤트 통신 버스입니다.
 */
interface EventBus {
    listeners: { [eventName: string]: Function[] }; // 이벤트별 콜백 배열.

    /**
     * 이벤트 리스너를 등록합니다.
     * @param eventName - 구독할 이벤트 이름입니다.
     * @param callback - 이벤트 발생 시 호출될 콜백 함수입니다.
     */
    on(eventName: string, callback: Function): void;

    /**
     * 특정 이벤트 리스너를 해제합니다.
     * @param eventName - 해제할 이벤트 이름입니다.
     * @param callback - 해제할 콜백 함수입니다.
     */
    off(eventName: string, callback: Function): void;

    /**
     * 이벤트를 발행합니다.
     * @param eventName - 발행할 이벤트 이름입니다.
     * @param data - 콜백에 전달할 데이터입니다.
     */
    emit(eventName: string, data?: any): void;
}

/**
 * 간단한 EventBus 구현체.
 */
const eventBus: EventBus = {
    listeners: {}, // 내부 이벤트 리스너 저장소.

    on(eventName, callback) {
        // 이벤트 배열이 없으면 새로 생성합니다.
        if (!this.listeners[eventName]) {
            this.listeners[eventName] = [];
        }
        // 콜백을 배열에 추가합니다.
        this.listeners[eventName].push(callback);
    },

    off(eventName, callback) {
        const handlers = this.listeners[eventName];
        if (!handlers) return; // 해당 이벤트가 없으면 종료합니다.
        const idx = handlers.indexOf(callback);
        if (idx >= 0) {
            // 배열에서 콜백을 제거합니다.
            handlers.splice(idx, 1);
        }
    },

    emit(eventName, data) {
        const handlers = this.listeners[eventName];
        if (!handlers) return; // 구독된 핸들러가 없으면 종료합니다.
        // 각 핸들러를 호출하며 데이터를 전달합니다.
        handlers.forEach((handler) => handler(data));
    },
};

// DOM에서 #vue-board-app 요소를 찾아 앱을 마운트합니다.
const root = document.querySelector('#vue-board-app');
if (root) {
    vueBoardAppMount(root, eventBus); // 초기 마운트 실행.
}
