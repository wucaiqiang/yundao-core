package com.yundao.core.dto.sms;

import java.util.Map;

/**
 * 短消息信息
 *
 * @author wupengfei wupf86@126.com
 */
public class SmsTemplateDto extends SmsDto {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板参数
     */
    private Map<String, Object> templateParams;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, Object> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, Object> templateParams) {
        this.templateParams = templateParams;
    }
}
