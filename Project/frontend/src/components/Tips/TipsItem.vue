<template>
  <div class="mt-5">
    <v-card
      class="d-flex align-start flex-column mx-3 my-3"
      max-width="400"
    >
      <v-img
        class="white--text align-end"
        height="200px"
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
      >
        <v-card-title>{{ tip.title }}</v-card-title>
      </v-img>

      <v-card-subtitle class="pb-0">{{ tip.created_at }}</v-card-subtitle>

      <v-card-text class="text--primary" align="left">
        <div>subheading</div>

        <div class="box">{{ tip.contents }}</div>
      </v-card-text>

      <v-card-actions>
        <v-btn
          color="orange"
          text
        >
          Share
        </v-btn>

        <v-btn
          color="orange"
          text
          @click="goTipDetail(tip.id)"
          :tip="tip"
        >
          Show

        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
// const TIP_API_URL = 'http://localhost:8081/tips/'

export default {
  name: 'TipsItem',
  props: {
    tip: {
      type: Object,
    }
  },
  data() {
    return {
      show: false
    } 
  },
  methods: {
    goTipDetail(t) {
      console.log(t)
      this.$router.push(`/tips/${t}`) // new -> t
    }
  }
}
</script>

<style scoped>
.box {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
 }
</style>