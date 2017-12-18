package com.yundao.core.email.impl.sohu;

import java.util.List;

/**
 * sohu的模板
 *
 * @author wupengfei wupf86@126.com
 */
public class SohuTemplates {

    /**
     * 提示信息
     */
    private String message;

    /**
     * 模板列表
     */
    private List<SohuTemplate> templateList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SohuTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<SohuTemplate> templateList) {
        this.templateList = templateList;
    }

    public static class SohuTemplate {

        /**
         * 调用名称
         */
        private String invoke_name;

        /**
         * 模板名称
         */
        private String name;

        /**
         * 模板内容
         */
        private String html;

        /**
         * 邮件标题
         */
        private String subject;

        /**
         * 邮件类型
         */
        private int email_type;

        /**
         * 审核状态
         */
        private int is_verify;

        /**
         * 邮件模板创建时间
         */
        private String gmt_created;

        /**
         * 邮件模板更新时间
         */
        private String gmt_modified;

        public String getInvoke_name() {
            return invoke_name;
        }

        public void setInvoke_name(String invoke_name) {
            this.invoke_name = invoke_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getEmail_type() {
            return email_type;
        }

        public void setEmail_type(int email_type) {
            this.email_type = email_type;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public String getGmt_created() {
            return gmt_created;
        }

        public void setGmt_created(String gmt_created) {
            this.gmt_created = gmt_created;
        }

        public String getGmt_modified() {
            return gmt_modified;
        }

        public void setGmt_modified(String gmt_modified) {
            this.gmt_modified = gmt_modified;
        }
    }
}
