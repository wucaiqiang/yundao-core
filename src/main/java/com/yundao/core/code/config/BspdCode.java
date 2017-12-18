package com.yundao.core.code.config;

/**
 * 产品中心
 * 
 * @author wangchengyu
 *
 */
public interface BspdCode {
	// 5000~5299属于参数错误码
	// 5300~5599属于逻辑过程错误码
	
	//bspd 通用的错误码,需要给定默认的错误消息
	int BSPD_5999 = 5999;
	
	//逻辑错误码
	int BSPD_5300 = 5300;
	
	
	//参数错误码
	int BSPD_BASE = 5000;
	/**
	 * 密码错误
	 */
	int BSPD_5000 = BSPD_BASE;

	/**
	 * 公司全称长度必须在{min}到{max}之间
	 */
	int BSPD_5001 = BSPD_BASE + 1;

	/**
	 * 短信验证码错误
	 */
	int BSPD_5002 = BSPD_BASE + 2;

	/**
	 * 短信模板类型错误
	 */
	int BSPD_5003 = BSPD_BASE + 3;
	/**
	 * 人名称的长度必须在{min}到{max}之间
	 */
	int BSPD_5004 = BSPD_BASE + 4;

	/**
	 * 机构ID错误
	 */
	int BSPD_5005 = BSPD_BASE + 5;

	/**
	 * 联系人主从关系错误
	 */
	int BSPD_5006 = BSPD_BASE + 6;

	/**
	 * 产品专员ID错误
	 */
	int BSPD_5007 = BSPD_BASE + 7;

	/**
	 * 信托产品简称的长度必须在{min}到{max}之间
	 */
	int BSPD_5008 = BSPD_BASE + 8;

	/**
	 * 信托产品全称的长度必须在{min}到{max}之间
	 */
	int BSPD_5009 = 5009;

	/**
	 * 机构ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5010 = 5010;

	/**
	 * 产品期限的长度必须在{min}到{max}之间
	 */
	int BSPD_5011 = 5011;

	/**
	 * 认购起点的长度必须在{min}到{max}之间
	 */
	int BSPD_5012 = 5012;

	/**
	 * 付息方式的长度必须在{min}到{max}之间
	 */
	int BSPD_5013 = 5013;

	/**
	 * 产品类型的长度必须在{min}到{max}之间
	 */
	int BSPD_5014 = 5014;

	/**
	 * 投资领域的长度必须在{min}到{max}之间
	 */
	int BSPD_5015 = 5015;

	/**
	 * 省的长度必须在{min}到{max}之间
	 */
	int BSPD_5016 = 5016;

	/**
	 * 市的长度必须在{min}到{max}之间
	 */
	int BSPD_5017 = 5017;

	/**
	 * 发行开始日期的长度必须在{min}到{max}之间
	 */
	int BSPD_5018 = 5018;

	/**
	 * 发行结束日期的长度必须在{min}到{max}之间
	 */
	int BSPD_5019 = 5019;

	/**
	 * 融资主体的长度必须在{min}到{max}之间
	 */
	int BSPD_5020 = 5020;

	/**
	 * 销售状态的长度必须在{min}到{max}之间
	 */
	int BSPD_5021 = 5021;

	/**
	 * 资金用途的长度必须在{min}到{max}之间
	 */
	int BSPD_5022 = 5022;

	/**
	 * 还款来源的长度必须在{min}到{max}之间
	 */
	int BSPD_5023 = 5023;

	/**
	 * 风险控制的长度必须在{min}到{max}之间
	 */
	int BSPD_5024 = 5024;

	/**
	 * 产品亮点的长度必须在{min}到{max}之间
	 */
	int BSPD_5025 = 5025;

	/**
	 * 募集进度说明的长度必须在{min}到{max}之间
	 */
	int BSPD_5026 = 5026;

	/**
	 * 打款账户的长度必须在{min}到{max}之间
	 */
	int BSPD_5027 = 5027;

	/**
	 * 销售进度的长度必须在{min}到{max}之间
	 */
	int BSPD_5028 = 5028;

	/**
	 * 是否有小额的长度必须在{min}到{max}之间
	 */
	int BSPD_5029 = 5029;

	/**
	 * 已销售的长度必须在{min}到{max}之间
	 */
	int BSPD_5030 = 5030;

	/**
	 * 销售规模的长度必须在{min}到{max}之间
	 */
	int BSPD_5031 = 5031;

	/**
	 * 产品简称的长度必须在{min}到{max}之间
	 */
	int BSPD_5032 = 5032;

	/**
	 * 产品全称的长度必须在{min}到{max}之间
	 */
	int BSPD_5033 = 5033;

	/**
	 * 经理ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5034 = 5034;

	/**
	 * 机构ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5035 = 5035;

	/**
	 * 封闭期的长度必须在{min}到{max}之间
	 */
	int BSPD_5036 = 5036;

	/**
	 * 认购起点的长度必须在{min}到{max}之间
	 */
	int BSPD_5037 = 5037;

