<template lang="">
  <el-container>
    <div style="width: 350px">
      <!-- <QnaList  /> -->
      <QnaList />
    </div>
    <el-main>
      <QnaEdit v-if="key > 0" />
    </el-main>
  </el-container>
</template>
<script>
import QnaList from '@/components/qna/QnaList.vue';
import QnaEdit from '@/components/qna/QnaEdit.vue';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  components: {
    QnaList,
    QnaEdit,
  },
  setup() {
    const store = useStore();
    const key = computed(() => store.getters['moduleQna/getKey']);
    return {
      store,
      key,
    };
  },
};
</script>

<style>
.el-aside {
  overflow: hidden;
}
</style>
