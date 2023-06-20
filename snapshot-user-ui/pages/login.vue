<template>
  <view class="normal-login-container">
    <view class="logo-content align-center justify-center flex">
      <text class="title">您好，请登录</text>
    </view>
    <view class="login-form-content">
      <view class="input-item flex align-center">
        <view class="iconfont icon-user icon"></view>
        <input v-model="loginForm.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="loginForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
      </view>
      <view class="action-btn">
        <button @click="handleLogin" class="login-btn cu-btn block bg-blue lg round">登录</button>
      </view>
    </view>

    <view class="xieyi text-center">
      <text class="text-grey1">登录即代表同意</text>
      <text @click="handleUserAgrement" class="text-blue">《用户协议》</text>
      <text @click="handlePrivacy" class="text-blue">《隐私协议》</text>
    </view>
	<view class="register1 text-center">
		<text @click="tologinByMobile" class="text-blue">手机号登录</text>
	</view>
	<view class="register text-center">
		<text @click="toRegister" class="text-blue">还没有账号，先去注册</text>
	</view>
  </view>
</template>

<script>
  import { getCodeImg } from '@/api/login'
  export default {
    data() {
      return {
        globalConfig: getApp().globalData.config,
        loginForm: {
          username: "",
          password: ""
        }
      }
    },

    methods: {
      // 登录方法
      async handleLogin() {
        if (this.loginForm.username === "") {
			uni.showModal({
			  title: '提示',
			  content: '请输入您的账号',
			  cancelText: '取消',
			  confirmText: '确定',
			  success: function(res) {
			  }
			})
        } else if (this.loginForm.password === "") {
			uni.showModal({
			  title: '提示',
			  content: '请输入您的密码',
			  cancelText: '取消',
			  confirmText: '确定',
			  success: function(res) {
			  }
			})
        } 
		else {
		  this.$modal.loading("登录中，请耐心等待...")
		  this.pwdLogin()
		  }
      },
      // 密码登录
      async pwdLogin() {
        this.$store.dispatch('Login', this.loginForm).then((res) => {
		  console.log(res)
          this.$modal.closeLoading()
          this.loginSuccess()
        })
      },
      // 登录成功后，处理函数
      loginSuccess(result) {
        // 设置用户信息
       this.$tab.switchTab('/pages/index') 
      },
	  toRegister(){
	  		this.$tab.navigateTo('/pages/register') 
	  },
	  tologinByMobile(){
	  		this.$tab.navigateTo('/pages/loginByMobile') 
	  },
    },
}

</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }
  .register{
  		color: #333;
  		margin-top: 40px;
  }
  .register1{
  		color: #333;
  		margin-top: 40px;
  }
  .normal-login-container {
    width: 100%;

    .logo-content {
      width: 100%;
      font-size: 21px;
      text-align: center;
      padding-top: 15%;

      image {
        border-radius: 4px;
      }

      .title {
        margin-left: 10px;
      }
    }

    .login-form-content {
      text-align: center;
      margin: 20px auto;
      margin-top: 15%;
      width: 80%;

      .input-item {
        margin: 20px auto;
        background-color: #f5f6f7;
        height: 45px;
        border-radius: 20px;

        .icon {
          font-size: 38rpx;
          margin-left: 10px;
          color: #999;
        }

        .input {
          width: 100%;
          font-size: 14px;
          line-height: 20px;
          text-align: left;
          padding-left: 15px;
        }

      }

      .login-btn {
        margin-top: 40px;
        height: 45px;
      }

      .xieyi {
        color: #333;
        margin-top: 20px;
      }
      
	  
      .login-code {
        height: 38px;
        float: right;
      
        .login-code-img {
          height: 38px;
          position: absolute;
          margin-left: 10px;
          width: 200rpx;
        }
      }
    }
  }

</style>
