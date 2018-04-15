package com.fh.service.erp.warningexpire.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.warningexpire.WarningExpireManager;
import com.fh.service.erp.warningexpirebatch.WarningExpireBatchManager;
import com.fh.util.PageData;

/** 
 * 说明： 库存预警
 * 创建人：FH Q313596790
 * 创建时间：2017-12-06
 * @version
 */
@Service("warningexpireService")
public class WarningExpireService implements WarningExpireManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	@Resource(name="warningexpirebatchService")
	private WarningExpireBatchManager warningexpirebatchService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WarningExpireMapper.save", pd);
	}
	
	public void saveWarningData(List<PageData> warningExpires,List<PageData> warningExpireBatchs)throws Exception{
		dao.delete("WarningExpireMapper.deleteAllWarning", new PageData());
		if(CollectionUtils.isNotEmpty(warningExpires)) {
			dao.save("WarningExpireMapper.saveWarningExpire", warningExpires);
		}
		warningexpirebatchService.deleteAllBatch(new PageData());
		if(CollectionUtils.isNotEmpty(warningExpireBatchs)) {
			warningexpirebatchService.savewWarningExpireBatch(warningExpireBatchs);
		}
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("WarningExpireMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WarningExpireMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WarningExpireMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarningExpireMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> selectWarningExpire(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarningExpireMapper.selectWarningExpire", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> selectwarningExpireBatch(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarningExpireMapper.selectwarningExpireBatch", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WarningExpireMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WarningExpireMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

