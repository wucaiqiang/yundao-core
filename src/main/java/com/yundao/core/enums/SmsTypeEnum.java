package com.yundao.core.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信类型枚举
 *
 * @author jan
 * @create 2017-06-15 PM7:35
 **/
public enum SmsTypeEnum {

    /**
     * 验证码
     */
    CAPTCHA(1, "验证码"),

    /**
     * 通知
     */
    NOTIFY(2, "通知"),


    /**
     * 推广
     */
    PROMOTION(3, "推广");

    private Integer value;
    private String name;
    private static Map<Integer, SmsTypeEnum> enumMap = new HashMap<Integer, SmsTypeEnum>();

    static {
        EnumSet<SmsTypeEnum> set = EnumSet.allOf(SmsTypeEnum.class);
        for (SmsTypeEnum each : set) {
            enumMap.put(each.getValue(), each);
        }
    }

    SmsTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     *
     * @param value
     * @return
     */
    public static SmsTypeEnum getServiceTypeEnum(Integer value) {
        return enumMap.get(value);
    }
}
