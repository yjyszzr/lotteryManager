package com.fh.service.erp.inbound.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.util.PageData;

/** 
 * 说明： 入库单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("inboundService")
public class InboundService implements InboundManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	//inbound_notice_code
	/**通过inbound_notice_code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByInboundNoticeCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundMapper.findByInboundNoticeCode", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InboundMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InboundMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listInbound(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundMapper.listInbound", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundMapper.deleteAll", ArrayDATA_IDS);
	}
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllTimeSpan10(PageData pd)throws Exception{
		List<PageData> list= (List<PageData>)dao.findForList("InboundMapper.listAllTimeSpan10", pd);
	    return list;
	}
	
	@Override
	public List<PageData> listInboundDetail(PageData pd) throws Exception {
		@SuppressWarnings("unchecked")
		List<PageData> list= (List<PageData>)dao.findForList("InboundMapper.listInboundDetail", pd);
	    return list;
	}

	@Override
	public void updateSynStatus(PageData pd) throws Exception {
		dao.update("InboundMapper.updateSynStatus", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryByPurchase(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("InboundMapper.queryByPurchaseCode", pd);
	}
	
}

