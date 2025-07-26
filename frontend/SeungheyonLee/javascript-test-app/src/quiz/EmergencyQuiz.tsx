export const EmergencyQuiz = () => {
    let appleCount = 100
    let applePrice = 10000
    let userWannaBuyCount = 5

    const LOOP_MIN = 2
    const LOOP_MAX = 3

    let queue = [];
    let loopCount = Math.floor(Math.random() * (LOOP_MAX - LOOP_MIN + 1)) + LOOP_MIN

    for (let i = 0; i < loopCount; i++) {
        const randomNumber = Math.floor(Math.random() * (LOOP_MAX - LOOP_MIN + 1)) + LOOP_MIN
        queue.push(randomNumber)
    }

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <p>현재 사과의 개수: {appleCount}</p>
            <p>악덕 과일 가게 사장의 사과 가격: {applePrice}</p>
            <p>사용자가 구매하기 원하는 사과 개수: {userWannaBuyCount}</p>
            <p>랜덤 루프 몇 개냐? {loopCount}</p>
            <p>queue: {queue.join(', ')}</p>
            <pre>
                {`
                    E1.
                    여러분이 과일 가게 사장입니다.
                    물건을 온라인으로 판매하려고 하는데 어떻게 해야 할까요?
                    
                    E2. 큐를 시뮬레이션 해봅시다.
                `}
            </pre>
        </div>
    )
}