<template>
  <div id="app" class="container is-max-desktop pt-5">
    <!-- <section>
      <div class="hero-body"></div>
    </section> -->
    <section>
      <div class="container is-max-desktop">
        <b-notification
          type="is-info is-light"
          aria-close-label="Close notification"
        >
          <b> WOW!!! 수고하셨습니다!! </b>
        </b-notification>
        <center>
          <b-button
            tag="router-link"
            to="/profile"
            type="is-link"
            class="button is-primary is-pulled-center"
            >마이 페이지로 돌아가기</b-button
          >
        </center>
      </div>
    </section>
  </div>
</template>
<script>
export default {};
</script>

<style>
.btn {
  color: rgb(51, 91, 221);
}
</style>
