export interface Board {
    boardId: number
    title: string
    nickname: string
    content: string
    createDate: string
    updateDate: string
}

export interface BoardState {
    boardList: Board[]
    board: Board | null
}

export const boardState = () => ({
    boardList: [],
    board: null,
})
