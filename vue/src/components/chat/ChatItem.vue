<template height="80px">
  <!-- 여기는 채팅방 리스트 카드 한칸한칸
  들어갈 내용은 왼쪽 프로필이미지, 상대방 이름(아이디), 마지막 대화내용, 안읽은 메시지수? -->
  <i class="el-icon-user-solid"></i>
  <span>{{ client.username }}님 방({{ id }})</span>
  <br />
  <br />
  <br />
  <sub>{{ time }}</sub>
</template>
<script>
// import { computed } from '@vue/runtime-core'
import { useStore } from 'vuex';

export default {
  props: {
    room: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    const store = useStore();

    return {
      id: props.room.session.session_id.split('-')[0],
      time: props.room.session.created_at.slice(0, 19),
      client: props.room.client,
      store,
    };
  },
  //   setup(props) {
  //     const sessionSlice = computed(() => {
  //       return props.room.session_id.slice(0, 12)
  //     })
  //     const timeSlice = computed(() => {
  //       return props.room.created_at.slice(0, 19)
  //     })
  //     return {
  //       sessionSlice,
  //       timeSlice
  //     }
  //   }
};
</script>
<style scoped>
sub {
  float: right;
  bottom: 0px;
  color: gray;
}
</style>
