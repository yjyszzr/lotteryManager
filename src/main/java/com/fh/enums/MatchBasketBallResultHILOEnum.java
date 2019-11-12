package com.fh.enums;

public enum MatchBasketBallResultHILOEnum {
	
	H_SCORE("1", "大分"),
	L_SCORE("2", "小分");
	
	private String code;
	private String msg;
	
	private MatchBasketBallResultHILOEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getName(String index) {
		for(MatchBasketBallResultHILOEnum lwd: MatchBasketBallResultHILOEnum.values()) {
			if(lwd.getCode().equals(index)) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static String getCode(String value) {
		for(MatchBasketBallResultHILOEnum lwd: MatchBasketBallResultHILOEnum.values()) {
			if(lwd.getMsg().equals(value)) {
				return lwd.getCode();
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
