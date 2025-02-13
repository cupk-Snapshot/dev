package com.cupk.snapshot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cupk.snapshot.domain.entity.SysRole;

import java.util.List;

/**
* @author User
* @description 针对表【sys_role(角色)】的数据库操作Service
* @createDate 2023-06-13 14:11:08
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户id查询用户角色
     * @param userId
     * @return
     */
    SysRole getSysRole(Long userId);

}
