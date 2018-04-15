package com.fh.service.dst.outboundnotice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.util.PageData;

/** 
 * 说明： 发货单列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("outboundnoticeService")
public class OutboundNoticeService implements OutboundNoticeManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OutboundNoticeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OutboundNoticeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OutboundNoticeMapper.edit", pd);
	}
	
	
	/**修改
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public Object updateStatus(PageData pd)throws Exception{
		return dao.update("OutboundNoticeMapper.updateStatus", pd);
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutboundNoticeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutboundNoticeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OutboundNoticeMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listByNoticeIds(String[] noticeIdArr) throws Exception {
		return (List<PageData>)dao.findForList("OutboundNoticeMapper.listByNoticeIds", noticeIdArr);
	}

	@Override
	public PageData orderConsigeen(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OutboundNoticeMapper.orderConsigeen", pd);
	}
	@Override
	public Object updatePrintCount(String[] noticeIdArr)throws Exception{
		return dao.update("OutboundNoticeMapper.updatePrintCount", noticeIdArr);
	}
}

