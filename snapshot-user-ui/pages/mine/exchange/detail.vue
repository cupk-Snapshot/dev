<template>
	<view class="content">
		<view class="t-body" v-if="scoreList && scoreList.length>0">
			<view class="t-jf-item t-flex-row-sb" v-for="(item,index) in scoreList" wx:key="index">
				<view class="t-flex-col-s t-lt">
					<text>{{item.type}}</text>
					<text
						:class="[{'t-hx-color':item.score<0,'t-p-color':item.score>0}]">{{item.score>0?("+"+item.score):item.score}}</text>
				</view>
				<view class="t-rt">{{item.createAt}}</view>
			</view>
		</view>
		<view class="t-more t-flex-row">
			没有更多数据哦~
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				scoreList: [{
						type: '分享好友',
						score: 100,
						createAt: '2022-12-09'
					},
					{
						type: '兑换商品',
						score: -5000,
						createAt: '2022-11-08'
					},
					{
						type: '分享好友',
						score: 100,
						createAt: '2022-10-09'
					},
					{
						type: '浏览课程',
						score: 1000,
						createAt: '2022-09-19'
					},
					{
						type: '分享好友',
						score: 100,
						createAt: '2022-09-09'
					},
				],
				user:{}
			}
		},
		onLoad(){
			this.getUser()
		},
		methods: {
            getUser(){
              getUserProfile().then(response => {
                this.user = response.data
              })
            },
		}
	}
</script>

<style scoped lang="scss">
	.t-flex-row {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}
	.t-more {
		font-size: 28rpx;
		color: #888888;
		line-height: 40rpx;
		margin-top: 50rpx;
		margin-bottom: 85rpx;
	}
	.t-flex-row-sb {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
	}
	.t-flex-col-s {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
	}
	.content {
		background: #F6F3F3;
		min-width: 100vw;
		min-height: 100vh;
		box-sizing: border-box;
		padding-bottom: 30rpx;

		.t-body {
			width: 100%;
			padding: 0rpx 50rpx;
			background: #fff;
			box-sizing: border-box;
			border-top: 1rpx solid #D8D8D8;
		}

		.t-jf-item {
			width: 100%;
			height: 184rpx;
			box-sizing: border-box;
			padding: 40rpx 0rpx;
		}

		.t-jf-item:not(:last-child) {
			border-bottom: 1rpx solid #ddd;
		}

		.t-lt {
			align-items: flex-start;
			font-size: 30rpx;
			color: #333333;
			line-height: 42rpx;
		}

		.t-lt text:last-child {
			margin-top: 20rpx;
		}

		.t-rt {
			font-size: 30rpx;
			color: #333333;
		}

		.t-hx-color {
			color: #D41428 !important;
		}

		.t-p-color {
			color: #17ba7a !important;
		}

		.t-tips {
			margin-top: 50rpx;
			margin-bottom: 10rpx;
			font-size: 28rpx;
			color: #888888;
			line-height: 40rpx;
		}
	}
</style>