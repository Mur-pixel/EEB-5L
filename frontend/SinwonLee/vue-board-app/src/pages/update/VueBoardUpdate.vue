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
              <v-btn color="primary" @click="onUpdate">수정완료</v-btn>
            </v-col>
            <v-col cols="auto">
              <router-link :to="{ name: 'VueBoardList' }">
                <v-btn color="secondary">돌아가기</v-btn>
              </router-link>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from "../../stores/boardStore.ts";

const boardStore = useBoardStore()
const route = useRoute()
const router = useRouter()

const boardId = Number(route.params.boardId)
const board = ref(boardStore.board)

const title = ref('')
const content = ref('')

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
