<template>
  <v-container fluid>
    <!-- <h1>{{ id }}</h1> -->
    <h1>{{ tip.id }}</h1>
    <h2>{{ tip.contents }}</h2>
    <h3>{{ tip.title }}</h3>
    <h4>{{ tip.created_at }}</h4>
    <h5>{{ tip.writer }}</h5>
    <button @click="goTipsDelete">삭제</button>
  </v-container>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  name: 'TipsItemDetail',
  data() {
    return {
      tip: {}
    }
  },
  methods: {
    confirm() {

    },
    goTipsDelete() {
      axios.delete(`${this.$store.state.api_server}/tips/${this.tip.id}`)
        .then(res => {
          console.log(res.data)
          this.$router.push('/tips')
        })
        .catch(err => {
          console.log(err)
        })
    },
    goTipsUpdate() {

    }
  },
  created() {
    // console.log(this.$route.params)
    axios.get(`${this.$store.state.api_server}/tips/${this.$route.params.tip_id}`)
      .then(res => {
        // console.log(res.data)
        this.tip = res.data
      })
      .catch(err => {
        // console.log(this.id)
        console.log(err)
      })
  },
  computed: {
    ...mapState([
      'api_server'
    ])
  }
}
</script>

<style scoped>

</style>