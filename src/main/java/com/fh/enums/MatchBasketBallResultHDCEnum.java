package com.fh.enums;

public enum MatchBasketBallResultHDCEnum {
	
	HHD_H(1, "主胜"),
	HHD_A(2, "客胜");
	
	private Integer code;
	private String msg;
	
	private MatchBasketBallResultHDCEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getName(int index) {
		for(MatchBasketBallResultHDCEnum lwd: MatchBasketBallResultHDCEnum.values()) {
			if(lwd.getCode() == index) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static Integer getCode(String value) {
		for(MatchBasketBallResultHDCEnum lwd: MatchBasketBallResultHDCEnum.values()) {
			if(lwd.getMsg().equals(value)) {
				return lwd.getCode();
			}
		}
		return null;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
