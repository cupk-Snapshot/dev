<template>
	<view class="content b-t">
		<text style="display:block;padding: 16upx 30upx 10upx;lihe-height: 1.6;color: #999;background: #f8f8f8;font-size: 24upx;">收货地址请认真填写与核对,我们将按照您所填写的地址快递产品,如因地址填写错误导致您无法收到产品,责任由您承担。</text>
		<view class="list b-b" v-for="(item, index) in addressList" :key="index" @click="checkAddress(item)">
			<view class="wrapper">
				<view class="address-box">
					<text v-if="item.default" class="tag">默认</text>
					<text class="address">{{item.area}} {{item.address}}</text>
				</view>
				<view class="u-box">
					<text class="name">{{item.name}}</text>
					<text class="mobile">{{item.mobile}}</text>
				</view>
			</view>
			<uni-icons type="compose" size="16" @click="editAddress(item)"></uni-icons>
			<text class="" @tap="editAddress(item)">编辑</text>
			<uni-icons type="trash-filled" size="16" @click="handleRemove(item.addressId)"></uni-icons>
			<text class="" @tap="handleRemove(item.id)">删除</text>
		</view>
		
		<button class="add-btn" @tap="addAddress()">新增地址</button>
	</view>
</template>

<script>
	import { getUserProfile } from '@/api/system/user'
	export default {
		data() {
			return {
				source: 0,
				addressList:[],
				user:{},
				token:''
			}
		},
		onLoad(option){
			this.source = option.source;
			this.user=uni.getStorageSync('user');
			this.token=uni.getStorageSync('SET_TOKEN');
			console.log(this.token)
			this.loadAddress();
		},
		onShow(){
			this.loadAddress();
		},
		methods: {
			loadAddress(){
				let that=this;
				uni.request({
					url:"http://localhost:9955/user/address/all/3",
					data:{
						// user_id:that.user.userId
					},
					header:{
						'Authorization':'Bearer '+that.token
					},
					methods:"GET",
					success:(res)=>{
						console.log(res);
						that.addressList=res.data.data
					}
				})
			},
			editAddress(item){
				let id=item.addressId
				uni.setStorageSync('address',item)
				uni.navigateTo({
					url: '/pages/mine/exchange/address_manage?id='+id
				})
			},
			//删除地址
			handleRemove(addressId){
				const that = this
				uni.showModal({
					title: "提示",
					content: "您确定要删除当前收货地址吗?",
					success: function (res) {
			    		if (res.confirm){
							uni.request({
									url: "",
									head:{
										token:that.token
									},
									data:{
										addressId:addressId,
										userId:that.user.uuid
									},
									success: (resp) => {
										if(resp.data.code == 200){//删除成功
										that.addressList=resp.data.
										uni.showToast({
											title: '删除成功',
											duration: 2000
										});
								        }
									},
							})
							that.loadAddress()
						}
						else if (res.cancel) {
			    			console.log('用户点击取消');
			    		}	
			    	}
			  });	
			},
			//选择地址
			checkAddress(item){
				if(this.source == 1){
					//this.$api.prePage()获取上一页实例，在App.vue定义
					this.$api.prePage().addressData = item;
					this.setDefault(item)
					uni.navigateBack()
				}
			},
			setDefault(item){
				var li =this.addressList;
				for(let i in li){
					let ite=li[i];
					ite.default=false;
				}
				for(let j in li){
					let itee=li[j];
					if(itee.id===item.id){
						itee.default=true;
					}
				}
				// this.$dataLocal("address",li);
				this.addressList=li
			},
			addAddress(){
				uni.navigateTo({
					url: `/pages/mine/exchange/address_manage`
				})
			},
			//添加或修改成功之后回调
			refreshList(data, type){
				this.addressList.unshift(data);	
				console.log(data, type);
			}
		}
	}
</script>
<style lang='scss'>
	page{
		padding-bottom: 120upx;
	}
	.content{
		position: relative;
	}
	.list{
		display: flex;
		align-items: center;
		padding: 20upx 30upx;;
		background: #fff;
		position: relative;
	}
	.wrapper{
		display: flex;
		flex-direction: column;
		flex: 1;
	}
	.address-box{
		display: flex;
		align-items: center;
		.tag{
			font-size: 24upx;
			color: #fa436a;
			margin-right: 10upx;
			background: #fffafb;
			border: 1px solid #ffb4c7;
			border-radius: 4upx;
			padding: 4upx 10upx;
			line-height: 1;
			display: inline-block;
			height:35rpx;
			width:66rpx;
		}
		.address{
			font-size: 30upx;
			color: #303133;
		}
	}
	.u-box{
		font-size: 28upx;
		color: #909399;
		margin-top: 16upx;
		.name{
			margin-right: 30upx;
		}
	}
	.icon-bianji{
		display: flex;
		align-items: center;
		height: 80upx;
		font-size: 40upx;
		color: #909399;
		padding-left: 30upx;
	}
	
	.add-btn{
		position: fixed;
		left: 30upx;
		right: 30upx;
		bottom: 16upx;
		z-index: 95;
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		font-size: 32upx;
		color: #fff;
		background-color: #fa436a;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);		
	}
</style>
