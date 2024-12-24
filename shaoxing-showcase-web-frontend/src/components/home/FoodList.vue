<template>
  <div class="food-container">
    <!-- 搜索框和添加按钮放在这里 -->
    <div class="top-bar">
      <!-- 搜索框 -->
      <input type="text" v-model="searchQuery" class="search-input" placeholder="搜索食物..." />

      <!-- 搜索按钮 -->
      <button @click="searchFoods" class="search-button">搜索</button>

      <!-- 添加按钮 -->
      <button @click="addFood" class="add-button">添加</button>
    </div>

    <!-- 食物展示部分 -->
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
            <button @click="viewDetails(food.id)" class="view-details-btn">查看详情</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 加载中和错误消息 -->
    <div v-if="loading" class="loading-spinner">加载中...</div>
    <div v-if="error" class="error-message">{{ error }}</div>
  </div>
</template>

<script>
import { ref, onMounted} from 'vue'
import axios from 'axios'

export default {
  setup() {
    const foods = ref([])  // 存储食物数据
    const loading = ref(false)  // 加载状态
    const error = ref(null)  // 错误信息
    const searchQuery = ref('')  // 搜索关键词

    // 获取食物数据
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
        error.value = err.message || '获取美食数据时出错'
      } finally {
        loading.value = false
      }
    }

    // 根据搜索关键词进行搜索
    const searchFoods = async () => {
      if (searchQuery.value.trim() === '') {
        // 如果没有输入搜索关键词，重新加载所有食物
        await fetchFoods()
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
          error.value = err.message || '搜索食物时出错'
        } finally {
          loading.value = false
        }
      }
    }

    // 查看详情
    const viewDetails = (foodId) => {
      window.location.href = `/food/${foodId}`
    }

    // 添加食物
    const addFood = () => {
      console.log('添加新食物')
      window.location.href = '/food/add'  // 跳转到添加食物页面
    }

    onMounted(() => {
      fetchFoods()  // 页面加载时获取食物数据
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
  justify-content: flex-end;
  gap: 20px;
  margin-bottom: 20px;
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
</style>
