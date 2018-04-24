package com.fh.service.lottery.userbankmanager.impl;

import java.util.List;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.lottery.userbankmanager.UserBankManagerManager;

/** 
 * 说明： 用户银行列表
 * 创建人：FH Q313596790
 * 创建时间：2018-04-24
 * @version
 */
@Service("userbankmanagerService")
public class UserBankManagerService implements UserBankManagerManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserBankManagerMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UserBankManagerMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UserBankManagerMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserBankManagerMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserBankManagerMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserBankManagerMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserBankManagerMapper.deleteAll", ArrayDATA_IDS);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByUser(@Param("pd") PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserBankManagerMapper.listAllByUserId", pd);
	}
	
}

