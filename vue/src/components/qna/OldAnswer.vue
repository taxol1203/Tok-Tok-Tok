<template lang="">
  <div v-for="(oa, index) in old_answer" :key="oa.pk_idx">
    <el-row align="middle">
      <el-col :span="12">
        <div style="border: 1px solid #eee; border-radius: 10px 10px 0px 10px; width: 200px">
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
      <el-col :span="12">
        <!-- 다음 시나리오 select -->
        <el-select
          v-model="selectValue[index]"
          placeholder="next scene"
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
      <!-- {{ oa.fk_next_idx }} -->
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
    };
  },
};
</script>
<style>
/* input 상자 테두리 없애기 */
.ans {
  background-color: transparent;
  border: 0px solid;
}
</style>
