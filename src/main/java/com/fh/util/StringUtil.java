package com.fh.util;
/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	/**
	 * 获取html中的文字内容
	 * 
	 */
    public static String stripHtml(String content) { 
    	// <p>段落替换为换行 
    	content = content.replaceAll("<p .*?>", "\r\n"); 
    	// <br><br/>替换为换行 
    	content = content.replaceAll("<br\\s*/?>", "\r\n"); 
    	// 去掉其它的<>之间的东西 
    	content = content.replaceAll("\\<.*?>", ""); 
    	// 还原HTML 
    	// content = HTMLDecoder.decode(content); 
    	return content; 
    }
    
    public static void main(String[] args) {
		System.out.println(strReplace("20181218111111298879", 8, 14, "XXXXXX"));
	}
    
    public static String strReplace(String srcStr, int startIndex, int endIndex, String repStr) {
    	try {
			if (StringUtil.isEmptyStr(srcStr)) return null;
			
			String str1 = "";
			try {
				str1 = srcStr.substring(0, startIndex);
			} catch (Exception e) {
				// TODO: handle exception
			}
					
			String str2 = "";
			try {
				str2 = srcStr.substring(endIndex);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if (null == repStr) repStr = "";
			
			return str1 + repStr + str2;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	
	
	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	public static boolean isEmptyStr(String str) {
		return null == str || "".equals(str.trim());
	}
	
}
