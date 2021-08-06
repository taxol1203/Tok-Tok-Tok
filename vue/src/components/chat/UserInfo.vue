<template>
  <el-row>
    <el-col>
      <div class="avatar-flip">
        <img
          src="http://media.idownloadblog.com/wp-content/uploads/2012/04/Phil-Schiller-headshot-e1362692403868.jpg"
          height="80"
          width="80"
        />
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
      <el-row class="rowitem message-me"> 기기 고장 </el-row>
      <el-row class="rowitem message-me"> surface laptop 3 </el-row>
      <el-row class="rowitem message-me"> 전원이 안 들어옵니다. </el-row>
      <el-row class="rowitem message-me"> 해결 되지 않았습니다. </el-row>
    </el-col>
    <hr style="border: 2px solid whitesmoke; margin-top: 40px" width="90%" />
    <el-col>
      <el-row class="row">
        <i class="el-icon-info" style="margin-right: 3px"></i>
        고객 정보
      </el-row>
    </el-col>
    <el-row class="rowitem2"> username: {{ user.username }} </el-row>
    <el-row class="rowitem2"> email: {{ user.email }} </el-row>
    <el-row class="rowitem2"> phone: 010-1234-5678 </el-row>
    <hr style="border: 2px solid whitesmoke; margin-top: 40px" width="90%" />
  </el-row>
</template>
<script>
import { useStore } from "vuex";
import { computed } from "vue";

export default {
  data() {
    return {
      url: "@/assets/profile.jpg",
    };
  },
  setup() {
    const store = useStore();
    const user = computed(() => store.state.auth.user);

    return {
      store,
      user,
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
</style>
