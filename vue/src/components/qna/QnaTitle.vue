<template lang="">
  <div>
    <el-input
      v-if="check"
      type="text"
      placeholder="예상 시나리오의 '제목'을 입력해주세요."
      v-model="select.title"
      style="font-size: 17px"
    ></el-input>
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
