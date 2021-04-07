<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 수정하기
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
      return ["Instructor", "Modify Test"];
    }
  }
};
</script>

<style lang="scss" scoped></style>
