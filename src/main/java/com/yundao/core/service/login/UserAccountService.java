package com.yundao.core.service.login;

import com.yundao.core.code.Result;
import com.yundao.core.dto.login.UserAccountModel;

public interface UserAccountService {
	/**
	 * 通过手机号查询用户
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	Result<UserAccountModel> selectByMobile(String mobile) throws Exception;

	/**
	 * 通过用户名查询用户
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	Result<UserAccountModel> selectByUserName(String userName) throws Exception;
	/**
	 * 查询用户ID
	 * @param accountId
	 * @return
	 */
	Result<UserAccountModel> selectById(Long accountId) throws Exception;
	/**
	 * 用户是否存在
	 * @param userName
	 * @param oldPassword
	 * @return
	 */
	Boolean isExist(String userName,String oldPassword) throws Exception;

	/**
	 * 修改用户密码
	 * @param userName
	 * @param newPassword
	 */
	Integer updateUserPassword(String userName,String newPassword) throws Exception;
}
