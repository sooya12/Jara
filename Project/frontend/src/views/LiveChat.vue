<template>
  <v-container fluid mt-5>
    <div class="text-sm-h3 text-h4 font-weight-bold ml-3">심심한 자라<v-icon x-large class="ml-2">mdi-cellphone-message</v-icon></div>
    <div
      v-for="(item, idx) in chats"
      :key="idx"
      class="mt-5"
    >
      <div :class="{ 'text-right': item.userName==nickname}">
        <div v-if="item.userName!=nickname"><v-icon>mdi-account-circle</v-icon> {{ item.userName }} :</div>
        <div class="text-h6 mx-3 font-weight-bold">{{ item.content }}</div>
      </div>
    </div>
    <v-form class="mt-5 d-flex">
      <v-textarea
        v-model="chat"
        label="채팅을 남겨주세요."
        color="green darken-1"
        auto-grow
        outlined
        rows="1"
        row-height="15"
        @keyup.enter="sendChat"
      ></v-textarea>
      <div>
        <v-btn @click="sendChat" text class="mt-3 font-weight-bold" color="green darken-1">등록</v-btn>
      </div>
    </v-form>
    <v-speed-dial
      id="jara"
      v-model="fab"
      fixed
      top
      right
      direction="bottom"
      transition="slide-y-transition"
    >
      <template v-slot:activator>
        <v-btn
          v-model="fab"
          color="green darken-2"
          dark
          fab
        >
          <v-icon v-if="fab">mdi-close</v-icon>
          <v-icon v-else>mdi-turtle</v-icon>
        </v-btn>
      </template>
      <v-btn
        fab
        dark
        small
        color="green"
        @click="goToHome"
      >
        <v-icon>mdi-home</v-icon>
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="light-green"
        @click="scrollToTop"
      >
        <v-icon>mdi-apple-keyboard-control</v-icon>
      </v-btn>
    </v-speed-dial>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'LiveChat',
  computed: {
    ...mapState([
      'users',
      'userInfo',
      'api_server'
    ])
  },
  data() {
    return {
      nickname: this.$store.state.userInfo.nickname,
      chat: '',
      chats: [],
      fab: false,
    }
  },
  methods: {
    sendChat() {
      if (this.nickname !== '' && this.chat !== '') {
        this.send()
        this.chat = ''
      }
    },
    send() {
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          userName: this.nickname,
          content: this.chat
        }
        this.stompClient.send("/receive", JSON.stringify(msg), {})
      }
    },   
    connect() {
      const serverURL = this.$store.state.api_server
      let socket = new SockJS(serverURL)
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect(
        {},
        () => {
          this.connected = true
          this.stompClient.subscribe("/send", res => {
            this.chats.push(JSON.parse(res.body))
          })
        },
      )        
    },
    scrollToTop() {
      this.$vuetify.goTo(0)
    },
    goToHome() {
      this.$router.push('/main')
    }
  },
  created() {
    this.connect()
  }
} 
</script>

<style scoped>
  #jara {
    top: 10vh;
  }
</style>