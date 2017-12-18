package com.yundao.core.dto.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.utils.ConfigUtils;

/**
 * 日志请求
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class LogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ip;

    /**
     * 系统编码
     */
    private String systemCode;

    /**
     * 日志类型 0：插入，1：更新，2：删除
     */
    private Integer type;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务主键
     */
    private Integer businessId;

    /**
     * 系统表名
     */
    private String tableName;

    /**
     * 主动用户id
     */
    private Integer activeUserId;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 被动用户id
     */
    private Integer passiveUserId;

    /**
     * 字段列表
     */
    private List<FieldDto> fieldList = new ArrayList<>();

    public LogDto() {
        this.systemCode = ConfigUtils.getValue(CommonConstant.ID);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(Integer activeUserId) {
        this.activeUserId = activeUserId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getPassiveUserId() {
        return passiveUserId;
    }

    public void setPassiveUserId(Integer passiveUserId) {
        this.passiveUserId = passiveUserId;
    }

    public List<FieldDto> getFieldList() {
        return fieldList;
    }

    /**
     * 增加字段修改
     *
     * @param fieldDto
     */
    public void addField(FieldDto fieldDto) {
        this.fieldList.add(fieldDto);
    }

    /**
     * 记录字段名更改
     *
     * @param fieldName 字段名
     * @param oldValue  旧值
     * @param newValue  新值
     */
    public void putField(String fieldName, String oldValue, String newValue) {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setName(fieldName);
        fieldDto.setOldValue(oldValue);
        fieldDto.setNewValue(newValue);
        this.addField(fieldDto);
    }

    /**
     * 表字段
     */
    public static class FieldDto implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 字段名
         */
        private String name;

        /**
         * 旧值
         */
        private String oldValue;

        /**
         * 新值
         */
        private String newValue;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOldValue() {
            return oldValue;
        }

        public void setOldValue(String oldValue) {
            this.oldValue = oldValue;
        }

        public String getNewValue() {
            return newValue;
        }

        public void setNewValue(String newValue) {
            this.newValue = newValue;
        }
    }
}
