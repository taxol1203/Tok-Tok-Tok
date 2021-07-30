<template lang="">
  <div>
    <el-container>
      <el-aside><ChatList :test="test" /></el-aside>
      <el-container>
        <!-- <el-header v-if="store.state.selected_room"><UserTitle /></el-header> -->
        <!-- <el-main> -->
        <!-- <el-container> -->
        <transition name="chat-change" mode="out-in">
          <el-main v-if="store.state.selected_room"><ChatDetail /></el-main>
        </transition>
        <transition name="chat-change" mode="out-in">
          <el-aside v-if="store.state.selected_room"><UserInfo /></el-aside>
        </transition>
        <!-- </el-container> -->
        <!-- </el-main> -->
      </el-container>
    </el-container>
  </div>
</template>
<script>
import ChatList from '../../components/chat/ChatList.vue';
// import UserTitle from '../../components/chat/UserTitle.vue';
import ChatDetail from '../../components/chat/ChatDetail.vue';
import UserInfo from '../../components/chat/UserInfo.vue';
import { useStore } from 'vuex';

export default {
  components: {
    ChatList,
    // UserTitle,
    ChatDetail,
    UserInfo,
  },
  setup() {
    const test = 'abcdef';
    const store = useStore();

    return {
      test,
      store,
    };
  },
};
</script>
<style>
/* .el-container {
  border: 0px solid #eee;
} */
.el-main {
  padding: 0;
}
/* 생성 부분 */
.chat-change-enter-from {
  opacity: 0;
}
.chat-change-enter-to {
  opacity: 1;
}
.chat-change-enter-active {
  transition: all 0.2s ease-out;
}
/* 소멸 부분 */
.chat-change-leave-from {
  opacity: 1;
}
.chat-change-leave-to {
  opacity: 0;
}
.chat-change-leave-active {
  transition: all 0.2s ease-out;
}
</style>
