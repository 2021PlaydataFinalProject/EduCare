<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 감독
    </hero-bar>
    <section>
      <div class="tile is-ancestor">
        <div class="tile is-4 is-vertical is-parent">
          <div class="tile is-child box">
            <p class="title is-5">시험감독</p>
            <p>
              시험이 시작될때 녹화시작 먼저 클릭한 후 시험을 완료 후 시험완료를
              클릭하면 감독관에게 전송됩니다.
            </p>
            <p>
              영상 분석 데이터 확인 (부정행위 감지 시간)
            </p>
            <b-field label="시험 점수를 입력하세요. 부정행위시 0점">
              <b-field>
                <b-numberinput
                  expanded
                  controls-position="compact"
                  placeholder="0"
                  step="5"
                  aria-minus-label="Decrement by 5"
                  aria-plus-label="Increment by 5"/>
                <p class="control">
                  <b-button label="시험 점수 입력" /></p
              ></b-field>
            </b-field>

            <!-- <img id="video" src="{{ url_for('video_viewer') }}"> -->
          </div>
        </div>
        <div class="tile is-parent">
          <div class="tile is-child box">
            <p class="title is-5">영상 확인</p>
            <!-- <b-button>플레이</b-button> -->

            <iframe
              width="560"
              height="315"
              src="https://www.youtube.com/embed/0-q1KafFCLU"
              title="YouTube video player"
              frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen
            ></iframe>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";

export default {
  components: {
    TitleBar,
    HeroBar
  },
  computed: {
    titleStack() {
      return ["Instructor", "TestSupervision"];
    }
  }
};
</script>
