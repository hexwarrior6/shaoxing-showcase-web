<template>
  <div class="sidebar">
    <ul class="menu">
      <li @click="loadContent('food')" :class="{ active: isActive('food') }">
        <i class="menu-icon" v-html="foodIcon"></i> 家乡美食
      </li>
      <li @click="loadContent('scenery')" :class="{ active: isActive('scenery') }">
        <i class="menu-icon" v-html="sceneryIcon"></i> 家乡景点
      </li>
      <li @click="loadContent('culture')" :class="{ active: isActive('culture') }">
        <i class="menu-icon" v-html="cultureIcon"></i> 家乡文化
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Sidebar',
  data() {
    return {
      // 图标 SVG 数据
      foodIcon: `<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M32 44C32 44 32 40.1355 32.0015 40.1282C32.6987 39.7817 33.3702 39.3913 34.0124 38.9606C34.9896 38.3053 35.899 37.5569 36.7279 36.7279C39.9853 33.4706 42 28.9706 42 24H6C6 28.9706 8.01472 33.4706 11.2721 36.7279C12.1086 37.5645 13.0271 38.319 14.0145 38.9786C14.653 39.4051 15.3204 39.792 16.0131 40.1355L16 44H32Z" fill="none" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M24 18.0083V8" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M36 18.0083V12" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M12 18.0083V12" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M40 8C37.7909 8 36 9.79086 36 12" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M28 4C25.7909 4 24 5.79086 24 8" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><path d="M16 8C13.7909 8 12 9.79086 12 12" stroke="#333" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/></svg>`,
      sceneryIcon: `<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M14 28C17 28 26.5 29.5 26.5 33C26.5 36.5 21.394 35.7386 19 36C16.9274 36 12 36 12 39C12 43 32.5447 44 36.5 44" stroke="#333" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M31 15C31 10.7333 26.7297 4 20.805 4C14.8804 4 10.333 12 8.54511 19.4667C6.7572 26.9333 7.01261 36 7.01261 36" stroke="#333" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M26 24C26 21.5 27.4 17 32 17C36 17 38.2 21.0424 39 24.5C39.8 27.9576 40 33 40 33" stroke="#333" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>`,
      cultureIcon: `<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M23.9995 31V36M14.0134 27.7553L4.02734 24.5106C5.39776 20.2929 8.06824 16.6173 11.656 14.0106C15.2438 11.404 19.5648 10 23.9995 10C28.4343 10 32.7552 11.404 36.343 14.0106C39.9308 16.6173 42.6013 20.2929 43.9717 24.5106L33.9856 27.7553H14.0134ZM14.0134 27.7553C14.6986 25.6465 16.0339 23.8087 17.8278 22.5053L14.0134 27.7553ZM14.0134 27.7553L23.9995 31L14.0134 27.7553ZM17.8278 22.5053C19.6217 21.202 21.7821 20.5 23.9995 20.5L17.8278 22.5053ZM17.8278 22.5053L23.9995 31L17.8278 22.5053ZM23.9995 20.5C26.2169 20.5 28.3774 21.202 30.1713 22.5053L23.9995 20.5ZM23.9995 20.5V31V20.5ZM30.1713 22.5053C31.9652 23.8087 33.3004 25.6465 33.9856 27.7553L30.1713 22.5053ZM30.1713 22.5053L23.9995 31L30.1713 22.5053ZM33.9856 27.7553L23.9995 31L33.9856 27.7553Z" stroke="#333" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>`
    };
  },
  methods: {
    loadContent(contentType) {
      this.activeContent = contentType;
      this.$emit('load-content', contentType);
    },
    isActive(contentType) {
      return this.activeContent === contentType;
    },
  },
};
</script>

<style scoped>
/* Sidebar 样式 */
.sidebar {
  width: 200px;
  background-color: var(--accent-100);
  color: var(--bg-100);
  padding: 20px;
  box-shadow: 4px 0 10px rgba(0, 0, 0, 0.1);
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
  padding-left: 0;
}

.menu li {
  font-size: 1.2rem;
  padding: 12px 20px;
  margin: 10px 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  border-radius: 8px;
  transition: background-color 0.3s, color 0.3s;
}

.menu li:hover {
  background-color: var(--primary-200);
  color: var(--bg-100);
}

.menu li.active {
  background-color: var(--primary-100);
  color: var(--bg-100);
}

.menu-icon {
  margin-right: 10px;
  width: 20px;
  height: 20px;
}

.menu li i {
  margin-right: 8px;
}
</style>
