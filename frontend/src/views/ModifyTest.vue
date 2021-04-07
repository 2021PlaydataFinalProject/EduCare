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
          <hr />
          <b-field label="시험명" message="과목명을 적어주세요." horizontal>
            <b-input
              placeholder="자바 쪽지시험 : 소제목"
              v-model="form.subject"
              required
            />
          </b-field>
          <b-field
            label="문제"
            message="당신의 문제를 255자 이내로 작성하세요."
            horizontal
          >
            <b-input
              type="textarea"
              placeholder="시험 항목 만들기"
              v-model="form.question"
              maxlength="255"
              required
            />
          </b-field>
          <hr />
          <b-field horizontal>
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
          </b-field>
        </form>
      </card-component>
      <card-component title="Custom elements" icon="ballot-outline">
        <b-field label="Checkbox" class="has-check" horizontal>
          <checkbox-picker
            :options="{ lorem: 'Lorem', ipsum: 'Ipsum', dolore: 'Dolore' }"
            v-model="customElementsForm.checkbox"
            type="is-primary"
          />
        </b-field>
        <hr />
        <b-field label="Radio" class="has-check" horizontal>
          <radio-picker
            :options="{ one: 'One', two: 'Two' }"
            v-model="customElementsForm.radio"
          ></radio-picker>
        </b-field>
        <hr />
        <b-field label="Switch" horizontal>
          <b-switch v-model="customElementsForm.switch">
            Default
          </b-switch>
        </b-field>
        <hr />
        <b-field label="File" horizontal>
          <file-picker v-model="customElementsForm.file" />
        </b-field>
      </card-component>
    </section>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import mapValues from "lodash/mapValues";
import CheckboxPicker from "@/components/CheckboxPicker";
import RadioPicker from "@/components/RadioPicker";
import FilePicker from "@/components/FilePicker";
import HeroBar from "@/components/HeroBar";

export default {
  name: "AddTest",
  components: {
    HeroBar,
    FilePicker,
    RadioPicker,
    CheckboxPicker,
    CardComponent,
    TitleBar
  },
  data() {
    return {
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
      return ["Instructor", "Modify Test"];
    }
  },
  methods: {
    submit() {},
    reset() {
      this.form = mapValues(this.form, item => {
        if (item && typeof item === "object") {
          return [];
        }
        return null;
      });

      this.$buefy.snackbar.open({
        message: "Reset successfully",
        queue: false
      });
    }
  }
};
</script>
