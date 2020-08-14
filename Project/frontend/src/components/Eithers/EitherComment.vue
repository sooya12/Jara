<template>
  <div>
    <div v-if="comments.length > 0">
      <div v-for="(comment, index) in comments" :key="index">
        <v-chip
          class="mt-2"
          v-if="!comment.choice"
          color="red darken-1"
          text-color="white"
        >
        {{ either.choiceA }}
        </v-chip>
        <v-chip
          class="mt-2"
          v-else
          color="blue darken-2"
          text-color="white"
        >
        {{ either.choiceB }}
        </v-chip>
        <div class="d-flex justify-space-between align-center">
          <v-btn text x-large class="pa-0 font-weight-bold" @click="goToUser(comment.writer)"><v-icon class="mr-1">mdi-account-circle</v-icon>{{ users[comment.writer] }}</v-btn>
          <div class="d-flex align-center">
            <div v-if="comment.updated_at==null" class="grey--text">{{ comment.created_at }}</div> 
            <div v-else class="grey--text">{{ comment.updated_at }} (수정됨)</div>
            <v-menu offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon>mdi-dots-horizontal</v-icon>
                </v-btn>
              </template>
              <v-list>
                <v-list-item
                  v-for="(menu, idx) in menuItems"
                  :key="idx"
                  @click="updateOrDelete(comment, idx, index)"
                >
                  <v-list-item-title>{{ menu.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
        <div class="ml-5">{{ comment.contents }}</div>
        <v-divider class="mt-5"></v-divider>
      </div>  
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'EitherComment',
  computed: {
    ...mapState([
      'userInfo',
      'users',
    ])  
  },
  props: {
    comments: {
      type: Array,
    },
    either: {
      type: Object
    }
  },
  data() {
    return {
      menuItems: [
        { title: '수정' },
        { title: '삭제' },
      ],
    }
  },
  methods: {
    updateOrDelete(comment, item, index) {
      this.$emit('updateOrDelete', comment, item, index)
    },
    goToUser(writer) {
      this.$router.push(`/accounts/${writer}`)
    }
  },
}
</script>

<style>

</style>