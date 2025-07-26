import axios, { AxiosInstance } from 'axios'
import env from "../env.ts";

const springAxiosInst: AxiosInstance = axios.create({
    baseURL: env.api.MAIN_API_URL,
    timeout: 2500,
})

// 요청 인터셉터 설정
springAxiosInst.interceptors.request.use((config) => {
    const userToken = localStorage.getItem('userToken')
    const requestUrl = config.url || ''

    const isPublicRequest =
        requestUrl === '/game-chip/list' ||
        /^\/game-chip\/read\/\d+$/.test(requestUrl)

    // 공개 요청이면 토큰 안 붙임
    if (isPublicRequest) {
        return config
    }

    if (userToken) {
        config.headers = config.headers || {}
        config.headers.Authorization = `Bearer ${userToken}`
    }

    return config
})

const fastApiAxiosInst: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8000',
    timeout: 2500,
})

export default { springAxiosInst, fastApiAxiosInst }
