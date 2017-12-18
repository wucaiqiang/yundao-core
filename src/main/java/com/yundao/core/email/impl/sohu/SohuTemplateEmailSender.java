package com.yundao.core.email.impl.sohu;

import com.yundao.core.email.TemplateEmailInformation;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import io.jstack.sendcloud4j.mail.Substitution;

import javax.mail.internet.InternetAddress;
import java.util.Map;

/**
 * 发送邮件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class SohuTemplateEmailSender extends SohuEmailSender {

    private static Log log = LogFactory.getLog(SohuTemplateEmailSender.class);


    /**
     * 发送模板邮件
     *
     * @param emailInformation 模板信息
     * @return 是否发送成功
     */
    public static boolean sendEmail(TemplateEmailInformation emailInformation) {
        // 校验邮件
        if (emailInformation == null || BooleanUtils.isEmpty(emailInformation.getToList()) || BooleanUtils.isBlank(emailInformation.getCode())) {
            log.info("发送的邮件内容不正确");
            return false;
        }

        // 替换模板变量
        Substitution.Sub sub = Substitution.sub();
        Map<String, String> params = emailInformation.getParamMap();
        if (BooleanUtils.isNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sub.set(entry.getKey(), entry.getValue());
            }
        }

        // 获取收件人
        Substitution.SubGroup subGroup = Substitution.subGroup();
        int size = emailInformation.getToList().size();
        String[] toArray = new String[size];
        for (int i = 0; i < size; i++) {
            InternetAddress each = emailInformation.getToList().get(i);
            toArray[i] = each.getAddress();
            subGroup.add(sub);
        }

        // 构造发送邮件
        Email email = Email.template(emailInformation.getCode()).from(emailInformation.getFrom().getAddress()).
                fromName(emailInformation.getSenderName()).substitutionVars(subGroup).to(toArray);
        Result result = getSendCloud(emailInformation.getType()).mail().send(email);

        log.info("发送结果" + result.isSuccess() + "," + result.getMessage());
        return result.isSuccess();
    }

}
