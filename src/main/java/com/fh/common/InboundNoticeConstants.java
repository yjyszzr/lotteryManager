package com.fh.common;

public class InboundNoticeConstants {

	//采购入库
	public static final int TYPE_PURCHASE = 1;
	//生产入库
	public static final int TYPE_PRODUCT = 2;
	//调拨入库
	public static final int TYPE_ALLOCATION = 3;
	//退货入库
	public static final int TYPE_RETURN = 4;
	//退料入库
	public static final int TYPE_MATERIAL = 5;
	//拒收入库
	public static final int TYPE_REJECT = 6;
	//其他入库
	public static final int TYPE_OTHER = 7;

	//物流中
	public static final int STATUS_TRANSPORTING = 0;
	//已到货
	public static final int STATUS_ARRIVED = 1;
	//已完成
	public static final int STATUS_COMPLETE = 9;

	//明细质检常量
	public static class DetailQuality{
		//未质检
		public static final int STATUS_UNCHECK = 0;
		//已质检
		public static final int STATUS_CHECKED = 1;
	}
	
	//明细入库常量
	public static class DetailInbound{
		// 未入库
		public static final int STATUS_UNINBOUND = 0;
		// 已入库
		public static final int STATUS_INBOUNDED = 1;
	}
}
