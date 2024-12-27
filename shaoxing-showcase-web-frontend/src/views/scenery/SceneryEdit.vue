<template>
  <Header/>
  <div class="add-scenery-container">
    <el-form @submit.prevent="updateScenery" class="scenery-form" :model="scenery" ref="sceneryForm" label-width="120px">
      <h2 class="form-title" style="font-size: 24px;">编辑景点</h2>

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
          更新
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
  name: 'EditLocalScenery',
  components: {Footer, Header},
  data() {
    return {
      scenery: {
        id: '',
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
    // Fetch data for the existing scenery item
    fetchSceneryData() {
      const sceneryId = this.$route.params.id; // 获取URL中的id参数
      axios.get(`/api/local-sights/${sceneryId}`, {withCredentials: true})
          .then(response => {
            if (response.data.code === 200) {
              this.scenery = {...response.data.data, id: sceneryId};
              if (!this.scenery.imageUrl) {
                this.scenery.imageUrl = '';
              } else {
                this.imageFileList = [{url: '/image/' + this.scenery.imageUrl}];
              }
            } else {
              console.error('获取数据失败', response.data.message);
            }
          })
          .catch(error => {
            console.error('请求失败', error);
          });
    },

    // Handle file upload
    handleFileUpload(file, fileList) {
      this.imageFile = file.raw;
      this.imageFileList = fileList;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.scenery.imageUrl = e.target.result;
      };
      reader.readAsDataURL(this.imageFile);
    },

    async submitForm() {
      const formEl = this.$refs.sceneryForm;
      if (!formEl) return;
      await formEl.validate((valid, fields) => {
        if (valid) {
          this.updateScenery();
        } else {
          console.log('error submit!', fields);
          ElMessage.error('请完整填写带（*）的栏目！');
        }
      });
    },

    // Update the scenery item
    async updateScenery() {
      if (!this.imageFile && !this.scenery.imageUrl) {
        ElMessage.error('请选择一张图片');
        return;
      }

      this.loading = true;
      try {
        let formData = new FormData();
        if (this.imageFile) {
          formData.append('file', this.imageFile);
          formData.append('folder', 'local_sights');
          const imageUploadResponse = await axios.post('/api/upload-image', formData, {withCredentials: true});
          this.scenery.imageUrl = imageUploadResponse.data.data;
        }

        const response = await axios.put(`/api/local-sights/${this.scenery.id}`, this.scenery, {withCredentials: true});
        if (response.data.code === 200) {
          ElMessage.success('景点更新成功！');
        } else {
          ElMessage.error('更新景点失败，请重试。');
        }
      } catch (error) {
        console.error('更新过程中发生错误:', error);
        ElMessage.error('更新过程中发生错误，请重试。');
      } finally {
        this.loading = false;
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
    this.fetchSceneryData();
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