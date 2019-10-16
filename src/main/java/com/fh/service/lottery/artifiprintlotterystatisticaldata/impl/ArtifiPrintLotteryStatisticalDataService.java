package com.fh.service.lottery.artifiprintlotterystatisticaldata.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.artifiprintlotterystatisticaldata.ArtifiPrintLotteryStatisticalDataManager;
import com.fh.util.PageData;

/**
 * 说明： 手动出票数据统计 创建人：FH Q313596790 创建时间：2018-11-16
 * 
 * @version
 */
@Service("artifiprintlotterystatisticaldataService")
public class ArtifiPrintLotteryStatisticalDataService implements ArtifiPrintLotteryStatisticalDataManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ArtifiPrintLotteryStatisticalDataMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ArtifiPrintLotteryStatisticalDataMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ArtifiPrintLotteryStatisticalDataMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ArtifiPrintLotteryStatisticalDataMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ArtifiPrintLotteryStatisticalDataMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ArtifiPrintLotteryStatisticalDataMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ArtifiPrintLotteryStatisticalDataMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> toExcelListAllForIds(String[] arrayDATA_IDS) throws Exception {
		return (List<PageData>) dao.findForList("ArtifiPrintLotteryStatisticalDataMapper.toExcelListAllForIds", arrayDATA_IDS);
	}

	@Override
	public PageData findByTime(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ArtifiPrintLotteryStatisticalDataMapper.findByTime", pd);
	}

	@Override
	public void savePrintStatistical(PageData pdPrintHasStatisticalA) throws Exception {
		dao.save("ArtifiPrintLotteryStatisticalDataMapper.savePrintStatistical", pdPrintHasStatisticalA);
	}

	@Override
	public void editPrintStatistical(PageData pdPrintHasStatisticalA) throws Exception {
		dao.update("ArtifiPrintLotteryStatisticalDataMapper.editPrintStatistical", pdPrintHasStatisticalA);
	}

	@Override
	public void savePaidStatistical(PageData pdPaidHasStatisticalA) throws Exception {
		dao.save("ArtifiPrintLotteryStatisticalDataMapper.savePaidStatistical", pdPaidHasStatisticalA);
	}

	@Override
	public void editPaidStatistical(PageData pdPaidHasStatisticalA) throws Exception {
		dao.update("ArtifiPrintLotteryStatisticalDataMapper.editPaidStatistical", pdPaidHasStatisticalA);
	}

	@Override
	public void saveRewardStatistical(PageData pdRewardHasStatisticalA) throws Exception {
		dao.save("ArtifiPrintLotteryStatisticalDataMapper.saveRewardStatistical", pdRewardHasStatisticalA);
	}

	@Override
	public void editRewardStatistical(PageData pdRewardHasStatisticalA) throws Exception {
		dao.update("ArtifiPrintLotteryStatisticalDataMapper.editRewardStatistical", pdRewardHasStatisticalA);
	}

	@Override
	public void updateUserMoney(PageData pdMoney) throws Exception {
		dao.update("ArtifiPrintLotteryStatisticalDataMapper.updateUserMoney", pdMoney);
	}

}
