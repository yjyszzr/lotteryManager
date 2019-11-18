package com.fh.enums;

public enum BasketBallHILOLeverlEnum {
	
	HILO_H_LEV1("1", "1-5分"), 
	HILO_H_LEV2("2", "6-10分"),
	HILO_H_LEV3("3", "11-15分"),
	HILO_H_LEV4("4", "16-20分"),
	HILO_H_LEV5("5", "21-25分"),
	HILO_H_LEV6("6", "26分+"),
	HILO_V_LEV1("7", "1-5分"), 
	HILO_V_LEV2("8", "6-10分"),
	HILO_V_LEV3("9", "11-15分"),
	HILO_V_LEV4("10", "16-20分"),
	HILO_V_LEV5("11", "21-25分"),
	HILO_V_LEV6("12", "26分+");

	
	private String code;
	private String msg;
	
	private BasketBallHILOLeverlEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getName(String index) {
		for(BasketBallHILOLeverlEnum lwd: BasketBallHILOLeverlEnum.values()) {
			if(lwd.getCode().equals(index)) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static String getCode(String value) {
		for(BasketBallHILOLeverlEnum lwd: BasketBallHILOLeverlEnum.values()) {
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
