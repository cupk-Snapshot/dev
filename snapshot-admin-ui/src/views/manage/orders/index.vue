<template>
  <div class="table-demo-container layout-padding">
    <div class="table-demo-padding layout-padding-view layout-padding-auto">
      <TableSearch :search="state.tableData.search" @search="onSearch"/>
      <Table
          ref="tableRef"
          v-bind="state.tableData"
          class="table-demo"
          @delRow="onTableDelRow"
          @pageChange="onTablePageChange"
          @sortHeader="onSortHeader"
      />
    </div>
  </div>
</template>

<script setup lang="ts" name="makeTableDemo">
import {defineAsyncComponent, reactive, ref, onMounted} from 'vue';
import {ElMessage} from 'element-plus';
import {orderApi} from "/@/api/manage/order"
// 引入组件
const Table = defineAsyncComponent(() => import('/@/views/manage/orders/table.vue'));
const TableSearch = defineAsyncComponent(() => import('/@/views/review/search.vue'));

// 定义变量内容
const tableRef = ref<RefType>();
const state = reactive<TableDemoState>({
  tableData: {
    // 列表数据（必传）
    data: [],
    // 表头内容（必传，注意格式）
    header: [
      { key: 'orderId', colWidth: '', title: '订单号', type: 'text', isCheck: true },
      { key: 'title', colWidth: '', title: '商品描述', type: 'text', isCheck: true },
      { key: 'picUrl', colWidth: '', width: '80', height: '80', title: '商品图片', type: 'image', isCheck: true },
      { key: 'point', colWidth: '', title: '商品积分', type: 'text', isCheck: true },
      { key: 'name', colWidth: '', title: '兑换用户', type: 'text', isCheck: true },
      { key: 'name', colWidth: '', title: '收货人', type: 'text', isCheck: true },
      { key: 'phoneNum', colWidth: '', title: '收货号码', type: 'text', isCheck: true },
      { key: 'area', colWidth: '', title: '收货区域', type: 'text', isCheck: true },
      { key: 'address', colWidth: '', title: '收货地址', type: 'text', isCheck: true },
      { key: 'createTime', colWidth: '', title: '下单时间', type: 'text', isCheck: true }
    ],
    // 配置项（必传）
    config: {
      total: 0, // 列表总数
      loading: true, // loading 加载
      isBorder: false, // 是否显示表格边框
      isSerialNo: true, // 是否显示表格序号
      isSelection: true, // 是否显示表格多选
      isOperate: true, // 是否显示表格操作栏
    },
    // 搜索表单，动态生成（传空数组时，将不显示搜索，注意格式）
    search: [
      {label: '用户ID', prop: 'uid', placeholder: '请输入要搜索的信息', required: false, type: 'input'},
    ],
    // 搜索参数（不用传，用于分页、搜索时传给后台的值，`getTableData` 中使用）
    param: {
      pageNum: 1,
      pageSize: 10,
    },
    // 打印标题
    printName: 'vueNextAdmin 表格打印演示',
  },
});

// 初始化列表数据
const getTableData = () => {
  state.tableData.config.loading = true;
  state.tableData.data = [];

  orderApi().all(state.tableData.param.pageNum, state.tableData.param.pageSize).then(res => {
    state.tableData.config.total = res.data.data.total
    state.tableData.data = res.data.data.list
  })

  // for (let i = 0; i < 20; i++) {
  //   state.tableData.data.push({
  //     order_id: `123456789${i + 1}`,
  //     title: (i % 2) == 0 ? `妙洁 清洁洗衣洗碗家务做饭橡胶手套` : '立白椰油精华洗衣皂',
  //     pic_url: (i % 2) == 0 ? 'https://keesuishoupai.ylkee.top/uploads/20220515/b2bf12c2db5e16f8ffa389521dc231dd.jpg' : 'https://keesuishoupai.ylkee.top/uploads/20220515/3ea9a0d32db2b411761b5881e89e639a.jpg',
  //     point: 5,
  //     username: 'ad',
  //     name: (i % 2) == 0 ? 'zyg' : 'zdy',
  //     phone_num: '18187625432',
  //     area: '北京市-北京市-海淀区',
  //     address: '中国石油大学（北京）',
  //     creat_time: '2023-6-18 16:23:23'
  //   });
  // }
  // 数据总数（模拟，真实从接口取）
  // state.tableData.config.total = state.tableData.data.length;
  setTimeout(() => {
    state.tableData.config.loading = false;
  }, 500);
};
// 搜索点击时表单回调
const onSearch = (data: EmptyObjectType) => {
  state.tableData.param = Object.assign({}, state.tableData.param, {...data});
  tableRef.value.pageReset();
};
// 删除当前项回调
const onTableDelRow = (row: EmptyObjectType) => {
  ElMessage.success(`删除${row.name}成功！`);
  getTableData();
};
// 分页改变时回调
const onTablePageChange = (page: TableDemoPageType) => {
  state.tableData.param.pageNum = page.pageNum;
  state.tableData.param.pageSize = page.pageSize;
  getTableData();
};
// 拖动显示列排序回调
const onSortHeader = (data: TableHeaderType[]) => {
  state.tableData.header = data;
};
// 页面加载时
onMounted(() => {
  getTableData();
});
</script>

<style scoped lang="scss">
.table-demo-container {
  .table-demo-padding {
    padding: 15px;

    .table-demo {
      flex: 1;
      overflow: hidden;
    }
  }
}
</style>
