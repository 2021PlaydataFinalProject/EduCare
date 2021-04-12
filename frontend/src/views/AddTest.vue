<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 만들기
      <router-link slot="right" to="/" class="button">
        Dashboard
      </router-link>
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="Forms" icon="ballot">
        <form @submit.prevent="submit">
          <b-field label="출제자 정보" horizontal>
            <b-field>
              <b-input
                icon="account"
                v-model="form.username"
                placeholder="이름"
                name="userName"
                required
              />
            </b-field>
            <b-field>
              <b-input
                icon="finish"
                v-model="form.testNum"
                placeholder="시험 번호"
                name="testNum"
                required
              />
            </b-field>
          </b-field>
          <!-- <b-field message="필수 작성 부분입니다." horizontal>
            <b-field>
              <p class="control">
                <a class="button is-static">
                  +82
                </a>
              </p>
              <b-input type="subject" v-model="form.subject" name="" expanded />
            </b-field>
          </b-field> -->
          <!-- <b-field label="시험 과목" horizontal>
            <b-select
              placeholder="시험 과목을 선택하세요."
              v-model="form.department"
              required
            >
              <option
                v-for="(department, index) in departments"
                :key="index"
                :value="department"
              >
                {{ department }}
              </option>
            </b-select>
          </b-field> -->
          <b-field label="시험명" message="과목명을 적어주세요." horizontal>
            <b-input
              placeholder="시험명 : 소제목"
              v-model="form.testName"
              name="testName"
              maxlength="150"
              required
            />
          </b-field>
          <b-field label="시험 시간 지정" horizontal>
            <b-field
              :label-position="labelPosition"
              label="시험 시작 시간"
              name="startTime"
              message="필수 작성 부분입니다."
            >
              <b-timepicker
                placeholder="시험 시작 시간을 설정해 주세요."
                icon="clock"
                name="startTime"
                :incrementMinutes="minutesGranularity"
                :incrementHours="hoursGranularity"
              >
              </b-timepicker>
            </b-field>
            <b-field
              :label-position="labelPosition"
              label="시험 종료 시간"
              name="endTime"
              message="필수 작성 부분입니다."
            >
              <b-timepicker
                placeholder="시험 종료 시간을 설정해 주세요."
                message="필수 작성 부분입니다."
                name="endTime"
                icon="clock"
                :incrementMinutes="minutesGranularity"
                :incrementHours="hoursGranularity"
              >
              </b-timepicker>
            </b-field>
          </b-field>
          <b-field
            label="시험 유의사항"
            message="당신의 시험 유의사항을 255자 이내로 작성하세요."
            horizontal
          >
            <b-input
              type="textarea"
              placeholder="해당 시험 유의사항 만들기"
              v-model="form.testGuide"
              maxlength="255"
              required
            />
          </b-field>
          <b-button native-type="submit" type="is-primary"
            >시험 만들기</b-button
          >
          <hr />
          <b-field label="문항 번호" horizontal>
            <b-field>
              <b-input
                icon="account"
                v-model="form.proNum"
                placeholder="문항 번호를 작성해 주세요."
                name="proNum"
                required
              />
            </b-field>
          </b-field>
          <b-field
            label="문제"
            name="proDes"
            message="당신의 문제를 255자 이내로 작성하세요."
            horizontal
          >
            <b-input
              type="textarea"
              placeholder="시험 문제 만들기"
              v-model="form.question"
              maxlength="255"
              required
            />
          </b-field>
          <b-field
            label="보기"
            name="proSel"
            message="당신이 내고 싶은 문항을 작성하세요."
            horizontal
          >
            <b-field label="1번" :label-position="labelPosition">
              <b-input
                placeholder="1번 보기를 입력하세요."
                maxlength="150"
              ></b-input>
            </b-field>
            <b-field label="2번" :label-position="labelPosition">
              <b-input placeholder="2번 보기를 입력하세요." maxlength="150">
              </b-input>
            </b-field>
            <b-field label="3번" :label-position="labelPosition">
              <b-input
                placeholder="3번 보기를 입력하세요."
                maxlength="150"
              ></b-input>
            </b-field>
            <b-field label="4번" :label-position="labelPosition">
              <b-input
                placeholder="4번 보기를 입력하세요."
                maxlength="150"
              ></b-input>
            </b-field>
          </b-field>

          <b-field
            label="정답"
            name="proAnswer"
            message="당신이 낸 문항의 정답을 작성하세요."
            horizontal
          >
            <b-field label="정답" :label-position="labelPosition">
              <b-input
                placeholder="정답을 입력하세요."
                maxlength="150"
              ></b-input>
            </b-field>
          </b-field>
          <!-- <div style="text-align: center;"> -->
          <b-button native-type="submit" type="is-primary"
            >문제/보기 등록</b-button
          >
          <!-- </div> -->
          <hr />
          <hr />
          <card-component
            class="has-table has-mobile-sort-spaced"
            title="문제/보기 확인"
            icon="account-multiple"
          >
            <!-- <add-test-table
              :data-url="`${$router.options.base}data-sources/clients.json`"
              :checkable="true"
            /> -->
            <add-test-table />
          </card-component>
          <div style="text-align: center;">
            <!-- <b-field horizontal> -->
            <b-field grouped>
              <div class="control">
                <b-button native-type="submit" type="is-primary"
                  >시험 출제</b-button
                >
              </div>
              <div class="control">
                <b-button type="is-primary is-outlined" @click="reset"
                  >다시 만들기</b-button
                >
              </div>
            </b-field>
            <!-- </b-field> -->
          </div>
        </form>
      </card-component>
    </section>
  </div>
