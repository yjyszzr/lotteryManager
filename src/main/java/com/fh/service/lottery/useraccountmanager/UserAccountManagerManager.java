package com.fh.service.lottery.useraccountmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 流水相关接口 创建人：FH Q313596790 创建时间：2018-04-24
 * 
 * @version
 */
public interface UserAccountManagerManager {

	public static final int ACC_PRO_TYPE_ALL = 0;
	public static final int ACC_PRO_TYPE_AWARD = 1;// 奖金
	public static final int ACC_PRO_TYPE_RECHARGE = 2;// 充值
	public static final int ACC_PRO_TYPE_BUY = 3; // 购彩
	public static final int ACC_PRO_TYPE_CASH = 4; // 提现
	public static final int ACC_PRO_TYPE_REDPKG = 5;// 红包

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

	/***
	 * 获取个人消费资金总额
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Double getTotalConsumByUserId(@Param("userId") Integer userId)
			throws Exception;

	/****
	 * 充值资金总额
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double getTotalRechargeByUserId(@Param("userId") Integer userId)
			throws Exception;

	/****
	 * 累计中奖
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double getTotalAwardByUserId(@Param("userId") Integer userId)
			throws Exception;

	/****
	 * 获取个人钱包余额
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double getTotalRestByUserId(@Param("userId") Integer userId)
			throws Exception;

	public List<PageData> listForReward(Page page) throws Exception;
}
