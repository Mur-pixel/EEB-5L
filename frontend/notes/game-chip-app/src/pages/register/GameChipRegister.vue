<template>
  <v-container
      fluid
      class="pa-4"
      style="height: calc(100vh - 64px); overflow-y: auto;"
  >
    <v-row>
      <v-col cols="12">
        <h2>🎮 게임칩 등록</h2>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-text-field v-model="title" label="제목" />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-text-field v-model.number="price" label="가격" type="number" />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-textarea v-model="description" label="설명" auto-grow />
      </v-col>
    </v-row>

    <!-- 썸네일 미리보기 -->
    <v-row v-if="thumbnailUrl" class="mb-2">
      <v-col cols="12">
        <h4>현재 썸네일</h4>
        <v-img :src="thumbnailUrl" height="150" contain />
        <v-btn color="error" small @click="removeThumbnail">썸네일 삭제</v-btn>
      </v-col>
    </v-row>

    <!-- 썸네일 업로드 -->
    <v-row>
      <v-col cols="12">
        <v-file-input
            v-model="thumbnailInputValue"
            label="썸네일 이미지 업로드 (1개)"
            prepend-icon="mdi-camera"
            show-size
            accept="image/*"
            clearable
        />
      </v-col>
    </v-row>

    <!-- 추가 이미지 업로드 -->
    <v-row>
      <v-col cols="12">
        <v-file-input
            v-model="addImageInputValue"
            label="추가 이미지 업로드 (여러 개 가능)"
            multiple
            prepend-icon="mdi-camera"
            show-size
            accept="image/*"
            hint="추가 선택 시 기존 파일에 추가됩니다."
            persistent-hint
            clearable
        />
      </v-col>
    </v-row>

    <!-- 추가 이미지 목록 -->
    <v-row v-if="imageFiles.length > 0" class="mb-2">
      <v-col cols="12">
        <h4>선택한 추가 이미지 목록</h4>
        <v-chip-group column>
          <v-chip
              v-for="(file, index) in imageFiles"
              :key="file.name + '-' + index"
              class="ma-1"
              close
              @click:close="removeImage(index)"
          >
            {{ file.name }}
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>

    <!-- 추가 이미지 미리보기 -->
    <v-row v-if="imagePreviews.length > 0" class="mb-4">
      <v-col cols="12">
        <h4>추가 이미지 미리보기</h4>
        <v-row>
          <v-col
              v-for="(src, idx) in imagePreviews"
              :key="src + '-' + idx"
              cols="3"
          >
            <v-img :src="src" aspect-ratio="1" contain />
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <!-- 버튼 -->
    <v-row>
      <v-col cols="12" class="text-right">
        <v-btn
            :style="primaryBtnStyle"
            class="btn-primary"
            @mouseover="hoverPrimary = true"
            @mouseleave="hoverPrimary = false"
            @click="onSubmit"
        >
          등록 완료
        </v-btn>

        <v-btn
            :style="errorBtnStyle"
            class="btn-error ml-2"
            @mouseover="hoverError = true"
            @mouseleave="hoverError = false"
            @click="onCancel"
        >
          취소
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useGameChipStore } from '../../stores/gameChipStore'

// 상태
const title = ref('')
const description = ref('')
const price = ref(0)

const thumbnailInputValue = ref<File[] | null>(null)
const thumbnailFile = ref<File | null>(null)
const thumbnailUrl = ref('')

const addImageInputValue = ref<File[] | null>(null)
const imageFiles = ref<File[]>([])
const imagePreviews = ref<string[]>([])

const hoverPrimary = ref(false)
const hoverError = ref(false)

const router = useRouter()
const gameChipStore = useGameChipStore()

// 버튼 스타일
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

