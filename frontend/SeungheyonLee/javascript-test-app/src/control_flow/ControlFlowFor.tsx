export const ControlFlowFor = () => {
    let loopResults = []
    let steps = []
    for (let i = 0; i < 3; i++) {
        loopResults.push(i)
        steps.push(`i = ${i} 일 때 loopResults = [${[...loopResults].join(", ")}]`)
    }

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>for 문</h3>
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
                    for (let i = 0; i < 3; i++) {
                        loopResults.push(i)
                    }
                `}
            </pre>
        </div>
    )
}