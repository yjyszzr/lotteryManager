package com.fh.service.erp.inboundnoticedetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 到货通知单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("inboundnoticedetailService")
public class InboundNoticeDetailService implements InboundNoticeDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateState(PageData pd)throws Exception{
		dao.update("InboundNoticeDetailMapper.updateState", pd);
	}
	
	/**列表listByWhere
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByWhere(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeDetailMapper.datalistByWhere", pd);
	}
	
	/**列表listPageByWhere
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPageByWhere(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeDetailMapper.datalistPageByWhere", page);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InboundNoticeDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundNoticeDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InboundNoticeDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundNoticeDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundNoticeDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveBatch(PageData pd)throws Exception{
		dao.save("InboundNoticeDetailMapper.saveBatch", pd);
	}
	
	public void editbatch(PageData pd)throws Exception{
		dao.update("InboundNoticeDetailMapper.editbatch", pd);
	}
	
	public void updateStatus(PageData pd)throws Exception{
		dao.update("InboundNoticeDetailMapper.updateStatus", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findlistByNoticeCode(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeDetailMapper.findlistByNoticeCode", pd);
	}
}

