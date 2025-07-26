export const SecondProblemVersionThree = () => {
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

    let profanityCountList = []
    for (let i = 0; i < abuseReportList.length; i++) {
        if (abuseReportList[i].type === "profanity") {
            if (abuseReportList[i].userId === "user1") {
                profanityCountList[0] = (profanityCountList[0] || 0) + 1
            } else if (abuseReportList[i].userId === "user2") {
                profanityCountList[1] = (profanityCountList[1] || 0) + 1
            } else if (abuseReportList[i].userId === "user3") {
                profanityCountList[2] = (profanityCountList[2] || 0) + 1
            } else if (abuseReportList[i].userId === "user4") {
                profanityCountList[3] = (profanityCountList[3] || 0) + 1
            }
        }
    }

    let punishmentList = []
    for (let i = 0; i < profanityCountList.length; i++) {
        if (profanityCountList[i] >= 7) {
            punishmentList[i] = "영구 정지"
        } else if (profanityCountList[i] >= 5) {
            punishmentList[i] = "1달 정지"
        } else if (profanityCountList[i] >= 3) {
            punishmentList[i] = "1주 정지"
        }
    }

    const userIds = ["user1", "user2", "user3"]

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Quiz2-2번 문제 버전 3</h3>
            <ul>
                { userIds.map((userId, index) => (
                    <li key={userId}>
                        {userId} 는 {punishmentList[index]} 를 부여받았습니다.
                    </li>
                ))}
            </ul>
        </div>
    )
}