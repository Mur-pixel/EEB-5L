export const MapExample = () => {
    let numbers: number[] = [1, 2, 3, 4, 5]
    let results: number[] = numbers.map(num => num * 2)
    let steps: string[] = numbers.map((num, index) =>
        `num = ${num} -> 결과 = ${results[index]}`)

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Map 사용법</h3>
            <p>결과:</p>
            <ul>
                { steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    let results = numbers.map(num => num * 2)
                `}
            </pre>
        </div>
    )
}