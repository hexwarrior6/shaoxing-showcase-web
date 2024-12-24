<template>
  <header class="header">
    <div class="logo">
      <img src="@/assets/logo.png" alt="网站logo" class="logo-image"/>
      <span class="site-title">基于Java的Web开发期末大作业</span>
    </div>
    <nav class="navbar">
      <ul>
        <li><a href="/home">首页</a></li>
        <li><a href="/personalInfo">个人介绍</a></li>
        <li><a href="#logout" @click="logout">登出({{ username }})</a></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'Header',
};
</script>

<script setup>
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
import {get} from "@/net";

const username = localStorage.getItem('username')

const logout = () => {
  try {
    get('/api/auth/logout', (code, data, message) => {
      localStorage.removeItem('username');  // 清除登录状态
      ElMessage.success("退出成功：" + data);
      router.push("/")
    })
  } catch (error) {
    ElMessage.error("退出失败：" + error.message);
  }
}

</script>

<style scoped>
/* Header 样式 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--accent-200);
  padding: 20px 30px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.logo {
  display: flex;
  align-items: center;
}

.logo-image {
  width: 40px;
  height: 40px;
  margin-right: 15px;
}

.site-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: var(--bg-100);
}

.navbar ul {
  list-style: none;
  display: flex;
  gap: 20px;
}

.navbar ul li {
  font-size: 1.2rem;
}

.navbar ul li a {
  text-decoration: none;
  color: var(--bg-100);
  transition: color 0.3s;
}

.navbar ul li a:hover {
  color: var(--primary-100);
}
</style>
