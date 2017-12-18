package com.yundao.core.utils;

import java.math.BigInteger;

import com.yundao.core.constant.CommonConstant;

/**
 * 层级编码工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class HierarchyCodeUtils {

	/**
	 * 获取层级编码
	 * 
	 * @param peerCode
	 * @param parentId
	 * @param parentCode
	 * @return
	 */
	public static String getCode(String peerCode, Long parentId, String parentCode) {
		String result = CommonConstant.ROOT_CODE;
		if (!BooleanUtils.isEmpty(peerCode)) {
			result = new BigInteger(peerCode).add(new BigInteger(String.valueOf(CommonConstant.ONE))).toString();
		}
		else if (parentId != null && CommonConstant.ROOT_ID != parentId) {
			result = parentCode + CommonConstant.ROOT_CODE;
		}
		return result;
	}
}
