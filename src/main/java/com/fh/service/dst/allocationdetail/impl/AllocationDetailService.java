package com.fh.service.dst.allocationdetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.allocationdetail.AllocationDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 调拨明细单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("allocationdetailService")
public class AllocationDetailService implements AllocationDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AllocationDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AllocationDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AllocationDetailMapper.edit", pd);
	}
	

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AllocationDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AllocationDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AllocationDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AllocationDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 修改数量
	 * @param pd
	 * @throws Exception
	 */
	public void updateQuantity(PageData pd)throws Exception{
		dao.update("AllocationDetailMapper.updateQuantity", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AllocationDetailMapper.listByCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByAllocationCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllocationDetailMapper.findByAllocationCode", pd);
	}
	
	
}