</template>
<script>
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import HeroBar from "@/components/HeroBar";
import AddTestTable from "@/components/AddTestTable";
import axios from "axios";

export default {
  name: "AddTest",
  components: {
    HeroBar,
    AddTestTable,
    CardComponent,
    TitleBar
  },
  data() {
    return {
      testService: "",
      labelPosition: "on-border",
      selectedOptions: [],
      isLoading: false,
      form: {
        username: null,
        testnum: null,
        testname: null,
        endtime: null,
        starttime: null,
        testguide: null
      },
      // customElementsForm: {
      //   checkbox: [],
      //   radio: null,
      //   switch: true,
      //   file: null
      // },
      departments: ["JAVA", "SPRINGBOOT", "VUE", "SQL"]
    };
  },
  computed: {
    titleStack() {
      return ["Instructor", "Add Test"];
    }
  },
  methods: {
    testForm() {
      const addtestData = {
        // userName: this.userName,
        testNum: this.testNum,
        testName: this.testName,
        endTime: this.endTime,
        startTime: this.startTime,
        testGuide: this.testGuide
      };
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      axios
        .post(
          "http://localhost:8000/test/create/" + "?username=",
          addtestData,
          {
            headers: {
              "Content-Type": "application/json"
            }
          }
        )
        .then(response => {
          alert("시험 생성 성공!");
          console.log(response.data);
          // this.$router.push({ name: "InstructorTest" });
        })
        .catch(error => {
          alert("시험 생성 실패");
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    testproblemForm() {
      const addtestproblemData = {
        // testnum: this.testnum,
        proId: this.proId,
        proNum: this.proNum,
        proDes: this.proDes,
        proSel: this.proSel,
        proImage: this.proImage,
        proAnswer: this.proAnswer
      };
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      axios
        .post(
          "http://localhost:8000/testpro/create/" + this.testService,
          addtestproblemData,
          {
            // headers: {
            //   "Content-Type": "application/json"
            // }
          }
        )
        .then(Headers => {
          alert("시험 문제 생성 성공!");
          console.log(Headers); //get("Authorization")
          // sessionStorage.setItem("user", JSON.stringify(response.data));
          this.$router.push({ name: "InstructorTest" });
        })
        .catch(error => {
          alert("시험 문제 생성 실패");
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    initForm() {
      this.testnum = "";
      this.proId = "";
      this.pronum = "";
      this.proDes = "";
      this.proSel = "";
      this.proImage = "";
      this.proAnswer = "";
    }
  }
};
</script>
