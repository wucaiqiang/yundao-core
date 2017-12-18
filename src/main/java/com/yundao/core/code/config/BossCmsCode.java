package com.yundao.core.code.config;

/**
 * 运营中心_cms
 * @author gjl
 *
 */
public interface BossCmsCode {
	// 6000~6299属于参数错误码
	// 6300~6599属于逻辑过程错误码

	int CMS_BASE = 6000;
	
	/**
	 * 平台名称长度必须在{min}到{max}之间
	 */
	int CMS_6001 = CMS_BASE + 1;
	
	/**
	 * 平台code长度必须在{min}到{max}之间
	 */
	
	int CMS_6002 = CMS_BASE + 2;
	
	/**
	 * 平台code已经存在
	 */
	int CMS_6003 = CMS_BASE + 3;
	
	/**
	 * 平台name已经存在
	 */
	int CMS_6004 = CMS_BASE + 4;
	
	/**
	 * 平台url已经存在
	 */
	int CMS_6005 = CMS_BASE + 5;
	
	/**
	 * 平台url长度必须在{min}到{max}之间
	 */
	int CMS_6006 = CMS_BASE + 6;
	
	/**
	 * 系统编码长度必须在{min}到{max}之间
	 */
	int CMS_6007 = CMS_BASE + 7;

	/**
	 * 栏目名称必须在{min}到{max}之间
	 */
	int CMS_6008 = CMS_BASE + 8;

	/**
	 * 栏目描述必须在{min}到{max}之间
	 */
	int CMS_6009 = CMS_BASE + 9;

	/**
	 * 链接编码必须在{min}到{max}之间
	 */
	int CMS_6010 = CMS_BASE + 10;

	/**
	 * url必须在{min}到{max}之间
	 */
	int CMS_6011 = CMS_BASE + 11;
	
	/**
	 * SEO标题必须在{min}到{max}之间
	 */
	int CMS_6012 = CMS_BASE + 12;
	
	/**
	 * SEO关键词必须在{min}到{max}之间
	 */
	int CMS_6013 = CMS_BASE + 13;
	
	/**
	 * SEO描述必须在{min}到{max}之间
	 */
	int CMS_6014 = CMS_BASE + 14;

	/**
	 * 所属栏目不能为空
	 */
	int CMS_6015 = CMS_BASE + 15;

	/**
	 * 所属栏目已经存在文章，不允许添加栏目
	 */
	int CMS_6016 = CMS_BASE + 16;

	/**
	 * 参数名称必须在{min}到{max}之间
	 */
	int CMS_6017 = CMS_BASE + 17;
	
	
	/**
	 * 关键字必须在{min}到{max}之间
	 */
	int CMS_6018 = CMS_BASE + 18;
	
	/**
	 * 关键字URL必须在{min}到{max}之间
	 */
	int CMS_6019 = CMS_BASE + 19;
	
	/**
	 * 关键字已经存在
	 */
	int CMS_6020 = CMS_BASE + 20;
	
	/**
	 * 标签名字必须在{min}到{max}之间
	 */
	int CMS_6021 = CMS_BASE + 21;
	
	/**
	 * 标签URL必须在{min}到{max}之间
	 */
	int CMS_6022 = CMS_BASE + 22;
	
	
	/**
	 * 属性名字必须在{min}到{max}之间
	 */
	int CMS_6023 = CMS_BASE + 23;
	
	/**
	 * 属性描述必须在{min}到{max}之间
	 */
	int CMS_6024 = CMS_BASE + 24;
	
	/**
	 * 关键字已经存在
	 */
	int CMS_6025 = CMS_BASE + 25;
	
	/**
	 * 平台水印必须在{min}到{max}之间
	 */
	int CMS_6026 = CMS_BASE + 26;

	/**
	 * 文件未上传
	 */
	int CMS_6027 = CMS_BASE + 27;
	
	/**
	 * 模块系统编码必须在{min}到{max}之间
	 */
	int CMS_6028 = CMS_BASE + 28;
	
	/**
	 * 模块名称必须在{min}到{max}之间
	 */
	int CMS_6029 = CMS_BASE + 29;
	
	/**
	 * 模块编码必须在{min}到{max}之间
	 */
	int CMS_6030 = CMS_BASE + 30;

	/**
	 * 该栏目下有栏目，无法删除!
	 */
	int CMS_6031 = CMS_BASE + 31;

	/**
	 * 当前平台下有启用的栏目，不能删除该平台
	 */
	int CMS_6032 = CMS_BASE + 32;
	
	/**
	 * 当前平台下，URL已经存在
	 */
	int CMS_6033 = CMS_BASE + 33;
	
	/**
	 * 标签URL已经存在
	 */
	int CMS_6034 = CMS_BASE + 34;
	/**
	 * 当前平台下标签名称已经存在
	 */
	int CMS_6035 = CMS_BASE + 35;
	
