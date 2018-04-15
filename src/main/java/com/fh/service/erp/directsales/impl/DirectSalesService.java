package com.fh.service.erp.directsales.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.directsales.DirectSalesManager;
import com.fh.util.PageData;

/** 
 * 说明： 内部直销管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 * @version
 */
@Service("directsalesService")
public class DirectSalesService implements DirectSalesManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**提交
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception{
		dao.update("DirectSalesMapper.update", pd);
	}
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DirectSalesMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DirectSalesMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DirectSalesMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DirectSalesMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DirectSalesMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DirectSalesMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DirectSalesMapper.deleteAll", ArrayDATA_IDS);
	}
	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DirectSalesMapper.findByCode", pd);
	}
	@Override
	public void updatePrice(PageData pd) throws Exception {
		dao.update("DirectSalesMapper.updatePrice", pd);
	}
	@Override
	public void updateShare(PageData pd) throws Exception {
		dao.update("DirectSalesMapper.updateShere", pd);
	}
	@Override
	public void outboundRewrite(PageData pd) throws Exception {
		dao.update("DirectSalesMapper.outbondrewrite", pd);
	}
	@Override
	public void updateAudit(PageData pd) throws Exception {
		dao.update("DirectSalesMapper.updateAudit", pd);
		
	}
	
}

