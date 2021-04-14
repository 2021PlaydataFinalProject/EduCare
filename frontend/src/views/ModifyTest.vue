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
        <b-button v-on:click="testForm()">시험 수정하기</b-button>
        <hr />
      </card-component>
    </section>
  </div>
</template>
<script>
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import HeroBar from "@/components/HeroBar";
import { localISOdt } from "local-iso-dt";
import { fetchTest } from "@/api/test";
// import axios from "axios";

export default {
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
        testName: "",
        endTime: "",
        startTime: "",
        testGuide: ""
      },
      testNum: ""
    };
  },
  computed: {
    titleStack() {
      return ["Instructor", "Modify Test"];
    }
  },
  methods: {
    submitForm() {}
  },
  async created() {
    const testNum = this.$route.params.testNum;
    const { data } = await fetchTest(testNum);
    this.testName = data.testname;
    this.endTime = data.endtime;
    this.startTime = data.startTime;
    this.testGuide = data.testGuide;

    console.log(data);
  }
};
</script>
