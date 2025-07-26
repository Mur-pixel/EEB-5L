<script lang="ts">
  // Dice 컴포넌트를 가져옴.
  import Dice from './components/Dice.svelte';

  // 게임 화면 표시 여부를 관리함.
  let showGame = false;
  // 현재 게임의 ID를 저장함.
  let gameId: number | null = null;

  // 게임을 시작하는 비동기 함수.
  async function startGame() {
    try {
      // 로컬 스토리지에서 사용자 토큰을 가져옴.
      const userToken = localStorage.getItem('userToken');

      // 토큰이 없으면 로그인 필요 알림 후 종료함.
      if (!userToken) {
        alert('로그인이 필요합니다.');
        return;
      }

      // 게임 시작 API에 GET 요청을 보냄.
      const response = await fetch(`${import.meta.env.VITE_API_URL}/game/start`, {
        method: 'GET',
        headers: {
          // Bearer 인증 헤더를 설정함.
          //이 뒤에 오는 문자열이 액세스 토큰(Access Token)”임을 나타내는 인증 스키마
          'Authorization': `Bearer ${userToken}`,
          'Content-Type': 'application/json',
        },
      });

      // 서버 응답이 실패면 에러를 던짐.
      if (!response.ok) {
        throw new Error(`서버 오류: ${response.status}`);
      }

      // 응답 본문에서 게임 ID를 파싱함.
      //response.json()은 Fetch API의 Response 객체가 가진 메서드로,
      //서버가 보낸 응답 본문(body) 을 JSON 문자열에서 JavaScript 값(객체, 배열, 숫자 등) 으로 파싱
      gameId = await response.json();
      console.log('게임 ID:', gameId);

      // 게임 화면을 표시하도록 플래그를 활성화함.
      showGame = true;
    } catch (error) {
      // 에러 발생 시 콘솔에 로그를 남기고 사용자에게 알림함.
      console.error('게임 시작 중 오류 발생:', error);
      alert('게임 시작에 실패했습니다.');
    }
  }

  // 홈 버튼 클릭 시 게임 화면을 초기 상태로 되돌림.
  function goHome() {
    showGame = false;
    gameId = null;
  }
</script>

<!-- 게임 화면이 활성화된 상태라면 홈 버튼과 Dice 컴포넌트를 렌더링함. -->
{#if showGame && gameId}
  <button on:click={goHome} style="position: absolute; top: 20px; right: 20px; z-index: 10;">
    🏠 홈으로
  </button>
  <!-- Dice 컴포넌트에 gameId를 전달함. -->
  <Dice {gameId} />
{:else}
  <!-- 게임이 시작되지 않은 초기 메인 화면을 렌더링함. -->
  <main style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100vh;">
    <h1>🎲 주사위 게임에 오신 것을 환영합니다</h1>
    <!-- 게임 시작 버튼 클릭 시 startGame 호출함. -->
    <button on:click={startGame} style="padding: 12px 24px; font-size: 18px; cursor: pointer;">
      게임 시작
    </button>
  </main>
{/if}
