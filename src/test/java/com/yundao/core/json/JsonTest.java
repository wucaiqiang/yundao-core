package com.yundao.core.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yundao.core.utils.JsonUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.yundao.core.code.Result;
import com.yundao.core.json.jackson.type.BaseTypeReference;

/**
 * json测试类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class JsonTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testJson() throws Exception {
		String sku = "[{\"nameId\":1, \"values\":[{\"id\": 2, \"sequence\" : 1, \"state\" : 1}, {\"id\": 3, \"sequence\" : 2, \"state\" : 1}]}, {\"nameId\":2, \"values\":[{\"id\": 4, \"sequence\" : 3, \"state\" : 1}]}]";
		JavaType javaType = JsonUtils.getCollectionType(ArrayList.class, Product.class);
		List<Product> list = JsonUtils.jsonToObject(sku, javaType);
		for (Product each : list) {
			System.out.println(each.getNameId() + ":" + each.getValues());
		}

		sku = "{\"nameId\":\"1\", \"values\":\"1test\"}";
		Map<String, String> map = JsonUtils.jsonToObject(sku, Map.class);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}

		sku = "[\"123\", \"acd2\", \"3ddee\"]";
		List<String> list2 = JsonUtils.jsonToObject(sku, List.class);
		for (String each : list2) {
			System.out.println(each);
		}

		SendResult sr = new SendResult(true, "0", "1", "成功");
		System.out.println(JsonUtils.objectToJson(sr));

		sku = "{\"code\":1007,\"success\":false,\"message\":\"系统异常，请稍后重试\",\"result\":" + JsonUtils.objectToJson(sr)
				+ "}";
		Result<SendResult> result = JsonUtils.jsonToObject(sku, new BaseTypeReference<Result<SendResult>>(){});
		System.out.println(result);
	}
}

class SendResult {

	private boolean isSuccess;
	private String myCode;
	private String thirdPartyCode;
	private String message;

	public SendResult() {

	}

	public SendResult(boolean isSuccess, String myCode, String thirdPartyCode, String message) {
		this.isSuccess = isSuccess;
		this.myCode = myCode;
		this.thirdPartyCode = thirdPartyCode;
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getMyCode() {
		return myCode;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public String getMessage() {
		return message;
	}
}

class Product implements Serializable {

	private static final long serialVersionUID = -8765372157007343796L;
	private int nameId;
	private List<ValueList> values;

	public static class ValueList implements Serializable {
		private static final long serialVersionUID = -5945046405823195262L;
		private int id;
		private int sequence;
		private int state;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getSequence() {
			return sequence;
		}

		public void setSequence(int sequence) {
			this.sequence = sequence;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String toString() {
			return id + ":" + sequence + ":" + state;
		}
	}

	public int getNameId() {
		return nameId;
	}

	public void setNameId(int nameId) {
		this.nameId = nameId;
	}

	public List<ValueList> getValues() {
		return values;
	}

	public void setValues(List<ValueList> values) {
		this.values = values;
	}

}