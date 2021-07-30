<template lang="">
  <div style="padding-top: 10px">
    <el-row gutter="20" align="middle">
      <el-col span="12">
        <div style="border: 1px solid #eee; border-radius: 10px 10px 0px 10px; width: 200px">
          <p v-if="!show" style="margin: 10px" @click="changeShow" v-bind="input.value"></p>
          <el-input
            id="ans"
            placeholder="내용을 입력해주세요."
            v-if="show"
            v-model="input.value"
            @keyup.enter="changeShow()"
            clearable
          >
          </el-input>
          <!-- 예상답변 -->
        </div>
      </el-col>
      <el-col span="12">
        <!-- 다음 시나리오 select -->
        <el-select v-model="value" placeholder="Select">
          <el-option
            v-for="item in options"
            :key="item.pk_idx"
            :label="item.content"
            :value="item.pk_idx"
          >
          </el-option>
        </el-select>
      </el-col>
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
    let show = ref(true);
    const changeShow = () => {
      show.value = !show.value;
    };
    let input = ref('');
    const options = computed(() => store.state.moduleQna.qnaList);
    const value = '';
    return {
      store,
      select,
      show,
      changeShow,
      input,
      options,
      value,
    };
  },
};
</script>
<style>
/* input 상자 테두리 없애기 */
#ans {
  background-color: transparent;
  border: 0px solid;
}
</style>
