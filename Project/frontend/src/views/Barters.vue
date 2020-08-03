<template>
  <v-container fluid id="barters">
    <div id="working">
      <v-img
        alt="JARA Logo"
        src="@/assets/jara.png"
        class="mx-auto"
        transition="scale-transition"
        width="50%"
      />
      <div class="text-sm-h3 text-h6 text-center mt-5 font-weight-bold">준비중입니다.</div>
    </div>
  </v-container>
</template>

<script>
export default {
  name: 'Barters'
}
</script>

<style scoped>
  #barters {
    height: 100%;
  }
  #working {
    padding-top: 20vh;
  }
</style>