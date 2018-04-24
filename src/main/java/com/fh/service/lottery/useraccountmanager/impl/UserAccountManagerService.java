package com.fh.service.lottery.useraccountmanager.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;

/** 
 * 说明： 流水相关
 * 创建人：FH Q313596790
 * 创建时间：2018-04-24
 * @version
 */
@Service("useraccountmanagerService")
public class UserAccountManagerService implements UserAccountManagerManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserAccountManagerMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UserAccountManagerMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UserAccountManagerMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserAccountManagerMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserAccountManagerMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserAccountManagerMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserAccountManagerMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public Double getTotalConsumByUserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return (Double)dao.findForObject("UserAccountManagerMapper.getTotalById", userId);
	}

	@Override
	public Double getTotalRechargeByUserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return (Double)dao.findForObject("UserAccountManagerMapper.getTotalRecharge", userId);
	}

	@Override
	public Double getTotalAwardByUserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return (Double)dao.findForObject("UserAccountManagerMapper.getTotalAward", userId);
	}

	@Override
	public Double getTotalRestByUserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return (Double)dao.findForObject("UserAccountManagerMapper.getTotalRest", userId);
	}
	
	
}

