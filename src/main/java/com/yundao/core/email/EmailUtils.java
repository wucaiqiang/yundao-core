package com.yundao.core.email;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import org.springframework.beans.BeanUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class EmailUtils {

    private static Log log = LogFactory.getLog(EmailUtils.class);

    /**
     * 转化字符串为对象
     *
     * @param address
     * @return
     */
    public static List<InternetAddress> getAddress(String address) {
        try {
            if (BooleanUtils.isBlank(address)) {
                return null;
            }

            List<InternetAddress> result = new ArrayList<InternetAddress>();
            String[] addresses = address.split(CommonConstant.COMMA_SEMICOLON);
            for (String eachAddr : addresses) {
                if (BooleanUtils.isBlank(eachAddr)) {
                    continue;
                }
                result.add(new InternetAddress(eachAddr));
            }
            return result;
        }
        catch (Exception e) {
            log.error("获取邮件地址错误", e);
            return null;
        }
    }

    /**
     * 获取认证
     *
     * @param usernameEnum
     * @param passwordEnum
     * @param index        选用第几个用户和密码
     * @return
     */
    public static Authenticator getAuthenticator(EmailConfigEnum usernameEnum, EmailConfigEnum passwordEnum, int index) {
        final UsernamePassword username = getUsernamePassword(usernameEnum, passwordEnum, index);
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username.getUsername(), username.getPassword());
            }
        };
    }

    /**
     * 获取用户名和密码
     *
     * @param usernameEnum
     * @param passwordEnum
     * @param index
     * @return
     */
    public static UsernamePassword getUsernamePassword(EmailConfigEnum usernameEnum, EmailConfigEnum passwordEnum, int index) {
        String[] usernames = EmailFileConfig.getValue(usernameEnum).split(CommonConstant.COMMA_SEMICOLON);
        String[] passwords = EmailFileConfig.getValue(passwordEnum).split(CommonConstant.COMMA_SEMICOLON);
        int usernameLength = usernames.length;
        int passwordLength = passwords.length;

        // 检验获取用户的索引
        if (index < 0 || index >= usernameLength) {
            index = 0;
        }

        // 获取用户名和密码
        String username = usernames[index];
        String password;
        if (usernameLength == passwordLength) {
            password = passwords[index];
        }
        else {
            password = passwords[0];
        }
        return new UsernamePassword(username, password);
    }

    /**
     * 获取配置的信息
     *
     * @return
     */
    public static EmailInformation getConfigEmailInformation() {
        try {
            EmailInformation result = new EmailInformation();

            // 发件人
            String from = EmailFileConfig.getValue(EmailConfigEnum.SMTP_FROM);
            List<InternetAddress> fromList = EmailUtils.getAddress(from);
            if (fromList != null && fromList.size() != 0) {
                result.setFrom(fromList.get(0));
            }

            // 收件人
            String to = EmailFileConfig.getValue(EmailConfigEnum.TO);
            List<InternetAddress> toList = EmailUtils.getAddress(to);
            result.setToList(toList);

            // 抄送人
            String cc = EmailFileConfig.getValue(EmailConfigEnum.CC);
            List<InternetAddress> ccList = EmailUtils.getAddress(cc);
            result.setCcList(ccList);

            // 密送人
            String bcc = EmailFileConfig.getValue(EmailConfigEnum.BCC);
            List<InternetAddress> bccList = EmailUtils.getAddress(bcc);
            result.setBccList(bccList);

            // 回复邮件时的收件人
            String replyTo = EmailFileConfig.getValue(EmailConfigEnum.REPLY_TO);
            List<InternetAddress> replyToList = EmailUtils.getAddress(replyTo);
            result.setReplyToList(replyToList);
            return result;
        }
        catch (Exception e) {
            log.error("获取配置邮件信息错误", e);
            return null;
        }
    }

    /**
     * 获取模板配置的信息
     *
     * @return
     */
    public static TemplateEmailInformation getTemplateEmailInformation() {
        TemplateEmailInformation result = new TemplateEmailInformation();
        EmailInformation emailInformation = getConfigEmailInformation();
        BeanUtils.copyProperties(emailInformation, result);
        return result;
    }

}
