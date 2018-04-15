package com.fh.service.erp.deliverylog.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.erp.deliverylog.DeliveryLogManager;
import com.fh.util.PageData;

/** 
 * 说明： 物流日志管理
 * 创建人：FH Q313596790
 * 创建时间：2017-11-01
 * @version
 */
@Service("syndeliverylogService")
public class SynDeliveryLogService implements DeliveryLogManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public Object save(PageData pd)throws Exception{
		return dao.save("SynDeliveryLogMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SynDeliveryLogMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SynDeliveryLogMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SynDeliveryLogMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SynDeliveryLogMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SynDeliveryLogMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SynDeliveryLogMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> synListAll(PageData pd) throws Exception {
		return null;
	}

	@Override
	public void updateStatus(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

