<template>
  <!-- <i class="el-icon-user-solid"></i> -->
  <el-row>
    <el-col :span="4" class="imgBox">
      <img :src="require('@/assets/Profile/profile' + client.imgidx + '.jpg')" class="profileImg" />
    </el-col>
    <el-col :span="4" style="display: inline-block" class="noMarginPadding">{{
      client.username
    }}</el-col>
  </el-row>
  <sub class="lastmsg" v-if="lastmsg.length > 1">{{ lastmsg[lastmsg.length - 1].message }}</sub>
  <br />
  <sub>{{ timeMsg }}</sub>
  <!-- <sub>{{ time }}</sub> -->
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
    let timeMsg = "";
    const calcTime = (time) => {
      var now = new Date();
      var writeDay = new Date(time);
      // Tue Aug 17 2021 21:39:53 GMT+0900 (한국 표준시)
      // Tue Aug 17 2021 05:06:23 GMT+0900 (한국 표준시)
      // console.log(now);
      // console.log(writeDay);
      var minus;
      //현재 년도랑 글쓴시간의 년도 비교
      if (now.getFullYear() > writeDay.getFullYear()) {
        //두개의 차이를 구해서 표시
        timeMsg = time.slice(0, 11);
      } else if (now.getMonth() > writeDay.getMonth()) {
        //년도가 같을 경우 달을 비교해서 출력
        timeMsg = time.slice(0, 11);
      } else if (now.getDate() > writeDay.getDate()) {
        //같은 달일 경우 일을 계산
        minus = now.getDate() - writeDay.getDate();
        if (minus == 1) {
          timeMsg = "어제";
        } else {
          timeMsg = time.slice(0, 11);
        }
      } else if (now.getDate() == writeDay.getDate()) {
        //당일인 경우에는
        var nowTime = now.getTime();
        var writeTime = writeDay.getTime();
        if (nowTime > writeTime) {
          //시간을 비교
          let sec = parseInt(nowTime - writeTime) / 1000;
          let day = parseInt(sec / 60 / 60 / 24);
          sec = sec - day * 60 * 60 * 24;
          let hour = parseInt(sec / 60 / 60);
          sec = sec - hour * 60 * 60;
          let min = parseInt(sec / 60);
          sec = parseInt(sec - min * 60);
          if (hour > 0) {
            timeMsg = hour + "시간 전";
          } else if (min > 0) {
            timeMsg = min + "분 전";
          } else if (sec > 0) {
            timeMsg = sec + "초 전";
          }
        }
      }
    };
    calcTime(props.room.session.created_at.slice(0, 19));
    return {
      id: props.room.session.session_id.split("-")[0],
      time: props.room.session.created_at.slice(0, 19),
      client: props.room.client,
      lastmsg: props.room.messages,
      calcTime,
      timeMsg,
    };
  },
};
</script>
<style scoped>
sub {
  float: right;
  bottom: 0px;
  color: gray;
  font-size: 14px;
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
