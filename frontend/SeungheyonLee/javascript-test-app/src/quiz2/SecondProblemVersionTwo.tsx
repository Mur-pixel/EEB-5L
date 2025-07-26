const getPunishment = (count): string | null => {
    if (count >= 7) return "영구 정지"
    if (count >= 5) return "1달 정지"
    if (count >= 3) return "1주 정지"

    return null
}

export const SecondProblemVersionTwo = () => {
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

    const punishmentsMap = Object.values(abuseReportList.reduce((localMap, { userId }) => {
        const count = (localMap[userId]?.count || 0) + 1

        localMap[userId] = { userId, count, punishment: getPunishment(count) }

        return localMap
    }, {} as Record<string, { userId: string; count: number, punishment: string }>))

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Quiz2-2번 문제 버전 2</h3>
            { punishmentsMap.map(({ userId, count, punishment }, index) => (
                <li key={index}>
                    { punishment
                        ? `${userId} 에게 ${punishment} 를 부여합니다.`
                        : `${userId} 는 아무런 제재를 받지 않았습니다.` }
                </li>
            ))}
        </div>
    )
}