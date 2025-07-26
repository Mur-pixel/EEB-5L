export const ControlFlowForEach = () => {
    let numbers = [1, 2, 3]
    let results = []
    let steps: string[] = []

    numbers.forEach(num => {
        results.push(num * 2);
        steps.push(`num = ${num} 일 때 results = [${[...results].join(", ")}]`)
    })

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>forEach 문</h3>
            <p>결과:</p>
            <ul>
                {steps.map((step, index) => (
                    <li key={index}>
                        {step}
                    </li>
                ))}
            </ul>
            <pre>
                {`
                    numbers.forEach(num => {
                        results.push(num * 2)
                    });
                `}
            </pre>
        </div>
    )
}