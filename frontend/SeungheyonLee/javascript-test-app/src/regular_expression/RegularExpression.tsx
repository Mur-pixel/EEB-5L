export const RegularExpression = () => {
    let text = "Hello 123 World 456!"
    let findNumberRegex = /\d+/g;
    let matches = text.match(findNumberRegex) || []
    let steps = matches.map((match, index) => `매칭된 숫자 ${index + 1}: ${match}`)

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Regular Expression Test</h3>
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
                    let findNumberRegex = /\\d+/g;
                    let matches = text.match(findNumberRegex)
                `}
            </pre>
        </div>
    )
}