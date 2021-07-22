<template lang="">
  <el-container>
    <el-aside style="width: 300px; border: 1px solid #eee">
      {{ child }}
      <QnaList @child="update" :select="select" />
    </el-aside>
    <el-main style="width: 300px; border: 1px solid #eee">
      <QnaEdit :select="select" />
    </el-main>
  </el-container>
</template>
<script>
import QnaList from '../../components/qna/QnaList.vue';
import QnaEdit from '../../components/qna/QnaEdit.vue';

export default {
  data() {
    return {
      select: 'test',
    };
  },
  components: {
    QnaList,
    QnaEdit,
  },
  methods: {
    update(select) {
      console.log('parent:' + this.select);
      this.select = select;
    },
  },
};
</script>

<style lang=""></style>
