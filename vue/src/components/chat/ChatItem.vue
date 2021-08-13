<template>
  <!-- <i class="el-icon-user-solid"></i> -->
  <span>{{ client.username }}</span>
  <br />
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
}
.lastmsg {
  color: black;
}
</style>
