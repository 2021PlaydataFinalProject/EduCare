<template>
  <div>
    <div id="headerBtn">
      <div id="1" class="btn-rightup">
        <ModalClient />
      </div>
      <div id="2" class="btn-rightup-two">
        <ModalNewRoom />
      </div>
    </div>
    <div><RoomList /></div>
  </div>
</template>

<script>
// @ is an alias to /src
import ModalClient from "@/components/clientinfo/ModalClient.vue";
import ModalNewRoom from "@/components/meetingrooms/ModalNewRoom.vue";
import RoomList from "@/components/meetingrooms/RoomList.vue";

export default {
  name: "WaittingRoom",
  components: {
    ModalClient,
    ModalNewRoom,
    RoomList
  }
};
</script>

<style></style>
