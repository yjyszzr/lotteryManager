package com.fh.enums;

public enum SNBusinessCodeEnum {
	ORDER_SN(1, "订单sn"),
	ORDER_PARENT_SN(2, "订单父sn"),
	TICKET_SN(3, "出票单sn"),
	BONUS_SN(6, "红包sn"),
	ACCOUNT_SN(7, "流水单sn"),
	WITHDRAW_SN(8, "提现单sn"),
	RECHARGE_SN(9, "充值单sn"),
	PAY_SN(10, "支付单sn");

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private int code;
	private String msg;

	SNBusinessCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
