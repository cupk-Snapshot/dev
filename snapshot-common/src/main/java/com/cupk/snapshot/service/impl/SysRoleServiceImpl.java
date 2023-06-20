package com.cupk.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cupk.snapshot.domain.entity.SysRole;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.SysRoleService;
import com.cupk.snapshot.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author User
* @description 针对表【sys_role(角色)】的数据库操作Service实现
* @createDate 2023-06-13 14:11:08
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysRole getSysRole(Long userId) {
        SysRole sysRole = sysRoleMapper.getSysRole(userId);
        return sysRole;
    }
}




