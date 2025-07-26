// .svelte 파일용 타입 선언
declare module '*.svelte' {
    import { SvelteComponentTyped } from 'svelte';
    export default class Component extends SvelteComponentTyped<any, any, any> {}
}

// 👇 여기에 추가
export {};

declare global {
    interface Window {
        app: any;
    }
}