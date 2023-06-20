<template>
	<view :class="['card-item',radius ? 'radius' : '']">
		<view class="item-head">
			<view class="left-info">
				<view class="img-wrap flex-center">
					<image :src="item.avatarUrl" mode="widthFix" class="avatar"></image>
				</view>
				<view class="head-name">{{ item.nickName }}</view>
			</view>
			<text class="color-nine">{{item.createTime}}</text>
		</view>
		<view class="content" @tap="toDetails(item.id)">
			<view class="text-content">{{ item.wfAddress }}</view>
			<view class="text-content">{{ item.wfDesc }}</view>
			<view class="text-content">{{ item.wfType }}</view>
			<view class="img-wrap padding-bottom-lg">
				<view class="img-box">
					<image v-if="item.imgList[0]" :src="item.imgList[0].url1" mode="aspectFill" class="img"></image>
					<image v-if="item.imgList[1]" :src="item.imgList[1].url2" mode="aspectFill" class="img"></image>
					<image v-if="item.imgList[2]" :src="item.imgList[2].url3" mode="aspectFill" class="img"></image>
				</view>
			</view>
			
<!-- 			<view class="img-wrap padding-bottom-lg" v-if="item.imgList.length == 1">
				<view class="img-box">
					<image v-for="(child, idx) in item.imgList" :key="idx" :src="child.url" mode="widthFix" class="img" @tap.stop @tap="ViewImage(idx, item.imgList)"></image>
				</view>
			</view>
			<view class="img-list padding-bottom-lg" v-if="item.imgList.length > 1">
				<view class="img-box" v-for="(child, idx) in item.imgList" :key="idx">
					<image :src="child.url" mode="widthFix" class="img" @tap.stop @tap="ViewImage(idx, item.imgList)"></image>
				</view>
			</view>
			<view class="bottom-btn padding-bottom-sm">
				<view class="btn-item flex-center">
					<image class="img" src="/static/images/diary/img_say.png" mode="aspectFit"></image>
					<text>{{ item.commentNum }}</text>
				</view>
				<view class="btn-item flex-center" @tap.stop @tap="handleLike(item.id, item.isLike, item.likeNum)">
					<image class="img" v-if="!item.isLike" src="/static/images/diary/img_zan.png" mode="aspectFit"></image>
					<image class="img" v-else src="/static/images/diary/img_zan_1.png" mode="aspectFit"></image>
					<text>{{ item.likeNum }}</text>
				</view>
			</view>
			
			 -->
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			obj: {
				type: Object
			},
			radius:{
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				item: this.obj,
				showPop: false,
				commentList: []
			};
		},
		watch: {
			obj(val) {
				this.list = val;
			}
		},
		methods: {
			handleFollow(id) {
				let that = this;
				that.item.follow = !that.item.follow;
			},
			toDetails(id) {
				uni.navigateTo({
					url: '/pages/comment/comments'
				});
			},
			handleLike(id, isLike, likeNum) {
				let that = this;
				if (that.item.isLike) {
					that.item.likeNum--;
				} else {
					that.item.likeNum++;
				}
				that.item.isLike = !that.item.isLike;
			},
			ViewImage(index, arr) {
				let list = [];
				for (let i = 0; i < arr.length; i++) {
					list.push(arr[i].url);
				}
				uni.previewImage({
					current: index,
					urls: list
				});
			}
		}
	};
</script>

<style lang="less" scoped>
	.radius{
		border-radius: 16px;
	}
	.card-item {
		padding: 5px 20px 0px 20px;
		background-color: #ffffff;
		box-shadow: 0 20px 40px 0 rgba(0, 0, 0, 0.1);

		.item-head {
			display: flex;
			align-items: center;
			width: 100%;
			justify-content: space-between;

			.left-info {
				display: flex;
				align-items: center;

				.img-wrap {
					width: 40px;
					height: 40px;
					overflow: hidden;
					border-radius: 10%;

					.avatar {
						width: 50%;
						height: 40px;
					}
				}

				.head-name {
					padding-left: 20px;
					color: #666666;
					font-size: 16px;
				}
			}

			.follow-btn {
				width: 60px;
				height: 30px;
				border: 1px solid var(--mainColor);
				color: var(--mainColor);
				border-radius: 10px;
			}

			.followed {
				color: #fff;
				background: var(--activeColor);
				border: 1px solid var(--activeColor);
			}
		}

		.content {
			.text-content {
				justify-content: space-between;
				color: var(--mainColor);
				font-size: 16px;
				text-align: justify;
			}

				.img-box {
					
					border-radius: 4px;
					background-color: #ffffff;
					
					.img {
						margin-right: 10px;
						height: 100px;
						width: 150px;
					}
				}

			.img-wrap {
				.img-box {
					width: 100%;
					max-height: 200px;
					overflow: hidden;
					display: flex;
					align-items: center;
				}
			}
		}
	}

	.cont {
		--color: var(--activeColor);
		--count: 1;
	}
	
	
	
	.flex-center {
		display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.mainColor {
		color: var(--mainColor);
	}
	
	.aColor {
		color: var(--activeColor);
	}
	
	.color-nine{
		color: #999999;
	}
	
	.main-btn {
		border-radius: 40px;
		display: flex;
		align-items: center;
		justify-content: center;
		color: var(--mainColor);
		border: 1px solid var(--mainColor);
		padding: 10px 40px;
	}
	
	.active-btn {
		color: #FFFFFF !important;
		background-color: var(--activeColor) !important;
		border: 1upx solid var(--activeColor) !important;
	}
	
	/* 点赞和评论 */
	.bottom-btn {
		display: flex;
	
		.btn-item {
			flex: 1;
			color: var(--mainColor);
	
			.img {
				width: 20px;
				height: 20px;
				margin-right: 20px;
			}
		}
	}
</style>
