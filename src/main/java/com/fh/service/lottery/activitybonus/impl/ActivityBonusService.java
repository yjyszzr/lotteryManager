package com.fh.service.lottery.activitybonus.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.activitybonus.ActivityBonusManager;
import com.fh.util.PageData;

/**
 * 说明： 优惠券 创建人：FH Q313596790 创建时间：2018-05-05
 * 
 * @version
 */
@Service("activitybonusService")
public class ActivityBonusService implements ActivityBonusManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ActivityBonusMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ActivityBonusMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ActivityBonusMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"ActivityBonusMapper.datalistPage", page);
	}

	/**
	 * query列表 by type
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryListByType(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ActivityBonusMapper.queryListByType", pd);
	}	
	
	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ActivityBonusMapper.listAll",
				pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ActivityBonusMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ActivityBonusMapper.deleteAll", ArrayDATA_IDS);
	}

}
