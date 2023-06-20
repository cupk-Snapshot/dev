<template>
	<view class="content">
		  <mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault"
		 @onConfirm="onConfirm"></mpvue-city-picker>
		
		<view class="row b-b">
			<text class="tit">联系人</text>
			<input class="input" type="text" v-model="addressData.name" placeholder="收货人姓名" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">手机号</text>
			<input class="input" type="number" v-model="addressData.phoneNum" placeholder="收货人手机号码" placeholder-class="placeholder" />
		</view>
		<view class="row b-b" @click="showMulLinkageThreePicker">
			<text class="tit">地区</text>
			<input class="input" type="text"  v-model="addressData.area" disabled="true"   placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">地址</text>
			<input class="input" type="text" v-model="addressData.address" placeholder="详细地址" placeholder-class="placeholder" />
			<text class="icon-shouhuodizhi" @click="chooseLocation">选择位置</text>
		</view>
	
		
		<view class="row default-row">
			<text class="tit">设为默认收货地址</text>
			<switch :checked="addressData.is_default" color="#fa436a" @change="switchChange" />
		</view>
		<button class="add-btn" @click="confirm">提交</button>
	</view>
</template>

<script>
	import mpvueCityPicker from '@/components/mpvue-citypicker/mpvueCityPicker.vue'
	import { getUserProfile } from '@/api/system/user'
	
	export default {
		components: {
		    mpvueCityPicker,
		},
		data() {
			return {
			editCityPicker:true,
			cityPickerValueDefault: [0, 0, 1],
			themeColor: '#007AFF',
			addressData: {
				name: '',
				phoneNum: '',
				address: '',
				area: '',
				is_default: false
				},
			addressList:[],
			user:{}
			}
		},
		//页面加载函数
		onLoad(option){
			let that=this
			that.user=uni.getStorageSync('user');
			let title = '新增收货地址';
			if(!!option.id){
				title = '编辑收货地址';
				that.addressData=uni.getStorageSync('address')
				let def=0;
				if(!that.addressData.is_default){
					 def=0;
				} 
				else {
					 def=1;
					}
				uni.request({
					url:"http://localhost:9955/user/address/update",
					method:"POST",
					header:{
						'Authorization':'Bearer '+that.token
					},
					data:{
						addressId:option.id,
						userId:that.user.user_id,
						name: that.addressData.name,
						phoneNum: that.addressData.phoneNum,
						area: that.addressData.area,
						address: that.addressData.address,
						is_default:def
					},
					success:(res)=>{
						console.log(res.data);
						that.addressData=res.data;
					}
				// this.locateAddress(option.id)
				})
			}
			uni.setNavigationBarTitle({ 
				title
			})
		},
		methods: {
			getUser() {
			  getUserProfile().then(response => {
			    this.user = response.data
			  })
			},
		   onConfirm(e) {
				console.log(e);
				 this.addressData.area=e.label;
				 console.log(this.addressData)
			},
			showMulLinkageThreePicker() {		
			    this.$refs.mpvueCityPicker.show()
			},
			//选择默认开关
			switchChange(e){
				var li =this.addressList;
				console.log(true===e.detail.value)
				if(true===e.detail.value){
					for(let i in li){
						let ite=li[i];
						ite.default=false;
					}
				}
				console.log(li);
				this.addressData.default = e.detail.value;
			},
			//地图选择地址
			chooseLocation(){
				let that=this
				uni.chooseLocation({
					success: (data)=> {
						that.addressData.address = data.address;
					}
				})
			},
			//提交
			confirm(){
				//let li=this.addressList;
				let data = this.addressData;
				if(!data.name){
					this.$toast('请填写收货人姓名');
					return;
				}
				if(!/(^1[3|4|5|7|8|9][0-9]{9}$)/.test(data.mobile)){
					this.$toast('请输入正确的手机号码');
					return;
				}
				
				if(!data.area){
					this.$toast('请选择所在区域');
					return;
				}
				if(!data.address){
					this.$toast('请在地图选择所在位置');
					return;
				}
				if(!data.default) data.default=int(0);
				else data.default=1;
				// if(typeof data.id!="undefined"){
				// 	for(let i in li){
				// 		var ite=li[i];
				// 		if(parseFloat(ite.id)===parseFloat(data.id)){
				// 			ite=data;
				// 		}
				// 	}
				// }else{
				// 	data.id=li.length;
				// 	li.push(data);
				// }		 
				// this.$dataLocal("address",li);
				let that=this
				const token=uni.getStorageSync('SET_TOKEN')
				uni.request({
					url:"http://localhost:9955/user/address/add",
					method:"POST",
					header:{
						'Authorization':'Bearer '+token,
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					data:{
						user_id:that.user.userId,
						name:data.name,
						phone_num:data.mobile,
						area:data.area,
						address:data.address,
						is_default:data.default
					},
					success:(res)=>{
						console.log(res);
					}
				})
				setTimeout(()=>{
					uni.navigateBack()
				}, 800)
			},
		}
	}
</script>

<style lang="scss">
	page{
		background: #f1f1f1;
		padding-top: 16upx;
	}

	.row{
		display: flex;
		align-items: center;
		position: relative;
		padding:0 30upx;
		height: 110upx;
		background: #fff;
		
		.tit{
			flex-shrink: 0;
			width: 120upx;
			font-size: 30upx;
			color: #303133;
		}
		.input{
			flex: 1;
			font-size: 30upx;
			color: #303133;
		}
		.icon-shouhuodizhi{
			font-size: 36upx;
			color: #909399;
		}
	}
	.default-row{
		margin-top: 16upx;
		.tit{
			flex: 1;
		}
		switch{
			transform: translateX(16upx) scale(.9);
		}
	}
	.add-btn{
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		margin: 60upx auto;
		font-size: 32upx;
		color: #fff;
		background-color: #fa436a;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}
</style>
