<template>
  <div id="app" class="container is-max-desktop pt-5">
    <section>
      <div class="hero-body">
        <div class="container has-text-centered">
          <h1 class="title ">
            로그인
          </h1>
          <h2 class="subtitle centered">
            오늘도 환영합니다.
          </h2>
        </div>
      </div>
    </section>
    <section>
      <div class="container is-max-desktop">
        <div class="columns is-mobile">
          <div class="column is-half is-offset-one-quarter">
            <b-field label="ID" type="" message="" align="left">
              <b-input
                v-model="id"
                placeholder="아이디"
                maxlength="30"
                size="is-medium"
              ></b-input>
            </b-field>

            <b-field label="Password" type="" message="" align="left">
              <b-input
                v-model="pw"
                type="password"
                placeholder="비밀번호"
                maxlength="30"
                size="is-medium"
              >
              </b-input>
            </b-field>
            <center>
              <b-button
                type="submit"
                outlined
                id="btn"
                v-bind:disabled="!isIdValid || !pw"
                position="is-centered"
                size="is-medium"
                >로그인</b-button
              >
            </center>
            <br /><br />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { validateEmail } from "@/utils/validation";
import { signinUser } from "@/api/auth";

export default {
  data() {
    return {
      //form values
      id: "",
      pw: ""
    };
  },
  computed: {
    isIdValid() {
      return validateEmail(this.id); //id가 이메일 형식이 맞는지 체크
    }
  },
  methods: {
    async submitForm() {
      console.log("sub");
      alert(1);
      try {
        // 비즈니스 로직
        const userData = {
          id: this.id,
          pw: this.pw
        };
        const data = await signinUser(userData);

        if (data.data.desturl == "admin") {
          alert("관리자 admin 환영합니다.");
        } else if (data.data.desturl == "waittingroom") {
          alert("환영합니다!");
        } else {
          alert("잘못된 정보를 입력하셨거나 이미 로그인중입니다.");
        }

        this.$store.commit("setToken", data.data.token);
        this.$store.commit("setId", data.data.memberinfo.id);

        this.$session.set("userinfo", data.data.memberinfo); //브라우저 localstorage에 멤버정보 저장
        this.$router.push("../" + data.data.desturl);
      } catch (error) {
        // 에러 핸들링할 코드
        console.log(error.response.data);
        this.$router.push("../Signin");
        alert("로그인 중 문제가 발생했습니다.");
      } finally {
        this.initForm();
      }
    },
    initForm() {
      this.username = "";
      this.password = "";
    }
  }
};
</script>

<style>
.btn {
  color: white;
}
</style>
