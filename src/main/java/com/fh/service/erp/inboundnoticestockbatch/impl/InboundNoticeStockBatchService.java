package com.fh.service.erp.inboundnoticestockbatch.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明： 批次管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-18
 * @version
 */
@Service("inboundnoticestockbatchService")
public class InboundNoticeStockBatchService implements InboundNoticeStockBatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("createby", user.getUSER_ID());	//创建人
		pd.put("create_time", new Date());	//创建时间
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		Object status = pd.get("status");
		if (status==null) {
			pd.put("status", 0);	//更新时间
		}
		dao.save("InboundNoticeStockBatchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundNoticeStockBatchMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		dao.update("InboundNoticeStockBatchMapper.edit", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateStatus(PageData pd)throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());	//更新人
		pd.put("update_time", new Date());	//更新时间
		dao.update("InboundNoticeStockBatchMapper.updateStatus", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeStockBatchMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeStockBatchMapper.listAll", pd);
	}
	
	/**列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByPd(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundNoticeStockBatchMapper.listByPd", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundNoticeStockBatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundNoticeStockBatchMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public boolean isHasBatch(PageData queryBatch) throws Exception {
		PageData result = (PageData)dao.findForObject("InboundNoticeStockBatchMapper.isHasBatch", queryBatch);
		return result!=null;
	}
	
}

