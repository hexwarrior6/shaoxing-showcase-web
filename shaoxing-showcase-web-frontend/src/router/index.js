import {createRouter, createWebHistory} from 'vue-router';
import HomePage from '../views/HomePageView.vue';
import FoodDetail from "@/components/food/FoodDetail.vue";

const routes = [
    {
        path: '/',
        name: 'welcome',
        component: () => import('@/views/WelcomeView.vue'),
        children: [
            {
                path: '',
                name: 'welcome-login',
                component: () => import('@/components/welcome/LoginPage.vue'),
            },
            {
                path: 'register',
                name: 'welcome-register',
                component: () => import('@/components/welcome/RegisterPage.vue'),
            },
            {
                path: 'reset-password',
                name: 'welcome-reset-password',
                component: () => import('@/components/welcome/ResetPasswordPage.vue'),
            },
        ],
    },
    {
        path: '/home',
        name: 'Home',
        component: HomePage,
        meta: {requiresAuth: true},  // 需要登录才能访问
    },
    {
        path: '/not-found',
        name: 'not-found',
        component: () => import('@/views/NotFoundView.vue'),
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/not-found',  // 未找到页面时重定向到 /not-found
    },
    {
        path: '/food/:id', // :id 是动态参数
        name: 'FoodDetail',
        component: FoodDetail,
        meta: {requiresAuth: true},
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

// 路由守卫
router.beforeEach((to, from, next) => {
    const username = localStorage.getItem('username');

    // 如果路由需要认证且用户未登录，则重定向到登录页面
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (username === null) {
            next({name: 'welcome-login'});  // 跳转到登录页面
        } else {
            next();  // 已登录，继续访问
        }
    } else {
        next();  // 无需登录的页面，直接访问
    }
});

export default router;
