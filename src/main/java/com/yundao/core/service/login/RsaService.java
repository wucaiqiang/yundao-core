
package com.yundao.core.service.login;

/**
 * Function: Reason: Date: 2017年7月17日 下午6:54:31
 * 
 * @author wucq
 * @version
 */
public interface RsaService {
	public String processEncryptDBPassword(String password) throws Exception;
	public String processDecryptTranPassword(String password) throws Exception;
}
