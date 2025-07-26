export const ThirdProblem = () => {
    const abuseReportList = [
        { userId: "user1", type: "profanity", date: "2025-06-01" },
        { userId: "user1", type: "profanity", date: "2025-06-03" },
        { userId: "user1", type: "profanity", date: "2025-06-05" },

        { userId: "user2", type: "profanity", date: "2025-06-01" },
        { userId: "user2", type: "profanity", date: "2025-06-04" },
        { userId: "user2", type: "profanity", date: "2025-06-06" },
        { userId: "user2", type: "profanity", date: "2025-06-07" },
        { userId: "user2", type: "profanity", date: "2025-06-08" },

        { userId: "user3", type: "profanity", date: "2025-06-02" },
        { userId: "user3", type: "profanity", date: "2025-06-04" },
        { userId: "user3", type: "profanity", date: "2025-06-06" },
        { userId: "user3", type: "profanity", date: "2025-06-07" },
        { userId: "user3", type: "profanity", date: "2025-06-08" },
        { userId: "user3", type: "profanity", date: "2025-06-09" },
        { userId: "user3", type: "profanity", date: "2025-06-10" },

        { userId: "user4", type: "profanity", date: "2025-06-06" },
    ];

    const dailyProfanityCountsHashMap = abuseReportList.reduce((localMap, abuseReport) => {
        if (abuseReport.type !== "profanity") return localMap

        localMap[abuseReport.date] = (localMap[abuseReport.date] || 0) + 1

        return localMap
    }, {} as Record<string, number>)

    const sortedDate = Object.keys(dailyProfanityCountsHashMap).sort()

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Quiz2-3번 문제</h3>
            { sortedDate.map((date) => (
                <li key={date}>
                    {date}: {dailyProfanityCountsHashMap[date]} 건의 사고 발생
                </li>
            ))}
        </div>
    )
}