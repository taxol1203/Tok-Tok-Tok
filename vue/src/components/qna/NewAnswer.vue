<template lang="">
  <div
    v-for="(oa, index) in new_answer"
    :key="oa.pk_idx"
    style="padding-right: 10px; padding-top: 5px"
  >
    <div style="width: 70%; display: inline-block">
      <div class="answerBox">
        <div>
          <!-- @keyup.enter="changeShow()"<p v-if="!show" style="margin: 10px" @click="changeShow" v-bind="input.value"></p> -->
          <el-input
            class="ans"
            type="textarea"
            placeholder="예상 '답변'을 입력해주세요."
            v-if="show"
            v-model="oa.content"
            clearable
            autosize
          >
          </el-input>
          <!-- 예상답변 -->
        </div>
      </div>
    </div>
    <div style="float: right">
      <el-row>
        <el-col :span="20">
          <!-- 다음 시나리오 select style="border: 1px solid #dcdfe6; border-radius: 5px 5px 5px 5px"-->
          <el-select
            v-model="selectValue[index]"
            placeholder="이어질 시나리오"
            @change="setNextIdx(index)"
          >
            <el-option
              v-for="item in qnaList"
              :key="item.pk_idx"
              :label="item.title"
              :value="item.pk_idx"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button
            type="danger"
            icon="el-icon-delete-solid"
            circle
            style="border: 0px solid; background-color: transparent; color: #006f3e"
            @click="removeAnswer(index)"
          ></el-button>
        </el-col>
        <!-- {{ oa.fk_next_idx }} el-icon-delete-->
      </el-row>
    </div>
  </div>
  <el-button
    @click="add"
    class="green-color-btn"
    style="float: right; margin-top: 10px; margin-right: 10px"
    ><i class="el-icon-plus"></i
  ></el-button>
</template>
<script>
import { useStore } from 'vuex';
import { computed, ref } from 'vue';
export default {
  setup() {
    const store = useStore();
    const qnaList = computed(() => store.state.moduleQna.qnaList);
    const new_answer = computed(() => store.state.moduleQna.new_answer);
    const selectedKey = computed(() => store.getters['moduleQna/getKey']);
    const selectValue = ref([]);
    let show = ref(true);
    const changeShow = () => {
      show.value = !show.value;
    };
    const add = () => {
      store.commit('moduleQna/addNewAnswer', selectedKey.value);
    };
    const setNextIdx = (index) => {
      new_answer.value[index].fk_next_idx = selectValue.value[index];
    };
    const removeAnswer = (idx) => {
      store.commit('moduleQna/removeNewAns', idx);
      selectValue.value.splice(idx, 1);
    };
    return {
      store,
      qnaList,
      new_answer,
      selectedKey,
      selectValue,
      show,
      changeShow,
      add,
      setNextIdx,
      removeAnswer,
    };
  },
};
</script>
<style scoped>
.ans >>> .el-textarea__inner {
  border: 0;
  resize: none;
  border: none;
}

.answerBox {
  border: 1px solid #eee;
  border-radius: 10px 10px 0px 10px;
  text-align: right;
  padding: 10px;
  float: right;
}
</style>
