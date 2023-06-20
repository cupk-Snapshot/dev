<template>
  <div class="system-user-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="system-user-search mb15">
        <el-input size="default" placeholder="请输入用户名称" style="max-width: 180px"></el-input>
        <el-button size="default" type="primary" class="ml10">
          <el-icon>
            <ele-Search/>
          </el-icon>
          查询
        </el-button>
        <el-button size="default" type="success" class="ml10" @click="onOpenAddUser('add')">
          <el-icon>
            <ele-FolderAdd/>
          </el-icon>
          新增商品
        </el-button>
      </div>
      <el-table :data="state.tableData.data" v-loading="state.tableData.loading" style="width: 100%">
        <el-table-column type="index" prop="goodsId" label="序号" width="60"/>
        <el-table-column prop="title" label="商品描述" show-overflow-tooltip></el-table-column>
        <el-table-column
            prop="picUrl"
            label="商品图片">
          <template v-slot="scope">
            <el-image
                style="width: 100px; height: 100px; margin-bottom:-4px"
                :src='scope.row.picUrl'
                :zoom-rate="1.2"
                :preview-src-list="[scope.row.picUrl]"
                preview-teleported
                fit="cover"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="point" label="所需积分" show-overflow-tooltip></el-table-column>
        <el-table-column prop="stocks" label="库存" show-overflow-tooltip></el-table-column>
        <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button :disabled="scope.row.userName === 'admin'" size="small" text type="primary"
                       @click="onOpenEditUser('edit', scope.row)"
            >修改
            </el-button
            >
            <el-button :disabled="scope.row.userName === 'admin'" size="small" text type="primary"
                       @click="onRowDel(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="onHandleSizeChange"
          @current-change="onHandleCurrentChange"
          class="mt15"
          :pager-count="5"
          :page-sizes="[1, 2, 5]"
          v-model:current-page="state.tableData.param.pageNum"
          background
          v-model:page-size="state.tableData.param.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="state.tableData.total"
      >
      </el-pagination>
    </el-card>
    <UserDialog ref="userDialogRef" @refresh="getTableData()"/>
  </div>
</template>

<script setup lang="ts" name="userManage">
import {defineAsyncComponent, reactive, onMounted, ref} from 'vue';
import {ElMessageBox, ElMessage} from 'element-plus';
import request from "/@/utils/request";
import {goodsApi} from "/@/api/manage/goods"

// 引入组件
const UserDialog = defineAsyncComponent(() => import('/@/views/manage/goods/dialog.vue'));
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
// 初始化表格数据
const getTableData = () => {
  state.tableData.loading = true;
  goodsApi().all(state.tableData.param.pageNum, state.tableData.param.pageSize).then(res => {
    state.tableData.total = res.data.data.total
    state.tableData.data = res.data.data.list
  })
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
// 删除商品
const onRowDel = (row: RowUserType) => {
  ElMessageBox.confirm(`此操作将永久删除商品：“${row.userNickname}”，是否继续?`, '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(() => {
        const ids=[]
        ids.push(row.goodsId)
        goodsApi().delete(ids).then(res => {
          if (res.data.code == 200) {
            ElMessage.success("删除成功")
            getTableData();
          }else {
            ElMessage.error("删除失败")
          }
        });
      })
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
