<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 수정하기
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="시험" icon="ballot" @submit.prevent="submitForm">
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
            name="endTime"
            message="시험 종료 시간 지정"
          >
            <b-datetimepicker
              rounded
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
        <hr />
        <hr />
        <b-field label="문항 번호" horizontal>
          <b-numberinput step="1" v-model="form.proNum"></b-numberinput>
        </b-field>
        <b-field
          label="문제"
          message="당신의 문제를 255자 이내로 작성하세요."
          horizontal
        >
          <b-input
            type="textarea"
            placeholder="시험 문제 만들기"
            v-model="form.proDes"
            maxlength="255"
          />
        </b-field>
        <b-field
          label="보기"
          message="당신이 내고 싶은 문항을 작성하세요."
          horizontal
        >
          <b-field label="1번" :label-position="labelPosition">
            <b-input
              placeholder="1번 보기를 입력하세요."
              name="form.proSel[]"
              v-on:input="changed"
              v-model="form.proSel[0]"
              maxlength="150"
            ></b-input>
          </b-field>
          <b-field label="2번" :label-position="labelPosition">
            <b-input
              placeholder="2번 보기를 입력하세요."
              v-on:input="changed"
              name="form.proSel[]"
              v-model="form.proSel[1]"
              maxlength="150"
            >
            </b-input>
          </b-field>
          <b-field label="3번" :label-position="labelPosition">
            <b-input
              placeholder="3번 보기를 입력하세요."
              v-on:input="changed"
              name="form.proSel[]"
              v-model="form.proSel[2]"
              maxlength="150"
            ></b-input>
          </b-field>
          <b-field label="4번" :label-position="labelPosition">
            <b-input
              placeholder="4번 보기를 입력하세요."
              v-on:input="changed"
              name="form.proSel[]"
              v-model="form.proSel[3]"
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
              v-model="form.proAnswer"
              placeholder="정답을 입력하세요."
              maxlength="150"
            ></b-input>
          </b-field>
        </b-field>
        <div style="text-align: center;">
          <b-field grouped>
            <div class="control">
              <b-button
                tag="router-link"
                @click="updatetestForm"
                to="/instructor"
                type="is-link"
              >
                시험 수정 완료
              </b-button>
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
import axios from "axios";

export default {
  components: {
    HeroBar,
    CardComponent,
    TitleBar
  },
  data() {
    const testNum = this.$route.params.testNum;
    return {
      showWeekNumber: false,
      enableSeconds: true,
      hourFormat: undefined, // Browser locale
      locale: undefined, // Browser locale
      testService: "",
      labelPosition: "on-border",
      selectedOptions: [],
      isLoading: false,
      form: {
        testName: testNum !== undefined ? this.data[testNum].testName : "",
        endTime: testNum !== undefined ? this.data[testNum].endTime : "",
        startTime: testNum !== undefined ? this.data[testNum].startTime : "",
        testGuide: testNum !== undefined ? this.data[testNum].testNum : "",
        proNum: 1,
        proDes: testNum !== undefined ? this.data[testNum].proDes : "",
        proSel: testNum !== undefined ? this.data[testNum].proSel : "",
        proImage: testNum !== undefined ? this.data[testNum].proImage : "",
        proAnswer: testNum !== undefined ? this.data[testNum].proAnswer : ""
      }
    };
  },
  computed: {
    titleStack() {
      return ["Instructor", "Modify Test"];
    }
  },
  methods: {
    updatetestForm() {
      const addTestData = {
        testNum: this.form.testNum,
        endTime: this.form.endTime,
        startTime: this.form.startTime,
        testGuide: this.form.testGuide
      };
      axios
        .get(
          "http://localhost:8000/test/create?username=teacher",
          addTestData,
          {
            headers: {
              contentType: false,
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(response => {
          this.test = response.data;
          console.log(this.test);
        });
      const addtestproblemData = {
        proId: this.proId,
        proNum: this.proNum,
        proDes: this.proDes,
        proSel: this.proSel,
        proImage: this.proImage,
        proAnswer: this.proAnswer
      };
      // let instance = axios.create();
      // instance.defaults.headers.common[
      //   "Authorization"
      // ] = sessionStorage.getItem("Authorization");
      axios
        .get(
          "http://localhost:8000/testpro/create/" + this.testService,
          addtestproblemData,
          {
            headers: {
              "Content-Type": "application/json"
            }
          }
        )
        .then(Headers => {
          alert("시험 문제 생성 성공!");
          console.log(Headers); //get("Authorization")
          // sessionStorage.setItem("user", JSON.stringify(response.data));
          this.getModifyTest();
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
    initForm(testNum) {
      this.testName = this.data[testNum].testName;
      this.endTime = this.data[testNum].endTime;
      this.startTime = this.data[testNum].startTime;
      this.testGuide = this.data[testNum].testGuide;
      this.testnum = this.data[testNum].testNum;
      this.proId = this.data[testNum].proId;
      this.pronum = this.data[testNum].proNum;
      this.proDes = this.data[testNum].proDes;
      this.proSel = this.data[testNum].proSel;
      this.proImage = this.data[testNum].proImage;
      this.proAnswer = this.data[testNum].proAnswer;
    }
  }
};
</script>
