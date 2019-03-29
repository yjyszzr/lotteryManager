package com.fh.service.lottery.checklottery.impl;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.checklottery.CheckLotteryManager;
import com.fh.util.PageData;
@Service("checkLotteryService")
public class CheckLotteryService implements CheckLotteryManager{
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	@Override
	public List<String> findShopIDByUserId(String userId) throws Exception {
		return (List<String>) dao.findForList("CheckLotteryMapper.findShopIDByUserId", userId);
	}
	
	@Override
	public List<PageData> findShops(List<String> shopId) throws Exception {
		return (List<PageData>) dao.findForList("CheckLotteryMapper.findShops", shopId);
	}
	
	@Override
	public HashMap<String,Object> getNumAndMon(PageData pd) throws Exception{
		return (HashMap<String, Object>) dao.findForObject("CheckLotteryMapper.getNumAndMon", pd);
	}
	
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CheckLotteryMapper.datalistPage", page);
	}

	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CheckLotteryMapper.dataAllList", pd);
	}

	@Override
	public PageData findById(String orderno) throws Exception {
		return (PageData) dao.findForObject("CheckLotteryMapper.getOrderById", orderno);
	}

	@Override
	public void checkOrder(PageData pd) throws Exception {
		dao.update("CheckLotteryMapper.checkOrder", pd);
	}
}
