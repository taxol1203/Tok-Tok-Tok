<template>
  <div v-if="isOpen == 'OPEN'">
    <div v-loading="loading">상담 연결 중입니다. 잠시만 기다려주세요.</div>
    <!-- <i class="el-icon-loading" style="margin-right: 3px"></i> -->
  </div>
  <div v-if="isOpen == 'LIVE'">상담이 시작되었습니다.</div>
  <!-- 실시간채팅 시작 -->
  <div v-for="(msg, index) in messages" :key="index">
    <el-row>
      <el-col v-if="msg.fk_author_idx == userPkidx">
        <div class="message-me">
          {{ msg.message }}
        </div>
      </el-col>
      <el-col v-else>
        <div class="message-other">
          {{ msg.message }}
          <br v-if="msg.type == 'VID'" />
          <el-button class="accept-btn" v-if="msg.type == 'VID'" @click="openVideo">
            수락하기
          </el-button>
        </div>
        <!-- <div
          class="message-other vr"
          v-if="msg.type == 'VID'"
          @click="openVideo"
        >
          메세지를 클릭해 주세요.
        </div> -->
      </el-col>
    </el-row>
  </div>
  <p>{{ closeMsg }}</p>
  <!-- 실시간채팅 끝 -->
</template>
<script>
import { useStore } from "vuex";
import { computed } from "vue";

export default {
  name: "Chat",
  components: {},
  setup() {
    const store = useStore();
    const userPkidx = computed(() => store.state.auth.user.pk_idx);
    const sessionId = computed(() => store.getters["get_selected_idx"]); //user가 생성한 방 id
    const messages = computed(() => store.getters["get_user_messages"]);
    const isOpen = computed(() => store.getters["get_user_room_status"]);
    const closeMsg = computed(() => store.getters["clostMsgGetter"]);
    const loading = true;
    const openVideo = () => {
      store.commit("OPEN_VIDEO");
    };

    return {
      store,
      sessionId,
      userPkidx,
      messages,
      isOpen,
      loading,
      closeMsg,
      openVideo,
    };
  },
};
</script>
<style scoped>
.accept-btn {
  border-radius: 10%;
  cursor: pointer;
  background-color: #27251f;
  color: #fff;
}
#topMessages {
  display: block;
  top: 0px;
  height: 21rem;
  width: 100%;
}
#bottomInput {
  bottom: 0px;
  width: 100%;
}
.el-scroll {
  overflow-x: hidden;
}
.message-me {
  border: 1px solid #f7f4f0;
  border-radius: 10px 10px 0px 10px;
  background: #f7f4f0;
  float: right;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
.message-other {
  border: 1px solid #006f3e;
  border-radius: 10px 10px 10px 0px;
  color: #fff;
  background: #006f3e;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
#inputBox {
  width: 90%;
  height: 100%;
  background-color: transparent;
  border: 0px solid #eee;
}
.vr:hover {
  background-color: white;
}
/* hover로 버튼 색 변하게 하기: 추가기능 */
</style>
