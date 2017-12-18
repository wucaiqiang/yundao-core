package com.yundao.core.http.config;

/**
 * http配置类
 *
 * @author wupengfei wupf86@126.com
 */
public class HttpConfig {

    /**
     * 连接超时时间，单位毫秒
     */
    private Integer connectTimeout;

    /**
     * 传输超时时间，单位毫秒
     */
    private Integer socketTimeout;

    public HttpConfig() {

    }

    public HttpConfig(Integer connectTimeout, Integer socketTimeout) {
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
