package com.fh.service.dst.scrapdetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.scrapdetail.ScrapDetailManager;
import com.fh.util.PageData;

/**
 * 说明： 报损明细管理 创建人：FH Q313596790 创建时间：2017-09-12
 * 
 * @version
 */
@Service("scrapdetailService")
public class ScrapDetailService implements ScrapDetailManager {

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ScrapDetailMapper.save", pd);
	}

	public boolean checkexists(PageData pd) throws Exception {
		int num = (int) dao.findForObject("ScrapDetailMapper.checkexists", pd);
		if (num > 0)
			return true;
		return false;
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ScrapDetailMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ScrapDetailMapper.edit", pd);
	}

	public void check(PageData pd) throws Exception {
		dao.update("ScrapDetailMapper.check", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ScrapDetailMapper.list", pd);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ScrapDetailMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ScrapDetailMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ScrapDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listByCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ScrapDetailMapper.listByCode", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByAllocationCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ScrapDetailMapper.findByAllocationCode", pd);
	}

}
