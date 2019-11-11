package com.fh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class T {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		String start_add_time = "";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
		 Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		 cal_1.add(Calendar.MONTH, -1);
		 cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		 String firstDay = format.format(cal_1.getTime());
		 start_add_time = String.valueOf(format.parse(firstDay).getTime());   
		  
		 System.out.println("start_add_time:"+start_add_time);
	}

}
