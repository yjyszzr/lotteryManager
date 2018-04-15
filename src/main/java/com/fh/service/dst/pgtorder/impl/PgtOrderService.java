package com.fh.service.dst.pgtorder.impl;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.dst.pgtorder.PgtOrderManager;
import com.fh.util.PageData;

/** 
 * 说明： pgt订单
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 * @version
 */
@Service("pgtorderService")
@EnableScheduling
public class PgtOrderService implements PgtOrderManager{
	Logger log = LoggerFactory.getLogger(getClass());
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao3;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PgtOrderMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PgtOrderMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PgtOrderMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PgtOrderMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PgtOrderMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PgtOrderMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PgtOrderMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void boundOrdersToTask(PageData pd) throws Exception {
		dao.update("PgtOrderMapper.boundOrdersToTask", pd);
		
	}
	
	/**
	 * 同步物流信息到商之翼
	 * @throws Exception 
	 * 
	 */
	// 每2分钟执行
	/*@Scheduled(cron = "0 0/2 * * * ?")
	public void syncLogisticsToSzy() throws Exception {
		//1天前的
		Date beginTime = new Date(System.currentTimeMillis()-1000*60*60*24);
		PageData para  = new PageData();
		para.put("beginTime", beginTime);
		
		List<PageData> pgtOrders = (List<PageData> )dao.findForList("PgtOrderMapper.listLatestShiped", para);
		log.info("同步物流订单数量："+pgtOrders.size());
		for(PageData pd : pgtOrders) {
			PageData szyOrder  = new PageData();
			szyOrder.put("order_id", szyOrder.get("order_id"));
			szyOrder.put("express_sn", szyOrder.get("logistics_code"));
			szyOrder.put("express_name", szyOrder.get("logistics_company_name"));
			szyOrder.put("express_id", szyOrder.get("logistics_company_id"));
			szyOrder.put("is_selfship", szyOrder.get("is_selfship"));
			dao3.update("SzyOrderMapper.edit", szyOrder);
			PageData newPd = new PageData();
			newPd.put("pgt_order_id", pd.getString("pgt_order_id"));
			newPd.put("isShippingSynced", "1");
		    dao.update("PgtOrderMapper.edit", newPd);
		}
		log.info("同步物流订完成");
	}*/
}

