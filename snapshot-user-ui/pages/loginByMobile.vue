<template>
  <view class="normal-login-container">
    <view class="logo-content align-center justify-center flex">
      <text class="title">您好，请登录</text>
    </view>
    <view class="login-form-content">
      <view class="input-item flex align-center">
        <uni-icons class="icon" type="phone" size="16"></uni-icons>
        <input v-model="loginForm.mobile" class="input" type="text" placeholder="请输入手机号 " maxlength="30" />
      </view>
      <view class="input-item flex align-center" style="width: 60%;margin: 0px;">
        <view class="iconfont icon-code icon"></view>
        <input v-model="loginForm.code" type="number" class="input" placeholder="请输入验证码" maxlength="4" />
        <view class="login-code"> 
      		  <text class="code-text":class="{ gray: showTime }" @click="handleGetCodeClick">{{showTime ?currentCountTime+'再次获取验证码':'获取验证码'}}</text>
        </view>
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
		<text @click="toLogin" class="text-blue">账号密码登录</text>
	</view>
	<view class="register text-center">
		<text @click="toRegister" class="text-blue">还没有账号，先去注册</text>
	</view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        codeUrl: "",
        globalConfig: getApp().globalData.config,
        loginForm: {
          mobile:"",
          code: "",
        },
		countTime: 60,
		currentCountTime: 0,
		showTime:false,
		timeId:null
      }
    },
	mounted() {
		this.currentCountTime = this.countTime
	},
    methods: {
      // 登录方法
      async handleLogin() {
        if (this.loginForm.mobile === "") {
          this.$modal.msgError("请输入您的手机号")
        } else if (this.loginForm.code === ""){
          this.$modal.msgError("请输入验证码")
        } else {
          this.$modal.loading("登录中，请耐心等待...")
          this.mobLogin()
        }
      },
	  handleGetCodeClick(){
	  	if (this.loginForm.mobile === "") {
	  		this.$toast('请输入您的手机号');
	  		return
	  	}
	  	else if(!/(^1[3|4|5|7|8|9][0-9]{9}$)/.test(this.loginForm.mobile)){
	  		this.$toast('请输入正确的手机号码');
	  		return;
	  	}
	    	this.showTime = true
	    	if(this.showTime && this.currentCountTime !== this.countTime) return
	    	this.currentCountTime
	  	uni.request({
	  		url:"",
	  		method:"POST",
	  		data:{
	  			mobile:this.mobile,
	  			type:'注册发送验证码'
	  		},
	  		success: () => {
	  			uni.showToast({
	  			  title: '验证码已发送',
	  			  icon: 'success',
	  			  duration: 2000
	  			})
	  			this.timeId = setInterval(()=>{
	  				this.currentCountTime--
	  				if(this.currentCountTime <= 0) {
	  					this.currentCountTime = this.countTime
	  					this.showTime = false
	  					clearInterval(this.timeId)
	  				}
	  			},1000)
	  		},
	  		fail: () => {
	  			this.$modal.msgError("发送失败，请稍后再试")
	  		}
	  	})
	  },
      //手机号登录
      async mobLogin() {
        this.$store.dispatch('Login', this.loginForm).then(() => {
          this.$modal.closeLoading()
          this.loginSuccess()
        }).catch(() => {
          if (this.captchaEnabled) {
            this.getCode()
          }
        })
      },
      // 登录成功后，处理函数
      loginSuccess(result) {
        // 设置用户信息
        this.$store.dispatch('GetInfo').then(res => {
          this.$tab.reLaunch('/pages/index')
        })
      },
	  toRegister(){
	  		 this.$tab.navigateTo('/pages/register') 
	  },
	  toLogin(){
	  		 this.$tab.navigateTo('/pages/login') 
	  }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }
  .register1{
  		color: #333;
  		margin-top: 40px;
  }
  .register{
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
      		.code-text {
      			font-size: 14px;
      			color: #30C6B3;
      			position: absolute;
      			margin-left: 10px;
      			width: 200rpx;
      			height: 38px;
      			text-align: left;
      			padding-left: 15px;
      			margin-top:10px;
      		}
      }
    }
  }

</style>
