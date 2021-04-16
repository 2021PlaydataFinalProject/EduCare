<template>
  <card-component title="프로필 수정" icon="account-circle">
    <form @submit.prevent="submit">
      <b-field horizontal label="사진">
        <input
          type="file"
          id="file"
          ref="file"
          v-on:change="handleFileUpload()"
        />
      </b-field>
      <hr />
      <b-field horizontal label="이름" message="필수 입력 사항">
        <b-input v-model="userRealname" name="name" required />
      </b-field>
      <b-field horizontal label="이메일" message="필수 입력 사항">
        <b-input v-model="userName" name="email" type="email" required />
      </b-field>
      <b-field horizontal label="번호" message="필수 입력 사항">
        <b-input
          v-model="phoneNumber"
          name="phonenumber"
          type="text"
          required
        />
      </b-field>
      <b-field
        horizontal
        label="비밀번호"
        message="새로운 비밀번호를 입력하세요."
      >
        <b-input name="password" type="password" v-model="password" required />
      </b-field>
      <hr />
      <b-field horizontal>
        <div class="control">
          <button
            type="submit"
            class="button is-primary"
            :class="{ 'is-loading': isLoading }"
            @click="updateProfileForm()"
          >
            수정완료
          </button>
        </div>
      </b-field>
    </form>
  </card-component>
</template>

<script>
// import { mapState } from "vuex";
// import FilePicker from "@/components/FilePicker";
import CardComponent from "@/components/CardComponent";
import { fetchUserInfo } from "../api/auth.js";
import axios from "axios";
import { validateEmail } from "@/utils/validation";
import { validatePhoneNumber } from "@/utils/validation";

export default {
  name: "ProfileUpdateForm",
  components: {
    CardComponent
  },
  data: () => ({
    // userName: this.$store.state.userName,
    // userRealname: this.$store.state.userRealname,
    // phoneNumber: this.$store.state.phoneNumber,
    // role: this.$store.state.role
    username: "",
    password: "",
    userRealname: "",
    phoneNumber: "",
    userImage: "",
    role: ""
  }),
  // this.userImage =
  created() {
    fetchUserInfo()
      .then(response => {
        // alert(response.data.username);
        // alert(response.data.userRealname);
        // alert(response.data.phoneNumber);
        // alert(response.data.role);
        this.$store.state.userName = response.data.username;
        this.$store.state.userRealname = response.data.userRealname;
        this.$store.state.phoneNumber = response.data.phoneNumber;
        this.$store.state.role = response.data.role;
      })
      //this.$store.state.userInfo = response.data
      //this.$store.state.userInfo = response.data.phoneNumber
      .catch();
  },
  computed: {
    isIdValid() {
      return validateEmail(this.username); //id가 이메일 형식이 맞는지 체크
    },
    isPhoneNumberValid() {
      return validatePhoneNumber(this.phoneNumber); //phonenumber가 핸드폰 번호 형식이 맞는지 체크
    }
  },
  // computed: {
  //   ...mapState([
  //     "userName",
  //     "userImage",
  //     "userRealname",
  //     "phoneNumber",
  //     "role"
  //   ])
  // },
  mounted() {
    this.form.name = this.userName;
    this.form.realname = this.userRealname;
  },
  methods: {
    updateProfileForm() {
      let formData = new FormData();
      formData.append("username", this.userName);
      formData.append("password", this.password);
      formData.append("userRealname", this.userRealname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("file", this.file);
      formData.append("role", this.role);
      axios
        .put("http://localhost:8000/user/update", formData, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization"),
            "Content-Type": "multipart/form-data",
            "Access-Control-Allow-Origin": "*"
          }
        })
        .then(response => {
          alert("회원 정보 수정 성공");
          console.log(response.data);
          this.$router.push({ name: "Home" });
        })
        .catch(error => {
          alert("회원 정보 수정실패");
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    // const addProfileData = {
    //   userName: this.userName,
    //   password: this.password,
    //   userRealname: this.userRealname,
    //   phoneNumber: this.phoneNumber,
    //   userImage: this.file,
    //   role: this.role
    // };
    // alert(JSON.stringify(addProfileData));
    // axios
    //   .put("http://localhost:8000/user/update", addProfileData, {
    //     headers: {
    //       Authorization: sessionStorage.getItem("Authorization"),
    //       "Content-Type": "multipart/form-data",
    //       "Access-Control-Allow-Origin": "*"
    //     }
    //   })
    //   .then(response => {
    //     // this.test = response.data;
    //     console.log(response);
    //     this.success();

    //     this.getTestForm();
    //   })
    //   .catch(e => {
    //     this.danger();
    //     console.log(e);
    //     this.errors.push(e);
    //   });
    // },
    initForm() {
      this.username = "";
      this.password = "";
      this.userRealName = "";
      this.phoneNumber = "";
      this.userImage = "";
      this.role = "";
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    },
    created() {
      fetchUserInfo()
        .then(response => (this.$store.state.userInfo = response.data))
        .catch();
    }
    // fetch_Image() {
    //   const username = getUserIdFromSession();
    //   axios
    //     .get("http://localhost:8000/user/myinfo", username)
    //     .then(response => {
    //       alert("이미지 조회 성공");
    //       this.userImage = response.data.userImage;
    //       return this.userImage;
    //     })
    //     .catch(() => {
    //       alert("이미지 조회 실패");
    //     });
    // }

    // submit() {
    //   this.isLoading = true;
    //   setTimeout(() => {
    //     this.isLoading = false;
    //     this.$store.commit("user", this.form);
    //     this.$buefy.snackbar.open({
    //       message: "Updated",
    //       queue: false
    //     });
    //   }, 500);
    // }
    // watch: {
    //   userName(newValue) {
    //     this.form.name = newValue;
    //   },
    //   userEmail(newValue) {
    //     this.form.email = newValue;
    //   }
    // }
  }
};
</script>
