export const ArrayIndexOfExample = () => {
    let numbers: number[] = [10, 20, 30, 40]
    let index = numbers.indexOf(30)

    let steps = [
        `초개 배열: [${numbers.join(", ")}]`,
        `indexOf(30) 결과: ${index}`
    ]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>배열 indexOf(값) 사용법</h3>
            <ul>
                { steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    let numbers: number[] = [10, 20, 30, 40]
                    let index = numbers.indexOf(30)
                `}
            </pre>
        </div>
    )
}