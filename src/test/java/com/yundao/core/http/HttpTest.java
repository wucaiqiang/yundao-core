package com.yundao.core.http;

import com.yundao.core.threadlocal.filter.RequestCommonParams;
import com.yundao.core.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Http测试类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class HttpTest {

	// @Test
	public void testHttp() throws Exception {
		Map<String, String> methodParams = new HashMap<String, String>();
		methodParams.put("ip", "192");
		methodParams.put("userId", "6");
		methodParams.put("type", "6");
		methodParams.put("beforeContent", "6");
		methodParams.put("afterContent", "6");
		RequestCommonParams requestParams = RequestCommonParams.newDefault(methodParams);

		System.out.println(HttpUtils.get("http://localhost:8080/ubs/log/insert", requestParams));
	}
}
