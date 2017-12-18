package com.yundao.core.email;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板邮件信息
 *
 * @author wupengfei wupf86@126.com
 */
public class TemplateEmailInformation extends EmailInformation {

    public static final int BATCH = 0;
    public static final int TRIGGER = 1;

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板参数
     */
    private Map<String, String> paramMap;

    /**
     * 模板类型 0：批量，1：触发
     */
    private int type = TRIGGER;

    /**
     * 是否让所有人看到所发送的收件人地址
     */
    private boolean isShowToAddressToAll;

    public TemplateEmailInformation() {
        paramMap = new HashMap<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isShowToAddressToAll() {
        return isShowToAddressToAll;
    }

    public void setShowToAddressToAll(boolean showToAddressToAll) {
        isShowToAddressToAll = showToAddressToAll;
    }

    /**
     * 添加参数
     *
     * @param name
     * @param value
     * @return
     */
    public TemplateEmailInformation addParameter(String name, String value) {
        paramMap.put(name, value);
        return this;
    }
}
