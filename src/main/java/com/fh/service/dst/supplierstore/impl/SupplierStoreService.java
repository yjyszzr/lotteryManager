package com.fh.service.dst.supplierstore.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.supplierstore.SupplierStoreManager;
import com.fh.util.PageData;

/** 
 * 说明： 供应商仓库关系
 * 创建人：FH Q313596790
 * 创建时间：2017-09-10
 * @version
 */
@Service("supplierstoreService")
public class SupplierStoreService implements SupplierStoreManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SupplierStoreMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SupplierStoreMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SupplierStoreMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SupplierStoreMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SupplierStoreMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SupplierStoreMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SupplierStoreMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**findBySupplierAndStore
	 * @param pd
	 * @throws Exception
	 */
	public PageData findBySupplierAndStore(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SupplierStoreMapper.findBySupplierAndStore", pd);
	}
	
	/**供应商与仓库是否已关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public boolean isHas(PageData pd) throws Exception {
		return findBySupplierAndStore(pd)!=null;
	}
}

