<template>
  <el-row :gutter="20">
    <el-col :span="23" :offset="0">
      <p class="user-title border-solid">(Eng) Gil Dong. Hong (한글) 홍길동</p>
    </el-col>
  </el-row>
</template>
<script>
export default {};
</script>
<style scoped>
.user-title {
  font-size: 200%;
  font-family: "BMJUA", Times, serif;
  text-align: left;
  color: white;
  background-color: #258c60;
}
.border-solid {
  border: solid 1px;
  border-radius: 4px;
  box-shadow: 0 2px 24px 0 rgba(0, 0, 0, 0.1);
  padding: 1px;
  margin: 5px 1px 5px 1px;
}
</style>
