<template>
  <el-row :gutter="20">
    <div id="DummyMain">
      <div class="fab-container">
        <div>
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
          <transition class="same-pos" name="fade" mode="out-in">
            <div id="chat-box" v-if="isHidden">
              <div class="full-box">
                <ChatDetail />
                <!-- <ChatDetailDummy /> -->
                <p>현재 chat socket 연결 문제로 주석처리해둠</p>
                <el-button @click="createChatRoom" class="green-color-btn"
                  >채팅방개설요청</el-button
                >
                <el-button @click="changeCondition" type="danger" icon="el-icon-close"
                  >닫기</el-button
                >
              </div>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </el-row>
</template>
<script>
import ChatDetailDummy from "./ChatDetailDummy.vue";
import ChatDetail from "../../../src/components/chat/ChatDetail.vue";
import { ref } from "vue";
/* eslint-disable */
export default {
  components: {
    ChatDetail,
    ChatDetailDummy,
  },

  setup() {
    let isHidden = ref(false);

    let changeCondition = () => {
      isHidden.value = !isHidden.value;
    };
    let createChatRoom = () => {
      console.log("USER: CREATE CHAT ROOM");
      console.log("현재 실제 생성되는 코드는 주석처리 해둠");
      // store.dispatch("createChatRooms");
    };
    return {
      isHidden,
      changeCondition,
      createChatRoom,
    };
  },
};
</script>
<style scoped>
#DummyMain {
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
  width: 650px;
  height: 750px;
  padding: 10px;
  background-color: gray;
  /* 상속 받을 수 있나? */
}

.big-btn {
  width: 80px;
  height: 80px;
  /* icon 사이즈 키우는 법 */
  font-size: 2rem;
  background-color: #006f3e;
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

/* 위치 고정을 시키지 않으면 렌더링하면서 (생명&소멸) 서로 다른 공간에 보여짐 */
.same-pos {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

.full-box {
  box-sizing: border-box;
}
</style>
