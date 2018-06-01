package com.fh.enums;

public enum MatchBetTypeEnum {
	BET_TYPE_11("11","单关"),
	BET_TYPE_21("21","2串1"),
	BET_TYPE_31("31","3串1"),
	BET_TYPE_41("41","4串1"),
	BET_TYPE_51("51","5串1"),
	BET_TYPE_61("61","6串1"),
	BET_TYPE_71("71","7串1"),
	BET_TYPE_81("81","8串1");
	
	private String code;
    private String msg;

    private MatchBetTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public static String getName(String index) {
		for(MatchBetTypeEnum lwd: MatchBetTypeEnum.values()) {
			if(lwd.getcode().equals(index)) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static String getCode(String value) {
		for(MatchBetTypeEnum lwd: MatchBetTypeEnum.values()) {
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
