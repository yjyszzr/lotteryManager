package com.fh.entity.dto;

import java.util.List;

public class PayAllDTO {
	
	private List<PayLogDTO> payLogDTOList;
	
	private Double totalPayMoney;

	public List<PayLogDTO> getPayLogDTOList() {
		return payLogDTOList;
	}

	public void setPayLogDTOList(List<PayLogDTO> payLogDTOList) {
		this.payLogDTOList = payLogDTOList;
	}

	public Double getTotalPayMoney() {
		return totalPayMoney;
	}

	public void setTotalPayMoney(Double totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}

	

	
}
