<template lang="">
  <el-container>
    <el-aside>
      <!-- <QnaList  /> -->
      <QnaList />
    </el-aside>
    <el-main>
      <QnaEdit />
    </el-main>
  </el-container>
</template>
<script>
import QnaList from '../../components/qna/QnaList.vue';
import QnaEdit from '../../components/qna/QnaEdit.vue';

export default {
  components: {
    QnaList,
    QnaEdit,
  },
};
</script>

<style>
.el-aside {
  overflow: hidden;
}
</style>
