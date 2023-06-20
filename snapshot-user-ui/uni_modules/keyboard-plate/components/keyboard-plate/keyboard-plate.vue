<template>
  <!-- 车牌组件 -->
  <view class="plate-content">
    <view class="plate-popup" v-show="plateShow">
      <view class="plate-close" @click="close">{{ plateNumber.length > 6 ? '确定' : '关闭' }}</view>
      <view class="numbox" v-if="isShow">
        <block v-for="(item, index) in 7" :key="index">
          <input v-model="plateNumber[index]" disabled class="numInput" maxlength="1" type="text" value="" @click="carInputClick(index)"/>
        </block>
        <input v-model="plateNumber[7]" disabled class="newnumInput" maxlength="1" type="text" value="" placeholder="新能源" placeholder-class="newpl" @click="carInputClick(7)" />
      </view>
      <!-- 汉字键盘 -->
      <view v-show="!plateNumber" :class="safeArea ? 'isPhoneX' : ''">
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" v-for="(item, itemIndex) in keyVehicle1" :key="itemIndex" @click="plateHead(item)">{{ item }}</view>
        </view>
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" v-for="(item, itemIndex) in keyVehicle2" :key="itemIndex" @click="plateHead(item)">{{ item }}</view>
        </view>
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" v-for="(item, itemIndex) in keyVehicle3" :key="itemIndex" @click="plateHead(item)">{{ item }}</view>
        </view>
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" v-for="(item, itemIndex) in keyVehicle4" :key="itemIndex" @click="plateHead(item)">{{ item }}</view>
          <!-- 删除 -->
          <view class="plate-popup-item-list delImg" @click="plateDel">
            <image :src="src" />
          </view>
        </view>
      </view>
      <!-- 数字字母键盘 -->
      <view v-show="plateNumber" :class="safeArea ? 'isPhoneX' : '' ">
        <view class="plate-popup-item">
          <!-- 数字选择 -->
          <view class="plate-popup-item-list" :class="plateNumber.length < 2 || isInputZh ? 'plate-popup-item-disabled' : ''" v-for="(item, itemIndex) in keyNumber" :key="itemIndex" @click="plateNum(item)">{{ item }}</view>
        </view>
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" :class="!isInputZh ? '' : 'plate-popup-item-disabled'" v-for="(item, itemIndex) in keyEnInput1" :key="itemIndex" @click="plateInput(item)"> <view :class="item =='I' || item == 'O' ? 'plate-popup-item-disabled' : ''">{{ item }}</view></view>
        </view>
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" :class="!isInputZh ? '' : 'plate-popup-item-disabled'" v-for="(item, itemIndex) in keyEnInput2" :key="itemIndex" @click="plateInput(item)">{{ item }}</view>
        </view>
        <view class="plate-popup-item">
          <view class="plate-popup-item-list" :class="!isInputZh ? '' : 'plate-popup-item-disabled'" v-for="(item, itemIndex) in keyEnInput3" :key="itemIndex" @click="plateInput(item)">{{ item }}</view>
        </view>
        <view class="plate-popup-item">
          <!-- 车牌最后一位-->
          <view class="plate-popup-item-list del2" :class="(plateNumber.length === 6 || plateNumber.length === 7) && !isInputZh ? '' : 'plate-popup-item-disabled'" v-for="(item, itemIndex) in keyEnInput4" :key="itemIndex" @click="plateLast(item)">{{ item }}</view>
          <!-- 删除 -->
          <view class="plate-popup-item-list delImg" @click="plateDel">
            <image :src="src" />
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
/*
 * @change By:CkCode
 * @logs:
 * 1:将键盘和输入框整合到了一起
 * 2:适配苹果X 
 * 3:增加校验规则
 * 4:增加了错误提示
 * @Last Modified time: 2022-10-17
 */
  import plateDel from './KeyboardDelete.png'

  export default {
    data() {
      return {
        plateNumber: '', //输入的车牌
        plateShow: false, //车牌选择是否打开
        keyVehicle1: ['京', '沪', '粤', '津', '冀', '豫', '云', '辽', '黑', '湘'],
        keyVehicle2: ['皖', '鲁', '新', '苏', '浙', '赣', '鄂', '桂', '甘'],
        keyVehicle3: ['晋', '蒙', '陕', '吉', '闽', '贵', '渝', '川'],
        keyVehicle4: ['青', '藏', '琼', '宁', '使'],
        keyNumber: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'],
        keyEnInput1: ['Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'],
        keyEnInput2: ['A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'],
        keyEnInput3: ['Z', 'X', 'C', 'V', 'B', 'N', 'M'],
        keyEnInput4: ['港', '澳', '学', '警', '临','挂'],
        isInputZh: false, //是否输入了特殊字符
        src: '',
      };
    },
    props: {
      //是否开启输入框
      isShow:{
        type:Boolean,
        default:true
      },
       //是否适配安全线（iphone X）
			safeArea: {
				type:Boolean,
        default:false
			},
		},
    mounted() {
      this.src = plateDel;
    },
    onShow() {},
    watch:{
      plateNumber(num){
        this.$emit('update:plateNum', num);
      }
    },
    methods: {
      /**
       * @desc 初始化
       */
      init() {
        this.plateNumber = '';
      },
      /**
       * @desc 打开下拉栏
       */
      open() {
        this.plateShow = true;
      },
      /**
       * @desc 关闭弹出的车牌
       */
      close() {
		
        if (this.plateNumber && this.plateNumber.length < 7 ) {
          return uni.showModal({
            title: '温馨提示',
            content: '关闭将会清空您已输入的内容',
            success: res => {
              if (res.confirm) {
                this.init();
                this.plateShow = false;
                //传值到父组件
                this.$emit('change', {
                  value:this.plateNumber
                })
              } else if (res.cancel) {
              }
            },
          });
        }
        if(this.isVehicleNumber(this.plateNumber)){
          this.plateShow = false;
          //传值到父组件,写成对象，方便后期改造
          this.$emit('change', {
            value:this.plateNumber
          })
        }else if(this.plateNumber){
        uni.showToast({
          title: '车牌号输入错误,请重新输入',
          duration: 2000,icon:'none'
        });
          
        }else{
          this.plateShow = false;
        }
      },
      /**
       * @desc 车牌号验证方法
       */
      isVehicleNumber(vehicleNumber) {
		let plateNumMatch=/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))/
		//let plateNumMatch = /^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})/
        if (vehicleNumber.length == 7||vehicleNumber.length == 8) {
          return plateNumMatch.test(vehicleNumber);
        } else{
          return false;
        }
      },
      /**
       * @desc 汉字选择
       */
      plateHead(plate) {
        if (!this.plateNumber) {
          this.plateNumber = this.plateNumber + plate;
        }
      },
      /**
       * @desc 字母选择
       */
      plateInput(plate) {
        //判断是否输入了字母
        if (this.isInputZh || plate =='I' || plate == 'O') {
          return;
        }
        if (this.plateNumber.length < 8) {
          this.plateNumber = this.plateNumber + plate;
        }
      },
      /**
       * @desc 数字选择
       */
      plateNum(plate) {
        //判断是否输入了字母
        if (this.isInputZh) {
          return;
        }
        if (this.plateNumber.length >= 2 && this.plateNumber.length < 8) {
          this.plateNumber = this.plateNumber + plate;
        }
      },
      /**
       * @desc 最后一位选择
       */
      plateLast(plate) {
        //判断是否输入了字母
        if (this.isInputZh) {
          return;
        }
        if (this.plateNumber.length === 6 || this.plateNumber.length === 7) {
          this.plateNumber = this.plateNumber + plate;
          this.isInputZh = true;
        }
      },
      /**
       * @desc 删除
       */
      plateDel() {
        if (this.plateNumber) {
          this.plateNumber = this.plateNumber.substring(0, this.plateNumber.length - 1);
        }

        if (this.plateNumber.length === 6 || this.plateNumber.length === 7) {
          this.isInputZh = false;
        }
      },
    },
  };
