export const ArraySliceExample = () => {
    let numbers: number[] = [10, 20, 30, 40, 50]
    let sliced = numbers.slice(1, 4)

    let steps = [
        `초개 배열: [${numbers.join(", ")}]`,
        `slice(1, 4) 결과: [${sliced.join(", ")}]`
    ]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>배열 slice(start, end) 사용법</h3>
            <ul>
                { steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    let numbers: number[] = [10, 20, 30, 40, 50]
                    let sliced = numbers.slice(1, 4)
                `}
            </pre>
        </div>
    )
}