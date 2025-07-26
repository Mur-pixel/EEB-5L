<template>
    <v-container>
        <h2>Vue3 + TypeScript + Vuetify3 + Spring + JPA</h2>
        <v-card v-if="board">
            <v-card-title>게시물 수정</v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field v-model="title" label="제목" />
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field v-model="board.nickname" readonly label="작성자" />
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field v-model="board.createDate" readonly label="등록일자" />
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-textarea v-model="content" label="내용" />
                        </v-col>
                    </v-row>
                    <v-row justify="end">
                        <v-col cols="auto">
                            <v-btn
                                    @mouseover="hoverUpdate = true"
                                    @mouseleave="hoverUpdate = false"
                                    :style="updateBtnStyle"
                                    @click="onUpdate"
                            >
                                수정완료
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

<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from "../../stores/boardStore.ts"

const boardStore = useBoardStore()
const route = useRoute()
const router = useRouter()

const boardId = Number(route.params.boardId)
const board = ref(boardStore.board)

const title = ref('')
const content = ref('')

// hover 상태
const hoverUpdate = ref(false)
const hoverBack = ref(false)

// 버튼 스타일
const updateBtnStyle = computed(() => ({
    backgroundColor: hoverUpdate.value ? '#1565c0' : '#1976d2', // 파란색
    color: 'white',
    transition: 'background-color 0.3s ease',
}))

const backBtnStyle = computed(() => ({
    backgroundColor: hoverBack.value ? '#00796b' : '#009688', // 청록색
    color: 'white',
    transition: 'background-color 0.3s ease',
}))

const onUpdate = async () => {
    await boardStore.requestUpdateBoardToSpring({
        boardId,
        title: title.value,
        content: content.value,
    })

    router.push({
        name: 'VueBoardRead',
        params: { boardId },
    })
}

onMounted(async () => {
    await boardStore.requestBoardToSpring(boardId)

    if (boardStore.board) {
        board.value = boardStore.board
        title.value = board.value.title
        content.value = board.value.content
    }
})
</script>
