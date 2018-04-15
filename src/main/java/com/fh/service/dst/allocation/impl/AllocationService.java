package com.fh.service.dst.allocation.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.allocation.AllocationManager;
import com.fh.util.PageData;

/** 
 * 说明： 调拨单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("allocationService")
public class AllocationService implements AllocationManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AllocationMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AllocationMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AllocationMapper.edit", pd);
	}
	
	/**审核通过/驳回
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception{
		dao.update("AllocationMapper.update", pd);
	}
	
	public void updateStatusWithIn(PageData pd)throws Exception{
		dao.update("AllocationMapper.updateStatusWithIn", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AllocationMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AllocationMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AllocationMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AllocationMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateStatus(PageData pd)throws Exception{
		dao.update("AllocationMapper.updateStatus", pd);
	}
	
	
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AllocationMapper.findByCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> querylist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("AllocationMapper.querylist", page);
	}

	@Override
	public void updateReceiveTime(PageData pd) throws Exception {
		dao.update("AllocationMapper.updateReceiveTime", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByCodes(String[] noticeIdArr) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AllocationMapper.findByCodes", noticeIdArr);
		return list;
	}
	
}

