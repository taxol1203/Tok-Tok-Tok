<template lang="">
  <div style="border: 1px solid #eee; height: 500px">
    <div id="questionBox">
      <el-input
        v-if="!show"
        type="textarea"
        autosize
        v-model="select.content"
        id="question"
        style="margin: 10px; width: 500px"
        @click="changeShow"
      ></el-input>
      <el-row v-if="show" align="middle">
        <el-col :span="20">
          <el-input
            type="textarea"
            autosize
            v-model="select.content"
            id="question"
            placeholder="예상 질문을 입력해주세요."
          >
          </el-input>
        </el-col>
        <el-col :span="4"><el-button @click="sendContent()">등록</el-button></el-col>
      </el-row>
    </div>
    <!-- Question -->
    <div style="float: right">
      <!-- {{ answers }} -->
      <QnaAnswer />
    </div>
  </div>
</template>

<script>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import QnaAnswer from '@/components/qna/QnaAnswer.vue';

export default {
  components: {
    QnaAnswer,
  },
  setup() {
    const store = useStore();
    const select = computed(() => store.state.moduleQna.qnaList);
    let show = ref(select.content);
    let changeShow = () => {
      show.value = !show.value;
    };
    const key = computed(() => store.getters['moduleQna/setKey']);
    const title = computed(() => store.getters['moduleQna/setTitle']);

    const sendContent = () => {
      store.dispatch('moduleQna/editContent', {
        content: input.value,
        pk_idx: key.value,
        title: title.value,
      });
    };
    return {
      store,
      select,
      show,
      key,
      changeShow,
      sendContent,
    };
  },
};
</script>
<style>
#question {
  background-color: transparent;
  border: 0px solid;
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
