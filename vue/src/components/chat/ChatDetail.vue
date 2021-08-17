<template>
  <i
    v-if="chatStatus == 'LIVE'"
    class="el-icon-error"
    @click="closeRoom"
    style="color: #006f3e"
  ></i>
  <!-- 상대방  height: 750px; -->
  <el-scrollbar ref="scrollbar" id="topMessages">
    <div v-for="(msg, index) in messages" :key="index">
      <el-row>
        <el-col v-if="msg.fk_author_idx == userPkidx">
          <div class="message-me" v-if="msg.type == 'MSG' || msg.type == 'VID'">
            {{ msg.message }}
          </div>
        </el-col>
        <el-col v-else>
          <div class="message-other" v-if="msg.type == 'MSG' || msg.type == 'VID'">
            {{ msg.message }}
          </div>
        </el-col>
      </el-row>
    </div>
  </el-scrollbar>
  <el-row v-if="chatStatus != 'END'" id="bottomInput" gutter="2">
    <!-- 입력창 -->
    <el-col :span="2">
      <el-button
        icon="el-icon-video-camera"
        class="icon-m-p green-color-btn"
        @click="openVideo"
      ></el-button>
    </el-col>
    <el-col :span="20">
      <div>
        <el-input
          type="text"
          @keyup.enter="sendMessage"
          v-model="message"
          placeholder="메시지를 입력해주세요:)"
          clearable
        >
        </el-input>
      </div>
    </el-col>
    <el-col :span="2">
      <el-button
        @click="sendMessage"
        icon="el-icon-s-promotion"
        class="icon-m-p green-color-btn"
      ></el-button>
    </el-col>
  </el-row>
</template>
<script>
import { useStore } from 'vuex';
import { ref, computed, watch, onMounted } from 'vue';

export default {
  name: 'Chat',
  components: {},
  setup() {
    const store = useStore();
    const sessionId = computed(() => store.getters['get_selected_idx']);
    const messages = computed(() => store.getters.get_messages);
    const userPkidx = computed(() => store.state.auth.user.pk_idx);
    const chatStatus = computed(() => store.getters['statusGetter']);
    const stompClient = computed(() => store.getters['stompGetter']);
    const message = ref('');
    const scrollbar = ref('');
    watch(sessionId, () => {
      setTimeout(() => {
        scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
      }, 100);
    });
    watch(scrollbar, () => {
      store.commit('scrollbarSetter', scrollbar.value);
    });

    onMounted(() => {
      scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
    });
    const openVideo = () => {
      store.commit('OPEN_VIDEO');
      // 사용자에게 초대 메세지 보내기 메세지타입 VID
      send('VID');
    };

    const sendMessage = () => {
      if (userPkidx.value && message.value) {
        send('MSG');
      }
      message.value = '';
    };

    const send = (type) => {
      // console.log('Send message:' + message.value);
      if (stompClient.value && stompClient.value.connected) {
        console.log('IN SOCKET');
        let msg;
        if (type === 'VID') {
          msg = {
            message: '화상상담을 요청합니다.',
            fk_author_idx: userPkidx.value,
            created: '',
            deleted: false,
            fk_session_id: sessionId.value,
            type: type,
          };
        } else {
          msg = {
            message: message.value,
            fk_author_idx: userPkidx.value,
            fk_session_id: sessionId.value,
            type: type,
          };
        }
        stompClient.value.send('/receive/' + sessionId.value, JSON.stringify(msg), {});
      }
    };

    const closeRoom = () => {
      send('END');
      store.dispatch('chatClose', sessionId.value);
    };

    return {
      sessionId,
      messages,
      userPkidx,
      chatStatus,
      stompClient,
      message,
      scrollbar,
      openVideo,
      sendMessage,
      send,
      closeRoom,
    };
  },
};
</script>
<style scoped>
#topMessages {
  top: 0px;
  width: 50vw;
  height: 82vh;
}
#bottomInput {
  position: fixed;
  bottom: 10px;
  width: 50vw;
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
  /* background: #27251f; */
  background: #006f3e;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
#inputBox {
  /* width: 90%; */
  height: 100%;
  background-color: transparent;
  border: 0px solid #eee;
}
/* hover로 버튼 색 변하게 하기: 추가기능 */
.el-icon-error {
  float: right;
  font-size: 1.5rem;
  cursor: pointer;
}
.el-icon-close {
  float: right;
  font-size: 1.5rem;
  cursor: pointer;
}
</style>
