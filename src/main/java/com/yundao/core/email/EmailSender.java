package com.yundao.core.email;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.utils.ThreadUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 发送邮件
 *
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class EmailSender {

	private static Log log = LogFactory.getLog(EmailSender.class);

	private static Properties props;
	private static AtomicLong failureCount = new AtomicLong(0);
	private static AtomicLong successCount = new AtomicLong(0);

	static {
		props = new Properties();
		props.put("mail.smtp.host", EmailFileConfig.getValue(EmailConfigEnum.SMTP_HOST));
		props.put("mail.smtp.from", EmailFileConfig.getValue(EmailConfigEnum.SMTP_FROM));
		props.put("mail.smtp.port", EmailFileConfig.getValue(EmailConfigEnum.SMTP_PORT));
		props.put("mail.smtp.class", EmailFileConfig.getValue(EmailConfigEnum.SMTP_CLASS));
		props.put("mail.mime.encodefilename", "true");
	}

	/**
	 * 设置发送邮件的属性
	 * 
	 * @param p
	 */
	public static void setProperties(Properties p) {
		if (p == null) {
			log.info("邮件的属性为空");
			return;
		}
		props = p;
	}

	/**
	 * 发送邮件
	 * 
	 * @param email
	 * @return
	 */
	public static boolean sendEmail(EmailInformation email) {
		return sendEmail(email, null);
	}

	/**
	 * 异步发送邮件
	 * 
	 * @param email
	 * @return
	 */
	public static FutureTask<Boolean> asyncSendEmail(EmailInformation email) {
		return asyncSendEmail(email, null);
	}

	/**
	 * 异步发送邮件
	 * 
	 * @param email
	 * @param auth
	 * @return
	 */
	public static FutureTask<Boolean> asyncSendEmail(EmailInformation email, Authenticator auth) {
		if (email == null) {
			log.info("邮件为空");
			return null;
		}

		Callable<Boolean> emailCallable = new EmailCallable(email, auth);
		FutureTask<Boolean> futureTask = new FutureTask<Boolean>(emailCallable);
		ThreadUtils.execute(futureTask);
		return futureTask;
	}

	/**
	 * 发送邮件
	 * 
	 * @param email
	 * @param auth
	 * @return
	 */
	public static boolean sendEmail(EmailInformation email, Authenticator auth) {
		log.begin(email);
		if (email == null) {
			log.info("邮件为空");
			return false;
		}

		int retry = 1;
		boolean result = false;
		while (retry <= 2 && !result) {
			try {
				if ("true".equalsIgnoreCase(EmailFileConfig.getValue(EmailConfigEnum.SMTP_AUTH))) {
					if (auth == null) {
						props.put("mail.smtp.auth", "true");
						auth = EmailUtils.getAuthenticator(EmailConfigEnum.SENDER_USERNAME_MASTER,
								EmailConfigEnum.SENDER_PASSWORD_MASTER, 0);
					}
					else if (retry == 2) {
						auth = EmailUtils.getAuthenticator(EmailConfigEnum.SENDER_USERNAME_SLAVE,
								EmailConfigEnum.SENDER_PASSWORD_SLAVE, 0);
					}
				}
				else {
					props.remove("mail.smtp.auth");
				}
				result = doSendEmail(email, auth);
				if (result) {
					successCount.getAndIncrement();
				}
			}
			catch (Exception e) {
				failureCount.getAndIncrement();
				log.error("发送邮件失败" + ((retry == 1) ? "，用备份用户名和密码重试一次" : ""), e);
			}
			retry++;
		}

		log.end();
		return result;
	}

	/**
	 * 获取当前发送邮件失败的数量
	 * 
	 * @return
	 */
	public static long getFailureCount() {
		return failureCount.get();
	}

	/**
	 * 获取当前发送邮件成功的数量
	 * 
	 * @return
	 */
	public static long getSuccessCount() {
		return successCount.get();
	}

	/**
	 * 重置当前发送邮件失败和成功的数量
	 */
	public static void resetCount() {
		failureCount = new AtomicLong(0);
		successCount = new AtomicLong(0);
	}

	/**
	 * 发送邮件
	 * 
	 * @param email
	 * @param auth
	 * @throws Exception
	 */
	private static boolean doSendEmail(EmailInformation email, Authenticator auth) throws Exception {
		// 获取会话
		Session session = Session.getInstance(props, auth);
		session.setDebug(Boolean.valueOf(EmailFileConfig.getValue(EmailConfigEnum.SESSION_DEBUG)));

		MimeMessage message = getMimeMessage(email, session);
		if (message == null) {
			log.info("发送的邮件信息为空");
			return false;
		}

		Transport transport = session.getTransport(EmailFileConfig.getValue(EmailConfigEnum.TRANSPORT_PROTOCOL));
		if (transport.isConnected() == false) {
			transport.connect();
		}
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		return true;
	}

	/**
	 * 获取发送邮件的内容
	 * 
	 * @param email
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private static MimeMessage getMimeMessage(EmailInformation email, Session session) throws Exception {
		// 校验邮件
		if (email == null || BooleanUtils.isEmpty(email.getToList()) || BooleanUtils.isBlank(email.getSubject())
				|| (BooleanUtils.isBlank(email.getTextContent()) && BooleanUtils.isBlank(email.getHtmlContent()))) {
			log.info("发送的邮件内容不正确");
			return null;
		}

		MimeMessage message = new MimeMessage(session);

		// 设置邮件标题
		String subject = email.getSubject();
		message.setSubject(EmailFileConfig.getValue(EmailConfigEnum.SUBJECT_PREFIX, "") + subject,
				CommonConstant.UTF_8);

		// 设置发件人
		InternetAddress from = email.getFrom();
		if (from == null) {
			log.info("发件人为空，从配置文件中获取默认发件人");
			List<InternetAddress> fromList = EmailUtils.getAddress(EmailFileConfig.getValue(EmailConfigEnum.SMTP_FROM));
			if (fromList != null && fromList.size() > 0) {
				from = fromList.get(0);
			}
		}
		if (from == null) {
			log.info("发件人为空");
			return null;
		}

		// 设置发件人姓名
		String senderName = email.getSenderName();
		if (BooleanUtils.isBlank(senderName)) {
			senderName = EmailFileConfig.getValue(EmailConfigEnum.SENDER_NAME);
		}
		if (!BooleanUtils.isBlank(senderName)) {
			from.setPersonal(senderName, CommonConstant.UTF_8);
		}
		message.setFrom(from);

		// 设置收件人
		List<InternetAddress> toList = email.getToList();
		String to = EmailFileConfig.getValue(EmailConfigEnum.TO);
		if (!BooleanUtils.isBlank(to)) {
			toList.addAll(EmailUtils.getAddress(to));
		}
		message.setRecipients(Message.RecipientType.TO, toList.toArray(new InternetAddress[0]));
		if (!BooleanUtils.isEmpty(email.getCcList())) {
			message.setRecipients(Message.RecipientType.CC, email.getCcList().toArray(new InternetAddress[0]));
		}
		if (!BooleanUtils.isEmpty(email.getBccList())) {
			message.setRecipients(Message.RecipientType.BCC, email.getBccList().toArray(new InternetAddress[0]));
		}

		// 设置回复时的收件人
		if (!BooleanUtils.isEmpty(email.getReplyToList())) {
			message.setReplyTo(email.getReplyToList().toArray(new InternetAddress[0]));
		}

		// 设置附件
		List<File> attachmentList = email.getAttachmentList();
		Multipart mp;
		if (!BooleanUtils.isEmpty(attachmentList)) {
			mp = new MimeMultipart();
		}
		else {
			mp = new MimeMultipart("alternative");
		}

		setTextContent(email.getTextContent(), message, mp);
		setHtmlContent(email.getHtmlContent(), mp);
		setAttachment(attachmentList, mp);

		message.setSentDate(new Date());
		message.setContent(mp);
		return message;
	}

	/**
	 * 设置文本内容
	 * 
	 * @param content
	 * @param message
	 * @param mp
	 * @throws Exception
	 */
	private static void setTextContent(String content, MimeMessage message, Multipart mp) throws Exception {
		if (BooleanUtils.isBlank(content)) {
			return;
		}
		message.setHeader("Content-Transfer-Encoding", "base64");

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(content, CommonConstant.UTF_8);
		mp.addBodyPart(bodyPart);
	}

	/**
	 * 设置html内容
	 * 
	 * @param content
	 * @param mp
	 * @throws Exception
	 */
	private static void setHtmlContent(String content, Multipart mp) throws Exception {
		if (BooleanUtils.isBlank(content)) {
			return;
		}

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setDataHandler(new DataHandler(
				new ByteArrayDataSource(content, "text/html; charset=\"" + CommonConstant.UTF_8 + "\"")));
		bodyPart.setHeader("Content-Transfer-Encoding", "base64");
		mp.addBodyPart(bodyPart);
	}

	/**
	 * 设置附件
	 * 
	 * @param attachments
	 * @param mp
	 * @throws Exception
	 */
	private static void setAttachment(List<File> attachments, Multipart mp) throws Exception {
		if (BooleanUtils.isEmpty(attachments)) {
			return;
		}

		for (File attachment : attachments) {
			String attachmentPath = attachment.getAbsolutePath();

			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setDataHandler(new DataHandler(new FileDataSource(attachmentPath)));
			bodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
			mp.addBodyPart(bodyPart);
		}
	}

	private static class EmailCallable implements Callable<Boolean> {

		private EmailInformation email;
		private Authenticator auth;

		public EmailCallable(EmailInformation email, Authenticator auth) {
			this.email = email;
			this.auth = auth;
		}

		@Override
		public Boolean call() throws Exception {
			return EmailSender.sendEmail(email, auth);
		}
	}
}


