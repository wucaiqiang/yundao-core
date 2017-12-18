package com.yundao.core.dto.message;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息
 *
 * @author wupengfei wupf86@126.com
 */
public class MessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    private Integer topicId;

    /**
     * 发送目标类型
     */
    private String targetType;

    /**
     * 发送目标值
     */
    private String targetValue;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 项目标题
     */
    private String title;

    /**
     * 名称
     */
    private String name;

    /**
     * 推送消息的内容
     */
    private String content;

    /**
     * 即将发送的时间
     */
    private Date sendTime;

    /**
     * 系统编码
     */
    private String source;

    /**
     * 用户ID,多个以逗号分隔，和设备ID对应
     */
    private String users;

    /**
     * 发送者
     */
    private String senderName;

    /**
     * pdf链接
     */
    private String url;

    /**
     * 红包类型
     */
    private String redpacketType;

    /**
     * 推送链接
     */
    private String pushUrl;

    /**
     * 创建人
     */
    private String creator;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(String redpacketType) {
        this.redpacketType = redpacketType;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
