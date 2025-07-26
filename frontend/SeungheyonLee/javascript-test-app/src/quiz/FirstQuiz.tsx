function getRandomInt(min: number, max: number) {
    return Math.floor(Math.random() * ( max - min + 1)) + min
}

function randomEnqueue() {
    const LOOP_MIN = 1
    const LOOP_MAX = 2

    const MAX_NUMBER = 5
    const MIN_NUMBER = 1

    const generatedEnqueueNumber = []

    const loop = getRandomInt(LOOP_MIN, LOOP_MAX)

    for (let i = 0; i < loop; i++) {
        generatedEnqueueNumber.push(getRandomInt(MIN_NUMBER, MAX_NUMBER))
    }

    return generatedEnqueueNumber
}

function randomDequeue(currentQueue: number[]) {
    const LOOP_MIN = 1
    const LOOP_MAX = 2

    const loop = getRandomInt(LOOP_MIN, LOOP_MAX)

    for (let i = 0; i < loop; i++) {
        if (currentQueue.length === 0) break;

        currentQueue.pop();
    }
}

export const FirstQuiz = () => {
    const MAX_LOOP = 7

    const DEQUEUE = 0
    const ENQUEUE = 1

    let currentQueue: number[] = []

    for (let i = 0; i < MAX_LOOP; i++) {
        const checkEnqueueNumber = getRandomInt(DEQUEUE, ENQUEUE)
        if (checkEnqueueNumber == ENQUEUE) {
            currentQueue.push(...randomEnqueue())
        }

        if (checkEnqueueNumber == DEQUEUE) {
            randomDequeue(currentQueue)
        }
    }

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>1번 문제 최종 결과</h3>
            <p>currentQueue: {currentQueue.join(', ')}</p>
            <pre>
                {`
                    1. 우선 Queue를 시뮬레이션 하려면 입력의 개수, 
                       출력의 개수를 지정해야 합니다.
                    2. 그러므로 랜덤으로 입력 / 출력 둘 중 하나를 선택하게 만듭니다.
                    3. 선택 했다면 그 다음은 랜덤으로 몇 개를 넣을 것인지 선택하게 만듭니다. 
                       (이것의 개수를 적절하게 지정해야 확인이 수월할 것입니다)
                    4. 적정 숫자가 설정되면 종료되도록 만들어서 
                       전체 흐름이 올바르게 동작 했는지 검증하게 만듭니다.
                `}
            </pre>
        </div>
    )
}