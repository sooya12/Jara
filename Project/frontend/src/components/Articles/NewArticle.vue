<template>
  <v-container fluid mt-5>
    <div class="d-flex align-center">
      <v-icon x-large>mdi-account-circle</v-icon>
      <div class="ml-2 font-weight-bold">{{ userInfo.nickname }}</div>
      <v-btn fixed bottom right fab color="green lighten-1" @click="createArticle" small dark><v-icon>mdi-pencil</v-icon></v-btn>
    </div>
    <v-img v-if="file != null" :src="imageURL"></v-img>
    <v-textarea
      v-model="article.contents"
      append-outer-icon="mdi-head-dots-horizontal"
      color="green darken-2"
      class="mt-5"
      auto-grow
      rows="1"
      row-height="20"
      label="어떤 이야기를 공유하고 싶으신가요?"
      autofocus
    ></v-textarea>
    <v-file-input
      @change="image"
      v-model="file"
      placeholder="사진을 첨부해 주세요."
      color="green darken-2"
    >
    </v-file-input>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'
import firebase from 'firebase'

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
        writer: this.$store.state.userInfo.id,
      },
      file: null,
      imageURL: '',
      img_src: ''
    }
  },
  methods: {
    image() {
      this.imageURL = URL.createObjectURL(this.file)
    },
    createArticle() {
      if (this.$route.path == '/main/new') {
        axios.post(`${this.$store.state.api_server}/articles`, this.article)
          .then(res => {
            if (this.file!=null) {
              firebase.storage().ref(`images/${res.data}`).put(this.file)
              setTimeout(() => {
                firebase.storage().ref().child(`images/${res.data}`).getDownloadURL().then(url => {
                  this.img_src = url
                })
              }, 500)
              setTimeout(() => {
                axios.put(`${this.$store.state.api_server}/articles/${res.data}/img`, { id: res.data, img_src: this.img_src })
              }, 1000)
              this.$router.push('/main')
            }
            else {this.$router.push('/main')}
          })
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