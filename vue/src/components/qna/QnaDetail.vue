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
        v-model="title"
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
            v-model="title"
            id="question"
          >
          </el-input>
        </el-col>
        <el-col :span="4"><el-button @click="changeShow()">등록</el-button></el-col>
      </el-row>
    </div>
    <!-- Question -->
    <div style="float: right">
      <QnaAnswer v-for="a in answers" :key="a" />
      <el-button @click="add" style="float: right; margin-top: 10px"
        ><i class="el-icon-plus"></i
      ></el-button>
    </div>
  </div>
</template>

<script>
import QnaAnswer from '../../components/qna/QnaAnswer.vue';

export default {
  components: {
    QnaAnswer,
  },
  data() {
    return {
      answers: [],
      title: '',
      textarea: '',
      show: true,
    };
  },
  methods: {
    add() {
      if (this.answers.length < 5) this.answers.push('test');
      else alert('예상 답변은 최대 5개 추가 가능합니다.');
    },
    changeShow() {
      this.show = !this.show;
      this.title = this.title.replace('\r\n', '~');
      console.log(this.title);
    },
  },
};
</script>
<style>
#question {
  background-color: transparent;
  border: 0px solid;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei',
    '微软雅黑', Arial, sans-serif;
}
</style>
