<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 만들기
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="시험" icon="ballot">
        <b-field label="시험명" horizontal>
          {{ props.row.testName }}
        </b-field>
        <b-field label="시험 시간 지정" horizontal>
          <b-field
            :label-position="labelPosition"
            message="시험 시작 시간 지정"
          >
            <b-datetimepicker
              rounded
              label="시험 시작 시간"
              icon="calendar-today"
              v-model="form.startTime"
              :localISOdt="localISOdt"
              :datepicker="{ showWeekNumber }"
              :timepicker="{ enableSeconds, hourFormat }"
              horizontal-time-picker
            >
            </b-datetimepicker>
          </b-field>
          <b-field
            :label-position="labelPosition"
            label="시험 종료 시간"
            name="endTime"
            message="시험 종료 시간 지정"
          >
            <b-datetimepicker
              rounded
              label="시험 종료 시간"
              icon="calendar-today"
              v-model="form.endTime"
              :localISOdt="localISOdt"
              :datepicker="{ showWeekNumber }"
              :timepicker="{ enableSeconds, hourFormat }"
              horizontal-time-picker
            >
            </b-datetimepicker>
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
        <b-button v-on:click="testForm()">시험 만들기</b-button>
        <hr />
        <b-field label="문항 번호" horizontal>
          <b-field>
            <b-input
              icon="account"
              v-model="form.proNum"
              placeholder="문항 번호를 작성해 주세요."
              name="proNum"
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
            <b-input placeholder="정답을 입력하세요." maxlength="150"></b-input>
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
          <!-- <add-test-table /> -->
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
        </div>
      </card-component>
    </section>
  </div>
</template>
<script>
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import HeroBar from "@/components/HeroBar";
import { localISOdt } from "local-iso-dt";
// import AddTestTable from "@/components/AddTestTable";
import axios from "axios";

export default {
  name: "AddTest",
  components: {
    HeroBar,
    CardComponent,
    TitleBar
  },
  data() {
    return {
      showWeekNumber: false,
      enableSeconds: true,
      hourFormat: undefined, // Browser locale
      locale: undefined, // Browser locale
      localISOdt,
      testService: "",
      labelPosition: "on-border",
      selectedOptions: [],
      isLoading: false,
      form: {
        // username: null,
        // testNum: "",
        testName: "",
        endTime: "",
        startTime: "",
        testGuide: ""
      },
      testNum: "",
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
      const addTestData = {
        // userName: this.userName,
        // testNum: null,
        testName: this.form.testName,
        endTime: this.form.endTime,
        startTime: this.form.startTime,
        testGuide: this.form.testGuide
      };
      // let instance = axios.create();
      // instance.defaults.headers.common[
      //   "Authorization"
      // ] = sessionStorage.getItem("Authorization");
      axios
        .post(
          "http://localhost:8000/test/create?username=teacher@educare.com",
          addTestData,
          {
            headers: {
              contentType: false,
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(response => {
          alert("시험 생성 성공!");
          console.log(response.data);
          this.testNum = response.data;
          // this.$router.push({ name: "InstructorTest" });
          this.$router.push({
            name: "AddTestProblems",
            params: { testNum: this.testNum }
          });
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
