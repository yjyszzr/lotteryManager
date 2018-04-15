package com.fh.common;

public class InOutBoundType {

	// 采购待检入库
	public static final int PURCHASE_INBOUND_QUALITY = 0;
	// 采购总仓入库
	public static final int PURCHASE_INBOUND_CENTERWARE = 1;
	// 采购正品入库
	public static final int PURCHASE_INBOUND_GOOD = 2;
	// 采购不良品入库
	public static final int PURCHASE_INBOUND_BAD = 3;
	// 生产待检入库
	public static final int PRODUCTION_INBOUND_QUALITY = 4;
	// 采购总仓入库
	public static final int PRODUCTION_INBOUND_CENTERWARE = 5;
	// 调拨入库
	public static final int ALLOCATION_INBOUND = 6;
	// 调拨物流入库
	public static final int ALLOCATION_INBOUND_LOGISTIC = 7;
	// 调拨待检入库
	public static final int ALLOCATION_INBOUND_QUALITY = 8;
	// 销售物流入库
	public static final int ORDER_INBOUND_LOGISTIC = 9;
	// 报损入库
	public static final int SCRAP_INBOUND = 10;
	// 其他入库
	public static final int OTHER_INSTORAGE_INBOUND = 11;
	// 生产正品入库
	public static final int PRODUCTION_INBOUND_GOOD = 12;
	// 生产不良品入库
	public static final int PRODUCTION_INBOUND_BAD = 13;
	// 客户退货入库单
	public static final int CUSTOMER_INBOUND_RETURN = 14;
	// 领料退货入库单
	public static final int LINGLIAO_INBOUND_RETURN = 15;
	// 客户退货入库单
	public static final int CUSTOMER_INBOUND_REJECT = 16;
	//订单入库俱乐部仓
	public static final int ORDER_INBOUND_CLUB = 20;
	    //拒收待检入库
	public static final int ORDER_INBOUND_REJECT_QUALITY = 21;
		//拒收入库
	public static final int ORDER_INBOUND_REJECT = 22;
	//不良品库
	public static final int BAD_INBOUND_REJECT = 23;
	//销售退货入库
	public static final int ORDER_INBOUND_RETURN = 30;
	//销售采购入库
	public static final int ORDER_PURCHASE_INBOUND = 31;
	
	
	// 领料出库
	public static final int LINGLIAO_OUTBOUND = 0;
	// 调拨出库
	public static final int ALLOCATION_OUTBOUND = 1;
	// 采购退货出库
	public static final int PURCHASE_OUTBOUND_RETURN = 2;
	// 报损出库
	public static final int SCRAP_OUTBOUND =3;
	// 内部直销出库
	public static final int DIRECT_SALES_OUTBOUND = 4;
	// 销售出库
	public static final int ORDER_OUTBOUND =5;
	// 其他出库
	public static final int OTHER_OUTSTORAGE_OUTBOUND =6;
	// 不良品出库
	public static final int BAD_GOODS_OUTBOUND = 7;
	// 生产退货出库
	public static final int PRODUCT_OUTBOUND_RETURN = 8;
	// 采购待检出库
	public static final int PURCHASE_OUTBOUND_QUALITY = 10;
	// 采购总仓出库
	public static final int PURCHASE_OUTBOUND_CENTERWARE = 11;
	//生产待检出库
	public static final int PRODUCTION_OUTBOUND_QUALITY = 12;
	// 生产总仓出库
	public static final int PRODUCTION_OUTBOUND_CENTERWARE = 13;
	// 调拨物流出库
	public static final int ALLOCATION_OUTBOUND_LOGISTIC = 14;
	// 调拨待检出库
	public static final int ALLOCATION_OUTBOUND_QUALITY =15;
	// 销售物流出库
	public static final int ORDER_OUTBOUND_LOGISTIC = 16;
	//俱乐部仓出
	public static final int ORDER_OUTBOUND_CLUB = 20;
	//物流拒收出库
	public static final int ORDER_OUTBOUND_REJECT = 21;
	//拒收待检出库
	public static final int ORDER_OUTBOUND_REJECT_QUALITY = 22;
	
	//销售出库
	public static final int ORDER_GOOD_OUTBOUND = 30;
	//销售退货出库
	public static final int ORDER_OUTBOUND_RETURN = 31;
	
}
