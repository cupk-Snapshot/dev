<template>
	<view>
	  <view class="tui-drawer__box" v-for="(item, index) in orderList" :key="index">
			<text>收货信息</text>
			<view class="order-content">
				<uni-icons type="location" size="30"></uni-icons>
				<view class="cen">
					<view class="top">
						<text class="name">{{item.name}}</text>
						<text class="mobile">{{item.mobile}}</text>
					</view>
					<text class="address">{{item.area}} {{item.address}}</text>
				</view>
				<uni-icons type="right" size="30"></uni-icons>
			</view>
		<text>详情信息</text>
		<view class="good-content">
			<image :src="item.goodsUrl"></image>
			<view>
				<text class="good-name">{{item.goodsDesc}}</text>
			    <text class="good-score">{{item.goodsScore}}积分</text>
			</view>
		</view>
		<text>兑换时间：{{item.creatTime}}</text>
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
			}
		},
		onLoad() {
			uni.request({
				url:"",
				method:"GET",
				data:{
					user_id:userId,
				},
				success: (res) => {
					console.log(res.data),
					orderList=res.data
				}
			})
		}
		
	}
</script>

<style scoped lang="scss">
	.content {
		box-sizing: border-box;
		padding-bottom: 20rpx;
		background: #fff;
		min-width: 100vw;
		min-height: 100vh;
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