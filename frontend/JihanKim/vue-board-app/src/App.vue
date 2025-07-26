<template>
  <div class="mt-10 text-3xl mx-auto max-w-6xl">
    <div>Name: vue-board-app</div>
    <div>Framework: vue3</div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  import mitt from 'mitt';
  const eventBus = mitt();
  export default defineComponent({});
</script>