<template>
  <v-container fluid class="pa-4">
    <v-row class="mb-4" align="center" justify="space-between">
      <v-col cols="auto">
        <h2>🎮 게임칩 목록</h2>
      </v-col>
      <v-col cols="auto">
        <router-link :to="{ name: 'GameChipRegister' }">
          <v-btn
              :class="['btn-primary']"
              @mouseover="hoverRegister = true"
              @mouseleave="hoverRegister = false"
              :style="registerBtnStyle"
              elevation="2"
          >
            게임칩 등록
          </v-btn>
        </router-link>
      </v-col>
    </v-row>

    <v-row v-if="loading">
      <v-col cols="12" class="text-center">
        <v-progress-circular indeterminate color="primary" />
      </v-col>
    </v-row>

    <v-row v-else-if="gameChipStore.gameChipList.length === 0">
      <v-col cols="12" class="text-center">
        등록된 게임칩이 없습니다.
      </v-col>
    </v-row>

    <v-row v-else>
      <v-col
          v-for="chip in pagedGameChipList"
          :key="chip.id"
          cols="3"
      >
        <v-card @click="goToDetail(chip.id)" class="hoverable" elevation="3">
          <v-img
              :src="`data:image/png;base64,${chip.thumbnailImageData}`"
              height="125px"
              cover
          />
          <v-card-title>{{ chip.title }}</v-card-title>
          <v-card-subtitle>₩ {{ chip.price.toLocaleString() }}</v-card-subtitle>
          <v-card-text>{{ chip.description }}</v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row class="mt-4" justify="center">
      <v-pagination
          v-model="page"
          :length="totalPages"
          @input="fetchGameChips"
          color="primary"
      />
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useGameChipStore } from '../../stores/gameChipStore'

const gameChipStore = useGameChipStore()
const router = useRouter()

const page = ref(1)
const perPage = 8  // 2행 x 4열 = 8개씩 보여줌
// const totalPages = ref(1)
const totalPages = computed(() => gameChipStore.totalPages)
const loading = ref(false)

// 호버 상태
const hoverRegister = ref(false)
const registerBtnStyle = computed(() => ({
  backgroundColor: hoverRegister.value ? '#1565c0' : '#1976d2',
  color: 'white',
  transition: 'background-color 0.3s ease',
}))

// const fetchGameChips = async () => {
//   loading.value = true
//   try {
//     await gameChipStore.requestGameChipListToSpring(page.value, perPage)
//     totalPages.value = Math.ceil(gameChipStore.totalItems / perPage)
//   } catch (error) {
//     console.error('게임칩 목록 불러오기 실패:', error)
//   } finally {
//     loading.value = false
//   }
// }
const fetchGameChips = async () => {
  loading.value = true
  try {
    await gameChipStore.requestGameChipListToSpring(page.value, perPage)
  } catch (error) {
    console.error('게임칩 목록 불러오기 실패:', error)
  } finally {
    loading.value = false
  }
}

// 현재 페이지 아이템만 보여주는 computed
const pagedGameChipList = computed(() => {
  const start = 0 // 실제 데이터는 이미 서버에서 perPage 단위로 가져오니 0부터 끝까지 모두 보여줌
  return gameChipStore.gameChipList.slice(start, perPage)
})

const goToDetail = (id: number) => {
  router.push({
    name: 'GameChipRead',
    params: { id: id.toString() }
  })
}

onMounted(fetchGameChips)
</script>
