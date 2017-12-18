package com.yundao.core.dto.login;

import com.yundao.core.base.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class TicketModel extends BaseModel implements Serializable {
    /**
	 * 帐号id
	 */
    private Long accountId;

    /**
	 * pc:电脑端，app：手机端
	 */
    private String appType;

    /**
	 * 凭证码
	 */
    private String ticket;

    /**
	 * 存活时间，以秒为单位
	 */
    private Integer activeTime;

    /**
	 * 过期时间
	 */
    private Date expireTime;

    private static final long serialVersionUID = 1L;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Integer activeTime) {
        this.activeTime = activeTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}