package com.cupk.snapshot.controller;

import cn.hutool.core.util.ReUtil;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.service.impl.SmsServiceImpl;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短信验证码接口
 * Created by Guo Tianyou on 2023/6/8.
 */
@RestController
@RequestMapping("/oauth")
public class SmsController {

    /**
     * 国内手机号正则表达式
     */
    private static final String PHONE_REG = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    @Autowired
    private SmsServiceImpl smsServiceImpl;

    @GetMapping("/sms")
    public R sendSms(@RequestParam("phone_num") String phoneNum) throws TencentCloudSDKException {
        if (!ReUtil.isMatch(PHONE_REG, phoneNum)) {
            return R.error("手机号格式错误");
        }
        smsServiceImpl.sendSms(phoneNum);
        return R.success("短信发送成功");
    }

}
