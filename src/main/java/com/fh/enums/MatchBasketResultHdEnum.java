package com.fh.enums;

public enum MatchBasketResultHdEnum {
	
	HD_H(1, "让分主胜"),
	HD_D(2, "让分客胜");
	
	private Integer code;
	private String msg;
	
	private MatchBasketResultHdEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getName(int index) {
		for(MatchBasketResultHdEnum lwd: MatchBasketResultHdEnum.values()) {
			if(lwd.getCode() == index) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static Integer getCode(String value) {
		for(MatchBasketResultHdEnum lwd: MatchBasketResultHdEnum.values()) {
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
