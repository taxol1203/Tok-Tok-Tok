<template>
  <!--  -->

  <div class="chat-detial-box border-solid">
    <el-row :gutter="12">
      <el-col :span="24" :offset="0">
        <div class="border-solid">임시 타이틀 V1.0</div>
      </el-col>
    </el-row>

    <div class="border-solid chat-detail grid-content">
      <el-row :gutter="24">
        <!-- 채팅창 부분+녹색 -->
        <el-col :span="24" :offset="0"> [Chat Detail]</el-col>
        <!-- 상대방 -->
        <el-col :span="20" :offset="0">
          <div class="user-name border-solid">상담사 사진(기본 사진) | 상담사 이름</div>
        </el-col>
        <!-- <el-col :span="4" :offset="0"><div class="border-solid">공백</div></el-col> -->
        <el-col :span="20" :offset="0">
          <div class="message-other border-solid">Other Message</div>
        </el-col>
        <!-- <el-col :span="4" :offset="0"><div class="border-solid">공백</div></el-col> -->
        <!-- 나 -->
        <!-- <el-col :span="4" :offset="0">
          <div class="border-solid">공백</div>
        </el-col> -->
        <el-col :span="20" :offset="4">
          <div class="message-me border-solid">My Message</div>
        </el-col>
        <!-- 입력창 -->
        <el-col :span="2" :offset="0">
          <el-button type="primary" icon="el-icon-video-camera"></el-button>
          <!-- <i class="el-icon-video-camera defualt-m-p"></i> -->
        </el-col>
        <el-col :span="20" :offset="0">
          <div class="border-solid">입력창(message manager, chat manager, chat input 등</div>
        </el-col>
        <el-col :span="2" :offset="0">
          <el-button type="success" icon="el-icon-s-promotion" plain></el-button>
          <!-- <i class="el-icon-s-promotion defualt-m-p"></i> -->
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style>
.border-solid {
  border: solid 1px;
  border-radius: 4px;
  box-shadow: 0 2px 24px 0 rgba(0, 0, 0, 0.1);
  padding: 1px;
  margin: 5px 1px 5px 1px;
}
.user-name {
  padding: 1px 0px 0px 0px;
  margin: 15px 0px 0px 0px;
}
.chat-detail {
  background-color: rgb(242, 255, 127);
}

.defualt-m-p {
  padding: 1px;
  margin: 5px 1px 5px 1px;
}
</style>
