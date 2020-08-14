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
                <v-btn color="green" icon v-if="!barter.status" @click="saleCompleted">
                  <v-icon>
                    mdi-toggle-switch
                  </v-icon>
                </v-btn>
                <!-- <v-btn icon v-else> -->
                  <v-icon color="gray" v-else>
                    mdi-toggle-switch-off
                  </v-icon>
                <!-- </v-btn> -->
                <v-spacer></v-spacer>
                <v-toolbar v-if="$store.state.userInfo.id === barter.writer" flat color="white">
                  <v-row justify="end">
                    <v-dialog v-model="dialog" persistent max-width="600px">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          v-if="!barter.status"
                          v-bind="attrs"
                          v-on="on"
                          @click="updateItem"
                          icon
                        >
                          <v-icon>mdi-pencil</v-icon>
                        </v-btn>
                        <v-btn icon @click="deleteItem">
                          <v-icon>
                            mdi-delete
                          </v-icon>
                        </v-btn>
                      </template>
                      <v-card>
                        <v-card-title>
                          <span class="headline">Edit Item</span>
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-row>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.title" label="Title"></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.contents" label="Contents"></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.price" label="Price"></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-autocomplete
                                  label="Type"
                                  v-model="tag"
                                  :items="tags"
                                  required
                                ></v-autocomplete>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                          <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
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
                        <v-list-item-title>{{users[barter.writer]}}
                        </v-list-item-title>
                        <v-list-item-title v-if="!barter.updated_at">{{barter.created_at | filterCreated }}</v-list-item-title>
                        <v-list-item-title v-else>{{barter.updated_at | filterCreated }}<p style="font-size: x-small; display: inline-block; margin: 0;">(수정됨)</p></v-list-item-title>
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
                  <v-chip href='javascript:false' class='tag'>#{{ tag_name[barter.tag_id] }}</v-chip>
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
      dialog: false,
      barter: {},
      comments: [],
      isTaken : false,
      tag_name: {5:'구해요',6:'사요',7:'팔아요',8:'나눠요'},
      tags: ['구해요','사요','팔아요','나눠요'],
      tag: '',
      new_comment: {
        contents: '',
        writer: this.$store.state.userInfo.id,
        item_id: this.$route.params.item_id
      },
      editedItem: {
        title: '',
        tag_id: null,
        contents: '',
        writer: this.$store.state.userInfo.id,
        price: 0,
      },
      defaultItem: {
        title: '',
        tag_id: null,
        contents: '',
        writer: this.$store.state.userInfo.id,
        price: 0,
      },
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/barters/${this.$route.params.item_id}`)
      .then(res => {
        this.barter = res.data
        // console.log('1차 성공')
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
    saleCompleted() {
      if (!this.barter.status) {
        if (this.$store.state.userInfo.id === this.barter.writer) {
          const response = confirm('판매를 완료하시겠습니까?')
          if (response) {
            this.editedItem = this.barter
            this.editedItem.status = true
            axios.put(`${this.$store.state.api_server}/barters/${this.barter.id}`,this.editedItem)
              .then(res => {
                console.log(res)
                this.barter = res.data
                this.$nextTick(() => {
                  this.editedItem = Object.assign({}, this.defaultItem)
                })
              })
              .catch(err => {
                console.log(err.message)
              })
          }
        }
      } else {
        alert('이미 판매가 완료된 Item 입니다.')
      }
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
      if (this.$store.state.userInfo.id === this.barter.writer) {
        const response = confirm('정말로 삭제 하시겠습니까?')
        if (response) {
          axios.delete(`${this.$store.state.api_server}/barters/${this.barter.id}`)
            .then(() => {
              // console.log('성공')
              this.$router.push('/barters')
            })
            .catch(err => {
              console.log(err.message)
            })
        }
      } else {
        alert('Item 등록자만 삭제 가능 합니다.')
      }
    },
    updateItem() {
      this.editedItem = Object.assign({}, this.barter)
    },
    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
      })
    },
    save() {
      if ((this.$store.state.userInfo.id === this.barter.writer) && !this.barter.status) {
        const tag_id_dict = {'구해요':5,'사요':6,'팔아요':7,'나눠요':8}
        this.editedItem.tag_id = tag_id_dict[this.tag]
        console.log(this.editedItem)
        axios.put(`${this.$store.state.api_server}/barters/${this.barter.id}`,this.editedItem)
          .then(res => {
            // console.log(res.data)
            this.barter = res.data
          })
          .catch(err => {
            console.log(err.message)
          })
        this.close()
      } else {
        alert('Item 등록자만 수정 가능 합니다.')
      }
    }
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