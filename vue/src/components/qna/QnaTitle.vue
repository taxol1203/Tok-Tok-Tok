<template lang="">
  <div>
    <!-- key 13 입력/ 버튼 : 질문 fix  -->
    <el-input
      v-if="check"
      type="text"
      placeholder="예상 질문 제목 편집 창"
      v-model="select.title"
    ></el-input>
    <!-- <h1 v-if="check" style="text-align: left">{{ input }}</h1>  :span="23" :offset="0"-->
    <!-- <h1 v-if="check" class="qna-title" @click="hiddenInput">{{ select.title }}</h1> -->
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { computed, ref } from 'vue';
export default {
  setup() {
    const store = useStore();
    const select = computed(() => store.state.moduleQna.select);
    let check = ref(select.title == null);
    return {
      store,
      check,
      select,
    };
  },
};
</script>
<style scoped>
.qna-title {
  text-align: left;
}
</style>
