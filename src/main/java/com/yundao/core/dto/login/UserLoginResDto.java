
package com.yundao.core.dto.login;

import java.io.Serializable;
import java.util.Map;

/**
 * Function: Reason: Date: 2017年7月17日 上午10:08:35
 * 
 * @author wucq
 * @version
 */
public class UserLoginResDto implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String mobile;
	private String username;
	private String email;
	private String ticket;
	private Integer status;
	/**
	 * 是否修改过密码，1：是，0：否，默认：0
	 */
	private Integer onceEditPwd;
	private Map<String,Object> params;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getMobile() {

		return mobile;
	}

	public void setMobile(String mobile) {

		this.mobile = mobile;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getTicket() {

		return ticket;
	}

	public void setTicket(String ticket) {

		this.ticket = ticket;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOnceEditPwd() {
		return onceEditPwd;
	}

	public void setOnceEditPwd(Integer onceEditPwd) {
		this.onceEditPwd = onceEditPwd;
	}
}