<template lang="">
  <div></div>
</template>
<script>
import { useStore } from 'vuex';
import { computed, reactive, ref } from 'vue';

export default {
  setup() {
    const store = userStore();
    const scenes = store.dispatch('');
  },
};
</script>
<style></style>
