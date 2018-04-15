package com.fh.service.erp.warningexpirebatch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.warningexpirebatch.WarningExpireBatchManager;
import com.fh.util.PageData;

/** 
 * 说明： 库存预警批次详情
 * 创建人：FH Q313596790
 * 创建时间：2017-12-06
 * @version
 */
@Service("warningexpirebatchService")
public class WarningExpireBatchService implements WarningExpireBatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WarningExpireBatchMapper.save", pd);
	}
	
	public void savewWarningExpireBatch(List<PageData> warningExpireBatchs)throws Exception{
		dao.save("WarningExpireBatchMapper.savewWarningExpireBatch", warningExpireBatchs);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("WarningExpireBatchMapper.delete", pd);
	}
	
	public void deleteAllBatch(PageData pd)throws Exception{
		dao.delete("WarningExpireBatchMapper.deleteAllBatch", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WarningExpireBatchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WarningExpireBatchMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarningExpireBatchMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WarningExpireBatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WarningExpireBatchMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

