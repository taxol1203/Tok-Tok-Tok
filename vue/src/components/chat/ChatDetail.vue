<template>
  <div style="position: relative; width: 650px; height: 750px; padding: 10px">
    <!-- <i class="el-icon-close"></i> -->
    <i v-if="chatStatus == 'LIVE'" class="el-icon-error" @click="closeRoom"></i>
    <!-- 상대방 -->
    <el-scrollbar ref="scrollbar" id="topMessages">
      <div v-for="(msg, index) in messages" :key="index">
        <el-row>
          <el-col v-if="msg.fk_author_idx == userPkidx">
            <div class="message-me" v-if="msg.type == 'MSG'">
              {{ msg.message }}
            </div>
          </el-col>
          <el-col v-else>
            <div class="message-other" v-if="msg.type == 'MSG'">
              {{ msg.message }}
            </div>
          </el-col>
        </el-row>
      </div>
    </el-scrollbar>
    <div v-if="chatStatus != 'END'">
      <el-row id="bottomInput">
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
              placeholder="Please input"
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
    </div>
  </div>
</template>
<script>
// import axios from "axios";

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
    onMounted(() => {
      scrollbar.value.setScrollTop(999999999999999999999);
    });
    const openVideo = () => {
      store.commit('OPEN_VIDEO');
      // 사용자에게 초대 메세지 보내기 메세지타입 VID
      send('VID');
    };

    const sendMessage = () => {
      if (userPkidx.value && message.value) {
        send('MSG'); // 전송 실패 감지는 어떻게? 프론트단에서 고민좀 부탁 dream
        // 관리자가 첫 메세지 보냈을때 방상태를 LIVE로 바꾸기
        // 보냈을 때 바로 바꾸니까 관리자 측에서 메시지를 받을 때 잃어버리는 경우가 있어서 받을때 바꾸는 것으로 해결했습니다 -소빈
        // 'MESSAGE_PUSH' 커밋 시 변경
        // if (store.state.rooms[`${sessionId.value}`].session.status == 'OPEN') {
        //   store.dispatch('enterRoom', sessionId);
        // }
      }
      message.value = '';
      // 어차피 유저는 채팅 상담 신청을 한뒤 메세지를 치지 못하므로 open 상태에서 메세지를 보내는건 관리자뿐이니까
      // 방상태가 OPEN일때 여기서 put을 보내면되나
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
            message: message.value, // 메세지 내용. type이 MSG인 경우를 제외하곤 비워두고 프론트단에서만 처리.
            fk_author_idx: userPkidx.value, // 작성자의 회원 idx
            created: '', // 작성시간, 공란으로 비워서 메세지 보내기. response에는 담겨옵니다.
            deleted: false, // 삭제된 메세지 여부. default = false
            fk_session_id: sessionId.value, // 현재 채팅세션의 id.
            // 주의할 점은, 방 세션 id가 아닌, 방 정보의 pk_idx를 첨부한다. created 라이프사이클 메서드 참조.
            type: type, // 메세지 타입.
          };
        }
        stompClient.value.send('/receive/' + sessionId.value, JSON.stringify(msg), {});
      }
    };

    const closeRoom = () => {
      // 방 닫는 로직 작성 "admin_pk_idx": 0 넣어서 요청 해줘야함
      // 방 상태가 LIVE 일때, admin_pk_idx가 나와 같을때
      send('END');
      store.dispatch('chatClose', sessionId.value);
    };

    return {
      store,
      chatStatus,
      sessionId,
      messages,
      message,
      scrollbar,
      sendMessage,
      send,
      stompClient,
      userPkidx,
      closeRoom,
      openVideo,
    };
  },
};
</script>
<style scoped>
#topMessages {
  display: block;
  top: 0px;
  height: 700px;
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
  border: 1px solid #27251f;
  border-radius: 10px 10px 10px 0px;
  color: #fff;
  background: #27251f;
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
