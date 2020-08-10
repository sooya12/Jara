<template>
  <v-container fluid style="potition: relative">
    <div class="mt-5">
      <div class="mx-3 font-weight-bold text-center text-sm-h3 text-h4">
        Barters
      </div>
      <div>
        <v-flex class="sm6 offset-sm3 justify-center">
          <v-text-field
            label="Search Items..."
            v-model="search"
            class="col-12"
          >
          </v-text-field>
        </v-flex>
      </div>
      <!-- <div align="center" justify="center">
        <BartersItem
          v-for="(barter, index) in barters"
          :key="index"
          :barter="barter"
        />
      </div> -->
      <!-- <v-btn @click="write" style="position: fixed; bottom:3vh; right: 3vh" color="primary" fab small dark absolute bottom right>
        <v-icon>mdi-pencil</v-icon>
      </v-btn> -->
      <v-container>
        <v-layout row wrap>
          <v-flex xs12>
            <v-data-table
              :headers="headers"
              :items="barters"
              :loading="loading"
              :page.sync="page"
              :items-per-page="5"
              hide-default-footer
              class="elevation-1"
              @page-count="pageCount = $event"
              :search="search"
              :custom-filter="filterSearch"
            >
              <template v-slot:item.status="{ item }" v-slot:body.append>
                <v-chip :color="getColor(item.status)" dark>
                  {{ getStatus(item.status) }}
                </v-chip>
              </template>
            </v-data-table>
            <div class="text-center pt-2">
              <v-pagination v-model="page" :length="pageCount"></v-pagination>
            </div>
          </v-flex>
        </v-layout>
      </v-container>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios'
// import BartersItem from '../components/Barters/BartersItem.vue'
// import _ from 'lodash'
import { mapState } from 'vuex'

export default {
  name: 'Barters',
  // components: {
  //   BartersItem,
  // },
  data() {
    return {
      barters: [],
      headers: [
        { text: '날짜', value: 'created_at', sortable: true },
        { text: '제목', value: 'title', sortable: false },
        { text: '가격', value: 'price', sortable: true },
        { text: '조회 수', value: 'hits', sortable: true },
        { text: '상태', value: 'status', sortable: true },
      ],
      loading: false,
      search: '',
      page: 1,
      pageCount: 0,
    }
  },
  computed: {
    ...mapState([
      'api_server'
    ])
  },
  created() {
    this.loading = true
    axios.get(`${this.$store.state.api_server}/barters/`)
      .then(res => {
        // console.log(res.data)
        this.barters = res.data
        this.loading = false
      })
      .catch(err => {
        console.log(err)
        this.loading = false
      })
  },
  methods: {
    // id2date(val) {
    //   if (!val) return '잘못된 시간 정보'
    //   return new Date(parseInt(val.substring(0,8), 16) * 1000).toLocaleString()
    // }
    getColor(s) {
      if (s) return 'gray'
      else return 'green'
    },
    getStatus(s) {
      if (s) return '완료'
      else return '진행 중'
    },
    filterSearch(value, search) {
      return value != null && search !=null &&
        typeof value ==='string' && value.toString().indexOf(search) !== -1
    }
  }
}
</script>

<style scoped>
  #barters {
    height: 100%;
  }
  #working {
    padding-top: 20vh;
  }
</style>