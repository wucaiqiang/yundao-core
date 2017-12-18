package com.yundao.core.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 主键
	 */
	private Long tenantId;

	/**
	 * 创建人id
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新人id
	 */
	private Long updateUserId;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 是否启用 0 否 1是，默认1
	 */
	private Integer isEnabled;

	/**
	 * 是否删除 0 否  1是 ，默认0
	 */
	private Integer isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
