package com.fh.service.lottery.artifiprintlotterystatisticaldata;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 手动出票数据统计接口 创建人：FH Q313596790 创建时间：2018-11-16
 * 
 * @version
 */
public interface ArtifiPrintLotteryStatisticalDataManager {

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

	public List<PageData> toExcelListAllForIds(String[] arrayDATA_IDS) throws Exception;

	public PageData findByTime(PageData pd) throws Exception;

	public void savePrintStatistical(PageData pdPrintHasStatisticalA) throws Exception;

	public void editPrintStatistical(PageData pdPrintHasStatisticalA) throws Exception;

	public void savePaidStatistical(PageData pdPaidHasStatisticalA) throws Exception;

	public void editPaidStatistical(PageData pdPaidHasStatisticalA) throws Exception;

	public void saveRewardStatistical(PageData pdRewardHasStatisticalA) throws Exception;

	public void editRewardStatistical(PageData pdRewardHasStatisticalA) throws Exception;

	public void updateUserMoney(PageData pdMoney) throws Exception;

	public PageData getByTime(String currentTime) throws Exception;

}
