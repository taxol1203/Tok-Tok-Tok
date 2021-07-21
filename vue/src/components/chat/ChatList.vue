<template lang="">
  <div>
    [ChatList]
     <el-button type="primary" @click="addOne">임시생성</el-button>
    <!-- for문 -->
    <!-- chat item에 props 내려서 정보랑 아이콘도 item에서 표현-->
    <!-- 리스트에서는 반복문이랑 스크롤 처리 필요 -->
    <!-- <div class="infinite-list-wrapper" style="overflow:auto">
      <ul
      class="list"
      v-infinite-scroll="load"
      infinite-scroll-disabled="disabled"
      style="list-style:none;">
        <li v-for="item in items"
          :key="item.id" class="list-item">
          <el-card  class="box-card">
            <ChatItem :item="item" />
          </el-card>
        </li>
      </ul>
      <p v-if="loading">Loading...</p>
      <p v-if="noMore">No more</p>
    </div> -->
    <el-scrollbar height="400px">
      <el-card v-for="item in items"
          :key="item.id" class="list-item box-card" style="height: 80px;">
          <!-- 라우터링크로 chat detail 띄워야함 -->
        <ChatItem :item="item" />
      </el-card>
    </el-scrollbar>
  </div>
</template>
<script>
import ChatItem from "./ChatItem.vue"
import { reactive, defineComponent } from 'vue'
export default defineComponent({
  components: {
    ChatItem,
  },
  setup() {
    // 여기서 나와 연결된 모든 채팅방을 받아와야함
    const items = reactive([
      { id: 1, name: 'dohyeong' },
      { id: 2, name: 'minsu' },
      { id: 3, name: 'sobin' },
      { id: 4, name: 'daeyeon' },
      { id: 5, name: 'ilmin' },
      { id: 6, name: 'juyeon' },
      { id: 7, name: 'shinil' },
      { id: 8, name: 'minjin' },
      { id: 9, name: 'dohoon' },
    ])
    // const loading = ref(false)
    // const noMore = computed(() => items.length >= 20)
    // const disabled = computed(() => loading.value || noMore.value)
    // const load = () => {
    //   loading.value = true
    //   setTimeout(() => {
    //     items.push({ id: items[items.length - 1].id + 1, name: items[items.length - 1].id + 1 })
    //     loading.value = false
    //   }, 2000)
    // }
    const addOne = () => {
      items.push({ id: items[items.length - 1].id + 1, name: items[items.length - 1].id + 1 })
    }
    return {
      items,
      // loading,
      // noMore,
      // disabled,
      // load,
      addOne,
    }
  }
});
</script>
<style lang="">
</style>
