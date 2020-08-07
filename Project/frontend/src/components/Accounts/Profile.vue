<template>
  <v-container fluid mt-5 v-if="isLoad">
    <div class="d-flex justify-space-between align-center">
      <div class="text-sm-h3 text-h6">
        <v-icon x-large>mdi-account-circle</v-icon>
        {{ user.nickname }}
      </div>
      <div class="d-flex text-sm-h6 text-subtitle-2">
        <div>게시글 <div class="text-center"><v-btn text class="text-h6">0</v-btn></div></div>
        <div class='ml-3'>
          팔로워
          <div class="text-center">
            <v-menu offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  text
                  class="text-h6"
                  v-bind="attrs"
                  v-on="on"
                >{{ user.followerList.length }}
                </v-btn>
              </template>
              <v-list v-if="user.followerList.length > 0">
                <v-list-item
                  v-for="(user, index) in user.followerList"
                  :key="index"
                >
                  <v-list-item-title>{{ users[user] }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
        <div class="ml-3">
          팔로잉
          <div class="text-center">
            <v-menu offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  text
                  class="text-h6"
                  v-bind="attrs"
                  v-on="on"
                >{{ user.followingList.length }}
                </v-btn>
              </template>
              <v-list v-if="user.followingList.length > 0">
                <v-list-item
                  v-for="(user, index) in user.followingList"
                  :key="index"
                >
                  <v-list-item-title>{{ users[user] }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
      </div>
    </div>
    <div>
      <div class="ml-2">
        <v-icon v-if="user.sex" color="red darken-1">mdi-human-female</v-icon>
        <v-icon v-else color="blue darken-1">mdi-human-male</v-icon>
        {{ user.bio }}
      </div>
    </div>
    <div class="text-right">
      <v-btn text @click="updateUserInfo"><v-icon>mdi-account-edit-outline</v-icon>회원정보 수정</v-btn>
    </div>
    <v-divider class="my-5"></v-divider>
    <div class="d-flex justify-space-around align-center">
      <v-btn text class="font-weight-bold">게시글<v-icon class="ml-1" color="green lighten-1">mdi-pencil-box</v-icon></v-btn>
      |
      <v-btn text class="font-weight-bold">저장<v-icon class="ml-1" color="teal">mdi-bookmark</v-icon></v-btn>
    </div>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'Profile',
  computed: {
    ...mapState([
      'userInfo',
      'users',
      'api_server'
    ])
  },
  data() {
    return {
      user: null,
      isLoad: false,   
    }
  },
  methods: {
    getUserData() {
      axios.get(`${this.$store.state.api_server}/accounts/${this.$store.state.userInfo.id}`)
        .then(res => {
          this.user = res.data
          this.isLoad = !this.isLoad
        })
    },
    updateUserInfo() {
      this.$router.push(`/accounts/${this.user.id}/info`)
    },
  },
  created() {
    this.getUserData()
  }
}
</script>

<style>

</style>