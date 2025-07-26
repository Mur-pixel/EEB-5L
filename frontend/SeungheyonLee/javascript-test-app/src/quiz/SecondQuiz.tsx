export const SecondQuiz = () => {
    const news = `Stocks surged on Monday after the U.S. and China agreed to roll back tariffs for a 90-day period.

The Dow Jones Industrial Average rose 1,160.72 points, or 2.81%, exiting correction territory. The S&P 500 climbed 3.26% and the Nasdaq Composite rose 4.35%, exiting bear market territory.

The tariffs President Donald Trump announced against China on April 2 are being cut by 24 percentage points for this temporary period while retaining the remaining ad valorem rate of 10% from that announcement, according to a joint statement.

China agreed to the same stipulations, adding that it will "adopt all necessary administrative measures to suspend or remove the non-tariff countermeasures taken against the United States since April 2," the announcement stated.

Under the deal, reciprocal tariffs for both countries would be reduced by 115%. The U.S. will temporarily lower its tariffs on Chinese goods from 145% to 30%, and China will reduce its levies on American products from 125% to 10%.

FOX Business Landon Mion contributed to this report`

    const sentences = news.split(/\.\s+/)
    const importantSentences = sentences.filter(sentence =>
        sentence.includes("percent") || sentence.includes("tariff") ||
        sentence.includes("rose") || sentence.includes("climbed")
    )

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            { importantSentences.map((sentence, index) => (
                <li key={index}>
                    {sentence.trim()}.
                </li>
            ))}
            <pre>
                {`
                    1. 우선 문장을 분리 할 수 있어야 합니다.
                    2. 문장 내에 주요 키워드가 존재하는지 파악합니다.
                    3. 전체 키워드에 해당하는 문장을 뽑아서 해당 문장만 출력합니다. 
                `}
            </pre>
        </div>
    )
}