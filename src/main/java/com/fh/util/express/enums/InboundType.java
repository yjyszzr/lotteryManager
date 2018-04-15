package com.fh.util.express.enums;

public enum InboundType {
	PURCHASE_INBOUND_QUALITY("0","采购待检入库"),
	PURCHASE_INBOUND_CENTERWARE("1","采购总仓入库"),
	PURCHASE_INBOUND_GOOD("2","采购正品入库"),
	PURCHASE_INBOUND_BAD("3","采购不良品入库"),
	PRODUCTION_INBOUND_QUALITY("4","生产待检入库"),
	PRODUCTION_INBOUND_CENTERWARE("5","生产总仓入库"),
	ALLOCATION_INBOUND("6","调拨入库"),
	ALLOCATION_INBOUND_LOGISTIC("7","调拨物流入库"),
	ALLOCATION_INBOUND_QUALITY("8","调拨待检入库"),
	ORDER_INBOUND_LOGISTIC("9","销售物流入库"),
	SCRAP_INBOUND("10","报损入库"),
	OTHER_INSTORAGE_INBOUND("11","其他入库"),
	PRODUCTION_INBOUND_GOOD("12","生产正品入库"),
	PRODUCTION_INBOUND_BAD("13","生产不良品入库"),
	CUSTOMER_INBOUND_RETURN("14","客户退货入库"),
	LINGLIAO_INBOUND_RETURN("15","生产退料入库"),
	CUSTOMER_INBOUND_REJECT("16","客户拒收入库"),
	ORDER_INBOUND_CLUB("20","俱乐部采购入库"),
	ORDER_INBOUND_REJECT_QUALITY("21","拒收待检入库"),
	ORDER_INBOUND_REJECT("22","拒收入库"),
	BAD_INBOUND_REJECT("23","不良品入库"),
	ORDER_INBOUND_RETURN("30","销售退货入库"),
	ORDER_PURCHASE_INBOUND("31","销售采购入库");
	private String code;
    private String name;
    // 到货通知单的类型1、采购到货通知、2、生产到货通知、3、调拨到货通知、4、客户退货到货通知,
    private InboundType(String code, String name) {
        this.code = code;
        this.name=name;
    }

    public static String getByCode(String code) {
        for (InboundType ls : InboundType.values()) {
            if (ls.getCode().equals(code)) {
                return ls.getName();
            }
        }
        return "";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
