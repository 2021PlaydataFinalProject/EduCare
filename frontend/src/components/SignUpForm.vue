<template>
  <div class="container">
    <div class="container pt-6 pb-6 pl-4 pr-4">
      <div class="box">
        <h2 class="subtitle is-5">회원가입</h2>
        <form @submit.prevent="submitForm()" class="form">
          <!-- <div> -->
          <!-- <label for="memberagree">개인 정보를 전송합니다.</label>
            동의하려면 체크하세요. -->
          <!-- <input
              v-model="memberagree"
              id="memberagree"
              type="checkbox"
              name="memberagree"
              value="memberagree"
            /> -->
          <!-- </div> -->
          <br />
          <b-field label="ID" type="" message="" align="left">
            <b-input
              v-model="username"
              placeholder="xxxx@xxxx.xxx"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field>
          <br />
          <b-field label="비밀번호" type="" message="" align="left">
            <b-input
              v-model="password"
              type="password"
              placeholder="비밀번호"
              maxlength="30"
              size="is-medium"
              password-reveal
            >
            </b-input>
          </b-field>
          <br />
          <b-field label="이름" type="" message="" align="left">
            <b-input
              v-model="userRealname"
              placeholder="이름"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field>
          <br />
          <b-field label="핸드폰 번호" type="" align="left">
            <b-input
              v-model="phoneNumber"
              placeholder="01x-xxxx-xxxx"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field>
          <br />
          <!-- 파일 선택 및 업로드 -->
          <b-field label="프로필 사진" align="left">
            <input
              type="file"
              id="file"
              ref="file"
              v-on:change="handleFileUpload()"
            />
          </b-field>
          <b-field label="권한 설정" type="" message="" align="left">
            <div class="block">
              <b-radio
                v-model="role"
                name="instructor"
                native-value="instructor"
              >
                강사
              </b-radio>
              <b-radio v-model="role" name="student" native-value="student">
                학생
              </b-radio>
            </div>
          </b-field>
          <br />
          <button class="btn btn-primary btn-sm">
            회원 가입
          </button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
// import { signupUser } from "@/api/auth";
import { validateEmail } from "@/utils/validation";
import { validatePhoneNumber } from "@/utils/validation";
import axios from "axios";
export default {
  name: "SignUpForm",
  data() {
    return {
      //form values
      username: "",
      password: "",
      userRealname: "",
      phoneNumber: "",
      userImage: "",
      role: "",
      memberagree: false
    };
  },
  computed: {
    isIdValid() {
      return validateEmail(this.username); //id가 이메일 형식이 맞는지 체크
    },
    isPhoneNumberValid() {
      return validatePhoneNumber(this.phoneNumber); //phonenumber가 핸드폰 번호 형식이 맞는지 체크
    }
  },
  methods: {
    submitForm() {
      let formData = new FormData();
      formData.append("username", this.username);
      formData.append("password", this.password);
      formData.append("userRealname", this.userRealname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("file", this.file);
      formData.append("role", this.role);
      axios
        .post("http://localhost:8000/user/signup", formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(response => {
          alert("회원가입성공");
          console.log(response.data);
          this.$router.push({ name: "Service" });
        })
        .catch(error => {
          alert("회원가입실패");
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    // async submitForm() {
    //   try {
    //   let formData = new FormData();
    // formData.append("userId", this.userid);
    // formData.append("password", this.password);
    // formData.append("name", this.name);
    // formData.append("age", this.age);
    // formData.append("email", this.email);
    // formData.append("phoneNum", this.phonenum);
    // formData.append("address", this.address);
    // formData.append("position", this.position);
    // formData.append("stacklist", this.stacklist);
    // formData.append("file", this.file);
    // {headers: {
    //     "Content-Type": "multipart/form-data"
    //   }}
    // 비즈니스 로직
    //     const userData = {
    //       username: this.username,
    //       password: this.password,
    //       userRealName: this.userRealName,
    //       phoneNumber: this.phoneNumber,
    //       file: this.userImage,
    //       role: this.role
    //     };
    //     const data = await signupUser(userData);
    //     if (data.data == "Signin") {
    //       this.logMessage = "회원가입성공";
    //       alert("회원가입성공");
    //     } else {
    //       alert("회원가입실패");
    //     }
    //     this.$router.push("../" + data.data);
    //   } catch (error) {
    //     // 에러 핸들링할 코드
    //     alert("회원가입 중 에러가 발생했습니다.");
    //     console.log(error);
    //   } finally {
    //     this.initForm();
    //   }
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
    }
  }
};
</script>
<style></style>
