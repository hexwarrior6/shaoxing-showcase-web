<template>
  <footer class="footer">
    <div class="footer-content">
      <p>&copy; 2024 HexWarrior6. All rights reserved. </p>
    </div>
  </footer>
</template>

<script>
import {Right} from "@element-plus/icons-vue";

export default {
  name: 'Footer',
  components: {Right},
};
</script>

<style scoped>
/* Footer 样式 */
.footer {
  background-color: var(--accent-200);
  color: var(--bg-100);
  text-align: center;
  padding: 15px 30px;
  font-size: 1rem;
  border-top: 4px solid var(--primary-100);
}

.footer-content p {
  margin: 5px 0;
  line-height: 1.5;
}

.footer a {
  color: var(--bg-100);
  text-decoration: none;
}

.footer a:hover {
  color: var(--primary-100);
}
</style>
