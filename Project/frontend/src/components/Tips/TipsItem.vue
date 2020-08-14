<template>
  <div class="mt-5">
    <v-card
      class="d-flex align-start flex-column mx-3 my-3"
      max-width="400"
    >
      <v-img
        id="img"
        class="white--text align-end"
        height="200px"
        :src="tip.img_src"
        contain
      >
      </v-img>
      <v-card-title class="text-center font-weight-bold">{{ tip.title }}</v-card-title>
      <v-card-subtitle class="pb-0">{{ tip.created_at }}</v-card-subtitle>

      <v-card-text class="text--primary" align="left">
        <!-- <div>subheading</div> -->

        <div class="box">{{ tip.contents }}</div>
      </v-card-text>

      <v-card-actions style="width: 100%;">
        <v-btn
          color="orange"
          text
          @click="goTipDetail(tip.id)"
          :tip="tip"
        >
          Show
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn color="orange" icon>
          <v-icon> mdi-share-variant </v-icon>
        </v-btn>

        <v-btn @click="scrapTip(tip.id)" color="orange" icon>
          <v-icon>mdi-bookmark-multiple-outline</v-icon>
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