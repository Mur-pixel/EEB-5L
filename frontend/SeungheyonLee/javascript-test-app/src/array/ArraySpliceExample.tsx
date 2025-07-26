export const ArraySpliceExample = () => {
    let numbers: number[] = [10, 20, 30, 40, 50]
    let spliced = [...numbers]
    spliced.splice(2, 1, 99)

    let steps = [
        `초개 배열: [${numbers.join(", ")}]`,
        `splice(2, 1, 99) 결과: [${spliced.join(", ")}]`
    ]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>배열 splice(start, deleteCount, ...items) 사용법</h3>
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
                    let spliced = [...numbers]
                    spliced.splice(2, 1, 99)
                `}
            </pre>
        </div>
    )
}