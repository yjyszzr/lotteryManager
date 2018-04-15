package com.fh.util.express.enums;

public enum InboundNoticeType {
	PURCHASE_IN(1,"采购到货通知"),
    PRODUCTION_IN(2,"生产到货通知"),
    ALLOCATION_IN(3,"调拨到货通知"),
    CUSTOMERRETURN_IN(4,"客户退货到货通知"),
    PRODUCTIONRETURN_IN(5,"领料退货到货通知单");

	private Integer code;
    private String name;
    // 到货通知单的类型1、采购到货通知、2、生产到货通知、3、调拨到货通知、4、客户退货到货通知,
    private InboundNoticeType(Integer code, String name) {
        this.code = code;
        this.name=name;
    }

    public static InboundNoticeType getByCode(short code) {
        for (InboundNoticeType ls : InboundNoticeType.values()) {
            if (ls.code == code) {
                return ls;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
