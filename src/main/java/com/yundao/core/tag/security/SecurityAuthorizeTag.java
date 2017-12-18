package com.yundao.core.tag.security;

import java.util.Map;

import com.yundao.core.tag.AbstractTag;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.base.service.BaseService;
import com.yundao.core.constant.CommonConstant;

/**
 * 安全授权标签
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SecurityAuthorizeTag extends AbstractTag {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 校验链接
	 */
	private String url;

	/**
	 * 任一链接有权限即可显示
	 */
	private String anyUrl;

	/**
	 * 授权资源，若不为空则用此权限
	 */
	private Map<String, Boolean> authorizeMap;

	@SuppressWarnings("unchecked")
	@Override
	public int startTag() throws Exception {
		int result = SKIP_BODY;

		// 校验参数
		boolean isUrlBlank = BooleanUtils.isBlank(url);
		if (userId == null || (isUrlBlank && BooleanUtils.isBlank(anyUrl))) {
			return result;
		}
		String checkUrl = isUrlBlank ? anyUrl : url;

		// 获取链接权限
		if (BooleanUtils.isEmpty(authorizeMap)) {
			authorizeMap = (Map<String, Boolean>) ThreadLocalUtils.getFromRequest(CommonConstant.AUTHORIZE_MAP);
			if (BooleanUtils.isEmpty(authorizeMap)) {
				BaseService service = BaseService.getBaseService();
				authorizeMap = service.getAuthorizeUrl(userId.toString(), checkUrl);
			}
		}

		// 执行链接校验
		String[] anyUrls = checkUrl.split(CommonConstant.COMMA_SEMICOLON);
		int length = anyUrls.length;
		for (int i = 0; i < length; i++) {
			String each = anyUrls[i];
			int eachLength = each.length() - 1;
			boolean isEndSeparator = each != null && each.charAt(eachLength) == '/';
			if (authorizeMap.containsKey(each)
					|| (isEndSeparator && authorizeMap.containsKey(each.substring(0, eachLength)))
					|| (!isEndSeparator && authorizeMap.containsKey(each + "/"))) {
				result = EVAL_BODY_INCLUDE;
				break;
			}
		}
		return result;
	}

	@Override
	public void destroy() {
		userId = null;
		url = null;
		anyUrl = null;
		authorizeMap = null;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAnyUrl(String anyUrl) {
		this.anyUrl = anyUrl;
	}

	public void setAuthorizeMap(Map<String, Boolean> authorizeMap) {
		this.authorizeMap = authorizeMap;
	}

}
