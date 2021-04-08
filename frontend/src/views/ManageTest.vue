<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험관리
      <router-link slot="right" to="/addtest" class="button">
        시험생성
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
                field="rownum"
                label="번호"
                numeric
                centered
                v-slot="props"
              >
                {{ props.row.aplSeq }}
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
                field="userName"
                label="학생 이름"
                v-slot="props"
                centered
              >
                {{ props.row.userName }}
              </b-table-column>
              <b-table-column
                field="testStatus"
                label="시험응시여부"
                v-slot="props"
                centered
              >
                <b-field>
                  <span v-if="props.row.testStatus == 0" class="tag is-warn">
                    시험 응시 하기
                  </span>
                  <span
                    v-else-if="props.row.testStatus == 1"
                    class="tag is-success"
                  >
                    시험 응시 완료
                  </span>
                </b-field>
              </b-table-column>
              <b-table-column
                field="testResult"
                label="채점결과"
                v-slot="props"
                centered
              >
                {{ props.row.testResult }}
              </b-table-column>
              <b-table-column
                field="testName"
                label="시험과목"
                v-slot="props"
                centered
              >
                {{ props.row.testName }}
              </b-table-column>
            </b-table>
          </section>
        </div>
      </card-component>

      <hr />

      <!-- <notification class="is-info">
        <div>
          <b-icon icon="buffer" custom-size="default" />
          <b>Tightly wrapped</b> &mdash; table header becomes card header
        </div>
      </notification>

      <card-component class="has-table has-mobile-sort-spaced">
        <clients-table-sample
          :data-url="`${$router.options.base}data-sources/clients.json`"
          :checkable="true"
        />
      </card-component>

      <hr />

      <notification class="is-info">
        <div>
          <b-icon icon="buffer" custom-size="default" />
          <b>Empty table.</b> When there's nothing to show
        </div>
      </notification>

      <card-component class="has-table has-thead-hidden">
        <clients-table-sample />
      </card-component> -->
    </section>
  </div>
</template>

<script>
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";

export default {
  name: "ManageTest",
  components: {
    HeroBar,
    TitleBar,
    CardComponent,
  },
  computed: {
    titleStack() {
      return ["Instructor", "ManageTest"];
    }
  }
};
</script>
