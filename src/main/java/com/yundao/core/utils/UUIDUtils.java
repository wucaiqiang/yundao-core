package com.yundao.core.utils;

import sun.applet.Main;

import java.util.UUID;

/**
 * uuid工具类
 * 
 * @author wupengfei wupf86@126.com
 */
public abstract class UUIDUtils {

	/**
	 * 获取uuid字符串
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	public static void main(String[] args){
		System.out.print(UUIDUtils.getUUID());
	}
}
