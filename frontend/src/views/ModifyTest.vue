<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 수정하기
    </hero-bar>
    <section class="section is-main-section">
      <card-component
        class="has-table has-mobile-sort-spaced"
        title="시험 수정하기"
        icon="account-multiple"
      >
        <div id="app" class="container">
          <section>
            <b-table :data="isEmpty ? [] : test" :hoverable="isHoverable">
              <b-table-column
                field="testNum"
                label="시험번호"
                numeric
                centered
                v-slot="props"
              >
                {{ props.row.testNum }}
              </b-table-column>

              <b-table-column
                field="endTime"
                label="종료 시간"
                v-slot="props"
                centered
              >
                {{ props.row.endTime }}
              </b-table-column>

              <b-table-column
                field="startTime"
                label="시작 시간"
                v-slot="props"
                centered
              >
                {{ props.row.startTime }}
              </b-table-column>
              <b-table-column
                field="testGuide"
                label="시험 유의사항"
                v-slot="props"
                centered
              >
                {{ props.row.testGuide }}
              </b-table-column>
              <b-table-column
                field="instructorId"
                label="담당교수명"
                v-slot="props"
                centered
              >
                {{ props.row.instructorId }}
              </b-table-column>
              <b-table-column label="수정 및 삭제" v-slot="props" centered>
                <b-button
                  type="is-primary is-light"
                  outlined
                  v-on:click="updateInstructorTest(props.row.testNum)"
                  position="is-centered"
                  size="is-small"
                  >수정</b-button
                >
                <b-button
                  type="is-danger is-light"
                  outlined
                  v-on:click="deleteInstructorTest(props.row.testNum)"
                  position="is-centered"
                  size="is-small"
                  >삭제</b-button
                >
              </b-table-column>
              <b-table-column label="응시자 관리" v-slot="props" centered>
                <b-button
                  type="is-primary is-light"
                  outlined
                  v-on:click="manageStudentList(props.row.testNum)"
                  position="is-centered"
                  size="is-small"
                  >관리</b-button
                >
              </b-table-column>
            </b-table>
          </section>
          <b-button
            tag="router-link"
            to="/addtest"
            type="is-link"
            class="button is-primary is-pulled-right"
            >시험생성</b-button
          >
        </div>
      </card-component>

      <hr />
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
