<template>
  <div class="home-container layout-pd">
    <el-row :gutter="15" class="home-card-one mb15">
      <NoticeBar
          text="🎉🎉🔥基于Vue3.x 、Typescript、Vite、Element plus等技术，适配手机、平板、pc
				的后台管理系统，仓库地址：https://github.com/cupk-Snapshot/FrontAdmin"
          leftIcon="iconfont icon-tongzhi2"
          rightIcon="ele-ArrowRight"
          background="#ecf5ff"
          color="#409eff"
      />
    </el-row>

    <el-row :gutter="15" class="home-card-one mb15">
      <el-col
          :xs="24"
          :sm="12"
          :md="12"
          :lg="6"
          :xl="6"
          v-for="(v, k) in state.homeOne"
          :key="k"
          :class="{ 'home-media home-media-lg': k > 1, 'home-media-sm': k === 1 }"
      >
        <div class="home-card-item flex">
          <div class="flex-margin flex w100" :class="` home-one-animation${k}`">
            <div class="flex-auto">
              <span class="font30">{{ v.num1 }}</span>
              <span class="ml5 font16" :style="{ color: v.color1 }"></span>
              <div class="mt10">{{ v.num3 }}</div>
            </div>
            <div class="home-card-item-icon flex" :style="{ background: `var(${v.color2})` }">
              <i class="flex-margin font32" :class="v.num4" :style="{ color: `var(${v.color3})` }"></i>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="home-card-two mb15">
      <el-col :xs="24" :sm="12" :md="12" :lg="16" :xl="16">
        <div class="home-card-item">
          <div style="height: 100%" ref="homeLineRef"></div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="home-media">
        <div class="home-card-item">
          <div style="height: 100%" ref="homePieRef"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="home">
// 引入组件
const NoticeBar = defineAsyncComponent(() => import('/@/components/noticeBar/index.vue'));
import {reactive, onMounted, ref, watch, nextTick, onActivated, markRaw, defineAsyncComponent} from 'vue';
import * as echarts from 'echarts';
import {storeToRefs} from 'pinia';
import {useThemeConfig} from '/@/stores/themeConfig';
import {useTagsViewRoutes} from '/@/stores/tagsViewRoutes';
import {chartsApi} from "/@/api/charts";

// 定义变量内容
const homeLineRef = ref();
const homePieRef = ref();
const storesTagsViewRoutes = useTagsViewRoutes();
const storesThemeConfig = useThemeConfig();
const {themeConfig} = storeToRefs(storesThemeConfig);
const {isTagsViewCurrenFull} = storeToRefs(storesTagsViewRoutes);

const state = reactive({
  global: {
    homeChartOne: null,
    homeChartTwo: null,
    homeCharThree: null,
    dispose: [null, '', undefined],
  } as any,
  homeOne: [
    {
      num1: '125,12',
      num3: '总用户数',
      num4: 'iconfont icon-gerenzhongxin',
      color1: '#FF6462',
      color2: '--next-color-primary-lighter',
      color3: '--el-color-primary',
    },
    {
      num1: '653,33',
      num3: '总举报数',
      num4: 'iconfont icon-shenqingkaiban',
      color1: '#6690F9',
      color2: '--next-color-success-lighter',
      color3: '--el-color-success',
    },
    {
      num1: '125,65',
      num3: '未处理数',
      num4: 'iconfont icon-xuanzeqi',
      color1: '#6690F9',
      color2: '--next-color-danger-lighter',
      color3: '--el-color-danger',
    },
    {
      num1: '520,43',
      num3: '总赠送积分',
      num4: 'iconfont icon-crew_feature',
      color1: '#FF6462',
      color2: '--next-color-warning-lighter',
      color3: '--el-color-warning',
    },
  ],
  data1: [],
  data2: [],
  myCharts: [] as EmptyArrayType,
  charts: {
    theme: '',
    bgColor: '',
    color: '#303133',
  },
});

