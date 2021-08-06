<template>
  <el-row :gutter="20">
    <div id="UserMain">
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
        <transition class="same-pos" name="fade" mode="out-in">
          <div id="chat-box" v-if="isHidden">
            <el-card :body-style="{ padding: '0px' }">
              <div id="card-head" class="card-header">
                <el-button @click="changeCondition" type="danger" icon="el-icon-close"
                  >닫기</el-button
                >
              </div>
              <div class="full-box">
                <div>QnA List</div>
                <el-button @click="createChatRoom" class="green-color-btn"
                  >채팅방개설요청/상담사상담신청 등</el-button
                >
                <p>채팅방개설버튼은 나중에 말풍선같은 걸로 qna 밑에 위치하도록</p>
                <div v-if="isChatExist">
                  <UserChatDetail />
                </div>
              </div>
            </el-card>
          </div>
        </transition>
      </div>
    </div>
  </el-row>
</template>
<script>
import axios from "@/axios";
import { useStore } from "vuex";
import UserChatDetail from "./UserChatDetail.vue";
import ChatDetail from "../../components/chat/ChatDetail.vue";
import { computed, ref } from "vue";
/* eslint-disable */
export default {
  components: {
    ChatDetail,
    UserChatDetail,
  },

  setup() {
    let isChatExist = ref(false);
    let isHidden = ref(false);
    const store = useStore();
    const user_pk_idx = computed(() => store.state.auth.user.pk_idx);
    const sessionId = computed(() => store.state.user_selected_room);

    let changeCondition = () => {
      isHidden.value = !isHidden.value;
    };
    let changeisChatExist = () => {
      isChatExist.value = !isChatExist.value;
    };
    // 유저의 채팅방 개설요청
    let createChatRoom = () => {
      store.dispatch("createChatRooms", user_pk_idx.value);
      isChatExist.value = true;
    };
    return {
      store,
      sessionId,
      user_pk_idx,
      isChatExist,
      isHidden,
      changeisChatExist,
      changeCondition,
      createChatRoom,
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
  /* width: 360px;
  height: 720px; */
  width: 25rem; /* 1rem = 16px */
  height: 40rem;
  padding: 10px;
  position: sticky;
  background-color: gray;
  /* 상속 받을 수 있나? */
  border-radius: 4px;
  /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

el-card {
  width: 100%;
  height: 100%;
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
  width: auto;
  height: auto;
  padding: 14px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #006f3e;
  /* background: linear-gradient(103deg, #258c60 0%, #006f3e 50%, #9ec8ac) */
  /* background: linear-gradient(103deg, rgb(74, 38, 255) 0%, rgb(74, 38, 255) 50%, rgb(125, 38, 255)) */
  /* 100% center; */
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  /* width: 480px; */
}
</style>
