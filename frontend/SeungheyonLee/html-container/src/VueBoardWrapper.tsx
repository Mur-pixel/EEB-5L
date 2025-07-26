import React, { useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";

/**
 * React 컴포넌트: VueBoardAppWrapper
 * - Module Federation을 통해 로드된 Vue 마이크로 프론트엔드 앱을 감싸서 렌더링합니다.
 * @param eventBus - Vue 앱과 이벤트 통신을 위한 버스 객체
 */
const VueBoardAppWrapper = ({ eventBus }): JSX.Element => {
    // vueModuleRef: Vue 앱을 마운트할 DOM 노드에 대한 ref
    const vueModuleRef = useRef<HTMLDivElement>(null);
    // isMountedRef: 마이크로 프론트엔드가 이미 마운트되었는지 여부를 추적
    const isMountedRef = useRef(false);
    // unmountRef: (필요 시) Vue 앱 언마운트 함수를 저장할 수 있는 ref
    const unmountRef = useRef<(() => void) | null>(null);

    // React Router의 현재 위치 정보를 가져옵니다.
    const location = useLocation();

    /**
     * 첫 렌더링 시 Vue 마이크로 프론트엔드 로드 및 마운트
     */
    useEffect(() => {
        console.log('preparing mount vuetify board remotes app');

        if (!isMountedRef.current) {
            const loadRemoteComponent = async () => {
                // Module Federation에 설정된 remote 이름을 기준으로 동적 import 수행
                // "vueBoardApp2/bootstrap"은 webpack.config.js의 remotes 설정에서 정의한 엔트리
                const { vueBoardAppMount } = await import("vueBoardApp2/bootstrap");
                // 가져온 마운트 함수에 마운트할 DOM 노드와 eventBus 전달
                vueBoardAppMount(vueModuleRef.current, eventBus);
                isMountedRef.current = true;
            };

            loadRemoteComponent();
            console.log("Vuetify Board Remotes App ready: ", vueModuleRef.current);
        }

        return () => {
            // 컴포넌트 언마운트 시 eventBus의 특정 이벤트 리스너 해제
            eventBus.off('routing-event', /* 해당 콜백 참조 */);
            // 필요 시 Vue 앱 언마운트 호출 (unmountRef 사용)
            if (unmountRef.current) {
                unmountRef.current();
            }
        };
    }, []); // 빈 배열: 최초 마운트 후 한 번만 실행

    /**
     * location 변경 시 Vue 앱에 라우팅 이벤트 전달
     */
    useEffect(() => {
        console.log('Vuetify Board 라우터 위치 바꿈: ', location.pathname);

        const handleNavigation = () => {
            console.log('handleNavigation() 호출');
            // 특정 경로일 때 Vue 앱에 라우팅 신호 전달
            if (location.pathname === "/vue-board/list") {
                console.log('라우터 변경 이벤트 emit');
                // Module Federation으로 마운트된 Vue 앱에서 수신할 이벤트 이름
                eventBus.emit('vue-board-routing-event', '/');
            }
        };

        // 위치가 바뀔 때마다 즉시 호출하여 Vue 앱 라우터 동기화
        handleNavigation();
    }, [location]);

    return (
        <div>
            {/* Vue 앱을 주입할 컨테이너 */}
            <div style={{ position: 'relative' }} ref={vueModuleRef} />
        </div>
    );
};

export default VueBoardAppWrapper;
