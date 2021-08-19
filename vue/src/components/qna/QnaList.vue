<template lang="">
  <div style="position: relative">
    <el-scrollbar ref="scrollbar" id="qcards">
      <div v-for="q in cards" :key="q.pk_idx" class="text item">
        <el-card class="box-card" @click="showDetail(q.pk_idx)">
          <span style="color: #606266">{{ q.title }}</span>
          <span v-if="q.title == undefined">시나리오 제목을 입력해주세요.</span>
        </el-card>
      </div>
    </el-scrollbar>
    <el-card class="box-card" @click="addScene" id="addButton">
      <i class="el-icon-circle-plus">&nbsp;<b>예상 시나리오 추가</b></i>
    </el-card>
  </div>
</template>
<script>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
export default {
  setup() {
    const store = useStore();
    store.dispatch('moduleQna/loadQna');
    const qDetail = '';
    const cards = computed(() => store.state.moduleQna.qnaList);
    const select = computed(() => store.state.moduleQna.select);
    const count = computed(() => store.getters['moduleQna/allQnaCount']);
    const scrollbar = ref('');
    const showDetail = (key) => {
      store.dispatch('moduleQna/pickQna', key);
      store.dispatch('moduleQna/loadAnswer', key);
      store.commit('moduleQna/resetNewAns');
    };
    const addScene = async () => {
      await store.dispatch('moduleQna/addQna');
      scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
    };
    return {
      store,
      qDetail,
      cards,
      select,
      count,
      scrollbar,
      showDetail,
      addScene,
    };
  },
};
</script>
<style scoped>
#qcards {
  top: 0px;
  width: 100%;
  height: 87vh;
}

#addButton {
  width: 100%;
  color: #006f3e;
}
</style>