	/**
	 * 所属客户端不能为空
	 */
	int CMS_6036 = CMS_BASE + 36;
	
	/**
	 * 发版标题不能为空
	 */
	int CMS_6037 = CMS_BASE + 37;
	
	/**
	 * 发版详细文案不能为空
	 */
	int CMS_6038 = CMS_BASE + 38;
	
	/**
	 * 发布的版本号不能为空
	 */
	int CMS_6039 = CMS_BASE + 39;
	
	/**
	 * 客户端的大小必须为数字!
	 */
	int CMS_6040 = CMS_BASE + 40;
	
	/**
	 * 同一平台下位置名称不能重复!
	 */
	int CMS_6041 = CMS_BASE + 41;
	/**
	 * 文章标题不能为空
	 */
	int CMS_6042 = CMS_BASE + 42;
	/**
	 * 文章链接不能为空
	 */
	int CMS_6043 = CMS_BASE + 43;
	/**
	 * 文章状态不能为空
	 */
	int CMS_6044 = CMS_BASE + 44;
	
	/**
	 * 同一客户端版本号不能重复！
	 */
	int CMS_6045 = CMS_BASE + 45;

	/**
	 * 请选择所属平台
	 */
	int CMS_6046 = CMS_BASE + 46;
	
	/**
	 * 请选择广告位置
	 */
	int CMS_6047 = CMS_BASE + 47;

	/**
	 * 标题必须在{min}到{max}之间
	 */
	int CMS_6048 = CMS_BASE + 48;
	
	/**
	 * 手机号码必须11位
	 */
	int CMS_6049 = CMS_BASE + 49;
	
	/**
	 *评论必须在{min}到{max}之间
	 */
	int CMS_6050 = CMS_BASE + 50;
	
	/**
	 *昵称必须在{min}到{max}之间
	 */
	int CMS_6051 = CMS_BASE + 51;
	/**
	 *路演id不能为空
	 */
	int CMS_6052 = CMS_BASE + 52;
	
	/**
	 * 不能从UBS取得手机加密文件
	 */
	int CMS_6053 = CMS_BASE + 53;

	/**
	 * 路演名称必须在{min}到{max}之间
	 */
	int CMS_6054 = CMS_BASE + 54;

	/**
	 * 主办方必须在{min}到{max}之间
	 */
	int CMS_6055 = CMS_BASE + 55;

	/**
	 * 主讲人必须在{min}到{max}之间
	 */
	int CMS_6056 = CMS_BASE + 56;

	/**
	 * 视频/音频封面名称必须在{min}到{max}之间
	 */
	int CMS_6057 = CMS_BASE + 57;

	/**
	 * 请上传视频/音频封面
	 */
	int CMS_6058 = CMS_BASE + 58;

	/**
	 * 请选择平台
	 */
	int CMS_6059 = CMS_BASE + 59;

	/**
	 * 请选择标签
	 */
	int CMS_6060 = CMS_BASE + 60;

	/**
	 * 请选择位置
	 */
	int CMS_6061 = CMS_BASE + 61;

	/**
	 * 请先保存路演
	 */
	int CMS_6062 = CMS_BASE + 62;
	
	/**
	 * 推荐路演不能为空
	 */
	int CMS_6063 = CMS_BASE + 63;
	
	/**
	 * 关联路演的产品不能为空
	 */
	int CMS_6064 = CMS_BASE + 64;
	
	/**
	 * 关联路演的产品不能为空
	 */
	int CMS_6065 = CMS_BASE + 65;
	
	/**
	 * 路演名称重复
	 */
	int CMS_6066 = CMS_BASE + 66;
	
	/**
	 * 推送的话题类型不能为空
	 */
	int CMS_6067 = CMS_BASE + 67;
	
	/**
	 * 答题人的手机号码不能为空！
	 */
	int CMS_6068 = CMS_BASE + 68;
	
	/**
	 * 答题的答案为空或者提交答案的方式不正确！请先答题！
	 */
	int CMS_6069 = CMS_BASE + 69;
	
	/**
	 * 答题的答案提交不正确！
	 */
	int CMS_6070 = CMS_BASE + 70;
	
	/**
	 * 你还没提交答卷或者尚未测评！
	 */
	int CMS_6071 = CMS_BASE + 71;
	
	/**
	 * 你已经提交答卷！在一定时间内请勿反复提交！
	 */
	int CMS_6072 = CMS_BASE + 72;
	
	/**
	 * 已经存在该权限等级！
	 */
	int CMS_6073 = CMS_BASE + 73;
	
	/**
	 * 标题或名称的对应项不存在或有误, 你可从提示的下拉框中选择对应项或者向招财猫官方反馈！
	 */
	int CMS_6074 = CMS_BASE + 74;
}
