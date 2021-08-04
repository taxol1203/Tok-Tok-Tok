<template lang="">
  <div
    v-for="(oa, index) in old_answer"
    :key="oa.pk_idx"
    style="padding-right: 10px; padding-top: 5px"
  >
    <el-row align="middle">
      <el-col :span="11">
        <div style="border: 1px solid #eee; border-radius: 10px 10px 0px 10px; flaot: right">
          <p v-if="!show" style="margin: 10px" @click="changeShow" v-bind="input.value"></p>
          <el-input
            class="ans"
            placeholder="내용을 입력해주세요."
            v-if="show"
            v-model="oa.content"
            @keyup.enter="changeShow()"
            clearable
          >
          </el-input>
          <!-- 예상답변 -->
        </div>
      </el-col>
      <el-col :span="11">
        <!-- 다음 시나리오 select -->
        <el-select
          v-model="selectValue[index]"
          placeholder="next scene"
          @change="setNextIdx(index)"
          style="border: 1px solid #dcdfe6; border-radius: 5px 5px 5px 5px"
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
          icon="el-icon-delete"
          circle
          style="border: 0px solid; background-color: transparent; color: #000"
          @click="removeAnswer(index)"
        ></el-button>
      </el-col>
      <!-- {{ oa.fk_next_idx }} el-icon-delete-->
    </el-row>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { computed, ref } from 'vue';
export default {
  setup() {
    const store = useStore();
    const select = computed(() => store.state.moduleQna.select);
    const qnaList = computed(() => store.state.moduleQna.qnaList);
    const old_answer = computed(() => store.state.moduleQna.old_answer);
    const next_scene = computed(() => store.state.moduleQna.next_scene);
    const selectValue = ref(next_scene);
    let show = ref(true);
    const changeShow = () => {
      show.value = !show.value;
    };
    const setNextIdx = (index) => {
      old_answer.value[index].fk_next_idx = selectValue.value[index];
      console.log(old_answer.value[index]);
    };
    const removeAnswer = (idx) => {
      store.dispatch('moduleQna/removeOldAns', idx);
    };
    return {
      store,
      select,
      qnaList,
      old_answer,
      next_scene,
      selectValue,
      show,
      changeShow,
      setNextIdx,
      removeAnswer,
    };
  },
};
</script>
<style scoped>
/* input 상자 테두리 없애기 */
.ans {
  border: 0;
  width: 90%;
}
.el-input__inner {
  border: none;
  width: 95%;
}
</style>
