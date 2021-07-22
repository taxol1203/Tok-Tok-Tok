import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/user/Login.vue";
import ChatArea from "../views/chat/ChatArea.vue";
import QnaArea from "../views/qna/QnaArea.vue";

const routes = [
  {
    path: "/",
    component: Login,
  },
  {
    path: "/signup",
    name: "Signup",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ "../views/user/Signup.vue"),
  },
  {
    path: "/login",
    name: "Login",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Login,
  },
  {
    path: "/secret",
    name: "Secret",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ "../views/Secret.vue"),
  },
  {
    path: "/admin",
    name: "Admin",
    component: () => import("../views/Admin.vue"),
    children: [
      {
        path: 'chat',
        component: ChatArea,
      },
      {
        path: 'qna',
        component: QnaArea,
      },
    ]
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
