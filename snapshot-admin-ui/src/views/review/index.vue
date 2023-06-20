<template>
	<div class="table-demo-container layout-padding">
		<div class="table-demo-padding layout-padding-view layout-padding-auto">
			<TableSearch :search="state.tableData.search" @search="onSearch" />
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
import { defineAsyncComponent, reactive, ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import {acceptApi} from '/@/api/manage/accpet';
// 引入组件
const Table = defineAsyncComponent(() => import('/@/components/table/index.vue'));
const TableSearch = defineAsyncComponent(() => import('/@/views/review/search.vue'));

// 定义变量内容
const tableRef = ref<RefType>();
const state = reactive<TableDemoState>({
	tableData: {
		// 列表数据（必传）
		data: [],
		// 表头内容（必传，注意格式）
		header: [
      { key: 'acceptId', colWidth:'80', title: '审核ID', type: 'int', isCheck: true },
      { key: 'userId', colWidth:'80', title: '用户ID', type: 'int', isCheck: true },
      { key: 'username', colWidth: '90', title: '举报用户', type: 'text', isCheck: true },
			{ key: 'wfDesc', colWidth: '200', title: '举报内容', type: 'text', isCheck: true },
			{ key: 'wfType', colWidth: '', title: '违法类型', type: 'text', isCheck: true },
			{ key: 'wfAddress', colWidth: '', title: '举报位置', type: 'text', isCheck: true },
			{ key: 'wfTime', colWidth: '', title: '举报时间', type: 'text', isCheck: true },
      { key: 'pic1Url', colWidth: '', width: '100', height: 'auto', title: '举报图片1', type: 'image', isCheck: true },
      { key: 'pic2Url', colWidth: '', width: '100', height: 'auto', title: '举报图片2', type: 'image', isCheck: true },
      { key: 'pic3Url', colWidth: '', width: '100', height: 'auto', title: '举报图片3', type: 'image', isCheck: true },
      { key: 'hphm', colWidth: '', title: '车牌号码', type: 'text', isCheck: true },
      { key: 'hpzl', colWidth: '', title: '车牌种类', type: 'text', isCheck: true },
      { key: 'examineName', colWidth: '', title: '审核人', type: 'text', isCheck: true },
      { key: 'status', colWidth: '', title: '审核状态', type: 'text', isCheck: true },
      { key: 'isPublic', colWidth: '180', title: '是否公开' , type: 'int', isCheck: true },

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
			{ label: '用户ID', prop: 'uid', placeholder: '请输入用户ID', required: false, type: 'input' },
			{ label: '举报位置', prop: 'address', placeholder: '请输入详细举报位置', required: false, type: 'input' },
			{ label: '联系电话', prop: 'phone', placeholder: '请输入采样点联系电话', required: false, type: 'input' },
			{ label: '举报时间', prop: 'time', placeholder: '请选择', required: false, type: 'date' },
			{
				label: '支持24小时',
				prop: 'isSupport',
				placeholder: '请选择',
				required: false,
				type: 'select',
				options: [
					{ label: '是', value: 1 },
					{ label: '否', value: 0 },
				],
			},
			{ label: '图片描述', prop: 'image', placeholder: '请输入图片描述', required: false, type: 'input' },
			{ label: '核酸机构', prop: 'mechanism', placeholder: '请输入核酸机构', required: false, type: 'input' },
		],
		// 搜索参数（不用传，用于分页、搜索时传给后台的值，`getTableData` 中使用）
		param: {
			pageNum: 1,
			pageSize: 10,
		},
		// 打印标题
		printName: '表格打印演示',
	},
});

// 初始化列表数据
const getTableData = () => {
  state.tableData.config.loading = true;
  state.tableData.data = [];

  acceptApi().all(state.tableData.param.pageNum, state.tableData.param.pageSize).then(res => {
    state.tableData.config.total = res.data.data.total
    state.tableData.data = res.data.data.list
  })
	// for (let i = 0; i < 20; i++) {
	// 	state.tableData.data.push({
  //     reportId: state.tableData.data,
  //     content: `这个路上有${i + 1}辆车乱停，堵路`,
	// 		address: `中沧公寓中庭榕树下${i + 1}`,
	// 		phone: `0592-6081259${i + 1}`,
	// 		time: `2020.02.06 6:00 ~ 24:00`,
  //     isOpen: `${i % 2}`,
  //     image1:`https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg`,
  //     image2:`https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg`,
  //     image3:`https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg`,
  //     pass:0,
  //     point:0,
	// 	});
	// }
	// // 数据总数（模拟，真实从接口取）
	// state.tableData.config.total = state.tableData.data.length;
	setTimeout(() => {
		state.tableData.config.loading = false;
	}, 500);
};
// 搜索点击时表单回调
const onSearch = (data: EmptyObjectType) => {
	state.tableData.param = Object.assign({}, state.tableData.param, { ...data });
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
