<template lang="">
  <div>[QnaList]</div>
  <div v-for="q in qList" :key="q.q_idx" class="text item">
    <el-card class="box-card" @click="showDetail(key)">
      {{ q.contents }}
    </el-card>
  </div>
  <el-card class="box-card" @click="addScene()">시나리오 추가</el-card>
</template>
<script>
export default {
  data() {
    return {
      qDetail: '',
      qList: [
        {
          q_idx: '1',
          contents: '시작합니다.',
          answers: [
            {
              q_idx: '1-1',
              contents: '예상답변1',
              fk_idx: '2',
            },
            {
              q_idx: '1-2',
              contents: '예상답변2',
              fk_idx: '2',
            },
          ],
        },
        {
          q_idx: '2',
          contents: '종료합니다.',
          answers: [],
        },
      ],
      // qCount: this.qList.length,
    };
  },
  methods: {
    showDetail(key) {
      this.qDetail = key;
    },
    addScene() {
      let tmp = {
        q_idx: '3',
        contents: 'dummy',
        answers: [],
      };
      if (this.qList.length < 10) this.qList.push(tmp);
      else alert('시나리오는 최대 10개 추가할 수 있습니다.');
      console.log(this.qList);
    },
  },
};
</script>
<style lang=""></style>
