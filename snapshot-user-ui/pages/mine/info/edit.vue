<template>
	<view class="container">
		<view class="example">
			<uni-forms ref="form" :model="info" labelWidth="80px">
				<uni-forms-item label="用户昵称" name="nickName">
					<uni-easyinput v-model="info.nickName" placeholder="请输入昵称" />
				</uni-forms-item>
				<uni-forms-item label="手机号码" name="phoneNum">
					<uni-easyinput v-model="info.phoneNum" placeholder="请输入手机号码" />
				</uni-forms-item>
				<uni-forms-item label="姓名" name="name">
					<uni-easyinput v-model="info.name" placeholder="请输入姓名" />
				</uni-forms-item>
			</uni-forms>
			<button type="primary" @click="submit">提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				info: {
					nickName: "",
					name: "",
					phoneNum: ""
				},
				token:'',
				user:{},
				rules: {
					nickName: {
						rules: [{
							required: true,
							errorMessage: '用户昵称不能为空'
						}]
					},
					phoneNum: {
						rules: [{
							required: true,
							errorMessage: '手机号码不能为空'
						}, {
							pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
							errorMessage: '请输入正确的手机号码'
						}]
					},
				}
			}
		},
		onLoad() {
			this.user = uni.getStorageSync('user');
			this.token=uni.getStorageSync("SET_TOKEN")
			//获取个人信息
			uni.request({
				url: 'http://localhost:9955/user/info',
				method: "GET",
				data: {
					user_id: this.user.userId,
				},
				header: {
					Authorization: 'Bearer ' + this.token,
				},
				success: (res) => {
					this.info = res.data.data
				}
			})
			
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			submit(ref) {
				this.$refs.form.validate().then(res => {
					uni.request({
						url: 'http://localhost:9955/user/update/info',
						method: "POST",
						data: {
							user_id: this.user.userId,
							nick_name: this.info.nickName,
							phone_num: this.info.phoneNum,
							name: this.info.name,
						},
						header: {
							'Content-Type': 'application/x-www-form-urlencoded',
							Authorization: 'Bearer ' + this.token,
						},
						success: (res) => {
							this.$modal.msgSuccess('修改成功')
						}
					})
				})
			}//submit
		}// method
	}
</script>

<style lang="scss">
	page {
		background-color: #ffffff;
	}

	.example {
		padding: 15px;
		background-color: #fff;
	}

	.segmented-control {
		margin-bottom: 15px;
	}

	.button-group {
		margin-top: 15px;
		display: flex;
		justify-content: space-around;
	}

	.form-item {
		display: flex;
		align-items: center;
		flex: 1;
	}

	.button {
		display: flex;
		align-items: center;
		height: 35px;
		line-height: 35px;
		margin-left: 10px;
	}
</style>
