export const ArraySortExample = () => {
    let numbers: number[] = [3, 1, 4, 1, 5, 9, 7]
    let sorted = [...numbers].sort((a, b) => a - b)
    let sortedReverse = [...numbers].sort((a, b) => b - a)

    let steps = [
        `초개 배열: [${numbers.join(", ")}]`,
        `sort((a, b) => a - b) 결과: [${sorted.join(", ")}]`,
        `sort((a, b) => b - a) 결과: [${sortedReverse.join(", ")}]`,
    ]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>배열 sort((a, b) =&gt; 정렬 순서) 사용법</h3>
            <p>a - b 는 작은 수 -&gt; 큰 수</p>
            <p>b - a 는 큰 수 -&gt; 작은 수</p>
            <ul>
                { steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    let numbers: number[] = [3, 1, 4, 1, 5, 9, 7]
                    let sorted = [...numbers].sort((a, b) => a - b)
                    let sortedReverse = [...numbers].sort((a, b) => b - a)
                `}
            </pre>
        </div>
    )
}