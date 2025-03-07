import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import PageViewer from "@/components/PageViewer.vue";
import Calculator from "@/components/Calculator.vue";
import Contact from "@/components/Contact.vue";
import Login from "@/components/Login.vue";
import { authState } from "@/eventBus";

const isTokenValid = () => {
    const token = localStorage.getItem('jwtToken');
    if (!token) return false;

    try {
        const payload = JSON.parse(atob(token.split('.')[1])); // Decode JWT payload
        const expiry = payload.exp * 1000; // Convert seconds to milliseconds
        return Date.now() < expiry; // Check if token is still valid
    } catch (error) {
        console.error('Invalid token format:', error);
        return false;
    }
};

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        { path: '/login', component: Login },
        { path: '/:index?', component: PageViewer },
        { path: '/calculator', component: Calculator },
        { path: '/contact', component: Contact },
        { path: '/', redirect: '/login'}
    ]
});

router.beforeEach((to, from, next) => {
    const isAuthnticated = isTokenValid();
    if (to.path !== '/login' && !isAuthnticated) {
        next('/login');
    } else {
        next();
    }
});

export default router;
