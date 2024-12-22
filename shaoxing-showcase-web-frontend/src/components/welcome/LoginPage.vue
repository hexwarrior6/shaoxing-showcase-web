<script setup>
import {ElMessage} from "element-plus";
import {post} from "@/net";
import router from "@/router/index.js";
import {reactive} from "vue"
import {useUserStore} from "@/stores";

const form = reactive({
  username: '',
  password: '',
  remember: false,
})

const userStore = useUserStore()
const login = () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入用户名和密码！')
  } else {
    post('api/auth/login', {
      username: form.username,
      password: form.password,
      remember: form.remember,
    }, (code, data, message) => {
      ElMessage.success('登录成功：' + data)
      localStorage.setItem('username', data);
      router.push("/home");  // 登录成功后跳转到首页
    })
  }
}
</script>

<template>
  <div style="width: 90%; background-color: #fff;padding: 50px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
    <!-- 标题 -->
    <div style="font-size: 24px; font-weight: bold; margin-bottom: 10px;">登录</div>
    <div style="font-size: 14px; color: gray; margin-bottom: 30px;">在进入系统之前请输入用户名和密码进行登录</div>

    <!-- 用户名输入框 -->
    <el-input
        v-model="form.username"
        type="text"
        placeholder="用户名/邮箱"
        style="margin-bottom: 20px; width: 100%;"
        prefix-icon="user"
    />

    <!-- 密码输入框 -->
    <el-input
        v-model="form.password"
        type="password"
        placeholder="密码"
        style="margin-bottom: 10px; width: 100%;"
        prefix-icon="lock"
    />

    <!-- 记住我 + 忘记密码 -->
    <div style="display: flex; justify-content: space-between; align-items: center; width: 100%; margin-bottom: 20px;">
      <el-checkbox v-model="form.remember" style="font-size: 14px; color: gray;">记住我</el-checkbox>
      <el-button @click="router.push('/reset-password')" style="color: gray; font-size: 14px;">忘记密码?</el-button>
    </div>

    <!-- 登录按钮 -->
    <el-button type="success" style="width: 250px; margin-bottom: 20px; margin-top: 10px" @click="login()" plain>立即登录</el-button>

    <!-- 注册提示 -->
    <el-divider style="margin-bottom: 40px">
      <div style="color: gray">没有账号？</div>
    </el-divider>

    <!-- 注册按钮 -->
    <el-button type="warning" style="width: 250px; margin-bottom: 20px;" @click="router.push('/register')" plain>注册账号</el-button>
  </div>
</template>

<style scoped>

</style>