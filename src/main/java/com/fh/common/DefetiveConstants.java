package com.fh.common;

public class DefetiveConstants {

	// ------- 不良状态----
	/**
	 * 采购单状态0 创建
	 */
	public static final int BUSINESS_STATUS_UNSUBMIT = 0;
	/**
	 * 采购单状态1 待审
	 */
	public static final int BUSINESS_STATUS_SUBMIT = 1;
	/**
	 * 审核状态-2 审核通过
	 */
	public static final int BUSINESS_STATUS_AGREE = 2;
	/**
	 * 审核状态-通过 8 驳回
	 */
	public static final int BUSINESS_STATUS_REJECT = 8;
	/**
	 * 审核状态 9 完成
	 */
	public static final int BUSINESS_STATUS_COMPLETE = 9;
}
