package com.cupk.snapshot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cupk.snapshot.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author User
* @description 针对表【sys_role(角色)】的数据库操作Mapper
* @createDate 2023-06-13 14:11:08
* @Entity com.cupk.snapshot.domain/entity.SysRole
*/
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    SysRole getSysRole(@Param("userId") Long userId);

}




