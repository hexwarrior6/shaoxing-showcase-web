<script>
import axios from 'axios';
import {ElMessage} from 'element-plus';
import Header from "@/components/home/Header.vue";
import Footer from "@/components/home/Footer.vue";

export default {
  name: 'EditLocalFood',
  components: {Footer, Header},
  data() {
    return {
      food: {
        id: '',
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
    // Fetch data for the existing food item
    fetchFoodData() {
      const foodId = this.$route.params.id; // 获取URL中的id参数
      axios.get(`/api/local-foods/${foodId}`, {withCredentials: true})
          .then(response => {
            if (response.data.code === 200) {
              this.food = {...response.data.data, id: foodId};
              // If the imageUrl is not provided, set it to an empty string or handle accordingly
              if (!this.food.imageUrl) {
                this.food.imageUrl = '';
              } else {
                // Assuming the server returns a URL for existing images
                this.imageFileList = [{url: '/image/' + this.food.imageUrl}];
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
        this.food.imageUrl = e.target.result;
      };
      reader.readAsDataURL(this.imageFile);
    },

    async submitForm() {
      const formEl = this.$refs.foodForm;
      if (!formEl) return;
      await formEl.validate((valid, fields) => {
        if (valid) {
          this.updateFood(); // Changed to updateFood for editing
        } else {
          console.log('error submit!', fields);
          ElMessage.error('请完整填写带（*）的栏目！');
        }
      });
    },

    // Update the food item
    async updateFood() {
      if (!this.imageFile && !this.food.imageUrl) {
        ElMessage.error('请选择一张图片');
        return;
      }

      this.loading = true;
      try {
        let formData = new FormData();
        if (this.imageFile) {
          formData.append('file', this.imageFile);
          formData.append('folder', 'local_foods');
          const imageUploadResponse = await axios.post('/api/upload-image', formData, {withCredentials: true});
          this.food.imageUrl = imageUploadResponse.data.data;
        }

        const response = await axios.put(`/api/local-foods/${this.food.id}`, this.food, {withCredentials: true});
        if (response.data.code === 200) {
          ElMessage.success('美食更新成功！');
          // Optionally, navigate back or to another page
        } else {
          ElMessage.error('更新美食失败，请重试。');
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
    this.fetchFoodData();
  }
};
</script>

<template>
  <Header/>
  <div class="add-food-container">
    <el-form @submit.prevent="updateFood" class="food-form" :model="food" ref="foodForm" label-width="120px">
      <h2 class="form-title" style="font-size: 24px;">编辑家乡美食</h2>

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