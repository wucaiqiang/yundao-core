package com.yundao.core.dto.user;

import java.io.Serializable;

/**
 * 用户信息
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 机构id
	 */
	private Integer mechanismId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 身份证
	 */
	private String idCard;

	/**
	 * 性别 0：未知；1：男；2：女'
	 */
	private Integer sex;

	/**
	 * 生日
	 */
	private String birth;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户状态
	 */
	private Integer userStatus;

	/**
	 * 头像
	 */
	private String picture;

	/**
	 * 用户类型
	 */
	private Integer userType;

	/**
	 * 部门名称
	 */
	private String organizationName;

	/**
	 * 机构名称
	 */
	private String mechanismName;

	/**
	 * 机构简称
	 */
	private String mechanismSimpleName;

	/**
	 * 注册来源
	 */
	private String registerResource;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(Integer mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getMechanismName() {
		return mechanismName;
	}

	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}

	public String getMechanismSimpleName() {
		return mechanismSimpleName;
	}

	public void setMechanismSimpleName(String mechanismSimpleName) {
		this.mechanismSimpleName = mechanismSimpleName;
	}

	public String getRegisterResource() {
		return registerResource;
	}

	public void setRegisterResource(String registerResource) {
		this.registerResource = registerResource;
	}

}
