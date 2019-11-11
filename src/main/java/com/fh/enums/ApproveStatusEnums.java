package com.fh.enums;
/*
 * 审核状态定义
 */
public enum ApproveStatusEnums {
	
	Approving(0,"待审核"),
	Pass(1,"审核通过"),
	Refuse(2,"审核拒绝");
	
	private Integer code;
    private String msg;

    private ApproveStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public static String getNameByCode(Integer index) {
		for(ApproveStatusEnums lwd: ApproveStatusEnums.values()) {
			if(lwd.getcode() == index) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static Integer getCodeByName(String value) {
		for(ApproveStatusEnums lwd: ApproveStatusEnums.values()) {
			if(lwd.getMsg().equals(value)) {
				return lwd.getcode();
			}
		}
		return null;
	}

    public Integer getcode() {
        return code;
    }

    public void setcode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
