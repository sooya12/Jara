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
                {{ barter.title }}
                <v-spacer></v-spacer>
                <span>
                  <v-btn v-if="$store.state.userInfo.id == barter.writer" icon @click="deleteItem">
                    <v-icon>
                      mdi-delete
                    </v-icon>
                  </v-btn>
                </span>
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
                        <v-list-item-title>{{users[barter.writer]}}
                          <!-- <span>({{ tip.writer }})</span> -->
                        </v-list-item-title>
                        <v-list-item-title>{{barter.created_at | filterCreated }}</v-list-item-title>
                        <!-- <v-list-item-sub-title>{{tip.created_at | filterCreated}} · {{category}}</v-list-item-sub-title> -->
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                </v-flex>
                <v-flex xs5 text-xs-right class='pr-4 pt-3'>
                  <div>
                    · 댓글 {{ comments.length }}명
                  </div>
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
              <v-card-text>
                <article>{{ barter.contents }}</article>
              </v-card-text>
              <v-card-text>
                <template>
                  <v-chip href='javascript:false' class='tag'>#{{ tag[barter.tag_id] }}</v-chip>
                </template>
              </v-card-text>
            </v-card>
          </v-flex>
          <v-flex xs12>
            <v-subheader class='pl-0' >댓글 ({{comments.length}})</v-subheader>
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
                <BarterComment
                  @change_comment="updateComment"
                  @find_commentId="deleteComment"
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
import { mapState} from 'vuex'
import BarterComment from '../Barters/BarterComment.vue'

export default {
  name: 'BartersItemDetail',
  components: {
    BarterComment,
  },
  data() {
    return {
      barter: {},
      comments: [],
      isTaken : false,
      tag: {5:'구해요',6:'사요',7:'팔아요',8:'나눠요'},
      new_comment: {
        contents: '',
        writer: this.$store.state.userInfo.id,
        item_id: this.$route.params.item_id
      }
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/barters/${this.$route.params.item_id}`)
      .then(res => {
        this.barter = res.data
        // console.log('1차 성공')
        // console.log(`${this.$store.state.api_server}/barters/${this.$route.params.barter_id}/comments`)
      })
      .catch(err => {
        console.log(err)
      })
    axios.get(`${this.$store.state.api_server}/barters/${this.$route.params.item_id}/comments`)
      .then(res => {
        // console.log('2차 성공')
        this.comments = res.data
      })
      .catch(err => {
        console.log(err.message)
      })
    this.isTaken = true
  },
  methods: {
    newComment() {
      axios.post(`${this.$store.state.api_server}/barters/${this.barter.id}/comments`, this.new_comment)
        .then(res => {
          // console.log('성공~')
          this.comments.push(res.data)
          this.new_comment.contents = ''
        })
        .catch(err => {
          console.log(err.message)
        })
    },
    updateComment(changeComment) {
      axios.put(`${this.$store.state.api_server}/barters/${this.barter.id}/comments/${changeComment.id}`,changeComment)
        .then(res => {
          this.comments.splice(this.comments.findIndex(x => x.id === changeComment.id), 1, res.data)
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteComment(commentId) {
      axios.delete(`${this.$store.state.api_server}/barters/${this.barter.id}/comments/${commentId}`)
        .then(() => {
          this.comments.splice(this.comments.findIndex(x => x.id === commentId), 1)
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteItem() {
      const response = confirm('정말로 삭제 하시겠습니까?')
      if (response) {
        axios.delete(`${this.$store.state.api_server}/barters/${this.barter.id}`)
          .then(() => {
            console.log('성공')
            this.$router.push('/barters')
          })
          .catch(err => {
            console.log(err.message)
          })
      }
    },
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

<style>

</style>