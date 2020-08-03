<template>
  <v-container mt-5 v-if="isLoad" fluid>
    <v-alert v-if="isError" type="error">
      유효하지 않은 입력입니다.
    </v-alert>
    <v-icon x-large>mdi-account-edit-outline</v-icon>
    <v-form
      v-model="isValid"
      ref="form"
      class="px-3"
    >
      <div class="font-weight-bold mt-5">닉네임<v-icon class="ml-1">mdi-account</v-icon></div>
      <v-text-field
        v-model="user.nickname"
        :rules="[nickNameRules.required, nickNameRules.max]"
        background-color="white"
        placeholder="닉네임을 입력해주세요. (특수문자는 사용할 수 없습니다.)"
        outlined
        color="green darken-2"
        class="my-2"
      ></v-text-field>
    
      <div class="font-weight-bold">자기 소개<v-icon class="ml-1">mdi-card-account-details</v-icon></div>
      <v-textarea
        v-model="user.bio"
        color="green darken-2"
        class="my-2"
        outlined
        auto-grow
        rows="1"
        row-height="20"
        label="회원님을 어떻게 소개하시겠나요?"
      ></v-textarea>

      <div class="font-weight-bold">현재 비밀번호<v-icon class="ml-1">mdi-lock</v-icon></div>
      <v-text-field
        v-model="currentPWD"
        :append-icon="showCurrentPWD ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[currentPWDRules.required, currentPWDRules.same]"
        background-color="white"
        :type="showCurrentPWD ? 'text' : 'password'"
        placeholder="현재 비밀번호를 입력해주세요."
        outlined
        counter
        @click:append="showCurrentPWD = !showCurrentPWD"
        color="green darken-2"
        class="my-2"
      ></v-text-field>

      <div class="mb-2 font-weight-bold d-flex align-center">
        <v-btn icon @click="isChange = !isChange"><v-icon>mdi-lock-question</v-icon></v-btn>
        <div>비밀번호를 변경하시겠습니까?</div>
      </div>
      
      <div v-if="isChange" class="font-weight-bold">변경하실 비밀번호<v-icon class="ml-1">mdi-lock</v-icon></div>
      <v-text-field
        v-if="isChange"
        v-model="changePWD"
        :append-icon="showPWD ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[passwordRules.min]"
        background-color="white"
        :type="showPWD ? 'text' : 'password'"
        placeholder="비밀번호를 입력해주세요."
        outlined
        counter
        @click:append="showPWD = !showPWD"
        color="green darken-2"
        class="my-2"
      ></v-text-field>

      <div v-if="isChange" class="font-weight-bold">비밀번호 확인<v-icon class="ml-1">mdi-lock-check</v-icon></div>
      <v-text-field
        v-if="isChange"
        v-model="confirmPWD"
        :append-icon="showConfirmPWD ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[passwordConfirmRules.same]"
        background-color="white"
        :type="showConfirmPWD ? 'text' : 'password'"
        placeholder="비밀번호를 입력해주세요."
        outlined
        counter
        @click:append="showConfirmPWD = !showConfirmPWD"
        color="green darken-2"
        class="my-2"
      ></v-text-field>

      <div class="font-weight-bold d-flex align-center">주소<v-icon class="ml-2">mdi-map-search</v-icon></div>
      <v-text-field
        v-model="user.location"
        label="예) 서울특별시 강남구"
        :rules="[locationRules.required]"
        background-color="white"
        outlined
        color="green darken-2"
        class="my-2"
      ></v-text-field>
    </v-form>
    <div class="text-right px-3">
      <v-btn color="grey darken-1 white--text" class="mr-2" @click="cancle">취소</v-btn>
      <v-btn
        :disabled="!isValid"
        color="green darken-1 white--text"
        @click="updateInfo"
      >수정</v-btn>
    </div>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'ChageUserInfo',
  created() {
    this.getUserInfo()
  },
  computed: {
    ...mapState([
      'userInfo',
      'api_server'
    ])
  },
  data() {
    return {
      user: null,
      currentPWD: '',
      changePWD: '',
      confirmPWD: '',
      isLoad: false,
      isValid: false,
      isError: false,
      isSave: false,
      isChange: false,
      showCurrentPWD: false,
      showPWD: false,
      showConfirmPWD: false,
      nickNameRules: {
        required: value => !!value || '닉네임을 입력해주세요.',
        max: v => v.length <= 8 || '닉네임은 8자리 이하이어야 합니다.'
      },
      currentPWDRules: {
        required: value => !!value || '현재 비밀번호를 입력해주세요.',
        same: v => v == this.user.password || '비밀번호가 일치하지 않습니다.'
      },
      passwordRules: {
        required: value => !!value || '변경하실 비밀번호를 입력해주세요.',
        min: v => v.length >= 8 || '비밀번호는 8자리 이상이어야 합니다.',
      },
      passwordConfirmRules: {
        required: value => !!value || '입력하신 비밀번호를 한번 더 입력해주세요.',
        same: v => v == this.changePWD || '비밀번호가 일치하지 않습니다.'
      },
      locationRules: {
        requried: value => !!value || '주소를 입력해주세요.',
      },
    }
  },
  methods: {
    getUserInfo() {
      axios.get(`${this.$store.state.api_server}/accounts/${this.$route.params.user_id}`)
        .then(res => {
          this.user = res.data
          this.isLoad = !this.isLoad
        })
    },
    updateInfo() {
      if (this.changePWD.length > 0 && this.confirmPWD == this.changePWD) {this.user.password = this.changePWD}
      axios.put(`${this.$store.state.api_server}/accounts/${this.$route.params.user_id}`, this.user)
        .then(() => {
          alert('수정이 성공적으로 완료되었습니다.')
          this.isSave = !this.isSave
          this.$router.push(`/accounts/${this.$route.params.user_id}`)
        })
        .catch(() => {
          this.$vuetify.goTo(0)
          this.isError = !this.isError
        })
    },
    cancle() {
      this.$router.push(`/accounts/${this.$route.params.user_id}`)
    }
  },
  beforeRouteLeave(to, from, next) {
    if (!this.isSave) {
      if (confirm('입력 내용이 저장되지 않습니다. 정말로 이 페이지에서 벗어나시겠습니까?')) {next()}
      else {next(false)}
    } else {next()}
  },
}
</script>

<style>

</style>