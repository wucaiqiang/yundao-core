/**
 * 
 */
package com.yundao.core.utils;

import com.yundao.core.ed.config.EDConfigEnum;

/**
 * @author Jon Chiang
 * @date 2016年8月30日
 */
public class ClientUtil {

	public static String getClientName(String clientType) {
		if ("crm.app".equals(clientType)) {
			return "诺赢理财师";
		} else if (EDConfigEnum.ZCMALL_APP.getKey().equals(clientType)) {
			return "诺赢百万理财";
		} else if (EDConfigEnum.ZCMALL_H5.getKey().equals(clientType)) {
			return "H5页面";// TODO
		} else if (EDConfigEnum.FRONT_ZCMALL.getKey().equals(clientType)) {
			return "诺赢招财猫主站";// TODO
		} else {
			return "H5页面";// TODO
		}

	}
}
