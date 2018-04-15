package com.fh.service.dst.outboundnoticedetail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 发货明细单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("outboundnoticedetailService")
public class OutboundNoticeDetailService implements OutboundNoticeDetailManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OutboundNoticeDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OutboundNoticeDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OutboundNoticeDetailMapper.edit", pd);
	}
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateSendQuantity(PageData pd)throws Exception{
		dao.update("OutboundNoticeDetailMapper.updateSendQuantity", pd);
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeDetailMapper.listAll", pd);
	}
	
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutboundNoticeDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OutboundNoticeDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findAllByOutBoundNoticeCode(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeDetailMapper.findAllByOutBoundNoticeCode", pd);
	}
	
	
}

