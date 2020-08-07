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
        writer: this.$store.state.userInfo.id,
      },
      file: null,
      imageURL: ''
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
            const imageData = new FormData()
            imageData.append('file', this.file)
            imageData.append('id', res.data)
            console.log(imageData)
            axios.post(`${this.$store.state.api_server}/fileupload/article/${res.data}`, imageData, { headers: {'Content-Type': 'multipart/form-data'}})
              .then(() => {
                alert('이미지가 성공적으로 업로드 되었습니다.')
                this.$router.push('/main')
              })
              .catch(() => alert('유효하지 않은 동작입니다.'))
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