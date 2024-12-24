<template>
  <div class="food-page">
    <h2>家乡美食</h2>

    <!-- 显示美食列表 -->
    <div class="food-list">
      <div v-for="food in foodList" :key="food.id" class="food-item">
        <img :src="food.imageUrl" alt="美食图片" class="food-image" />
        <div class="food-info">
          <h3>{{ food.foodName }}</h3>
          <p><strong>描述：</strong>{{ food.description }}</p>
          <p><strong>主要原料：</strong>{{ food.ingredients }}</p>
          <p><strong>起源：</strong>{{ food.origin }}</p>
        </div>
      </div>
    </div>

    <!-- 添加美食按钮 -->
    <button @click="openAddFoodModal">添加美食</button>

    <!-- 添加美食弹窗 -->
    <div v-if="showAddModal" class="modal">
      <div class="modal-content">
        <h3>添加美食</h3>
        <input v-model="newFood.foodName" placeholder="美食名称" />
        <textarea v-model="newFood.description" placeholder="美食描述"></textarea>
        <textarea v-model="newFood.ingredients" placeholder="主要原料"></textarea>
        <input v-model="newFood.origin" placeholder="美食起源地" />
        <input v-model="newFood.imageUrl" placeholder="图片URL" />

        <button @click="addFood">提交</button>
        <button @click="closeAddFoodModal">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      foodList: [],  // 存储美食数据
      showAddModal: false,  // 是否显示添加美食弹窗
      newFood: {  // 添加新美食的数据模型
        foodName: '',
        description: '',
        ingredients: '',
        imageUrl: '',
        origin: ''
      }
    };
  },
  created() {
    // 组件创建时获取美食数据
    this.fetchFoodList();
  },
  methods: {
    // 获取所有美食
    async fetchFoodList() {
      try {
        const response = await axios.get('/api/local-foods');
        this.foodList = response.data;  // 将返回的数据赋值给 foodList
      } catch (error) {
        console.error('获取美食列表失败:', error);
      }
    },

    // 打开添加美食的弹窗
    openAddFoodModal() {
      this.showAddModal = true;
    },

    // 关闭添加美食的弹窗
    closeAddFoodModal() {
      this.showAddModal = false;
      this.resetNewFood();  // 重置输入框
    },

    // 重置新增美食的输入框
    resetNewFood() {
      this.newFood.foodName = '';
      this.newFood.description = '';
      this.newFood.ingredients = '';
      this.newFood.imageUrl = '';
      this.newFood.origin = '';
    },

    // 提交添加美食
    async addFood() {
      try {
        const response = await axios.post('/api/local-foods', this.newFood);
        if (response.data === 1) {  // 假设返回值 1 表示成功
          this.fetchFoodList();  // 重新获取美食列表
          this.closeAddFoodModal();  // 关闭弹窗
        } else {
          alert('添加失败，请稍后重试');
        }
      } catch (error) {
        console.error('添加美食失败:', error);
      }
    }
  }
};
</script>

<style scoped>
.food-page {
  padding: 20px;
}

.food-list {
  display: flex;
  flex-direction: column;  /* 改为垂直排列 */
  gap: 20px;
}

.food-item {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  padding: 15px;
  border-radius: 8px;
  background-color: var(--bg-100);
  width: 100%;
  max-width: 600px;  /* 限制最大宽度 */
  margin: 0 auto;  /* 居中显示 */
}

.food-image {
  width: 120px;
  height: 120px;
  object-fit: cover;
  margin-right: 20px;
}

.food-info h3 {
  margin: 0;
  color: var(--primary-100);
}

.food-info p {
  margin: 5px 0;
  color: var(--text-200);
}

button {
  background-color: var(--primary-100);
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

button:hover {
  background-color: var(--primary-200);
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: var(--bg-100);
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.modal-content input,
.modal-content textarea {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid var(--primary-100);
  border-radius: 5px;
  font-size: 16px;
}

.modal-content button {
  width: 100%;
  background-color: var(--accent-100);
}

.modal-content button:hover {
  background-color: var(--accent-200);
}
</style>
