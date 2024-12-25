<template>
  <Header/>
  <div class="add-food-container">
    <el-form @submit.prevent="submitFood" class="food-form" :model="food" ref="foodForm" label-width="120px">
      <h2 class="form-title" style="font-size: 24px;">添加家乡美食</h2>

      <el-form-item label="美食名称:" prop="foodName" :rules="[{ required: true, message: '请输入美食名称', trigger: 'blur' }]">
        <el-input v-model="food.foodName" placeholder="输入美食名称"></el-input>
      </el-form-item>

      <el-form-item label="美食起源地:" prop="origin" :rules="[{ required: true, message: '请输入美食起源地', trigger: 'blur' }]">
        <el-input v-model="food.origin" placeholder="输入美食起源地"></el-input>
      </el-form-item>

      <el-form-item label="主要原料:" prop="ingredients" :rules="[{ required: true, message: '请输入主要原料', trigger: 'blur' }]">
        <el-input v-model="food.ingredients" placeholder="输入主要原料"></el-input>
      </el-form-item>

      <el-form-item label="美食描述:" prop="description" :rules="[{ required: true, message: '请输入美食描述', trigger: 'blur' }]">
        <el-input :autosize="{ minRows: 3, maxRows: 5 }" type="textarea" v-model="food.description" placeholder="输入美食描述" :rows="4"></el-input>
      </el-form-item>

      <el-form-item label="上传图片:" prop="imageUrl" :rules="[{ required: true, message: '请选择一张图片', trigger: 'change' }]">
        <div class="upload-container">
          <el-upload
              class="upload-image"
              action=""
              :before-upload="beforeImageUpload"
              :on-change="handleFileUpload"
              accept="image/*"
              list-type="picture-card"
              :file-list="imageFileList"
              :limit="1"
              :auto-upload="false"
          >
            <el-button slot="trigger" size="small" type="primary">选择图片</el-button>
          </el-upload>
        </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
  <Footer/>
</template>

<script>
import axios from 'axios';
import {ElMessage} from 'element-plus';
import Header from "@/components/home/Header.vue";
import Footer from "@/components/home/Footer.vue";

export default {
  name: 'AddLocalFood',
  components: {Footer, Header},
  data() {
    return {
      food: {
        foodName: '',
        description: '',
        ingredients: '',
        origin: '',
        imageUrl: ''
      },
      imageFile: null,
      imageFileList: [],
      loading: false
    };
  },
  methods: {
    // 处理文件上传选择
    handleFileUpload(file, fileList) {
      this.imageFile = file.raw;
      this.imageFileList = fileList;

      // 上传图片并获取缩略图
      const reader = new FileReader();
      reader.onload = (e) => {
        this.food.imageUrl = e.target.result; // 获取上传图片的Base64 URL作为缩略图
      };
      reader.readAsDataURL(this.imageFile);
    },

    async submitForm() {
      const formEl = this.$refs.foodForm;
      if (!formEl) return;
      await formEl.validate((valid, fields) => {
        if (valid) {
          this.submitFood(); // Call the existing submitFood method if validation passes
        } else {
          console.log('error submit!', fields);
          ElMessage.error('请完整填写带（*）的栏目！');
        }
      });
    },

    // Existing submitFood method
    async submitFood() {
      if (!this.imageFile) {
        ElMessage.error('请选择一张图片');
        return;
      }

      this.loading = true;
      try {
        // 首先上传图片
        const formData = new FormData();
        formData.append('file', this.imageFile);
        formData.append('folder', 'local_foods');
        const imageUploadResponse = await axios.post('/api/upload-image', formData, {withCredentials: true});

        // 设置图片URL
        this.food.imageUrl = imageUploadResponse.data.data;

        // 然后提交美食信息
        const response = await axios.post('/api/local-foods', this.food, {withCredentials: true});
        if (response.data.code === 200) {
          ElMessage.success('美食添加成功！');
          this.resetForm();
        } else {
          ElMessage.error('添加美食失败，请重试。');
        }
      } catch (error) {
        console.error('上传过程中发生错误:', error);
        ElMessage.error('上传过程中发生错误，请重试。');
      } finally {
        this.loading = false;
      }
    },

    // 重置表单
    resetForm() {
      this.food = {
        foodName: '',
        description: '',
        ingredients: '',
        origin: '',
        imageUrl: ''
      };
      this.imageFile = null;
      this.imageFileList = [];
    },

    // 上传图片前的检查
    beforeImageUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2; // 限制图片大小为2MB

      if (!isImage) {
        ElMessage.error('只能上传图片文件');
      }
      if (!isLt2M) {
        ElMessage.error('上传图片大小不能超过 2MB');
      }
      return isImage && isLt2M;
    }
  }
};
</script>

<style scoped>
.add-food-container {
  max-width: 700px;
  margin: 30px auto;
  padding: 50px 90px 50px 50px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  height: auto; /* 让高度自动适应内容 */
  min-height: 600px; /* 设置一个最小高度 */
}

.food-form {
  display: flex;
  flex-direction: column;
}

.form-title {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.upload-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>