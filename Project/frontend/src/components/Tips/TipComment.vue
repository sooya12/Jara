<template>
  <v-container grid-list-xs>
    <v-layout column>
        <v-card flat style='padding:0' class='comments'>
          <v-card-title class="grey--text pb-1">
            {{ comment.writer }} 
            <div class="my-2">
              <v-btn @click="deleteComment" style="text-align : left;" depressed small color="error">삭제</v-btn>
            </div>
            · {{comment.created_at | filterCreated}}
          </v-card-title>
          <v-card-text class='pt-0 pb-0'> {{ comment.contents }} </v-card-text>
          <v-card-actions class='pr-3'>
            <!-- <v-btn small flat><v-icon left dark class='mr-2'>favorite_border</v-icon> 좋아요({{c.net_votes}})</v-btn> -->
            <v-spacer></v-spacer>
          </v-card-actions>
          <v-divider></v-divider>
        </v-card>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'TipComments',
  props: {
    comment: {
      type: Object,
    }
  },
  data() {
    return {
      commentId: null
    }
  },
  methods: {
    deleteComment() {
      this.commentId = this.comment.id
      if (this.commentId) {
        this.$emit('find_commentId',this.commentId)
      }
    }
  },
  computed: {
    ...mapState([
      'userInfo',
      'users',
      'api_server'
    ])
  }
}
</script>

<style>

</style>