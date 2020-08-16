<template>
  <v-container v-if="isTaken" fill-height fluid grid-list-md>
    <!-- {{ tip }} -->
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
                <v-spacer></v-spacer>
                <v-toolbar v-if="$store.state.userInfo.id === tip.writer" flat color="white">
                  <v-row justify="end">
                    <template>
                      <v-btn @click="goTipsUpdate" icon>
                        <v-icon>mdi-pencil</v-icon>
                      </v-btn>
                      <v-btn icon @click="goTipsDelete">
                        <v-icon>
                          mdi-delete
                        </v-icon>
                      </v-btn>
                    </template>
                  </v-row>
                </v-toolbar>
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
                        <v-list-item-title>{{users[tip.writer]}}
                          <!-- <span>({{ tip.writer }})</span> -->
                        </v-list-item-title>
                        <v-list-item-title>{{tip.created_at | filterCreated }}</v-list-item-title>
                        <!-- <v-list-item-sub-title>{{tip.created_at | filterCreated}} · {{category}}</v-list-item-sub-title> -->
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                </v-flex>
                <v-flex xs5 text-xs-right class='pr-4 pt-3'>
                  <div v-if="!liked">
                    <v-btn @click="like" icon><v-icon>mdi-heart-outline</v-icon></v-btn>{{ tip.likeAccounts.length }}명이 이 글을 좋아합니다.
                  </div>
                  <div v-else-if="liked && tip.likeAccounts.length == 1">
                    <v-btn @click="like" icon><v-icon color="red darken-1">mdi-heart</v-icon></v-btn>{{ userInfo.nickname }}님 이 글을 좋아합니다.
                  </div>
                  <div v-else>
                    <v-btn @click="like" icon><v-icon color="red darken-1">mdi-heart</v-icon></v-btn>{{ userInfo.nickname }}님 외 {{ tip.likeAccounts.length - 1 }} 명이 글을 좋아합니다.
                  </div>
                  <div>
                    · 댓글 {{ tip.comments.length }}명
                  </div>
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
              <v-card-text>
                <article>{{ tip.contents }}</article>
              </v-card-text>
              <v-card-text>
                <template>
                  <v-chip href='javascript:false' class='tag'>#{{ tag[tip.tag_id] }}</v-chip>
                </template>
              </v-card-text>
            </v-card>
          </v-flex>
          <v-flex xs12>
            <!-- <v-subheader class='pl-0' >댓글 ({{tip.comments.length}})</v-subheader> -->
            <v-subheader class='pl-0' >댓글 ({{tip.comments.length}})</v-subheader>
            <v-card ref='comments'>
              <v-card-text>
                <v-text-field
                  ref="contents"
                  v-model="new_comment.contents"
                  label="Content"
                  placeholder="댓글을 입력해 주세요."
                  required
                  @keyup.enter="newComment"
                >
                </v-text-field>
              </v-card-text>
              <!-- <v-container grid-list-md v-if='!loadedComments'>
                <v-layout align-center justify-center>
                  <v-progress-circular color="primary" indeterminate></v-progress-circular>
                </v-layout>
              </v-container> -->
              <template>
              <!-- <template v-if='loadedComments'> 로딩 창-->
                <!-- <Comments :comments='comments'></Comments>  // 여기 포인트 -->
                <TipComment
                  @find_commentId="deleteComment"
                  @change_comment="updateComment"
                  v-for="comment in tip.comments"
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
      // comments: [],
      liked: false,
      new_comment : {
        contents: '',
        writer: this.$store.state.userInfo.id,
        tip_id: this.$route.params.tip_id
      },
      isTaken : false,
      tag: {1:'요리',2:'세탁',3:'청소',4:'보관'}
    }
  },
  // 아직 등록자와 작성자가 같은지 확인은 안함...
  methods: {
    goTipsDelete() {
      if (this.$store.state.userInfo.id === this.tip.writer) {
        axios.delete(`${this.$store.state.api_server}/tips/${this.tip.id}`)
          .then(() => {
            // console.log(res.data)
            this.$router.push('/tips')
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        alert('게시글 작성자만 삭제 가능 합니다.')
      }
    },
    goTipsUpdate() {
      if (this.$store.state.userInfo.id === this.tip.writer) {
        this.$router.push({ name: 'UpdateTip', params: { tip_id: this.tip.id }})
      }
    },
    like() {
      axios.post(`${this.$store.state.api_server}/tips/${this.tip.id}/like`, '',{ params : { user_id: this.$store.state.userInfo.id }})
        .then(() => {
          // console.log(res)
          if (this.liked) {
            this.liked = false
            // console.log(this.tip.likeAccounts)
            this.tip.likeAccounts.splice(this.tip.likeAccounts.indexOf(this.$store.state.userInfo.id),1)
            // console.log(this.tip.likeAccounts)
          } else {
            this.liked = true
            this.tip.likeAccounts.push(this.$store.state.userInfo.id)
            // console.log(this.tip.likeAccounts)
          }
        })
        .catch(err => {
          console.log(err.message)
        })
    },
    deleteComment(commentId) {
      axios.delete(`${this.$store.state.api_server}/tips/${this.$route.params.tip_id}/comments/${commentId}`)
        .then(() => {
          this.tip.comments.splice(this.tip.comments.findIndex(x => x.id === commentId),1)
        })
        .catch(err => {
          console.log(err)
        })
    },
    newComment() {
      axios.post(`${this.$store.state.api_server}/tips/${this.tip.id}/comments`, this.new_comment, { params: {tip_id: this.tip.id }})
        .then(res => {
          // console.log(res.data)
          this.tip.comments.push(res.data)
          // console.log(this.tip.comments)
          this.new_comment.contents = ''
        })
        .catch(err => {
          console.log(err.message)
        })
    },
    updateComment(changeComment) {
      axios.put(`${this.$store.state.api_server}/tips/${this.tip.id}/comments/${changeComment.id}`,changeComment)
        .then(res => {
          this.tip.comments.splice(this.tip.comments.findIndex(x => x.id === changeComment.id), 1, res.data)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/tips/${this.$route.params.tip_id}`)
      .then(res => {
        // console.log(res.data)
        this.tip = res.data
        if (this.tip.likeAccounts.length>0 && this.tip.likeAccounts.includes(this.$store.state.userInfo.id)) {
          this.liked = true
        }
        this.isTaken = true
      })
      .catch(err => {
        // console.log(this.id)
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