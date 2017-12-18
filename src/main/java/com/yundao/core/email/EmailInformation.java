package com.yundao.core.email;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件信息
 *
 * @author wupengfei wupf86@126.com
 *
 */
public class EmailInformation {

	/**
	 * 发件人
	 */
	private InternetAddress from;

	/**
	 * 发件人姓名
	 */
	private String senderName;

	/**
	 * 标题
	 */
	private String subject;

	/**
	 * html内容
	 */
	private String htmlContent;

	/**
	 * text内容
	 */
	private String textContent;

	/**
	 * 附件
	 */
	private List<File> attachmentList = new ArrayList<>();

	/**
	 * 回复邮件时的收件人
	 */
	private List<InternetAddress> replyToList;

	/**
	 * 收件人
	 */
	private List<InternetAddress> toList;

	/**
	 * 抄送
	 */
	private List<InternetAddress> ccList;

	/**
	 * 密送
	 */
	private List<InternetAddress> bccList;

	public InternetAddress getFrom() {
		return from;
	}

	public void setFrom(InternetAddress from) {
		this.from = from;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public List<File> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<File> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<InternetAddress> getReplyToList() {
		return replyToList;
	}

	public void setReplyToList(List<InternetAddress> replyToList) {
		this.replyToList = replyToList;
	}

	public List<InternetAddress> getToList() {
		return toList;
	}

	public void setToList(List<InternetAddress> toList) {
		this.toList = toList;
	}

	public List<InternetAddress> getCcList() {
		return ccList;
	}

	public void setCcList(List<InternetAddress> ccList) {
		this.ccList = ccList;
	}

	public List<InternetAddress> getBccList() {
		return bccList;
	}

	public void setBccList(List<InternetAddress> bccList) {
		this.bccList = bccList;
	}

}
