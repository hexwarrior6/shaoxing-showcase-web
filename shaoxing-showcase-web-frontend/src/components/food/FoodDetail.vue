<template>
  <Header />
  <div class="food-detail-container" v-if="food">
    <div class="food-header">
      <h1 class="food-name">{{ food.foodName }}</h1>
      <p class="food-origin">{{ food.origin }}</p>
    </div>

    <div class="food-image-container">
      <img :src="`/image/${food.imageUrl}`"
           :alt="food.foodName"
           class="food-image"/>
    </div>

    <div class="food-description">
      <h2 class="indent-title">描述</h2>
      <p>{{ food.description }}</p>

      <h2 class="indent-title">配料</h2>
      <p>{{ food.ingredients }}</p>
    </div>

    <!-- 底部按钮 -->
    <div class="button-container">
      <el-button style="font-size: larger; padding: 20px" type="primary" plain><el-icon style="margin-right: 10px"><Edit /></el-icon>编辑</el-button>
      <el-button style="font-size: larger; padding: 20px" type="danger" plain><el-icon style="margin-right: 10px"><Delete /></el-icon>删除</el-button>
    </div>
  </div>

  <div v-else class="loading">
    <span class="loading-text">加载中...</span>
  </div>
  <Footer />
</template>


<script>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import Footer from "@/components/home/Footer.vue";
import Header from "@/components/home/Header.vue";

export default {
  name: 'FoodDetail',
  components: {Header, Footer},
  setup() {
    const food = ref(null); // 用于存储美食数据
    const route = useRoute(); // 获取当前路由对象
    const foodId = route.params.id; // 从 URL 获取 id 参数

    // 请求函数
    const fetchFoodData = () => {
      axios.get(`/api/local-foods/${foodId}`, {withCredentials: true})
          .then(response => {
            if (response.data.code === 200) {
              food.value = response.data.data;
            } else {
              console.error('获取数据失败', response.data.message);
            }
          })
          .catch(error => {
            console.error('请求错误', error);
          });
    };

    // 在组件挂载时发起请求
    onMounted(() => {
      fetchFoodData();
    });

    // 监听 URL 参数变化（如果 id 变化）
    watch(() => route.params.id, (newId) => {
      food.value = null; // 清空之前的数据
      fetchFoodData(); // 重新请求新的数据
    });

    return {
      food
    };
  }
};
</script>

<style scoped>
/* 引入 DingTalk JinBuTi 字体 */
@font-face {
  font-family: 'DingTalk JinBuTi';
  src: url('@/assets/DingTalk JinBuTi.ttf') format('truetype');
  font-weight: normal;
  font-style: normal;
}

/* 使用 DingTalk JinBuTi 字体 */
.food-detail-container {
  font-family: 'DingTalk JinBuTi', sans-serif; /* 使用自定义字体 */
  max-width: 900px;
  margin: 40px auto;
  padding: 60px;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  color: var(--text-100);
  background: linear-gradient(to top, #e6e9f0 0%, #eef1f5 100%);
  animation: fadeIn 1s ease-out;
  transition: transform 0.3s ease;
}

/* 标题区域 */
.food-header {
  text-align: center;
  margin-bottom: 20px;
}

.food-name {
  font-family: 'DingTalk JinBuTi', sans-serif; /* 使用 DingTalk JinBuTi 字体 */
  font-size: 3rem;
  font-weight: bold;
  letter-spacing: 1.5px;
  text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.3);
  margin-bottom: 10px;
  color: var(--text-100);
}

.food-origin {
  font-size: 1.5rem;
  color: var(--primary-300); /* 使用浅黄色 */
  font-style: italic;
  text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.2);
}

/* 图片区域 */
.food-image-container {
  display: flex;
  justify-content: center;
  margin: 30px 0;
}

.food-image {
  width: 100%;
  max-width: 650px;
  height: auto;
  border-radius: 15px;
  border: 5px solid var(--bg-100); /* 白色边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease;
}

.food-image:hover {
  transform: scale(1.05);
}

/* 描述区域 */
.food-description {
  font-size: 1.3rem;
  line-height: 1.7;
  text-align: left;
  color: var(--text-200);
}

.indent-title {
  font-size: 1.8rem;
  font-weight: normal;
  color: var(--primary-300);
  margin: 25px 0 15px;
  text-transform: uppercase;
  letter-spacing: 1px;
  border-bottom: 3px solid var(--primary-300);
  padding-bottom: 8px;
  padding-left: 2ch;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
}

.food-description p {
  margin-bottom: 25px;
  text-indent: 2em;
  color: var(--text-200);
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

/* 加载提示 */
.loading {
  text-align: center;
  font-size: 1.8rem;
  color: var(--primary-300);
  font-weight: bold;
  animation: pulse 1.5s infinite;
}

.loading-text {
  display: inline-block;
  padding: 12px 30px;
  background-color: var(--primary-100); /* 橙色背景 */
  color: var(--bg-100); /* 白色文字 */
  border-radius: 30px;
  font-size: 1.3rem;
  letter-spacing: 1px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 0.7;
  }
  50% {
    transform: scale(1.1);
    opacity: 1;
  }
  100% {
    transform: scale(1);
    opacity: 0.7;
  }
}

/* 页脚样式 */
footer {
  background-color: var(--accent-200);
  color: var(--bg-100);
  text-align: center;
  padding: 20px;
  margin-top: auto;
  font-size: 1rem;
  border-top: 4px solid var(--accent-100);
}

footer p {
  margin: 5px 0;
}
/* 按钮容器 */
.button-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

</style>