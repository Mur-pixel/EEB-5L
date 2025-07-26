<template>
    <v-container>
        <h2>Vue3 + TypeScript + Vuetify3 + Spring + JPA</h2>
        <v-card v-if="board">
            <v-card-title>게시물 정보</v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field v-model="board.title" readonly label="제목" />
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field v-model="board.nickname" readonly label="작성자" />
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-textarea v-model="board.content" readonly label="내용" />
                        </v-col>
                    </v-row>
                    <v-row justify="end">
                        <v-col cols="auto">
                            <router-link :to="{ name: 'VueBoardUpdate', params: { boardId } }">
                                <v-btn
                                    @mouseover="hoverUpdate = true"
                                    @mouseleave="hoverUpdate = false"
                                    :style="updateBtnStyle"
                                >
                                    수정하기
                                </v-btn>
                            </router-link>
                        </v-col>
                        <v-col cols="auto">
                            <v-btn
                                @mouseover="hoverDelete = true"
                                @mouseleave="hoverDelete = false"
                                :style="deleteBtnStyle"
                                @click="onDelete"
                            >
                                삭제
                            </v-btn>
                        </v-col>
                        <v-col cols="auto">
                            <router-link :to="{ name: 'VueBoardList' }">
                                <v-btn
                                    @mouseover="hoverBack = true"
                                    @mouseleave="hoverBack = false"
                                    :style="backBtnStyle"
                                >
                                    돌아가기
                                </v-btn>
                            </router-link>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from '../../stores/boardStore.ts'

const route = useRoute()
const router = useRouter()
const boardStore = useBoardStore()

const boardId = Number(route.params.boardId)
const board = computed(() => boardStore.board)

onMounted(() => {
    boardStore.requestBoardToSpring(boardId)
})

const onDelete = async () => {
    await boardStore.requestDeleteBoardToSpring(boardId)
    await router.push({ name: 'VueBoardList' })
}

// 버튼 hover 상태
const hoverUpdate = ref(false)
const hoverDelete = ref(false)
const hoverBack = ref(false)

// 버튼 스타일
const updateBtnStyle = computed(() => ({
    backgroundColor: hoverUpdate.value ? '#1565c0' : '#1976d2', // 파란색
    color: 'white',
    transition: 'background-color 0.3s ease',
}))

const deleteBtnStyle = computed(() => ({
    backgroundColor: hoverDelete.value ? '#b71c1c' : '#d32f2f', // 빨간색
    color: 'white',
    transition: 'background-color 0.3s ease',
}))

const backBtnStyle = computed(() => ({
    backgroundColor: hoverBack.value ? '#00796b' : '#009688', // 청록색
    color: 'white',
    transition: 'background-color 0.3s ease',
}))
</script>
