import axiosInstance from "../utility/axiosInstance.ts";

export const gameChipActions = {
    async requestGameChipListToSpring(page: number, perPage: number): Promise<void> {
        try {
            const res = await axiosInstance.springAxiosInst.get('/game-chip/list', {
                params: { page, perPage }
            })
            this.gameChipList = res.data.gameChipList
            this.totalItems = res.data.totalItems
            this.totalPages = res.data.totalPages
            console.log('res.data.gameChipList:', res.data.gameChipList)
        } catch (error) {
            console.error('requestGameChipListToSpring():', error)
            throw error
        }
    },

    async requestGameChipToSpring(id: number): Promise<void> {
        try {
            const res = await axiosInstance.springAxiosInst.get(`/game-chip/read/${id}`)
            this.gameChip = res.data
        } catch (error) {
            alert('requestGameChipToSpring() 문제 발생!')
            throw error
        }
    },

    async requestCreateGameChipToSpring(payload: FormData): Promise<any> {
        try {
            const token = localStorage.getItem('userToken') || ''
            if (!token) {
                alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.')
                return
                // router.push('/authentication')
            }

            console.log('FormData entries:')
            for (const pair of payload.entries()) {
                console.log(pair[0], pair[1])
            }

            const res = await axiosInstance.springAxiosInst.post('/game-chip/register', payload, {
                headers: {
                    Authorization: `Bearer ${token}`,  // 헤더에 토큰 포함
                },
            })
            alert('등록 성공!')
            return res.data
        } catch (error) {
            alert('requestCreateGameChipToSpring() 문제 발생')
            throw error
        }
    },

    async requestDeleteGameChipToSpring(id: number): Promise<void> {
        console.log('delete game-chip id:', id)

        try {
            await axiosInstance.springAxiosInst.delete(`/game-chip/delete/${id}`)
            alert('삭제 성공!')
        } catch (error) {
            alert('requestDeleteGameChipToSpring() 문제 발생')
            throw error
        }
    },

    async requestUpdateGameChipToSpring(payload: {
        id: number
        title: string
        content: string
        price: number
        thumbnailFile: File | null
        imageFileList: File[]
        removeDetailImageIndexes: number[]
    }): Promise<void> {
        try {
            const formData = new FormData()
            formData.append('title', payload.title)
            formData.append('description', payload.content)
            formData.append('price', payload.price.toString())

            if (payload.thumbnailFile) {
                formData.append('thumbnailFile', payload.thumbnailFile);
            }

            for (const file of payload.imageFileList) {
                formData.append('imageFileList', file);
            }

            for (const index of payload.removeDetailImageIndexes) {
                formData.append('removeDetailImageIndexes', index.toString());
            }

            for (const [key, value] of formData.entries()) {
                if (value instanceof File) {
                    console.log(`${key}: [File name: ${value.name}, size: ${value.size}]`)
                } else {
                    console.log(`${key}: ${value}`)
                }
            }

            await axiosInstance.springAxiosInst.put(`/game-chip/update/${payload.id}`, formData)
            alert('수정 성공!')
        } catch (error) {
            alert('게임칩 수정 중 오류 발생')
            throw error
        }
    }
}
