<template lang="">
  <el-container>
    <el-header>
      <!-- <h1>All cards({{ this.$store.getters.allQnaCount }})</h1> -->
      <el-row>
        <el-col :span="4">
          <el-button type="primary" icon="el-icon-edit" circle></el-button>
          <!-- edit btn: 수정 모드 -->
          <el-button type="success" icon="el-icon-check" circle @click="savaQnaData"></el-button>
          <!-- check btn: 저장 모드(서버로 현재 qList 전송) -->
          <el-button type="danger" icon="el-icon-delete" circle></el-button>
          <!-- delete btn: 삭제 모드(서버로 현재 시나리오 삭제 요청 (시작+종료 제외)) -->
        </el-col>
        <el-col :span="20"><QnaTitle /></el-col>
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

    return {
      store,
      select,
    };
  },
};
</script>
<style lang=""></style>
