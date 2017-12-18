package com.yundao.core.email;

/**
 * 邮件配置文件枚举类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public enum EmailConfigEnum {

	/**
	 * 传输类
	 */
	SMTP_CLASS("smtp.class"),

	/**
	 * 会话debug的开关，默认false
	 */
	SESSION_DEBUG("session.debug"),

	/**
	 * 传输协议
	 */
	TRANSPORT_PROTOCOL("transport.protocol"),

	/**
	 * 主机
	 */
	SMTP_HOST("smtp.host"),

	/**
	 * 发件人
	 */
	SMTP_FROM("smtp.from"),

	/**
	 * 端口号
	 */
	SMTP_PORT("smtp.port"),

	/**
	 * 是否校验账户
	 */
	SMTP_AUTH("smtp.auth"),

	/**
	 * 显示的发件人名字
	 */
	SENDER_NAME("sender.name"),

	/**
	 * 主用户名，多个时以逗号分隔
	 */
	SENDER_USERNAME_MASTER("sender.username.master"),

	/**
	 * 主密码，多个时以逗号分隔
	 */
	SENDER_PASSWORD_MASTER("sender.password.master"),

	/**
	 * 备份用户名，多个时以逗号分隔
	 */
	SENDER_USERNAME_SLAVE("sender.username.slave"),

	/**
	 * 备份密码，多个时以逗号分隔
	 */
	SENDER_PASSWORD_SLAVE("sender.password.slave"),

	/**
	 * 邮件标题前辍
	 */
	SUBJECT_PREFIX("subject.prefix"),

	/**
	 * 收件人
	 */
	TO("to"),

	/**
	 * 抄送
	 */
	CC("cc"),

	/**
	 * 密送
	 */
	BCC("bcc"),

	/**
	 * 回复时的收件人
	 */
	REPLY_TO("reply.to");

	private String key;

	private EmailConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
