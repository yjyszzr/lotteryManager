package com.fh.entity.sms;

import java.io.Serializable;

public class ReqSmsCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public String mobile;
	public String smsType;
	public String verifyCode;
	public int needVerifyReg;

	public ReqSmsCodeEntity(String mobile, String code) {
		this.mobile = mobile;
		this.smsType = "3";
		this.verifyCode = code;
		this.needVerifyReg = 0;
	}
}
