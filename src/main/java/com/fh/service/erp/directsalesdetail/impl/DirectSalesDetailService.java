package com.fh.service.erp.directsalesdetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.directsalesdetail.DirectSalesDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 内部直销管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 * @version
 */
@Service("directsalesdetailService")
public class DirectSalesDetailService implements DirectSalesDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByCode(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("DirectSalesDetailMapper.listByCode", page);
	}
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DirectSalesDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DirectSalesDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DirectSalesDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DirectSalesDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DirectSalesDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DirectSalesDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DirectSalesDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	public void updatePrice(PageData pd)throws Exception{
		dao.update("DirectSalesDetailMapper.updatePrice", pd);
	}
	
	public PageData queryGoodSku(PageData pd)throws Exception{
		return (PageData) dao.findForObject("DirectSalesDetailMapper.queryGoodSku", pd);
	}
}

