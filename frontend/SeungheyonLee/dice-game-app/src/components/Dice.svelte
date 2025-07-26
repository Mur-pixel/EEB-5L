<script lang="ts">
    // Svelte 라이프사이클 훅 onMount를 가져옴 (컴포넌트가 마운트될 때 실행됨)
    import { onMount } from 'svelte';
    // Three.js(3D 렌더링)와 Cannon-es(물리 엔진) 모듈을 가져옴
    import * as THREE from 'three';
    import * as CANNON from 'cannon-es';

    // 주사위 면 이미지를 각각 임포트함 (텍스처로 사용)
    import Side1 from '../assets/Side_1_Pip.png';
    import Side2 from '../assets/Side_2_Pips.png';
    import Side3 from '../assets/Side_3_Pips.png';
    import Side4 from '../assets/Side_4_Pips.png';
    import Side5 from '../assets/Side_5_Pips.png';
    import Side6 from '../assets/Side_6_Pips.png';

    // -----------------------------
    // 변수 선언 섹션
    // -----------------------------
    // 렌더링할 DOM 컨테이너
    let container: HTMLDivElement;
    // Three.js 메쉬(주사위), 씬, 카메라, 렌더러
    let diceMesh: THREE.Mesh;
    let scene: THREE.Scene;
    let camera: THREE.PerspectiveCamera;
    let renderer: THREE.WebGLRenderer;

    // Cannon 물리 엔진 세계(World)와 주사위 바디(Body)
    let world: CANNON.World;
    let diceBody: CANNON.Body;
    // 애니메이션 루프 간 간격을 계산하기 위한 이전 프레임 타임스탬프
    let lastTime: number | undefined;

    // 주사위 결과값, 멈춘 프레임 수 카운트, 결과 전송 여부 플래그
    let result = 0;
    let stoppedFrameCount = 0;
    let hasSentResult = false;

    // 부모 컴포넌트에서 전달받는 게임 ID (결과 서버 저장 시 사용)
    export let gameId: number;

    // -----------------------------
    // 텍스처 및 면 정보
    // -----------------------------
    // 각 축 방향 별 주사위 면 이미지 순서 (+X, -X, +Y, -Y, +Z, -Z)
    const texturePaths = [Side3, Side4, Side1, Side6, Side2, Side5];
    // 면 법선 벡터 -> 숫자 매핑 객체
    const faceNormalToValue: { [key: string]: number } = {
        '0,1,0': 1,
        '0,-1,0': 6,
        '1,0,0': 3,
        '-1,0,0': 4,
        '0,0,1': 2,
        '0,0,-1': 5
    };
    // Vec3 객체 배열로 변환하여 연산에 사용
    const faceNormals = Object.keys(faceNormalToValue).map(key => {
        const [x, y, z] = key.split(',').map(Number);
        return new CANNON.Vec3(x, y, z);
    });

    // -----------------------------
    // 함수: 위쪽 면 숫자 계산
    // -----------------------------
    function getTopFaceIndex(): number {
        const worldUp = new CANNON.Vec3(0, 1, 0);  // 월드 업 벡터
        let maxDot = -Infinity;
        let topFace: CANNON.Vec3 | null = null;

        // 모든 면 법선을 현재 회전에 따라 변환하고, 업 벡터와의 dot 연산으로 위쪽 면 결정
        for (const normal of faceNormals) {
            const worldNormal = diceBody.quaternion.vmult(normal);
            const dot = worldNormal.dot(worldUp);
            if (dot > maxDot) {
                maxDot = dot;
                topFace = normal;
            }
        }
        if (!topFace) return -1;
        const key = `${topFace.x},${topFace.y},${topFace.z}`;
        return faceNormalToValue[key] ?? -1;
    }

    // -----------------------------
    // 함수: 주사위 멈춤 여부 확인
    // -----------------------------
    function isDiceStopped(): boolean {
        // 선형 속도와 회전 속도가 임계값 이하라면 멈췄다고 판단
        const linear = diceBody.velocity.length();
        const angular = diceBody.angularVelocity.length();
        return linear < 0.1 && angular < 0.1;
    }

    // -----------------------------
    // 함수: Three.js 초기화
    // -----------------------------
    function initThree(): void {
        scene = new THREE.Scene();
        const width = container.clientWidth;
        const height = container.clientHeight;

        // 카메라 설정 (FOV, 종횡비, 클리핑)
        camera = new THREE.PerspectiveCamera(75, width / height, 0.1, 1000);
        camera.position.set(3, 4, 5);
        camera.lookAt(0, 0, 0);

        // 렌더러 생성 및 설정
        renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setSize(width, height);
        renderer.setPixelRatio(window.devicePixelRatio || 1);
        container.appendChild(renderer.domElement);

        // 주사위 메쉬 생성: 텍스처 배열을 재질로 사용
        const loader = new THREE.TextureLoader();
        const materials = texturePaths.map(path =>
            new THREE.MeshStandardMaterial({ map: loader.load(path) })
        );
        diceMesh = new THREE.Mesh(new THREE.BoxGeometry(1, 1, 1), materials);
        scene.add(diceMesh);

        // 조명 추가: HemisphereLight
        scene.add(new THREE.HemisphereLight(0xffffff, 0x444444, 1));

        // 바닥 메쉬 생성: Plane 뒤집어 씬에 추가
        const ground = new THREE.Mesh(
            new THREE.PlaneGeometry(10, 10),
            new THREE.MeshStandardMaterial({ color: 0xdddddd })
        );
        ground.rotation.x = -Math.PI / 2;
        scene.add(ground);
    }

    // -----------------------------
    // 함수: Cannon 물리 엔진 초기화
    // -----------------------------
    function initPhysics(): void {
        // 중력 벡터 설정
        world = new CANNON.World({ gravity: new CANNON.Vec3(0, -9.82, 0) });

        // 바닥 바디 (정적 Plane) 생성 및 추가
        const groundBody = new CANNON.Body({
            type: CANNON.Body.STATIC,
            shape: new CANNON.Plane()
        });
        groundBody.quaternion.setFromEuler(-Math.PI / 2, 0, 0);
        world.addBody(groundBody);

        // 주사위 바디 (동적 Box) 생성 및 추가
        diceBody = new CANNON.Body({
            mass: 1,
            shape: new CANNON.Box(new CANNON.Vec3(0.5, 0.5, 0.5)),
            position: new CANNON.Vec3(0, 5, 0),
        });
        world.addBody(diceBody);
    }

    // -----------------------------
    // 함수: 주사위 굴리기 설정
    // -----------------------------
    function rollDice(): void {
        // 이전 결과 리셋
        result = 0;
        stoppedFrameCount = 0;
        hasSentResult = false;

        // 속도와 위치, 회전값 초기화
        diceBody.velocity.set(0, 0, 0);
        diceBody.angularVelocity.set(0, 0, 0);
        diceBody.position.set(0, 5, 0);
        diceBody.quaternion.set(0, 0, 0, 1);

        // 랜덤 회전 속도 설정 (최소 spin 보장)
        const minSpin = 5;
        diceBody.angularVelocity.set(
            (Math.random() - 0.5) * 20 + Math.sign(Math.random() - 0.5) * minSpin,
            (Math.random() - 0.5) * 20 + Math.sign(Math.random() - 0.5) * minSpin,
            (Math.random() - 0.5) * 20 + Math.sign(Math.random() - 0.5) * minSpin
        );
    }

    // -----------------------------
    // 함수: 서버에 결과 전송
    // -----------------------------
    async function sendResultToServer(result: number): Promise<void> {
        // 로컬에서 토큰 불러오기
        const userToken = localStorage.getItem('userToken');
        // 필수 정보 확인
        if (!userToken || !gameId) {
            console.warn('❗ userToken 또는 gameId 누락');
            return;
        }
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/dice/save-roll-result`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${userToken}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ gameId, number: result })
            });
            // 응답 파싱 및 로깅
            const ok = await response.json();
            if (ok) console.log('✅ 주사위 결과 전송 성공');
            else    console.warn('⚠ 주사위 결과 저장 실패');
        } catch (error) {
            console.error('전송 실패:', error);
        }
    }

    // -----------------------------
    // 함수: 애니메이션 루프
    // -----------------------------
    function animate(time: number): void {
        requestAnimationFrame(animate);    // 다음 프레임 예약

        // 지난 프레임과의 시간 차 계산
        const dt = lastTime ? (time - lastTime) / 1000 : 0;
        lastTime = time;

        // 물리 세계 스텝 실행
        world.step(1 / 60, dt);
        // 메쉬 위치/회전 동기화
        diceMesh.position.copy(diceBody.position as any);
        diceMesh.quaternion.copy(diceBody.quaternion as any);

        // 주사위가 멈추면 결과 산출 및 서버 전송
        if (isDiceStopped()) {
            stoppedFrameCount++;
            if (stoppedFrameCount >= 15 && result === 0) {
                result = getTopFaceIndex();
                console.log('🎲 주사위 눈:', result);
                if (!hasSentResult) {
                    sendResultToServer(result);
                    hasSentResult = true;
                }
            }
        } else {
            stoppedFrameCount = 0;
        }

        // 씬 렌더링
        renderer.render(scene, camera);
    }

    // -----------------------------
    // 함수: 창 크기 변경 처리
    // -----------------------------
    function onResize(): void {
        const width = container.clientWidth;
        const height = container.clientHeight;
        camera.aspect = width / height;
        camera.updateProjectionMatrix();
        renderer.setSize(width, height);
        renderer.setPixelRatio(window.devicePixelRatio || 1);
    }

    // -----------------------------
    // Svelte onMount: 컴포넌트 초기화
    // -----------------------------
    onMount(() => {
        initThree();
        initPhysics();
        rollDice();
        animate(0);
        window.addEventListener('resize', onResize);
        return () => {
            window.removeEventListener('resize', onResize);
            renderer.dispose(); // WebGL 리소스 정리
        };
    });
</script>

<style>
    /* 전체 문서 기본 스타일 초기화 */
    html, body {
        margin: 0;
        padding: 0;
        height: 100%;
        overflow: hidden;
        background: #000;
    }

    /* 컨테이너 설정: 화면 전체, 중앙 정렬 */
    .container {
        width: 100vw;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
        position: relative;
        background: #000;
    }

    /* 캔버스 블록 레벨 요소로 표시 */
    canvas {
        display: block;
        max-width: 100%;
        max-height: 100%;
        user-select: none;
    }

    /* 주사위 굴리기 버튼 위치 및 스타일 */
    button {
        position: absolute;
        top: 20px;
        left: 20px;
        padding: 10px 20px;
        font-size: 16px;
        z-index: 10;
        cursor: pointer;
    }

    /* 결과 표시박스 스타일 */
    .result {
        position: absolute;
        top: 20px;
        right: 20px;
        font-size: 24px;
        background: rgba(0, 0, 0, 0.7);
        color: white;
        padding: 10px 16px;
        border-radius: 8px;
        z-index: 10;
    }
</style>

<!-- 렌더링되는 HTML 구조 -->
<div class="container" bind:this={container}>
    <!-- 주사위 굴리기 버튼 클릭 시 rollDice 호출 -->
    <button on:click={rollDice}>🎲 주사위 굴리기</button>
    <!-- 결과가 있으면 화면에 표시 -->
    {#if result > 0}
        <div class="result">결과: {result}</div>
    {/if}
</div>
