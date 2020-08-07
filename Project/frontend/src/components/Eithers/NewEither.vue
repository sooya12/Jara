<template>
  <v-container fluid>
    <div class="mt-5 d-flex align-center font-weight-bold">
      <v-icon x-large class="mr-2">mdi-account-circle</v-icon>{{ userInfo.nickname }}
    </div>
    <v-text-field
      class="mt-5"
      label="어떤 고민을 하고 계신가요?"
      v-model="eitherData.question"
      append-outer-icon="mdi-comment-question"
      color="green darken-2"
    ></v-text-field>
    <v-text-field
      class="mt-5"
      label="선택지 1번을 입력해 주세요."
      v-model="eitherData.choiceA"
      append-outer-icon="mdi-alpha-a-box"
      color="green darken-2"
    ></v-text-field>
    <v-text-field
      class="mt-5"
      label="선택지 2번을 입력해 주세요."
      v-model="eitherData.choiceB"
      append-outer-icon="mdi-alpha-b-box"
      color="green darken-2"
    ></v-text-field>
    <v-btn fixed bottom right fab color="green lighten-1" @click="createEither" small dark><v-icon>mdi-pencil</v-icon></v-btn>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'NewEither',
  computed: {
    ...mapState([
      'userInfo',
      'api_server'
    ])
  },
  data() {
    return {
      eitherData: {
        writer: this.$store.state.userInfo.id,
        question: '',
        choiceA: '',
        choiceB: '',
      }  
    }
  },
  methods: {
    createEither() {
      axios.post(`${this.$store.state.api_server}/eithers`, this.eitherData)
        .then(() => this.$router.push('/eithers'))
    }
  }
}
</script>

<style>

</style>