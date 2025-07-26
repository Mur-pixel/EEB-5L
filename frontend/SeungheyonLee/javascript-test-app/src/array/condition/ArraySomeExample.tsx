export const ArraySomeExample = () => {
    let numbers: number[] = [11, 22, 33, 44, 55]
    let isItOdd = numbers.some(num => num % 2 == 1)

    let numbers2: number[] = [10, 20, 30, 40, 50]
    let isItOdd2 = numbers2.some(num => num % 2 == 1)

    let steps = [
        `초개 배열: [${numbers.join(", ")}]`,
        `numbers.some(num => num % 2 == 1) 결과: ${isItOdd}`,
        `초개 배열2: [${numbers2.join(", ")}]`,
        `numbers2.some(num => num % 2 == 1) 결과: ${isItOdd2}`
    ]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>배열 some(조건) 사용법</h3>
            <ul>
                { steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    let numbers: number[] = [11, 22, 33, 44, 55]
                    let isItOdd = numbers.some(num => num % 2 == 1)

                    let numbers2: number[] = [10, 20, 30, 40, 50]
                    let isItOdd2 = numbers2.some(num => num % 2 == 1)
                `}
            </pre>
        </div>
    )
}