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
    <div class="text-right" v-if="isUser">
      <v-btn text @click="updateUserInfo"><v-icon>mdi-account-edit-outline</v-icon>회원정보 수정</v-btn>
    </div>
    <div class="text-right" v-else>
      <v-btn v-if="isFollow" text @click="unfollow" class="font-weight-bold"><v-icon color="red darken-1">mdi-account-minus-outline</v-icon>언팔로우</v-btn>
      <v-btn v-else text @click="follow" class="font-weight-bold"><v-icon color="blue darken-1">mdi-account-plus</v-icon>팔로우</v-btn>
    </div>
    <v-divider class="mt-5"></v-divider>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'User',
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
      isUser: false,
      isFollow: false,
      isLoad: false,   
    }
  },
  methods: {
    getUserData() {
      axios.get(`${this.$store.state.api_server}/accounts/${this.$route.params.user_id}`)
        .then(res => {
          this.user = res.data
          if (this.user.id == this.$store.state.userInfo.id) {this.isUser = !this.isUser}
          if (this.user.followerList.length > 0 && this.user.followerList.includes(this.$store.state.userInfo.id)) {this.isFollow = !this.isFollow}
          this.isLoad = !this.isLoad
        })
    },
    updateUserInfo() {
      this.$router.push(`/accounts/${this.user.id}/info`)
    },
    follow() {
      const followData = {
        follower: this.$store.state.userInfo.id,
        following: this.user.id,
      }
      axios.post(`${this.$store.state.api_server}/accounts/follow`, followData)
        .then(res => {
          if (res.data) { alert('팔로우 요청을 보냈습니다.') }
          else { alert('유저의 승인이 필요한 상태입니다. 조금만 기다려주세요.') }
        })
    },
    unfollow() {
      axios.delete(`${this.$store.state.api_server}/accounts/follow`, { data: { follower : this.$store.state.userInfo.id, following: this.user.id }})
        .then(() => {
          this.isFollow = !this.isFollow
          this.user.followerList.pop(this.$store.state.userInfo.id)
        })
    }
  },
  created() {
    this.getUserData()
  }
}
</script>

<style>

</style>