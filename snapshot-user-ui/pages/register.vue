<template>
  <view class="normal-login-container">
    <view class="logo-content align-center justify-center flex">
      <image style="width: 100rpx;height: 100rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
      </image>
      <text class="title">您好，请注册</text>
    </view>
    <view class="login-form-content">
      <view class="input-item flex align-center">
        <view class="iconfont icon-user icon"></view>
        <input v-model="loginForm.username" class="input" type="text" placeholder="请输入用户名" maxlength="30" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="loginForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
      </view>
	  <view class="input-item flex align-center">
	    <uni-icons class="icon" type="phone" size="16"></uni-icons>
	    <input v-model="loginForm.mobile" class="input" type="text" placeholder="请输入手机号 " maxlength="30" />
	  </view>
      <view class="input-item flex align-center" style="width: 60%;margin: 0px;">
        <view class="iconfont icon-code icon"></view>
        <input v-model="loginForm.code" type="number" class="input" placeholder="请输入验证码" maxlength="6" />
        <view class="login-code"> 
		  <text class="code-text":class="{ gray: showTime }" @click="handleGetCodeClick">{{showTime ?currentCountTime+'再次获取验证码':'获取验证码'}}</text>
        </view >
      </view>
      <view class="action-btn">
        <button @click="handleRegister" class="login-btn cu-btn block bg-blue lg round">注册</button>
      </view>
    </view>

    <view class="xieyi text-center">
      <text class="text-grey1">注册即代表同意</text>
      <text @click="handleUserAgrement" class="text-blue">《用户协议》</text>
      <text @click="handlePrivacy" class="text-blue">《隐私协议》</text>
    </view>
	<view class="login text-center">
		<text @click="toLogin" class="text-blue">已有帐号，去登录</text>
	</view>
  </view>
</template>

<script>
	import { reactive, onMounted } from 'vue'
  export default {
    data() {
      return {
        globalConfig: getApp().globalData.config,
        loginForm: {
          username: "",
		  mobile:"",
          password: "",
          code: ""
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
	//验证码第一次发送后倒计时60秒重发
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
			url:"http://localhost:9955/oauth/sms",
			method:"GET",
			data:{
				phone_num:this.loginForm.mobile
			},
			success: (res) => {
				console.log(res)
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
      // 注册方法
      async handleRegister() {
        if (this.loginForm.username === "") {
          this.$modal.msgError("请输入您的账号")
        } else if (this.loginForm.password === "") {
          this.$modal.msgError("请输入您的密码")
        } else if (this.loginForm.code === "") {
          this.$modal.msgError("请输入验证码")
        } else {
          this.$modal.loading("注册中，请耐心等待...");
		  let that=this;
          uni.request({
			  url:"http://localhost:9955/user/signup",
			  method:"POST",
			  header:{
				  'Content-Type':'application/x-www-form-urlencoded'
			  },
			  data:{
				  username:that.loginForm.username,
				  phone_num:that.loginForm.mobile,
				  password:that.loginForm.password,
				  sms_code:that.loginForm.code
			  },
			  success: (res) => {
				  console.log(res.data);
				  uni.showToast({
				    title: '注册成功',
				    icon: 'success',
				    duration: 2000
				  })
				  that.$tab.reLaunch('/pages/login')
			  },
			  fail: () => {
				    uni.showModal({
				      title: '提示',
				      content: '注册失败',
				      cancelText: '取消',
				      confirmText: '确定',
				      success: function(res) {
				      }
				    })
			  }
		  })
        }
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
  .login{
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
