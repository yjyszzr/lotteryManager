package com.fh.service.lottery.dlwhiteblacklistmanager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.dlwhiteblacklistmanager.DlWhiteBlackListManagerManager;
import com.fh.util.PageData;

/**
 * 说明： 黑名单管理 创建人：FH Q313596790 创建时间：2018-09-26
 * 
 * @version
 */
@Service("dlwhiteblacklistmanagerService")
public class DlWhiteBlackListManagerService implements DlWhiteBlackListManagerManager {

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
		dao.save("DlWhiteBlackListManagerMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("DlWhiteBlackListManagerMapper.delete", pd);
	}

	@Override
	public void updateStatus(PageData pd) throws Exception {
		dao.delete("DlWhiteBlackListManagerMapper.updateStatus", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("DlWhiteBlackListManagerMapper.edit", pd);
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
		return (List<PageData>) dao.findForList("DlWhiteBlackListManagerMapper.datalistPage", page);
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
		return (List<PageData>) dao.findForList("DlWhiteBlackListManagerMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DlWhiteBlackListManagerMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("DlWhiteBlackListManagerMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("DlWhiteBlackListManagerMapper.updateAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> findByMobile(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DlWhiteBlackListManagerMapper.findByMobile", pd);
	}

}
