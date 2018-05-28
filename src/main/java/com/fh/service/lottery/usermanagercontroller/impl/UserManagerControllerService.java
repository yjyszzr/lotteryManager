package com.fh.service.lottery.usermanagercontroller.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.PageData;

/**
 * 说明： 用户列表 创建人：FH Q313596790 创建时间：2018-04-23
 * 
 * @version
 */
@Service("usermanagercontrollerService")
public class UserManagerControllerService implements UserManagerControllerManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("UserManagerControllerMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("UserManagerControllerMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("UserManagerControllerMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserManagerControllerMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("UserManagerControllerMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findByUserId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.findByUserId", pd);
	}

	@SuppressWarnings("unchecked")
	public Double findUserBonusByUserId(PageData pd) throws Exception {
		int userId = Integer.parseInt(pd.getString("user_id"));
		return (Double) dao.findForObject("UserManagerControllerMapper.findUserBonusByUserId", userId);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findAll() throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.findAll", null);
	}
}
