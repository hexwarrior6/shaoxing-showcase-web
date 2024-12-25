<template>
  <div class="sidebar">
    <ul class="menu">
      <li
        v-for="item in menuItems"
        :key="item.id"
        @click="loadContent(item.id)"
        :class="{ active: isActive(item.id) }"
      >
        <i class="menu-icon" v-html="item.icon"></i>
        <span>{{ item.label }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'SidebarMenu',
  data() {
    return {
      activeContent: 'food', // 默认选中第一项
      menuItems: [
        {
          id: 'food',
          label: '家乡美食',
          icon: `<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M32 44C32 44 32 40.1355 32.0015 40.1282C32.6987 39.7817 33.3702 39.3913 34.0124 38.9606C34.9896 38.3053 35.899 37.5569 36.7279 36.7279C39.9853 33.4706 42 28.9706 42 24H6C6 28.9706 8.01472 33.4706 11.2721 36.7279C12.1086 37.5645 13.0271 38.319 14.0145 38.9786C14.653 39.4051 15.3204 39.792 16.0131 40.1355L16 44H32Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M24 18.0083V8" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M36 18.0083V12" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M12 18.0083V12" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/></svg>`
        },
        {
          id: 'scenery',
          label: '家乡景点',
          icon: `<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M14 28C17 28 26.5 29.5 26.5 33C26.5 36.5 21.394 35.7386 19 36C16.9274 36 12 36 12 39C12 43 32.5447 44 36.5 44" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M31 15C31 10.7333 26.7297 4 20.805 4C14.8804 4 10.333 12 8.54511 19.4667C6.7572 26.9333 7.01261 36 7.01261 36" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M26 24C26 21.5 27.4 17 32 17C36 17 38.2 21.0424 39 24.5C39.8 27.9576 40 33 40 33" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>`
        },
        {
          id: 'culture',
          label: '家乡文化',
          icon: `<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M23.9995 31V36M14.0134 27.7553L4.02734 24.5106C5.39776 20.2929 8.06824 16.6173 11.656 14.0106C15.2438 11.404 19.5648 10 23.9995 10C28.4343 10 32.7552 11.404 36.343 14.0106C39.9308 16.6173 42.6013 20.2929 43.9717 24.5106L33.9856 27.7553H14.0134Z" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>`
        }
      ]
    };
  },
  methods: {
    loadContent(contentType) {
      this.activeContent = contentType;
      this.$emit('load-content', contentType);
    },
    isActive(contentType) {
      return this.activeContent === contentType;
    }
  }
};
</script>

<style scoped>
.sidebar {
  width: 200px;
  background-color: #ffffff;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 250px;
  max-height: 90vh;
  overflow-y: auto;
}

.menu {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu li {
  font-size: 1.1rem;
  padding: 12px 16px;
  margin: 20px 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  border-radius: 8px;
  color: #4a5568;
  transition: all 0.3s ease;
}

.menu li:hover {
  background-color: #edf2f7;
}

.menu li.active {
  background-color: #3b82f6;
  color: white;
}

.menu-icon {
  margin-right: 12px;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
}

/* SVG 图标颜色继承当前文字颜色 */
.menu-icon svg {
  color: currentColor;
}
</style>