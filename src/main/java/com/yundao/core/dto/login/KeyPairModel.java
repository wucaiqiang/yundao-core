package com.yundao.core.dto.login;

import com.yundao.core.base.model.BaseModel;

import java.io.Serializable;

public class KeyPairModel extends BaseModel implements Serializable {
    /**
	 * tran:传输，db:数据库数据，default:默认
	 */
    private String area;

    /**
	 * 密钥对值
	 */
    private byte[] keyPair;

    private static final long serialVersionUID = 1L;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public byte[] getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(byte[] keyPair) {
        this.keyPair = keyPair;
    }
}