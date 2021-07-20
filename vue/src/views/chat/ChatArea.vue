<template lang="">
  <div>
    [ChatArea]
    <ChatList />
    <UserTitle />
    <ChatDetail />
    <UserInfo />
  </div>
</template>
<script>
import ChatList from '../../components/chat/ChatList.vue';
import UserTitle from '../../components/chat/UserTitle.vue';
import ChatDetail from '../../components/chat/ChatDetail.vue';
import UserInfo from '../../components/chat/UserInfo.vue';

export default {
  components: {
    ChatList,
    UserTitle,
    ChatDetail,
    UserInfo,
  },
};
</script>
<style lang=""></style>
