<template>
  <v-container fluid mt-5>
    <div class="d-flex align-center">
      <v-icon x-large>mdi-account-circle</v-icon>
      <div class="ml-2 font-weight-bold">{{ userInfo.nickname }}</div>
      <v-btn fixed bottom right fab color="light-green" @click="createArticle" small dark><v-icon>mdi-pencil</v-icon></v-btn>
    </div>
    <v-textarea
      v-model="article.contents"
      append-outer-icon="mdi-head-dots-horizontal"
      color="green darken-2"
      class="mt-5"
      auto-grow
      rows="1"
      row-height="20"
      label="어떤 이야기를 공유하고 싶으신가요?"
    ></v-textarea>
  </v-container>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  name: 'NewArticle',
  computed: {
    ...mapState([
      'userInfo',
      'api_server'
    ])
  },
  mounted() {
    if (this.$route.path != '/main/new') {
      axios.get(`${this.$store.state.api_server}/articles/${this.$route.params.article_id}`)
        .then(res => this.article = res.data)
    }
  },
  data() {
    return {
      article: {
        contents: '',
        writer: this.$store.state.userInfo.id
      },
    }
  },
  methods: {
    createArticle() {
      if (this.$route.path == '/main/new') {
        axios.post(`${this.$store.state.api_server}/articles`, this.article)
          .then(() => this.$router.push('/main'))
      } else {
        axios.put(`${this.$store.state.api_server}/articles/${this.article.id}`, this.article)
          .then(() => this.$router.push('/main'))  
      }
    }
  }
}
</script>

<style>

</style>