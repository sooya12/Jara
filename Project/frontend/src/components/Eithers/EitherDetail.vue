<template>
  <v-container>
    {{ either.question }}
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'EitherDetail',
  data() {
    return {
      either: null,
    }
  },
  computed: {
    ...mapState([
      'api_server',
      'userInfo',
      'users',
    ])
  },
  mounted() {
    this.fetchEither()
  },
  methods: {
    fetchEither() {
      axios.get(`${this.$store.state.api_server}/eithers/${this.$route.params.either_id}`)
        .then(res => this.either = res.data)
    }
  },
}
</script>

<style>

</style>