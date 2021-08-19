<template>
  <el-row>
    <el-col>
      <div class="avatar-flip">
        <div class="imgBox">
          <img
            :src="require('@/assets/Profile/profile' + user.imgidx + '.jpg')"
            class="profileImg"
          />
        </div>
        <h3 style="margin: 10px">
          {{ user.username }}
        </h3>
        <div style="color: gray; margin-bottom: 10px">
          {{ user.email }}
        </div>
      </div>
      <hr style="border: 2px solid whitesmoke" width="90%" />
      <el-row class="row">
        <i class="el-icon-s-opportunity" style="margin-right: 3px"></i>
        고객 채팅봇 기록
      </el-row>

      <el-row v-for="(item, index) in qna" :key="index" class="rowitem message-me">
        {{ item }}
      </el-row>
    </el-col>
    <hr style="border: 2px solid whitesmoke; margin-top: 40px" width="90%" />
    <el-col>
      <el-row class="row">
        <i class="el-icon-info" style="margin-right: 3px"></i>
        고객 정보
      </el-row>
    </el-col>
    <div class="rowitem2">닉네임: {{ user.username }}</div>
    <div class="rowitem2">이메일: {{ user.email }}</div>
    <div class="rowitem2">전화번호: 010-1234-5678</div>
    <hr style="border: 2px solid whitesmoke; margin-top: 40px" width="90%" />
  </el-row>
</template>
<script>
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  data() {
    return {
      url: '@/assets/profile.jpg',
    };
  },
  setup() {
    const store = useStore();
    const user = computed(() => store.getters['clientGetter']);
    const qna = computed(() => store.getters['qnaGetter']);
    return {
      store,
      user,
      qna,
    };
  },
};
</script>
<style scoped>
.avatar-flip img {
  border-radius: 100px;
  transition: all 0.3s ease-in-out;
  -webkit-transition: all 0.3s ease-in-out;
  -moz-transition: all 0.3s ease-in-out;
}
.avatar-flip {
  margin-top: 50px;
  margin-bottom: 50px;
}
.row {
  margin-top: 30px;
  margin-bottom: 20px;
  margin-left: 15px;
}
.rowitem {
  border: #f7f4f0;
  margin-left: 10px;
  margin-top: 15px;
  color: gray;
  font-size: 15px;
  background-color: #f7f4f0;
}
.rowitem2 {
  color: gray;
  margin-left: 20px;
  margin-bottom: 10px;
  width: 100%;
  text-align: left;
}
.message-me {
  border: 1px solid #f7f4f0;
  border-radius: 10px 10px 0px 10px;
  background: #f7f4f0;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
.imgBox {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
  margin: 0 auto;
}
.profileImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
