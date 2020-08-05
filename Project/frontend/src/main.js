import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false

Vue.filter('filterCreated', function (value) {
  if (!value) return ''
  const now = new Date()
  // console.log(now)
  const created = new Date(value.toString() + 'Z')
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
  render: h => h(App)
}).$mount('#app')
