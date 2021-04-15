<template>
  <div class="is-user-avatar">
    <img :src="newUserAvatar" :alt="userName" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
import { getUserIdFromSession } from "../utils/session";

export default {
  name: "UserAvatar",
  props: {
    avatar: {
      type: String,
      default: null
    }
  },
  data: () => ({
    loginUser: {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      userImage: this.$store.state.userImage
    }
  }),
  computed: {
    newUserAvatar() {
      if (this.avatar) {
        return this.avatar;
      }

      if (this.userAvatar) {
        return this.userAvatar;
      }

      // let name = "somename";
      if (this.userName) {
        alert(JSON.stringify(this.userImage));
        // 로그인 했을 경우../../
        // `http://localhost:8000/userimg/${this.userImage}`
        return `http://localhost:8000/userimg/default.png`;
      } else {
        // 로그인 안했을 경우
        return `http://localhost:8000/userimg/default.png`;
      }
      // if (this.userName) {
      //   name = this.userName.replace(/[^a-z0-9]+/i, "");
      // }
      // https://avatars.dicebear.com/v2/human/${name}.svg?options[mood][]=happy
    },
    ...mapState(["userAvatar", "userName", "userRole", "imgURL", "userImage"])
  },
  methods: {
    // 유저 부분
    // http://localhost:8000/userimg/default.png
    // http://localhost:8000/user/myinfo
    // `http://localhost:8000/userimg/${userImage}`
    fetch_Image() {
      const username = getUserIdFromSession();
      axios
        .get("http://localhost:8000/user/myinfo", username)
        .then(response => {
          alert("이미지 조회 성공");
          this.userImage = response.data.userImage;
          return this.userImage;
        })
        .catch(() => {
          alert("이미지 조회 실패");
        });
    }
  }
};
</script>
