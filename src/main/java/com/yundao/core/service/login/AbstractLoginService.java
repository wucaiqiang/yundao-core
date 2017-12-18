
package com.yundao.core.service.login;

import com.yundao.core.cache.redis.JedisUtils;
import com.yundao.core.code.Result;
import com.yundao.core.code.config.CoreCode;
import com.yundao.core.dto.HeaderUser;
import com.yundao.core.dto.login.*;
import com.yundao.core.exception.BaseException;
import com.yundao.core.service.AbstractService;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.DesUtils;
import com.yundao.core.utils.TicketGeneratorUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * Function: Reason: Date: 2017年7月17日 下午4:26:46
 * 
 * @author wucq
 * @version
 */
public abstract class AbstractLoginService extends AbstractService {

	public Result<UserLoginResDto> doLogin(UserLoginReqDto loginReqDto, Object object) throws Exception {
		if (BooleanUtils.isEmpty(loginReqDto.getUserName())) {
			throw new BaseException(CoreCode.CORE_1250001);
		}
		if (BooleanUtils.isEmpty(loginReqDto.getPassword())) {
			throw new BaseException(CoreCode.CORE_1250002);
		}
		UserAccountModel userAccountModel = selectByMobileOrUsernmae(loginReqDto.getUserName());
		if (userAccountModel == null) {
			throw new BaseException(CoreCode.CORE_1250001);
		}
		// 校验用户名和密码是否正常
		String password = userAccountModel.getPassword();
		String orginPassword = processDecryptTranPassword(loginReqDto.getPassword());
		String md5Password = processEncryptDBPassword(orginPassword);
		if (!password.equals(md5Password)) {
			throw new BaseException(CoreCode.CORE_1250002);
		}
		// 删除ticket
		deleteByTicket(userAccountModel.getId(), false);
		// 新增新的ticet
		String ticketVal = TicketGeneratorUtil.generateTicket("TGT", "cas.yundao.com");
		TicketModel ticket = insertTicket(userAccountModel.getId(), object, ticketVal);

		JedisUtils.setObject(ticket.getTicket(), ticket.getActiveTime(), ticket.getTicket());
		// TODO GJL设备绑定

		// 返回结果
		UserLoginResDto dto = new UserLoginResDto();
		BeanUtils.copyProperties(dto, userAccountModel);
		dto.setMobile(loginReqDto.getUserName());
		dto.setTicket(ticket.getTicket());

		// 回掉,返回params
		Map<String, Object> params = loginAfter(dto, object);
		dto.setParams(params);
		return Result.newSuccessResult(dto);
	}

	public Result<Boolean> doLoginOut() throws Exception {
		HeaderUser user = this.getHeaderUser();
		deleteByTicket(user.getUserId(), false);
		// TODO 设备解绑
		return Result.newSuccessResult(true);

	}

	public Result<Boolean> doLoginOutAndClearAllTicket() throws Exception {
		HeaderUser user = this.getHeaderUser();
		deleteByTicket(user.getUserId(), true);
		// TODO 设备解绑
		return Result.newSuccessResult(true);
	}

	public Result<Integer> updatePwdByUser(UserUpdatePasswordResDto userUpdatePasswordResDto) throws Exception {
		if (BooleanUtils.isEmpty(userUpdatePasswordResDto.getUserName())) {
			throw new BaseException(CoreCode.CORE_1250001);
		}
		if (BooleanUtils.isEmpty(userUpdatePasswordResDto.getOldPassword())) {
			throw new BaseException(CoreCode.CORE_1250005);
		}
		if (BooleanUtils.isEmpty(userUpdatePasswordResDto.getNewPassword())) {
			throw new BaseException(CoreCode.CORE_1250006);
		}

		// 通过用户名和密码获取用户
		String oldPasswrod = processDecryptTranPassword(userUpdatePasswordResDto.getOldPassword());
		String oldMd5Password = processEncryptDBPassword(oldPasswrod);
		Boolean exist = isExist(userUpdatePasswordResDto.getUserName(), oldMd5Password);
		if (!exist) {
			// 密码有误
			throw new BaseException(CoreCode.CORE_1250007);
		}
		// 修改密码
		String newPassword = processDecryptTranPassword(userUpdatePasswordResDto.getNewPassword());// 所传的密码
		String newMd5Password = processEncryptDBPassword(newPassword);
		int count = updateUserPassword(userUpdatePasswordResDto.getUserName(), newMd5Password);
		// 登出当前的用户
		doLoginOutAndClearAllTicket();
		return Result.newSuccessResult(count);
	}

	/**
	 * 通过手机号查询用户
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	protected abstract UserAccountModel selectByMobileOrUsernmae(String mobile) throws Exception;

	/**
	 * 用户是否存在
	 * 
	 * @param userName
	 * @param oldPassword
	 * @return
	 */
	protected abstract Boolean isExist(String userName, String oldPassword) throws Exception;

	/**
	 * 修改用户密码
	 * 
	 * @param userName
	 * @param newPassword
	 */
	protected abstract Integer updateUserPassword(String userName, String newPassword) throws Exception;

	/**
	 * 加密密码
	 * 
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	protected abstract String processEncryptDBPassword(String password) throws Exception;

	/**
	 * 解密密码
	 * 
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	protected abstract String processDecryptTranPassword(String password) throws Exception;

	/**
	 * 新增用户的ticketk
	 * 
	 * @param userId
	 *            用户ID
	 * @param object
	 *            对象
	 * @return
	 * @throws Exception
	 */
	protected abstract TicketModel insertTicket(Long userId, Object object, String ticket) throws Exception;

	/**
	 * 通过用户ID，删除ticket deleteByTicket:
	 * 
	 * @author: wucq
	 * @param userId
	 * @param clearAll
	 *            是否清除该帐号的所有ticket
	 * @return
	 * @throws Exception
	 * @description:
	 */
	protected abstract Integer deleteByTicket(Long userId, boolean clearAll) throws Exception;

	/**
	 * 登陆后置
	 * 
	 * @param object
	 * @return 返回需要在params中返回的参数
	 */
	protected abstract Map<String, Object> loginAfter(UserLoginResDto dto, Object object)throws Exception;
}
