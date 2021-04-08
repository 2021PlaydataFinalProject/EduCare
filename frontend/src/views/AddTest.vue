<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 만들기
      <router-link slot="right" to="instructor" class="button">
        시험 목록
      </router-link>
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="Forms" icon="ballot">
        <form @submit.prevent="submit">
          <b-field label="출제자 정보" horizontal>
            <b-field>
              <b-input
                icon="account"
                v-model="form.name"
                placeholder="이름"
                name="name"
                required
              />
            </b-field>
            <b-field>
              <b-input
                icon="email"
                type="email"
                v-model="form.email"
                placeholder="이메일"
                name="email"
                required
              />
            </b-field>
          </b-field>
          <b-field message="필수 작성 부분입니다." horizontal>
            <b-field>
              <p class="control">
                <a class="button is-static">
                  +82
                </a>
              </p>
              <b-input type="tel" v-model="form.phone" name="phone" expanded />
            </b-field>
          </b-field>
          <b-field label="시험 과목" horizontal>
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
          </b-field>

          <b-field label="시험명" message="과목명을 적어주세요." horizontal>
            <b-input
              placeholder="자바 쪽지시험 : 소제목"
              v-model="form.subject"
              maxlength="255"
              required
            />
          </b-field>
          <hr />
          <b-field
            label="문제"
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
// import CheckboxPicker from "@/components/CheckboxPicker";
// import RadioPicker from "@/components/RadioPicker";
// import FilePicker from "@/components/FilePicker";
import HeroBar from "@/components/HeroBar";
import AddTestTable from "@/components/AddTestTable";

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
      labelPosition: "on-border",
      isLoading: false,
      form: {
        name: null,
        email: null,
        phone: null,
        department: null,
        subject: null,
        question: null
      },
      customElementsForm: {
        checkbox: [],
        radio: null,
        switch: true,
        file: null
      },
      departments: ["JAVA", "SPRINGBOOT", "VUE", "SQL"]
    };
  },
  computed: {
    titleStack() {
      return ["Instructor", "Add Test"];
    }
  },
  methods: {}
};
</script>
