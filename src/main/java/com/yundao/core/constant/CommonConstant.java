package com.yundao.core.constant;

/**
 * 常用静态
 *
 * @author wupengfei wupf86@126.com
 */
public interface CommonConstant {

    /**
     * 路径分隔符
     */
    char PATH_SEPARATOR = '/';

    /**
     * 以逗号或分号分隔
     */
    String COMMA_SEMICOLON = "\\s*,\\s*|\\s*;\\s*";

    /**
     * utf-8编码
     */
    String UTF_8 = "utf-8";

    /**
     * gbk编码
     */
    String GBK = "gbk";

    /**
     * resources目录
     */
    String RESOURCES = "resources";

    /**
     * contextPath目录
     */
    String CONTEXT_PATH = "contextPath";

    /**
     * version版本号
     */
    String VERSION = "version";

    /**
     * id
     */
    String ID = "id";

    /**
     * ?
     */
    String QUESTION = "?";

    /**
     * &
     */
    String AND = "&";

    /**
     * =
     */
    String EQUALS = "=";

    /**
     * 1
     */
    int ONE = 1;

    /**
     * 2
     */
    int TWO = 2;

    /**
     * 0
     */
    int ZERO = 0;

    /**
     * -1
     */
    int NEGATIVE_ONE = -1;

    /**
     * success
     */
    String SUCCESS = "success";

    /**
     * result
     */
    String RESULT = "result";

    /**
     * message
     */
    String MESSAGE = "message";

    /**
     * code
     */
    String CODE = "code";

    /**
     * 分页查询数据
     */
    String DATAS = "datas";

    /**
     * failure
     */
    String FAILURE = "failure";

    /**
     * 发生异常时要跳转的页面名字和从request中获取异常信息的name
     */
    String EXCEPTION = "exception";

    /**
     * 用户的ip地址
     */
    String IP = "ip";

    /**
     * 记录方法的开始时间，单位毫秒
     */
    String BEGIN_TIME = "beginTime";

    /**
     * 保存用户标志
     */
    String USERID = "userId";

    /**
     * 机构id
     */
    String MECHANISM_ID = "mechanismId";


    /**
     * 部门id
     */
    String ORGANIZATION_ID = "organizationId";

    /**
     * 用户登录ticket
     */
    String TICKET = "ticket";

    /**
     * 根ID，-1
     */
    long ROOT_ID = 0;

    /**
     * 上下级编码时的根编码
     */
    String ROOT_CODE = "10000000";

    /**
     * url
     */
    String URL = "url";

    /**
     * 系统容量capacity
     */
    String CAPACITY = "capacity";

    /**
     * 丢弃请求的间隔，单位毫秒
     */
    String ABANDON_INTERVAL = "abandon.interval";

    /**
     * 文本text/plain
     */
    String TEXT_PLAIN = "text/plain";

    /**
     * 短信验证码位数
     */
    String SMS_CODE_DIGIT = "sms.code.digit";

    /**
     * 短信密码验证码位数
     */
    String SMS_PWD_DIGIT = "sms.pwd.digit";

    /**
     * 短信验证码保存时间
     */
    String SMS_CODE_EXPIRETIME = "sms.code.expiretime";

    /**
     * 短信密码保存时间
     */
    String SMS_PWD_EXPIRETIME = "sms.pwd.expiretime";

    /**
     * 用户信息保存时间
     */
    String UBS_USER_EXPIRETIME = "ubs.user.expiretime";

    /**
     * 随机数保存时间
     */
    String UBS_RANDOM_EXPIRETIME = "ubs.random.expiretime";

    /**
     * 字体
     */
    String COMMON_SERVICE_FONT = "font";

    /**
     * 图片验证码
     */
    String COMMON_SERVICE_CAPTCHA = "captcha";

    /**
     * 随机数
     */
    String UUID = "uuid";

    /**
     * 图片验证码保存时间
     */
    String CAPTCHA_EXPIRE_TIME = "captcha.expire.time";

    /**
     * 手机号
     */
    String MOBILE_USERNAME = "username";

    /**
     * cookie域
     */
    String COOKIE_DOMAIN = "/";

    /**
     * 加密值
     */
    String SIGN = "sign";

    /**
     * 手机加密key值
     */
    String UBS_MOBILE_RSA = "ubs.mobile.rsa";

    /**
     * 登录rsa 密码加密文件名
     */
    String LOGIN_RSA_FILE = "login.rsa.txt";

    /**
     * 注册rsa 密码加密文件名
     */
    String REGISTER_RSA_FILE = "register.rsa.txt";

    /**
     * 注册rsa 手机加密文件名
     */
    String MOBILE_RSA_FILE = "mobile.rsa.txt";

    /**
     * des加密
     */
    String DES_KEY = "des.key";

    /**
     * Jsonp请求格式参数
     */
    String CALLBACK = "callback";

    /**
     * 任务id标志
     */
    String TASKID = "taskId";

    /**
     * 流程实例id标志
     */
    String PROCESSINSTANCEID = "processInstanceId";

    /**
     * post请求
     */
    String POSTMETHOD = "post";

    /**
     * get请求
     */
    String GSTMETHOD = "get";

    /**
     * 用户基础服务链接
     */
    String UBS_URL = "ubs.url";

    /**
     * 头部用户
     */
    String HEADER_USER = "headerUser";

    /**
     * 用户关系
     */
    String USER_RELATION = "userRelation";

    /**
     * 真实姓名
     */
    String REAL_NAME = "realName";

    /**
     * 允许跨域请求url加端口
     */
    String ACAO_FRONT_URL = "acao.front.url";

    /**
     * 用户登录的链接
     */
    String LOGIN_URL = "login.url";

    /**
     * cookie加密值
     */
    String COOKIE_SIGN = "cookie.sign";

    /**
     * cookie的时间
     */
    String COOKIE_TIME = "cookie.time";

    /**
     * 登录后的有效时间
     */
    String LOGIN_VALID_SECONDS = "login.valid.seconds";

    /**
     * 校验权限
     */
    String CHECK_PERMISSION = "checkPermission";

    /**
     * 授权资源
     */
    String AUTHORIZE_MAP = "authorizeMap";

    /**
     * 系统编码
     */
    String SYSTEM_CODE = "system.code";

    /**
     * 数量
     */
    String NUMBER = "number";

    /**
     * 消息中心链接
     */
    String MSC_URL = "msc.url";

    /**
     * 是否强制校验TGT
     */
    String FORCE_VALIDATE_TGT = "force.validate.tgt";

    /**
     * 是否强制校验AJAX
     */
    String FORCE_VALIDATE_AJAX = "force.validate.ajax";

    /**
     * 注册来源
     */
    String REGISTER_SOURCE = "registerSource";

    /**
     * 数据中心链接
     */
    String DC_URL = "dc.url";

    /**
     * 通过
     */
    String PASS = "pass";

    /**
     * 用户id
     */
    String USER_ID = "userId";

    /**
     * 是否完成
     */
    String IS_COMPLETE = "isComplete";

    /**
     * 租户id
     */
    String TENANT_ID="tenantId";
    /**
     * 租户code
     */
    String TENANT_CODE="tenantCode";
}
