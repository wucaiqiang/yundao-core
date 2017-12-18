package com.yundao.core.email.impl.sohu;

import com.yundao.core.email.TemplateEmailInformation;
import com.yundao.core.email.UsernamePassword;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.HttpUtils;
import com.yundao.core.utils.JsonUtils;
import com.yundao.core.utils.ListUtils;
import com.yundao.core.email.EmailConfigEnum;
import com.yundao.core.email.EmailFileConfig;
import com.yundao.core.email.EmailUtils;
import com.yundao.core.exception.BaseException;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.GeneralEmail;
import io.jstack.sendcloud4j.mail.Result;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用sohu发送邮件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class SohuEmailSender {

    protected static UsernamePassword usernameTrigger = EmailUtils.getUsernamePassword(EmailConfigEnum.SENDER_USERNAME_MASTER, EmailConfigEnum.SENDER_PASSWORD_MASTER, 0);
    protected static UsernamePassword usernameBatch = EmailUtils.getUsernamePassword(EmailConfigEnum.SENDER_USERNAME_SLAVE, EmailConfigEnum.SENDER_PASSWORD_SLAVE, 0);
    protected static SendCloud sendCloudTrigger = SendCloud.createWebApi(usernameTrigger.getUsername(), usernameTrigger.getPassword());
    protected static SendCloud sendCloudBatch = SendCloud.createWebApi(usernameBatch.getUsername(), usernameBatch.getPassword());

    private static Log log = LogFactory.getLog(SohuEmailSender.class);

    /**
     * 发送普通邮件
     *
     * @param emailInformation 模板信息
     * @return 是否发送成功
     */
    public static boolean sendEmail(TemplateEmailInformation emailInformation) {
        // 校验邮件
        if (emailInformation == null || BooleanUtils.isEmpty(emailInformation.getToList()) || (BooleanUtils.isBlank(emailInformation.getCode()) && BooleanUtils.isBlank(emailInformation.getTextContent()) && BooleanUtils.isBlank(emailInformation.getHtmlContent()))) {
            log.info("发送的邮件内容不正确");
            return false;
        }

        // 构造发送邮件
        String[] toArray = toArray(emailInformation.getToList());
        GeneralEmail email = Email.general().from(emailInformation.getFrom().getAddress()).fromName(emailInformation.getSenderName()).to(toArray);

        // 设置邮件标题
        String subject = emailInformation.getSubject();
        email.subject(EmailFileConfig.getValue(EmailConfigEnum.SUBJECT_PREFIX, "") + subject);

        // 获取发送的对象
        SendCloud sendCloud = getSendCloud(emailInformation.getType());

        // 设置邮件内容
        String code = emailInformation.getCode();
        if (!BooleanUtils.isBlank(code)) {
            try {
                // 获取邮件模板
                Map<String, String> params = new HashMap<>(4);
                params.put("api_user", sendCloud.apiUser());
                params.put("api_key", sendCloud.apiKey());
                params.put("invoke_name", code);
                String url = "http://sendcloud.sohu.com/webapi/template.get.json";
                SohuTemplates templates = JsonUtils.jsonToObject(HttpUtils.get(url, params), SohuTemplates.class);
                List<SohuTemplates.SohuTemplate> templateList = templates.getTemplateList();
                if (templateList == null || templateList.size() != 1) {
                    log.info("根据模板编码没有查询到模板code=" + code);
                    return false;
                }

                // 设置邮件内容
                SohuTemplates.SohuTemplate sohuTemplate = templateList.get(0);
                email.html(formatContent(sohuTemplate.getHtml(), emailInformation.getParamMap()));

                // 设置邮件标题
                email.subject(formatContent(sohuTemplate.getSubject(), emailInformation.getParamMap()));
            }
            catch (BaseException e) {
                log.error("根据模板编码获取模板内容失败", e);
                return false;
            }
        }
        else if (!BooleanUtils.isBlank(emailInformation.getHtmlContent())) {
            email.html(emailInformation.getHtmlContent());
        }
        else {
            email.plain(emailInformation.getTextContent());
        }

        // 是否有抄送,若收件人列表和抄送人列表都存在同一邮箱，则删除抄送人
        List<InternetAddress> ccList = removeCCSameEmail(emailInformation.getToList(), emailInformation.getCcList());
        String[] ccArray = toArray(ccList);
        if (ccArray != null && ccArray.length > 0) {
            email.cc(ccArray);
        }
        Result result = sendCloud.mail().send(email);

        log.info("发送结果" + result.isSuccess() + "," + result.getMessage());
        return result.isSuccess();
    }

    /**
     * 根据邮件类型获取发送邮件的对象
     *
     * @param emailType 模板类型 0：批量，1：触发
     * @return 发送邮件的对象
     */
    public static SendCloud getSendCloud(int emailType) {
        return emailType == TemplateEmailInformation.TRIGGER ? sendCloudTrigger : sendCloudBatch;
    }

    /**
     * 转换邮件列表为数组
     *
     * @param emailList 邮件列表
     * @return 邮件数组
     */
    public static String[] toArray(List<InternetAddress> emailList) {
        if (emailList != null) {
            List<String> collect = emailList.stream().map(InternetAddress::getAddress).collect(Collectors.toList());
            return collect.toArray(new String[collect.size()]);
        }
        return null;
    }

    /**
     * 格式化内容
     *
     * @param content 待格式化的内容%param%
     * @param params  参数
     * @return 替换对应参数后的值
     */
    public static String formatContent(String content, Map<String, String> params) {
        String result = content;
        if (!BooleanUtils.isBlank(content) && !BooleanUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result = result.replace("%" + entry.getKey() + "%", entry.getValue());
            }
        }
        return result;
    }

    private static List<InternetAddress> removeCCSameEmail(List<InternetAddress> toList, List<InternetAddress> ccList) {
        List<InternetAddress> result = new ArrayList<>();
        int ccSize = ListUtils.getSize(ccList);
        for (int j = 0; j < ccSize; j++) {
            InternetAddress ccEach = ccList.get(j);
            if (!toList.contains(ccEach)) {
                result.add(ccEach);
            }
        }
        return result;
    }

}


