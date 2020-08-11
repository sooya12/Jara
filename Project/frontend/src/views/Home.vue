<template>
  <v-container fluid mt-5>
    <div class="text-center font-weight-bold text-sm-h3 text-h6">{{ date }} {{ day }}</div>
    <div class="text-center text-sm-h6 text-subtitle-2">{{ weather.fore }}<v-icon :color="weatherIcon[weather.pic].color">mdi-{{ weatherIcon[weather.pic].icon }}</v-icon>{{ weather.back }}</div>
    <div v-if="weather.plus" class="text-center text-sm-h6 text-subtitle-2"><v-icon>{{ weather.plus.icon }}</v-icon>{{ weather.plus.tip }}</div>
    <div class="mt-10 d-flex justify-center align-center">
      <v-icon x-large>mdi-account-circle</v-icon>
      <v-btn text class="grey--text font-weight-bold text-sm-h5 text-body1" @click="write">어떤 이야기를 공유해 볼까요?</v-btn>
    </div>
    <v-divider class="mt-5 mb-5"></v-divider>
    <div>
      <v-card v-for="item in articles" :key="item.id" class="my-5">
        <v-card-title>
          <v-btn
            text
            class="px-0 font-weight-bold text-sm-h6"
            @click="goToUser(item.writer)"
          >
            <v-icon x-large>mdi-account-circle</v-icon>{{ users[item.writer] }}
          </v-btn>
          <div class="d-flex align-center ml-auto">
            <v-card-subtitle>
              {{ item.created_at }}
            </v-card-subtitle>
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
                  @click="updateOrDelete(item, idx)"
                >
                  <v-list-item-title>{{ menu.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </v-card-title>
        <v-img v-if="item.img_src!=null" aspect-ratio="4/3" height="300" contain :src="item.img_src"></v-img>
        <v-card-text class="text-subtitle-1 black--text mx-1">
          {{ item.contents }}
        </v-card-text>
        <v-card-actions>
          <div class="mt-5 mx-3 d-flex ml-auto">
            <div v-if="!isLike(item.likeAccounts)">
              <v-btn icon @click="like(item)">
                <v-icon>mdi-heart-outline</v-icon>
              </v-btn>
              {{ item.likeAccounts.length }}
            </div>
            <div v-else-if="item.likeAccounts.length==1&&isLike(item.likeAccounts)">
              <v-btn icon @click="dislike(item)"><v-icon color="red darken-1">mdi-account-heart</v-icon></v-btn>
              {{ userInfo.nickname }}님이 이 글을 좋아합니다.
            </div>
            <div v-else>
              <v-btn icon @click="dislike(item)"><v-icon color="red darken-1">mdi-account-heart</v-icon></v-btn>
              {{ userInfo.nickname }}님 외 {{ item.likeAccounts.length-1 }}명이 이 글을 좋아합니다.
            </div>
            <div>
              <v-btn icon @click="goToDetail(item.id)"><v-icon>mdi-comment-processing-outline</v-icon></v-btn>
              {{ item.comments.length }}
            </div>
            <div>
              <v-btn icon @click="share(item.id)"><v-icon color="teal">mdi-share-variant</v-icon></v-btn>
              {{ item.shares }}
            </div>
          </div>
        </v-card-actions>
      </v-card>
      <div v-if="isLoad&&(articles.length < numOfArticles)" v-view="loadArticles" id="bottom"></div>
    </div>
    <v-btn @click="scrollToTop" class="top" color="light-green" fab small dark>
      <v-icon>mdi-apple-keyboard-control</v-icon>
    </v-btn>
  </v-container>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import axios from 'axios'
import firebase from 'firebase'

export default {
  name: 'Home',
  computed: {
    ...mapState([
      'today',
      'week',
      'userInfo',
      'users',
      'api_server'
    ]),
    ...mapGetters([
      'isLoggedIn',
      'isEntrance',
    ])
  },
  data() {
    return {
      isLoad: false,
      date: `오늘은 ${this.$store.state.today.getMonth()+1}월 ${this.$store.state.today.getDate()}일`,
      day: `${this.$store.state.week[this.$store.state.today.getDay()]}요일 입니다.`,
      weather: '',
      weatherIcon: {
        흐림: {
          icon: 'apple-icloud', 
          color: 'grey'
        },
        구름많음: {
          icon: 'weather-cloudy',
          color: 'grey'
        },
        맑음: {
          icon: 'white-balance-sunny',
          color: 'yellow darken-2'
        },
        비: {
          icon: 'weather-pouring',
          color: 'blue'
        },
        눈: {
          icon: 'weather-pouring',
          color: 'blue-grey'
        }
      },
      items: [],
      articles: [],
      menuItems: [
        { title: '수정' },
        { title: '삭제' }
      ],
      from: 0,
      articlePerRQ: 5,
      numOfArticles: 0
    }
  },
  methods: {
    loadArticles(e) {
      if (e.type === 'exit') {
        return
      }
      if (e.type === 'progress') {
        return
      }    

      if (this.articles.length < this.numOfArticles) {
        const checkBottom = document.querySelector('#bottom')
        const bottom = checkBottom.getBoundingClientRect(checkBottom)
        if (bottom.top <= (window.innerHeight || document.documentElement.clientWidth)) {
          axios.get(`${this.$store.state.api_server}/articles/${this.from}/${this.articlePerRQ}`, { params: { user_id : this.$store.state.userInfo.id }})
            .then(res => {
              this.articles = [ ...this.articles, ...res.data ]
              this.from += this.articlePerRQ
            })
        }
      }
    },
    fetchArticles() {
      axios.get(`${this.$store.state.api_server}/articles`, { params: { user_id : this.$store.state.userInfo.id }} )
        .then(res => {
          this.isLoad = true
          this.numOfArticles = res.data.length
        })
    },
    scrollToTop() {
      this.$vuetify.goTo(0)
    },
    write() {
      this.$router.push('/main/new')
    },
    like(val) {
      const key = firebase.database().ref(`liked/${val.writer}`).push().key
      const update = {}
      update['like'] = 1
      update['by'] = this.$store.state.userInfo.id
      update['key'] = key
      firebase.database().ref(`liked/${val.writer}/${key}`).update(update)
      axios.post(`${this.$store.state.api_server}/articles/${val.id}/like`, '' , { params: { user_id : this.$store.state.userInfo.id}})
        .then(() => {
          val.likeAccounts.push(this.$store.state.userInfo.id)
        })
    },
    dislike(val) {
      axios.delete(`${this.$store.state.api_server}/articles/${val.id}/like`, { params: { user_id : this.$store.state.userInfo.id}})
        .then(() => {
          val.likeAccounts.pop(this.$store.state.userInfo.id)
        })
    },
    goToDetail(val) {
      this.$router.push(`/main/${val}`)
    },
    goToUser(val) {
      this.$router.push(`/accounts/${val}`)
    },
    share(val) {
      val
    },
    updateOrDelete(val, item) {
      if (this.$store.state.userInfo.id == val.writer) {
        if (item == 0) { this.$router.push({ name: 'UpdateArticle', params: { article_id : val.id }}) }
        else { 
          const response = confirm('정말로 삭제 하시겠습니까?')
          if (response) { 
            this.articles.splice(val, 1)
            axios.delete(`${this.$store.state.api_server}/articles/${val.id}`) 
            firebase.storage().ref().child(`images/${val.id}`).delete()
          }
        }
      } else { alert('작성자만 사용할 수 있어요.') }
    },
    isLike(val) {
      if (val.includes(this.$store.state.userInfo.id)) {
        return true
      } else { return false }
    },
    setWeather() {
      if (this.$store.state.userInfo.PTY=='없음') {
        if (this.$store.state.userInfo.SKY=='맑음') {
          this.weather = { 
            pic: this.$store.state.userInfo.SKY,
            fore: '현재 ' + `${this.$store.state.userInfo.location}` + '의 날씨는 ' + `${this.$store.state.userInfo.SKY}`,
            back: '이며, 기온은 ' + `${this.$store.state.userInfo.T1H}` + '도 입니다.',
            plus: {
              icon: 'shield-sun-outline',
              tip: '선크림을 꼭 발라주세요 :D'
            }
          }
        } else {
          this.weather = { 
            pic: this.$store.state.userInfo.SKY,
            fore: '현재 ' + `${this.$store.state.userInfo.location}` + '의 날씨는 ' + `${this.$store.state.userInfo.SKY}`,
            back: '이며, 기온은 ' + `${this.$store.state.userInfo.T1H}` + '도 입니다.',
          }
        }
      } else if (this.$store.state.userInfo.PTY=='비') {
        this.weather = {
          pic: this.$store.state.userInfo.PTY,
          fore: '현재 ' + `${this.$store.state.userInfo.location}` + '는 ' + `${this.$store.userInfo.PTY}`,
          back: '가 내리고 있으며, 기온은 ' + `${this.$store.state.userInfo.T1H}` + '도 입니다.',
          plus: {
            icon: 'umbrella-closed-variant',
            tip: '우산을 꼭 챙겨주세요 :D'
          }
        }
      } 
    }
  },
  created() {
    this.fetchArticles()
    this.setWeather()
  },  
  updated() {
    if (this.articles.length < this.numOfArticles) {
      setTimeout(() => {
        this.loadArticles('enter')
      }, 1000)
    }
  },
}
</script>

<style scoped>
  .top {
    position: fixed;
    bottom: 3vh;
    right: 3vh;
  }

  #bottom {
    height: 50px;
  }
</style>