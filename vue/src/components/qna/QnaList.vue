<template lang="">
  <div>
    <el-scrollbar height="600px">
      <h1>All cards({{ count }})</h1>
      <div v-for="q in cards" :key="q.id" class="text item">
        <el-card class="box-card" @click="showDetail(q.q_idx)">
          {{ q.contents }}
        </el-card>
      </div>
    </el-scrollbar>
  </div>
  <el-card class="box-card" @click="addScene()">
    <i class="el-icon-plus"></i>
  </el-card>
</template>
<script>
import { mapGetters, mapState, mapActions } from 'vuex';
export default {
  data() {
    return {
      propSelect: this.select,
      qDetail: '',
    };
  },
  computed: {
    ...mapGetters({
      count: 'allQnaCount', //이 컴포넌트에서는 allQnaCount를 count로 쓰겠다
    }),
    ...mapState({
      cards: 'qnaList',
    }),
    // ...mapGetters(['allQnaCount']),
  },
  methods: {
    // ...mapMutations(['addQna']),
    ...mapActions(['addQna']),
    showDetail(key) {
      this.qDetail = key;
      this.propSelect = key;
      console.log(this.qDetail);
      // this.$emit('child', this.propSelect);
    },
    addScene() {
      let tmp = {
        q_idx: this.count,
        contents: 'dummy' + this.count,
        answers: [],
      };
      // if (this.qnaList.length < 8) this.qnaList.push(tmp);   // 0. 컴포넌트 내에서 사용하는 방법
      // if (this.count <= 10) this.addQna(tmp);                // 1. mapMutations 사용하는 방법
      // 2. 바로 mutations에 접근하는 방법
      if (this.count <= 10) this.addQna(tmp);
      else alert('시나리오는 최대 10개 추가할 수 있습니다.');
    },
    setList() {
      this.$store.dispatch('qna/setList');
    },
  },
};
</script>
<style lang=""></style>
