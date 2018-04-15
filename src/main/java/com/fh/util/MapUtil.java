package com.fh.util;

public class MapUtil {

	public static PageData mapStringToPageData(String str){
		str=str.substring(1, str.length()-1);
		String[] strs=str.split(",");
		PageData pageData = new PageData();
		for (String string : strs) {
			String[] spls = string.split("=");
			String key=string.split("=")[0].trim();
			String value = null;
			if(spls.length > 1) {
				value=string.split("=")[1].trim();
			}
			pageData.put(key,value);
		}
		return pageData;
	}
}
