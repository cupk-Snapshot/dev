package com.cupk.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.SysUserService;
import com.cupk.snapshot.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Userd
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-06-13 14:11:08
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




