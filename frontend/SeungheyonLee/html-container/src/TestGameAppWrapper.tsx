import React, { useEffect, useRef } from "react";

const TestGameAppWrapper: React.FC = () => {
    const containerRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        let app: any;

        import("testGameApp/App") // ✅ MFE로 불러온 Svelte 앱
            .then((mod) => {
                const SvelteApp = mod.default;

                if (containerRef.current) {
                    app = new SvelteApp({
                        target: containerRef.current,
                    });
                }
            })
            .catch((err) => {
                console.error("❌ Failed to mount diceGameApp via Module Federation:", err);
            });

        return () => {
            if (app) app.$destroy();
        };
    }, []);

    return <div ref={containerRef} />;
};

export default TestGameAppWrapper;
