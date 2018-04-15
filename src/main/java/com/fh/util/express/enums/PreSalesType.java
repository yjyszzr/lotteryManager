package com.fh.util.express.enums;

public enum PreSalesType {
	IS_NO("0","非预售商品"),
	IS_YES("1","预售商品"),
	IS_CB_NO("0","非组合商品"),
	IS_CB_YES("1","组合商品");
	
	private String code;
    private String name;
    // 0非预售商品,1预售商品
    private PreSalesType(String code, String name) {
        this.code = code;
        this.name=name;
    }

    public static String getByCode(String code) {
        for (PreSalesType ls : PreSalesType.values()) {
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
