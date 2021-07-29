<template>
  <div id="DummyMain">
    <div class="fab-container">
      <div class="fab fab-icon-holder">
        <i class="fas fa-question"></i>
      </div>
      <ul class="fab-options">
        <li>
          <span class="fab-label">Documentation</span>
          <div class="fab-icon-holder">
            <i class="fas fa-file-alt"></i>
          </div>
        </li>
        <li></li>
      </ul>
    </div>
    <div class="fab-container">
      <div id="chat-box">
        {{ isHidden }}
        <el-button
          class="big-btn"
          type="primary"
          icon="el-icon-edit"
          @click="changeCondition"
          v-if="!isHidden"
          circle
        ></el-button>
        <el-button
          class="big-btn"
          type="success"
          icon="el-icon-check"
          @click="changeCondition"
          v-else
          circle
        ></el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { ref } from "vue";

/* eslint-disable */
export default {
  setup() {
    let isHidden = ref(false);

    let changeCondition = () => {
      isHidden.value = !isHidden.value;
      console.log(isHidden.value);
    };
    return {
      isHidden,
      changeCondition,
    };
  },

  // data() {
  //   return {
  //     isHidden: false,
  //   };
  // },
};
</script>
<style scoped>
#DummyMain {
  position: fixed;
  width: 100%;
  height: 100%;
  background-image: url("../../assets/Microsoft.png");
}

.floating-btn {
  position: fixed;
  bottom: 500px;
  right: 500px;
}

.fab-container {
  position: fixed;
  bottom: 50px;
  right: 50px;
  z-index: 999;
  cursor: pointer;
}

.fab-icon-holder {
  width: 50px;
  height: 50px;
  border-radius: 100%;
  background: #016fb9;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
}

.fab-icon-holder:hover {
  opacity: 0.8;
}

.fab-icon-holder i {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 25px;
  color: #ffffff;
}

.fab {
  width: 60px;
  height: 60px;
  background: #d23f31;
}

.fab-options {
  list-style-type: none;
  margin: 0;
  position: absolute;
  bottom: 70px;
  right: 0;
  opacity: 0;
  transition: all 0.3s ease;
  transform: scale(0);
  transform-origin: 85% bottom;
}

.fab:hover + .fab-options,
.fab-options:hover {
  opacity: 1;
  transform: scale(1);
}

.fab-options li {
  display: flex;
  justify-content: flex-end;
  padding: 5px;
}

.fab-label {
  padding: 2px 5px;
  align-self: center;
  user-select: none;
  white-space: nowrap;
  border-radius: 3px;
  font-size: 16px;
  background: #666666;
  color: #ffffff;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
  margin-right: 10px;
}

#chat-box {
  width: 100px;
  height: 100px;
  /* background-color: black; */
}

.big-btn {
  width: 100px;
  height: 100px;
}
</style>
