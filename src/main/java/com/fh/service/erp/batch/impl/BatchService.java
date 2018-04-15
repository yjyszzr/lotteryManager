package com.fh.service.erp.batch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.service.erp.batch.BatchManager;
import com.fh.util.PageData;

@Service("batchService")
public class BatchService implements BatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	@Override
	public PageData findByBatchCode(PageData pd) throws Exception {
		return (PageData) dao.findForObject("BatchMapper.findByBatchCode", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listCheckByBatchCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BatchMapper.listCheckByBatchCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listOutBoundByBatchCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BatchMapper.listOutBoundByBatchCode", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listInboundByBatchCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BatchMapper.listInboundByBatchCode", pd);
	}
}
