package com.fh.service.lottery.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.order.OrderManager;
import com.fh.util.PageData;

/**
 * 说明： 订单模块 创建人：FH Q313596790 创建时间：2018-05-03
 * 
 * @version
 */
@Service("orderService")
public class OrderService implements OrderManager {

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
		dao.save("OrderMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("OrderMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("OrderMapper.edit", pd);
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
		return (List<PageData>) dao.findForList("OrderMapper.datalistPage", page);
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
		return (List<PageData>) dao.findForList("OrderMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OrderMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByOrderSn(String orderSn) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findByOrderSn", orderSn);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> selectByTime(String format) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.selectByTime", format);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> selectSuccessByTime(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.selectSuccessByTime", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getOrderList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.datalistPage1", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> toDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.toDetail", pd);
	}

	/**
	 * 首购订单（仅收购订单的钱）
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getFirstOrderList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getFirstOrderList", page);
	}
	
	/**
	 * 新增购彩用户
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getFirstOrderAllList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getFirstOrderAllList", page);
	}
	
	/**
	 * 新增购彩用户
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getOldUserOrderList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getOldUserOrderList", page);
	}

	/**
	 * 复购订单
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getAgainOrderList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getAgainOrderList", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getOrderAndDetail(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getOrderAndDetail", page);
	}
	/**
	 * 彩票数据（玩法分组）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getOrderOfPlay(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getOrderOfPlay", page);

	}
	/**
	 * 根据小时汇总（折线图用）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAmountForDayHour(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getAmountForDayHour", page);

	}
	/**
	 * 赛事统计
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getMatchAmountByTime(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getMatchAmountByTime", page);

	}
	/**
	 * 根据时间汇总购彩人数、实付金额、红包金额、订单金额
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getTotalAmountByTime(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getTotalAmountByTime", page);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findPayLogList(List<PageData> varList) throws Exception {
		String[] orderSns = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			orderSns[i] = varList.get(i).getString("order_sn");
		}
		return (List<PageData>) dao.findForList("OrderMapper.findAllPayLog", orderSns);
	}
	/**
	 * 金额根据订单状态分组汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGroupByOrderStatus(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getGroupByOrderStatus", page);
	}
}