</script>

<style lang="scss">
  /*宽度转换vw*/
  @function vww($number) {
    @return ($number / 375) * 750 + rpx;
  }
  .isPhoneX {
    padding-bottom: env(safe-area-inset-bottom);
  }
  .numbox {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #fff;
    padding: 0 24rpx 32rpx;

    .numInput {
      width: 76rpx;
      height: 96rpx;
      line-height: 92rpx;
      text-align: center;
      background: #ffffff;
      border-radius: 4rpx;
      border: 2rpx solid #e6e6e6;
      font-size: 44rpx;
      font-family: SourceHanSansCN-Regular, SourceHanSansCN;
      font-weight: 400;
      color: #333333;
    }

    .numInputon {
      position: relative;
      border: 2rpx solid #377cff;

      &::after {
        width: 56rpx;
        height: 4rpx;
        background: #377cff;
        border-radius: 2rpx;
        content: '';
        position: absolute;
        bottom: 6rpx;
        left: 12rpx;
      }
    }

    .newnumInput {
      width: 76rpx;
      height: 96rpx;
      line-height: 92rpx;
      text-align: center;
      border-radius: 4rpx;
      font-size: 44rpx;
      font-family: SourceHanSansCN-Regular, SourceHanSansCN;
      font-weight: 400;
      background: #e7faf1;
      border: 2rpx dashed #0fd07e;
      color: #0fd07e;
    }

    .newnumInputon {
      position: relative;
      border: 2rpx solid #0fd07e;

      &::after {
        width: 56rpx;
        height: 4rpx;
        background: #0fd07e;
        border-radius: 2rpx;
        content: '';
        position: absolute;
        bottom: 6rpx;
        left: 12rpx;
      }
    }

    .newpl {
      font-size: 24rpx;
      font-family: SourceHanSansCN-Regular, SourceHanSansCN;
      font-weight: 400;
      color: #00bf6e;
      line-height: 36rpx;
    }
  }

  .plate-popup {
    position: fixed;
    z-index: 999;
    background-color: #e3e2e7;
    -webkit-box-shadow: 0 0 30upx rgba(0, 0, 0, 0.1);
    box-shadow: 0 0 30upx rgba(0, 0, 0, 0.1);
    bottom: 0;
    width: 100%;
    overflow: hidden;
    text-align: center;
    $height: vww(35);

    //关闭
    .plate-close {
      width: calc(100% - vww(10));
      background: #ffffff;
      line-height: vww(30);
      font-size: vww(12);
      text-align: right;
      padding-right: vww(10);
      color: #00a7ea;
    }

    //键盘主体内容-单行
    .plate-popup-item {
      margin: 0 auto;
      overflow: hidden;
      display: inline-block;
      display: table;

      &:last-child {
        margin-bottom: 2vw;
      }
    }

    //键盘主体内容-单个
    .plate-popup-item-list {
      float: left;
      font-size: vww(14);
      width: 8vw;
      margin: 0 1vw;
      margin-top: 2vw;
      height: $height;
      line-height: $height;
      background: #ffffff;
      border-radius: 5px;
      color: #4a4a4a;
      overflow: hidden;

      //删除键
      &.del {
        width: 14vw;
      }
      &.del2 {
        width: 12vw;
      }
      &.delImg {
        width: 14vw;
        display: flex;
        //删除键图片
        image {
          width: 48rpx;
          height: 48rpx;
          margin: 0 auto;
          display: block;
          vertical-align: middle;
          align-self: center;
        }
      }

      //特殊字符（港澳学）
      &.special {
        background: #f0f2f4;
      }
    }
    .plate-popup-item-disabled {
      background: #f0f2f4;
    }
  }
</style>
