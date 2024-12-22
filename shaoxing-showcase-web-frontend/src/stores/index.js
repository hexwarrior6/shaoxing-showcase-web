import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
    const auth = ref({
        user: null
    })

    return { auth }
})
