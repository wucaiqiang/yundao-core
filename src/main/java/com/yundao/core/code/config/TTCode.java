package com.yundao.core.code.config;

/**
 * 定时任务服务编码
 *
 * @author wupengfei wupf86@126.com
 *
 */
public interface TTCode {

	/**
	 * 请先配置任务的工作组
	 */
	int TT_8000 = 8000;

	/**
	 * 请先配置任务的触发器组
	 */
	int TT_8001 = 8001;

	/**
	 * 任务的类路径有误
	 */
	int TT_8002 = 8002;

	/**
	 * 启动任务失败
	 */
	int TT_8003 = 8003;

	/**
	 * 暂停任务失败
	 */
	int TT_8004 = 8004;

	/**
	 * 恢复任务失败
	 */
	int TT_8005 = 8005;

	/**
	 * 删除任务失败
	 */
	int TT_8006 = 8006;

	/**
	 * 关闭任务失败
	 */
	int TT_8007 = 8007;

	/**
	 * 任务组ID有误
	 */
	int TT_8008 = 8008;
}
