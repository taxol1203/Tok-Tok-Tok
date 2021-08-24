<template>
  <div v-cloak>
    <div class="row">
      <div>
        <h3>채팅방 리스트</h3>
      </div>
    </div>
    <div class="input-group">
      <div class="input-group-prepend">
        <label class="input-group-text">방제목</label>
      </div>
      <div class="input-group-append">
        <button class="btn btn-primary" type="button" @click="createRoom">
          채팅방 개설
        </button>
      </div>
    </div>
    <ul class="list-group">
      <li
        class="list-group-item list-group-item-action"
        v-for="item in chatrooms"
        v-bind:key="item.roomId"
        v-on:click="enterRoom(item.session_id)"
      >
        {{ item.session_id }} / {{ item.created_at }} / {{ item.fk_client_idx }}
      </li>
    </ul>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from "@/components/HelloWorld.vue";
import axios from "axios";

export default {
  name: "Home",
  components: {
    // HelloWorld,
  },
  data: function() {
    return {
      chatrooms: [],
      user_idx: 1, // 나중에 이 부분에 user pk_idx 주입해야 함.
    };
  },
  created() {
    this.findAllRoom();
  },
  methods: {
    findAllRoom: function() {
      axios
        .get("http://localhost:8088/temp/api/chat/rooms/user/" + this.user_idx)
        .then((response) => {
          this.chatrooms = response.data;
          console.log(this.chatrooms);
        });
    },
    createRoom: function() {
      // var params = new URLSearchParams();
      // params.append("name", this.room_name);
      axios
        .post("http://localhost:8088/temp/api/chat/room", {
          unread: 0,
          fk_created_by_idx: 1, // 상담 신청하는 고객의 userid
          fk_client_idx: 1, // 위 필드와 동일값 넣어주면 됨.
        })
        .then((response) => {
          console.log(response);
          alert("방 개설에 성공하였습니다.");
          this.findAllRoom();
        })
        .catch((response) => {
          alert("채팅방 개설에 실패하였습니다." + response);
        });
    },
    enterRoom: function(session_id) {
      // var sender = prompt("대화명을 입력해 주세요.");
      // if (sender != "") {
      //   localStorage.setItem("wschat.sender", sender);
      //   localStorage.setItem("wschat.roomId", roomId);
      // }
      location.href = "/chat/room/" + session_id;
    },
  },
};
</script>
