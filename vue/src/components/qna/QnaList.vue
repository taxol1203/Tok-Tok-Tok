<template lang="">
  <div>
    <el-scrollbar height="600px">
      <!-- <h1>All cards({{ count }}) {{ select.pk_idx }}</h1> -->
      <div v-for="q in cards" :key="q.pk_idx" class="text item">
        <el-card class="box-card" @click="showDetail(q.pk_idx)">
          {{ q.content }}
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
  created() {
    this.loadQna();
  },
  data() {
    return {
      qDetail: '',
    };
  },
  computed: {
    ...mapGetters({
      count: 'allQnaCount', //이 컴포넌트에서는 allQnaCount를 count로 쓰겠다
    }),
    ...mapState({
      cards: 'qnaList',
      select: 'select',
    }),
  },
  methods: {
    ...mapActions(['addQna', 'loadQna', 'pickQna']),
    showDetail(key) {
      this.pickQna(key);
    },
    addScene() {
      let tmp = {
        q_idx: this.count,
        content: 'dummy' + this.count,
        answers: [],
      };
      if (this.count <= 10) this.addQna(tmp);
      else alert('시나리오는 최대 10개 추가할 수 있습니다.');
    },
  },
};
</script>
<style lang=""></style>
