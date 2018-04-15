package com.fh.service.erp.warningquantityline.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.warningquantityline.WarningQuantityLineManager;
import com.fh.util.PageData;

/** 
 * 说明： 库存预警
 * 创建人：FH Q313596790
 * 创建时间：2017-12-07
 * @version
 */
@Service("warningquantitylineService")
public class WarningQuantityLineService implements WarningQuantityLineManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WarningQuantityLineMapper.save", pd);
	}
	
	public void saveWarningLineData(List<PageData> warningLines)throws Exception{
		dao.delete("WarningQuantityLineMapper.deleteWarningLine", new PageData());
		if(CollectionUtils.isNotEmpty(warningLines)) {
			dao.save("WarningQuantityLineMapper.saveWarningLineData", warningLines);
		}
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("WarningQuantityLineMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WarningQuantityLineMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WarningQuantityLineMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarningQuantityLineMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> selectWarningquantityline(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarningQuantityLineMapper.selectWarningquantityline", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WarningQuantityLineMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WarningQuantityLineMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

