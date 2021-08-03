<template lang="">
  <el-container>
    <el-header>
      <!-- <h1>All cards({{ this.$store.getters.allQnaCount }})</h1> -->
      <el-row>
        <el-col :span="20"><QnaTitle /></el-col>
        <el-col :span="4">
          <!-- <el-button type="primary" icon="el-icon-edit" circle></el-button> -->
          <el-button
            type="success"
            icon="el-icon-check"
            circle
            @click="savaAnsData"
            style="border: 0px solid; background-color: transparent; color: #000"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            circle
            v-if="selectKey > 2"
            @click="removeQna"
            style="border: 0px solid; background-color: transparent; color: #000"
          ></el-button>
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
    // const old_answer = computed(() => store.state.moduleQna.old_answer);
    const new_answer = computed(() => store.state.moduleQna.new_answer);

    const selectKey = computed(() => store.getters['moduleQna/getKey']);
    const savaAnsData = () => {
      store.dispatch('moduleQna/updateAnswer');
      if (new_answer.value.length > 0) store.dispatch('moduleQna/addAnswer');
      ElMessage({
        showClose: true,
        message: '시나리오가 수정되었습니다.',
        type: 'success',
      });
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
      // old_answer,
      selectKey,
      savaAnsData,
      removeQna,
    };
  },
};
</script>
<style lang=""></style>
