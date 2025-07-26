<template>
  <v-container
      fluid
      class="pa-4"
      style="height: calc(100vh - 64px); overflow-y: auto;"
  >
    <v-row>
      <v-col cols="12">
        <h2>📄 게임칩 정보</h2>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-text-field :model-value="title" label="제목" readonly />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-text-field :model-value="price" label="가격" readonly />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-textarea :model-value="description" label="설명" readonly auto-grow />
      </v-col>
    </v-row>

    <v-row v-if="thumbnailUrl">
      <v-col cols="12">
        <h4>썸네일</h4>
        <v-img :src="thumbnailUrl" height="150" contain />
      </v-col>
    </v-row>

    <v-row v-if="detailImageUrls.length > 0">
      <v-col cols="12">
        <h4>상세 이미지</h4>
        <v-row>
          <v-col
              v-for="(url, index) in detailImageUrls"
              :key="url + '-' + index"
              cols="3"
          >
            <v-img :src="url" aspect-ratio="1" contain />
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <!-- 버튼 그룹 -->
    <v-row justify="end" class="mt-4">
      <v-col cols="auto">
        <v-btn
            @mouseover="hoverEdit = true"
            @mouseleave="hoverEdit = false"
            :style="editBtnStyle"
            @click="onEdit"
        >
          수정
        </v-btn>
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
        <v-btn
            outlined
            @mouseover="hoverBack = true"
            @mouseleave="hoverBack = false"
            :style="backBtnStyle"
            @click="goBack"
        >
          뒤로가기
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useGameChipStore } from '../../stores/gameChipStore'

const route = useRoute()
const router = useRouter()
const gameChipStore = useGameChipStore()

const title = ref('')
const description = ref('')
const price = ref(0)
const thumbnailUrl = ref('')
const detailImageUrls = ref<string[]>([])
const gameChipId = ref<number | null>(null)

// 버튼 hover 상태
const hoverEdit = ref(false)
const hoverDelete = ref(false)
const hoverBack = ref(false)

// 버튼 스타일 computed
const editBtnStyle = computed(() => ({
  backgroundColor: hoverEdit.value ? '#1565c0' : '#1976d2',
  color: 'white',
  transition: 'background-color 0.3s ease',
}))

const deleteBtnStyle = computed(() => ({
  backgroundColor: hoverDelete.value ? '#b71c1c' : '#d32f2f',
  color: 'white',
  transition: 'background-color 0.3s ease',
}))

const backBtnStyle = computed(() => ({
  backgroundColor: hoverBack.value ? '#00796b' : '#009688',
  color: 'white',
  transition: 'background-color 0.3s ease',
}))

onMounted(async () => {
  const id = Number(route.params.id)
  if (isNaN(id)) {
    alert('잘못된 요청입니다.')
    router.push({ name: 'GameChipList' })
    return
  }
  gameChipId.value = id

  try {
    await gameChipStore.requestGameChipToSpring(id)
    const data = gameChipStore.gameChip
    console.log('read game-chip data:', data)
    title.value = data.title
    description.value = data.description
    price.value = data.price

    if (data.thumbnailImageBase64) {
      thumbnailUrl.value = `data:image/*;base64,${data.thumbnailImageBase64}`
    }

    if (Array.isArray(data.detailImageBase64List)) {
      detailImageUrls.value = data.detailImageBase64List.map(base64 =>
          `data:image/*;base64,${base64}`
      )
    }
  } catch (e) {
    alert('조회 실패')
    console.error(e)
    router.push({ name: 'GameChipList' })
  }
})

// 수정 버튼 클릭 시
function onEdit() {
  if (!gameChipId.value) return
  router.push({ name: 'GameChipUpdate', params: { id: gameChipId.value } })
}

// 삭제 버튼 클릭 시
async function onDelete() {
  if (!gameChipId.value) return
  if (!confirm('정말 삭제하시겠습니까?')) return

  try {
    await gameChipStore.requestDeleteGameChipToSpring(gameChipId.value)
    alert('삭제 완료')
    router.push({ name: 'GameChipList' })
  } catch (e) {
    alert('삭제 실패')
    console.error(e)
  }
}

// 뒤로가기
function goBack() {
  router.push({ name: 'GameChipList' })
}
</script>
