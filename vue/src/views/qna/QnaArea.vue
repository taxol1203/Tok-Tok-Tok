<template lang="">
  <div>
    [QnaArea]
    <QnaList />
    <QnaEdit />
  </div>
</template>
<script>
import QnaList from "../../components/qna/QnaList.vue";
import QnaEdit from "../../components/qna/QnaEdit.vue";

export default {
  components: {
    QnaList,
    QnaEdit,
  },
};
</script>

<style lang=""></style>
