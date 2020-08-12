<template>
  <v-container fluid class="mt-5">
    <div class="text-sm-h3 text-h4 font-weight-bold ml-3">JARA's Care<v-icon x-large class="ml-2">mdi-chevron-down-box-outline</v-icon></div>
    <div class="mt-5 ml-3">
      <v-chip
        @click="red"
        color="red darken-1"
        text-color="white"
      >
        집 구하기
        <v-icon right>mdi-home-group</v-icon>
      </v-chip>
      <v-chip
        @click="orange"
        color="orange darken-2"
        text-color="white"
      >
        입주
        <v-icon right>mdi-home-city</v-icon>
      </v-chip>
      <v-chip
        @click="yellow"
        color="yellow"
        text-color="white"
      >
        이사
        <v-icon right>mdi-truck-outline</v-icon>
      </v-chip>
      <v-chip
        @click="green"
        color="green darken-2"
        text-color="white"
      >
        자라
        <v-icon right>mdi-turtle</v-icon>
      </v-chip>
    </div>
    <v-divider class="my-5"></v-divider>
    <div v-if="isRed">
      메롱
    </div>
    <div v-if="isOrange">
      바보
    </div>
    <div v-if="isYellow">
      멍청이
    </div>
    <div v-if="isGreen">
      <div v-if="self.length==0" class="text-center">자라님의 체크리스트를 직접 만들어보세요.</div>
      <v-form v-model="isValid" class="mt-3 px-3 d-flex">
        <v-textarea
          v-model="checkItem"
          label="입력해주세요."
          color="green darken-1"
          auto-grow
          outlined
          rows="1"
          row-height="10"
          :rules="[checkListRules.required, checkListRules.min]"
        ></v-textarea>
        <div>
          <v-btn @click="addCheck" text class="mt-3 font-weight-bold" :disabled="!isValid" color="green darken-1">등록</v-btn>
        </div>
      </v-form>
      <div v-for="(item, idx) in self" :key="idx" class="px-3 d-flex justify-space-between align-center">
        <v-checkbox
          v-model="item.checked"
          color="green darken-2"
          :label="item.item"
        ></v-checkbox>
        <v-btn @click="del(idx)" icon><v-icon>mdi-close-circle</v-icon></v-btn>
      </div>
    </div> 
  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Checks',
  data() {
    return {
      isRed: true,
      isOrange: false,
      isYellow: false,
      isGreen: false,
      isValid: false,
      seek: [],
      in: [],
      out: [],
      self: [],
      checkItem: '',
      checkListRules: {
        required: v => !!v || '유효한 입력이 아닙니다.',
        min: val => val.trim().length > 0 || '유효한 입력이 아닙니다.'
      }
    }
  },
  methods: {
    red() {
      this.isRed = true
      this.isOrange = false
      this.isYellow = false
      this.isGreen = false
    },
    orange() {
      this.isRed = false
      this.isOrange = true
      this.isYellow = false
      this.isGreen = false
    },
    yellow() {
      this.isRed = false
      this.isOrange = false
      this.isYellow = true
      this.isGreen = false
    },
    green() {
      this.isRed = false
      this.isOrange = false
      this.isYellow = false
      this.isGreen = true
    },
    addCheck() {
      const data = {
        item: this.checkItem,
        checked: false
      }
      this.self.push(data)
      this.checkItem = ''
    },
    del(index) {
      this.self.splice(index ,1)
    }
  },
  computed: {
    ...mapState([
      'userInfo'
    ])
  }
}
</script>

<style scoped>
</style>