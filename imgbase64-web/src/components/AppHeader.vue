<template>
  <div class="w-100 bg-white border-bottom border-primary border-2">
    <div class="w-50 mx-auto py-3 d-flex">
      <router-link
        to="/home"
        class="btn border-secondary-subtle ms-2"
        active-class="btn-primary"
      >
        ImgBase64
      </router-link>
      <router-link
        to="/about"
        class="btn border-secondary-subtle ms-2"
        active-class="btn-primary"
      >
        About
      </router-link>
      <div class="ms-2 flex-grow-1 bg-primary-subtle rounded" />
    </div>
  </div>
</template>

<script>
export default {
  name: "AppHeader"
}
</script>
