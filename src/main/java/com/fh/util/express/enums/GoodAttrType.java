package com.fh.util.express.enums;

public enum GoodAttrType {
	RAW("0","原材料"),
	PACKING("1","包材"),
    PRODUCTION("2","成品");
	private String code;
    private String name;
    // 0:原材料 1:包材 2:成品
    private GoodAttrType(String code, String name) {
        this.code = code;
        this.name=name;
    }

    public static String getByCode(String code) {
        for (GoodAttrType ls : GoodAttrType.values()) {
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
