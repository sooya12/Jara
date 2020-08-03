import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import VueCookies from 'vue-cookies'
import Axios from 'axios'

Vue.use(Vuex)
Vue.use(VueCookies)


export default new Vuex.Store({
  state: {
    api_server: 'http://localhost:8081',
    authToken: VueCookies.get('auth-token'),
    entrance: true,
    drawer: false,
    group: null,
    userInfo: null,
    results: [],
    showSearch: false,
    users: null,
    today: new Date(),
    week: ['일', '월', '화', '수', '목', '금', '토'],
    error: false,
    request: false,
    dialog: false,
  },
  getters: {
    isLoggedIn: state => !!state.authToken,
    isEntrance: state => state.entrance,
    isDrawer: state => state.drawer,
    isSearch: state => state.showSearch,
    isError: state => state.error,
    isRequested: state => !state.request,
    isDialog: state => state.dialog
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token
      VueCookies.set('auth-token', token)
    },
    SET_USERINFO(state, val) {
      state.userInfo = val
    },
    SET_USERS(state, val) {
      state.users = val
    },
    SET_DRAWER(state, status) {
      state.drawer = status
    },
    SET_ENTRANCE(state, status) {
      state.entrance = status
    }, 
    SET_SHOW(state, status) {
      state.showSearch = status
    },
    SET_ERROR(state, status) {
      state.error = status
    },
    SET_DIALOG(state, status) {
      state.dialog = status
    }
  },
  actions: {
    signIn({ commit, state }, data) {
      Axios.post(`${state.api_server}/accounts/signin`, data)
        .then(res => {
          commit('SET_USERINFO', res.data)
          commit('SET_TOKEN', res.headers['jwt-auth-token'])
          router.push('/main')
        })
        .catch(() => {
          commit('SET_ERROR', true) 
        })
    },
    signOut({ commit }) {
      commit('SET_TOKEN', null)
      VueCookies.remove('auth-token')
      router.push('/accounts/signin')
    },
    getUsers({ commit, state }) {
      Axios.get(`${state.api_server}/accounts`)
        .then(res => {
          const nicks = {}
          state.results.push({ header: '모든 유저' })
          state.results.push({ divider: true })
          res.data.forEach(function(user) {
            nicks[user.id] = user.nickname
            state.results.push({ nickname: user.nickname, id: user.id })
          })
          commit('SET_USERS', nicks)
        })
    },
    draw({ commit, state }) {
      commit('SET_DRAWER', !state.drawer)
    },
    show({ commit, state }) {
      commit('SET_SHOW', !state.showSearch)
    },
    goToHome({ commit }) {
      router.push('/main')
      commit('SET_DRAWER', false)
    },
    goToBarters({ commit }) {
      router.push('/barters')
      commit('SET_DRAWER', false)
    },
    goToChecks({ commit }) {
      router.push('/checks')
      commit('SET_DRAWER', false)
    },
    goToEithers({ commit }) {
      router.push('/eithers')
      commit('SET_DRAWER', false)
    },
    goToTips({ commit }) {
      router.push('/tips')
      commit('SET_DRAWER', false)
    },
    goToProfile({ commit }) {
      router.push('/accounts/user')
      commit('SET_DRAWER', false)
    },
    report({ commit }) {
      commit('SET_DIALOG', true)
      commit('SET_DRAWER', false)
    },
    getUser({ commit, state }) {
      const requestHeaders = {
        headers : {
          token : VueCookies.get('auth-token'),
        }  
      }
      Axios.get(`${state.api_server}/accounts/info`, requestHeaders)
        .then(res => commit('SET_USERINFO', res.data))
        .catch(() => {
          commit('SET_TOKEN', null)
          VueCookies.remove('auth-token')
          alert('로그인 유효 시간이 만료되었습니다. 다시 로그인 해 주세요.')
          router.push('/accounts/signin')
        })
    },
    sendReport({ commit, state }, data) {
      Axios.post(`${state.api_server}/reports`, data)
        .then(() => {
          alert('신고 내역이 성공적으로 접수되었습니다.')
          commit('SET_DIALOG', false)
        })
        .catch(() => {
          alert('유효하지 않은 닉네임이거나, 이미 신고하신 유저입니다.')
          commit('SET_DIALOG', false)
        })
    }
  },
  modules: {
  }
})
