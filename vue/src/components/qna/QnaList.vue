<template lang="">
  <div>
    {{ select }}
    <el-scrollbar height="600px">
      <!-- 시작 카드 시작 -->
      <el-card class="box-card" @click="showDetail(start.contents)">
        {{ start.contents }}
      </el-card>
      <!-- 시작 카드 끝 -->
      <div v-for="q in qList" :key="q.q_idx" class="text item">
        <el-card class="box-card" @click="showDetail(q.contents)">
          {{ q.contents }}
        </el-card>
      </div>
      <!-- 종료 카드 시작 -->
      <el-card class="box-card" @click="showDetail(end.contents)">
        {{ end.contents }}
      </el-card>
      <!-- 종료 카드 끝 -->
    </el-scrollbar>
  </div>
  <el-card class="box-card" @click="addScene()">
    <i class="el-icon-plus"></i>
  </el-card>
</template>
<script>
export default {
  props: ['select'],
  data() {
    return {
      propSelect: this.select,
      qDetail: '',
      start: {
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
      end: {
        q_idx: '2',
        contents: '종료합니다.',
        answers: [],
      },
      qList: [],
    };
  },
  methods: {
    showDetail(key) {
      this.qDetail = key;
      this.propSelect = key;
      console.log(this.qDetail);
      this.$emit('child', this.propSelect);
    },
    addScene() {
      let tmp = {
        q_idx: '3',
        contents: 'dummy' + this.qList.length,
        answers: [],
      };
      if (this.qList.length < 8) this.qList.push(tmp);
      else alert('시나리오는 최대 10개 추가할 수 있습니다.');
    },
  },
};
</script>
<style lang=""></style>
