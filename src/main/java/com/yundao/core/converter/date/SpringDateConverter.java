package com.yundao.core.converter.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.converter.AbstractConverter;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;

/**
 * 日期转化类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringDateConverter extends AbstractConverter<String, Date> {

	private static Log log = LogFactory.getLog(SpringDateConverter.class);

	/**
	 * 日期格式，多个时以逗号分隔,yyyy-MM-dd HH:mm:ss
	 */
	private String pattern;

	/**
	 * 日期格式,yyyy-MM-dd
	 */
	private String patternmd;

	@Override
	public Date convert(String source) {
		String patterns = !BooleanUtils.isBlank(pattern) ? pattern : "";
		patterns = !BooleanUtils.isBlank(patternmd) ? patterns + "," + patternmd : patterns;
		if (!BooleanUtils.isBlank(patterns)) {
			String[] patternArray = patterns.split(CommonConstant.COMMA_SEMICOLON);
			for (String each : patternArray) {
				if (!BooleanUtils.isBlank(each)) {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat(each);
						dateFormat.setLenient(false);
						return dateFormat.parse(source);
					}
					catch (Exception e) {
						log.info("日期转化异常source=" + source);
					}
				}
			}
		}
		return null;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPatternmd() {
		return patternmd;
	}

	public void setPatternmd(String patternmd) {
		this.patternmd = patternmd;
	}

}
