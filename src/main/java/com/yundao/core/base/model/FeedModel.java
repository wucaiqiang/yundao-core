package com.yundao.core.base.model;

import java.io.Serializable;
import java.util.Date;

public class FeedModel implements Serializable {
    private Integer id;

    //发生操作的用户编号
    private Integer userId;

    //服务人id
    private Integer servicerId;

    //模版编号对应 feed_template表
    private Integer templateId;

    //能看到这条信息的用户id
    private Integer feedUserId;

    //消息标题
    private String title;

    //流水号
    private String seqNo;

    //已读状态 1未读 2已读
    private Byte readStatus;

    //客户id
    private Integer customerId;

    //是否删除 1正常 2已删除
    private Byte isDeleted;

    //模版编码
    private String templateCode;

    //模版参数
    private String params;

    //消息类型 如登录、注册、购买产品等
    private Integer type;

    //消息大类 如 客户分配 客户动态等
    private Byte messageType;

    //系统编号，如 bs.pd等
    private String systemCode;

    //当前记录创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;

    //操作真正发生的时间
    private Date optTime;

    static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getServicerId() {
        return servicerId;
    }

    public void setServicerId(Integer servicerId) {
        this.servicerId = servicerId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getFeedUserId() {
        return feedUserId;
    }

    public void setFeedUserId(Integer feedUserId) {
        this.feedUserId = feedUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Byte getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Byte readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }
}