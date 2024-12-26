import {createRouter, createWebHistory} from 'vue-router';
import HomePage from '../views/HomePageView.vue';
import FoodDetail from "@/views/food/FoodDetail.vue";
import FoodAdd from "@/views/food/FoodAdd.vue";
import FoodEdit from "@/views/food/FoodEdit.vue";
import UserInfoView from "@/views/UserInfoView.vue";
import SceneryDetail from "@/views/scenery/SceneryDetail.vue";
import SceneryEdit from "@/views/scenery/SceneryEdit.vue";
import SceneryAdd from "@/views/scenery/SceneryAdd.vue";
import CultureDetail from "@/views/culture/CultureDetail.vue";
import CultureAdd from "@/views/culture/CultureAdd.vue";
import CultureEdit from "@/views/culture/CultureEdit.vue";

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
    }, {
        path: '/home',
        name: 'Home',
        component: HomePage,
        meta: {requiresAuth: true},  // 需要登录才能访问
    }, {
        path: '/food',
        name: 'Food',
        meta: {requiresAuth: true},  // 需要登录才能访问
        children: [
            {
                path: 'detail/:id', // :id 是动态参数
                name: 'FoodDetail',
                component: FoodDetail,
            }, {
                path: 'add',
                name: 'FoodAdd',
                component: FoodAdd,
            }, {
                path: 'edit/:id',
                name: 'FoodEdit',
                component: FoodEdit,
            }
        ]
    }, {
        path: '/scenery',
        name: 'Scenery',
        meta: {requiresAuth: true},  // 需要登录才能访问
        children: [
            {
                path: 'detail/:id', // :id 是动态参数
                name: 'SceneryDetail',
                component: SceneryDetail,
            }, {
                path: 'add',
                name: 'SceneryAdd',
                component: SceneryAdd,
            }, {
                path: 'edit/:id',
                name: 'SceneryEdit',
                component: SceneryEdit,
            }
        ]
    }, {
        path: '/culture',
        name: 'Culture',
        meta: {requiresAuth: true},  // 需要登录才能访问
        children: [
            {
                path: 'detail/:id', // :id 是动态参数
                name: 'CultureDetail',
                component: CultureDetail,
            }, {
                path: 'add',
                name: 'CultureAdd',
                component: CultureAdd,
            }, {
                path: 'edit/:id',
                name: 'CultureEdit',
                component: CultureEdit,
            }
        ]
    }, {
        path: '/user-info',
        name: 'user-info',
        component: UserInfoView,
    }, {
        path: '/not-found',
        name: 'not-found',
        component: () => import('@/views/NotFoundView.vue'),
    }, {
        path: '/:pathMatch(.*)*',
        redirect: '/not-found',  // 未找到页面时重定向到 /not-found
    }
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
