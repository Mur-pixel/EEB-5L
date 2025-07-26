<template>
  <v-container fluid class="pa-4" style="height: calc(100vh - 64px); overflow-y: auto;">
    <v-row>
      <v-col cols="12"><h2>✏️ 게임칩 수정</h2></v-col>
    </v-row>

    <v-row><v-col cols="12"><v-text-field v-model="title" label="제목" /></v-col></v-row>
    <v-row><v-col cols="12"><v-text-field v-model.number="price" label="가격" type="number" /></v-col></v-row>
    <v-row><v-col cols="12"><v-textarea v-model="description" label="설명" auto-grow /></v-col></v-row>

    <v-row v-if="thumbnailUrl">
      <v-col cols="12">
        <h4>기존 썸네일</h4>
        <v-img :src="thumbnailUrl" height="150" contain />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-file-input
            v-model="thumbnailInputValue"
            label="새 썸네일 이미지 (선택)"
            prepend-icon="mdi-camera"
            show-size
            accept="image/*"
            clearable
        />
      </v-col>
    </v-row>

    <v-row v-if="existingDetailImageUrls.length > 0">
      <v-col cols="12">
        <h4>기존 상세 이미지</h4>
        <v-row>
          <v-col
              v-for="(url, idx) in existingDetailImageUrls"
              :key="'existing-' + idx"
              cols="3"
              class="position-relative"
          >
            <v-img :src="url" aspect-ratio="1" contain />
            <v-btn
                icon
                size="x-small"
                class="position-absolute top-0 right-0"
                style="z-index: 2; background-color: red; color: white;"
                @click="removeExistingImage(idx)"
            >
              ✕
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-file-input
            v-model="addImageInputValue"
            label="추가 이미지 (선택)"
            multiple
            prepend-icon="mdi-camera"
            show-size
            accept="image/*"
            clearable
        />
      </v-col>
    </v-row>

    <v-row v-if="imagePreviews.length > 0" class="mb-4">
      <v-col cols="12">
        <h4>추가 이미지 미리보기</h4>
        <v-row>
          <v-col v-for="(src, idx) in imagePreviews" :key="src + '-' + idx" cols="3">
            <v-img :src="src" aspect-ratio="1" contain />
          </v-col>
        </v-row>
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
          수정 완료
        </v-btn>

        <v-btn
            :class="['btn-error']"
            class="ml-2"
            @mouseover="hoverError = true"
            @mouseleave="hoverError = false"
            :style="errorBtnStyle"
            @click="goBack"
        >
          취소
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useGameChipStore } from '../../stores/gameChipStore'

const route = useRoute()
const router = useRouter()
const gameChipStore = useGameChipStore()

const title = ref('')
const description = ref('')
const price = ref(0)

const thumbnailUrl = ref('')
const thumbnailInputValue = ref<File[] | null>(null)
const thumbnailFile = ref<File | null>(null)

const existingDetailImageUrls = ref<string[]>([])
const removedDetailImageIndexes = ref<number[]>([])

const addImageInputValue = ref<File[] | null>(null)
const imageFiles = ref<File[]>([])
const imagePreviews = ref<string[]>([])

const gameChipId = Number(route.params.id)

// 버튼 hover 상태
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

onMounted(async () => {
  try {
    await gameChipStore.requestGameChipToSpring(gameChipId)
    const chip = gameChipStore.gameChip

    if (!chip) {
      alert('게임칩 데이터를 불러오지 못했습니다.')
      router.push({ name: 'GameChipList' })
      return
    }

    title.value = chip.title
    description.value = chip.description
    price.value = chip.price

    if (chip.thumbnailImageBase64) {
      thumbnailUrl.value = `data:image/*;base64,${chip.thumbnailImageBase64}`
    }

    if (chip.detailImageBase64List && chip.detailImageBase64List.length > 0) {
      existingDetailImageUrls.value = chip.detailImageBase64List.map(
          (b64: string) => `data:image/*;base64,${b64}`
      )
    }
  } catch (e) {
    alert('데이터 불러오기 실패')
    router.push({ name: 'GameChipList' })
  }
})

// 기존 상세 이미지 제거
function removeExistingImage(index: number) {
  removedDetailImageIndexes.value.push(index)
  existingDetailImageUrls.value.splice(index, 1)
}

// 썸네일 업로드 감시
watch(thumbnailInputValue, (newFiles) => {
  if (Array.isArray(newFiles) && newFiles.length > 0) {
    thumbnailFile.value = newFiles[0]
    thumbnailUrl.value = URL.createObjectURL(newFiles[0])
  } else {
    thumbnailFile.value = null
  }
})

// 추가 이미지 업로드 감시
watch(addImageInputValue, (newFiles) => {
  if (newFiles && newFiles.length > 0) {
    imageFiles.value = [...imageFiles.value, ...newFiles]
  }
  addImageInputValue.value = null
})

// 추가 이미지 미리보기
watch(imageFiles, (files) => {
  imagePreviews.value.forEach(url => URL.revokeObjectURL(url))
  imagePreviews.value = files.map(file => URL.createObjectURL(file))
}, { immediate: true })

async function onSubmit() {
  if (!title.value.trim() || !description.value.trim() || price.value <= 0) {
    alert('모든 필드를 올바르게 입력하세요.')
    return
  }

  try {
    await gameChipStore.requestUpdateGameChipToSpring({
      id: gameChipId,
      title: title.value,
      content: description.value,
      price: price.value,
      thumbnailFile: thumbnailFile.value,
      imageFileList: imageFiles.value,
      removeDetailImageIndexes: removedDetailImageIndexes.value
    })

    router.push({ name: 'GameChipRead', params: { id: gameChipId } })
  } catch (e) {
    alert('수정 실패')
    console.error(e)
  }
}

function goBack() {
  router.push({ name: 'GameChipRead', params: { id: gameChipId } })
}
</script>
