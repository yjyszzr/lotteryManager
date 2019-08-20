package com.fh.service.lottery.userwithdraw.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.userwithdraw.UserWithdrawManager;
import com.fh.util.PageData;

/**
 * 说明： 提现模块 创建人：FH Q313596790 创建时间：2018-05-02
 * 
 * @version
 */
@Service("userwithdrawService")
public class UserWithdrawService implements UserWithdrawManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("UserWithdrawMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("UserWithdrawMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("UserWithdrawMapper.edit", pd);
	}

	@Override
	public void withdrawOperation(PageData pd) throws Exception {
		dao.update("UserWithdrawMapper.withdrawOperation", pd);
	}

	@Override
	public void saveUserWithdrawLog(PageData pd) throws Exception {
		dao.update("UserWithdrawMapper.saveUserWithdrawLog", pd);
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
		return (List<PageData>) dao.findForList("UserWithdrawMapper.datalistPage", page);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> temporaryWithdrawList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserWithdrawMapper.datalistPage2", page);
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
		return (List<PageData>) dao.findForList("UserWithdrawMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserWithdrawMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("UserWithdrawMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateRemarks(PageData pd) throws Exception {
		dao.update("UserWithdrawMapper.updateRemarks", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findByUserId(int userId) throws Exception {
		return (List<PageData>) dao.findForList("UserWithdrawMapper.findByUserId", userId);
	}

	@Override
	public BigDecimal findTotalAwardById(int userId) throws Exception {
		return (BigDecimal) dao.findForObject("UserWithdrawMapper.findTotalAwardById", userId);
	}
	/**
	 * 总提现金额和人数
	 *  
	 * @throws Exception
	 */
	@Override
	public PageData findTotalWithDraw(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserWithdrawMapper.findTotalWithDraw", pd);

	}

	@Override
	public PageData findByWithdrawSn(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserWithdrawMapper.findByWithdrawSn", pd);
	}
}
