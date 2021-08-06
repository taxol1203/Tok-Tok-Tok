<template>
  <div style="position: relative; width: 50%; height: 50%; padding: 10px">
    <!-- 상대방 -->
    <el-scrollbar ref="scrollbar" id="topMessages">
      <div v-for="(item, index) in log" :key="index">
        <el-row>
          <el-col>
            <div class="message-other" v-html="item.content"></div>
          </el-col>
        </el-row>
        <el-row v-for="ans in item.answers" :key="ans.pk_idx">
          <el-col>
            <div class="message-me" @click="chooseAnswer(ans.fk_next_idx)">{{ ans.content }}</div>
          </el-col>
        </el-row>
      </div>
      <!-- v-html="question"<div v-for="answer in answers" :key="answer.pk_idx">
        <el-row>
          <el-col>
            <div class="message-me" @click="chooseAnswer(answer.fk_next_idx)">
              {{ answer.content }}
            </div>
          </el-col>
        </el-row>
      </div> -->
    </el-scrollbar>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  name: 'Chat',
  components: {},
  setup() {
    const store = useStore();
    store.commit('userQna/CHANGE_SELECT', 1);
    store.commit('userQna/SET_CURRENT');
    const log = computed(() => store.getters['userQna/logGetter']);
    const chooseAnswer = (next_idx) => {
      store.commit('userQna/CHANGE_SELECT', next_idx);
      store.commit('userQna/ADD_LOG');
    };
    return {
      log,
      chooseAnswer,
    };
  },
};
</script>
<style>
#bottomInput {
  bottom: 0px;
  width: 100%;
}
#topMessages {
  display: block;
  top: 0px;
  height: 300px;
  width: 100%;
}
.el-scroll {
  overflow-x: hidden;
}
.message-me {
  border: 1px solid #f7f4f0;
  border-radius: 10px 10px 0px 10px;
  background: #f7f4f0;
  float: right;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
.message-other {
  border: 1px solid #27251f;
  border-radius: 10px 10px 10px 0px;
  color: #fff;
  background: #27251f;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
#inputBox {
  width: 90%;
  height: 100%;
  background-color: transparent;
  border: 0px solid #eee;
}
/* hover로 버튼 색 변하게 하기: 추가기능 */
</style>
