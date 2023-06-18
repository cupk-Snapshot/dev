package com.cupk.snapshot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cupk.snapshot.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
* @author User
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-06-13 14:11:08
* @Entity com.cupk.snapshot.domain/entity.SysUser
*/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户id查询角色id
     * @param id 用户id
     * @return 角色id
     */
    Long selectRoleIdById(@Param("id") Long id);

    /**
     * 根据角色id查询角色权限
     * @param roleId 角色id
     * @return 该角色拥有的权限
     */
    Set<String> selectAuthoritiesByRoleId(@Param("roleId") Long roleId);


}




