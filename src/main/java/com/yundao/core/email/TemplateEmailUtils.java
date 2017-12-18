package com.yundao.core.email;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.ThreadUtils;

/**
 * 模板邮件工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class TemplateEmailUtils {

    private static Log log = LogFactory.getLog(TemplateEmailUtils.class);

    /**
     * 默认以批量的形式异步发送邮件
     *
     * @param to     收件人，多个时以逗号分隔
     * @param code   模板编码
     * @param params 模板参数
     * @return 是否发送成功
     */
    public static FutureTask<Boolean> asyncSendEmail(String to, String code, Map<String, String> params) {
        return asyncSendEmail(TemplateEmailInformation.BATCH, to, code, params);
    }

    /**
     * 异步发送邮件
     *
     * @param emailType 邮件类型，<code>TemplateEmailInformation.BATCH：批量</code>，<code>TemplateEmailInformation.TRIGGER：触发</code>
     * @param to        收件人，多个时以逗号分隔
     * @param code      模板编码
     * @param params    模板参数
     * @return 是否发送成功
     */
    public static FutureTask<Boolean> asyncSendEmail(int emailType, String to, String code, Map<String, String> params) {
        log.info(() -> "emailType=" + emailType + ", to=" + to + ", code=" + code + ", params=" + params);
        if (!params.containsKey("environment")) {
            if (ConfigUtils.isProduct()) {
                params.put("environment", "正式环境");
            }
            else {
                params.put("environment", "测试环境");
            }
        }
        TemplateEmailInformation emailInformation = EmailUtils.getTemplateEmailInformation();
        emailInformation.setCode(code);
        emailInformation.setParamMap(params);
        emailInformation.setType(emailType);
        emailInformation.setToList(EmailUtils.getAddress(to));
        Callable<Boolean> emailCallable = new TemplateEmailCallable(emailInformation);
        FutureTask<Boolean> futureTask = new FutureTask<>(emailCallable);
        ThreadUtils.execute(futureTask);
        return futureTask;
    }

    private static class TemplateEmailCallable implements Callable<Boolean> {

        private TemplateEmailInformation email;

        public TemplateEmailCallable(TemplateEmailInformation email) {
            this.email = email;
        }

        @Override
        public Boolean call() throws Exception {
            boolean result = TemplateEmailSender.sendEmail(email);
            return result;
        }
    }
}
