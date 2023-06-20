<template>
	<div class="system-user-container layout-padding">
		<el-card shadow="hover" class="layout-padding-auto">
			<div class="system-user-search mb15">
				<el-input size="default" placeholder="请输入用户名称" style="max-width: 180px"> </el-input>
				<el-button size="default" type="primary" class="ml10">
					<el-icon>
						<ele-Search />
					</el-icon>
					查询
				</el-button>
				<el-button size="default" type="success" class="ml10" @click="onOpenAddUser('add')">
					<el-icon>
						<ele-FolderAdd />
					</el-icon>
					新增用户
				</el-button>
			</div>
			<el-table :data="state.tableData.data" v-loading="state.tableData.loading" style="width: 100%">
<!--				<el-table-column type="index" label="序号" width="60" />-->
				<el-table-column prop="userId" label="序号" width="60" />
				<el-table-column prop="nickName" label="用户昵称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="username" label="用户名" show-overflow-tooltip></el-table-column>
        <el-table-column
            prop="avatarUrl"
            label="用户头像">
          <template v-slot="scope">
            <el-image
                style="width: 100px; height: 100px; margin-bottom:-4px"
                :src='scope.row.avatarUrl'
                :zoom-rate="1.2"
                :preview-src-list="[scope.row.avatarUrl]"
                preview-teleported
                fit="cover"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" show-overflow-tooltip></el-table-column>
<!--				<el-table-column prop="department" label="部门" show-overflow-tooltip></el-table-column>-->
				<el-table-column prop="phoneNum" label="手机号" show-overflow-tooltip></el-table-column>
        <el-table-column prop="idcard" label="身份证" show-overflow-tooltip></el-table-column>
<!--        <el-table-column prop="sex" label="性别" show-overflow-tooltip></el-table-column>-->
        <el-table-column prop="sysRoleVo.roleName" label="关联角色" show-overflow-tooltip></el-table-column>
				<el-table-column prop="isEnable" label="用户状态" show-overflow-tooltip>
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.isEnabled===1">启用</el-tag>
						<el-tag type="info" v-else>禁用</el-tag>
					</template>
				</el-table-column>
<!--				<el-table-column prop="describe" label="用户描述" show-overflow-tooltip></el-table-column>-->
				<el-table-column prop="createTime" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" width="100">
					<template #default="scope">
						<el-button :disabled="scope.row.userName === 'admin'" size="small" text type="primary" @click="onOpenEditUser('edit', scope.row)"
							>修改</el-button
						>
						<el-button :disabled="scope.row.userName === 'admin'" size="small" text type="primary" @click="onRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				class="mt15"
				:pager-count="5"
				:page-sizes="[10, 20, 30]"
				v-model:current-page="state.tableData.param.pageNum"
				background
				v-model:page-size="state.tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="state.tableData.total"
			>
			</el-pagination>
		</el-card>
		<UserDialog ref="userDialogRef" @refresh="getTableData()" />
	</div>
</template>

<script setup lang="ts" name="userManage">
import { defineAsyncComponent, reactive, onMounted, ref } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import request from "/@/utils/request";
import {userApi} from "/@/api/manage/user"

// 引入组件
const UserDialog = defineAsyncComponent(() => import('/@/views/manage/user/dialog.vue'));
// 定义变量内容
const userDialogRef = ref();
const state = reactive<SysUserState>({
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			pageNum: 1,
			pageSize: 10,
		},
	},
});
//查询所有用户
const allUser = () => {
  return request({
    url: "/",
    method: "get",
  });
};
// 初始化表格数据
const getTableData = () => {
  // allUser().then(res=>{
  //   console.log(res.data)
  //   const data=res.data
  // })
	state.tableData.loading = true;
	const data = [];
  userApi().all(state.tableData.param.pageNum, state.tableData.param.pageSize).then(res => {
    state.tableData.total = res.data.data.total
    state.tableData.data = res.data.data.list
  })
	// for (let i = 0; i < 2; i++) {
	// 	data.push({
	// 		userNickname: i === 0 ? '我是管理员' : '我是普通用户',
	// 		roleSign: i === 0 ? 'admin' : 'common',
	// 		department: i === 0 ? ['公安局', '一分局'] : ['公安局', '二分局'],
	// 		phone: '12345678910',
	// 		sex: '女',
	// 		password: '123456',
	// 		overdueTime: new Date(),
	// 		status: true,
	// 		describe: i === 0 ? '不可删除' : '测试用户',
	// 		createTime: new Date().toLocaleString(),
	// 	});
	// }
	// state.tableData.data = data;
	// state.tableData.total = state.tableData.data.length;
	setTimeout(() => {
		state.tableData.loading = false;
	}, 500);
};
// 打开新增用户弹窗
const onOpenAddUser = (type: string) => {
	userDialogRef.value.openDialog(type);
};
// 打开修改用户弹窗
const onOpenEditUser = (type: string, row: RowUserType) => {
	userDialogRef.value.openDialog(type, row);
};
// 删除用户
const onRowDel = (row: RowUserType) => {
	ElMessageBox.confirm(`此操作将永久删除账户：“${row.userNickname}”，是否继续?`, '提示', {
		confirmButtonText: '确认',
		cancelButtonText: '取消',
		type: 'warning',
	})
		.then(() => {
      const ids=[]
      ids.push(row.userId)
      userApi().delete(ids).then(res => {
        if (res.data.code == 200) {
          ElMessage.success("删除成功")
          getTableData();
        }else {
          ElMessage.error("删除失败")
        }
      });
		})
		.catch(() => {});
};
// 分页改变
const onHandleSizeChange = (val: number) => {
	state.tableData.param.pageSize = val;
	getTableData();
};
// 分页改变
const onHandleCurrentChange = (val: number) => {
	state.tableData.param.pageNum = val;
	getTableData();
};
// 页面加载时
onMounted(() => {
	getTableData();
});
</script>

<style scoped lang="scss">
.system-user-container {
	:deep(.el-card__body) {
		display: flex;
		flex-direction: column;
		flex: 1;
		overflow: auto;
		.el-table {
			flex: 1;
		}
	}
}
</style>
