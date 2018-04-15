package com.fh.service.dst.otheroutstoragedetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.otheroutstoragedetail.OtherOutstorageDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 其他出库明细
 * 创建人：FH Q313596790
 * 创建时间：2017-09-20
 * @version
 */
@Service("otheroutstoragedetailService")
public class OtherOutstorageDetailService implements OtherOutstorageDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OtherOutstorageDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OtherOutstorageDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OtherOutstorageDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OtherOutstorageDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OtherOutstorageDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OtherOutstorageDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OtherOutstorageDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OtherOutstorageDetailMapper.listByCode", pd);
	}
	
}

