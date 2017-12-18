package com.yundao.core.constant;

/**
 * 头部静态
 *
 * @author wupengfei wupf86@126.com
 */
public interface HeaderConstant {
	/**
	 * 用户ID
	 */
	String HEADER_USER_ID = "headerUserId";
	/**
	 * 用户名称
	 */
	String HEADER_REAL_NAME = "headerRealName";
	/**
	 * 租户ID
	 */
	String HEADER_TENANT_ID = "headerTenantId";
	/**
	 * 应用类型，pc或app
	 */
	String HEADER_APP_TYPE = "appType";
	/**
	 * 用户在租户中的角色，多种角色逗号分开
	 */
	String USER_ROLE="userRole";
	/**
	 * 请求是否异步
	 */
	String REQUEST_IS_ASYNC = "requestIsAsync";

	/**
	 * 请求的跟踪标识
	 */
	String REQUEST_TRACE_ID = "requestTraceId";

	/**
	 * 请求的版本号
	 */
	String REQUEST_VERSION = "requestVersion";

	/**
	 * 请求的标识
	 */
	String REQUEST_ID = "requestId";

	/**
	 * 请求的加密类型
	 */
	String REQUEST_SIGN_TYPE = "requestSignType";

	/**
	 * 请求的加密值
	 */
	String REQUEST_SIGN = "requestSign";

	/**
	 * 请求的时间
	 */
	String REQUEST_TIME = "requestTime";

	/**
	 * 请求随机数
	 */
	String REQUEST_NONCE = "requestNonce";

	/**
	 * 请求的ip
	 */
	String REQUEST_IP = "requestIp";

	/**
	 * 获取来源url
	 */
	String ORIGIN = "origin";

	/**
	 * 获取上级url
	 */
	String REFERER = "Referer";

	/**
	 * 设备信息
	 */
	String USER_AGENT = "user-agent";

	/**
	 * 内容长度
	 */
	String CONTENT_LENGTH = "Content-Length";

	/**
	 * 渠道号
	 */
	String CHANNEL_NUMBER = "X-Request-ChannelNumber";

	/**
	 * 网络类型
	 */
	String NETWORK_TYPE = "X-Request-NetworkType";

	/**
	 * 设备id
	 */
	String DEVICE_ID = "X-Request-DeviceId";

	/**
	 * 设备名称
	 */
	String DEVICE_NAME = "X-Request-DeviceName";

	/**
	 * 设备型号
	 */
	String DEVICE_MODEL = "X-Request-DeviceModel";

	/**
	 * 厂商
	 */
	String VENDOR = "X-Request-Vendor";

	/**
	 * 操作系统
	 */
	String DEVICE_OS = "X-Request-DeviceOS";

	/**
	 * app版本号
	 */
	String APP_VERSION = "X-Request-AppVersion";

	/**
	 * 尺寸
	 */
	String DEVICE_RESOLUTION = "X-Request-DeviceResolution";

	/**
	 * imei
	 */
	String IMEI = "X-Request-IMEI";

}
