<script setup>
import {ElMessage} from "element-plus";
import {post} from "@/net";
import router from "@/router/index.js";
import {reactive, ref} from "vue"

const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  validate_code: '',
})

const registerFormRef = ref()
const coldDownTime = ref(0)

const register = () => {
  registerFormRef.value.validate((valid) => {
    if (valid) {
      post('api/auth/register', form, () => {
        ElMessage.success("注册成功")
        router.push('/')
      })
    } else {
      ElMessage.warning('重新检查您的信息！')
      return false;
    }
  });
};

const getValidateCode = () => {
  if (!isEmailValid) {
    ElMessage.warning('请检查您的邮件地址是否正确！')
  } else {
    coldDownTime.value = 60
    post('api/auth/validate-email-register', {
      email: form.email,
    }, (code, data, message) => {
      ElMessage.success(data)
      setInterval(() => coldDownTime.value--, 1000) // 设置定时器，每秒钟减一
    })
  }
}

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if (!/^[A-Za-z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error('用户名只能包含字母、数字和中文'))
  } else {
    callback()
  }
}

const validatePassword_repeat = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const register_checker = {
  username: [
    {validator: validateUsername, trigger: ['blur', 'change']},
    {min: 5, message: '用户名不得小于5位', trigger: 'blur'},
    {max: 20, message: '用户名不得超过20位', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 5, message: '密码不得小于5位', trigger: 'blur'},
    {max: 20, message: '密码不得超过20位', trigger: 'blur'},
  ],
  password_repeat: [
    {validator: validatePassword_repeat, trigger: ['blur', 'change']}
  ]
  ,
  email: [
    {type: 'email', required: true, message: '请输入邮箱地址', trigger: ['blur', 'change']},
  ],
  validate_code: [
    {required: true, message: '请输入验证码', trigger: 'blur'}
  ]
}

const isEmailValid = ref(false)

const onValidate = (prop, isValid) => {
  if (prop === 'email') {
    isEmailValid.value = !!isValid;
  }
}
</script>

<template>
  <div style="width: 90%; background-color: #fff; padding: 50px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
    <!-- 标题 -->
    <div style="font-size: 24px; font-weight: bold; margin-bottom: 10px;">注册</div>
    <div style="font-size: 14px; color: gray; margin-bottom: 30px;">欢迎注册绍兴家乡介绍平台，请在下方填写相关信息</div>

    <el-form style="width: 100%" :model="form" :rules="register_checker" @validate="onValidate" ref="registerFormRef">
      <!-- 用户名输入框 -->
      <el-form-item prop="username">
        <el-input
            v-model="form.username"
            type="text"
            placeholder="用户名"
            prefix-icon="user"
        />
      </el-form-item>

      <!-- 密码输入框 -->
      <el-form-item prop="password">
        <el-input
            v-model="form.password"
            type="password"
            placeholder="密码"
            prefix-icon="lock"
        />
      </el-form-item>

      <!-- 重复密码 -->
      <el-form-item prop="password_repeat">
        <el-input
            v-model="form.password_repeat"
            type="password"
            placeholder="重复密码"
            prefix-icon="lock"
        />
      </el-form-item>

      <!-- 邮件输入框 -->
      <el-form-item prop="email">
        <el-input
            v-model="form.email"
            type="text"
            placeholder="邮件地址"
            prefix-icon="message"
        />
      </el-form-item>

      <!-- 邮件验证码 -->
      <el-form-item prop="validate_code">
        <el-row style="width: 100%;">
          <el-col :span="16">
            <el-input v-model="form.validate_code" type="text" placeholder="验证码" prefix-icon="Bell"/>
          </el-col>
          <el-col :span="1"></el-col>
          <el-col :span="7">
            <el-button :disabled="!isEmailValid || coldDownTime > 0" @click="getValidateCode" plain>
              {{coldDownTime > 0 ? coldDownTime + 's 后获取' : '获取验证码'}}
            </el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>

    <!-- 注册按钮 -->
    <el-button type="success" style="width: 250px; margin-bottom: 20px; margin-top: 10px" @click="register()" plain>立即注册</el-button>

    <!-- 已有帐户提示 -->
    <el-divider style="margin-bottom: 40px;">
      <div style="color: gray">已有账号？</div>
    </el-divider>

    <!-- 返回登录按钮 -->
    <el-button type="warning" style="width: 250px; margin-bottom: 20px;" @click="router.push('/')" plain>返回登录</el-button>
  </div>
</template>

<style scoped>

</style>