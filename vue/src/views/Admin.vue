<template lang="">
  <div>
    Admin
    <NavBar />
    <ChatArea />
    <QnaArea />
  </div>
</template>
<script>
import NavBar from "./NavBar.vue";
import ChatArea from "./chat/ChatArea.vue";
import QnaArea from "./qna/QnaArea.vue";

export default {
  components: {
    NavBar,
    ChatArea,
    QnaArea,
  },
};
</script>
<style lang=""></style>
