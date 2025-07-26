import React, { useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";

const VuetifyTailwindBoardAppWrapper = () => {
    const vueModuleRef = useRef<HTMLDivElement>(null);
    const isMountedRef = useRef(false);
    const unmountRef = useRef<(() => void) | null>(null);

    const location = useLocation()

    useEffect(() => {
        console.log('preparing mount vuetify board remotes app')

        if (!isMountedRef.current) {
            const loadRemoteComponent = async () => {
                const { vuetifyTailwindBoardAppMount  } = await import("vuetifyTailwindBoardApp/vuetifyBoardBootstrap");
                vuetifyTailwindBoardAppMount(vueModuleRef.current);
                isMountedRef.current = true;
            }

            loadRemoteComponent()
            console.log("Vuetify Board Remotes App ready: " + vueModuleRef)
        }

        return () => { };
    }, [])

    useEffect(() => {
        console.log('Vuetify Board 라우터 위치 바꿨어: ' + location.pathname)
        const handleNavigation = () => {
            console.log('handleNavigation()')
            if (location.pathname === "/vuetify-typescript-board-app") {
                console.log('라우터 변경')
                // eventBus.emit('vuetify-board-routing-event', '/');
                // eventBus.emit("module-unmount")
                // 다른 경로로 이동한 경우에만 eventBus의 구독 해제 등 정리 작업 수행
                // eventBus.off("dataReceived");
            }
        };

        // 컴포넌트가 마운트될 때 호출되는 이벤트 핸들러 등록
        handleNavigation();
    }, [location]);

    return (
        <div>
            <div>
                <div style={{ position: 'relative' }} ref={vueModuleRef}/>
            </div>
        </div>
    )

    // useEffect(() => {
    //     const loadRemoteComponent = async () => {
    //         try {
    //             const { vuetifyBoardAppMount } = await import("vuetifyTailwindBoardApp/vuetifyBoardBootstrap");
    //
    //             if (vueModuleRef.current && !isMountedRef.current) {
    //                 console.log("✅ Mounting Vue remote app");
    //                 // const unmount = await vuetifyBoardAppMount(vueModuleRef.current);
    //                 // unmountRef.current = unmount;
    //                 const unmount = await vuetifyTailwindBoardAppMount(vueModuleRef.current);
    //                 unmountRef.current = unmount;
    //                 isMountedRef.current = true;
    //             } else {
    //                 console.warn("❌ vueModuleRef가 없거나 이미 마운트됨");
    //             }
    //
    //             loadRemoteComponent();
    //             console.log("Vuetify Board Remotes App ready: " + vueModuleRef)
    //         } catch (err) {
    //             console.error("❌ Remote Vue 앱 로딩 실패:", err);
    //         }
    //     };
    //
    //     return () => {
    //         if (unmountRef.current) {
    //             console.log("🧹 Cleaning up: unmounting Vue remote app");
    //             unmountRef.current();
    //             unmountRef.current = null;
    //             isMountedRef.current = false;
    //         }
    //     };
    // }, []);
    //
    // return <div ref={vueModuleRef} style={{ width: "100%", height: "100%" }} />;
};

export default VuetifyTailwindBoardAppWrapper;
