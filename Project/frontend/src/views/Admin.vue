<template>
  <v-container fluid class="mt-5" style="font-family: 'Handon3gyeopsal300g';">
    <div class="mb-5 ml-3 font-weight-bold text-h4" style="font-family: 'Handon3gyeopsal600g' !important;">신고 내역<v-icon large class="ml-1">mdi-alarm-light-outline</v-icon></div>
    <div class="d-flex justify-space-between">  
      <v-select
        v-model="user"
        :items="allUsers"
        outlined
        color="green darken-2"
        item-color="green darken-2"
      ></v-select>
      <v-btn color="red" class="mt-1 ml-1 font-weight-bold" dark large @click="setUserDisabled">삭제</v-btn>
    </div>
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
      'api_server',
      'users'
    ])
  },
  data() {
    return {
      dialog: false,
      user: '',
      headers: [
        {
          text: '신고자',
          value: 'reporter_nickname'
        },
        {
          text: '신고 유저',
          value: 'accused_nickname'
        },
        {
          text: '신고 사유',
          value: 'contents'
        }
      ],
      reports: []
    }
  },
  methods: {
    fetchReports() {
      const requestHeaders = {
        headers: {
          token: this.$cookies.get('auth-token')
        }
      }
      axios.get(`${this.$store.state.api_server}/reports/admin`, requestHeaders)
        .then(res => {
          if (res.data.isAdmin) {
            this.reports = res.data.report
          }
          else {this.$router.push({name:'PageNotFound'})}
        })
    },
    setUserDisabled() {
      if (confirm(`정말 ${this.user} 님을 삭제하시겠습니까?`)) {
        const requestHeaders = {
          heaers: {
            token: this.$cookies.get('auth-token')
          }
        }
        axios.delete(`${this.$store.state.api_server}/reports/admin`, { accused_nickname: this.user }, requestHeaders)
          .then(() => {
            alert(`${this.user} 님이 성공적으로 삭제되었습니다.`)
            this.user = ''
          })
      }
    }
  },
  created() {
    this.fetchReports()
  },
}
</script>

<style>

</style>