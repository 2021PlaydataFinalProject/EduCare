<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      내 시험
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
        <!-- 데이터가 들어갈 곳 -->
        <clients-table-sample
          :data-url="`${$router.options.base}data-sources/clients.json`"
          :checkable="true"
        />
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
import ClientsTableSample from "@/components/ClientsTableSample";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";

export default {
  name: "StudentTestList",
  components: {
    HeroBar,
    TitleBar,
    CardComponent,
    ClientsTableSample
  },
  computed: {
    titleStack() {
      return ["Admin", "StudentTestList"];
    }
  }
};
</script>
