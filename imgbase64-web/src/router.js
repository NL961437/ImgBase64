import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/components/Home.vue";
import About from "@/components/About.vue";

export const router = createRouter({
  history: createWebHistory('/img-encode'),
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      component: Home
    },
    {
      path: '/about',
      component: About
    }
  ]
});
