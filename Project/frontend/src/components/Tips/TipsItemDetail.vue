<template>
  <v-container fluid>
    <!-- <h1>{{ tip }}</h1> -->
    <h1>{{ tip.id }}</h1>
    <h2>{{ tip.contents }}</h2>
    <h3>{{ tip.title }}</h3>
    <h4>{{ tip.created_at }}</h4>
    <h5>{{ tip.writer }}</h5>
    <h5>{{ tip.tag_id }}</h5>
    <button @click="goTipsDelete">삭제</button>
    <button @click="goTipsUpdate">수정</button>
    <TipComment
      v-for="comment in comments"
      :key="comment.id"
      :comment="comment"
    />
  </v-container>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'
import TipComment from '../Tips/TipComment.vue'

export default {
  name: 'TipsItemDetail',
  components: {
    TipComment,
  },
  data() {
    return {
      tip: {},
      comments: []
    }
  },
  // 아직 등록자와 작성자가 같은지 확인은 안함...
  methods: {
    goTipsDelete() {
      axios.delete(`${this.$store.state.api_server}/tips/${this.tip.id}`)
        .then(() => {
          // console.log(res.data)
          this.$router.push('/tips')
        })
        .catch(err => {
          console.log(err)
        })
    },
    goTipsUpdate() {
      this.$router.push({ name: 'UpdateTip', params: { tip_id: this.tip.id }})
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/tips/${this.$route.params.tip_id}`)
      .then(res => {
        // console.log(res)
        this.tip = res.data
      })
      .catch(err => {
        // console.log(this.id)
        console.log(err)
      })

    axios.get(`${this.$store.state.api_server}/tips/${this.$route.params.tip_id}/comments`)
      .then(res => {
        // console.log(res.data)
        this.comments = res.data
      })
      .catch(err => {
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