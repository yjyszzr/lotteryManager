package com.fh.service.erp.batch;

import java.util.List;

import com.fh.util.PageData;

public interface BatchManager {

	public PageData findByBatchCode(PageData pd)throws Exception;

	public List<PageData> listCheckByBatchCode(PageData pd) throws Exception;
	
	public List<PageData> listOutBoundByBatchCode(PageData pd) throws Exception;

	public List<PageData> listInboundByBatchCode(PageData pd) throws Exception;
}