	/**
	 * 认购费的长度必须在{min}到{max}之间
	 */
	int BSPD_5038 = 5038;

	/**
	 * 赎回费的长度必须在{min}到{max}之间
	 */
	int BSPD_5039 = 5039;

	/**
	 * 管理费的长度必须在{min}到{max}之间
	 */
	int BSPD_5040 = 5040;

	/**
	 * 基金经理的长度必须在{min}到{max}之间
	 */
	int BSPD_5041 = 5041;

	/**
	 * 开放日的长度必须在{min}到{max}之间
	 */
	int BSPD_5042 = 5042;

	/**
	 * 销售日期开始的长度必须在{min}到{max}之间
	 */
	int BSPD_5043 = 5043;

	/**
	 * 销售日期结束的长度必须在{min}到{max}之间
	 */
	int BSPD_5044 = 5044;

	/**
	 * 销售状态的长度必须在{min}到{max}之间
	 */
	int BSPD_5045 = 5045;

	/**
	 * 收益分成的长度必须在{min}到{max}之间
	 */
	int BSPD_5046 = 5046;

	/**
	 * 投资标的的长度必须在{min}到{max}之间
	 */
	int BSPD_5047 = 5047;

	/**
	 * 募集进度说明的长度必须在{min}到{max}之间
	 */
	int BSPD_5048 = 5048;

	/**
	 * 已销售的长度必须在{min}到{max}之间
	 */
	int BSPD_5049 = 5049;

	/**
	 * 销售规模的长度必须在{min}到{max}之间
	 */
	int BSPD_5050 = 5050;

	/**
	 * 产品简称的长度必须在{min}到{max}之间
	 */
	int BSPD_5051 = 5051;

	/**
	 * 产品全称的长度必须在{min}到{max}之间
	 */
	int BSPD_5052 = 5052;

	/**
	 * 经理ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5053 = 5053;

	/**
	 * 机构ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5054 = 5054;

	/**
	 * 托管人的长度必须在{min}到{max}之间
	 */
	int BSPD_5055 = 5055;

	/**
	 * 托管费的长度必须在{min}到{max}之间
	 */
	int BSPD_5056 = 5056;

	/**
	 * 认购起点的长度必须在{min}到{max}之间
	 */
	int BSPD_5057 = 5057;

	/**
	 * 存续期_1的长度必须在{min}到{max}之间
	 */
	int BSPD_5058 = 5058;

	/**
	 * 存续期_2的长度必须在{min}到{max}之间
	 */
	int BSPD_5059 = 5059;

	/**
	 * 存续期_3的长度必须在{min}到{max}之间
	 */
	int BSPD_5060 = 5060;

	/**
	 * 基金类型的长度必须在{min}到{max}之间
	 */
	int BSPD_5061 = 5061;

	/**
	 * 认购费的长度必须在{min}到{max}之间
	 */
	int BSPD_5062 = 5062;

	/**
	 * 管理费的长度必须在{min}到{max}之间
	 */
	int BSPD_5063 = 5063;

	/**
	 * 销售日期开始的长度必须在{min}到{max}之间
	 */
	int BSPD_5064 = 5064;

	/**
	 * 销售日期结束的长度必须在{min}到{max}之间
	 */
	int BSPD_5065 = 5065;

	/**
	 * 销售状态的长度必须在{min}到{max}之间
	 */
	int BSPD_5066 = 5066;

	/**
	 * 收益分成的长度必须在{min}到{max}之间
	 */
	int BSPD_5067 = 5067;

	/**
	 * 投资范围的长度必须在{min}到{max}之间
	 */
	int BSPD_5068 = 5068;

	/**
	 * 募集进度说明的长度必须在{min}到{max}之间
	 */
	int BSPD_5069 = 5069;

	/**
	 * 产品亮点的长度必须在{min}到{max}之间
	 */
	int BSPD_5070 = 5070;

	/**
	 * 预期收益的长度必须在{min}到{max}之间
	 */
	int BSPD_5071 = 5071;

	/**
	 * 已销售的长度必须在{min}到{max}之间
	 */
	int BSPD_5072 = 5072;

	/**
	 * 目标规模的长度必须在{min}到{max}之间
	 */
	int BSPD_5073 = 5073;

	/**
	 * 供应商ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5074 = 5074;

	/**
	 * 产品ID的长度必须在{min}到{max}之间
	 */
	int BSPD_5075 = 5075;

	/**
	 * 是否有确认函的长度必须在{min}到{max}之间
	 */
	int BSPD_5076 = 5076;

	/**
	 * Reason的长度必须在{min}到{max}之间
	 */
	int BSPD_5077 = 5077;

	/**
	 * 产品类型的长度必须在{min}到{max}之间
	 */
	int BSPD_5078 = 5078;

	/**
	 * 是否税前的长度必须在{min}到{max}之间
	 */
	int BSPD_5079 = 5079;

	/**
	 * 税点的长度必须在{min}到{max}之间
	 */
	int BSPD_5080 = 5080;

	/**
	 * 最小范围的长度必须在{min}到{max}之间
	 */
	int BSPD_5081 = 5081;

