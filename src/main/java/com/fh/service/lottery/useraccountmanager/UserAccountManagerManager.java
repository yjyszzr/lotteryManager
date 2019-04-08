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
	
	public PageData getUserByMobile(PageData pd) throws Exception;

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
	
	public PageData getCountOrderByMobile(PageData pd) throws Exception; 
	
	public PageData getUserByUserId(PageData pd) throws Exception;
	
	public PageData getOtherUserId(PageData pd) throws Exception;

	public List<PageData> findByUserIdStoreId(PageData pd) throws Exception;
	
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
	public Double getTotalConsumByUserId(Integer userId) throws Exception;

	/***
	 * 获取个人消费资金总额
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Double getTotalConsumByUserId(PageData pd) throws Exception;


	/***
	 * 获取个人充值资金总额
	 *
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Double getTotalRecharge(PageData pd) throws Exception;


	/****
	 * 充值资金总额
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double getTotalRechargeByUserId(@Param("userId") Integer userId) throws Exception;

	/****
	 * 累计中奖
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double getTotalAwardByUserId(@Param("userId") Integer userId) throws Exception;

	public List<PageData> listForReward(Page page) throws Exception;

	public List<PageData> findByUserId(int userId) throws Exception;

	public double totalAwardForAll() throws Exception;

	/****
	 * 累计提现
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double totalWithdraw(Integer  userId) throws Exception;

	/****
	 * 累计提现
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Double totalWithdraw(PageData pd) throws Exception;

	/****
	 * 根据操作类型返回列表
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByProcessType(Page page) throws Exception;

	
	
	/****
	 * 根据交易号查询交易记录
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
/*	public List<PageData> queryPayLogByPayOrderSn(PageData pd) throws Exception;*/


	/**
	 * 根据手机号查询店铺用户总余额
	 */
	public Double getBalanceByMobile(PageData pd) throws Exception;






}
