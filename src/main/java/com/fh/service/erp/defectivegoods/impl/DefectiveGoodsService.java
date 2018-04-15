package com.fh.service.erp.defectivegoods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.defectivegoods.DefectiveGoodsManager;
import com.fh.util.PageData;

/** 
 * 说明： erp
 * 创建人：FH Q313596790
 * 创建时间：2017-10-11
 * @version
 */
@Service("defectivegoodsService")
public class DefectiveGoodsService implements DefectiveGoodsManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	@SuppressWarnings("unchecked")
	public List<PageData> checkList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DefectiveGoodsMapper.checklistPage", page);
	}
	
	public void updateCheckStatus(PageData pd) throws Exception {
		dao.update("DefectiveGoodsMapper.updateCheckStatus", pd);
	}
	
	public void updateStatus(PageData pd)throws Exception{
		dao.update("DefectiveGoodsMapper.updateStatus", pd);
	}
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DefectiveGoodsMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DefectiveGoodsMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DefectiveGoodsMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DefectiveGoodsMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DefectiveGoodsMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DefectiveGoodsMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DefectiveGoodsMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void commit(PageData pd) throws Exception {
		dao.update("DefectiveGoodsMapper.commit", pd);
	}

	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DefectiveGoodsMapper.findByCode", pd);
	}

}

