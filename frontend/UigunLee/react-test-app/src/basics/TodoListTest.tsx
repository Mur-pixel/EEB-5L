import React, {useState} from "react";

type Task = {
    id: number,
    text: string
}

const TodoListTest = () => {
    const [taskList, setTaskList] = useState<Task[]>([])
    const [userInput, setUserInput] = useState("")

    const handleAddTask = () => {
        if (userInput.trim() === "") return

        const newTask: Task = { id: Date.now(), text: userInput.trim() }
        setTaskList([...taskList, newTask])
        setUserInput("")
    }

    const handleDeleteTask = (id: number) => {
        setTaskList(
            taskList.filter((task) => task.id !== id)
        )
    }

    return (
        // Background 배경
        // min-h-screen <- 화면 높이를 100vh로 설정 (즉 세로 높이를 가득 채우겠다는 뜻)
        // flex <- 내부를 flex 하게 유연하게 UI를 배치 할 수 있게 구성
        // bg-gradient-to-br <- 배경을 위에서 아래로 그라데이션
        // from-blue-50 to-purple-100 <- 적정한 값으로 그라데이션 시작 ~ 끝
        // items-center <- 수직 정렬: 중앙
        // justify-center <- 수평 정렬: 중앙
        // px-4 <- 좌우 패딩 (16px) [x-4의 경우 * 4]
        <div className="min-h-screen flex bg-gradient-to-br from-blue-50 to-purple-100 items-center justify-center px-4">

            {/*
                배경에 박혀 있는 카드
                bg-white <- 흰색 배경
                shadow-2xl <- 그림자 효과를 진하게
                rounded-3xl <- 모서리 라운딩
                w-full <- 가로폭 100% (parent 요소 만큼)
                max-w-xl <- 최대 너비 576px
                p-8 <- 전방향 패딩 32px
            */}
            <div className="bg-white shadow-2xl rounded-3xl w-full max-w-xl p-8">
                <div
                    style={{
                        display: "flex",
                        justifyContent: 'center',  // 가로 중앙 정렬
                        alignItems: 'center',      // 세로 중앙 정렬
                        flexDirection: 'column'    // 세로 방향 정렬
                    }}>
                    <h1 className="text-4xl font-serif font-bold mb-6  text-blue-900">🐋 Todo List</h1>
                </div>

                {/*
                    flex <- 가로 정렬 (input 과 button을 나란히)
                    items-center <- 수직 정렬
                    gap-2 <- input과 button 사이의 간격을 8px
                    mb-4 <- 아래 방향 마진 16px
                */}
                <div className="flex items-center gap-2 mb-4">

                    {/*
                        flex-grow <- 어떤 공간 크기가 지정되어 있고 남는 부분이 있으면 전부 채움
                        px-3 <- 좌우 12px 패딩
                        py-1 <- 상하 4px 패딩
                        border-2 border-gray-300 <- 테두리 두께 2px, 회색
                        rounded-lg <- 모서리 약간 둥글게
                        focus:outline-none <- 커서를 놓으면 focus가 되고 외곽선을 제거
                        focus:ring-2 focus:ring-blue-500 <- 커서를 놓으면 파란색 glow 효과 (테두리 강조)
                    */}
                    <input
                        type="text"
                        value={userInput}
                        onChange={(event) => setUserInput(event.target.value)}
                        placeholder="내용을 입력하세요."
                        className="flex-1 px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />

                    {/*
                        shrink-0 <- 버튼이 줄어들지 않도록 방지
                        bg-blue-600 <- 조금 진한 파란색
                        text-white <- 글자 색상 흰색
                        py-2 <- 상하 패딩 8px
                        rounded-lg <- 약간 둥글게
                        hover:bg-blue-700 <- 커서가 위쪽 배치 (hover) 되면 진한 파란색 처리
                        active:scale-95 <- 클릭 시 약간 축소
                        transition <- hover / active 효과 부드럽게 처리하도록 지원
                    */}
                    <button
                        onClick={handleAddTask}
                        className="bg-blue-500 text-white px-4 py-2 text-lg rounded-md hover:bg-blue-600 transition"
                    >
                        추가
                    </button>
                </div>

                {/*
                    space-y-2 <- 추가된 항목들 사이의 간격 8px
                */}
                <ul className="space-y-2">
                    {taskList.map((task) => (
                        // flex justify-between <- 내용과 삭제 버튼을 좌우로 배치
                        // items-center <- 수직 정렬
                        // bg-gray-50 <- 매우 밝은 회색
                        // border <- 기본 테두리 1px
                        // rounded-lg <- 약간 둥글게
                        // px-4 <- 좌우 16px 패딩
                        // py-2 <- 상하 8px 패딩
                        // 약한 그림자 효과
                        <li
                            key={task.id}
                            className="flex justify-between items-center bg-gray-50 border rounded-lg px-4 py-2"
                        >
                            <span>{task.text}</span>
                            {/*
                                text-red-500 <- 기본 빨간색
                                hover:text-red-700 <- hover 시 진한 빨간색
                                transition <- 색상 변화를 부드럽게 전환하도록 지원
                            */}
                            <button
                                onClick={() => handleDeleteTask(task.id)}
                                className="text-red-400 hover:text-red-700 transition"
                            >
                                삭제
                            </button>
                        </li>
                    ))}
                </ul>

                {/*
                    text-center <- 가운데 정렬
                    text-gray-400 <- 흐린 회색
                    mt-4 <- 위쪽 여백 (16px)
                */}
                {taskList.length == 0 && (
                    <p className="text-center font-bold text-rose-500 mt-2 text-base">
                        오늘은 할 일이 없습니다.
                    </p>
                )}
            </div>
        </div>
    )
}

export default TodoListTest