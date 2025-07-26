export const ArrayFlatExample = () => {
    let nestedArray = [[1, 2], [3, 4]]
    let flattened = nestedArray.flat()

    let steps = [
        `초기 배열: [${nestedArray.map(arr => `[${arr.join(", ")}]`).join(", ")}]`,
        `flat() 결과: [${flattened.join(", ")}]`
    ]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>다중 배열(행렬, 텐서) flat() 사용법</h3>
            <ul>
                { steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    let nestedArray = [[1, 2], [3, 4]]
                    let flattened = nestedArray.flat()
                `}
            </pre>
        </div>
    )
}