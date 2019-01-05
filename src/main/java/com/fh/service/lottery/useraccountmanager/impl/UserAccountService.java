package com.fh.service.lottery.useraccountmanager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.dao.DaoSupport4;
import com.fh.entity.Page;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.util.PageData;

/**
 * 说明： 流水相关 创建人：FH Q313596790 创建时间：2018-04-24
 * 
 * @version
 */
@Service("useraccountService")
public class UserAccountService implements UserAccountManagerManager {

	@Resource(name = "daoSupport4")
	private DaoSupport4 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("UserAccountManagerMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("UserAccountManagerMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("UserAccountManagerMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserAccountManagerMapper.findById", pd);
	}
	
	
	
	
	public PageData getUserByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserAccountManagerMapper.getUserByUserId", pd);
	}
	
	
	
	@Override
	public List<PageData> findByUserIdStoreId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.findByUserIdStoreId", pd);
	}
 

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("UserAccountManagerMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public Double getTotalConsumByUserId(Integer userId) throws Exception {
		return (Double) dao.findForObject("UserAccountManagerMapper.getTotalById", userId);
	}

	@Override
	public Double totalWithdraw(Integer userId) throws Exception {
		return (Double) dao.findForObject("UserAccountManagerMapper.totalWithdraw", userId);
	}

	@Override
	public Double getTotalRechargeByUserId(Integer userId) throws Exception {
		return (Double) dao.findForObject("UserAccountManagerMapper.getTotalRecharge", userId);
	}

	@Override
	public Double getTotalAwardByUserId(Integer userId) throws Exception {
		return (Double) dao.findForObject("UserAccountManagerMapper.getTotalAward", userId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listForReward(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.listPageForReward", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findByUserId(int userId) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.findByUserId", userId);
	}

	@Override
	public double totalAwardForAll() throws Exception {
		return (Double) dao.findForObject("UserAccountManagerMapper.totalAwardForAll", null);
	}
	 
	@SuppressWarnings("unchecked")
	public List<PageData> findByProcessType(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.findByProcessType", page);
	}
	
/*	@SuppressWarnings("unchecked")
	public List<PageData> queryPayLogByPayOrderSn(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserAccountManagerMapper.queryPayLogByPayOrderSn", pd);
	}*/
	
	
}
