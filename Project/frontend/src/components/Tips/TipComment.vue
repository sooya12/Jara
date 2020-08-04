<template>
  <!-- <v-container>
    <br>
    <h3>댓글</h3>
    <h4>{{ comment.contents }}</h4>
    <h5>{{ comment.writer }}</h5>
  </v-container> -->
  <v-container grid-list-xs>
    <v-layout column>
        <v-card flat style='padding:0' class='comments'>
          <v-card-title class="grey--text pb-1">
            {{ comment.writer }} 
            <!-- <span class='reputation'>({{c.author_reputation | filterReputation}})</span>
            · {{comment.created | filterCreated}} -->
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
export default {
  name: 'TipComments',
  props: {
    comment: {
      type: Object,
    }
  }
}
</script>

<style>

</style>