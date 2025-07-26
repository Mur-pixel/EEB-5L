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
        <v-btn
            :class="['btn-primary']"
            @mouseover="hoverPrimary = true"
            @mouseleave="hoverPrimary = false"
            :style="primaryBtnStyle"
            @click="onSubmit"
        >
          작성 완료
        </v-btn>

        <v-btn
            :class="['btn-error']"
            @mouseover="hoverError = true"
            @mouseleave="hoverError = false"
            :style="errorBtnStyle"
            @click="onCancel"
        >
          취소
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useBoardStore } from "../../stores/boardStore.ts";

const router = useRouter()
const boardStore = useBoardStore()

const title = ref('')
const content = ref('')

const hoverPrimary = ref(false)
const hoverError = ref(false)

const primaryBtnStyle = computed(() => ({
  backgroundColor: hoverPrimary.value ? '#1565c0' : '#1976d2',
  color: 'white',
  transition: 'background-color 0.3s ease',
}))

const errorBtnStyle = computed(() => ({
  backgroundColor: hoverError.value ? '#b71c1c' : '#d32f2f',
  color: 'white',
  transition: 'background-color 0.3s ease',
}))

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