// 썸네일 감시
watch(thumbnailInputValue, (newFiles) => {
  console.log('🟡 [watch] thumbnailInputValue changed:', newFiles)

  if (!newFiles) {
    thumbnailFile.value = null
    thumbnailUrl.value = ''
    console.warn('⚠️ [watch] 썸네일 없음')
    return
  }

  if (newFiles instanceof File) {
    // 단일 파일
    thumbnailFile.value = newFiles
    try {
      const url = URL.createObjectURL(newFiles)
      thumbnailUrl.value = url
      console.log('🟢 [watch] 썸네일 파일 등록됨 (단일):', thumbnailFile.value)
      console.log('🟢 [watch] 썸네일 URL 생성됨:', url)
    } catch (e) {
      console.error('🔴 [watch] createObjectURL 실패:', e)
    }
  } else if (Array.isArray(newFiles) && newFiles.length > 0) {
    // 배열 파일
    const file = newFiles[0]
    thumbnailFile.value = file
    try {
      const url = URL.createObjectURL(file)
      thumbnailUrl.value = url
      console.log('🟢 [watch] 썸네일 파일 등록됨 (배열):', thumbnailFile.value)
      console.log('🟢 [watch] 썸네일 URL 생성됨:', url)
    } catch (e) {
      console.error('🔴 [watch] createObjectURL 실패:', e)
    }
  } else {
    // 빈 배열 or 기타
    thumbnailFile.value = null
    thumbnailUrl.value = ''
    console.warn('⚠️ [watch] 썸네일 없음 (빈 배열)')
  }
})

// 썸네일 삭제
function removeThumbnail() {
  thumbnailFile.value = null
  thumbnailUrl.value = ''
  thumbnailInputValue.value = null
  console.log('🧹 썸네일 제거됨')
}

// 추가 이미지 감시
watch(addImageInputValue, (newFiles) => {
  console.log('🟡 [watch] addImageInputValue changed:', newFiles)

  if (newFiles && newFiles.length > 0) {
    const filesArray = Array.from(newFiles)
    console.log('🟡 [watch] newFiles to Array:', filesArray)

    const filteredNewFiles = filesArray.filter(
        (f) => !imageFiles.value.some((existing) => existing.name === f.name)
    )
    console.log('🟡 [watch] filtered new files:', filteredNewFiles)

    imageFiles.value = [...imageFiles.value, ...filteredNewFiles]
    console.log('🟢 추가 이미지 목록:', imageFiles.value)
  }
  addImageInputValue.value = null
})

// 추가 이미지 미리보기 URL 업데이트
watch(
    imageFiles,
    (files) => {
      imagePreviews.value.forEach((url) => URL.revokeObjectURL(url)) // 이전 URL 해제
      imagePreviews.value = files.map((file) => URL.createObjectURL(file))
      console.log('🟢 이미지 미리보기 URL 생성됨:', imagePreviews.value)
    },
    { immediate: true }
)

// 이미지 제거
function removeImage(index: number) {
  console.log('🗑️ 이미지 제거됨:', imageFiles.value[index])
  imageFiles.value.splice(index, 1)
}

// 등록 버튼
const onSubmit = async () => {
  console.log('📨 [submit] title:', title.value)
  console.log('📨 [submit] description:', description.value)
  console.log('📨 [submit] price:', price.value)
  console.log('📨 [submit] thumbnailFile:', thumbnailFile.value)
  console.log('📨 [submit] imageFiles:', imageFiles.value)

  if (!title.value.trim()) {
    alert('제목을 입력하세요.')
    return
  }
  if (!description.value.trim()) {
    alert('설명을 입력하세요.')
    return
  }
  if (typeof price.value !== 'number' || isNaN(price.value) || price.value <= 0) {
    alert('가격은 0보다 큰 숫자로 입력하세요.')
    return
  }
  if (!thumbnailFile.value) {
    alert('썸네일을 선택하세요.')
    console.warn('❌ 썸네일 없음')
    return
  }

  const formData = new FormData()
  formData.append('title', title.value)
  formData.append('description', description.value)
  formData.append('price', price.value.toString())
  formData.append('thumbnailFile', thumbnailFile.value)

  imageFiles.value.forEach((file) => {
    formData.append('imageFileList', file)
  })

  console.log('📦 [submit] FormData 최종 준비 완료')

  try {
    const response = await gameChipStore.requestCreateGameChipToSpring(formData)
    console.log('response:', response)

    router.push({ name: 'GameChipRead', params: { id: response.id } })
  } catch (error) {
    alert('등록 실패!')
    console.error('❌ 등록 실패:', error)
  }
}

// 취소 버튼
const onCancel = () => {
  console.log('🚪 [취소] 페이지 뒤로가기')
  setTimeout(() => router.go(-1), 0)
}
</script>
