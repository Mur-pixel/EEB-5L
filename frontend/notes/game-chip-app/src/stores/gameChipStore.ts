import { defineStore } from 'pinia'
import {gameChipState} from "./gameChipState.ts";
import {gameChipActions} from "./gameChipActions.ts";

export const useGameChipStore = defineStore('gameChipStore', {
    state: gameChipState,
    actions: gameChipActions,
})
