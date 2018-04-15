package com.fh.service.dst.outboundnoticestockbatch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.outboundnoticestockbatch.OutboundNoticeStockBatchManager;
import com.fh.util.PageData;

/** 
 * 说明： 出库通知批次
 * 创建人：FH Q313596790
 * 创建时间：2017-10-06
 * @version
 */
@Service("outboundnoticestockbatchService")
public class OutboundNoticeStockBatchService implements OutboundNoticeStockBatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OutboundNoticeStockBatchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OutboundNoticeStockBatchMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OutboundNoticeStockBatchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeStockBatchMapper.list", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeStockBatchMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutboundNoticeStockBatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OutboundNoticeStockBatchMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

