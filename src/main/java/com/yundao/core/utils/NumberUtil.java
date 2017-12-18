package com.yundao.core.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang.math.NumberUtils;
//import java.text.NumberFormat;

public abstract class NumberUtil {

//	private static final NumberFormat nf = NumberFormat.getPercentInstance();
	
	private static final DecimalFormat percent = new DecimalFormat("##%");
	private static final DecimalFormat integer = new DecimalFormat("##");
	
	public static String format(double number) {
		return percent.format(number);
	}
	
	public static String parseIntegerFormat(double number) {
		return integer.format(number);
	}
	
	public static String parseIntegerFormat(String number) {
		try {
			if (!BooleanUtils.isBlank(number)) {
				return integer.format(NumberUtils.toDouble(number));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(parseIntegerFormat("150.0"));
	}
	
}
