<template>
  <v-container fluid>
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
      <div class="mx-3">
        <v-flex class="sm8 offset-sm3">
          <v-toolbar flat color="white">
            <v-dialog v-model="dialog" max-width="500px">
              <template v-slot:activator="{ on, attrs}">
                <v-spacer></v-spacer>
                <v-btn
                  color="primary"
                  dark
                  class="my-1"
                  v-bind="attrs"
                  v-on="on"
                >
                  New Item
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">{{ formTitle }}</span>
                </v-card-title>

                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.title" label="Title"></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.contents" label="Contents"></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.price" label="Price"></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="editedItem.tag_id" label="Type"></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                  <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
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
              <template v-slot:item.status="{ item }">
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
import _ from 'lodash'
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
      dialog: false,
      editedIndex: -1,
      editedItem: {
        title: '',
        tag_id: null,
        contents: '',
        writer: this.$store.state.userInfo.id,
        price: 0,
      },
      defaultItem: {
        title: '',
        tag_id: null,
        contents: '',
        writer: this.$store.state.userInfo.id,
        price: 0,
      },
    }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    },
    ...mapState([
      'api_server'
    ])
  },
  watch: {
    dialog(val) {
      val || this.close()
    }
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
    },
    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },
    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.barters[this.editedIndex], this.editedItem)
      } else {
        axios.post(`${this.$store.state.api_server}/barters/`,this.editedItem)
          .then(res => {
            // console.log(res.data)
            this.barters.push(res.data)
            this.barters = _.orderBy(this.barters,'id','desc')
          })
          .catch(err => {
            console.log(err)
          })
      }
      this.close()
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