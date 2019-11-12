package com.fh.entity.sms;

import java.io.Serializable;

import org.apache.http.util.TextUtils;

public class RspSmsCodeEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String code = "-1";
	public String data;
	public String msg;
	
	public boolean isSucc() {
		boolean succ = false;
		if(!TextUtils.isEmpty(code) && code.equals("0")) {
			succ = true;
		}
		return succ;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "code:" + code +" data:" + data +" msg:" + msg;
		return str;
	}
}
