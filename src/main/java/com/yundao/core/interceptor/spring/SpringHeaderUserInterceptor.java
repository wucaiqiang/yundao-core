package com.yundao.core.interceptor.spring;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.EDUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 从头部参数获取用户信息的拦截器
 *
 * @author wupengfei wupf86@126.com
 */
public class SpringHeaderUserInterceptor extends AbstractSpringLoginInterceptor {

    private static Log log = LogFactory.getLog(SpringHeaderUserInterceptor.class);

    /**
     * 获取用户登录后的会话
     *
     * @param request
     * @return
     */
    public Object getSession(HttpServletRequest request) {
        // 获取用户的登录信息
        HeaderUser result = null;
        String userId = request.getHeader(CommonConstant.USERID);
        String mechanismId = request.getHeader(CommonConstant.MECHANISM_ID);
        String organizationId = request.getHeader(CommonConstant.ORGANIZATION_ID);
        String realName = request.getHeader(CommonConstant.REAL_NAME);
        if (!BooleanUtils.isBlank(userId)) {
            result = new HeaderUser();
            result.setUserId(NumberUtils.toInt(userId));
            result.setRealName(EDUtils.decode(realName));
            result.setMechanismId(NumberUtils.toInt(mechanismId));
            request.setAttribute(CommonConstant.HEADER_USER, result);
        }
        log.info("从头部获取用户的信息userId=" + userId + ",mechanismId=" + mechanismId + ",organizationId=" + organizationId);
        return result;
    }

    /**
     * 头部参数
     */
    public static class HeaderUser implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 用户id
         */
        private Integer userId;

        /**
         * 真实姓名
         */
        private String realName;

        /**
         * 用户机构id
         */
        private Integer mechanismId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public Integer getMechanismId() {
            return mechanismId;
        }

        public void setMechanismId(Integer mechanismId) {
            this.mechanismId = mechanismId;
        }
    }

}
