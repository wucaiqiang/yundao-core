package com.yundao.core.enums.msc;

import org.apache.commons.lang.math.NumberUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 短消息认证服务类型枚举
 *
 * @author wupengfei wupf86@126.com
 */
public enum SmsAuthenticatorServiceTypeEnum {

    /**
     * 【招财猫微路演】生产
     */
    VALUE_0("0", "【招财猫微路演】生产"),

    /**
     * 【招财猫微路演】服务
     */
    VALUE_1("1", "【招财猫微路演】服务"),

    /**
     * 【招财猫】生产
     */
    VALUE_2("2", "【招财猫】生产"),

    /**
     * 【招财猫】服务
     */
    VALUE_3("3", "【招财猫】服务"),

    /**
     * 【混金融】生产
     */
    VALUE_4("4", "【混金融】生产"),

    /**
     * 【混金融】服务
     */
    VALUE_5("5", "【混金融】服务");

    private static Map<String, SmsAuthenticatorServiceTypeEnum> enumMap = new HashMap<String, SmsAuthenticatorServiceTypeEnum>();

    static {
        EnumSet<SmsAuthenticatorServiceTypeEnum> set = EnumSet.allOf(SmsAuthenticatorServiceTypeEnum.class);
        for (SmsAuthenticatorServiceTypeEnum each : set) {
            enumMap.put(each.getValue(), each);
        }
    }

    private String value;
    private String name;

    SmsAuthenticatorServiceTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取短消息认证服务类型枚举
     *
     * @param value
     * @return
     */
    public static SmsAuthenticatorServiceTypeEnum getSmsAuthenticatorServiceTypeEnum(String value) {
        return enumMap.get(value);
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getIntValue() {
        return NumberUtils.toInt(value);
    }

}