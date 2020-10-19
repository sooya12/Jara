import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import vuetify from './plugins/vuetify'
import firebase from 'firebase'

const key = process.env.VUE_APP_FIREBASE_API_KEY
var firebaseConfig = {
  apiKey: key,
  authDomain: "[AuthDomain]",
  databaseURL: "[DataBaseURL]",
  projectId: "[ProjectId]",
  storageBucket: "[StorageBucket]",
  messagingSenderId: "[MessagingSenderId]",
  appId: "[AppId]",
  measurementId: "[MeasurementId]"
 }
firebase.initializeApp(firebaseConfig)
var database = firebase.database()
var storage = firebase.storage()

Vue.config.productionTip = false

Vue.filter('filterCreated', function (value) {
  if (!value) return ''
  const now = new Date()
  // console.log(now)
  const created = new Date(value.toString())
  // console.log(created)
  const elapsedSeconds = (now - created) / 1000 // 경과 시간(초)
  // console.log(elapsedSeconds)
  // console.log('-----------------')
  if (elapsedSeconds < 60) {
    return Math.round(elapsedSeconds) + '초 전'
  } else if (elapsedSeconds < 3600) {
    return Math.round(elapsedSeconds / 60) + '분 전'
  } else if (elapsedSeconds < 86400) {
    return Math.round(elapsedSeconds / 3600) + '시간 전'
  } else if (elapsedSeconds < 172800) {
    return '어제'
  } else {
    return (now.getFullYear() !== created.getFullYear() ? created.getFullYear() + '년 ' : '') +
                            (created.getMonth() + 1) + '월 ' +
                            created.getDate() + '일'
  }
})

new Vue({
  store,
  router,
  vuetify,
  database,
  storage,
  render: h => h(App)
}).$mount('#app')
