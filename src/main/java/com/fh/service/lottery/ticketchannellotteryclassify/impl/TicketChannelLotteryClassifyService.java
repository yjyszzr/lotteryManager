package com.fh.service.lottery.ticketchannellotteryclassify.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.ticketchannellotteryclassify.TicketChannelLotteryClassifyManager;
import com.fh.util.PageData;

/**
 * 说明： 渠道彩种管理 创建人：FH Q313596790 创建时间：2018-07-31
 * 
 * @version
 */
@Service("ticketchannellotteryclassifyService")
public class TicketChannelLotteryClassifyService implements TicketChannelLotteryClassifyManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("TicketChannelLotteryClassifyMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("TicketChannelLotteryClassifyMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("TicketChannelLotteryClassifyMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("TicketChannelLotteryClassifyMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TicketChannelLotteryClassifyMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TicketChannelLotteryClassifyMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("TicketChannelLotteryClassifyMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateStatusByTicketChannelId(PageData pd) throws Exception {
		dao.update("TicketChannelLotteryClassifyMapper.updateStatusByTicketChannelId", pd);
	}

	@Override
	public void updateStatus(PageData pd) throws Exception {
		dao.update("TicketChannelLotteryClassifyMapper.updateStatus", pd);
	}

}
