<template>
	<view>
		<view class="inputView">
			<text class="leftTitle">{{leftTitle}}</text>
			<input class="rightInput" :name="name" v-model="value" :placeholder="placeholder" />
		</view>

		<view class="line"></view>
	</view>
</template>

<script>
	export default {
		name: "ccPickerView",
		props: {
			// 左边标题栏
			leftTitle: {
				type: String,
				default: ""
			},
			// 默认输入占位符
			placeholder: {
				type: String,
				default: ""
			},
			// 输入框name
			name: {
				type: String,
				default: ""
			},
			// 选择输入框值
			value: {
				type: String,
				default: ""
			},

		},
		data() {
			return {

			}
		},
		methods: {


		}

	}
</script>

<style scoped>
	.inputView {
		flex-direction: row;
		display: flex;
		height: 50px;
		align-items: center;
		width: 100%;
	}





	.leftTitle {
		margin-left: 20px;
		width: 70px;
		font-size: 28rpx;
		color: #333333;
	}



	.rightInput {
		width: calc(100vw - 109px);
		font-size: 26rpx;
		height: 50px;
	}




	.line {
		width: 90%;
		height: 2rpx;
		margin-left: -2rpx;
		background-color: #f8f8f8;
		margin-left: 5%;

	}
</style>