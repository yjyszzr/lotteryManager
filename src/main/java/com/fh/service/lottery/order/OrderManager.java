package com.fh.service.lottery.order;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 订单模块接口 创建人：FH Q313596790 创建时间：2018-05-03
 * 
 * @version
 */
public interface OrderManager {

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

	public PageData findByOrderSn(String orderSn) throws Exception;

	public List<PageData> selectByTime(String format) throws Exception;

	public List<PageData> selectSuccessByTime(Page page) throws Exception;

	public List<PageData> getOrderList(Page page) throws Exception;

	public List<PageData> toDetail(PageData pd) throws Exception;

	public List<PageData> getFirstOrderList(Page page) throws Exception;
	
	public List<PageData> getFirstOrderAllList(Page page) throws Exception;
	
	public List<PageData> getOldUserOrderList(Page page) throws Exception;

	public List<PageData> getAgainOrderList(Page page) throws Exception;

	public List<PageData> getOrderAndDetail(Page page) throws Exception;

	public List<PageData> getOrderOfPlay(Page page) throws Exception;

	public List<PageData> getAmountForDayHour(Page page) throws Exception;

	public List<PageData> getMatchAmountByTime(Page page) throws Exception;

	public List<PageData> getTotalAmountByTime(Page page) throws Exception;

	public List<PageData> findPayLogList(List<PageData> varList) throws Exception;

	public List<PageData> getGroupByOrderStatus(Page page) throws Exception;

	public List<PageData> findByUserId(int userId) throws Exception;
}
