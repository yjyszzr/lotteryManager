package com.fh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileCheckUtils {
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(1)\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	};

	public static void main(String[] args) {
		System.out.println(isMobileNO("1w222222222"));
	}
}
