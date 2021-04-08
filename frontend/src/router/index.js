import Vue from "vue";
import VueRouter from "vue-router";
// import Home from "../views/Home.vue";

Vue.use(VueRouter);

// 라우터 가드
// 로그인 유저 거절
const rejectAuthUser = (to, from, next) => {
  if (sessionStorage.getItem("user") != null) {
    next("/");
  } else {
    next();
  }
};
// 비 로그인 유저 거절
const onlyAuthUser = (to, from, next) => {
  if (sessionStorage.getItem("user") == null) {
    next("/signin");
  } else {
    next();
  }
};

const routes = [
  {
    meta: {
      title: "Home"
    },
    path: "/",
    name: "Home",
    component: () => import("../views/Home.vue")
  },
  {
    meta: {
      title: "Service"
    },
    path: "/service",
    name: "Service",
    component: () => import("../views/Service.vue")
  },
  {
    meta: {
      title: "Profile"
    },
    path: "/profile",
    name: "Profile",
    beforeEnter: onlyAuthUser,
    component: () =>
      import(/* webpackChunkName: "profile" */ "../views/Profile.vue")
  },
  {
    meta: {
      title: "Tables"
    },
    path: "/tables",
    name: "tables",
    beforeEnter: onlyAuthUser,
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "tables" */ "../views/Tables.vue")
  },
  {
    meta: {
      title: "TestGuide"
    },
    path: "/testguide",
    name: "TestGuide",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/TestGuide.vue")
  },
  {
    meta: {
      title: "CompleteTest"
    },
    path: "/completetest",
    name: "CompleteTest",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/CompleteTest.vue")
  },
  {
    meta: {
      title: "StudentTestList"
    },
    path: "/student",
    name: "StudentTestList",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/StudentTestList.vue")
  },
  {
    meta: {
      title: "StudentTest"
    },
    path: "/studenttest",
    name: "StudentTest",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/StudentTest.vue")
  },
  {
    meta: {
      title: "ManageTest"
    },
    path: "/managetest",
    name: "ManageTest",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/ManageTest.vue")
  },
  {
    meta: {
      title: "ModifyTest"
    },
    path: "/modifytest",
    name: "ModifyTest",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/ModifyTest.vue")
  },
  {
    meta: {
      title: "TestSuperVision"
    },
    path: "/testsupervision",
    name: "TestSuperVision",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/TestSuperVision.vue")
  },
  {
    meta: {
      title: "ManageStudent"
    },
    path: "/managestudent",
    name: "ManageStudent",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/ManageStudent.vue")
  },
  {
    meta: {
      title: "AddTest"
    },
    path: "/addtest",
    name: "AddTest",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/AddTest.vue")
  },
  {
    meta: {
      title: "InstructorTest"
    },
    path: "/instructor",
    name: "InstructorTest",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/InstructorTest.vue")
  },
  {
    meta: {
      title: "Sign In"
    },
    path: "/signin",
    name: "Sign In",
    beforeEnter: rejectAuthUser,
    component: () => import("../views/SignIn.vue"),
    props: true
  },
  {
    meta: {
      title: "Sign Up"
    },
    path: "/signup",
    name: "Sign Up",
    beforeEnter: rejectAuthUser,
    component: () => import("../views/SignUp.vue"),
    props: true
  }
];

const router = new VueRouter({
  mode: "history", // # 값 제거 /#/
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  }
});

export default router;