// 折线图
const initLineChart = (data1: []) => {
  if (!state.global.dispose.some((b: any) => b === state.global.homeChartOne)) state.global.homeChartOne.dispose();
  state.global.homeChartOne = markRaw(echarts.init(homeLineRef.value, state.charts.theme));
  const option = {
    backgroundColor: state.charts.bgColor,
    title: {
      text: '近七日举报数',
      x: 'left',
      textStyle: {fontSize: '15', color: state.charts.color},
    },
    grid: {top: 70, right: 20, bottom: 30, left: 30},
    tooltip: {trigger: 'axis'},
    legend: {data: ['举报数'], right: 0},
    xAxis: {
      data: ['1天前', '2天前', '3天前', '4天前', '5天前', '6天前', '7天前'],
    },
    yAxis: [
      {
        type: 'value',
        name: '举报数',
        splitLine: {show: true, lineStyle: {type: 'dashed', color: '#f5f5f5'}},
      },
    ],
    series: [
      {
        name: '举报数',
        type: 'line',
        symbolSize: 6,
        symbol: 'circle',
        smooth: true,

        // data: state.data1,
        data: data1,

        lineStyle: {color: '#fe9a8b'},
        itemStyle: {color: '#fe9a8b', borderColor: '#fe9a8b'},
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {offset: 0, color: '#fe9a8bb3'},
            {offset: 1, color: '#fe9a8b03'},
          ]),
        },
        emphasis: {
          itemStyle: {
            color: {
              type: 'radial',
              x: 0.5,
              y: 0.5,
              r: 0.5,
              colorStops: [
                {offset: 0, color: '#9E87FF'},
                {offset: 0.4, color: '#9E87FF'},
                {offset: 0.5, color: '#fff'},
                {offset: 0.7, color: '#fff'},
                {offset: 0.8, color: '#fff'},
                {offset: 1, color: '#fff'},
              ],
            },
            borderColor: '#9E87FF',
            borderWidth: 2,
          },
        },
      },
    ],
  };
  state.global.homeChartOne.setOption(option);
  state.myCharts.push(state.global.homeChartOne);
};
// 饼图
const initPieChart = (data2: []) => {
  if (!state.global.dispose.some((b: any) => b === state.global.homeChartTwo)) state.global.homeChartTwo.dispose();
  state.global.homeChartTwo = markRaw(echarts.init(homePieRef.value, state.charts.theme));

  var getname = ['其他', '压线变道', '闯红灯', '违章停车', '开车打电话', '逆行'];
  var getvalue = data2;

  var data = [];
  for (var i = 0; i < getname.length; i++) {
    data.push({name: getname[i], value: getvalue[i]});
  }
  const colorList = ['#51A3FC', '#36C78B', '#FEC279', '#968AF5', '#E790E8'];
  const option = {
    backgroundColor: state.charts.bgColor,
    title: {
      text: '违法类别占比',
      x: 'left',
      textStyle: {fontSize: '14', color: state.charts.color},
    },
    tooltip: {trigger: 'item', formatter: '{b} <br/> {c}%'},
    legend: {
      top: 'bottom'
    },
    toolbox: {
      show: true,
      feature: {
        mark: {show: true},
        dataView: {show: true, readOnly: false},
        restore: {show: true},
        saveAsImage: {show: true}
      }
    },
    series: [
      {
        name: 'Nightingale Chart',
        type: 'pie',
        radius: [25, 150],
        center: ['50%', '50%'],
        roseType: 'area',
        itemStyle: {
          borderRadius: 8
        },
        data: data
      }
    ]
  };
  state.global.homeChartTwo.setOption(option);
  state.myCharts.push(state.global.homeChartTwo);
};
// 批量设置 echarts resize
const initEchartsResizeFun = () => {
  nextTick(() => {
    for (let i = 0; i < state.myCharts.length; i++) {
      setTimeout(() => {
        state.myCharts[i].resize();
      }, i * 1000);
    }
  });
};
// 批量设置 echarts resize
const initEchartsResize = () => {
  window.addEventListener('resize', initEchartsResizeFun);
};
// 页面加载时
onMounted(() => {
  chartsApi().totalUser().then(res => {
    state.homeOne[0].num1 = res.data.data.total
  })
  chartsApi().totalReport().then(res => {
    state.homeOne[1].num1 = res.data.data.total
  })
  chartsApi().totalAccept().then(res => {
    state.homeOne[2].num1 = res.data.data.total
  })
  chartsApi().totalPoints().then(res => {
    state.homeOne[3].num1 = res.data.data.total
  })
  chartsApi().reportNum().then(res => {
    initLineChart(res.data.data)
  })
  chartsApi().typePercent().then(res => {
    initPieChart(res.data.data);
    // state.data2 = res.data.data
  })
  initEchartsResize();
});
// 由于页面缓存原因，keep-alive
onActivated(() => {
  initEchartsResizeFun();
});
// 监听 pinia 中的 tagsview 开启全屏变化，重新 resize 图表，防止不出现/大小不变等
watch(
    () => isTagsViewCurrenFull.value,
    () => {
      initEchartsResizeFun();
    }
);
// 监听 pinia 中是否开启深色主题
watch(
    () => themeConfig.value.isIsDark,
    (isIsDark) => {
      nextTick(() => {
        state.charts.theme = isIsDark ? 'dark' : '';
        state.charts.bgColor = isIsDark ? 'transparent' : '';
        state.charts.color = isIsDark ? '#dadada' : '#303133';
        setTimeout(() => {
          // initLineChart();
        }, 500);
        setTimeout(() => {
          // initPieChart();
        }, 700);
      });
    },
    {
      deep: true,
      immediate: true,
    }
);
</script>

