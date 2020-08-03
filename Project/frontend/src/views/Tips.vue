<template>
  <v-container fluid style="potition: relative">
    <div class="mt-5">
      <div class="mx-3 font-weight-bold text-center text-sm-h3 text-h4">
        꿀 Tips
      </div>
      <div>
        <v-flex class="sm6 offset-sm3 justify-center">
          <v-text-field
            label="Search Tips..."
            v-model="search"
            class="col-6"
          >
          </v-text-field>
        </v-flex>
      </div>
      <div align="center" justify="center">
        <TipsItem
          v-for="tip in tips"
          :key="tip.id"
          :tip="tip"
        />
      </div>
      <v-btn @click="write" style="position: fixed; bottom:3vh; right: 3vh" color="primary" fab small dark absolute bottom right>
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios'
import TipsItem from '../components/Tips/TipsItem.vue'
import { mapState } from 'vuex'

export default {
  name: 'Tips',
  components: {
    TipsItem,
  },
  data() {
    return {
      tips: [],
      search: '',
      searchTip: [],
      tipPerPage: 2,
      curPageNum: 1,
    }
  },
  created() {
    axios.get(`${this.$store.state.api_server}/tips/`)
      .then(res => {
        // console.log(res.data)
        this.tips = res.data
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
      // return Math.ceil(this.tips.length / this.tipPerPage) // 여기 부분 살리기
      return Math.ceil(15 / this.tipPerPage) // 나중에 지우기
    },
    calData() {
      // this.searchTip = this.tips.filter((data) => {
      //   return data.tag.toLowerCase().includes(this.search.toLowerCase())
      // }).slice(0)
      // return this.searchTip.slice(this.startOffset, this.endOffset)
      return this.tips.slice(this.startOffset, this.endOffset)
    },
    ...mapState([
      'api_server'
    ])
  }
}
</script>


<style scoped>
 .box {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
 }
</style>