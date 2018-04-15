package com.fh.service.erp.purchasematerialdetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.purchasematerialdetail.PurchaseMaterialDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 生产领料明细
 * 创建人：FH Q313596790
 * 创建时间：2017-10-13
 * @version
 */
@Service("purchasematerialdetailService")
public class PurchaseMaterialDetailService implements PurchaseMaterialDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PurchaseMaterialDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PurchaseMaterialDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PurchaseMaterialDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseMaterialDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseMaterialDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PurchaseMaterialDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PurchaseMaterialDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**列表(通过生产明细Id)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseMaterialDetailMapper.listById", pd);
	}

	@Override
	public void increaseQuantity(PageData pd) throws Exception {
		dao.update("PurchaseMaterialDetailMapper.increaseQuantity", pd);
	}
}

