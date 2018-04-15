package com.fh.service.inboundstockbatch.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.inboundstockbatch.InboundStockBatchManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;


/** 
 * 说明： 入库明细批次
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 * @version
 */
@Service("inboundstockbatchService")
public class InboundStockBatchService implements InboundStockBatchManager{

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
		dao.save("InboundStockBatchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InboundStockBatchMapper.delete", pd);
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
		dao.update("InboundStockBatchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InboundStockBatchMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InboundStockBatchMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InboundStockBatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InboundStockBatchMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryInboundBatchByCode(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("InboundStockBatchMapper.queryInboundBatchByCode", pd);
	}
	
}

