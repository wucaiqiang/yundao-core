package com.yundao.core.dto.sms;

import com.yundao.core.enums.msc.SmsAuthenticatorServiceTypeEnum;

import java.io.Serializable;

/**
 * 短消息信息
 *
 * @author wupengfei wupf86@126.com
 */
public class SmsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号，多个时以逗号分隔
     */
    private String mobiles;

    /**
     * 手机号类型
     */
    private Integer mobileType;

    /**
     * 服务类型
     */
    private SmsAuthenticatorServiceTypeEnum serviceTypeEnum;

    /**
     * 发送者ip
     */
    private String ip;

    /**
     * 验证码
     */
    private String validateCode;

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public Integer getMobileType() {
        return mobileType;
    }

    public void setMobileType(Integer mobileType) {
        this.mobileType = mobileType;
    }

    public SmsAuthenticatorServiceTypeEnum getServiceTypeEnum() {
        return serviceTypeEnum;
    }

    public void setServiceTypeEnum(SmsAuthenticatorServiceTypeEnum serviceTypeEnum) {
        this.serviceTypeEnum = serviceTypeEnum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
