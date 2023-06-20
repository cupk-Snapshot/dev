<template>
	<div class="system-user-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="769px">
			<el-form ref="userDialogFormRef" :model="state.ruleForm" size="default" label-width="90px">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="用户昵称">
							<el-input v-model="state.ruleForm.nickName" placeholder="请输入用户昵称" clearable></el-input>
						</el-form-item>
					</el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户名">
              <el-input v-model="state.ruleForm.username" placeholder="请输入用户昵称" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="姓名">
              <el-input v-model="state.ruleForm.name" placeholder="请输入用户昵称" clearable></el-input>
            </el-form-item>
          </el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号">
							<el-input v-model="state.ruleForm.phoneNum" placeholder="请输入手机号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="用户状态">
							<el-switch v-model="state.ruleForm.isEnabled" inline-prompt active-text="启" inactive-text="禁"></el-switch>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit(state.ruleForm)" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="systemUserDialog">
import { reactive, ref } from 'vue';
import {goodsApi} from "/@/api/manage/goods";
import {ElMessage} from "element-plus";
import {userApi} from "/@/api/manage/user"

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义变量内容
const userDialogFormRef = ref();
const state = reactive({
	ruleForm: {},
	deptData: [] as DeptTreeType[], // 部门数据
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
	},
});

// 打开弹窗
const openDialog = (type: string, row: RowUserType) => {
	if (type === 'edit') {
		state.ruleForm = row;
		state.dialog.title = '修改用户';
		state.dialog.submitTxt = '修 改';
	} else {
		state.dialog.title = '新增用户';
		state.dialog.submitTxt = '新 增';
		// 清空表单，此项需加表单验证才能使用
		// nextTick(() => {
		// 	userDialogFormRef.value.resetFields();
		// });
	}
	state.dialog.isShowDialog = true;
	getMenuData();
};
// 关闭弹窗
const closeDialog = () => {
	state.dialog.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
};
// 提交
const onSubmit = (data: object) => {
  if (state.dialog.title == '修改用户') {
    //修改用户信息
    userApi().update(data).then(res => {
      if (res.data.code == 200) {
        ElMessage.success("修改成功")
        closeDialog();
      } else {
        ElMessage.error("修改失败")
      }
    })
  } else {
    //新增用户信息
    userApi().add(data).then(res => {
      if (res.data.code == 200) {
        ElMessage.success("添加成功")
        closeDialog();
      } else {
        ElMessage.error("添加失败")
      }
    })
  }
	// closeDialog();
	emit('refresh');
	// if (state.dialog.type === 'add') { }
};
// 初始化部门数据
const getMenuData = () => {
	state.deptData.push({
		deptName: '公安局',
		createTime: new Date().toLocaleString(),
		status: true,
		sort: Math.random(),
		describe: '顶级部门',
		id: Math.random(),
		children: [
			{
				deptName: '一分局',
				createTime: new Date().toLocaleString(),
				status: true,
				sort: Math.random(),
				describe: '一分局',
				id: Math.random(),
			},
			{
				deptName: '二分局',
				createTime: new Date().toLocaleString(),
				status: true,
				sort: Math.random(),
				describe: '二分局',
				id: Math.random(),
			},
      {
        deptName: '三分局',
        createTime: new Date().toLocaleString(),
        status: true,
        sort: Math.random(),
        describe: '三分局',
        id: Math.random(),
      },
      {
        deptName: '四分局',
        createTime: new Date().toLocaleString(),
        status: true,
        sort: Math.random(),
        describe: '四分局',
        id: Math.random(),
      },
		],
	});
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
