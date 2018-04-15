package com.fh.util.express.enums;

public enum ShippingStatusEnum {

	SS_UNSHIPPED(0, "待发货"), 
	SS_SHIPPED(1, "已发货"), 
	SS_SHIPPED_PART(2, "发货中"), 
	SS_LOGISTICS(3, "已提交物流系统");
	private int code;
	private String name;

	private ShippingStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getByCode(int code) {
		for (ShippingStatusEnum ls : ShippingStatusEnum.values()) {
			if (ls.getCode()==code) {
				return ls.getName();
			}
		}
		return "";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
