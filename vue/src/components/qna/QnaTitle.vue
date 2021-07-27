<template lang="">
  <div>
    <!-- key 13 입력/ 버튼 : 질문 fix  -->
    <el-input
      v-if="!check"
      type="text"
      placeholder="예상 질문 제목 편집 창"
      v-model="select.content"
      @keyup.enter="hiddenInput()"
    ></el-input>
    <!-- <h1 v-if="check" style="text-align: left">{{ input }}</h1>  :span="23" :offset="0"-->
    <el-row :gutter="20" v-if="check">
      <el-col>
        <p class="qna-title border-solid" @click="hiddenInput">{{ select.content }}</p>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { computed } from 'vue';
export default {
  setup() {
    const store = useStore();
    const select = computed(() => store.state.moduleQna.select);
    const check = false;
    const hiddenInput = () => {
      this.check = !this.check;
    };
    return {
      store,
      check,
      select,
      hiddenInput,
    };
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
