<template>
  <v-app>
    <v-app-bar 
      v-if="!isEntrance"
      app
      color="green darken-2"
      dark
    >
      <v-app-bar-nav-icon @click="draw"></v-app-bar-nav-icon>
      <div>
        <v-img
          alt="JARA Logo"
          class="shrink mr-2"
          contain
          src="@/assets/jara.png"
          transition="scale-transition"
          width="40"
        />
      </div>
      <v-toolbar-title class="font-weight-bold">JARA</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon @click="show" v-if="!isSearch">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <div v-else class="searchbar mt-6">
        <v-autocomplete
          v-model="searchWord"
          :search-input.sync="searched"
          :items="results"
          outlined
          dense
          color="white"
          autocapitalize="off"
          clearable
          item-text="nickname"
          item-value="id"
          item-color="green darken-1"
        ></v-autocomplete>
      </div>
      <v-btn @click="goToUser" icon v-if="isSearch"><v-icon>mdi-magnify</v-icon></v-btn>
      <v-menu offset-y :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn 
            icon
            v-bind="attrs"
            v-on="on"
          >
            <v-badge
              v-if="isRequested"
              color="deep-orange darken-1"
              overlap
            >
              <v-icon>mdi-bell</v-icon>
            </v-badge>
            <v-icon v-else>mdi-bell</v-icon>
          </v-btn>
        </template>
        <v-tabs
          background-color="lime"
          class="elevation-2"
          dark
          grow
        >
          <v-tabs-slider></v-tabs-slider>

          <v-tab>
            알림
          </v-tab>

          <v-tab-item>
            <v-card
              flat
              tile
            >
              <v-card-text>준비 중 입니다.</v-card-text>
            </v-card>
          </v-tab-item>

          <v-tab>요청</v-tab>
          <v-tab-item><v-card><v-card-text>준비 중 입니다.</v-card-text></v-card></v-tab-item>
        </v-tabs>
      </v-menu>

      </v-app-bar>

    <v-navigation-drawer
      :value="isDrawer"
      temporary
      app
    >
      <v-list
        nav
      >
        <v-list-item-group
          active-class="green darken-2 white--text text--accent-4"
        >
          <v-list-item two-line>
            <v-list-item-avatar>
              <v-icon>mdi-account-circle</v-icon>
            </v-list-item-avatar>
            <v-list-item-content v-if="!isLoggedIn">
              <v-list-item-title>방문자 님</v-list-item-title>
              <v-list-item-subtitle>안녕하세요</v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-content v-else @click="goToProfile">
              <v-list-item-title>{{ userInfo.nickname }} 님</v-list-item-title>
              <v-list-item-subtitle>안녕하세요</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-divider></v-divider>

          <v-list-item @click="goToHome">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goToBarters">
            <v-list-item-icon>
              <v-icon>mdi-shopping</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Barter</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goToChecks">
            <v-list-item-icon>
              <v-icon>mdi-checkbox-marked</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Check</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goToEithers">
            <v-list-item-icon>
              <v-icon>mdi-align-horizontal-left</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Either</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goToTips">
            <v-list-item-icon>
              <v-icon>mdi-lightbulb</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Tip</v-list-item-title>
          </v-list-item>

          <v-list-item @click="report">
            <v-dialog
              v-model="isDialog"
              width="500"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-list-item-icon v-bind="attrs" v-on="on">
                  <v-icon color="error">mdi-alarm-light</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Report</v-list-item-title>
              </template>
              <v-card>
                <v-card-title class="headline grey lighten-2">
                  <v-icon x-large color="red darken-1" class="mr-2">mdi-alarm-light</v-icon> 유저 신고
                </v-card-title>

                <v-card-text class="mt-5">
                  <div class="mb-5">더 나은 자라를 위해 함께해 주셔서 감사합니다.</div>
                  <v-form>
                    <div class="black--text font-weight-bold">신고할 유저의 닉네임</div>
                    <v-text-field
                      placeholder="닉네임을 입력해주세요."
                      v-model="reportData.accused_nickname"
                      :rules="[nickNameRules.required]"
                      outlined
                      color="green darken-2"
                      class="my-2"
                    ></v-text-field>
                    <div class="black--text font-weight-bold">신고 사유</div>
                    <v-textarea
                      label="신고 사유를 입력해 주세요."
                      v-model="reportData.contents"
                      :rules="[contentsRules.required]"
                      color="green darken-1"
                      auto-grow
                      outlined
                      rows="1"
                      row-height="15"
                      class="my-2"
                    ></v-textarea>
                  </v-form>
                  <div>더 노력하는 자라가 되겠습니다.<v-icon>mdi-turtle</v-icon></div>
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="red darken-1"
                    text
                    @click="sendReport"
                    class="font-weight-bold"
                  >
                    신고
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            
          </v-list-item>

          <v-divider class="bottom"></v-divider>

          <v-list-item @click="signOut" v-if="isLoggedIn">
            <v-list-item-icon>
              <v-icon>mdi-export</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Sign out</v-list-item-title>
          </v-list-item>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <!-- Sizes your content based upon application components -->
    <v-main>

      <!-- Provides the application the proper gutter -->
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: 'App',
  created() {
    this.$store.dispatch('getUsers')
    if (this.$route.path != "/") {this.$store.commit('SET_ENTRANCE', false)}
    if (this.$store.state.authToken&&this.$store.state.userInfo==null) {this.$store.dispatch('getUser')}
  },
  computed: {
    ...mapState([
      'userInfo',
      'users',
      'results',
      'authToken',
      'requested',
      'dialog',
      'api_server',
      'reportData',
      'nickNameRules',
      'contentsRules'
    ]),
    ...mapGetters([
      'isLoggedIn',
      'isEntrance',
      'isDrawer',
      'isSearch',
      'isRequested',
      'isDialog'
    ]),
  },
  methods: {
    ...mapActions([
      'signIn',
      'signOut',
      'draw',
      'show',
      'getUser',
      'getUsers',
      'goToHome',
      'goToBarters',
      'goToChecks',
      'goToEithers',
      'goToTips',
      'goToProfile',
      'report',
      'sendReport'
    ]),
    ...mapMutations([
      'SET_ENTRANCE',
      'SET_DIALOG',
    ]),
    querySelections(v) {
      this.items = this.$store.state.results.filter(e => {
        return (e || '').toLowerCase().indexOf((v || '').toLowerCase()) > -1
      })
    },
    goToUser() {
      if (this.$store.state.showSearch&&this.searchWord==null) {
        this.$store.dispatch('show')
      } else {
        this.$store.dispatch('show')
        this.$router.push(`/accounts/${this.searchWord}`)
        this.searchWord = null
      }
    },
  },
  data() {
    return {
      searched: null,
      searchWord: null,
    }
  },
  watch: {
    searched (val) {
      val && val !== this.searchWord && this.querySelections(val)
    },
  }
}
</script>

<style scoped>
  .searchbar {
    height: 64px;
  }
</style>
