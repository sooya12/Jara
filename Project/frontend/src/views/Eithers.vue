<template>
  <v-container class="mt-5">
    <div class="text-sm-h3 text-h6 font-weight-bold">A or B?<v-icon x-large class="ml-2">mdi-checkbox-multiple-marked-outline</v-icon></div>
    <div class="mt-10">
      <v-card
        class="mx-auto my-2"
        v-for="either in eithers"
        :key="either.id"
      >
        <v-card-text>
          <div><v-icon class="mr-2">mdi-account-circle</v-icon>{{ users[either.writer] }}</div>
          <p class="text-h6 text--primary mt-5 ml-3">
            {{ either.question }}
          </p>
          <div class="text--primary text-h6 d-flex justify-space-around mt-5">
            <div id="choiceA" class="red darken-1 white--text font-weight-bold">{{ either.choiceA }}</div>
            <div id="or" class="font-weight-bold">OR</div>
            <div id="choiceB" class="blue darken-1 white--text font-weight-bold">{{ either.choiceB }}</div>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn
            class="ml-auto font-weight-bold"
            text
            color="deep-purple accent-4"
          >
            투표하기
            <v-icon>mdi-vote</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  name: 'Eithers',
  mounted() {
    this.fetchEithers()
  },
  data() {
    return {
      eithers: [],
    }
  },
  methods: {
    fetchEithers() {
      axios.get(`${this.$store.state.api_server}/eithers/`)
        .then(res => this.eithers = res.data)
    }
  },
  computed: {
    ...mapState([
      'userInfo',
      'users',
      'api_server'
    ])
  }
}
</script>

<style scoped>
</style>