<template>
  <div class="mt-5">
    <v-card
      class="d-flex align-start flex-column mx-3 my-3"
      max-width="400"
    >
      <v-container
        class="px-0 py-0"
        @click="goTipDetail(tip.id)"
        :tip="tip"
      >
        <v-img
          id="img"
          v-if="tip.img_src!=null"
          class="white--text align-end"
          aspect-ratio="1.4"
          :src="tip.img_src"
          contain
        >
        </v-img>
        
        <v-card-title class="text-center font-weight-bold">{{ tip.title }}</v-card-title>
        <v-card-subtitle class="pb-0 text-start">{{ tip.created_at }}</v-card-subtitle>

        <v-card-text class="text--primary" align="left">
          <!-- <div>subheading</div> -->

          <div class="box">{{ tip.contents }}</div>
        </v-card-text>
      </v-container>
      <v-card-actions style="width: 100%;">
        <v-chip href="javascript:false" class="tag">#{{ tag[tip.tag_id] }}</v-chip>
        <v-spacer></v-spacer>
        <v-btn color="teel" icon>
          <v-icon> mdi-share-variant </v-icon>
        </v-btn>

        <v-btn @click="scrapTip(tip.id)" color="teel" icon>
          <v-icon>mdi-bookmark-outline</v-icon>
        </v-btn>

        <v-btn v-if="!liked" @click="like" icon>
          <v-icon>mdi-heart-outline</v-icon>
        </v-btn>

        <v-btn v-else @click="like" icon>
          <v-icon color="red darken-1">mdi-heart</v-icon>
        </v-btn>

      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import axios from "axios"
import { mapState } from 'vuex'

export default {
  name: 'TipsItem',
  props: {
    tip: {
      type: Object,
    }
  },
  data() {
    return {
      show: false,
      tag: {1:'요리',2:'세탁',3:'청소',4:'보관'},
      liked: false,
      likeAccounts: []
    } 
  },
  methods: {
    goTipDetail(t) {
      // console.log(t)
      this.$router.push(`/tips/${t}`) // new -> t
    },
    scrapTip(t) {
      axios.post(`${this.$store.state.api_server}/tips/${t}/scrap`, '', { params: { user_id: this.$store.state.userInfo.id}})
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    like() {
      axios.post(`${this.$store.state.api_server}/tips/${this.tip.id}/like`, '',{ params : { user_id: this.$store.state.userInfo.id }})
        .then(() => {
          // console.log(res)
          if (this.liked) {
            this.liked = false
            // console.log(this.tip.likeAccounts)
            this.likeAccounts.splice(this.likeAccounts.indexOf(this.$store.state.userInfo.id),1)
            // console.log(this.tip.likeAccounts)
          } else {
            this.liked = true
            this.likeAccounts.push(this.$store.state.userInfo.id)
            // console.log(this.tip.likeAccounts)
          }
        })
        .catch(err => {
          console.log(err.message)
        })
    },
  },
  created() {
    axios.get(`${this.$store.state.api_server}/tips/${this.tip.id}/like`)
      .then(res => {
        this.likeAccounts = res.data
        if (this.likeAccounts.length>0 && this.likeAccounts.includes(this.$store.state.userInfo.id)) {
          this.liked = true
        }
      })
      .catch(err => {
        console.log(err.message)
      })
  },
  computed: {
    ...mapState([
      'api_server',
      'users',
      'userInfo'
    ])
  },
}
</script>

<style scoped>
.box {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
 }

</style>