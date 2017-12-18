package com.yundao.core.dto.role;

import java.io.Serializable;

/**
 * 角色
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class RoleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private Integer id;

	/**
	 * 组织id
	 */
	private Integer organizationId;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 是否管理员角色
	 */
	private Integer isAdmin;

	/**
	 * 创建者id
	 */
	private Integer creatorId;

	/**
	 * 机构id
	 */
	private Integer mechanismId;

	/**
	 * 0:手动创建的数据，1:默认初始化数据
	 */
	private Integer isInit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(Integer mechanismId) {
		this.mechanismId = mechanismId;
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

}
