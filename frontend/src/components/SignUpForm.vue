<template>
  <div class="container">
    <div class="container pt-6 pb-6 pl-4 pr-4">
      <div class="box">
        <h2 class="subtitle is-5">개인 정보 동의</h2>
        <form @submit.prevent="submitForm()" class="form">
          <div>
            <label for="memberagree">개인 정보를 전송합니다.</label>
            동의하려면 체크하세요.
            <input
              v-model="memberagree"
              id="memberagree"
              type="checkbox"
              name="memberagree"
              value="memberagree"
            />
          </div>
          <br />
          <b-field label="ID" type="" message="" align="left">
            <b-input
              v-model="id"
              placeholder="xxxx@xxxx.xxx"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field>
          <br />
          <b-field label="비밀번호" type="" message="" align="left">
            <b-input
              v-model="pw"
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
              v-model="name"
              placeholder="이름"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field>
          <br />
          <b-field label="핸드폰 번호" type="" align="left">
            <b-input
              v-model="phonenumber"
              placeholder="01x-xxxx-xxxx"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field>
          <br />
          <!-- <b-field label="D" type="" message="" align="left">
            <b-input
              v-model="birth"
              placeholder="생년월일"
              maxlength="30"
              size="is-medium"
            ></b-input>
          </b-field> -->
          <b-field class="file">
            <b-upload v-model="file" @input="upload" :accept="accept">
              <a class="button is-primary">
                <b-icon icon="upload" custom-size="default"></b-icon>
                <span>{{ buttonLabel }}</span>
              </a>
            </b-upload>
            <span class="file-name" v-if="file">{{ file.name }}</span>
          </b-field>
          <br />
          <!-- <div>
          <label for="gender">남성</label>
          <input
            v-model="gender"
            id="male"
            type="radio"
            name="gender"
            value="male"
          />
        </div>
        <div>
          <label for="gender">여성</label>
          <input
            v-model="gender"
            id="female"
            type="radio"
            name="gender"
            value="female"
          />
        </div> -->
          <b-field label="역할" type="" message="" align="left">
            <div class="block">
              <b-radio v-model="ROLE" name="student" native-value="student">
                학생
              </b-radio>
              <b-radio
                v-model="ROLE"
                name="instructor"
                native-value="instructor"
              >
                강사
              </b-radio>
            </div>
          </b-field>
          <br />
          <button
            class="btn btn-primary btn-sm"
            v-bind:disabled="
              !isIdValid ||
                !pw ||
                !name ||
                !nickname ||
                !isPhoneNumberValid ||
                !isBirthValid ||
                !gender ||
                !memberagree
            "
            type="submit"
          >
            회원 가입
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { signupUser } from "@/api/auth";
import { validateEmail } from "@/utils/validation";
import { validatePhoneNumber } from "@/utils/validation";
import { validateBirth } from "@/utils/validation";

export default {
  data() {
    return {
      //form values
      id: "",
      pw: "",
      name: "",
      nickname: "",
      phonenumber: "",
      birth: "",
      gender: "",
      memberagree: false
    };
  },
  computed: {
    isIdValid() {
      return validateEmail(this.id); //id가 이메일 형식이 맞는지 체크
    },
    isPhoneNumberValid() {
      return validatePhoneNumber(this.phonenumber); //phonenumber가 핸드폰 번호 형식이 맞는지 체크
    },
    isBirthValid() {
      return validateBirth(this.birth); //birth가 YYYY-MM-DD 형식에 맞는지 체크
    }
  },
  methods: {
    async submitForm() {
      try {
        // 비즈니스 로직
        const userData = {
          id: this.id,
          pw: this.pw,
          name: this.name,
          nickname: this.nickname,
          phonenumber: this.phonenumber,
          birth: this.birth,
          gender: this.gender
        };
        const data = await signupUser(userData);

        if (data.data == "Signin") {
          this.logMessage = "회원가입성공";
          alert("회원가입성공");
        } else {
          alert("회원가입실패");
        }
        this.$router.push("../" + data.data);
      } catch (error) {
        // 에러 핸들링할 코드
        alert("회원가입 중 에러가 발생했습니다.");
        console.log(error);
      } finally {
        this.initForm();
      }
    },
    initForm() {
      this.username = "";
      this.password = "";
      this.name = "";
      this.nickname = "";
      this.phonenumber = "";
      this.birth = "";
      this.gender = "";
    }
  }
};
</script>

<style></style>
