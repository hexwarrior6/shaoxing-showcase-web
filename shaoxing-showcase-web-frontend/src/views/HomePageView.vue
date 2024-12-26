<template>
  <div class="app-container">
    <Header/>
    <div class="main-content">
      <Sidebar @load-content="loadContent"/>
      <div class="content-display">
        <transition name="el-zoom-in-left" mode="out-in">
          <component
              :is="getCurrentComponent"
              :key="content"
          />
        </transition>
      </div>

    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/home/Header.vue';
import Sidebar from '@/components/home/Sidebar.vue';
import Footer from '@/components/home/Footer.vue';
import FoodList from "@/components/home/FoodList.vue";
import SceneryList from "@/components/home/SceneryList.vue";
import CultureList from "@/components/home/CultureList.vue";

export default {
  computed: {
    getCurrentComponent() {
      switch (this.content) {
        case 'food':
          return 'FoodList'
        case 'scenery':
          return 'SceneryList'
        case 'culture':
          return 'CultureList'
        default:
          return 'FoodList'
      }
    }
  },
  name: 'HomePage',
  components: {
    FoodList,
    SceneryList,
    CultureList,
    Header,
    Sidebar,
    Footer,
  },
  data() {
    return {
      content: '',
    };
  },
  methods: {
    loadContent(contentType) {
      this.content = contentType;
    },
  },
};
</script>

<style scoped>
/* 全局样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Arial', sans-serif;
  background-color: var(--bg-100);
  color: var(--text-100);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

:root {
  --primary-100: #de283b;
  --primary-200: #ff6366;
  --primary-300: #ffccc4;
  --accent-100: #25b1bf;
  --accent-200: #005461;
  --text-100: #1a1a1a;
  --text-200: #404040;
  --bg-100: #c81111;
  --bg-200: #f5f5f5;
  --bg-300: #cccccc;
}

/* 页面容器 */
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保页面至少占满整个视口高度 */
}

.main-content {
  display: flex;
  flex-grow: 1; /* 主内容区域占据剩余空间 */
  padding: 20px;
  gap: 20px; /* 主内容区域之间的间隙 */
}

/* 内容显示区 */
.content-display {
  flex-grow: 1;
  background-color: var(--bg-100);
  padding: 20px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  transition: all 0.3s ease;
}

.content-display h2 {
  font-size: 1.8rem;
  color: var(--primary-100);
}

.content-display p {
  font-size: 1.2rem;
  line-height: 1.6;
  color: var(--text-200);
}

.content-display:hover {
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
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

/* Sidebar 样式 */
.sidebar {
  width: 200px;
  background-color: var(--accent-100);
  color: var(--bg-100);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.sidebar ul {
  list-style: none;
}

.sidebar ul li {
  margin: 15px 0;
}

.sidebar ul li a {
  text-decoration: none;
  color: var(--bg-100);
  font-size: 1.2rem;
  transition: color 0.3s;
}

.sidebar ul li a:hover {
  color: var(--primary-100);
}
</style>
