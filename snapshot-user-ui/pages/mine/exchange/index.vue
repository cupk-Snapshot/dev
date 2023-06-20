<template>
	<view class="content">
		<view class="t-wrap">
			<view class="t-jf">
				<view class="t-jf-points">
					<view class="t-jf-title">我的积分</view>
					<view class="t-jf-info">
						<view class="t-jf-num">{{allPoints}}</view>
						<view class="t-jf-detail" hover-class="t-hc" @click="toDetail">
							<text>积分明细</text>
							<image src="/static/images/mine/icon-right.png"></image>
						</view>
					</view>
				</view>
				<view class="t-jf-history" hover-class="t-hc" @click="toHistory">
					兑换历史
				</view>
			</view>
		</view>
		<view class="t-goods" v-if="goodsList&&goodsList.length>0">
			<view class="t-goods-item" v-for="(item,index) in goodsList" :key="index">
				<image class="t-goods-img" :src="item.picUrl"></image>
				<view class="t-goods-title t-text-oneline">{{item.title}}</view>
				 
				<view class="t-goods-score t-flex-row-s">
					<text>{{item.point}}</text>
					<text>积分</text>
				</view>
				<view class="t-dh t-flex-row" @click="saveChoose(item.goodsId)"><tui-button shape="circle" shadow @click="bDrawer">我要兑换</tui-button></view>
					<tui-drawer mode="bottom" :visible="bottomDrawer" @close="closeDrawer">
						<view class="tui-drawer__box">
							<navigator url="/pages/mine/exchange/address_setting?source=1" class="address-section">
								<text>选择地址</text>
								<view class="order-content">
									<uni-icons type="location" size="30"></uni-icons>
									<view class="cen">
										<view class="top">
											<text class="name">{{addressData.name}}</text>
											<text class="mobile">{{addressData.phoneNum}}</text>
										</view>
										<text class="address">{{addressData.area}} {{addressData.address}}</text>
									</view>
									<uni-icons type="right" size="30"></uni-icons>
								</view>
							</navigator>
							<text>详情信息</text>
							<view class="good-content">
								<image :src="goodinfo.picUrl"></image>
								<view>
									<text class="good-name">{{goodinfo.title}}</text>
								    <text class="good-score">{{goodinfo.point}}积分<text class="good-stock">商品库存：{{goodinfo.stocks}}</text></text>
								</view>
							</view>
							<button class="btn-submit" @click="sureToBuy(item.goodsId,addressData.addressId)">确定</button>
						</view>
					</tui-drawer>
			</view>
		</view>
		<view class="t-more t-flex-row">
			我也是有底线的哦~
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
				bottomPopup: false,
				bottomDrawer: false,
				allPoints:'',
				goodsList: [],
				addressData:{
					addressId: 0,
					name: '',
					phoneNum: '',
					area: '',
					address: '',
					isDefault: 0,
				},
				goodinfo:{},
				orderData:{},
				user:{},
				token:''
			}
		},
		onLoad() {
			this.token=uni.getStorageSync('SET_TOKEN')
			this.user=uni.getStorageSync('user')
			uni.request({
				url:'http://localhost:9955/user/goods/all',
				method:'GET',
				header:{
					 Authorization: 'Bearer '+this.token,
				},
				success: (res) => {
					this.goodsList=res.data.data
				},
			}),
			uni.request({
				url:'http://localhost:9955/user/points/details',
				method:'GET',
				header:{
					 Authorization: 'Bearer '+this.token,
				},
				data: {
					user_id: this.user.userId
				},
				success:(res) => {
					this.allPoints=res.data.data.total
				}
			}),
			uni.request({
				url:'http://localhost:9955/user/address/default',
				method:'GET',
				header:{
					 Authorization: 'Bearer '+this.token,
				},
				data: {
					user_id: this.user.userId
				},
				success:(res) => {
					this.addressData=res.data.data
					console.log("=======================");
					console.log(this.data.data);
					console.log(this.addressData);
					console.log("=======================");
				}
			})
		},
		onNavigationBarButtonTap(e) {
				uni.navigateTo({
					url: `/pages/mine/exchange/address_setting`
				})
			},
		methods: {
			//跳转积分明细
			toDetail() {
				uni.navigateTo({
					url: '/pages/mine/exchange/detail'
				})
			},
			toHistory() {
				uni.navigateTo({
					url: '/pages/mine/exchange/history'
				})
			},
			bDrawer() {
				this.bottomDrawer = true;
			},
			closeDrawer(e) {
				this.bottomDrawer = false;
			},
			saveChoose(Id){
				var li =this.goodsList;
				for(let j in li){
					let itee=li[j];
					if(itee.goodsId===Id){
						this.goodinfo=itee;
					}
				}
			},
			sureToBuy(goodsId, addressId){
				let id = uni.getStorageSync('user').userId
				let token = uni.getStorageSync('SET_TOKEN')
				uni.request({
					url: "http://localhost:9955/user/goods/buy",
					method: "POST",
					data: {
						user_id: id,
						goods_id: goodsId,
						address_id: addressId
					},
					header: {
						Authorization: 'Bearer ' + token,
						'Content-Type': 'application/x-www-form-urlencoded',
					},
					success: (res) => {
						this.$modal.showToast('兑换成功');
						this.bottomDrawer=false
					}
				})

			}
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
		.btn-submit{
			display: flex;
			float:right;
			flex-direction: row;
			justify-content: center;
			align-items: center;
			width: 310rpx;
			height: 70rpx;
			background: #17ba7a;
			border-radius: 35rpx;
			font-size: 32rpx;
			color: #FFFFFF;
		}
	}
	
	.t-hc {
		opacity: 0.7;
	}

	.t-wrap {
		height: 280rpx;
		width: 100%;
		box-sizing: border-box;

		.t-jf {
			width: 100%;
			height: 100%;
			box-sizing: border-box;
			padding-bottom: 40rpx;
			background:#3c96f3;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;

			.t-jf-points {
				padding-left: 70rpx;

				.t-jf-title {
					font-size: 32rpx;
					color: #fff;
				}

				.t-jf-info {
					margin-top: 20rpx;
					display: flex;
					flex-direction: row;
					justify-content: flex-start;
					align-items: flex-end;

					.t-jf-num {
						font-size: 56rpx;
						font-weight: bold;
						color: #fff;
						line-height: 56rpx;
					}

					.t-jf-detail {
						display: flex;
						flex-direction: row;
						justify-content: flex-start;
						align-items: center;

						text {
							font-size: 24rpx;
							color: #fff;
							margin-left: 20rpx;
						}

						image {
							width: 18rpx;
							height: 18rpx;
							margin-left: 6rpx;
						}
					}
				}
			}

			.t-jf-history {
				margin-right: 70rpx;
				padding: 15rpx 30rpx;
				background: #fff;
				border-radius: 50rpx;
				color: #3c96f3;
			}
		}

		.t-bg-wrap {
			position: relative;
			align-self: flex-start;
			top: 54rpx;

			.t-bg {
				--width: 440vw;
				position: absolute;
				height: var(--width);
				width: var(--width);
				left: calc(var(--width)*-1/2 + 50vw - 40rpx);
				top: calc(var(--width)*-1 + 1vw);
				border-radius: 50%;
				z-index: -1;
				background: linear-gradient(to bottom, #18ba7a, #88bf87);
			}
		}
	}

	.t-sign-con {
		width: 100%;
		box-sizing: border-box;
		position: relative;
		padding: 0rpx 30rpx;

		.wrap {
			width: 100%;
			height: 360rpx;
			border-radius: 10rpx;
			background-color: #fff;
			position: relative;
			top: -50rpx;
			box-sizing: border-box;
			box-shadow: 0 30rpx 50px rgba(0, 0, 0, .4);

			.t-sign-top {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;
				box-sizing: border-box;
				width: 100%;
				padding: 28rpx 20rpx;

				.lx {
					font-size: 32rpx;
					color: #333;

					text {
						color: #18ba7a;
						margin: 0rpx 3rpx;
					}
				}

				.gz {
					display: flex;
					flex-direction: row;
					justify-content: flex-start;
					align-items: center;
					font-size: 28rpx;
					color: #999;

					image {
						width: 28rpx;
						height: 28rpx;
						margin-left: 8rpx;
					}
				}
			}

			.t-sign-body {
				height: 124rpx;
				width: 100%;
				box-sizing: border-box;
				padding: 0rpx 20rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;

				.t-sign-item {
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					height: 100%;
					box-sizing: border-box;
					padding: 0rpx 12rpx;
					border-radius: 10rpx;

					&.signed {
						background: #27ba76;

						text {
							color: #fff !important;
						}
					}

					.t-img-con {
						width: 56rpx;
						height: 56rpx;
						border-radius: 50%;
						background: #57da78;
						display: flex;
						flex-direction: row;
						justify-content: center;
						align-items: center;

						.t-img {
							width: 40rpx;
							height: 40rpx;
							border-radius: 50%;
							background: #27ba76;
							display: flex;
							flex-direction: row;
							justify-content: center;
							align-items: center;

							image {
								width: 24rpx;
								height: 24rpx;
							}
						}
					}

					text {
						font-size: 20rpx;
						color: #777;
						margin-top: 12rpx;
					}
				}
			}

			.t-sign-btn {
				width: 514rpx;
				height: 80rpx;
				text-align: center;
				line-height: 80rpx;
				border-radius: 50rpx;
				background-color: #17ba7a;
				font-size: 32rpx;
				color: #fff;
				margin: 32rpx auto 0rpx;

				&.t-signed {
					background-color: #b8b8b8 !important;
				}
			}
		}
	}

	.t-o-wrap {
		width: 100%;
		position: relative;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		box-sizing: border-box;

		.t-sign-bg {
			width: 550rpx;
			border-bottom-left-radius: 20rpx;
			border-bottom-right-radius: 20rpx;
		}

		.t-g-jf {
			position: relative;
			top: -220rpx;
			font-size: 36rpx;
			color: #17ba7a;
		}

		.t-g-desc {
			position: relative;
			top: -200rpx;
			font-size: 28rpx;
			color: #999;
		}

		.close-btn {
			position: relative;
			top: -173rpx;
			width: 300rpx;
			height: 60rpx;
			border-radius: 50rpx;
			background: #17ba7a;
			text-align: center;
			line-height: 60rpx;
			font-size: 28rpx;
			color: #fff;
		}

		.t-rule {
			width: 690rpx;
			background: #fff;
			border-radius: 20rpx;
			padding: 30rpx;
			box-sizing: border-box;

			.title {
				text-align: center;
				color: #333;
				font-size: 32rpx;
				margin-top: 10rpx;
			}

			.desc {
				text-align: justify;
				margin-top: 20rpx;
				white-space: pre-line;
				font-size: 32rpx;
				color: #666;
				line-height: 50rpx;
			}
		}

		.t-close {
			width: 60rpx;
			height: 60rpx;
			margin-top: 40rpx;
			padding: 10rpx;
		}
	}
	.t-goods {
			width: 100%;
			box-sizing: border-box;
			display: flex;
			flex-direction: row;
			flex-wrap: wrap;
			justify-content: space-between;
			align-items: flex-start;
			padding: 16rpx 18rpx 0rpx;
	}
	
	.t-goods-item {
		width: 350rpx;
		height: 576rpx;
		background: #fff;
		border-radius: 10rpx;
		margin-top: 14rpx;
	}
	
	.t-goods-img {
		width: 350rpx;
		height: 350rpx;
		border-top-left-radius: 10rpx;
		border-top-right-radius: 10rpx;
		display: block;
	}
	
	.t-goods-title {
		width: 308rpx;
		margin: 30rpx auto 0rpx;
		font-size: 28rpx;
		color: #272727;
	}
	
	.t-goods-score {
		margin-top: 16rpx;
		height: 32rpx;
		line-height: 32rpx;
		margin-left: 20rpx;
	}
	
	.t-goods-score text:first-child {
		font-size: 32rpx;
		color: #D41428;
		font-weight: bold;
	}
	
	.t-goods-score text:last-child {
		font-size: 28rpx;
		color: #17ba7a;
		margin-left: 4rpx;
	}
	
	.t-dh {
		width: 310rpx;
		height: 70rpx;
		background: #17ba7a;
		border-radius: 35rpx;
		font-size: 32rpx;
		color: #FFFFFF;
		margin: 20rpx auto 0rpx;
	}
	
	.t-more {
		font-size: 28rpx;
		color: #888888;
		line-height: 40rpx;
		margin-top: 50rpx;
		margin-bottom: 85rpx;
	}
	.t-click-class {
		opacity: 0.7;
	}
	
	.t-flex-row {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}
	
	.t-flex-row-s {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
	}
</style>