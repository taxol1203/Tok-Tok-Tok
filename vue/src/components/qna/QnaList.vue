<template lang="">
  <div id="container">
    <el-scrollbar id="qcards">
      <div v-for="q in cards" :key="q.pk_idx" class="text item">
        <el-card class="box-card" @click="showDetail(q.pk_idx)">
          {{ q.title }}
        </el-card>
      </div>
    </el-scrollbar>
    <el-card class="box-card" @click="addScene" id="addButton">
      <i class="el-icon-plus"></i>
    </el-card>
  </div>
</template>
<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
export default {
  setup() {
    const store = useStore();
    store.dispatch('moduleQna/loadQna');
    const qDetail = '';
    const cards = computed(() => store.state.moduleQna.qnaList);
    const select = computed(() => store.state.moduleQna.select);
    const count = computed(() => store.getters['moduleQna/allQnaCount']);
    const showDetail = (key) => {
      store.dispatch('moduleQna/pickQna', key);
      store.dispatch('moduleQna/loadAnswer', key);
    };
    const addScene = () => {
      let tmp = {
        pk_idx: count.value,
        content: '',
        answers: [],
      };
      console.log(tmp);
      // if (count.value <= 10)
      store.dispatch('moduleQna/addQna', tmp);
      // else alert('시나리오는 최대 10개 추가할 수 있습니다.');
    };
    return {
      store,
      qDetail,
      cards,
      select,
      count,
      showDetail,
      addScene,
    };
  },
};
</script>
<style scoped>
#qcards {
  display: block;
  top: 0px;
  height: 700px;
  width: 100%;
}
#container {
  position: relative;
  height: 750px;
  background: #f7f4f0;
}
.el-scroll {
  overflow: visible;
}
#addButton {
  bottom: 0px;
  width: 100%;
}
</style>
