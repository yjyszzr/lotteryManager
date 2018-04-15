package com.fh.service.dst.purchasegroup.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.purchasegroup.PurchaseGroupManager;
import com.fh.util.PageData;

/** 
 * 说明： 采购组
 * 创建人：FH Q313596790
 * 创建时间：2017-10-13
 * @version
 */
@Service("purchasegroupService")
public class PurchaseGroupService implements PurchaseGroupManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PurchaseGroupMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PurchaseGroupMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PurchaseGroupMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseGroupMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseGroupMapper.listAll", pd);
	}
	/**根据用户和组类型查组
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllGPByUser(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PurchaseGroupMapper.listAllGPByUser", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PurchaseGroupMapper.findById", pd);
	}
	/**通过name获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PurchaseGroupMapper.findByName", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PurchaseGroupMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listAllUserByGP(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PurchaseGroupMapper.listAllUserByGP", pd);
	}

	@Override
	public void deleteGpUser(PageData pd) throws Exception {
		dao.delete("PurchaseGroupMapper.deleteGpUser", pd);
		
	}

	@Override
	public void batchInsertGpUser(List<PageData> pds) throws Exception {
		dao.save("PurchaseGroupMapper.batchInsertGpUser", pds);		
	}
	

	
	
}

