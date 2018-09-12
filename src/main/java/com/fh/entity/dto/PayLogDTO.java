package com.fh.entity.dto;

public class PayLogDTO {
	
	//交易号
	private String tradeNo;
	
	//收款金额
	private Double receiveAmout;
	
	//手续费
	private Double sxf;
	
	//是否已经支付
	private Integer isPaid;

	public Integer getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Integer isPaid) {
		this.isPaid = isPaid;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Double getReceiveAmout() {
		return receiveAmout;
	}

	public void setReceiveAmout(Double receiveAmout) {
		this.receiveAmout = receiveAmout;
	}

	public Double getSxf() {
		return sxf;
	}

	public void setSxf(Double sxf) {
		this.sxf = sxf;
	}

	
}
