<template>
  <div>
    <el-row>
      <el-col>
        <div class="avatar-flip">
          <img
            src="http://media.idownloadblog.com/wp-content/uploads/2012/04/Phil-Schiller-headshot-e1362692403868.jpg"
            height="150"
            width="150"
          />
        </div>
        <h2>John Smith</h2>
        <el-row justify="center">
          <el-button size="medium" type="primary" round>Follow</el-button>
          <el-button size="medium" round>Message</el-button>
        </el-row>
        <p>
          Lorem ipsum, dolor sit amet consectetur adipisicing elit. Facilis dolor ipsum quam amet,
          eos explicabo saepe ipsam autem eligendi nulla minus magnam voluptatum facere, rem unde
          labore quis! Delectus, aliquid!
        </p>
      </el-col>
    </el-row>
    <!-- <el-image style="width: 100px; height: 100px" :src="url" fit="cover"></el-image> -->
  </div>
</template>
<script>
export default {
  data() {
    return {
      url: '@/assets/profile.jpg',
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
</style>
