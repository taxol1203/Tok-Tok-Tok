<template lang="">
  <el-container>
    <el-header>
      <h1>All cards({{ this.$store.getters.allQnaCount }})</h1>
      <el-row gutter="20">
        <el-col span="4">
          <el-button type="primary" icon="el-icon-edit" circle></el-button>
          <!-- edit btn: 수정 모드 -->
          <el-button type="success" icon="el-icon-check" circle></el-button>
          <!-- check btn: 저장 모드(서버로 현재 qList 전송) -->
        </el-col>
        <el-col span="20"><QnaTitle /></el-col>
      </el-row>
    </el-header>
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
