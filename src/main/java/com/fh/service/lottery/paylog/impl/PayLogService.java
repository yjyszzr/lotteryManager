package com.fh.service.lottery.paylog.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.lottery.paylog.PayLogManager;

/** 
 * 说明： payLog
 * 创建人：FH Q313596790
 * 创建时间：2018-09-10
 * @version
 */
@Service("paylogService")
public class PayLogService implements PayLogManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PayLogMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PayLogMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PayLogMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PayLogMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PayLogMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PayLogMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PayLogMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/****
	 * 根据交易号查询交易记录
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findPLByOrderSns(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("PayLogMapper.findPLByOrderSns", pd);
	}
	
	
}

