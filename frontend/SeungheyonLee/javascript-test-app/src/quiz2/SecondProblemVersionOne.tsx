export const SecondProblemVersionOne = () => {
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

    let profanityUserHashMap = {}
    for (let i = 0; i < abuseReportList.length; i++) {
        if (abuseReportList[i].type === "profanity") {
            profanityUserHashMap[abuseReportList[i].userId] =
                (profanityUserHashMap[abuseReportList[i].userId] || 0) + 1
        }
    }

    let precautionsHashMap = {}
    Object.entries(profanityUserHashMap).forEach(([key, value]) => {
        if (value >= 7) {
            precautionsHashMap[key] = "영구 정지"
        } else if (value >= 5) {
            precautionsHashMap[key] = "1달 정지"
        } else if (value >= 3) {
            precautionsHashMap[key] = "1주 정지"
        }
    })

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Quiz2-2번 문제 버전 1</h3>
            { Object.entries(precautionsHashMap).map(([key, value]) => (
                <li key={key}>
                    {key} 에게 {value} 를 부여합니다.
                </li>
            ))}
        </div>
    )
}