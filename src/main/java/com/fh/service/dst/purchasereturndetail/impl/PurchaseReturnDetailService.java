package com.fh.service.dst.purchasereturndetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.purchasereturndetail.PurchaseReturnDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 采购退货明细
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 * @version
 */
@Service("purchasereturndetailService")
public class PurchaseReturnDetailService implements PurchaseReturnDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PurchaseReturnDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PurchaseReturnDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PurchaseReturnDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseReturnDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseReturnDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PurchaseReturnDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PurchaseReturnDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editQuantity(PageData pd)throws Exception{
		dao.update("PurchaseReturnDetailMapper.editQuantity", pd);
	}

	@Override
	public List<PageData> queryNoCompletes(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PurchaseReturnDetailMapper.queryNoCompletes", pd);
	}
	
}

