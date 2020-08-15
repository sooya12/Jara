<template>
  <v-container fluid style="potition: relative">
    <div class="mt-5">
      <div class="mx-3 font-weight-bold text-sm-h3 text-h4">
        유익한 자라
        <v-icon x-large class="ml-2">mdi-lightbulb-outline</v-icon>
      </div>
      <div>
        <v-flex class="sm6 offset-sm3 justify-center">
          <v-text-field
            label="팁 게시글 검색..."
            v-model="search"
            class="col-12"
          >
          </v-text-field>
        </v-flex>
      </div>
      <div align="center" justify="center">
        <TipsItem
          v-for="(tip, index) in calData"
          :key="index"
          :tip="tip"
        />
        <v-pagination
          v-model="curPageNum"
          :length="numOfPages">
        </v-pagination>
      </div>
      <v-btn @click="write" style="position: fixed; bottom:3vh; right: 3vh" color="success" fab small dark absolute bottom right>
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios'
import TipsItem from '../components/Tips/TipsItem.vue'
import { mapState } from 'vuex'
import _ from 'lodash'


export default {
  name: 'Tips',
  components: {
    TipsItem,
  },
  data() {
    return {
      tips: [],
      search: '',
      tipPerPage: 5,
      curPageNum: 1,
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/tips/`)
      .then(res => {
        // console.log(res.data)
        this.tips = _.orderBy(res.data, 'id', 'desc')
      })
      .catch(err => {
        console.log(err)
      })
  },
  methods: {
    write() {
      this.$router.push('/tips/new')
    }
  },
  computed: {
    startOffset() {
      return ((this.curPageNum - 1) * this.tipPerPage)
    },
    endOffset() {
      return (this.startOffset + this.tipPerPage)
    },
    numOfPages() {
      return Math.ceil(this.tips.filter((tip) => {
        return tip.title.toLowerCase().includes(this.search.toLowerCase())
      }).slice(0).length / this.tipPerPage) // 여기 부분 살리기
    },
    calData() {
      return this.tips.filter((tip) => {
        return tip.title.toLowerCase().includes(this.search.toLowerCase())
      }).slice(this.startOffset, this.endOffset)
    },
    ...mapState([
      'api_server'
    ])
  },
}
</script>


<style scoped>
 .box {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
 }
</style>