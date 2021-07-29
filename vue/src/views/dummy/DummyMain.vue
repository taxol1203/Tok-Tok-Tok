<template>
  <div id="DummyMain">
    <div class="fab-container">
      <div>
        <transition class="same-pos" name="fade" mode="out-in">
          <el-button
            class="big-btn"
            type="primary"
            icon="el-icon-plus"
            @click="changeCondition"
            v-if="!isHidden"
            circle
          ></el-button>
        </transition>
        <transition class="same-pos" name="fade" mode="out-in">
          <div id="chat-box" @click="changeCondition" v-if="isHidden">
            <div class="full-box">
              <ChatDetail />
            </div>
          </div>
          <!-- <p v-if="isHidden">hello</p> -->
        </transition>
      </div>
    </div>
  </div>
</template>
<script>
import ChatDetail from '../../../src/components/chat/ChatDetail.vue';
import { ref } from 'vue';

/* eslint-disable */
export default {
  components: {
    ChatDetail,
  },

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
};
</script>
<style scoped>
#DummyMain {
  position: fixed;
  width: 100%;
  height: 100%;
  background-image: url('../../assets/Microsoft.png');
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

#chat-box {
  width: 20%;
  height: 50%;
  background-color: black;
}

.big-btn {
  width: 100px;
  height: 100px;
}

/* 생성 부분 */
.fade-enter-from {
  opacity: 0;
}
.fade-enter-to {
  opacity: 1;
}
.fade-enter-active {
  transition: all 0.3s ease-out;
}
/* 소멸 부분 */
.fade-leave-from {
  opacity: 1;
}
.fade-leave-to {
  opacity: 0;
}
.fade-leave-active {
  transition: all 0.7s ease-out;
}

/* 위치 고정을 시키지 않으면 렌더링하면서 (생명&소멸) 서로 다른 공간에 보여짐 */
.same-pos {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

.full-box {
  box-sizing: border-box;
}
</style>
