<template>
  <div class="centered-404">
    <el-row justify="center" align="middle" class="full-height">
      <el-col :span="24" class="text-center">
        <el-icon size="100" class="icon-404">
          <document-delete/>
        </el-icon>
        <h1 class="title">404</h1>
        <p class="description">抱歉，您访问的页面不存在</p>
        <el-button type="primary" @click="goHome">返回首页</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {ElButton, ElCol, ElIcon, ElRow} from 'element-plus';
import {DocumentDelete} from '@element-plus/icons-vue';

export default {
  components: {
    ElButton,
    ElRow,
    ElCol,
    ElIcon,
    DocumentDelete
  },
  methods: {
    goHome() {
      this.$router.push('/home');
    }
  }
};
</script>

<style scoped>
.centered-404 {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.full-height {
  height: 100%;
}

.icon-404 {
  color: #409EFF;
}

.title {
  font-size: 72px;
  font-weight: bold;
  margin: 20px 0;
}

.description {
  font-size: 18px;
  color: #999;
  margin-bottom: 30px;
}

</style>