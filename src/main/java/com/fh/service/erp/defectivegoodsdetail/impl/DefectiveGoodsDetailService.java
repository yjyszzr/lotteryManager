package com.fh.service.erp.defectivegoodsdetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.defectivegoodsdetail.DefectiveGoodsDetailManager;
import com.fh.util.PageData;

/**
 * 说明： 不良品明细 创建人：FH Q313596790 创建时间：2017-10-11
 * 
 * @version
 */
@Service("defectivegoodsdetailService")
public class DefectiveGoodsDetailService implements DefectiveGoodsDetailManager {

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("DefectiveGoodsDetailMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("DefectiveGoodsDetailMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("DefectiveGoodsDetailMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DefectiveGoodsDetailMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DefectiveGoodsDetailMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DefectiveGoodsDetailMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("DefectiveGoodsDetailMapper.deleteAll", ArrayDATA_IDS);
	}


	@SuppressWarnings("unchecked")
	public List<PageData> listByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("DefectiveGoodsDetailMapper.listByCode", pd);
	}
	
	@Override
	public void updateStaus(PageData pd) throws Exception {
		dao.update("DefectiveGoodsDetailMapper.updateStaus", pd);
	}

	@Override
	public boolean checkexists(PageData pd) throws Exception {

		int num = (int) dao.findForObject("DefectiveGoodsDetailMapper.checkexists", pd);
		if (num > 0)
			return true;
		return false;

	}

	@Override
	public boolean existDetail(PageData pd) throws Exception {
		int num = (int) dao.findForObject("DefectiveGoodsDetailMapper.existDetail", pd);
		if (num > 0)
			return true;
		return false;
	}

}
