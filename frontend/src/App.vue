<template>
  <div id="app">
    <nav-bar />
    <aside-menu :menu="menu" @menu-click="menuClick" />
    <router-view />
    <footer-bar />
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from "@/components/NavBar";
import AsideMenu from "@/components/AsideMenu";
import FooterBar from "@/components/FooterBar";

export default {
  name: "home",
  components: {
    FooterBar,
    AsideMenu,
    NavBar
  },
  computed: {
    menu() {
      return [
        "프로필",
        [
          {
            to: "/profile",
            icon: "account-circle",
            label: "마이 프로필"
          }
        ],
        "메뉴",
        [
          // {
          //   to: "/forms",
          //   label: "Forms",
          //   icon: "square-edit-outline"
          // },
          // {
          //   to: "/profile",
          //   label: "Profile",
          //   icon: "account-circle"
          // },
          {
            label: "시험",
            //submenu가 강사와 어드민 학생에 따라 다르게 나와야함
            subLabel: "Submenus Example",
            icon: "view-list",
            menu: [
              {
                href: "/#/student",
                label: "학생 시험"
              },
              {
                href: "/#/managetest",
                label: "강사 시험"
              }
            ]
          }
        ],
        "HOME",
        [
          {
            href: "/#/service",
            label: "서비스 소개",
            icon: "help-circle"
          }
        ]
      ];
    }
  },
  created() {
    this.$store.commit("user", {
      name: "John Doe",
      email: "john@example.com",
      avatar: "https://avatars.dicebear.com/v2/gridy/John-Doe.svg"
    });
  },
  methods: {
    menuClick(item) {
      if (item.action && item.action === "dark-mode-toggle") {
        this.$store.commit("darkModeToggle");
      }
    }
  }
};
</script>
