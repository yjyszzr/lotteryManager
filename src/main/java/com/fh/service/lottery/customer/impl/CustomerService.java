package com.fh.service.lottery.customer.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.customer.CustomerManager;
import com.fh.util.PageData;

/** 
 * 说明： 销售
 * 创建人：FH Q313596790
 * 创建时间：2019-01-14
 * @version
 */
@Service("customerService")
public class CustomerService implements CustomerManager{

//	@Resource(name = "daoSupport")
//	private DaoSupport dao;
	
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	public PageData getUserByMobile(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.getUserByMobile", pd);
	}
	
	public PageData getUserByMobile11(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.getUserByMobile11", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CustomerMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CustomerMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CustomerMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustomerMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getOrdes(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustomerMapper.getOrdes", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustomerMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomerMapper.findById", pd);
	}
	
	public PageData setFirstPayTime(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomerMapper.setFirstPayTime", pd);
	}
	
	public PageData getCountOrderByMobile(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomerMapper.getCountOrderByMobile", pd);
	}
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CustomerMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateById(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateById", pd);
		
	}
	
}

