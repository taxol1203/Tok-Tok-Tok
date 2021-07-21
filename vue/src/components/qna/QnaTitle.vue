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
    <h1 v-if="check" style="text-align: left">{{ input }}</h1>
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
<style lang=""></style>
