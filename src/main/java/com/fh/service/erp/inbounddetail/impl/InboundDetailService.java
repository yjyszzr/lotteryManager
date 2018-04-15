package com.fh.service.erp.inbounddetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.inbounddetail.InboundDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 入库明细单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("inbounddetailService")
public class InboundDetailService implements InboundDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InboundDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InboundDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	public void saveBatch(PageData pd)throws Exception{
		dao.save("InboundDetailMapper.saveBatch", pd);
	}
	
	
	public void saveBatchAllocLogistic(PageData pd)throws Exception{
		dao.save("InboundDetailMapper.saveBatchAllocLogistic", pd);
	}
	

	@SuppressWarnings("unchecked")
	public List<PageData> listByInboundCode(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundDetailMapper.listByInboundCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAllByInboundDetilCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("InboundDetailMapper.findAllByInBoundNoticeCode", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryByPurchase(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("InboundDetailMapper.queryByPurchaseCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryInboundDetilByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("InboundDetailMapper.queryInboundDetilByCode", pd);
	}
}

