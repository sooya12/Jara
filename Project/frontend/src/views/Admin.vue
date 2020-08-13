<template>
  <v-container fluid class="mt-5">
    <div class="mb-5 ml-3 font-weight-bold text-h4">신고 내역<v-icon large class="ml-1">mdi-alarm-light-outline</v-icon></div>
    <v-data-table
      :headers="headers"
      :items="reports"
      :items-per-page="5"
      class="elevation-1"
    >
    </v-data-table>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'Admin',
  computed: {
    ...mapState([
      'userInfo',
      'api_server',
      'users'
    ])
  },
  data() {
    return {
      dialog: false,
      headers: [
        {
          text: '신고 유저',
          value: 'accused_nickname'
        },
        {
          text: '신고 사유',
          value: 'contents'
        },
      ],
      reports: []
    }
  },
  methods: {
    fetchReports() {
      axios.get(`${this.$store.state.api_server}/reports/admin`)
        .then(res => this.reports = res.data)
    }
  },
  created() {
    this.fetchReports()
  },
  beforeEnter(to, from, next) {
    if (this.$store.state.userInfo.id === 8) {
      next()
    } else { next({name: 'PageNotFound'}) }
  }
}
</script>

<style>

</style>