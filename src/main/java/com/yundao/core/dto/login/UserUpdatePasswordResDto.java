
package com.yundao.core.dto.login;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Function: Reason: Date: 2017年7月17日 上午10:08:35
 * 
 * @author gjl
 * @version
 */
public class UserUpdatePasswordResDto implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "旧密码")
	private String oldPassword;
	@ApiModelProperty(value = "新密码")
	private String newPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
