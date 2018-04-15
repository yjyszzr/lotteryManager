package com.fh.common;

public class ScrapConstants {

	// ------- 报损状态----
	/**
	 * 采购单状态0 创建
	 */
	public static final int BUSINESS_STATUS_UNSUBMIT = 0;
	/**
	 * 采购单状态1 待鉴定
	 */
	public static final int BUSINESS_STATUS_CREATE = 1;
	/**
	 * 采购单状态2 待审
	 */
	public static final int BUSINESS_STATUS_AUDIT = 2;
	/**
	 * 审核状态-3 审核通过
	 */
	public static final int BUSINESS_STATUS_AGREE = 3;
	/**
	 * 审核状态-通过 8 驳回
	 */
	public static final int BUSINESS_STATUS_REJECT = 8;
	/**
	 * 审核状态 9 完成
	 */
	public static final int BUSINESS_STATUS_COMPLETE = 9;
	// ------- 报损单状态 结束----
	/**
	 * 可用
	 */
	public static final int DEAL_SUGGEST_TYP_0 = 0;
	/**
	 * 不可用
	 */
	public static final int DEAL_SUGGEST_TYP_1 = 1;
	/**
	 * 其它
	 */
	public static final int DEAL_SUGGEST_TYP_2 = 2;
}
