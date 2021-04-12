<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 문제 만들기
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="시험 문제" icon="ballot">
        <hr />
        <b-field label="문항 번호" horizontal>
          <b-numberinput step="1" v-model="form.proNum"></b-numberinput>
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
            v-model="form.proDes"
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
              v-model="form.proSel.one"
              maxlength="150"
            ></b-input>
          </b-field>
          <b-field label="2번" :label-position="labelPosition">
            <b-input
              placeholder="2번 보기를 입력하세요."
              v-model="form.proSel.two"
              maxlength="150"
            >
            </b-input>
          </b-field>
          <b-field label="3번" :label-position="labelPosition">
            <b-input
              placeholder="3번 보기를 입력하세요."
              v-model="form.proSel.three"
              maxlength="150"
            ></b-input>
          </b-field>
          <b-field label="4번" :label-position="labelPosition">
            <b-input
              placeholder="4번 보기를 입력하세요."
              v-model="form.proSel.four"
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
        <b-button v-on:click="testproblemForm()">문제/보기 등록</b-button>
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
          <b-table :data="isEmpty ? [] : test" :hoverable="isHoverable">
            <b-table-column
              cell-class="has-no-head-mobile is-image-cell"
              v-slot="props"
            >
              <div class="image">
                <img :src="props.row.avatar" class="is-rounded" />
              </div>
            </b-table-column>
            <b-table-column label="문제" field="문제" sortable v-slot="props">
              {{ props.row.문제 }}
            </b-table-column>
            <b-table-column
              label="보기 1번"
              field="보기 1번"
              sortable
              v-slot="props"
            >
              {{ props.row.보기1번 }}
            </b-table-column>
            <b-table-column
              label="보기 2번"
              field="보기 2번"
              sortable
              v-slot="props"
            >
              {{ props.row.보기2번 }}
            </b-table-column>
            <b-table-column
              label="보기 3번"
              field="보기 3번"
              sortable
              v-slot="props"
            >
              {{ props.row.보기3번 }}
            </b-table-column>
            <b-table-column label="보기 4번" field="보기 4번" v-slot="props">
              {{ props.row.created }}
            </b-table-column>
          </b-table>
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
import axios from "axios";
export default {
  name: "AddTestProblems",
  components: {
    HeroBar,
    CardComponent,
    TitleBar
  },
  data() {
    return {
      labelPosition: "on-border",
      selectedOptions: [],
      isLoading: false,
      testNum: this.$route.params.testNum,
      form: {
        proNum: 1,
        proDes: "",
        proSel: {
          one: "",
          two: "",
          three: "",
          four: ""
        },
        proImage: "",
        proAnswer: ""
      }
    };
  },
  computed: {
    titleStack() {
      return ["Instructor", "Add Test Problems"];
    }
  },
  methods: {
    testproblemForm() {
      let proSelList = [];
      proSelList.push(this.form.proSel.one);
      proSelList.push(this.form.proSel.two);
      proSelList.push(this.form.proSel.three);
      proSelList.push(this.form.proSel.four);
      this.proSel = proSelList.join(",");

      const addTestProblemData = {
        // testnum: this.testnum,
        // proId: this.proId,
        proNum: this.form.proNum,
        proDes: this.form.proDes,
        proSel: this.proSel,
        proImage: this.form.proImage,
        proAnswer: this.form.proAnswer
      };
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      axios
        .post("http://localhost:8000/testpro/create/1", addTestProblemData, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
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
