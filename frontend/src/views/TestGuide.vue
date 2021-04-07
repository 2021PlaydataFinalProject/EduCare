<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 안내사항
      <router-link slot="right" to="/profile" class="button">
        마이 페이지
      </router-link>
    </hero-bar>
    <section>
      <SignInForm></SignInForm>
    </section>
  </div>
</template>

<script>
import SignInForm from "@/components/SignInForm.vue";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";

export default {
  components: {
    SignInForm,
    TitleBar,
    HeroBar
  },
  computed: {
    titleStack() {
      return ["Student", "SignInForm"];
    }
  }
};
</script>

<style lang="scss" scoped></style>
