package com.fh.common;

import java.util.HashMap;
import java.util.Map;

public class TextConfig {
	// public static final String URL_SMS_CODE =
	// "http://39.106.18.39:7071/sms/sendSmsCode";

	public static final String URL_SMS_CODE = "http://39.106.18.39:7071/sms/sendServiceSmsCode";

	// 手动派奖
	public static final String URL_MANUAL_AUDIT_CODE = "http://39.106.18.39:7071/user/account/batchUpdateUserAccount";

	// 大额提现
	public static final String URL_MANUAL_AWARD_CODE = "http://39.106.18.39:7076/cash/getcash";

	private static Map<String, String> BUSINESS_STATUS_MAP = new HashMap<String, String>();
	static {
		BUSINESS_STATUS_MAP.put(PurchaseConstants.BUSINESS_STATUS_NO_COMMIT + "", "待提交");
		BUSINESS_STATUS_MAP.put(PurchaseConstants.BUSINESS_STATUS_CREATE + "", "待审核");
		BUSINESS_STATUS_MAP.put(PurchaseConstants.BUSINESS_STATUS_AGREE + "", "审核通过");
		BUSINESS_STATUS_MAP.put(PurchaseConstants.BUSINESS_STATUS_REJECT + "", "驳回");
		BUSINESS_STATUS_MAP.put(PurchaseConstants.BUSINESS_STATUS_COMPLETE + "", "完成");
	}

	public static String getBusinessStatus(int status) {
		return BUSINESS_STATUS_MAP.get(status + "");
	}

	/**
	 * 获取“采购”或“生产”字样
	 * 
	 * @return
	 */
	public static String getPurchaseBsTypeText(int type) {
		if (PurchaseConstants.BUSINESS_TYPE_NORM == type) {
			return "采购";
		} else if (PurchaseConstants.BUSINESS_TYPE_PRODUCT == type) {
			return "生产";
		}
		return "";
	}
}
