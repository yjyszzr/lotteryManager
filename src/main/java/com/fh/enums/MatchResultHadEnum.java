package com.fh.enums;

public enum MatchResultHadEnum {
	
	HAD_H(3, "主胜"),
	HAD_D(1, "平局"),
	HAD_A(0, "客胜");
	
	private Integer code;
	private String msg;
	
	private MatchResultHadEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getName(int index) {
		for(MatchResultHadEnum lwd: MatchResultHadEnum.values()) {
			if(lwd.getCode() == index) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static Integer getCode(String value) {
		for(MatchResultHadEnum lwd: MatchResultHadEnum.values()) {
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
