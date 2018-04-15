package com.fh.util.express.enums;

public enum OutboundType {

	OUTBOUND_TYPE_0("0","领料出库"),
	OUTBOUND_TYPE_1("1","调拨出库"),
	OUTBOUND_TYPE_2("2","采退出库"),
	OUTBOUND_TYPE_3("3","报损出库"),
	OUTBOUND_TYPE_4("4","内销出库"),
	OUTBOUND_TYPE_5("5","销售出库"),
	OUTBOUND_TYPE_6("6","其他出库"),
	OUTBOUND_TYPE_7("7","不良品出库"),
	OUTBOUND_TYPE_8("8","生产退货出库"),
	OUTBOUND_TYPE_10("10","采购待检出库"),
	OUTBOUND_TYPE_11("11","采购总仓出库"),
	OUTBOUND_TYPE_12("12","生产待检出库"),
	OUTBOUND_TYPE_13("13","生产总仓出库"),
	OUTBOUND_TYPE_14("14","调拨物流出库"),
	OUTBOUND_TYPE_15("15","调拨待检出库"),
	OUTBOUND_TYPE_16("16","销售物流出库"),
	OUTBOUND_TYPE_20("20","俱乐部虚拟仓出库"),
	OUTBOUND_TYPE_21("21","物流拒收出库"),
	OUTBOUND_TYPE_22("22","拒收待检出库"),
	OUTBOUND_TYPE_30("30","销售出库"),
	OUTBOUND_TYPE_31("31","销售退货出库");
	private String code;
    private String name;

    private OutboundType(String code, String name) {
        this.code = code;
        this.name=name;
    }

    public static String getByCode(String code) {
        for (OutboundType ls : OutboundType.values()) {
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
