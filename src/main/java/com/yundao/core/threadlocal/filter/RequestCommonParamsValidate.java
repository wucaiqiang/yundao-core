package com.yundao.core.threadlocal.filter;

import com.yundao.core.ed.config.EDConfigEnum;
import com.yundao.core.log.Log;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.code.config.CommonCode;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.ed.Md5Utils;
import com.yundao.core.ed.config.EDFileConfig;
import com.yundao.core.exception.BaseException;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.config.ThreadLocalConfigEnum;
import com.yundao.core.threadlocal.config.ThreadLocalFileConfig;
import com.yundao.core.utils.StringBuilderUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 请求时的共用参数验证类
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class RequestCommonParamsValidate {

	private static Log log = LogFactory.getLog(RequestCommonParamsValidate.class);

	/**
	 * 验证参数
	 * 
	 * @param params
	 * @throws BaseException
	 */
	public static void validate(RequestCommonParams params) throws BaseException {
		validateId(params.getId());
		validateVersion(params.getVersion());
		validateTime(params.getTime());
		validateSignType(params.getSignType());
		validateSign(params);
	}

	/**
	 * 获取加密值
	 * 
	 * @param params
	 * @return
	 */
	public static String getSign(RequestCommonParams params) {
		// 获取参数名并排序
		List<String> nameList = new ArrayList<String>();
		Map<String, Object> methodParams = params.getMethodParams();
		if (!BooleanUtils.isEmpty(methodParams)) {
			for (Map.Entry<String, Object> entry : methodParams.entrySet()) {
				nameList.add(entry.getKey());
			}
			Collections.sort(nameList);
		}

		// 拼装参数名和参数值
		StringBuilder queryString = new StringBuilder();
		for (String name : nameList) {
			Object value = methodParams.get(name);
			queryString.append(name).append(CommonConstant.EQUALS).append(value).append(CommonConstant.AND);
		}
		StringBuilderUtils.deleteLastChar(queryString);

		// 计算md5
		String result = getMd5(params, queryString.toString());
		return result;
	}

	/**
	 * 获取请求方法为get时的加密值
	 * 
	 * @param params
	 * @return
	 */
	public static String getGetSign(RequestCommonParams params) {
		return getSign(params);
	}

	/**
	 * 验证系统标识
	 * 
	 * @param id
	 * @throws BaseException
	 */
	private static void validateId(String id) throws BaseException {
		log.info("系统标识id=" + id);
		if (!EDConfigEnum.isValid(id)) {
			throw new BaseException(CommonCode.COMMON_1009);
		}
	}

	/**
	 * 验证版本号是否合法
	 * 
	 * @param version
	 * @throws BaseException
	 */
	private static void validateVersion(String version) throws BaseException {
		log.info("系统版本号version=" + version);
	}

	/**
	 * 验证请求时间是否过期
	 * 
	 * @param time
	 * @throws BaseException
	 */
	private static void validateTime(Long time) throws BaseException {
		log.info("请求访问的时间time=" + time);

		// 是否需要校验
		String isValid = ThreadLocalFileConfig.getValue(ThreadLocalConfigEnum.IS_VALIDATE_REQUEST_TIME);
		if (CommonConstant.ZERO == NumberUtils.toInt(isValid)) {
			return;
		}

		int expire = NumberUtils.toInt(ThreadLocalFileConfig.getValue(ThreadLocalConfigEnum.REQUEST_TIME_EXPIRE));
		if (time == null || time + expire * 1000 < System.currentTimeMillis()) {
			throw new BaseException(CommonCode.COMMON_1011);
		}
	}

	/**
	 * 验证加密类型
	 * 
	 * @param signType
	 * @throws BaseException
	 */
	private static void validateSignType(String signType) throws BaseException {
		log.info("加密类型signType=" + signType);
		if (BooleanUtils.isBlank(signType)) {
			return;
		}
		String[] signTypes = ThreadLocalFileConfig.getValue(ThreadLocalConfigEnum.REQUEST_SIGN_TYPE)
				.split(CommonConstant.COMMA_SEMICOLON);
		boolean isMatch = false;
		for (String each : signTypes) {
			if (each.equals(signType)) {
				isMatch = true;
				break;
			}
		}
		if (!isMatch) {
			throw new BaseException(CommonCode.COMMON_1012);
		}
	}

	/**
	 * 验证加密值
	 * 
	 * @param params
	 * @throws BaseException
	 */
	private static void validateSign(RequestCommonParams params) throws BaseException {
		String signature = getSign(params);
		doValidateSign(params.getSign(), signature);
	}

	private static void doValidateSign(String frontSign, String backendSign) throws BaseException {
		if (backendSign != null && !backendSign.equals(frontSign)) {
			log.info("请求的加密值sign=" + frontSign + ",后台计算的加密值signature=" + backendSign);
			throw new BaseException(CommonCode.COMMON_1013);
		}
	}

	/**
	 * 获取md5值
	 * 
	 * @param params
	 * @param queryString
	 * @return
	 */
	private static String getMd5(RequestCommonParams params, String queryString) {
		String id = params.getId();
		Long time = params.getTime();
		String nonce = params.getNonce();

		// 获取参数的加密字符串
		StringBuilder signBuilder = new StringBuilder();
		signBuilder.append(params.getVersion()).append(CommonConstant.AND);
		signBuilder.append(id).append(CommonConstant.AND);
		if (time != null && time != 0) {
			signBuilder.append(time).append(CommonConstant.AND);
		}
		if (!BooleanUtils.isBlank(nonce)) {
			signBuilder.append(nonce).append(CommonConstant.AND);
		}

		// 获取加密配置
		if (!EDConfigEnum.isValid(id)) {
			log.info("系统标识没有配置id=" + id);
			return null;
		}
		signBuilder.append(EDFileConfig.getValue(id)).append(CommonConstant.AND);
		signBuilder.append(queryString);

		String sign = signBuilder.toString();
		log.info("后台计算加密串sign=" + sign);
		String result = Md5Utils.md5(sign);
		return result;
	}

}
