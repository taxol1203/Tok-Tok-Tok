<template lang="">
  <el-container>
    <el-aside style="width: 300px; border: 1px solid #eee"><QnaList /></el-aside>
    <el-main style="width: 300px; border: 1px solid #eee">
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

<style lang=""></style>
