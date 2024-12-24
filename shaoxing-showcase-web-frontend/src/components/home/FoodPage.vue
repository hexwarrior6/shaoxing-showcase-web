<template>
  <div class="food-display-container" v-if="!loading && !error">
    <div class="food-card" v-for="food in foods" :key="food.id">
      <div class="food-card-inner">
        <img :src="`/image/${food.imageUrl}`"
             :alt="food.foodName"
             class="food-image"/>
        <div class="food-details">
          <h2 class="food-name">{{ food.foodName }}</h2>
          <p class="food-description">{{ food.description }}</p>
          <p class="food-origin">原产地: {{ food.origin }}</p>
          <p class="food-ingredients">主要食材: {{ food.ingredients }}</p>
          <!-- 添加按钮 -->
          <button @click="viewDetails(food.id)" class="view-details-btn">查看详情</button>
        </div>
      </div>
    </div>
  </div>
  <div v-if="loading" class="loading-spinner">加载中...</div>
  <div v-if="error" class="error-message">{{ error }}</div>
</template>

<script>
import { ref, onMounted } from 'vue'
import axios from 'axios'

export default {
  setup() {
    const foods = ref([])
    const loading = ref(false)
    const error = ref(null)

    const fetchFoods = async () => {
      try {
        loading.value = true
        const response = await axios.get('/api/local-foods', { withCredentials: true })
        if (response.data.code === 200) {
          foods.value = response.data.data
        } else {
          throw new Error(response.data.message || '获取数据失败')
        }
      } catch (err) {
        error.value = err.message || '获取美食数据时出错'
      } finally {
        loading.value = false
      }
    }

    const viewDetails = (foodId) => {
      // 跳转到详情页面，假设详情页的路由是 `/food/:id`
      // 你可以根据自己的路由配置修改这里的代码
      window.location.href = `/food/${foodId}`
    }

    onMounted(() => {
      fetchFoods()
    })

    return { foods, loading, error, viewDetails }
  }
}
</script>

<style scoped>
.food-display-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  justify-items: center;
  padding: 40px;
}

.food-card {
  width: 100%;
  max-width: 350px;
  background: #fff;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.food-card-inner {
  position: relative;
}

.food-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
}

.food-image {
  width: 100%;
  height: auto;
  aspect-ratio: 16 / 9;
  object-fit: cover;
  border-radius: 20px 20px 0 0;
}

.food-details {
  padding: 20px;
  background: #fff;
  border-radius: 0 0 20px 20px;
  text-align: center;
}

.food-name {
  font-size: 1.6rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 12px;
}

.food-description {
  font-size: 1.1rem;
  color: #777;
  line-height: 1.6;
  margin-top: 10px;
}

.food-origin, .food-ingredients {
  font-size: 1rem;
  color: #555;
  margin-top: 8px;
}

.food-card:hover .food-name {
  color: #ff6347;
}

.food-card:hover .food-description {
  color: #555;
}

.loading-spinner {
  text-align: center;
  font-size: 1.2rem;
  color: #555;
}

.error-message {
  text-align: center;
  font-size: 1.2rem;
  color: #ff6347;
}

/* 按钮样式 */
.view-details-btn {
  display: inline-block;
  margin-top: 15px;
  padding: 10px 20px;
  font-size: 1rem;
  color: #fff;
  background-color: #ff6347;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.view-details-btn:hover {
  background-color: #ff4500;
}
</style>
