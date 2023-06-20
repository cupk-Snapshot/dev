<template>
  <view class="pwd-retrieve-container">
    <uni-forms ref="form" :value="user" labelWidth="80px">
      <uni-forms-item name="oldPassword" label="旧密码">
        <uni-easyinput type="password" v-model="pwd.password" placeholder="请输入旧密码" />
      </uni-forms-item>
      <uni-forms-item name="newPassword" label="新密码">
        <uni-easyinput type="password" v-model="pwd.newPassword" placeholder="请输入新密码" />
      </uni-forms-item>
      <uni-forms-item name="confirmPassword" label="确认密码">
        <uni-easyinput type="password" v-model="pwd.repeat" placeholder="请确认新密码" />
      </uni-forms-item>
      <button type="primary" @click="submit">提交</button>
    </uni-forms>
  </view>
</template>

<script>
  import { updateUserPwd } from "@/api/system/user"

  export default {
    data() {
      return {
		token: '',
		user: {},
        pwd: {
          password: '',
          newPassword: '',
          repeat: ''
        },
        rules: {
          password: {
            rules: [{
              required: true,
              errorMessage: '旧密码不能为空'
            }]
          },
          newPassword: {
            rules: [{
                required: true,
                errorMessage: '新密码不能为空',
              },
              {
                minLength: 6,
                maxLength: 20,
                errorMessage: '长度在 6 到 20 个字符'
              }
            ]
          },
          repeat: {
            rules: [{
                required: true,
                errorMessage: '确认密码不能为空'
              }, {
                validateFunction: (rule, value, data) => data.newPassword === value,
                errorMessage: '两次输入的密码不一致'
              }
            ]
          }
        }
      }
    },
    onReady() {
      this.$refs.form.setRules(this.rules)
    },
	onLoad() {
	  this.token=uni.getStorageSync('SET_TOKEN')
	  this.user=uni.getStorageSync('user')
	},
    methods: {
      submit() {
       uni.request({
			url:'http://localhost:9955/user/update/passwd',
			method:"POST",
			data: {
				user_id:this.user.userId,
				passwd:this.pwd.password,
				new_passwd:this.pwd.newPassword,
				repeat:this.pwd.repeat,
			},
			header:{
				'Content-Type':'application/x-www-form-urlencoded',
				 Authorization: 'Bearer '+this.token,
			},
			success: (res) => {
				if(res.data.code==500){
					uni.showToast({
						title:'密码输入错误'
					})
				}else {
					this.$modal.msgSuccess('修改成功，请重新登录')
					this.$tab.redirectTo('/pages/login')
				}
			},
			
		})
      }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }

  .pwd-retrieve-container {
    padding-top: 36rpx;
    padding: 15px;
  }
</style>
