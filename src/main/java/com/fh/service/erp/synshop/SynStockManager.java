package com.fh.service.erp.synshop;

public interface SynStockManager {
	
	public void SynInbound() throws Exception;
	public void SynOutbound() throws Exception;
	public void SynOrderShippingStatus() throws Exception;
}