	/**
	 * 最大范围的长度必须在{min}到{max}之间
	 */
	int BSPD_5082 = 5082;

	/**
	 * 备注的长度必须在{min}到{max}之间
	 */
	int BSPD_5083 = 5083;

	/**
	 * 是否删除的长度必须在{min}到{max}之间
	 */
	int BSPD_5084 = 5084;

	/**
	 * 收益率的长度必须在{min}到{max}之间
	 */
	int BSPD_5085 = 5085;

	/**
	 * 返佣点位的长度必须在{min}到{max}之间
	 */
	int BSPD_5086 = 5086;

	/**
	 * 前端是否税前的长度必须在{min}到{max}之间
	 */
	int BSPD_5087 = 5087;

	/**
	 * 前端费用的长度必须在{min}到{max}之间
	 */
	int BSPD_5088 = 5088;

	/**
	 * 后端是否税前的长度必须在{min}到{max}之间
	 */
	int BSPD_5089 = 5089;

	/**
	 * 后端分成的长度必须在{min}到{max}之间
	 */
	int BSPD_5090 = 5090;

	/**
	 * 其他费用是否税前的长度必须在{min}到{max}之间
	 */
	int BSPD_5091 = 5091;

	/**
	 * 其他费用的长度必须在{min}到{max}之间
	 */
	int BSPD_5092 = 5092;

	/**
	 * 部分已投项目的长度必须在{min}到{max}之间
	 */
	int BSPD_5093 = 5093;
	/**
	 * email错误
	 */
	int BSPD_5094 = BSPD_BASE + 94;

	/**
	 * qq错误
	 */
	int BSPD_5095 = BSPD_BASE + 95;

	/**
	 * 图片错误
	 */
	int BSPD_5096 = BSPD_BASE + 96;
	/**
	 * 法人代表或者授权代表错误
	 */
	int BSPD_5097 = BSPD_BASE + 97;
	/**
	 * 机构类型错误
	 */
	int BSPD_5098 = BSPD_BASE + 98;
	/**
	 * 发行机构简称有误
	 */
	int BSPD_5099 = BSPD_BASE + 99;
	/**
	 * 发行机构全称有误
	 */
	int BSPD_5100 = BSPD_BASE + 100;
	/**
	 * 发行机构拼音有误
	 */
	int BSPD_5101 = BSPD_BASE + 101;
	/**
	 * 发行机构类型有误
	 */
	int BSPD_5102 = BSPD_BASE + 102;
	/**
	 * 发行公司规模有误
	 */
	int BSPD_5103 = BSPD_BASE + 103;
	/**
	 * 产品公告标题有误
	 */
	int BSPD_5104 = BSPD_BASE + 104;
	/**
	 * 产品短信消息有误
	 */
	int BSPD_5105 = BSPD_BASE + 105;

	/**
	 * 产品供应类型错误
	 */
	int BSPD_5106 = BSPD_BASE + 106;
	/**
	 * 图片验证码错误
	 */
	int BSPD_5107 = BSPD_BASE + 107;

	/**
	 * 任务id有误
	 */
	int BSPD_110 = BSPD_BASE + 110;

	/**
	 * 流程定义id有误
	 */
	int BSPD_111 = BSPD_BASE + 111;
	
	/**
	 * 机构栏目不能为空
	 */
	int BSPD_112 = BSPD_BASE + 112;
	
	/**
	 * proId 和 proType 不能为空
	 */
	int BSPD_5113 = 5113;
	
	/**
	 * proType有错
	 */
	int BSPD_5114 = 5114;
	
	/**
	 * 基金经理名称错误
	 */
	int BSPD_5115 =  BSPD_BASE + 115;

	/**
	 * 基金经理拼音错误
	 */
	int BSPD_5116 =  BSPD_BASE + 116;
	
	/**
	 * 发行机构id不能为空
	 */
	int BSPD_5117 =  BSPD_BASE + 117;
	
	/**
	 * 发行机构简称已经存在
	 */
	int BSPD_5118 = BSPD_BASE + 118;
	
	/**
	 * 发行机构名称已经存在
	 */
	int BSPD_5119 = BSPD_BASE + 119;
	

	/**
	 * 供应商名称已经存在
	 */
	int BSPD_5120 = BSPD_BASE + 120;
	
	/**
	 * 只有阳光私募的才能选择代表产品
	 */
	int BSPD_5121 = BSPD_BASE + 121;
	
    /**
     * 代表产品不存在
     */
	int BSPD_5122 = BSPD_BASE + 122;
	
	/**
	 * 相关资质牌照不能为空
	 */
	int BSPD_5123 = BSPD_BASE + 123;
	
	/**
	 * 发行机构拼音只能为大小写字母
	 */
	int BSPD_5124 = BSPD_BASE + 124;
	
	/**
	 * 公司营业执照不能为空
	 */
	int BSPD_5125 = BSPD_BASE + 125;
	
	
	/**
	 * 组织机构照不能为空
	 */
	int BSPD_5126 = BSPD_BASE + 126;
	
}
