<template>
  <div class="system-user-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="769px">
      <text>商品图片</text>
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9955/file/oss/upload"
          name="file"
          :headers=headers
          :data=data
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
      >
        <img v-if="state.ruleForm.picUrl" :src="state.ruleForm.picUrl" class="avatar"/>
        <el-icon v-else class="avatar-uploader-icon">
          <Plus/>
        </el-icon>
      </el-upload>
      <el-form ref="userDialogFormRef" :model="state.ruleForm" size="default" label-width="90px"
               style="margin-top: 20px">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="商品描述">
              <el-input v-model="state.ruleForm.title" placeholder="请输入商品描述" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="所需积分">
              <el-input v-model="state.ruleForm.point" placeholder="请输入所需积分" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="库存">
              <el-input v-model="state.ruleForm.stocks" placeholder="请输入库存" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit(state.ruleForm)" size="default">{{
              state.dialog.submitTxt
            }}</el-button>
				</span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="systemUserDialog">
import {reactive, ref} from 'vue';
import {Session} from '/@/utils/storage';
import {goodsApi} from "/@/api/manage/goods"


// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义变量内容
const headers = {
  'Authorization': 'Bearer ' + Session.get('token')
}
const userDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    title: '',
    stocks: '',
    point: '',
    picUrl: ''
  },
  deptData: [] as DeptTreeType[], // 部门数据
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
});
const data = {
  location: 'picture'
}
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'

import type {UploadProps} from 'element-plus'

const imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
  // state.ruleForm.pic = URL.createObjectURL(uploadFile.raw!)
  state.ruleForm.picUrl = response.data[0]
  console.log(response.data[0])
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}
// 打开弹窗
const openDialog = (type: string, row: RowUserType) => {
  if (type === 'edit') {
    state.ruleForm = row;
    state.dialog.title = '修改';
    state.dialog.submitTxt = '修 改';
  } else {
    state.dialog.title = '新增商品';
    state.dialog.submitTxt = '新 增';
    // 清空表单，此项需加表单验证才能使用
    // nextTick(() => {
    // 	userDialogFormRef.value.resetFields();
    // });
  }
  state.dialog.isShowDialog = true;
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
  if (state.dialog.title == '修改') {
    //修改商品信息
    goodsApi().update(data).then(res => {
      if (res.data.code == 200) {
        ElMessage.success("修改成功")
        closeDialog();
      } else {
        ElMessage.error("修改失败")
      }
    })
  } else {
    //修改商品信息
    goodsApi().add(data).then(res => {
      if (res.data.code == 200) {
        ElMessage.success("添加成功")
        closeDialog();
      } else {
        ElMessage.error("添加失败")
      }
    })
  }
  emit('refresh');
  // if (state.dialog.type === 'add') { }
};
// 暴露变量
defineExpose({
  openDialog,
});
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
