package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.domain.model.vo.UserVo;
import com.cupk.snapshot.service.SysUserService;
import com.cupk.snapshot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户注册接口
     */
    @PostMapping("/signup")
    public R signUp(@RequestParam("username") String username, @RequestParam("password") String password,
                    @RequestParam("phone_num") String phoneNum, @RequestParam("sms_code") String smsCode) {
        long count = sysUserService.count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (count == 1) {
            return R.error("用户名已存在");
        }
        String code = redisUtils.get("code:" + phoneNum).toString();
        if (!StringUtils.isEmpty(code) && smsCode.equals(code)) {
            SysUser sysUser = new SysUser(username, bCryptPasswordEncoder.encode(password), phoneNum);
            sysUserService.save(sysUser);
            return R.success("注册成功");
        }
        return R.error("验证码错误");
    }

    /**
     * 查询当前登录用户信息
     */
    @GetMapping("/info")
    public R info(@RequestParam("user_id") Long userId) {
        SysUser sysUser = sysUserService.getOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, userId)
        );
        UserVo userVo = BeanUtil.toBean(sysUser, UserVo.class);
        return R.success(userVo);
    }

    /**
     * 修改密码
     */
    @PostMapping("/update/passwd")
    public R updatePasswd(@RequestParam("user_id") Long userId, @RequestParam("passwd") String passwd,
                          @RequestParam("new_passwd") String newPasswd, @RequestParam("repeat") String repeat) {
        SysUser sysUser = sysUserService.getOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, userId)
        );
        if (!bCryptPasswordEncoder.matches(passwd, sysUser.getPassword())) {
            return R.error("密码错误");
        } else if (!newPasswd.equals(repeat)) {
            return R.error("确认新密码与新密码输入不一致");
        }
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword, bCryptPasswordEncoder.encode(newPasswd)).eq(SysUser::getUserId, userId);
        sysUserService.update(updateWrapper);

        // 清除redis中的token信息
        logout();
        return R.success();
    }

    /**
     * 修改个人信息
     */
    @PostMapping("/update/info")
    public R updateInfo(@RequestParam("user_id") Long userId, @RequestParam("nick_name") String nickName,
                        @RequestParam("phone_num") String phoneNum, @RequestParam("name") String name) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(!StringUtils.isEmpty(nickName), SysUser::getNickName, nickName)
                .set(!StringUtils.isEmpty(phoneNum), SysUser::getPhoneNum, phoneNum)
                .set(!StringUtils.isEmpty(name), SysUser::getName, name)
                .eq(SysUser::getUserId, userId);
        sysUserService.update(updateWrapper);
        return R.success("修改成功");
    }

    /**
     * 退出登录
     */
    @GetMapping("/signout")
    public R logout() {
        OAuth2AuthenticationDetails details =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String key = "access_token:" + details.getTokenValue();
        redisUtils.del(key);
        return R.success();
    }

}
