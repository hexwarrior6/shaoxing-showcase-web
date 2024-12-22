import {createRouter, createWebHistory} from 'vue-router';
import HomePage from '../components/HomePage.vue';

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
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/not-found',  // 未找到页面时重定向到 /not-found
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

export default router;
