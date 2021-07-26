<template lang="">
  <!-- 여기는 채팅방 리스트 카드 한칸한칸
  들어갈 내용은 왼쪽 프로필이미지, 상대방 이름(아이디), 마지막 대화내용, 안읽은 메시지수? -->
  <!-- <div>CHatItem</div> -->
  <i class="el-icon-user-solid"></i>
  {{ room.session_id }}
  <br>
  {{ room.created_at }}
  <br>
  마지막 대화 내용
</template>
<script>
// import { computed } from '@vue/runtime-core'
export default {
  props: {
    room: {
      type: Object,
      required: true,
    }
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
}

</script>
<style lang=""></style>
