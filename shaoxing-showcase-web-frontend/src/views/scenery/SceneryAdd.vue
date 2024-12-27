<template>
  <Header/>
  <div class="add-scenery-container">
    <el-form @submit.prevent="submitScenery" class="scenery-form" :model="scenery" ref="sceneryForm" label-width="120px">
      <h2 class="form-title" style="font-size: 24px;">添加景点</h2>

      <el-form-item label="景点名称:" prop="sightName" :rules="[{ required: true, message: '请输入景点名称', trigger: 'blur' }]">
        <el-input v-model="scenery.sightName" placeholder="输入景点名称"></el-input>
      </el-form-item>

      <el-form-item label="景点位置:" prop="location" :rules="[{ required: true, message: '请输入景点位置', trigger: 'blur' }]">
        <el-input v-model="scenery.location" placeholder="输入景点位置"></el-input>
      </el-form-item>

      <el-form-item label="景点描述:" prop="description" :rules="[{ required: true, message: '请输入景点描述', trigger: 'blur' }]">
        <el-input :autosize="{ minRows: 3, maxRows: 5 }" type="textarea" v-model="scenery.description" placeholder="输入景点描述" :rows="4"></el-input>
      </el-form-item>

      <el-form-item label="开放时间:" prop="openingHours" :rules="[{ required: true, message: '请输入开放时间', trigger: 'blur' }]">
        <el-input v-model="scenery.openingHours" placeholder="输入开放时间"></el-input>
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
            <el-button slot="trigger" size="default" type="primary">选择图片</el-button>
          </el-upload>
        </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">
          <el-icon size="large" style="margin-right: 10px">
            <FolderChecked/>
          </el-icon>
          提交
        </el-button>
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
  name: 'AddLocalScenery',
  components: {Footer, Header},
  data() {
    return {
      scenery: {
        sightName: '',
        location: '',
        description: '',
        openingHours: '',
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

      // 获取缩略图
      const reader = new FileReader();
      reader.onload = (e) => {
        this.scenery.imageUrl = e.target.result; // 获取上传图片的Base64 URL作为缩略图
      };
      reader.readAsDataURL(this.imageFile);
    },

    async submitForm() {
      const formEl = this.$refs.sceneryForm;
      if (!formEl) return;
      await formEl.validate((valid, fields) => {
        if (valid) {
          this.submitScenery();
        } else {
          console.log('error submit!', fields);
          ElMessage.error('请完整填写带（*）的栏目！');
        }
      });
    },

    // Existing submitScenery method
    async submitScenery() {
      if (!this.imageFile) {
        ElMessage.error('请选择一张图片');
        return;
      }

      this.loading = true;
      try {
        // 首先上传图片
        const formData = new FormData();
        formData.append('file', this.imageFile);
        formData.append('folder', 'local_sights');
        const imageUploadResponse = await axios.post('/api/upload-image', formData, {withCredentials: true});

        // 设置图片URL
        this.scenery.imageUrl = imageUploadResponse.data.data;

        // 然后提交景点信息
        const response = await axios.post('/api/local-sights', this.scenery, {withCredentials: true});
        if (response.data.code === 200) {
          ElMessage.success('景点添加成功！');
          this.resetForm();
        } else {
          ElMessage.error('添加景点失败，请重试。');
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
      this.scenery = {
        sightName: '',
        location: '',
        description: '',
        openingHours: '',
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
.add-scenery-container {
  max-width: 700px;
  margin: 30px auto;
  padding: 50px 90px 50px 50px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  height: auto;
  min-height: 600px;
}

.scenery-form {
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