package com.yundao.core.service;

import com.yundao.core.base.model.BaseModel;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.dto.HeaderUser;
import com.yundao.core.threadlocal.ThreadLocalUtils;

import java.util.Date;

/**
 * 基础服务实现类
 */
public abstract class AbstractService {

    /**
     * 获取请求用户的信息
     *
     * @return 请求用户信息
     */
    protected HeaderUser getHeaderUser() {
        return (HeaderUser) ThreadLocalUtils.getFromRequest(CommonConstant.HEADER_USER);
    }

    /**
     * 查找请求用户id
     *
     * @return 请求用户id
     */
    protected Long getHeaderUserId() {
        HeaderUser result = this.getHeaderUser();
        return result == null ? 0L : result.getUserId();
    }

    /**
     * 获取租户id
     *
     * @return 租户id
     */
    protected Long getHeaderTenantId() {
        HeaderUser result = this.getHeaderUser();
        return this.getHeaderUser() == null ? 0L : result.getTenantId();
    }

    /**
     * 获取用户姓名
     *
     * @return 用户姓名
     */
    protected String getRealName() {
        HeaderUser result = this.getHeaderUser();
        return this.getHeaderUser() == null ? "" : result.getRealName();
    }

    /**
     * 初始化实体[设置createUserId、tenantId、createDate]
     *
     * @param model 实体
     * @param <T>   类型
     * @return 初始化后的实体
     */
    protected <T extends BaseModel> T initialModel(T model) {
        HeaderUser user = this.getHeaderUser();
        if (user != null) {
            if (model.getCreateUserId() == null)
                model.setCreateUserId(user.getUserId());
            if (model.getTenantId() == null)
                model.setTenantId(user.getTenantId());
        }
        if (model.getCreateDate() == null)
            model.setCreateDate(new Date());
        return model;
    }

    /**
     * 更新数据设置值[设置updateUserId、updateDate]
     *
     * @param model 实体
     * @param <T>   类型
     * @return 更新后实体
     */
    protected <T extends BaseModel> T updateModel(T model) {
        HeaderUser user = this.getHeaderUser();
        if (user != null) {
            model.setUpdateUserId(user.getUserId());
        }
        model.setUpdateDate(new Date());
        return model;
    }

}
