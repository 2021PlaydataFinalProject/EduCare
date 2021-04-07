import Vue from "vue";
import VueRouter from "vue-router";
// import Home from "../views/Home.vue";

Vue.use(VueRouter);

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
    name: "profile",
    component: () =>
      import(/* webpackChunkName: "profile" */ "../views/Profile.vue")
  },
  // {
  //   // Document title tag
  //   // We combine it with defaultDocumentTitle set in `src/main.js` on router.afterEach hook
  //   meta: {
  //     title: "Dashboard"
  //   },
  //   path: "/",
  //   name: "home",
  //   component: Home
  // },
  {
    meta: {
      title: "Tables"
    },
    path: "/tables",
    name: "tables",
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
    component: () => import("../views/TestGuide.vue")
  },
  {
    meta: {
      title: "CompleteTest"
    },
    path: "/completetest",
    name: "CompleteTest",
    component: () => import("../views/CompleteTest.vue")
  },
  {
    meta: {
      title: "StudentTestList"
    },
    path: "/student",
    name: "StudentTestList",
    component: () => import("../views/StudentTestList.vue")
  },
  {
    meta: {
      title: "StudentTest"
    },
    path: "/studenttest",
    name: "StudentTest",
    component: () => import("../views/StudentTest.vue")
  },
  {
    meta: {
      title: "ManageTest"
    },
    path: "/managetest",
    name: "ManageTest",
    component: () => import("../views/ManageTest.vue")
  },
  {
    meta: {
      title: "ManageStudent"
    },
    path: "/managestudent",
    name: "ManageStudent",
    component: () => import("../views/ManageStudent.vue")
  },
  {
    meta: {
      title: "AddTest"
    },
    path: "/addtest",
    name: "AddTest",
    component: () => import("../components/AddTest.vue")
  },
  {
    meta: {
      title: "Forms"
    },
    path: "/forms",
    name: "forms",
    component: () =>
      import(/* webpackChunkName: "forms" */ "../views/Forms.vue")
  },
  {
    meta: {
      title: "New client"
    },
    path: "/client/new",
    name: "client.new",
    component: () =>
      import(/* webpackChunkName: "client-form" */ "../views/ClientForm.vue")
  },
  {
    meta: {
      title: "Edit client"
    },
    path: "/client/:id",
    name: "client.edit",
    component: () =>
      import(/* webpackChunkName: "client-form" */ "../views/ClientForm.vue"),
    props: true
  },
  {
    meta: {
      title: "Sign In"
    },
    path: "/signin",
    name: "Sign In",
    component: () => import("../views/SignIn.vue"),
    props: true
  },
  {
    meta: {
      title: "Sign Up"
    },
    path: "/signup",
    name: "Sign Up",
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
