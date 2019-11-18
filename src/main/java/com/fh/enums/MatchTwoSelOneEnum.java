package com.fh.enums;

public enum MatchTwoSelOneEnum {
	TWO_SEL_ONE_30("30","主败"),
	TWO_SEL_ONE_31("31","主胜"),
	TWO_SEL_ONE_32("32","主不败"),
	TWO_SEL_ONE_33("33","主不胜");
	
	private String code;
    private String msg;

    private MatchTwoSelOneEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public static String getName(String index) {
		for(MatchTwoSelOneEnum lwd: MatchTwoSelOneEnum.values()) {
			if(lwd.getcode().equals(index)) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static String getCode(String value) {
		for(MatchTwoSelOneEnum lwd: MatchTwoSelOneEnum.values()) {
			if(lwd.getMsg().equals(value)) {
				return lwd.getcode();
			}
		}
		return null;
	}

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
