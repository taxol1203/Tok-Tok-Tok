<template lang="">
  <div>
    <!-- key 13 입력/ 버튼 : 질문 fix  -->
    <el-input
      v-if="!check"
      type="text"
      placeholder="예상 질문 제목 편집 창"
      v-model="input"
      @keyup.enter="hiddenInput()"
    ></el-input>
    <!-- <h1 v-if="check" style="text-align: left">{{ input }}</h1>  :span="23" :offset="0"-->
    <el-row :gutter="20" v-if="check">
      <el-col>
        <p class="qna-title border-solid">{{ input }}</p>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      input: '', //props로 처리해서 edit에서 가지고 있어야할듯...
      check: false,
    };
  },
  methods: {
    hiddenInput() {
      this.check = !this.check;
    },
  },
};
</script>
<style scoped>
.qna-title {
  font-size: 200%;
  font-family: 'BMJUA', Times, serif;
  text-align: left;
  color: white;
  background-color: rgb(0, 112, 74);
}
.border-solid {
  border: solid 1px;
  border-radius: 4px;
  box-shadow: 0 2px 24px 0 rgba(0, 0, 0, 0.1);
  padding: 1px;
  margin: 5px 1px 5px 1px;
}
</style>
