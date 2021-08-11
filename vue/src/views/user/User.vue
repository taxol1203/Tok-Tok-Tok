<template lang="">
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
import { useStore } from 'vuex';
export default {
  setup() {
    const store = useStore();
    const user = store.state.auth.user;
    return {
      user,
    };
  },
};
</script>
<style lang=""></style>
