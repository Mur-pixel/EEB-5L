<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-text-field v-model="title" label="제목" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-textarea v-model="content" label="내용" auto-grow />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="text-right">
        <v-btn color="primary" @click="onSubmit">작성 완료</v-btn>
        <v-btn color="error" @click="onCancel">취소</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useBoardStore} from "../../stores/boardStore.ts";

const router = useRouter()
const boardStore = useBoardStore()

const title = ref('')
const content = ref('')

async function onSubmit() {
  try {
    const payload = {
      title: title.value,
      content: content.value
    }
    const createdBoard = await boardStore.requestCreateBoardToSpring(payload)

    await router.push({
      name: 'VueBoardRead',
      params: { boardId: createdBoard.boardId.toString() }
    })
  } catch (error) {
    alert('게시물 작성 중 오류가 발생했습니다.')
    console.error(error)
  }
}

function onCancel() {
  router.go(-1)
}
</script>
