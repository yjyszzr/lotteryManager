package com.fh.service.lottery.usermanagercontroller;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * 说明： 用户列表接口 创建人：FH Q313596790 创建时间：2018-04-23
 *
 * @version
 */
public interface UserManagerControllerManager {
	public static final int STATUS_NORMAL = 0; // 正常状态
	public static final int STATUS_LOCK = 1; // 被锁定
	public static final int STATUS_FREEN = 2; // 被冻结

	/**
	 * 新增
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;


	/**
	 * 新增市场统计数据
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void saveMarketData(PageData pd) throws Exception;


	/**
	 * 今天的市场统计数据条数
	 *
	 * @param pd
	 * @throws Exception
	 */
	public Integer getmarketCountToday(PageData pd) throws Exception;

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
	 * 修改用户的交易开关
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void changeUserSwitch(PageData pd) throws Exception;

	/**
	 * 列表
	 *
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
	public List<PageData> listDetailTwo(Page page) throws Exception;

	/**
	 * 列表(全部)˙
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

	public List<PageData> findByUserId(PageData pd) throws Exception;

	public Double findUserBonusByUserId(PageData pd) throws Exception;

	public List<PageData> findAll() throws Exception;

	public List<PageData> getMarketList(Page page) throws Exception;

	public List<PageData> getRemainUserCount(Page page) throws Exception;
	/**认证并购彩
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRealAndOrder(Page page)throws Exception;
	/**注册并认证统计（购彩）
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRealAndRegister(Page page)throws Exception;

	/**注册并充值
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRegisterAndRecharge(Page page)throws Exception;

	/**根据手机号查询用户
	 * @param pd
	 * @throws Exception
	 */
	public PageData queryUserByMobile(String mobile)throws Exception;

	/**注册并购彩
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRegisterAndOrder(Page page)throws Exception;

	/**注册并购彩(复购)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRegisterAndAgainOrder(Page page)throws Exception;


	/** 获取销售人员业绩总体查询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerAchieveList(Page page)throws Exception;

	/** 获取销售人员每个月的业绩查询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerAchieveByMonthList(PageData page)throws Exception;


	/** 获取销售人员每个月录入的用户数量
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerWriteUserhList(PageData page)throws Exception;

	/** 获取销售人员下的人员每个月的购彩量
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerBuyMoneyhList(PageData pd)throws Exception;


	/** 获取销售人员下的人员每个月红包使用量
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerUserBonushList(PageData page)throws Exception;

	/** 获取销售人员下的人员红包使用总量
	 * @param pd
	 * @throws Exception
	 */
	public PageData sellerUserBonushTotal(List<String> varList)throws Exception;

	/**根据手机号集合查询userId集合
	 * @param List mobileList
	 * @throws Exception
	 */
	public List<Integer> getUserIdListByMobileList(List<String> mobileList)throws Exception;


	/**
	 * 查询多人月售彩额度
	 * @param PageData page
	 * @throws Exception
	 */
	public List<PageData> queryBuyByMonth(List<PageData> varList)throws Exception;


	/**
	 * 查询多人总售彩额度
	 * @param PageData page
	 * @throws Exception
	 */
	public List<PageData> queryBuyTotal(List<PageData> varList)throws Exception;


	/**
	 * 查询单个销售人员的顾客id
	 */
	public List<String> queryUserIdsBySellersId(String sellerId)throws Exception;

	/**
	 * 根据多个销售员id 查询多个顾客的userId
	 */
	public List<Integer> queryUserIdsByfirstAddUserId(List<Integer> sellerId);


	/**
	 * 根据时间查询市场数据
	 */
	public List<PageData> queryMarketDataByTime(Page page) throws Exception;

	/**
	 * 获取圣和彩店注册人员
	 */
	public List<PageData> getShenHeUserList(Page page)throws Exception;

	/**
	 * 根据手机号查询用户
	 */
	public PageData queryUserByMobile(PageData pagedata)throws Exception;

	/**
	 * 退款到不可提现金额
	 */
	public PageData refoundToUserMoneyLimit(PageData pagedata)throws Exception;

	/**
	 * 获取	用户最新的充值流水
	 */
	public List<PageData> queryUserAccountRechargeLatest(PageData pagedata)throws Exception;
}
