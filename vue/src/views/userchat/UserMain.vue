<template>
  <el-row :gutter="20">
    <div id="UserMain">
      <div class="videoContainer" v-if="videoStatus != 'CLOSE'">
        <VideoChatDetail />
      </div>
      <div class="fab-container">
        <transition class="same-pos" name="fade" mode="out-in">
          <el-button
            class="big-btn"
            type="primary"
            icon="el-icon-plus"
            @click="changeCondition"
            v-if="!isHidden"
            circle
          ></el-button>
        </transition>
        <el-dialog
          title="상담을 종료하시겠습니까?"
          v-model="DialogVisible"
          width="30%"
          center
        >
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="DialogVisible = false">아니오</el-button>
              <el-button type="primary" @click="sendEnd">네</el-button>
            </span>
          </template>
        </el-dialog>
        <transition class="same-pos" name="fade" mode="out-in">
          <div id="chat-box" v-if="isHidden">
            <el-row :gutter="40">
              <el-col :span="20" :offset="0"></el-col>
              <el-col :span="4" :offset="0">
                <div style="float: right">
                  <i
                    @click="DialogVisible = true"
                    class="el-icon-error"
                    id="close-btn"
                  ></i>
                  <!-- <i @click="DialogVisible = true" class="el-icon-close" id="close-btn"></i> -->
                </div>
              </el-col>
            </el-row>
            <el-card :body-style="{ padding: '0px' }" id="chat-card">
              <div id="card-head" class="card-header"></div>
              <div class="full-box">
                <UserQna :close="isHidden" />
              </div>
            </el-card>
          </div>
        </transition>
      </div>
    </div>
  </el-row>
</template>
<script>
import { useStore } from "vuex";
import UserChatDetail from "./UserChatDetail.vue";
import UserQna from "./UserQna.vue";
import ChatDetail from "../../components/chat/ChatDetail.vue";
import VideoChatDetail from "@/components/VideoChat/VideoChatDetail.vue";
import { computed, ref } from "vue";
/* eslint-disable */
export default {
  components: {
    UserQna,
    UserChatDetail,
    ChatDetail,
    VideoChatDetail,
  },
  setup() {
    const store = useStore();
    let DialogVisible = ref(false);
    const isHidden = computed(() => store.getters['userQna/showUserChat']);
    const user_pk_idx = computed(() => store.state.auth.user.pk_idx);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    let stompClient = computed(() => store.getters['stompGetter']);
    const sendEnd = () => {
      send('END');
      store.commit('userQna/CHANGE_STATE');
      store.commit('changeSessionkeyStatus', 'END');
      DialogVisible.value = false;
    };
    const videoStatus = computed(() => store.state.video_status);

    let changeCondition = () => {
      //+ 버튼 눌러서 상담 시작해야하는 경우
      store.commit('changeSessionkeyStatus', '');
      store.dispatch('userQna/init');
      store.commit('userQna/CHANGE_STATE');
      DialogVisible.value = false;
    };
    let connected = false;

    const send = (type) => {
      console.log(sessionId.value);
      if (
        sessionId.value &&
        stompClient.value &&
        stompClient.value.connected &&
        user_pk_idx.value > 0
      ) {
        console.log('IN SOCKET');
        const msg = {
          message: '',
          fk_author_idx: user_pk_idx.value, // 작성자의 회원 idx
          fk_session_id: sessionId.value, // 현재 채팅세션의 id.
          type: type, // 메세지 타입.
        };
        stompClient.value.send('/receive/' + sessionId.value, JSON.stringify(msg), {});
      }
    };
    return {
      DialogVisible,
      sessionId,
      user_pk_idx,
      isHidden,
      connected,
      stompClient,
      videoStatus,
      changeisChatExist,
      changeCondition,
      sendEnd,
      send,
    };
  },
};
</script>
<style scoped>
#UserMain {
  position: fixed;
  width: 100%;
  height: 100%;
  background-image: url("../../assets/Microsoft.png");
  background-repeat: no-repeat;
  background-position: center;
}

.fab-container {
  position: fixed;
  bottom: 50px;
  right: 50px;
  z-index: 999;
  cursor: pointer;
}

#chat-box {
  width: 25rem; /* 1rem = 16px */
  height: 45rem; /* 720px */
  padding: 10px;
  position: sticky;
  background-color: #09c7fb;
  background-image: linear-gradient(315deg, #09c7fb 0%, #93fb9d 74%);
  border-radius: 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.24), 0 0 6px rgba(0, 0, 0, 0.08);
}

.big-btn {
  width: 80px;
  height: 80px;
  /* icon 사이즈 키우는 법 */
  font-size: 2rem;
  background-color: #09c7fb;
  background-image: linear-gradient(315deg, #09c7fb 0%, #93fb9d 74%);
  border: none;
}

#close-btn {
  font-size: 2rem;
  color: white;
  font-style: bold;
  margin-bottom: 10px;
}

/* 위치 고정을 시키지 않으면 렌더링하면서 (생명&소멸) 서로 다른 공간에 보여짐 */
.same-pos {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

.full-box {
  box-sizing: border-box;
  width: 100%;
  height: auto;
  padding: 14px;
  position: relative;
}

#chat-card {
  border-radius: 1rem;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  border: none;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* background-color: #006f3e; */
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

/* 생성 부분 */
.fade-enter-from {
  opacity: 0;
}
.fade-enter-to {
  opacity: 1;
}
.fade-enter-active {
  transition: all 0.3s ease-out;
}
/* 소멸 부분 */
.fade-leave-from {
  opacity: 1;
}
.fade-leave-to {
  opacity: 0;
}
.fade-leave-active {
  transition: all 0.5s ease-out;
}
.videoContainer {
  position: absolute;
  width: 65rem;
  height: 50rem;
  background-color: lightgrey;
  margin: 5rem;
}
</style>
