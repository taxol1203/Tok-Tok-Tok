<template>
  <!-- <i class="el-icon-user-solid"></i> -->
  <el-row>
    <el-col :span="4" class="imgBox">
      <img :src="require('@/assets/Profile/profile' + client.imgidx + '.jpg')" class="profileImg" />
    </el-col>
    <el-col :span="4" style="display: inline-block">{{ client.username }}</el-col>
  </el-row>
  <sub class="lastmsg" v-if="lastmsg.length > 1">{{ lastmsg[lastmsg.length - 1].message }}</sub>
  <br />
  <sub>{{ time }}</sub>
</template>
<script>
export default {
  props: {
    room: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    return {
      id: props.room.session.session_id.split('-')[0],
      time: props.room.session.created_at.slice(0, 19),
      client: props.room.client,
      lastmsg: props.room.messages,
    };
  },
};
</script>
<style scoped>
sub {
  float: right;
  bottom: 0px;
  color: gray;
  font-size: 16px;
}
.lastmsg {
  color: #2c3e50;
}
.imgBox {
  width: 50px;
  height: 40px;
  display: inline-block;
}
.imgBox > img {
  width: 40px;
  height: 40px;
  border-radius: 70%;
  object-fit: cover;
}
</style>
