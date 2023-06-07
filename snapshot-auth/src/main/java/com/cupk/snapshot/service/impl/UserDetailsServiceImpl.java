package com.cupk.snapshot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.controller.User;
import com.cupk.snapshot.controller.UserVo;
import com.cupk.snapshot.model.entity.SysUser;
import com.cupk.snapshot.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 从数据库加载用户信息
 * Create by Guo Tianyou on 2023/6/6.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名从数据库中查询用户信息
     *
     * @param s 登录用户名
     * @return UserDetails接口的实现类User，封装了UserVo类
     * @throws UsernameNotFoundException 用户名不存在
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, s));
        if (!ObjectUtils.isEmpty(sysUser)) {
            // 查询用户权限信息
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            sysUserMapper.selectAuthoritiesByRoleId(sysUserMapper.selectRoleIdById(sysUser.getId()))
                    .forEach(i -> authorities.add(new SimpleGrantedAuthority(i)));
            // 封装UserVo类
            UserVo userVo = BeanUtil.toBean(sysUser, UserVo.class);
            return new User(authorities, sysUser.getPassWord(), sysUser.getUserName(), userVo);
        }
        throw new UsernameNotFoundException("用户名未注册");
    }

}
