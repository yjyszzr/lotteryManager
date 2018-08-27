package com.fh.enums;

public enum ThirdApiEnum {
	//聚合的定义参考https://www.juhe.cn/docs/api/id/208
	/*"rescode":"24",输入detail为1时返回匹配详情码,11:匹配,21:姓名不匹配,22:身份证不匹配,
	23:姓名身份证均不匹配,33:身份证和姓名不一致,24:不匹配,具体要素不匹配未知*/
	mobile_match_all(1, "完全匹配"),
    mobile_match(11, "完全匹配"),
    name_not_match(21, "姓名不匹配手机号"),
    id_not_match(22, "身份证不匹配手机号"),
    id_name_not_match(23, "姓名身份证均不匹配手机号"),
    id_name_not_(33, "身份证和姓名不一致"),
    not_match(24, "不匹配");

	private Integer code;
	private String msg;
	
	private ThirdApiEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getName(Integer index) {
		for(ThirdApiEnum lwd: ThirdApiEnum.values()) {
			if(lwd.getCode().equals(index)) {
				return lwd.getMsg();
			}
		}
		return null;
	}
	
	public static Integer getCode(String value) {
		for(ThirdApiEnum lwd: ThirdApiEnum.values()) {
			if(lwd.getMsg().equals(value)) {
				return lwd.getCode();
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
