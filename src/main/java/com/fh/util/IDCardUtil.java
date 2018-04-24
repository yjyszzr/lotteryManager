package com.fh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.http.util.TextUtils;

/****
 * 身份证相关信息
 * @author 123
 */
public class IDCardUtil {
	
	/****
	 * 获取性别信息
	 * @param idCard
	 * @return
	 */
	public static final String getGenderByIdCard(String idCard) {
		String gender = null;
		if(!TextUtils.isEmpty(idCard)) {
			 String id17 = idCard.substring(16, 17);    
             if (Integer.parseInt(id17) % 2 != 0) {    
                 gender = "男";    
             } else {    
                 gender = "女";    
             }    
		}
		return gender;
	}
	
	/****
	 * 根据身份证号获取年龄
	 * @param idcard
	 * @return
	 */
	public static final String getAgeByIdCard(String idcard) {
		String birthday = idcard.substring(6,14);    
        Date birthdate;
		try {
			birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			GregorianCalendar currentDay = new GregorianCalendar();    
	        currentDay.setTime(birthdate);    
	        int yearIDCard = currentDay.get(Calendar.YEAR);    
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");  
	        String year=simpleDateFormat.format(new Date());  
	        int age = Integer.parseInt(year)- yearIDCard;  
			return age+"";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
