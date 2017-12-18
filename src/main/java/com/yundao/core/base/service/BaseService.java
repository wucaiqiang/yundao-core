package com.yundao.core.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yundao.core.dto.log.LogDto;
import com.yundao.core.dto.log.LogInsertDto;
import com.yundao.core.dto.mechanism.MechanismDto;
import com.yundao.core.dto.role.RoleDto;
import com.yundao.core.enums.msc.SmsAuthenticatorServiceTypeEnum;
import com.yundao.core.enums.product.ProductTypeEnum;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.threadlocal.filter.RequestCommonParams;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.HttpUtils;
import com.yundao.core.utils.JsonUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.yundao.core.code.Result;
import com.yundao.core.code.config.CommonCode;
import com.yundao.core.config.common.CommonConfigEnum;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.constant.MethodConstant;
import com.yundao.core.dto.dictionary.DictionaryDto;
import com.yundao.core.dto.log.LogDeleteDto;
import com.yundao.core.dto.log.LogUpdateDto;
import com.yundao.core.dto.message.MessageDto;
import com.yundao.core.dto.sms.SmsTemplateDto;
import com.yundao.core.dto.user.UserDto;
import com.yundao.core.exception.BaseException;
import com.yundao.core.json.jackson.type.BaseTypeReference;
import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.DateUtils;
import com.yundao.core.utils.FileUtils;
import com.yundao.core.utils.StringBuilderUtils;

