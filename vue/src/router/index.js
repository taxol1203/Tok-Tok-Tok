import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/user/Login.vue";
import Signup from "../views/user/Signup.vue";
import ChatArea from "../views/chat/ChatArea.vue";
import QnaArea from "../views/qna/QnaArea.vue";

const routes = [
  {
    path: "/",
    component: Login,
    meta: {
      authRequired: true
    }
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
    meta: {
      authRequired: true
    }
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      authRequired: true
    }
  },
  {
    path: "/secret",
    name: "Secret",
    component: () => import(/* webpackChunkName: "about" */ "../views/Secret.vue"),
  },
  {
    path: "/admin",
    name: "Admin",
    component: () => import("../views/Admin.vue"),
    children: [
      {
        path: '',
        name: 'comein',
        component: ChatArea,
      },
      {
        path: 'chat',
        component: ChatArea,
        name: "chat"
      },
      {
        path: 'qna',
        component: QnaArea,
        name: 'qna'
      },
    ]
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach(async (to, from, next) => {
  if (to.matched.some((routeInfo) => { return routeInfo.meta.authRequired}) ) {
    return next();
  }
  if (localStorage.getItem('jwt') === null) {
    alert('로그인해주세요');
    return next({name: 'Login'})
  }
  return next();
})

export default router;
