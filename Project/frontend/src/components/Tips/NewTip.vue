<template>
    <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6">
        <v-card ref="form">
          <v-card-text>
            <v-text-field
              ref="title"
              v-model="tip.title"
              :rules="[() => !!tip.title || 'This field is required']"
              label="Title"
              placeholder="제목을 입력해 주세요."
              required
            ></v-text-field>
            <v-text-field
              ref="contents"
              v-model="tip.contents"
              :rules="[() => !!tip.contents || 'This field is required']"
              label="Content"
              placeholder="내용을 입력해 주세요."
              required
            ></v-text-field>
            <v-autocomplete
              ref="tag"
              v-model="tag"
              :rules="[() => !!tag || 'This field is required']"
              :items="tags"
              label="Tag"
              placeholder="Select..."
              required
            ></v-autocomplete>
          </v-card-text>
          <v-divider class="mt-12"></v-divider>
          <v-card-actions>
            <v-btn text>Cancel</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="createTip">Submit</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  name: 'NewTip',
  computed: {
    ...mapState([
      'userInfo',
      'api_server'
    ])
  },
  data() {
    return {
      tip: {
        title: '',
        contents: '',
        tag_id: 0,
        writer: this.$store.state.userInfo.id,
      },
      tags: ['요리', '세탁', '청소', '보관'],
      tag: '',
      formHasErrors: false,
    }
  },
  methods: {
    createTip() {
      const tag_id_dict = {'요리': 1, '세탁': 2, '청소': 3, '보관': 4}
      this.tip.tag_id = tag_id_dict[this.tag]

      console.log(this.$store.state.authToken)

      axios.post('https://fcm.googleapis.com/fcm/send',
        { "to": `${this.$store.state.authToken}`, "data": {"message": "푸시 푸시 베이베~ 오 푸시 베이베"}},
        {headers: {'Accept': 'application/json', 'Content-type' : 'application/json', 'Authorization': 'key=AAAAqnvMOtY:APA91bFyqcyHLsR0mVk5AzquA6oNgLEnuRvhyNdpUTKhC_tFL287Y6jCRAjtmKthB7M1daRIzoPyYhYkN7UBLy0CYt3fkKvFCSiNlJ3v_d5GttA593enRX0x3qGfnAnMl66NK966EHNw'}}
      )
        .then(res => {
          console.log('성공', res)
        })
        .catch(err => {
          console.log('실패',err)
        })

      axios.post(`${this.$store.state.api_server}/tips`, this.tip)
        .then(() => {
          axios.post('https://fcm.googleapis.com/fcm/send',
            { "to": `${this.$store.state.authToken}`, "data": {"message": "푸시 푸시 베이베~ 오 푸시 베이베"}},
            {headers: {'Accept': 'application/json', 'Content-type' : 'application/json', 'Authorization': 'key=AAAAqnvMOtY:APA91bFyqcyHLsR0mVk5AzquA6oNgLEnuRvhyNdpUTKhC_tFL287Y6jCRAjtmKthB7M1daRIzoPyYhYkN7UBLy0CYt3fkKvFCSiNlJ3v_d5GttA593enRX0x3qGfnAnMl66NK966EHNw'}}
          )
            .then(res => {
              console.log('성공', res)
            })
            .catch(err => {
              console.log('실패',err)
            })
          this.$router.push('/tips')
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style>

</style>