/**
 * 服务基类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class BaseService {

    private static Log log = LogFactory.getLog(BaseService.class);

    private static BaseService service = null;

    static {
        service = new BaseService() {
        };
    }

    /**
     * 获取服务基类
     *
     * @return
     */
    public static BaseService getBaseService() {
        return service;
    }

    /**
     * 根据部门获取用户的关系(包括本部门其他用户信息)
     *
     * @param userId
     * @return
     * @throws BaseException
     */
    public String getRelationUserId(Integer userId) throws BaseException {
        return this.getRelationUserId(userId, null, null, null);
    }

    /**
     * 根据用户id获取本部门下面所有部门的用户信息（包括本部门用户信息）
     *
     * @param userId
     * @return
     * @throws BaseException
     */
    public String getAllRelationUserId(Integer userId) throws BaseException {
        return this.getRelationUserId(userId, null, null, "1");
    }

    /**
     * 根据部门获取用户的关系
     *
     * @param userId
     * @param userRoleId
     * @param systemCode
     * @return
     * @throws BaseException
     */
    public String getRelationUserId(Integer userId, Integer userRoleId, String systemCode, String flag) throws BaseException {
        if (userId == null) {
            throw new BaseException(CommonCode.COMMON_1004);
        }

        // 超级管理员
        if (userId == -1) {
            return null;
        }

        // 先从request中获取
        String result = (String) ThreadLocalUtils.getFromRequest(CommonConstant.USER_RELATION);
        if (result != null) {
            log.info("从request中获取到用户关系userId=" + userId + ",userRelationId=" + result);
            return result;
        }

        // 获取用户的关系
        result = userId.toString();
        Map<String, Object> methodParams = new HashMap<String, Object>();
        methodParams.put("userId", userId);
        methodParams.put("userRoleId", userRoleId);
        methodParams.put("systemCode", systemCode);
        methodParams.put("flag", flag);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/user/get-users");
        Result<List<Map<String, Object>>> response = this.get(url, methodParams, new BaseTypeReference<Result<List<Map<String, Object>>>>() {
        });
        List<Map<String, Object>> userIdList = response.getResult();
        int size = (userIdList != null) ? userIdList.size() : 0;
        for (int i = 0; i < size; i++) {
            Map<String, Object> userIdMap = userIdList.get(i);
            Object beUserId = userIdMap.get("userId");
            if (beUserId != null) {
                result = result + "," + beUserId.toString();
            }
        }

        // 保存用户关系
        ThreadLocalUtils.addToRequest(CommonConstant.USER_RELATION, result);
        return result;
    }

    /**
     * 校验用户是否有权限
     *
     * @param userIds
     * @param ownerUserId
     * @throws BaseException
     */
    public void checkPermission(String userIds, Integer ownerUserId) throws BaseException {
        if (BooleanUtils.isBlank(userIds) || ownerUserId == null) {
            log.error("校验用户权限时有误userIds=" + userIds + ",ownerUserId=" + ownerUserId);
            throw new BaseException(CommonCode.COMMON_1004);
        }

        // 先从request中获取
        Boolean result = (Boolean) ThreadLocalUtils.getFromRequest(CommonConstant.CHECK_PERMISSION);
        if (result != null && result) {
            return;
        }

        boolean check = false;
        String[] userIdArray = userIds.split(CommonConstant.COMMA_SEMICOLON);
        for (String each : userIdArray) {
            if (NumberUtils.toInt(each) == ownerUserId) {
                check = true;
                break;
            }
        }
        if (!check) {
            log.info("没有权限操作，传进来的ownerUserId=" + ownerUserId + ",相应的权限userid=" + userIds);
            throw new BaseException(CommonCode.COMMON_1060);
        }
        ThreadLocalUtils.addToRequest(CommonConstant.CHECK_PERMISSION, true);
    }

    /**
     * 获取数据字典
     *
     * @param code
     * @return
     * @throws BaseException
     */
    public Result<DictionaryDto> getDictionary(String code) throws BaseException {
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        Map<String, Object> methodParams = new HashMap<String, Object>(1);
        methodParams.put("code", code);
        String dicUrl = FileUtils.getRealPath(url, "/dictionary/get");
        Result<List<DictionaryDto>> result = this.get(dicUrl, methodParams, new BaseTypeReference<Result<List<DictionaryDto>>>() {
        });
        List<DictionaryDto> dicList = result.getResult();
        return Result.newSuccessResult(!BooleanUtils.isEmpty(dicList) ? dicList.get(0) : null);
    }

    /**
     * 获取子类数据字典
     *
     * @param code
     * @return
     * @throws BaseException
     */
    public Result<Map<String, DictionaryDto>> getChildrenDictionary(String code) throws BaseException {
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        Map<String, Object> methodParams = new HashMap<String, Object>(1);
        methodParams.put("code", code);
        String dicUrl = FileUtils.getRealPath(url, "/dictionary/get_by_code");
        Result<List<DictionaryDto>> dicResult = this.get(dicUrl, methodParams, new BaseTypeReference<Result<List<DictionaryDto>>>() {
        });
        List<DictionaryDto> dicList = dicResult.getResult();
        Map<String, DictionaryDto> dicMap = new HashMap<String, DictionaryDto>();
        int size = (dicList != null) ? dicList.size() : 0;
        for (int i = 0; i < size; i++) {
            DictionaryDto each = dicList.get(i);
            dicMap.put(each.getCode(), each);
        }
        return Result.newResult(dicResult.getCode(), dicResult.getMessage(), dicMap);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userIds 以逗号分隔
     * @author huangxiog 2016年4月21日
     */
    public Map<Integer, UserDto> getUserInfo(String userIds) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>();
        methodParams.put("userId", userIds);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/user/getByIds");
        Result<List<Map<String, Object>>> response = this.get(url, methodParams, new BaseTypeReference<Result<List<Map<String, Object>>>>() {
        });
        List<Map<String, Object>> userList = response.getResult();
        Map<Integer, UserDto> result = new HashMap<Integer, UserDto>();
        try {
            for (Map<String, Object> each : userList) {
                UserDto user = new UserDto();
                BeanUtils.populate(user, each);
                result.put(user.getUserId(), user);
            }
        }
        catch (Exception e) {
            log.error("获取用户信息转换时出错", e);
        }
        return result;
    }

    /**
     * 根据用户手机号获取用户信息
     *
     * @param mobile
     */
    public UserDto getUserByMobile(String mobile) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("mobile", mobile);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/user/getByMobile");
        Result<UserDto> response = this.get(url, methodParams, new BaseTypeReference<Result<UserDto>>() {
        });
        UserDto result = response.getResult();
        return result;
    }

    /**
     * 根据机构id获取机构信息
     *
     * @param mechanismId
     * @return
     * @throws BaseException
     */
    public MechanismDto getMechanism(Integer mechanismId) throws BaseException {
        MechanismDto result = null;
        if (mechanismId != null) {
            Map<Integer, MechanismDto> map = this.getMechanisms(mechanismId.toString());
            result = map.get(mechanismId);
        }
        return result;
    }

    /**
     * 根据机构id获取机构信息
     *
     * @param mechanismIds 以逗号分隔
     * @return
     * @throws BaseException
     */
    public Map<Integer, MechanismDto> getMechanisms(String mechanismIds) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>();
        methodParams.put("id", mechanismIds);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/mechanism/getByIds");
        Result<List<Map<String, Object>>> response = this.get(url, methodParams, new BaseTypeReference<Result<List<Map<String, Object>>>>() {
        });
        List<Map<String, Object>> mechanismList = response.getResult();
        Map<Integer, MechanismDto> result = new HashMap<Integer, MechanismDto>();
        try {
            for (Map<String, Object> each : mechanismList) {
                MechanismDto mechanism = new MechanismDto();
                BeanUtils.populate(mechanism, each);
                result.put(mechanism.getId(), mechanism);
            }
        }
        catch (Exception e) {
            log.error("获取机构信息转换时出错", e);
        }
        return result;
    }

    /**
     * 获取角色id
     *
     * @param roleName
     * @param mechanismId
     * @return
     * @throws BaseException
     */
    public Integer getRoleId(String roleName, Integer mechanismId) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("name", roleName);
        methodParams.put("mechanismId", mechanismId);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/role/get_name_mechanismId");
        Result<Integer> response = this.get(url, methodParams, new BaseTypeReference<Result<Integer>>() {
        });
        return response.getResult();
    }

    /**
     * 根据用户ID获取角色Id (获取该用户的上级用户角色Id)专员角色ID
     *
     * @param userId
     * @return
     * @throws BaseException
     * @author huangxiog 2016年5月12日
     */
    public Integer getRoleIdByUserId(Integer userId) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(1);
        methodParams.put("userId", userId);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/userRelation/get_userId");
        Result<Integer> response = this.get(url, methodParams, new BaseTypeReference<Result<Integer>>() {
        });
        return response.getResult();
    }

    /**
     * 获取角色名
     *
     * @param userId
     * @return
     * @throws BaseException
     */
    public RoleDto getRole(Integer userId) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("userId", userId);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/role/get_userId");
        Result<List<RoleDto>> response = this.get(url, methodParams, new BaseTypeReference<Result<List<RoleDto>>>() {
        });
        return !BooleanUtils.isEmpty(response.getResult()) ? response.getResult().get(0) : null;
    }

    /**
     * 获取角色名
     *
     * @param code
     * @return
     * @throws BaseException
     */
    public RoleDto getRole(String code) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("code", code);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/role/get_code");
        Result<RoleDto> response = this.get(url, methodParams, new BaseTypeReference<Result<RoleDto>>() {
        });
        return response.getResult();
    }

    /**
     * 获取审批角色名
     *
     * @param code
     * @return
     * @throws BaseException
     */
    public RoleDto getGroup(String code) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("code", code);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/group/getByCode");
        Result<RoleDto> response = this.get(url, methodParams, new BaseTypeReference<Result<RoleDto>>() {
        });
        return response.getResult();
    }

    /**
     * 根据角色编码获取用户id
     *
     * @param code
     * @return
     * @throws BaseException
     */
    public List<Integer> getUserIdByRoleCode(String code) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("code", code);
        String url = ConfigUtils.getValue(CommonConstant.UBS_URL);
        url = FileUtils.getRealPath(url, "/userRole/get_userid_by_code");
        Result<List<Integer>> response = this.get(url, methodParams, new BaseTypeReference<Result<List<Integer>>>() {
        });
        return response.getResult();
    }

    /**
     * 根据模板发送短信
     *
     * @param mobiles
     * @param templateCode
     * @param templateParams
     * @return
     * @throws BaseException
     */
    public Result<String> sendSms(String mobiles, String templateCode, Map<String, Object> templateParams) throws BaseException {
        return this.sendSms(mobiles, null, templateCode, templateParams);
    }

    /**
     * 根据模板发送短信
     *
     * @param mobiles
     * @param serviceTypeEnum
     * @param templateCode
     * @param templateParams
     * @return
     * @throws BaseException
     */
    public Result<String> sendSms(String mobiles, SmsAuthenticatorServiceTypeEnum serviceTypeEnum, String templateCode, Map<String, Object> templateParams)
            throws BaseException {
        SmsTemplateDto smsTemplate = new SmsTemplateDto();
        smsTemplate.setMobiles(mobiles);
        smsTemplate.setServiceTypeEnum(serviceTypeEnum);
        smsTemplate.setTemplateCode(templateCode);
        smsTemplate.setTemplateParams(templateParams);
        return sendSms(smsTemplate);
    }

    /**
     * 根据模板发送短信
     *
     * @param smsTemplate
     * @return
     * @throws BaseException
     */
    public Result<String> sendSms(SmsTemplateDto smsTemplate) throws BaseException {
        SmsAuthenticatorServiceTypeEnum serviceTypeEnum = smsTemplate.getServiceTypeEnum();
        if (serviceTypeEnum == null) {
            serviceTypeEnum = SmsAuthenticatorServiceTypeEnum.VALUE_2;
        }
        String switchKey = "switch."+smsTemplate.getTemplateCode();
        String switchValue = ConfigUtils.getValue(switchKey);
        if(BooleanUtils.isNotBlank(switchValue) && "off".equals(switchValue)){
        	log.warn("系统配置已经关闭当前短信发送，不做发送data:"+ JsonUtils.objectToJson(smsTemplate));
        }
        Map<String, Object> methodParams = new HashMap<>(8);
        methodParams.put("mobiles", smsTemplate.getMobiles());
        methodParams.put("serviceType", serviceTypeEnum.getValue());
        methodParams.put("templateCode", smsTemplate.getTemplateCode());
        methodParams.put("templateParams", JsonUtils.objectToJson(smsTemplate.getTemplateParams()));
        methodParams.put("ip", smsTemplate.getIp());
        methodParams.put("validateCode", smsTemplate.getValidateCode());
        methodParams.put("mobileType", smsTemplate.getMobileType());

        String url = ConfigUtils.getValue(CommonConstant.MSC_URL);
        url = FileUtils.getRealPath(url, "/sms/send_template/");
        Result<String> result = this.post(url, methodParams, new BaseTypeReference<Result<String>>() {
        });
        return result;
    }

    /**
     * 获取校验链接的结果
     *
     * @param userId
     * @param url，多个时以逗号分隔
     * @return
     * @throws BaseException
     */
    public Map<String, Boolean> getAuthorizeUrl(String userId, String url) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(2);
        methodParams.put("userId", userId);
        methodParams.put("url", url);

        String ubsUrl = ConfigUtils.getValue(CommonConstant.UBS_URL);
        ubsUrl = FileUtils.getRealPath(ubsUrl, ConfigUtils.getValue(CommonConfigEnum.SECURITY_AUTHORIZE_URL.getKey()));
        Result<Map<String, Boolean>> respResult = this.get(ubsUrl, methodParams, new BaseTypeReference<Result<Map<String, Boolean>>>() {
        });
        return respResult.getResult();
    }

    /**
     * 根据父链接获取子链接的结果
     *
     * @param systemCode
     * @param userId
     * @param parentUrl
     * @return
     * @throws BaseException
     */
    public Map<String, Boolean> getAuthorizeChildrenUrl(String systemCode, String userId, String parentUrl) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(3);
        methodParams.put("userId", userId);
        methodParams.put("systemCode", systemCode);
        methodParams.put("parentUrl", parentUrl);

        String ubsUrl = ConfigUtils.getValue(CommonConstant.UBS_URL);
        ubsUrl = FileUtils.getRealPath(ubsUrl, ConfigUtils.getValue(CommonConfigEnum.SECURITY_AUTHORIZE_CHILDREN_URL.getKey()));
        Result<Map<String, Boolean>> respResult = this.get(ubsUrl, methodParams, new BaseTypeReference<Result<Map<String, Boolean>>>() {
        });
        return respResult.getResult();
    }

    /**
     * 发送消息
     *
     * @param message
     * @return
     * @throws BaseException
     */
    @Deprecated
    public Integer pushMessage(MessageDto message) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(12);
        methodParams.put("businessId", message.getTopicId());
        methodParams.put("pushWay", message.getTargetType());

        ProductTypeEnum typeEnum = ProductTypeEnum.getProductTypeEnum(message.getProductType());
        if (typeEnum != null) {
            methodParams.put("proType", typeEnum.getName());
        }
        methodParams.put("businessType", message.getBusinessType());
        methodParams.put("pushTitle", message.getTitle());
        methodParams.put("businessName", message.getName());
        methodParams.put("pushContent", message.getContent());
        methodParams.put("sendTime", message.getSendTime());
        methodParams.put("source", message.getSource());
        methodParams.put("senderName", message.getSenderName());
        methodParams.put("url", message.getUrl());
        methodParams.put("redpacketType", message.getRedpacketType());
        methodParams.put("creator", message.getCreator());

        // 根据用户获取设备
        Map<String, Object> ubsMethodParams = new HashMap<String, Object>(2);
        ubsMethodParams.put("ids", message.getUsers());
        ubsMethodParams.put("pageSize", Integer.MAX_VALUE);
        String ubsUrl = ConfigUtils.getValue(CommonConstant.UBS_URL);
        ubsUrl = FileUtils.getRealPath(ubsUrl, "/device/getDeviceIds");
        Result<List<Map<String, String>>> ubsRespResult = this.post(ubsUrl, ubsMethodParams, new BaseTypeReference<Result<List<Map<String, String>>>>() {
        });
        List<Map<String, String>> userIdDeviceIdList = ubsRespResult.getResult();
        log.info("发送用户userIdDeviceIdList=" + userIdDeviceIdList);

        int size = (userIdDeviceIdList != null) ? userIdDeviceIdList.size() : 0;
        if (size == 0) {
            return CommonConstant.NEGATIVE_ONE;
        }

        StringBuilder users = new StringBuilder();
        StringBuilder deviceIds = new StringBuilder();
        for (int i = 0; i < size; i++) {
            Map<String, String> each = userIdDeviceIdList.get(i);
            users.append(each.get("userId")).append(",");
            deviceIds.append(each.get("udid")).append(",");
        }

        StringBuilderUtils.deleteLastChar(users);
        StringBuilderUtils.deleteLastChar(deviceIds);
        methodParams.put("users", users.toString());
        methodParams.put("pushWayValue", deviceIds.toString());

        // 发送消息
        Result<Integer> respResult = this.post(message.getPushUrl(), methodParams, new BaseTypeReference<Result<Integer>>() {
        });
        log.info("推送消息的结果respResult" + respResult.getResult());
        return respResult.getResult() == null ? CommonConstant.ZERO : respResult.getResult();
    }

    /**
     * 发送消息
     *
     * @param message
     * @return
     * @throws BaseException
     */
    @Deprecated
    public Integer pushMessage(MessageDto message, Integer userId, String deviceId) throws BaseException {
        Map<String, Object> methodParams = new HashMap<String, Object>(12);
        methodParams.put("businessId", message.getTopicId());
        methodParams.put("pushWay", message.getTargetType());

        ProductTypeEnum typeEnum = ProductTypeEnum.getProductTypeEnum(message.getProductType());
        if (typeEnum != null) {
            methodParams.put("proType", typeEnum.getName());
        }
        methodParams.put("businessType", message.getBusinessType());
        methodParams.put("title", message.getTitle());
        methodParams.put("businessName", message.getName());
        methodParams.put("pushContent", message.getContent());
        methodParams.put("sendTime", message.getSendTime());
        methodParams.put("source", message.getSource());
        methodParams.put("senderName", message.getSenderName());
        methodParams.put("url", message.getUrl());
        methodParams.put("redpacketType", message.getRedpacketType());
        methodParams.put("creator", message.getCreator());

        // 根据用户获取设备

        methodParams.put("users", userId);
        methodParams.put("pushWayValue", deviceId);

        // 发送消息
        Result<Integer> respResult = this.post(message.getPushUrl(), methodParams, new BaseTypeReference<Result<Integer>>() {
        });
        return respResult.getResult() == null ? CommonConstant.ZERO : respResult.getResult();
    }

    /**
     * 记录业务日志
     *
     * @param logDto 日志对象
     */
    public void logBusiness(LogDto logDto) {
        try {
            // 校验表名
            if (BooleanUtils.isBlank(logDto.getTableName())) {
                log.info("表名不能为空");
                return;
            }

            // 校验业务名
            if (BooleanUtils.isBlank(logDto.getBusinessName())) {
                log.info("业务名称不能为空");
                return;
            }

            // 校验主动用户id
            if (logDto.getActiveUserId() == null) {
                log.info("主动用户id不能为空");
                return;
            }

            // 设置业务类型
            if (logDto instanceof LogDeleteDto) {
                logDto.setType(LogDeleteDto.TYPE);
                if (logDto.getBusinessId() == null) {
                    log.info("业务id不能为空");
                    return;
                }
            }
            else if (logDto instanceof LogInsertDto) {
                logDto.setType(LogInsertDto.TYPE);
            }
            else {
                logDto.setType(LogUpdateDto.TYPE);
                if (logDto.getBusinessId() == null) {
                    log.info("业务id不能为空");
                    return;
                }
            }

            // 设置日志参数
            Map<String, Object> methodParams = new HashMap<>();
            methodParams.put("ip", logDto.getIp());
            methodParams.put("systemCode", logDto.getSystemCode());
            methodParams.put("type", logDto.getType());

            methodParams.put("businessName", logDto.getBusinessName());
            methodParams.put("businessId", logDto.getBusinessId());
            methodParams.put("tableName", logDto.getTableName());
            methodParams.put("activeUserId", logDto.getActiveUserId());
            methodParams.put("operateTime", DateUtils.format(logDto.getOperateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
            methodParams.put("passiveUserId", logDto.getPassiveUserId());
            methodParams.put("fieldJson", JsonUtils.objectToJson(logDto.getFieldList()));

            // 发送请求
            String dcUrl = ConfigUtils.getValue(CommonConstant.DC_URL);
            dcUrl = FileUtils.getRealPath(dcUrl, "/log/add");
            this.post(dcUrl, methodParams, new BaseTypeReference<Result<Integer>>() {
            });
        }
        catch (BaseException e) {
            log.error("记录业务日志异常", e);
        }
    }

    /**
     * 执行post方法
     *
     * @param url
     * @param methodParams
     * @param typeReference
     * @return
     * @throws Exception
     */
    public <T> Result<T> post(String url, Map<String, Object> methodParams, BaseTypeReference<Result<T>> typeReference) throws BaseException {
        return this.doExecute(url, null, methodParams, typeReference, MethodConstant.POST);
    }

    /**
     * 执行post方法
     *
     * @param url
     * @param headerParams
     * @param methodParams
     * @param typeReference
     * @return
     * @throws Exception
     */
    public <T> Result<T> post(String url, Map<String, String> headerParams, Map<String, Object> methodParams, BaseTypeReference<Result<T>> typeReference)
            throws BaseException {
        return this.doExecute(url, headerParams, methodParams, typeReference, MethodConstant.POST);
    }

    /**
     * 执行get方法 ;
     *
     * @param url
     * @param methodParams
     * @param typeReference
     * @return
     * @throws BaseException
     */
    public <T> Result<T> get(String url, Map<String, Object> methodParams, BaseTypeReference<Result<T>> typeReference) throws BaseException {
        return this.doExecute(url, null, methodParams, typeReference, MethodConstant.GET);
    }

    /**
     * 执行get方法 ;
     *
     * @param url
     * @param methodParams
     * @param typeReference
     * @return
     * @throws BaseException
     */
    public <T> Result<T> get(String url, Map<String, String> headerParams, Map<String, Object> methodParams, BaseTypeReference<Result<T>> typeReference)
            throws BaseException {
        return this.doExecute(url, headerParams, methodParams, typeReference, MethodConstant.GET);
    }

    private <T> Result<T> doExecute(String url, Map<String, String> headerParams, Map<String, Object> methodParams, BaseTypeReference<Result<T>> typeReference,
                                    String method) throws BaseException {
        try {
            log.info("头部参数headerParams=" + headerParams + ",方法参数methodParams=" + methodParams);
            RequestCommonParams commonParams = RequestCommonParams.newDefault();
            commonParams.setMethodParams(methodParams);
            commonParams.setHeaderParams(headerParams);
            String response = null;
            if (MethodConstant.POST.equals(method)) {
                response = HttpUtils.post(url, commonParams);
            }
            else {
                response = HttpUtils.get(url, commonParams);
            }
            Result<T> result = JsonUtils.jsonToObject(response, typeReference);
            return result;
        }
        catch (Exception e) {
            log.error("调用链接时异常", e);
            throw new BaseException(CommonCode.COMMON_1007, e);
        }
    }

}
