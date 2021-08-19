<template lang="">
  <el-container>
    <el-header style="margin-top: 20px">
      <el-row>
        <el-col :span="18"><QnaTitle /></el-col>
        <el-col :span="6">
          <el-button
            type="success"
            @click="savaAnsData"
            circle
            style="border: 0px solid; background-color: transparent; color: #006f3e"
            ><i class="el-icon-success" style="color: #006f3e">&nbsp;저장</i></el-button
          >

          <el-button
            type="danger"
            circle
            v-if="selectKey > 2"
            @click="removeQna"
            style="border: 0px solid; background-color: transparent; color: #006f3e"
            ><i class="el-icon-delete-solid" style="color: #006f3e">&nbsp;삭제</i></el-button
          >
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <QnaDetail />
    </el-main>
  </el-container>
</template>
<script>
import QnaTitle from '@/components/qna/QnaTitle.vue';
import QnaDetail from '@/components/qna/QnaDetail.vue';
import { ElMessage } from 'element-plus';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  components: {
    QnaTitle,
    QnaDetail,
  },
  setup() {
    const store = useStore();
    const select = computed(() => store.state.moduleQna.select);
    const new_answer = computed(() => store.state.moduleQna.new_answer);

    const selectKey = computed(() => store.getters['moduleQna/getKey']);
    const savaAnsData = () => {
      store.dispatch('moduleQna/editContent');
      store.dispatch('moduleQna/updateAnswer');
      if (new_answer.value.length > 0) store.dispatch('moduleQna/addAnswer');
    };
    const removeQna = () => {
      store.dispatch('moduleQna/removeQna', selectKey.value);
      ElMessage({
        showClose: true,
        message: '시나리오가 삭제되었습니다.',
        type: 'success',
      });
    };
    return {
      store,
      select,
      selectKey,
      savaAnsData,
      removeQna,
    };
  },
};
</script>
<style scoped>
i {
  font-size: 14px;
}
</style>
