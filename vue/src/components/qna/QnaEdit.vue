<template lang="">
  <el-container>
    <el-header><QnaTitle /></el-header>
    <el-main>
      <QnaDetail />
    </el-main>
  </el-container>
</template>
<script>
import QnaTitle from '../../components/qna/QnaTitle.vue';
import QnaDetail from '../../components/qna/QnaDetail.vue';

export default {
  components: {
    QnaTitle,
    QnaDetail,
  },
};
</script>
<style lang=""></style>
