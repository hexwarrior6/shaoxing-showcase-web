<template>
  <div class="food-container">
    <!-- 顶部工具栏 -->
    <div class="top-bar">
      <!-- 在搜索框左侧添加“家乡美食”文本 -->
      <span class="home-food-label">家乡美食</span>

      <!-- 搜索框和按钮右对齐 -->
      <div class="search-wrapper">
        <input type="text" v-model="searchQuery" class="search-input" placeholder="搜索美食..."/>
        <button @click="searchFoods" class="search-button">搜索</button>
        <button @click="addFood" class="add-button">添加</button>
      </div>
    </div>

    <!-- 美食展示容器 -->
    <div class="food-display-container" v-if="!loading && !error">
      <div class="food-card" v-for="food in foods" :key="food.id">
        <div class="food-card-inner">
          <img :src="`/image/${food.imageUrl}`"
               :alt="food.foodName"
               class="food-image"/>
          <div class="food-details">
            <h2 class="food-name">{{ food.foodName }}</h2>
            <p class="food-description">{{ food.description }}</p>
            <p class="food-origin">产地: {{ food.origin }}</p>
            <p class="food-ingredients">常见食材: {{ food.ingredients }}</p>
            <button @click="viewDetails(food.id)" class="view-details-btn">查看详情</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 加载中和错误提示 -->
    <div v-if="loading" class="loading-spinner">加载中...</div>
    <div v-if="error" class="error-message">{{ error }}</div>
  </div>
</template>

<script>
import {onMounted, ref} from 'vue'
import axios from 'axios'

export default {
  setup() {
    const foods = ref([])  // 存储美食数据
    const loading = ref(false)  // 加载中标志
    const error = ref(null)  // 错误信息
    const searchQuery = ref('')  // 搜索查询关键字

    // 获取美食数据
    const fetchFoods = async () => {
      try {
        loading.value = true
        const response = await axios.get('/api/local-foods', {withCredentials: true})
        if (response.data.code === 200) {
          foods.value = response.data.data
        } else {
          throw new Error(response.data.message || '获取数据失败')
        }
      } catch (err) {
        error.value = err.message || '获取数据过程中出现错误'
      } finally {
        loading.value = false
      }
    }

    // 根据搜索关键字搜索美食
    const searchFoods = async () => {
      if (searchQuery.value.trim() === '') {
        await fetchFoods()  // 如果没有输入搜索内容，则重新加载所有数据
      } else {
        try {
          loading.value = true
          const response = await axios.get(`/api/local-foods/search/${searchQuery.value}`, {withCredentials: true})
          if (response.data.code === 200) {
            foods.value = response.data.data
          } else {
            throw new Error(response.data.message || '搜索失败')
          }
        } catch (err) {
          error.value = err.message || '搜索美食时发生错误'
        } finally {
          loading.value = false
        }
      }
    }

    // 查看详情
    const viewDetails = (foodId) => {
      window.location.href = `/food/detail/${foodId}`
    }

    // 添加美食
    const addFood = () => {
      console.log('添加美食')
      window.location.href = '/food/add'
    }

    onMounted(() => {
      fetchFoods()  // 页面加载时获取美食数据
    })

    return {foods, loading, error, searchQuery, searchFoods, addFood, viewDetails}
  }
}
</script>

<style scoped>
.food-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.top-bar {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  justify-content: flex-start; /* 让“家乡美食”在左侧 */
  margin-bottom: 20px;
}

.home-food-label {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-left: 5px;
  font-family: 'DingTalk JinBuTi', sans-serif;
}

.search-wrapper {
  display: flex;
  gap: 10px;
  margin-left: auto; /* 将搜索框和按钮右对齐 */
}

.search-input {
  padding: 8px 12px;
  font-size: 1rem;
  border-radius: 5px;
  border: 1px solid #ccc;
  outline: none;
}

.search-input:focus {
  border-color: #ff6347;
}

.search-button {
  padding: 8px 16px;
  font-size: 1rem;
  color: #fff;
  background-color: #ff6347;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-button:hover {
  background-color: #ff4500;
}

.add-button {
  padding: 8px 16px;
  font-size: 1rem;
  color: #fff;
  background-color: #ff6347;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.add-button:hover {
  background-color: #ff4500;
}

.food-display-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  justify-items: center;
  padding: 40px;
  background: linear-gradient(135deg, #f1f1f1 30%, #e2e2e2);
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

.food-container,
.food-display-container {
  border-radius: 10px; /* Uniform roundness for both containers */
}

.food-container {
  padding: 20px;
  background: linear-gradient(135deg, #fafafa 30%, #FDFDFD);
  position: relative;
  z-index: 1;
  box-shadow: none; /* Ensure no shadow is applied */
}

.food-container::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: -1;
  filter: blur(5px);
  pointer-events: none;
  box-shadow: none; /* Ensure no shadow is applied to the pseudo-element */
  border-radius: 10px; /* Match the container's border-radius */
}

.food-display-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  justify-items: center;
  padding: 40px;
  background: linear-gradient(135deg, #f9f9f9 30%, #f2f2f2);
  position: relative;
  z-index: 1;
}

/* Other CSS rules remain unchanged */
</style>