<template lang="">
  <div>
    Admin
    <el-row>
      <el-col :span="3">
        <NavBar />
      </el-col>
      <ChatArea />
      <QnaArea />
    </el-row>
  </div>
</template>
<script>
import NavBar from './NavBar.vue';
import ChatArea from './chat/ChatArea.vue';
import QnaArea from './qna/QnaArea.vue';

export default {
  components: {
    NavBar,
    ChatArea,
    QnaArea,
  },
};
</script>
<style lang=""></style>
