import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
// import actions from "./actions.js";
import { getUserIdFromSession, getUserRoleFromSession } from "../utils/session";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    /* User */
    // userName: sessionStorage.getItem("userName")
    //   ? JSON.parse(sessionStorage.getItem("userName"))
    //   : null,
    // Bearer eyJjb20iOiJFRFVDQVJFIiwidHlwIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJhYWFAbmF2ZXIuY29tIiwiYXV0aCI6IlJPTEVfU1RVREVOVCIsImV4cCI6MTYxODMxOTEwNX0.qZ7cmn3tIDtoHpja_ynSDyOerW9-HG1SzXALxpEdJ9-KY-QNhJ_CqSvSu3HDWQ_sL7Jap87sK1WhwUqlCOcEFQ

    userName: getUserIdFromSession() || "",
    userRole: getUserRoleFromSession() || "",
    userEmail: null,
    userAvatar: null,
    // sessionStorage.getItem('LoginUser')
    //     ? JSON.parse(sessionStorage.getItem("LoginUser"))
    //     : null
    /* NavBar */
    isNavBarVisible: true,

    /* FooterBar */
    isFooterBarVisible: true,

    /* Aside */
    isAsideVisible: true,
    isAsideMobileExpanded: false,

    /* Dark mode */
    isDarkModeActive: false
  },
  mutations
});
