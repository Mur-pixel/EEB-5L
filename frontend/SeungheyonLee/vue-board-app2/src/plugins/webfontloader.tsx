/**
 * loadFonts 함수는 웹폰트를 비동기로 로드하여 초기 페이지 렌더링 성능을 최적화합니다.
 * Vuetify나 기타 UI 라이브러리에서 사용하는 Roboto 폰트를 앱에 적용합니다.
 */
export async function loadFonts() {
    // webfontloader 모듈을 동적으로 import 합니다.
    // 초기 번들 크기를 줄이고, 폰트 로딩이 필요할 때만 로드합니다.
    const WebFont = await import('webfontloader');

    // Google Fonts에서 Roboto 패밀리를 굵기 옵션과 함께 로드합니다.
    // families 배열에 추가 글꼴을 넣어 쉽게 확장할 수 있습니다.
    WebFont.load({
        google: {
            families: ['Roboto:100,300,400,500,700,900'],
        },
    });

    // WebFont.load 호출 후, 폰트가 비동기로 적용되어 FOIT(글꼴 보이지 않음)을 방지합니다.
}