<style scoped lang="scss">
$homeNavLengh: 8;
.home-container {
  overflow: hidden;

  .home-card-one,
  .home-card-two,
  .home-card-three {
    .home-card-item {
      width: 100%;
      height: 150px;
      border-radius: 4px;
      transition: all ease 0.3s;
      padding: 20px;
      overflow: hidden;
      background: var(--el-color-white);
      color: var(--el-text-color-primary);
      border: 1px solid var(--next-border-color-light);

      &:hover {
        box-shadow: 0 2px 12px var(--next-color-dark-hover);
        transition: all ease 0.3s;
      }

      &-icon {
        width: 70px;
        height: 70px;
        border-radius: 100%;
        flex-shrink: 1;

        i {
          color: var(--el-text-color-placeholder);
        }
      }

      &-title {
        font-size: 15px;
        font-weight: bold;
        height: 30px;
      }
    }
  }

  .home-card-one {
    @for $i from 0 through 3 {
      .home-one-animation#{$i} {
        opacity: 0;
        animation-name: error-num;
        animation-duration: 0.5s;
        animation-fill-mode: forwards;
        animation-delay: calc($i/4) + s;
      }
    }
  }

  .home-card-two,
  .home-card-three {
    .home-card-item {
      height: 500px;
      width: 100%;
      overflow: hidden;

      .home-monitor {
        height: 100%;

        .flex-warp-item {
          width: 25%;
          height: 111px;
          display: flex;

          .flex-warp-item-box {
            margin: auto;
            text-align: center;
            color: var(--el-text-color-primary);
            display: flex;
            border-radius: 5px;
            background: var(--next-bg-color);
            cursor: pointer;
            transition: all 0.3s ease;

            &:hover {
              background: var(--el-color-primary-light-9);
              transition: all 0.3s ease;
            }
          }

          @for $i from 0 through $homeNavLengh {
            .home-animation#{$i} {
              opacity: 0;
              animation-name: error-num;
              animation-duration: 0.5s;
              animation-fill-mode: forwards;
              animation-delay: calc($i/10) + s;
            }
          }
        }
      }
    }
  }
}
</style>
