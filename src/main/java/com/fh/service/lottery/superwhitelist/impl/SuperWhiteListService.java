package com.fh.service.lottery.superwhitelist.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport4;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.lottery.superwhitelist.SuperWhiteListManager;

/** 
 * 说明： 超级白名单
 * 创建人：FH Q313596790
 * 创建时间：2018-12-26
 * @version
 */
@Service("superwhitelistService")
public class SuperWhiteListService implements SuperWhiteListManager{

	@Resource(name = "daoSupport4")
	private DaoSupport4 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SuperWhiteListMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SuperWhiteListMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SuperWhiteListMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SuperWhiteListMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SuperWhiteListMapper.listAll", pd);
	}
	
	/**查流水 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAccount(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SuperWhiteListMapper.datalistAccountPage", page);
	}
	
	/**查流水（全部）
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAccountAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SuperWhiteListMapper.datalistAccount", pd);
	}
	
	public List<PageData> listStoreAll(PageData pd)throws Exception {
		return (List<PageData>)dao.findForList("SuperWhiteListMapper.datalistStore", pd);
	}
	
	public void recharge(PageData pd)throws Exception{
		dao.update("SuperWhiteListMapper.recharge", pd);
	}
	
	public void deduction(PageData pd)throws Exception{
		dao.update("SuperWhiteListMapper.deduction", pd);
	}
	
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SuperWhiteListMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SuperWhiteListMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

