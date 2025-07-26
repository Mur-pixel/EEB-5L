import {gameChipActions} from "./gameChipActions.ts";

export interface GameChip {
    id: number
    title: string
    description: string
    price: number
    imageUrl: string
    nickname: string
}

export interface GameChipState {
    gameChipList: GameChip[]
    gameChip: GameChip | null
    totalItems: number
}

export const gameChipState = () => ({
    gameChipList: [],
    gameChip: null,
    totalItems: 0,
    totalPages: 1,
})
