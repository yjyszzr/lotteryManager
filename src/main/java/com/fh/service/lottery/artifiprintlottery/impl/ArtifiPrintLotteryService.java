package com.fh.service.lottery.artifiprintlottery.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.lottery.artifiprintlottery.ArtifiPrintLotteryManager;

/** 
 * 说明： 手动出票
 * 创建人：FH Q313596790
 * 创建时间：2018-11-09
 * @version
 */
@Service("artifiprintlotteryService")
public class ArtifiPrintLotteryService implements ArtifiPrintLotteryManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ArtifiPrintLotteryMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ArtifiPrintLotteryMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ArtifiPrintLotteryMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArtifiPrintLotteryMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ArtifiPrintLotteryMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ArtifiPrintLotteryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ArtifiPrintLotteryMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findByTime(String currentTimeString) throws Exception {
		PageData pd =new PageData();
		pd.put("currentTime", currentTimeString);
		return (List<PageData>)dao.findForList("ArtifiPrintLotteryMapper.findByTime", pd);
	}
 
	@Override
	public PageData statisticalPrintData(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ArtifiPrintLotteryMapper.statisticalPrintData", pd);
	}

	@Override
	public PageData statisticalPaidData(PageData pdPaid) throws Exception {
		return (PageData)dao.findForObject("ArtifiPrintLotteryMapper.statisticalPaidData", pdPaid);
	}

	@Override
	public PageData statisticalRewardData(PageData pdReward) throws Exception {
		return (PageData)dao.findForObject("ArtifiPrintLotteryMapper.statisticalRewardData", pdReward);
	}

	@Override
	public void updatePrintStatisticalByOrderSn(String[] arrayDATA_IDS) throws Exception {
		dao.update("ArtifiPrintLotteryMapper.updatePrintStatisticalByOrderSn", arrayDATA_IDS);
		
	}

	@Override
	public void updatePaidStatisticalByOrderSn(String[] arrayDATA_IDS) throws Exception {
		dao.update("ArtifiPrintLotteryMapper.updatePaidStatisticalByOrderSn", arrayDATA_IDS);
	}

	@Override
	public void updateRewardStatisticalByOrderSn(String[] arrayDATA_IDS) throws Exception {
		dao.update("ArtifiPrintLotteryMapper.updateRewardStatisticalByOrderSn", arrayDATA_IDS);
		
	}

	@Override
	public void updateRewardStatusByOrderSn(PageData pd) throws Exception {
		dao.update("ArtifiPrintLotteryMapper.updateRewardStatusByOrderSn", pd);
	}
	
}

