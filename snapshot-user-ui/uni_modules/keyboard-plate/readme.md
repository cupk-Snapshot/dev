# keyboard-plate是一款适用于uniapp车牌号输入的组件
# ### 本组件目前兼容微信小程序、H5
### 本组件自带车牌号输入框，支持新能源，适配各种屏幕，适配苹果x系列，支持车牌号检验
### 使用方式	
``` html
<button @click="carInputClick">打开车牌输入</button>
<keyboard-plate ref="plateNumber" :plateNum.sync='plateNum' @change="getPlateNum"  isShow></keyboard-plate>
```
``` javascript
export default {
	data() {
		return {
			plateNum:''
		};
	},
	watch:{
		plateNum(e){
			console.log(e)
		}
	},
	methods: {
		//获取车牌
		getPlateNum(e){
			console.log('车牌号是：'+ e)
		},
		// 打开车牌选择器
		carInputClick() {
			this.$refs.plateNumber.open();
		},
	}
}
```

### 属性说明
| 名称                         | 类型            | 默认值                | 描述             |
| ----------------------------|--------------- | ---------------------- | ---------------|
| isShow                   | Boolean        | true          | 是否开启输入框
| safeArea                    | Boolean        | false          | 是否适配安全线（iphone X）



