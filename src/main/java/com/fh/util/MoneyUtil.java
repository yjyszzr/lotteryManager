package com.fh.util;

import java.text.DecimalFormat;

import org.apache.http.util.TextUtils;

/***
 * 金钱工具类
 * 
 */
public class MoneyUtil {

	public static final String getMoneyByFen(double val) {
		DecimalFormat df = new DecimalFormat("0.00");
		double data = val / (double) 100;
		return df.format(data);
	}

	public static final String getMoneyByFen(String val) {
		if (val != null && !TextUtils.isEmpty(val)) {
			Double d = Double.valueOf(val);
			return getMoneyByFen(d);
		}
		return "0.00";
	}

	/**
	 * 保留两位小数，四舍五入的一个最基本的方法
	 * 
	 * @param d
	 * @return
	 */
	public static double formatDouble(double d) {
		return (double) Math.round(d * 100) / 100;
	}
}
