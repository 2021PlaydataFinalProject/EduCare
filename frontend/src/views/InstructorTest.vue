<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 관리
      <router-link slot="right" to="/profile" class="button">
        마이 페이지
      </router-link>
    </hero-bar>
    <section class="section is-main-section">
      <!-- <notification class="is-info">
        <div>
          <b-icon icon="buffer" custom-size="default" />
          <b>Sorted and paginated table.</b>&nbsp;Based on Buefy's table.
        </div>
      </notification> -->

      <card-component
        class="has-table has-mobile-sort-spaced"
        title="시험"
        icon="account-multiple"
      >
        <div id="app" class="container">
          <section>
            <b-table :data="isEmpty ? [] : apps" :hoverable="isHoverable">
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
                field="testName"
                label="시험명"
                v-slot="props"
                centered
              >
                {{ props.row.testName }}
              </b-table-column>

              <b-table-column
                field="userName"
                label="학생 이름"
                v-slot="props"
                centered
              >
                {{ props.row.userName }}
              </b-table-column>
              <b-table-column label="수정 및 삭제" v-slot="props" centered>
                <b-button
                  type="is-primary is-light"
                  outlined
                  v-on:click="updateInstructorTest(props.row.pfSeq)"
                  position="is-centered"
                  size="is-small"
                  >수정</b-button
                >
                <b-button
                  type="is-danger is-light"
                  outlined
                  v-on:click="deleteInstructorTest(props.row.pfSeq)"
                  position="is-centered"
                  size="is-small"
                  >삭제</b-button
                >
              </b-table-column>
              <b-table-column label="응시자 관리" v-slot="props" centered>
                <b-button
                  type="is-primary is-light"
                  outlined
                  v-on:click="manageInstructorTest(props.row.pfSeq)"
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
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
// import axios from "axios";

export default {
  name: "InstructorTest",
  components: {
    HeroBar,
    TitleBar,
    CardComponent
  },
  computed: {
    titleStack() {
      return ["Instructor", "InstructorTest"];
    }
  }
};
//   methods: {
//     getInstructorTest() {
//       axios
//         .get(
//           "http://localhost:80/portfolio/getlist?userid=" +
//             JSON.parse(sessionStorage.getItem("user")).userId
//         )
//         .then(response => {
//           this.portfolio = response.data;
//         })
//         .catch(e => {
//           console.log(e);
//         });
//     },
//     updateInstructorTest(pfseq) {
//        수정버튼 클릭시 ModifyTest로 이동
//       return this.$router.push({
//         name: "ModifyTest",
//         params: { pfSeq: pfseq }
//       });
//     },
//     deleteInstructorTest(pfSeq) {
//       axios
//         .delete("http://localhost:80/portfolio/delete?pfSeq=" + pfSeq)
//         .then(() => {
//           this.getInstructorTest();
//         })
//         .catch(e => {
//           console.log(e);
//         });
//       this.getInstructorTest();
//     }
//   }
</script>
