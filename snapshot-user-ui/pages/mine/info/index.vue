<template>
  <view class="container">
    <uni-list>
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'person-filled'}" title="昵称" :rightText="user.nickName" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'phone-filled'}" title="身份证号" :rightText="user.idcard" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'email-filled'}" title="姓名" :rightText="user.name" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'auth-filled'}" title="手机号" :rightText="user.phoneNum" />
    </uni-list>
  </view>
</template>

<script>
  import { getUserProfile } from "@/api/system/user"

  export default {
    data() {
      return {
        user: {},
      }
    },
    onLoad() {
      this.user=uni.getStorageSync('user');
    },
    methods: {
	},
}
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }
</style>
