<template>
	<view class="home">
		<view class="card-bottom">
			<!--
			<y-Refresh ref="mixPulldownRefresh" :top="100" @refresh="onPulldownReresh">
				-->
				<view class="scroll-wrapper">
					<view class="margin-bottom" v-for="(item, index) in cardList" :key="index">
						<y-DiaryItem :obj="item" />
					</view>
					<y-LoadMore :status="loadMoreStatus"></y-LoadMore>
					
				</view>
				<!--
			</y-Refresh>-->
			
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			startNum: 0,
			cardList: [],
			// cardList: [
			// 	{
			// 		id: 0,
			// 		createTime: '2022-11-11',
			// 		avatarUrl: 'https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/avatar.png',
			// 		nickName: '用户名',
			// 		title: '生活就像夏天的柑橘树，挂着青皮的果，苦是一定的，甜也有。        --《少年的你，如此美丽》',
			// 		follow: false,
			// 		isLike: false,
			// 		likeNum: '28',
			// 		commentNum: '1',
			// 		imgList: [{
			// 			"url": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/tree.jpg"
			// 		}]
			// 	}, {
			// 		id: 1,
			// 		"createTime": "3天前",
			// 		"avatarUrl": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/avatar.png",
			// 		"nickName": "小黄鸭",
			// 		"title": "对你从山有木兮木有枝的满心欢喜;到沅有芷兮澧有兰的小心翼翼;经过玲珑骰子安红豆的世俗俗气;最后陷入长相思兮长相忆的无穷思念",
			// 		follow: false,
			// 		isLike: false,
			// 		likeNum: '24',
			// 		commentNum: '0',
			// 		"imgList": [{
			// 			"url": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/love.jpg"
			// 		}, {
			// 			url: 'https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/lonely.jpg'
			// 		}]
			// 	}, {
			// 		id: 2,
			// 		"createTime": "7-10",
			// 		"avatarUrl": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/avatar.png",
			// 		"nickName": "小黄鸭",
			// 		"title": "我说你是人间的四月天； 笑音点亮了四面风； 轻灵，在春的光艳中交舞著变。 你是四月早天里的云烟， 黄昏吹着风的软， 星子在无意中闪，细雨点洒在花前。    --民国四大美人之首林徽因《你是人间四月天》",
			// 		follow: false,
			// 		isLike: false,
			// 		likeNum: '24',
			// 		commentNum: '0',
			// 		"imgList": [{
			// 			"url": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/img_flower_1.jpg"
			// 		},{
			// 			"url": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/img_flower_2.jpg"
			// 		},{
			// 			"url": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/img_flower_3.jpg"
			// 		},{
			// 			"url": "https://6d61-matchbox-79a395-1302390714.tcb.qcloud.la/matchbox/img_flower_4.jpg"
			// 		}]
			// 	}
			// ],
			loadMoreStatus: 1, //0加载前，1加载中，2没有更多了
		};
	},
	onLoad() {
		let token = uni.getStorageSync('SET_TOKEN')
		let that = this;
		uni.request({
			url:'http://localhost:9955/user/index',
			method:"GET",
			header:{
				Authorization: "Bearer " + token
			},
			success(res) {
				console.log(res)
				that.cardList=res.data.data
			}
		})
		
	},
	//下拉刷新
	onPulldownReresh() {
		let that = this;
		that.loadData('refresh');
	},
	methods: {
		toDetails(id){
			uni.navigateTo({
				url: '/pages/comment/comments'
			})
		},
		handleTab(index) {
			let that = this;
			that.activeTab = index;
		},
		//点击右下角tab按钮
		handleFab(e) {
			let index = e.index;
			switch (index) {
				case 0:
					uni.navigateTo({
						url: '../push/push'
					});
					break;
				case 1:
					console.log(1);
					break;
			}
		}
	}
};
</script>

<style lang="less" scoped>
.home {
	padding-top: 30rpx;

	.top-barrage {
		width: 100%;
		height: 320rpx;
		overflow: hidden;
	}

	.card-bottom {
		width: 100%;

		.top-tab {
			display: flex;
			height: 120rpx;
			position: fixed;
			top: 0;
			width: 100%;
			z-index: 100;
			background-color: #ffffff;

			.tab-item {
				flex: 1;
				color: #999;
				border-bottom: 4rpx solid #ececec;
			}

			.active {
				color: var(--mainColor);
				border-bottom: 4rpx solid var(--mainColor);
			}
		}
	}
}
</style>
