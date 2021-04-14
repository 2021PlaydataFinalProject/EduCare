<template>
  <nav v-show="isNavBarVisible" id="navbar-main" class="navbar is-fixed-top">
    <div class="navbar-brand">
      <a
        class="navbar-item is-hidden-desktop"
        @click.prevent="menuToggleMobile"
      >
        <b-icon :icon="menuToggleMobileIcon" />
      </a>
      <div class="navbar-item has-control no-left-space-touch">
        <div class="control">
          <input class="input" placeholder="Search everywhere..." />
        </div>
      </div>
    </div>
    <div class="navbar-brand is-right">
      <a
        class="navbar-item navbar-item-menu-toggle is-hidden-desktop"
        @click.prevent="menuNavBarToggle"
      >
        <b-icon :icon="menuNavBarToggleIcon" custom-size="default" />
      </a>
    </div>
    <div
      class="navbar-menu fadeIn animated faster"
      :class="{ 'is-active': isMenuNavBarActive }"
    >
      <div class="navbar-end">
        <!-- <nav-bar-menu class="has-divider">
          <b-icon icon="menu" custom-size="default" />
          <span>Sample Menu</span>
          <div slot="dropdown" class="navbar-dropdown">
            <router-link
              to="/profile"
              class="navbar-item"
              exact-active-class="is-active"
            >
              <b-icon icon="account" custom-size="default" />
              <span>My Profile</span>
            </router-link>
            <a class="navbar-item">
              <b-icon icon="settings" custom-size="default" />
              <span>Settings</span>
            </a>
            <a class="navbar-item">
              <b-icon icon="email" custom-size="default" />
              <span>Messages</span>
            </a>
            <hr class="navbar-divider" />
            <a class="navbar-item">
              <b-icon icon="logout" custom-size="default" />
              <span>Log Out</span>
            </a>
          </div>
        </nav-bar-menu> -->
        <nav-bar-menu class="has-divider has-user-avatar">
          <user-avatar />
          <div class="is-user-name">
            <!-- 여기에 user role을 데이터 싱크 맞추어서 적으면 됨 
            {{userRole}} 이런식으로 -->
            <template v-if="authorization != null">
              <span>{{ userName }}</span>
            </template>
            <template v-else>
              <span>로그인</span>
            </template>
          </div>
          <div slot="dropdown" class="navbar-dropdown">
            <router-link
              to="/profile"
              class="navbar-item"
              exact-active-class="is-active"
            >
              <b-icon icon="account" custom-size="default"></b-icon>
              <template v-if="authorization != null">
                <span>마이 페이지</span>
              </template>
              <template v-else>
                <router-link to="/signin">로그인</router-link>
              </template>
            </router-link>
            <!-- <a class="navbar-item">
              <b-icon icon="settings" custom-size="default"></b-icon>
              <span>Settings</span>
            </a>
            <a class="navbar-item">
              <b-icon icon="email" custom-size="default"></b-icon>
              <span>Messages</span>
            </a> -->
            <hr class="navbar-divider" />
            <a class="navbar-item">
              <b-icon icon="logout" custom-size="default"></b-icon>
              <template v-if="authorization != null">
                <span @click="logout()">로그아웃</span>
              </template>
              <template v-else>
                <router-link to="/signup">회원가입</router-link>
              </template>
            </a>
          </div>
        </nav-bar-menu>
        <!-- <a
          class="navbar-item has-divider is-desktop-icon-only"
          title="Dark mode"
          @click="darkModeToggle"
        >
          <b-icon :icon="darkModeToggleIcon" custom-size="default" />
          <span>Dark mode</span>
        </a>
        <a
          href="https://justboil.me/bulma-admin-template/null"
          class="navbar-item has-divider is-desktop-icon-only"
          title="About"
        >
          <b-icon icon="help-circle-outline" custom-size="default" />
          <span>About</span>
        </a> -->
        <a
          class="navbar-item is-desktop-icon-only"
          title="Log out"
          @click="logout"
        >
        </a>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapState } from "vuex";
import NavBarMenu from "@/components/NavBarMenu";
import UserAvatar from "@/components/UserAvatar";
import axios from "axios";
export default {
  name: "NavBar",
  components: {
    UserAvatar,
    NavBarMenu
  },
  data() {
    return {
      isMenuNavBarActive: false,
      authorization: sessionStorage.getItem("Authorization")
    };
  },
  computed: {
    menuNavBarToggleIcon() {
      return this.isMenuNavBarActive ? "close" : "dots-vertical";
    },
    menuToggleMobileIcon() {
      return this.isAsideMobileExpanded ? "backburger" : "forwardburger";
    },
    darkModeToggleIcon() {
      return this.isDarkModeActive ? "white-balance-sunny" : "weather-night";
    },
    ...mapState([
      "isNavBarVisible",
      "isAsideMobileExpanded",
      "isDarkModeActive",
      "userName"
    ])
  },
  mounted() {
    this.$router.afterEach(() => {
      this.isMenuNavBarActive = false;
    });
  },
  methods: {
    menuToggleMobile() {
      this.$store.commit("asideMobileStateToggle");
    },
    menuNavBarToggle() {
      this.isMenuNavBarActive = !this.isMenuNavBarActive;
    },
    darkModeToggle() {
      this.$store.commit("darkModeToggle");
    },
    logout() {
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      instance
        .post("http://localhost:8000/user/logout")
        .then(() => {
          alert("로그아웃 성공");
          sessionStorage.removeItem("Authorization");
          this.$router.push({ name: "Home" });
          location.reload();
        })
        .catch(error => {
          alert("로그아웃 실패");
          console.log(error);
        });
      // if (sessionStorage.getItem("user") != null) {
      //   sessionStorage.removeItem("user");
      //   this.loginUserAct(null);
      //   this.$router.push({ name: "Home" });
      // } else {
      //   alert("로그인을 먼저 해주세요");
      //   this.$router.push({ name: "Sign In" });
      // }
      // this.$buefy.snackbar.open({
      //   message: "Log out clicked",
      //   queue: false
      // });
    }
  }
};
</script>
© 2021 GitHub, Inc.
