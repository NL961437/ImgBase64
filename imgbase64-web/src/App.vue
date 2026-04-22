<template>
  <app-header />
  <div class="w-50 mx-auto bg-white rounded border border-primary border-2 mt-3 mb-5 p-4">
    <router-view />
  </div>
</template>

<script>

import AppHeader from "@/components/AppHeader.vue";

export default {
  name: 'App',
  components: {
    AppHeader
  }
}
</script>
