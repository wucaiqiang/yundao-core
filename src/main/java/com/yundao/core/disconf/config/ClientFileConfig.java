package com.yundao.core.disconf.config;

import com.baidu.disconf.client.config.DisClientConfig;

/**
 * 用户文件配置
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class ClientFileConfig {

    private static final String ONLINE = "online";

    /**
     * 客户配置
     */
    private static DisClientConfig instance = DisClientConfig.getInstance();

    /**
     * @return 是否正式环境
     */
    public static boolean isProduct() {
        return ONLINE.equals(instance.ENV);
    }

}
