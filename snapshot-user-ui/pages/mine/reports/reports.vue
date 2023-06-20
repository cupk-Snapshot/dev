<template>
	<view>
		<view v-for="(item, index) in reportList">
			<uni-card :title="item.creat_time" :extra="status">
				<text style="display: block;">【{{item.wfType}}】<text style="float:right">{{item.wfTime}}</text></text>
				<text style="display: flex;">{{item.wfAddress}}</text>
				<view class="imageContant">
					<img class="image" v-if="item.pic1Url" :src="item.pic1Url">
					<img class="image" v-if="item.pic2Url" :src="item.pic2Url">
					<img class="image" v-if="item.pic3Url" :src="item.pic3Url">
				</view>
				<view style="display: flex;">
					<strong>审核状态：</strong>
					<strong style="color: seagreen;">{{item.status}}</strong>
				</view>
			</uni-card>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				reportList:[],
				user:{},
				token:'',
			}
		},
		onLoad(){
			var that=this
			that.token=uni.getStorageSync('SET_TOKEN')
			that.user=uni.getStorageSync('user')
			uni.request({
				url:'http://localhost:9955/user/report/all',
				method:'GET',
				header:{
					'Authorization':'Bearer '+that.token
				},
				data:{
					user_id:that.user.userId
				},
				success: (res) => {
					that.reportList=res.data.data
				}
			})
		},
		methods: {
		}
	}
</script>

<style scoped lang="scss">
.imageContant{
	display: inline-flex;
	margin-top:20px;
	.image{
		width: 90px;
		margin-right: 15px;
		height:	auto;
	}
}
</style>
