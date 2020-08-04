<template>
  <v-container>
    <br>
    <h3>댓글</h3>
    <h4>{{ comment.contents }}</h4>
    <h5>{{ comment.writer }}</h5>
  </v-container>
</template>

<script>
export default {
  name: 'TipComments',
  props: {
    comment: {
      type: Object,
    }
  }
}
</script>

<style>

</style>