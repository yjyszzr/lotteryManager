package com.fh.common;


public enum OrderShippingStatusEnum {  
	
	/*0 未处理
	1 已出库
	2 调度中
	3 运输中
	4 已收件
	5 已派件
	9 已签收*/
	TYPE0_INIT(0,"未处理"), 
	TYPE1_OUTBOUND(1,"已出库"), 
	TYPE2_DISPATCH(2,"调度中"),//目前为未用
	TYPE3_TRANSPORT(3,"运输中"),
	TYPE4_INDELIVERY(4,"已收件"),
	TYPE5_SEND(5,"已派件"),
	TYPE9_SIGN(9,"已签收");  
    // 成员变量  
    private String name;  
    private Integer code;  
    // 构造方法  
    private OrderShippingStatusEnum(Integer code,String name) {  
        this.name = name;  
        this.code = code;  
    } 
 // 普通方法  
    public static String getName(String code) {  
        for (OrderShippingStatusEnum c : OrderShippingStatusEnum.values()) {  
            if (code.equals(c.getCode().toString())) {  
                return c.name;  
            }  
        }  
        return "";  
    }  
    // 普通方法  
    public static String getName(int code) {  
        for (OrderShippingStatusEnum c : OrderShippingStatusEnum.values()) {  
            if (c.getCode() == code) {  
                return c.name;  
            }  
        }  
        return "";  
    }  
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public Integer getCode() {  
        return code;  
    }  
    public void setCode(Integer code) {  
        this.code = code;  
    }  
}  