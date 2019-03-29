package com.fh.service.lottery.checklottery;

import java.util.HashMap;
import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface CheckLotteryManager {
	/**
	 * 通过当前登陆用户ID获取店铺ID
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<String> findShopIDByUserId(String userId) throws Exception;
	/**
	 * 通过店铺ID获取店铺
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findShops(List<String> shopId) throws Exception;
	/**
	 * 獲取當前查詢結果總量和總金額
	 * 
	 * @param page
	 * @throws Exception
	 */
	public HashMap<String,Object> getNumAndMon(PageData pd) throws Exception;
	/**
	 * 订单列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 订单列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;

	/**
	 * 通过订单编号获取订单详情
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(String orderno) throws Exception;
	/**
	 * 核查订单
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void checkOrder(PageData pd) throws Exception;
}
