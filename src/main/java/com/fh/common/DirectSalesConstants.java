package com.fh.common;

public class DirectSalesConstants {

	//内部正常销售
	public static final int TYPE_INNER = 0;
	//报损品销售
	public static final int TYPE_SCRAP = 1;
	//赔偿销售
	public static final int TYPE_REPARATION = 2;

	//未提交
	public static final int STATUS_UNCOMMIT = 0;
	//已提交未定价
	public static final int STATUS_COMMIT = 1;
	//已定价未分摊
	public static final int STATUS_FIX_PRICE = 2;
	//已完成
	public static final int STATUS_COMPLETE = 3;
}
