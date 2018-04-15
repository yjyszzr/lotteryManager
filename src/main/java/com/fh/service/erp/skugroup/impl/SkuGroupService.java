package com.fh.service.erp.skugroup.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.skugroup.SkuGroupManager;
import com.fh.util.PageData;

/** 
 * 说明： 物料组合
 * 创建人：FH Q313596790
 * 创建时间：2017-11-23
 * @version
 */
@Service("skugroupService")
public class SkuGroupService implements SkuGroupManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SkuGroupMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SkuGroupMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SkuGroupMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SkuGroupMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SkuGroupMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SkuGroupMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SkuGroupMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByParentId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SkuGroupMapper.listByParentId", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("SkuGroupMapper.update", pd);
		
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findHas(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SkuGroupMapper.findHas", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listBySkuId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SkuGroupMapper.listBySkuId", pd);
	}
}

