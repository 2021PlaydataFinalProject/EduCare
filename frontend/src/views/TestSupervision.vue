<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 감독
    </hero-bar>
    <section class="pt-3 pl-3 pr-3 pb-3">
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
            <p>학생 답 띄우기</p>
            <b-field label="시험 점수를 입력하세요. 부정행위시 0점">
              <b-field>
                <b-numberinput
                  expanded
                  controls-position="compact"
                  placeholder="0"
                  v-model="testResult"
                  step="5"
                  aria-minus-label="Decrement by 5"
                  aria-plus-label="Increment by 5"/>
                <p class="control">
                  <b-button
                    @click="updateStudentScore"
                    label="시험 점수 입력"
                  /></p
              ></b-field>
            </b-field>
            <b-field grouped>
              <div class="control">
                <b-button tag="router-link" to="/instructor" type="is-link">
                  시험 감독 완료
                </b-button>
              </div>
            </b-field>
          </div>
        </div>
        <div class="tile is-parent">
          <div class="tile is-child box">
            <p class="title is-5">영상 확인</p>
            <video-player
              class="video-player-box"
              ref="videoPlayer"
              :options="playerOptions"
              :playsinline="true"
              customEventName="customstatechangedeventname"
              @play="onPlayerPlay($event)"
              @pause="onPlayerPause($event)"
              @ended="onPlayerEnded($event)"
              @waiting="onPlayerWaiting($event)"
              @playing="onPlayerPlaying($event)"
              @loadeddata="onPlayerLoadeddata($event)"
              @timeupdate="onPlayerTimeupdate($event)"
              @canplay="onPlayerCanplay($event)"
              @canplaythrough="onPlayerCanplaythrough($event)"
              @statechanged="playerStateChanged($event)"
              @ready="playerReadied"
            >
            </video-player>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import "video.js/dist/video-js.css";
import { videoPlayer } from "vue-video-player";
import axios from "axios";

export default {
  components: {
    TitleBar,
    HeroBar,
    videoPlayer
  },
  data() {
    return {
      testNum: this.$route.params.testNum,
      userName: this.$route.params.userName,
      testResult: "",
      studentTest: "",
      studentTest2: "",
      videoName: "",
      playerOptions: {
        // videojs options
        height: "480",
        responsive: true,
        muted: true,
        language: "ko",
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: "video/mp4",
            src:
              // "https://cdn.theguardian.tv/webM/2015/07/20/150716YesMen_synd_768k_vp8.webm"
              //testNum+username+video.avi 
              //\\src\\main\\webapp\\tproblemvideo\\ + testnum + username + video.avi
              "http://localhost:8000/video.mp4"
          }
        ],
        poster: "src/assets/videoposter.jpg"
      }
    };
  },
  mounted() {
    console.log("this is current player instance object", this.player);
  },
  computed: {
    titleStack() {
      return ["Instructor", "TestSupervision"];
    },
    player() {
      return this.$refs.videoPlayer.player;
    }
  },
  methods: {
    // listen event
    onPlayerPlay(player) {
      console.log("player play!", player);
    },
    onPlayerPause(player) {
      console.log("player pause!", player);
    },
    // ...player event

    // or listen state event
    playerStateChanged(playerCurrentState) {
      console.log("player current update state", playerCurrentState);
    },

    // player is ready
    playerReadied(player) {
      console.log("the player is readied", player);
      // you can use it to do something...
      //   player.[methods]
    },

    //video 정보 가져오기
    getStudentTest() {
      axios
        .get("http://localhost:8000/stutest/get/dkwjd/1", {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.studentTest = response.data;
          console.log("확인");
          console.log(this.studentTest);
          this.studentTest.forEach(function(item) {
            this.studentTest2 = item;
          });
          this.videoName = this.studentTest2.videoName;
          console.log("videoName확인");
          console.log(this.videoName);
        })
        .catch(e => {
          console.log(e);
        });
    },
    updateStudentScore() {
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      instance
        .put(
          "http://localhost:8000/stutest/update-score/" +
            this.userName +
            "/" +
            this.testNum +
            "/" +
            this.testResult
        )
        .then(response => {
          // this.applicants = response.data;
          console.log(response);
          // alert(this.test);
        })
        .catch(e => {
          console.log(e);
        });
    },
    mounted() {
      this.getStudentTest();
    }
  }
};
</script>
