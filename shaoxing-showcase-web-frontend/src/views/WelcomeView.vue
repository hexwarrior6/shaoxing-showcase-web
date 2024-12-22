<script setup>

</script>

<template>
  <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
    <!-- 左侧背景图 -->
    <div style="flex: 1; background-color: dodgerblue">
      <el-image style="width: 100%; height: 100%;" fit="cover" src="../src/assets/image/city-9193823_1920.jpg"/>
    </div>
    <div class="welcome-title">
      <div><h1>欢迎来到我们的学习平台</h1></div>
      <div><p>在这里你可以学习java和网页开发</p></div>
    </div>
    <!-- 右侧功能框 -->
    <div style="width: 400px; background-color: #fff;padding: 50px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; z-index: 1">

      <router-view v-slot="{ Component }">
        <transition name="el-zoom-in-center" mode="out-in">
          <component :is="Component"/>
        </transition>
      </router-view>
    </div>
  </div>
</template>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 50px;
  left: 60px;
  color: #fff;
  text-shadow: 0 0 10px #000;
}
</style>