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
                <v-btn color="primary">수정하기</v-btn>
              </router-link>
            </v-col>
            <v-col cols="auto">
              <v-btn color="error" @click="onDelete">삭제</v-btn>
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

<script setup lang="ts">
import { onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from "../../stores/boardStore.ts";

const route = useRoute()
const router = useRouter()
const boardStore = useBoardStore()

const boardId = Number(route.params.boardId)

const board = computed(() => boardStore.board)

const onDelete = async () => {
  await boardStore.requestDeleteBoardToSpring(boardId)
  await router.push({ name: 'VueBoardList' })
}

onMounted(() => {
  boardStore.requestBoardToSpring(boardId)
})
</script>
