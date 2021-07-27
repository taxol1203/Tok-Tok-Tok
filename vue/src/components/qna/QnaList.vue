<template lang="">
  <div>
    <el-scrollbar height="600px">
      <h1>All cards({{ count }})</h1>
      <div v-for="q in cards" :key="q.pk_idx" class="text item">
        <el-card class="box-card" @click="showDetail(q.pk_idx)">
          {{ q.content }}
        </el-card>
      </div>
    </el-scrollbar>
  </div>
  <el-card class="box-card" @click="addScene">
    <i class="el-icon-plus"></i>
  </el-card>
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
    };
    const addScene = () => {
      let tmp = {
        pk_idx: count.value,
        content: 'dummy' + count.value,
        answers: [],
      };
      console.log(tmp);
      if (count.value <= 10) store.dispatch('moduleQna/addQna', tmp);
      else alert('시나리오는 최대 10개 추가할 수 있습니다.');
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
  // computed: {
  //   ...mapGetters({
  //     count: 'allQnaCount', //이 컴포넌트에서는 allQnaCount를 count로 쓰겠다
  //   }),
  //   ...mapState({
  //     cards: 'qnaList',
  //     select: 'select',
  //   }),
  // },
  // methods: {
  //   ...mapActions(['addQna', 'loadQna', 'pickQna']),
  //   showDetail(key) {
  //     this.pickQna(key);
  //   },
  //   addScene() {
  //     let tmp = {
  //       pk_idx: this.count,
  //       content: 'dummy' + this.count,
  //       answers: [],
  //     };
  //     if (this.count <= 10) this.addQna(tmp);
  //     else alert('시나리오는 최대 10개 추가할 수 있습니다.');
  //   },
  // },
};
</script>
