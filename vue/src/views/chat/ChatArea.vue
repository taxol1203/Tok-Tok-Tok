<template lang="">
  <div>
    <el-container>
      <el-aside style="width: 300px; border: 1px solid #eee"><ChatList :test="test" /></el-aside>
      <el-container>
        <el-header><UserTitle /></el-header>
        <el-main>
          <el-container>
            <el-main><ChatDetail /></el-main>
            <el-aside><UserInfo /></el-aside>
          </el-container>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import ChatList from '../../components/chat/ChatList.vue'
import UserTitle from '../../components/chat/UserTitle.vue'
import ChatDetail from '../../components/chat/ChatDetail.vue'
import UserInfo from '../../components/chat/UserInfo.vue'

export default {
  components: {
    ChatList,
    UserTitle,
    ChatDetail,
    UserInfo,
  },
  setup() {
    const test = 'abcdef'
    return {
      test,
    }
  }
};
</script>
<style>
el-container {
  border: 1px solid #eee;
}
</style>
