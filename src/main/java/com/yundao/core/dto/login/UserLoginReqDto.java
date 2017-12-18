package com.yundao.core.dto.login;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 用户登录请求
 * 
 * @author 欧阳利
 * 2017年6月26日
 */
public class UserLoginReqDto implements Serializable{
	
	 /**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
    private String userName;
    
    /**
     * 用户密码
     */
	@ApiModelProperty(value = "密码")
    private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    
}
