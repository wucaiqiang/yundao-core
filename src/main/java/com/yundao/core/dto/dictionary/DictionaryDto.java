package com.yundao.core.dto.dictionary;

import com.yundao.core.base.model.BaseModel;

/**
 * 数据字典
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class DictionaryDto extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 父ID
	 */
	private Integer parentId;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 状态
	 */
	private Integer status;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
