export const TypeCoercion = () => {
    const stringConcat = (5 as any) + "10"
    const numericConversion = Number("5") - 2
    const booleanConversion = Boolean("hello")

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>타입 변환</h3>
            <pre>
                {`
                    console.log(5 + "10");              // ${stringConcat}
                    console.log(Number("5") - 2);       // ${numericConversion}
                    console.log(Boolean("hello"));      // ${booleanConversion}
                `}
            </pre>
        </div>
    )
}