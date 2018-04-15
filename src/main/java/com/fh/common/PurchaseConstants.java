package com.fh.common;

public class PurchaseConstants {
	/**
	 *  采购单据类型，现只考虑标准类型
	 */
	public static final int BILL_TYPE_NORM = 0;
	/**
	 * 采购类型-普通（标准）采购
	 */
	public static final int BUSINESS_TYPE_NORM = 0;
	/**
	 * 采购类型-生产采购
	 */
	public static final int BUSINESS_TYPE_PRODUCT = 1;

	// ------- 采购单状态----
	/**
	 * 采购单状态0 待提交
	 */
	public static final int BUSINESS_STATUS_NO_COMMIT = 0;
	/**
	 * 采购单状态1 待审
	 */
	public static final int BUSINESS_STATUS_CREATE = 1;
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
	// ------- 采购单状态 结束----
	

}
