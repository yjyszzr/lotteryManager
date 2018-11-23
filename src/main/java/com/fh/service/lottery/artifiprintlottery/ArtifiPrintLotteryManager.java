package com.fh.service.lottery.artifiprintlottery;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 手动出票接口
 * 创建人：FH Q313596790
 * 创建时间：2018-11-09
 * @version
 */
public interface ArtifiPrintLotteryManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	public List<PageData> findByTime(String currentTimeString)throws Exception;

	public PageData statisticalPrintData(PageData pd)throws Exception;

	public PageData statisticalPaidData(PageData pdPaid)throws Exception;

	public PageData statisticalRewardData(PageData pdReward)throws Exception;

	public void updatePrintStatisticalByOrderSn(String[] arrayDATA_IDS)throws Exception;

	public void updatePaidStatisticalByOrderSn(String[] arrayDATA_IDS)throws Exception;

	public void updateRewardStatisticalByOrderSn(String[] arrayDATA_IDS)throws Exception;

	public void updateRewardStatusByOrderSn(PageData pd)throws Exception;
}

