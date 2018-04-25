package com.fh.entity.sms;

import java.io.Serializable;

public class ReqSmsCodeEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public String mobile;
	public String smsType;
	public String smsCode;
	
	public ReqSmsCodeEntity(String mobile,String code) {
		this.mobile = mobile;
		this.smsType = "0";
		this.smsCode = code;
	}
}
