<script setup>
import { ElMessage } from "element-plus";
import { post } from "@/net";
import router from "@/router/index.js";
import { reactive, ref } from "vue";

const resetFormRef1 = ref();
const resetFormRef2 = ref();
const coldDownTime = ref(0); // 邮件验证码冷却时间
const step = ref(1); // 重置密码进度
const isEmailValid = ref(false);

const resetForm1 = reactive({
  email: '',
  validate_code: '',
});

const resetForm2 = reactive({
  password: '',
  password_repeat: '',
});

const reset1 = () => {
  resetFormRef1.value.validate((valid) => {
    if (valid) {
      post('api/auth/reset-password-step1', resetForm1, (code, data, message) => {
        ElMessage.success(data);
        step.value = 2;
      });
    } else {
      ElMessage.warning('重新检查您的信息！');
      return false;
    }
  });
};

const reset2 = () => {
  resetFormRef2.value.validate((valid) => {
    if (valid) {
      post('api/auth/reset-password-step2', resetForm2, (code, data, message) => {
        ElMessage.success(data);
        router.push('/');
      });
    } else {
      ElMessage.warning('重新检查您的密码！');
      return false;
    }
  });
};

let timer = null; // 用于存储定时器ID
const getValidateCode = () => {
  if (!isEmailValid.value) {
    ElMessage.warning('请检查您的邮件地址是否正确！');
  } else {
    coldDownTime.value = 60;
    post('api/auth/validate-email-reset-password', {
      email: resetForm1.email,
    }, (code, data, message) => {
      ElMessage.success(data);
      if (timer) clearInterval(timer); // 清除之前的定时器
      timer = setInterval(() => {
        coldDownTime.value--;
        if (coldDownTime.value <= 0) clearInterval(timer); // 冷却时间结束时清除定时器
      }, 1000); // 设置定时器，每秒钟减一
    });
  }
};

const validatePassword_repeat = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== resetForm2.password) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};

const reset_checker1 = {
  email: [
    { type: 'email', required: true, message: '请输入邮箱地址', trigger: ['blur', 'change'] },
  ],
  validate_code: [
    { required: true, message: '请输入验证码', trigger: 'blur' }
  ]
};

const reset_checker2 = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, message: '密码不得小于5位', trigger: 'blur' },
    { max: 20, message: '密码不得超过20位', trigger: 'blur' },
  ],
  password_repeat: [
    { validator: validatePassword_repeat, trigger: ['blur', 'change'] }
  ]
};

const onValidate = (prop, isValid) => {
  if (prop === 'email') {
    isEmailValid.value = !!isValid;
  }
};
</script>

<template>
  <div style="width: 90%; background-color: #fff; padding: 50px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
    <div style="width: 100%;margin-bottom: 50px">
      <el-steps align-center :active="step">
        <el-step title="验证电子邮件"></el-step>
        <el-step title="重新设定密码"></el-step>
      </el-steps>
    </div>
    <!-- 标题 -->
    <div style="font-size: 24px; font-weight: bold; margin-bottom: 10px;">重置密码</div>

    <div v-show="step===1" style="width: 100%">
      <div style="font-size: 14px; color: gray; margin-bottom: 30px;">请输入需要重置的账号的邮件地址</div>

      <el-form style="width: 100%" :model="resetForm1" :rules="reset_checker1" @validate="onValidate" ref="resetFormRef1">
        <!-- 邮件输入框 -->
        <el-form-item prop="email">
          <el-input
              v-model="resetForm1.email"
              type="text"
              placeholder="邮件地址"
              prefix-icon="message"
          />
        </el-form-item>

        <!-- 邮件验证码 -->
        <el-form-item prop="validate_code">
          <el-row style="width: 100%;">
            <el-col :span="16">
              <el-input v-model="resetForm1.validate_code" type="text" placeholder="验证码" prefix-icon="Bell"/>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="7">
              <el-button :disabled="!isEmailValid || coldDownTime > 0" @click="getValidateCode" plain>
                {{ coldDownTime > 0 ? coldDownTime + 's 后获取' : '获取验证码' }}
              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <!-- 下一步按钮 -->
      <el-button type="danger" style="width: 250px; margin-bottom: 20px; margin-top: 10px" @click="reset1" plain>下一步</el-button>
    </div>
    <div v-show="step===2" style="width: 100%">
      <div style="font-size: 14px; color: gray; margin-bottom: 30px;">请输入您的新密码</div>

      <el-form style="width: 100%" :model="resetForm2" :rules="reset_checker2" @validate="onValidate" ref="resetFormRef2">
        <!-- 密码输入框 -->
        <el-form-item prop="password">
          <el-input
              v-model="resetForm2.password"
              type="password"
              placeholder="密码"
              prefix-icon="lock"
          />
        </el-form-item>

        <!-- 重复密码 -->
        <el-form-item prop="password_repeat">
          <el-input
              v-model="resetForm2.password_repeat"
              type="password"
              placeholder="重复密码"
              prefix-icon="lock"
          />
        </el-form-item>
      </el-form>
      <!-- 重置密码按钮 -->
      <el-button type="danger" style="width: 250px; margin-bottom: 20px; margin-top: 10px" @click="reset2" plain>立即重置</el-button>
    </div>

  </div>
</template>

<style scoped>

</style>
