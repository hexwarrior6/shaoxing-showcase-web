<template>
  <div class="culture-container">
    <!-- 顶部工具栏 -->
    <div class="top-bar">
      <!-- 在搜索框左侧添加“家乡景点”文本 -->
      <span class="home-culture-label">家乡景点</span>

      <!-- 搜索框和按钮右对齐 -->
      <div class="search-wrapper">
        <input type="text" v-model="searchQuery" class="search-input" placeholder="搜索景点..."/>
        <button @click="searchCultures" class="search-button">搜索</button>
        <button @click="addCulture" class="add-button">添加</button>
      </div>
    </div>

    <!-- 景点展示容器 -->
    <div class="culture-display-container" v-if="!loading && !error">
      <div class="culture-card" v-for="culture in cultures" :key="culture.id">
        <div class="culture-card-inner">
          <img :src="`/image/${culture.imageUrl}`"
               :alt="culture.cultureName"
               class="culture-image"/>
          <div class="culture-details">
            <h2 class="culture-name">{{ culture.cultureName }}</h2>
            <p class="culture-description">{{ culture.description }}</p>
            <p class="culture-significance">意义: {{ culture.significance }}</p>
            <p class="culture-time">时间: {{ culture.time }}</p>
            <button @click="viewDetails(culture.id)" class="view-details-btn">查看详情</button>
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
    const cultures = ref([])  // 存储景点数据
    const loading = ref(false)  // 加载中标志
    const error = ref(null)  // 错误信息
    const searchQuery = ref('')  // 搜索查询关键字

    // 获取家乡景点数据
    const fetchCultures = async () => {
      try {
        loading.value = true
        const response = await axios.get('/api/local-cultures', {withCredentials: true})
        if (response.data.code === 200) {
          cultures.value = response.data.data
        } else {
          throw new Error(response.data.message || '获取数据失败')
        }
      } catch (err) {
        error.value = err.message || '获取数据过程中出现错误'
      } finally {
        loading.value = false
      }
    }

    // 根据搜索关键字搜索景点
    const searchCultures = async () => {
      if (searchQuery.value.trim() === '') {
        await fetchCultures()  // 如果没有输入搜索内容，则重新加载所有数据
      } else {
        try {
          loading.value = true
          const response = await axios.get(`/api/local-cultures/search/${searchQuery.value}`, {withCredentials: true})
          if (response.data.code === 200) {
            cultures.value = response.data.data
          } else {
            throw new Error(response.data.message || '搜索失败')
          }
        } catch (err) {
          error.value = err.message || '搜索景点时发生错误'
        } finally {
          loading.value = false
        }
      }
    }

    // 查看详情
    const viewDetails = (cultureId) => {
      window.location.href = `/culture/detail/${cultureId}`
    }

    // 添加景点
    const addCulture = () => {
      console.log('添加景点')
      window.location.href = '/culture/add'
    }

    onMounted(() => {
      fetchCultures()  // 页面加载时获取景点数据
    })

    return {cultures, loading, error, searchQuery, searchCultures, addCulture, viewDetails}
  }
}
</script>

<style scoped>
.culture-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.top-bar {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  justify-content: flex-start; /* 让“家乡景点”在左侧 */
  margin-bottom: 20px;
}

.home-culture-label {
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

.culture-display-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  justify-items: center;
  padding: 40px;
  background: linear-gradient(135deg, #f1f1f1 30%, #e2e2e2);
}

.culture-card {
  width: 100%;
  max-width: 350px;
  background: #fff;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.culture-card-inner {
  position: relative;
}

.culture-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
}

.culture-image {
  width: 100%;
  height: auto;
  aspect-ratio: 16 / 9;
  object-fit: cover;
  border-radius: 20px 20px 0 0;
}

.culture-details {
  padding: 20px;
  background: #fff;
  border-radius: 0 0 20px 20px;
  text-align: center;
}

.culture-name {
  font-size: 1.6rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 12px;
}

.culture-description {
  font-size: 1.1rem;
  color: #777;

  line-height: 1.6;
  margin-top: 10px;
}

.culture-significance, .culture-time {
  font-size: 1rem;
  color: #555;
  margin-top: 8px;
}

.culture-card:hover .culture-name {
  color: #ff6347;
}

.culture-card:hover .culture-description {
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

.culture-container,
.culture-display-container {
  border-radius: 10px; /* Uniform roundness for both containers */
}

.culture-container {
  padding: 20px;
  background: linear-gradient(135deg, #fafafa 30%, #FDFDFD);
  position: relative;
  z-index: 1;
  box-shadow: none; /* Ensure no shadow is applied */
}

.culture-container::after {
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

.culture-display-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  justify-items: center;
  padding: 40px;
  background: linear-gradient(135deg, #f9f9f9 30%, #f2f2f2);
  position: relative;
  z-index: 1;
}
</style>