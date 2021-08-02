<template lang="">
  <div style="border: 1px solid #eee; height: 500px">
    <div
      id="question"
      style="
        border: 1px solid #eee;
        border-radius: 10px 10px 10px 0px;
        text-align: left;
        padding: 10px;
        margin: 20px;
        width: 500px;
      "
    >
      <el-input
        type="textarea"
        autosize
        v-model="select.content"
        id="question"
        v-if="!show"
        style="margin: 10px; width: 500px"
        @click="changeShow"
      ></el-input>
      <el-row v-if="show" align="middle" gutter="10">
        <el-col :span="20">
          <el-input
            type="textarea"
            autosize
            placeholder="예상 질문을 입력해주세요."
            v-model="select.content"
            id="question"
          >
          </el-input>
        </el-col>
        <el-col :span="4"><el-button @click="changeShow()">등록</el-button></el-col>
      </el-row>
    </div>
    <!-- Question -->
    <div style="float: right">
      <!-- {{ answers }} -->
      <QnaAnswer v-for="oa in old_answer" :key="oa" />
      <QnaAnswer v-for="a in answers.answer" :key="a" />
      <el-button @click="add" class="colorVer" style="float: right; margin-top: 10px"
        ><i class="el-icon-plus"></i
      ></el-button>
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
    const select = computed(() => store.state.moduleQna.select);
    const old_answer = computed(() => store.state.moduleQna.old_answer);
    const add = () => {
      if (answers.value.answer.length < 5) answers.value.answer.push('test');
      else alert('예상 답변은 최대 5개 추가 가능합니다.');
    };
    let show = ref(true);
    let answers = ref({
      answer: [],
    });
    let changeShow = () => {
      show.value = !show.value;
    };
    return {
      old_answer,
      answers,
      show,
      store,
      select,
      add,
      changeShow,
    };
  },
};
</script>
<style>
#question {
  background-color: transparent;
  border: 0px solid;
}
</style>
