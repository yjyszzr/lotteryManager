package com.fh.service.dst.purchasedetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 采购单详情
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("purchasedetailService")
public class PurchaseDetailService implements PurchaseDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PurchaseDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PurchaseDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PurchaseDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseDetailMapper.datalistPage", page);
	}
	
	/**查询待生产加工的生产明细
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> dataPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseDetailMapper.detaillistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseDetailMapper.listAll", pd);
	}
	
	/**根据条件查询
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByParam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseDetailMapper.listByParam", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PurchaseDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PurchaseDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	public void qualityCheck(PageData pd) throws Exception {
		dao.update("PurchaseDetailMapper.qualityCheck", pd);
	}
	/**
	 *  增量修改数据，发货说量，可发货数量
	 * @param pd
	 * @throws Exception
	 */
	public void updateQuantityForAdd(PageData pd) throws Exception {
		dao.update("PurchaseDetailMapper.updateQuantityForAdd", pd);
	}
	
	public void updateBadQuantityForAdd(PageData pd) throws Exception {
		dao.update("PurchaseDetailMapper.updateBadQuantityForAdd", pd);
	}
	
}

