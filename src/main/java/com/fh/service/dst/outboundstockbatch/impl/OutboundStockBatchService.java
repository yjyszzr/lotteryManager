package com.fh.service.dst.outboundstockbatch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.outboundstockbatch.OutboundStockBatchManager;
import com.fh.util.PageData;

/** 
 * 说明： 出库批次管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 * @version
 */
@Service("outboundstockbatchService")
public class OutboundStockBatchService implements OutboundStockBatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OutboundStockBatchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OutboundStockBatchMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OutboundStockBatchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OutboundStockBatchMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutboundStockBatchMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutboundStockBatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OutboundStockBatchMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	public PageData findByOutBoundCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutboundStockBatchMapper.findByOutBoundCode", pd);
	}

	@Override
	public List<PageData> listByOutboundCode(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("OutboundStockBatchMapper.listByOutboundCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryOutboundBatchByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OutboundStockBatchMapper.queryOutboundBatchByCode", pd);
	}
	
	
}

