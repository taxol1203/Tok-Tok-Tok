<template lang="">
  <div>
    <!-- 질문 작성 칸 시작 -->
    <div id="questionBox">
      <el-input
        type="textarea"
        autosize
        v-model="select.content"
        id="inputBox"
        placeholder="예상 시나리오의 '질문'을 입력해주세요."
      >
      </el-input>
    </div>
    <!-- 질문 작성 칸 끝 -->
    <!-- 답변 작성 칸 시작 -->
    <div style="float: right; width: 100%">
      <OldAnswer />
      <NewAnswer />
    </div>
    <!-- 답변 작성 칸 끝 -->
  </div>
</template>

<script>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import OldAnswer from '@/components/qna/OldAnswer.vue';
import NewAnswer from '@/components/qna/NewAnswer.vue';

export default {
  components: {
    OldAnswer,
    NewAnswer,
  },
  setup() {
    const store = useStore();
    // const select = computed(() => store.state.moduleQna.qnaList);
    const select = computed(() => store.state.moduleQna.select);
    let show = ref(true);
    let changeShow = () => {
      show.value = !show.value;
    };
    const sendContent = () => {
      store.dispatch('moduleQna/editContent', select.value);
      // show.value = !show.value;
    };
    return {
      store,
      select,
      show,
      changeShow,
      sendContent,
    };
  },
};
</script>
<style>
#inputBox {
  /* background-color: transparent; */
  border: 0;
  resize: none;
}
#questionBox {
  border: 1px solid #eee;
  border-radius: 10px 10px 10px 0px;
  text-align: left;
  padding: 10px;
  margin: 20px;
  width: 500px;
}
</style>
