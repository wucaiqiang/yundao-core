package com.yundao.core.email;

import com.yundao.core.exception.BaseException;
import org.junit.Test;

import javax.mail.internet.InternetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 邮件测试类
 *
 * @author wupengfei wupf86@126.com
 */
public class EmailTest {

    @Test
    public void testSendTemplateEmail() throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        //params.put("environment", "【测试环境】");
        params.put("systemName", "core测试");
        params.put("title", "测试标题");
        params.put("message", "测试内容");
        TemplateEmailUtils.asyncSendEmail("xxx@zcmall.com", "system_error_message", params);
    }

    @Test
    public void testSendEmail() {
        TemplateEmailInformation emailInformation = EmailUtils.getTemplateEmailInformation();

        // 触发邮件发送
        emailInformation.setCode("ProductNotice");
        emailInformation.addParameter("username", "小王").addParameter("productname", "产品一号").addParameter("money", "50");

        // 批量邮件发送
//        emailInformation.setCode("new_product_online_notify");
//        emailInformation.setType(TemplateEmailInformation.BATCH);
//        emailInformation.addParameter("content", "你好，这是测试邮件");

        emailInformation.setCcList(EmailUtils.getAddress("wupengfei@zcmall.com"));
        emailInformation.setToList(EmailUtils.getAddress("wupengfei@zcmall.com"));
        boolean result = TemplateEmailSender.sendEmail(emailInformation);
        System.out.println(result);
    }

    @Test
    public void testSendHtmlEmail() throws BaseException {
        TemplateEmailInformation emailInformation = EmailUtils.getTemplateEmailInformation();
        emailInformation.setType(TemplateEmailInformation.BATCH);
        emailInformation.setCode("product_establish_remind");
        emailInformation.addParameter("dateSellEnd", "2016-09-19");
        emailInformation.addParameter("dayCount", "10");
        emailInformation.addParameter("proShortName", "赚钱");
        emailInformation.addParameter("proTypeValue", "固收");

        List<InternetAddress> address = EmailUtils.getAddress("xx@qq.com");
        emailInformation.setToList(EmailUtils.getAddress("xx@zcmall.com"));
        emailInformation.setCcList(address);
        boolean result = TemplateEmailSender.sendEmail(emailInformation);
        System.out.println(result);
    }

    // @Test
    // public void testAsyncSendEmail() throws Exception {
    // EmailInformation email = this.getEmail();
    // FutureTask<Boolean> result = EmailSender.asyncSendEmail(email);
    // Assert.assertSame(result.get(), true);
    // }

    private EmailInformation getEmail() {
        EmailInformation result = EmailUtils.getConfigEmailInformation();
        result.setToList(EmailUtils.getAddress("wupengfei@zcmall.com"));
        result.setSubject("找回密码");
        result.setHtmlContent("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""
                + " style=\"margin:0 auto; padding: 20px 0;\" width=\"700\">"
                + "\n\t<tbody>\n\t\t<tr>\n\t\t\t<td width=\"700\">&nbsp; &nbsp; "
                + "尊敬的wupf86注册客户： 我们收到您关于找回的请求。请您点击该链接以找回你的用户密码：http://zcmall.com &nbsp; &nbsp; "
                + "此邮件由系统自动发送，请勿直接回复 感谢您选择zcmall，我们一直在努力 zcmall客户服务部</td>\n\t\t</tr>\n\t</tbody>\n</table>\n\n<p>&nbsp;</p>\n");
        // result.setTextContent("尊敬的wupf86注册客户：
        // 我们收到您关于找回的请求。请您点击该链接以找回你的用户密码：http://zcmall.com "
        // + " 此邮件由系统自动发送，请勿直接回复 感谢您选择zcmall，我们一直在努力 zcmall客户服务部");

        // List<File> attachmentList = Lists.newArrayList();
        // attachmentList.add(new
        // File("C:/Users/Administrator//Desktop/map.png"));
        // attachmentList.add(new
        // File("C:/Users/Administrator//Desktop/map.png"));
        // result.setAttachmentList(attachmentList);
        return result;
    }

}
