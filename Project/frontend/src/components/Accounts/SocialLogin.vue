<template>
  <v-container>
    
  </v-container>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: 'SocialLogin',
  computed: {
    ...mapMutations([
      'SET_USERINFO',
      'SET_TOKEN'
    ])
  },
  created() {

  },
  methods: {

  },
}
</script>

<style>

</style>