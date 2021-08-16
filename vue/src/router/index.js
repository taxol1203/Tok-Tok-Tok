import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/user/Login.vue";
import Signup from "../views/user/Signup.vue";
import ChatArea from "../views/chat/ChatArea.vue";
import QnaArea from "../views/qna/QnaArea.vue";
import UserChat from "../views/userchat/UserMain.vue";
import UserSignup from "../views/userchat/UserSignup.vue";
import UserLogin from "../views/userchat/UserLogin.vue";
import NotFound from "../views/NotFound.vue";

const routes = [
  {
    path: "/",
    component: Login,
    meta: {
      authRequired: true,
    },
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
    meta: {
      authRequired: true,
    },
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      authRequired: true,
    },
  },
  {
    path: "/admin",
    name: "Admin",
    component: () => import("../views/Admin.vue"),
    children: [
      {
        path: "",
        name: "comein",
        component: ChatArea,
      },
      {
        path: "chat",
        component: ChatArea,
        name: "chat",
      },
      {
        path: "qna",
        component: QnaArea,
        name: "qna",
      },
      {
        path: "user",
        component: () => import("../views/user/User.vue"),
        name: "user",
      },
    ],
  },
  {
    path: "/userchat",
    name: "UserChat",
    component: UserChat,
    meta: {
      authRequired: false,
    },
  },
  {
    path: "/usersignup",
    name: "UserSignup",
    component: UserSignup,
    meta: {
      authRequired: true,
    },
  },
  {
    path: "/userlogin",
    name: "UserLogin",
    component: UserLogin,
    meta: {
      authRequired: false,
    },
  },
  {
    path: "/:catchAll(.*)",
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// 로그인 권한 부분. 잠시 꺼둠
// router.beforeEach(async (to, from, next) => {
//   if (
//     to.matched.some((routeInfo) => {
//       return routeInfo.meta.authRequired;
//     })
//   ) {
//     return next();
//   }
//   if (localStorage.getItem("jwt") === null) {
//     return next({ name: "Login" });
//   } 
//   return next();
// });

export default router;
