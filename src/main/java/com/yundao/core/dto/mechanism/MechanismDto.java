package com.yundao.core.dto.mechanism;

import java.io.Serializable;

/**
 * 机构
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class MechanismDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 机构id
	 */
	private Integer id;

	/**
	 * 机构类型编码
	 */
	private String typeCode;

	/**
	 * 机构名字
	 */
	private String name;

	/**
	 * 机构简称
	 */
	private String simpleName;

	/**
	 * 机构简介
	 */
	private String introduce;

	/**
	 * 投研团队
	 */
	private String researchTeam;

	/**
	 * 机构规模编码
	 */
	private String scaleCode;

	/**
	 * 机构成立日期
	 */
	private String creationDate;

	/**
	 * 机构logo
	 */
	private String logo;

	/**
	 * 省编码
	 */
	private String provinceCode;

	/**
	 * 市编码
	 */
	private String cityCode;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 营业执照
	 * 
	 */
	private String businessLicence;

	/**
	 * 组织机构照
	 * 
	 */
	private String orgCodeCertificate;

	/**
	 * 是否发行机构
	 */
	private Integer isIssueMechanism;
	
	/**
	 * 机构类型
	 */
	private Integer type;
	
	/**
	 * 管理员姓名
	 */
	private String adminName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getScaleCode() {
		return scaleCode;
	}

	public void setScaleCode(String scaleCode) {
		this.scaleCode = scaleCode;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResearchTeam() {
		return researchTeam;
	}

	public void setResearchTeam(String researchTeam) {
		this.researchTeam = researchTeam;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getOrgCodeCertificate() {
		return orgCodeCertificate;
	}

	public void setOrgCodeCertificate(String orgCodeCertificate) {
		this.orgCodeCertificate = orgCodeCertificate;
	}

	public Integer getIsIssueMechanism() {
		return isIssueMechanism;
	}

	public void setIsIssueMechanism(Integer isIssueMechanism) {
		this.isIssueMechanism = isIssueMechanism;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
}
