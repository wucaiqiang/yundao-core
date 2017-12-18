package com.yundao.core.ed.config;

import com.google.common.collect.Maps;
import com.yundao.core.config.AbstractFileConfig;

import java.util.EnumSet;
import java.util.Map;

/**
 * 加解密配置文件枚举类
 *
 * @author wupengfei wupf86@126.com
 */
public enum EDConfigEnum {

    /**
     * 网站模板
     */
    WEB_TEMPLATE("web.template"),

    /**
     * 产品基础服务
     */
    BS_PD("bs.pd"),

    /**
     * 产品基础服务web
     */
    WEB_PD("web.pd"),

    /**
     * h5前置系统
     */
    H5_PD("h5.pd"),

    /**
     * 产品基础服务dw
     */
    BS_DW("dw"),

    /**
     * 短信服务
     */
    BS_MSC("bs.msc"),

    /**
     * 短信页面服务
     */
    BS_MSC_WEB("bs.msc.web"),

    /**
     * 数据中心服务
     */
    BS_DC("bs.dc"),

    /**
     * 数据中心前端服务
     */
    BS_DC_WEB("bs.dc.web"),

    /**
     * 老BOSS系统
     */
    OLD_BOSS("old.boss"),

    /**
     * 用户基础服务
     */
    BS_UBS("bs.ubs"),

    /**
     * 定时任务
     */
    BS_TT("bs.tt"),

    /**
     * activiti
     */
    BS_PD_WORKFLOW("bs.pd.workflow"),

    /**
     * 招财猫PC
     */
    ZCMALL_PC("zcmall.pc"),

    /**
     * 招财猫h5
     */
    ZCMALL_H5("zcmall.h5"),

    /**
     * 文件服务
     */
    BS_FILE("bs.file"),

    /**
     * app前置系统
     */
    FRONTAPP("frontapp"),

    /**
     * frontapp授权列表
     */
    FRONTAPP_AUTHORZATION("frontapp.authorzation"),

    /**
     * 招财猫android
     */
    ZCMALL_ANDROID("zcmall.android"),

    /**
     * 招财猫ios
     */
    ZCMALL_IOS("zcmall.ios"),

    FRONT_ZCMALL("front.zcmall"),

    BOSS_CMS_WEB("boss.cms.web"),

    BOSS_CMS("boss.cms"),

    /**
     * crm
     */
    CRM("crm"),

    LIVE("live"),

    ZCMALL_APP("zcmall.app"),

    /**
     * 手机前置
     */
    FRONT_M_ZCMALL("front.m.zcmall"),

    /**
     * crm前置
     */
    FRONT_CRM("front.crm"),

    /**
     * crm服务
     */
    CRM_MS("crm.ms"),

    /**
     * 工作流
     */
    WORKFLOW("workflow"),

    /**
     * cps服务
     */
    CPS("cps"),

    /**
     * cps服务
     */
    WEB_QAS("web.qas");

    private static Map<String, EDConfigEnum> enumMap = Maps.newHashMap();
    private static Map<String, Object> dynamicMap = Maps.newHashMap();

    static {
        EnumSet<EDConfigEnum> set = EnumSet.allOf(EDConfigEnum.class);
        for (EDConfigEnum each : set) {
            enumMap.put(each.getKey(), each);
            dynamicMap.put(each.getKey(), each);
        }

        // 动态加载配置的系统标识
        reloadDynamic();
    }

    private String key;

    EDConfigEnum(String key) {
        this.key = key;
    }

    /**
     * 获取加解密枚举
     *
     * @param key
     * @return
     */
    public static EDConfigEnum getEDConfigEnum(String key) {
        return enumMap.get(key);
    }

    /**
     * 键是否存在
     *
     * @param key 键
     * @return 若存在则返回true
     */
    public static boolean isValid(String key) {
        return dynamicMap.containsKey(key) || dynamicMap.containsKey(EDFileConfig.getKeyPrefix() + key);
    }

    /**
     * 是否是app的系统
     *
     * @param systemCode
     * @return
     */
    public static boolean isApp(String systemCode) {
        EDConfigEnum configEnum = EDConfigEnum.getEDConfigEnum(systemCode);
        return ZCMALL_ANDROID == configEnum || ZCMALL_IOS == configEnum;
    }

    /**
     * 增加系统标识
     *
     * @param systemCode 系统标识
     */
    public static void addSystemId(String systemCode) {
        dynamicMap.put(systemCode, null);
    }

    /**
     * 动态加载配置的系统标识
     */
    public static void reloadDynamic() {
        Map<String, String> systemCodeMap = AbstractFileConfig.getKeyStartWith(EDFileConfig.getKeyPrefix());
        dynamicMap.putAll(systemCodeMap);
    }

    public String getKey() {
        return key;
    }
}
