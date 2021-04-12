import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
// import actions from "./actions.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    /* User */
    // userName: sessionStorage.getItem("userName")
    //   ? JSON.parse(sessionStorage.getItem("userName"))
    //   : null,
    userName: null,
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
