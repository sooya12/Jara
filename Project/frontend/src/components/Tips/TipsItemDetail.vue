<template>
  <!-- <v-container fluid>
    <h1>{{ tip }}</h1>
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
  </v-container> -->
  <v-container fill-height fluid grid-list-md>
    <!-- <v-layout v-if="loading" align-center justify-center>
      <v-progress-circular size="50" color="primary" indeterminate></v-progress-circular>
    </v-layout> -->
    <!-- <v-layout v-if="!loading"> -->
    <v-layout>
      <v-flex xs12 md8 offset-md2>
        <!-- <v-layout justify-start column fill-height v-scroll="onScroll" -->
        <v-layout justify-start column fill-height>
          <v-flex xs12>
            <v-card>
              <v-card-title class="headline pb-0">
                {{ tip.title }}
              </v-card-title>
              <v-layout>
                <v-flex xs7 class='pr-0'>
                  <v-list class='pt-0'>
                    <v-list-item>
                      <v-list-item-avatar>
                      <!-- <img :src="'https://steemitimages.com/u/' + author + '/avatar/small'" alt="avatar" onerror="this.src='https://steemitimages.com/u/monawoo/avatar/small'"> -->
                      <img :src="require('../../../src/assets/Totodile.jpg')" alt="avatar">
                      </v-list-item-avatar>
                      <v-list-item-content>
                        <v-list-item-title>{{userInfo.nickname}}<span>({{ tip.writer }})</span></v-list-item-title>
                        <v-list-item-title>{{tip.created_at | filterCreated }}</v-list-item-title>
                        <!-- <v-list-item-sub-title>{{tip.created_at | filterCreated}} · {{category}}</v-list-item-sub-title> -->
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                </v-flex>
                <v-flex xs5 text-xs-right class='pr-4 pt-3'>
                  <div v-if="!liked">
                    <v-btn @click="like" icon><v-icon>mdi-heart-outline</v-icon></v-btn>{{ tip.likes }}명이 이 글을 좋아합니다.
                  </div>
                  <div v-else-if="liked && article.likes > 0">
                    <v-btn @click="unLike" icon><v-icon color="red darken-1">mdi-heart</v-icon></v-btn>{{ userInfo.nickname }}님 이 글을 좋아합니다.
                  </div>
                  <div v-else>
                    <v-btn @click="unLike" icon><v-icon color="red darken-1">mdi-heart</v-icon></v-btn>{{ userInfo.nickname }}님 외 {{ article.likeAccounts.length-1 }} 명이 글을 좋아합니다.
                  </div>
                  <div>
                    · 댓글 1명
                  </div>
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
              <v-card-text>
                <article>{{ tip.contents }}</article>
              </v-card-text>
              <v-card-text>
                <template>
                  <a href='javascript:false' class='tag'>#{{ tip.tag_id }}</a>
                </template>
              </v-card-text>
            </v-card>
          </v-flex>
          <v-flex xs12>
            <v-subheader class='pl-0'>댓글 ({{comments.length}})</v-subheader>
            <v-card ref='comments'>
              <!-- <v-container grid-list-md v-if='!loadedComments'>
                <v-layout align-center justify-center>
                  <v-progress-circular color="primary" indeterminate></v-progress-circular>
                </v-layout>
              </v-container> -->
              <template>
              <!-- <template v-if='loadedComments'> 로딩 창-->
                <!-- <Comments :comments='comments'></Comments>  // 여기 포인트 -->
                <TipComment
                  v-for="comment in comments"
                  :key="comment.id"
                  :comment="comment"
                />
              </template>
            </v-card>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
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
      comments: [],
      liked: false,
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
    },
    like() {
      axios.post(`${this.$store.state.api_server}/tips/${this.tip.id}/like`, { id: this.tip.id }, { params: { user_id: this.$store.state.userInfo.id }})
        .then(() => {
          this.tip.likes += 1
          this.liked = true
        })
        .catcg(err => {
          console.log(err)
        })
    },
    unLike() {
      axios.delete(`${this.$store.state.api_server}/tips/${this.tip.id}/like`, { id: this.tip.id }, { params : { user_id : this.$store.state.userInfo.id }})
        .then(() => {
          this.tip.likes -= 1
          this.liked = false
        })
        .catcg(err => {
          console.log(err)
        })
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/tips/${this.$route.params.tip_id}`)
      .then(res => {
        // console.log(res)
        if (this.tip.likes > 0) {
          this.liked = true
        }
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
      'api_server',
      'users',
      'userInfo'
    ])
  }
}
</script>

<style scoped>

</style>