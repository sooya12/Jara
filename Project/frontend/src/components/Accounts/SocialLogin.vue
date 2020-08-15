<template>
  <v-container fluid entrance>
    <div class="load white--text text-h5 text-center font-weight-bold">
      <div class="text-center">잠시만 기다려주세요</div>
      <v-progress-circular
        indeterminate
        color="grey"
      ></v-progress-circular>
    </div>
  </v-container>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
// import axios from 'axios'

export default {
  name: 'SocialLogin',
  computed: {
    ...mapState([
      'api_server'
    ]),
    ...mapMutations([
      'SET_USERINFO',
      'SET_TOKEN',
      'SET_ENTRANCE'
    ])
  },
  data() {
    return {
      token: ''
    }
  },
  created() {
    this.$store.commit('SET_ENTRANCE', true)
  },
  methods: {
    getToken() {
      this.$route.query.token = this.token
    }
  },
}
</script>

<style scoped>
  .entrance {
    width: 100%;
    height: 100%;
    background-color: #388E3C;
  }

  .load {
    position: absolute;
    top: 50%;
  }
</style>