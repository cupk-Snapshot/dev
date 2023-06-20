package com.cupk.snapshot.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.SysRole;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.domain.model.vo.PageVo;
import com.cupk.snapshot.domain.model.vo.SysRoleVo;
import com.cupk.snapshot.domain.model.vo.SysUserVo;
import com.cupk.snapshot.service.SysRoleService;
import com.cupk.snapshot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页查询系统用户信息
     */
    @GetMapping("/all")
    public R getAll(@RequestParam("current") Long current, @RequestParam("size") Long size) {
        Page<SysUser> page = sysUserService.page(new Page<>(current, size), null);
        List<SysUser> sysUserList = page.getRecords();

        ArrayList<SysUserVo> sysUserVos = new ArrayList<>();
        sysUserList.forEach(i -> {
            SysUserVo sysUserVo = BeanUtil.toBean(i, SysUserVo.class);
            //获取用户角色信息
            SysRole sysRole = sysRoleService.getSysRole(i.getUserId());
            SysRoleVo sysRoleVo = BeanUtil.toBean(sysRole, SysRoleVo.class);
            sysUserVo.setSysRoleVo(sysRoleVo);

            sysUserVos.add(sysUserVo);
        });
        PageVo res = new PageVo(sysUserVos, page);
        return R.success(res);
    }

    /**
     * 查询当前登录用户信息
     */
    @GetMapping("/info")
    public R info(@RequestParam("user_id") Long userId) {
        SysUser sysUser = sysUserService.getOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, userId)
        );
        SysUserVo sysUserVo = BeanUtil.toBean(sysUser, SysUserVo.class);
        //获取当前登录角色信息
        SysRole sysRole = sysRoleService.getSysRole(sysUser.getUserId());
        SysRoleVo sysRoleVo = BeanUtil.toBean(sysRole, SysRoleVo.class);
        sysUserVo.setSysRoleVo(sysRoleVo);
        return R.success(sysUserVo);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/update")
    public R update(@RequestParam("userId") Long userId, @RequestParam("nickName") String nickName, @RequestParam("username") String username,
                    @RequestParam("phoneNum") Long phoneNum, @RequestParam("name") String name, @RequestParam("isEnabled") Integer isEnabled) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(!StringUtils.isEmpty(nickName), SysUser::getNickName, nickName)
                .set(!StringUtils.isEmpty(username), SysUser::getUsername, username)
                .set(!StringUtils.isEmpty(name), SysUser::getName, name)
                .set(phoneNum != null, SysUser::getPhoneNum, phoneNum)
                .set(isEnabled != null, SysUser::getIsEnabled, isEnabled)
                .eq(SysUser::getUserId, userId);
        boolean flag = sysUserService.update(updateWrapper);
        if (flag) {
            return R.success("修改成功");
        }  else {
            return R.error("修改失败");
        }
    }

//    /**
//     * MySQL条件查询
//     */
//    @GetMapping("/search")
//    public R search(@RequestParam("accept_id") Long acceptId, @RequestParam("status") String status, @RequestParam("is_public") Long isPublic) {
//        //定义查询条件，有可能为null（用户未输入或未选择）
//        QueryWrapper<Accept> queryWrapper = new QueryWrapper<>();
//        queryWrapper
//                .eq(acceptId != null, "accept_id", acceptId)
//                .eq(!StringUtils.isEmpty(status), "status", status)
//                .eq(isPublic != null, "is_public", isPublic);
//
//        List<Accept> res = acceptService.list(queryWrapper);
//        return R.success(res);
//    }
//
    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        boolean flag = sysUserService.removeBatchByIds(ids);
        if (flag) {
            return R.success("删除成功");
        }else {
            return R.error("删除失败");
        }
    }


}
