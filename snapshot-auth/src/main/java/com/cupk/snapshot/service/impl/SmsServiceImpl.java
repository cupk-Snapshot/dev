package com.cupk.snapshot.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.cupk.snapshot.exception.RedisException;
import com.cupk.snapshot.utils.RedisUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 腾讯云短信服务
 * Create by Guo Tianyou on 2023/6/8.
 */
@RefreshScope
@Service
public class SmsServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    /*
     * 访问管理-API密钥管理
     */
    @Value("${tencent.sms.secret-id}")
    private String secretId;

    @Value("${tencent.sms.secret-key}")
    private String secretKey;

    /*
     * 短信-应用列表-SDKAppID
     */
    @Value("${tencent.sms.sdk-app-id}")
    private String sdkAppId;

    /*
     * 短信-签名管理-签名内容
     */
    @Value("${tencent.sms.sign-name}")
    private String signName;

    /*
     * 短信-正文模板管理-ID
     */
    @Value("${tencent.sms.template-id}")
    private String templateId;

    public void sendSms(String phoneNum) throws TencentCloudSDKException {
        /*
         * 实例化一个认证对象，传入腾讯云账户密钥对secretId，secretKey
         * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi
         */
        Credential cred = new Credential(secretId, secretKey);

        /* 实例化要请求产品(以sms为例)的client对象 */
        SmsClient client = new SmsClient(cred, "ap-beijing");

        /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数 */
        SendSmsRequest req = new SendSmsRequest();

        /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
        req.setSmsSdkAppId(sdkAppId);

        /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名 */
        req.setSignName(signName);

        /* 模板 ID: 必须填写已审核通过的模板 ID */
        req.setTemplateId(templateId);

        // 生成6位数字随机验证码
        String smsCode = RandomUtil.randomNumbers(6);

        /*
         * 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空
         * 短信正文模板：{1}为您的登录验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。
         */
        String[] templateParamSet = {smsCode, "5"};
        req.setTemplateParamSet(templateParamSet);

        /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号] */
        String[] phoneNumberSet = {"+86" + phoneNum};
        req.setPhoneNumberSet(phoneNumberSet);

        SendSmsResponse res = client.SendSms(req);

        // 输出json格式的字符串回包
        log.info(SendSmsResponse.toJsonString(res));

        // 获取短信发送状态
        String code = res.getSendStatusSet()[0].getCode();

        // 将验证码存入Redis缓存
        if (code.equals("Ok")) {
            try {
                redisUtils.set("code:"+phoneNum, smsCode, 5, TimeUnit.MINUTES);
            } catch (Exception e) {
                throw new RedisException("redis连接失败");
            }
        }

    }

}
