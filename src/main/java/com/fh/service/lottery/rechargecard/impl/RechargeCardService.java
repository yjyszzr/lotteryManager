package com.fh.service.lottery.rechargecard.impl;

import com.fh.dao.DaoSupport4;
import com.fh.entity.Page;
import com.fh.service.lottery.rechargecard.RechargeCardManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： RechargeCard
 * 创建人：FH Q313596790
 * 创建时间：2018-08-21
 * @version
 */
@Service("rechargecardService")
public class RechargeCardService implements RechargeCardManager{

	@Resource(name = "daoSupport4")
	private DaoSupport4 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RechargeCardMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("RechargeCardMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RechargeCardMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RechargeCardMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RechargeCardMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RechargeCardMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RechargeCardMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByRealValue(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RechargeCardMapper.findByRealValue", pd);
	}
	
}

