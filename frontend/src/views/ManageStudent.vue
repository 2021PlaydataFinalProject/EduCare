<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      응시자 관리
    </hero-bar>
    <section class="section is-main-section">
      <card-component
        class="has-table has-mobile-sort-spaced"
        title="응시자"
        icon="account-multiple"
      >
        <div id="app" class="container">
          <section>
            <b-table :data="isEmpty ? [] : apps" :hoverable="isHoverable">
              <b-table-column
                field="stuNum"
                label="학생 번호"
                numeric
                centered
                v-slot="props"
              >
                {{ props.row.rowNum }}
              </b-table-column>

              <b-table-column
                field="userName"
                label="응시자"
                v-slot="props"
                centered
              >
                {{ props.row.userName }}
              </b-table-column>

              <b-table-column
                field="username"
                label="학생ID"
                v-slot="props"
                centered
              >
                {{ props.row.username }}
              </b-table-column>
               <b-table-column
                field="testResult"
                label="점수"
                v-slot="props"
                centered
              >
                {{ props.row.testResult }}
              </b-table-column>
              <b-table-column label="감독" v-slot="props" centered>
                <b-button
                  type="is-primary is-light"
                  outlined
                  v-on:click="updateInstructorTest(props.row.pfSeq)"
                  position="is-centered"
                  size="is-small"
                  >감독하기</b-button
                >
              </b-table-column>
              <b-table-column label="삭제" v-slot="props" centered>
                <b-button
                  type="is-primary is-light"
                  outlined
                  v-on:click="manageInstructorTest(props.row.pfSeq)"
                  position="is-centered"
                  size="is-small"
                  >삭제</b-button
                >
              </b-table-column>
            </b-table>
          </section>
          <b-button
            tag="router-link"
            to="/addstudent"
            type="is-link"
            class="button is-primary is-pulled-right"
            >응시자 추가</b-button
          >
        </div>
      </card-component>

      <hr />
    </section>
  </div>
</template>

<script>
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";

export default {
  name: "ManageStudent",
  components: {
    HeroBar,
    TitleBar,
    CardComponent,
  },
  computed: {
    titleStack() {
      return ["Instructor", "ManageStudent"];
    }
  }
};
</script>
