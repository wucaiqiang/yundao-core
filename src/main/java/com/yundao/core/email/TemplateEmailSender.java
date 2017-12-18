package com.yundao.core.email;

import com.yundao.core.email.impl.sohu.SohuTemplateEmailSender;
import com.yundao.core.email.impl.sohu.SohuEmailSender;
import com.yundao.core.utils.BooleanUtils;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * 发送邮件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class TemplateEmailSender {

    /**
     * 发送模板邮件
     *
     * @param emailInformation 模板信息
     * @return 是否发送成功
     */
    public static boolean sendEmail(TemplateEmailInformation emailInformation) {
        List<InternetAddress> ccList = emailInformation.getCcList();
        if (!BooleanUtils.isEmpty(ccList) || emailInformation.isShowToAddressToAll()) {
            return SohuEmailSender.sendEmail(emailInformation);
        }
        else {
            return SohuTemplateEmailSender.sendEmail(emailInformation);
        }
    }


}
