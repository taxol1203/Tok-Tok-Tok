<template>
  <div>
    <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#fff"
      text-color="black"
      active-text-color="#ffd04b"
    >
      <el-row>
        <el-col :span="2">
          <el-menu-item index="1">
            <img src="@/assets/mslogo.png" alt="" style="margin-bottom: 0.35rem" />
          </el-menu-item>
        </el-col>
        <el-col :span="2">
          <el-submenu index="2">
            <template #title>소프트웨어</template>
            <el-menu-item index="2-1">Windows 앱</el-menu-item>
            <el-menu-item index="2-2">OneDrive</el-menu-item>
            <el-menu-item index="2-3">OutLook</el-menu-item>
          </el-submenu>
        </el-col>
        <el-col :span="2">
          <el-submenu index="3">
            <template #title>Developer & IT</template>
            <el-menu-item index="3-1">.NET</el-menu-item>
            <el-menu-item index="3-2">Visual Studio</el-menu-item>
            <el-menu-item index="3-3">Windows Server</el-menu-item>
          </el-submenu>
        </el-col>
        <el-col :span="3" :offset="15">
          <el-menu-item v-if="user_pk_idx" index="10" id="loginbtn" @click="logout"
            >로그아웃</el-menu-item
          >
          <el-menu-item v-else index="10" id="loginbtn" @click="popUpLogin">로그인</el-menu-item>
        </el-col>
      </el-row>
    </el-menu>
    <img id="bg" src="@/assets/userBG.png" alt="" />
  </div>
  <div id="UserMain">
    <div class="fab-container">
      <el-button
        class="big-btn"
        type="primary"
        icon="el-icon-plus"
        @click="changeCondition"
        v-if="!isHidden && isLogin"
        circle
      ></el-button>
      <el-dialog title="" v-model="DialogVisible" width="30%" center>
        <div style="font-size: 18px; text-align: center">상담을 종료하시겠습니까?</div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="sendEnd" class="green-color-btn" style="width: 80px">네</el-button>
            <el-button @click="DialogVisible = false" style="width: 80px">아니오</el-button>
          </span>
        </template>
      </el-dialog>
      <transition class="same-pos" name="fade" mode="out-in">
        <div id="chat-box" v-if="isHidden">
          <div style="width: 100%; text-align: right">
            <i @click="DialogVisible = true" class="el-icon-error" id="close-btn"></i>
            <!-- <i @click="DialogVisible = true" class="el-icon-close" id="close-btn"></i> -->
          </div>
          <div>
            <el-card class="videoContainer" v-if="videoStatus != 'CLOSE'">
              <VideoChatDetail />
            </el-card>
            <el-card id="chat-card">
              <UserQna :close="isHidden" />
            </el-card>
          </div>
        </div>
      </transition>
    </div>
  </div>
  <el-dialog v-model="showModal" width="30%" :before-close="handleClose">
    <UserLogin />
  </el-dialog>
</template>
<script>
import { useStore } from "vuex";
import UserChatDetail from "./UserChatDetail.vue";
import UserQna from "./UserQna.vue";
import ChatDetail from "../../components/chat/ChatDetail.vue";
import VideoChatDetail from "@/components/VideoChat/VideoChatDetail.vue";
import { computed, ref, watch } from "vue";
import router from "@/router";
import UserLogin from "./UserLogin";
export default {
  components: {
    UserQna,
    UserChatDetail,
    ChatDetail,
    VideoChatDetail,
    UserLogin,
  },
  setup() {
    const store = useStore();
    let DialogVisible = ref(false);
    const isHidden = computed(() => store.getters["userQna/showUserChat"]);
    const isLogin = ref(false);
    const user_pk_idx = computed(() => store.state.auth.user.pk_idx);
    const sessionId = computed(() => store.getters["get_selected_idx"]);
    let stompClient = computed(() => store.getters["stompGetter"]);
    const showModal = ref(false);
    const videoStatus = computed(() => store.state.video_status);

    // 로그인 되어 있는 상태에서 새로고침할 때 대비
    if (user_pk_idx.value) {
      showModal.value = false;
      isLogin.value = true;
    }

    // modal창 자동으로 꺼지게 하는 부분
    watch(user_pk_idx, () => {
      if (user_pk_idx.value) {
        showModal.value = false;
        isLogin.value = true;
      }
    });

    const popUpLogin = () => {
      showModal.value = true;
    };
    const logout = () => {
      localStorage.clear();
      store.commit("auth/logout");
      isLogin.value = false;
      console.log(isLogin);
      console.log(isLogin.value);
    };
    const sendEnd = () => {
      send("END");
      store.commit("userQna/CHANGE_STATE");
      DialogVisible.value = false;
    };

    let changeCondition = () => {
      store.commit("userChatInit");
      store.dispatch("userQna/init");
      store.commit("userQna/CHANGE_STATE");
      DialogVisible.value = false;
    };
    let connected = false;

    const send = (type) => {
      if (
        sessionId.value &&
        stompClient.value &&
        stompClient.value.connected &&
        user_pk_idx.value > 0
      ) {
        const msg = {
          message: "",
          fk_author_idx: user_pk_idx.value,
          fk_session_id: sessionId.value,
          type: type,
        };
        stompClient.value.send("/receive/" + sessionId.value, JSON.stringify(msg), {});
      }
    };

    const goSignUp = () => {
      router.push("/usersignup");
    };
    const goLogin = () => {
      router.push("/userlogin");
    };
    return {
      DialogVisible,
      sessionId,
      user_pk_idx,
      isHidden,
      isLogin,
      connected,
      stompClient,
      videoStatus,
      showModal,
      changeCondition,
      sendEnd,
      send,
      goSignUp,
      goLogin,
      popUpLogin,
      logout,
    };
  },
};
</script>
<style scoped>
#bg {
  z-index: 1;
}
.fab-container {
  position: fixed;
  bottom: 50px;
  right: 50px;
  z-index: 999;
  cursor: pointer;
}
#chat-box {
  height: 52.5rem;
  width: auto;
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

.same-pos {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

#chat-card {
  border-radius: 1rem;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  border: none;
  height: 45rem;
  display: inline-block !important;
  width: 25rem;
}
.videoContainer {
  border-radius: 1rem;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  background-color: #fff;
  border: none;
  height: 50rem;
  margin-right: 10px;
  width: 26rem;
  display: inline-block !important;
}
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
}
#loginbtn {
  right: 0;
  color: #000 !important;
}
</style>
