<!-- <template>
	<view class='content'>
	  <view class="tui-drawer__box" v-for="(item, index) in orderList" :key="index">
			<text>收货信息</text>
			<view class="order-content">
				<uni-icons type="location" size="30"></uni-icons>
				<view class="cen">
					<view class="top">
						<text class="name">{{item.addressVo.name}}</text>
						<text class="mobile">{{item.addressVo.phoneNum}}</text>
					</view>
					<text class="address">{{item.addressVo.area}} {{item.addressVo.address}}</text>
				</view>
				<uni-icons type="right" size="30"></uni-icons>
			</view>
		<text>详情信息</text>
		<view class="good-content">
			<image :src="item.goodsUrl"></image>
			<view>
				<text class="good-name">{{item.title}}</text>
			    <text class="good-score">{{item.point}}积分</text>
			</view>
		</view>
		<text>兑换时间：{{item.creatTime}}</text>
	  </view>
	</view>
</template> -->
<template>
	<view>
		<view v-for="(item, index) in orderList">
			<uni-card :title="订单信息" :extra="status" type="line">
				<text class="good-name">兑换时间：{{item.createTime}}</text>
				<text style="display: flex;">商品信息：</text>
				<view class="imageContant">
					<img class="image" v-if="item.picUrl" :src="item.picUrl">
					<view>
						<text class="good-name">兑换了一个{{item.title}}</text>
					    <text class="good-score">消耗{{item.point}}积分</text>
					</view>
				</view>	
					<text style="display: flex;">收货信息：</text>
					<text style="display: flex;">收货人：{{item.addressVo.name}}     手机号{{item.addressVo.phoneNum}}</text>
					<text style="display: flex;">收货地址：{{item.addressVo.area}} {{item.addressVo.address}}</text>
			</uni-card>
		</view>
	</view>
</template>

<script>
	import tuiDrawer from "@/components/tui-drawer/tui-drawer.vue"
	import tuiButton from "@/components/tui-button/tui-button.vue"
	import tuiBottomPopup from "@/components/tui-bottom-popup/tui-bottom-popup.vue" 
	import { getUserProfile } from "@/api/system/user"
	export default {
		components:{
			tuiDrawer,
			tuiButton,
			tuiBottomPopup
		},
		data() {
			return {
				orderList:[],
				token:'',
				user:{}
			}
		},
		onLoad() {
			this.token=uni.getStorageSync('SET_TOKEN')
			this.user=uni.getStorageSync('user')
			uni.request({
				url:'http://localhost:9955/user/order/all',
				method:"GET",
				data:{
					user_id:this.user.userId,
				},
				header:{
					 Authorization: 'Bearer '+this.token,
				},
				success: (res) => {
					console.log('============\n' + res.data),
					this.orderList=res.data.data
				}
			})
		}
		
	}
</script>

<style scoped lang="scss">
	.imageContant{
		display: inline-flex;
		
		.good-score{
			display: flex;
			margin-top:20rpx;
			color: $font-color-light;
			.good-stock{
				margin-left:200rpx;
			}
		}
	}
	.image{
		width: 40%;
		height:	auto;
	}
	.content {
		box-sizing: border-box;
		padding-bottom: 20rpx;
		background: #fff;
		min-width: 100vw;
		min-height: 100vh;
		margin-top: 50px;
	}
	
	.tui-drawer__box {
		height: 500rpx;
		padding: 20rpx;
		box-sizing: border-box;
		
		.address-section {
			padding:0;
			background: #ffffff;
			position: relative;
		
			.order-content {
				display: flex;
				align-items: center;
				margin-top: 10upx;
			}
			.cen {
				display: flex;
				flex-direction: column;
				flex: 1;
				font-size: 28upx;
				color: $font-color-dark;
			}
			.name {
				font-size: 34upx;
				margin-right: 24upx;
			}
			.address {
				margin-top: 16upx;
				margin-right: 20upx;
				color: $font-color-light;
			}
		}
		.good-content{
			display: flex;
			height:200rpx;
			position: relative;
			margin-top: 16upx;
			image{
				float:left;
				width: 200rpx;
				height: 200rpx;
				margin-left: 6rpx;
			}
			.good-score{
				display: flex;
				margin-top:100rpx;
				color: $font-color-light;
				.good-stock{
					margin-left:200rpx;
				}
			}
		}
	}
</style>