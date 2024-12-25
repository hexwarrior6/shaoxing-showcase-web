<template>
  <Header/>
  <div class="edit-user-container">
    <el-form @submit.prevent="submitForm" class="user-form" :model="userInfo" ref="userForm" label-width="120px">
      <h2 class="form-title" style="font-size: 24px;">编辑用户信息</h2>

      <el-form-item label="用户名:" prop="username" :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]">
        <el-input v-model="userInfo.username" placeholder="输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="邮箱:" prop="email" :rules="[{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '请输入有效的邮箱地址', trigger: ['blur', 'change'] }]">
        <el-input v-model="userInfo.email" placeholder="输入邮箱"></el-input>
      </el-form-item>

      <el-form-item label="头像上传:" prop="avatarUrl" :rules="[{ required: true, message: '请选择头像图片', trigger: 'change' }]">
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
            <el-button slot="trigger" size="default" type="primary">选择头像</el-button>
          </el-upload>
        </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">
          <el-icon size="large" style="margin-right: 10px">
            <FolderChecked/>
          </el-icon>
          更新
        </el-button>
        <el-button type="danger" @click="deleteUser" :loading="loadingDelete">
          删除账号
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
  name: 'EditUserInfo',
  components: {Footer, Header},
  data() {
    return {
      userInfo: {
        id: '',
        username: '',
        email: '',
        avatarUrl: ''
      },
      imageFile: null,
      imageFileList: [],
      loading: false,
      loadingDelete: false
    };
  },
  methods: {
    fetchUserData() {
      axios.get('/api/user/me', {withCredentials: true})
          .then(response => {
            if (response.data.code === 200) {
              this.userInfo = response.data.data;
              if (this.userInfo.avatarUrl) {
                this.imageFileList = [{url: '/image/' + this.userInfo.avatarUrl}];
              }
            } else {
              console.error('获取用户数据失败:', response.data.message);
            }
          })
          .catch(error => {
            console.error('请求错误:', error);
          });
    },
    handleFileUpload(file, fileList) {
      this.imageFile = file.raw;
      this.imageFileList = fileList;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.userInfo.avatarUrl = e.target.result;
      };
      reader.readAsDataURL(this.imageFile);
    },
    async submitForm() {
      const formEl = this.$refs.userForm;
      if (!formEl) return;
      await formEl.validate((valid, fields) => {
        if (valid) {
          this.updateUser();
        } else {
          console.log('error submit!', fields);
          ElMessage.error('请完整填写带（*）的栏目！');
        }
      });
    },
    async updateUser() {
      if (!this.imageFile && !this.userInfo.avatarUrl) {
        ElMessage.error('请选择头像图片');
        return;
      }

      this.loading = true;
      try {
        let formData = new FormData();
        if (this.imageFile) {
          formData.append('file', this.imageFile);
          formData.append('folder', 'user_avatar');
          const imageUploadResponse = await axios.post('/api/upload-image', formData, {withCredentials: true});
          this.userInfo.avatarUrl = imageUploadResponse.data.data;
        }

        const response = await axios.post('/api/user/me', this.userInfo, {withCredentials: true});
        if (response.data.code === 200) {
          ElMessage.success('用户信息更新成功！');
        } else {
          ElMessage.error('更新用户信息失败，请重试。');
        }
      } catch (error) {
        console.error('更新过程中发生错误:', error);
        ElMessage.error('更新过程中发生错误，请重试。');
      } finally {
        this.loading = false;
      }
    },
    async deleteUser() {
      this.loadingDelete = true;
      try {
        const response = await axios.delete('/api/user/me', {withCredentials: true});
        if (response.data.code === 200) {
          ElMessage.success('账号已删除');
          // Redirect or handle post-deletion action
        } else {
          ElMessage.error('删除账号失败，请重试。');
        }
      } catch (error) {
        console.error('删除过程中发生错误:', error);
        ElMessage.error('删除过程中发生错误，请重试。');
      } finally {
        this.loadingDelete = false;
      }
    },
    beforeImageUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        ElMessage.error('只能上传图片文件');
      }
      if (!isLt2M) {
        ElMessage.error('上传图片大小不能超过 2MB');
      }
      return isImage && isLt2M;
    }
  },
  mounted() {
    this.fetchUserData();
  }
};
</script>

<style scoped>
.edit-user-container {
  max-width: 700px;
  margin: 30px auto;
  padding: 50px 90px 50px 50px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.user-form {
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