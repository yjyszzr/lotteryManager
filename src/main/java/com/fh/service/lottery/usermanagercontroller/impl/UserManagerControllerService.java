package com.fh.service.lottery.usermanagercontroller.impl;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明： 用户列表 创建人：FH Q313596790 创建时间：2018-04-23
 *
 * @version
 */
@Service("usermanagercontrollerService")
public class UserManagerControllerService implements UserManagerControllerManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("UserManagerControllerMapper.save", pd);
	}


	/**
	 * 新增市场统计数据
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void saveMarketData(PageData pd) throws Exception {
		dao.save("UserManagerControllerMapper.saveMarketData", pd);
	}

	/**
	 * 根据时间查询市场统计数据
	 *
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> queryMarketDataByTime(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.queryMarketDataByTime", page);
	}

	/**
	 * 删除
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("UserManagerControllerMapper.delete", pd);
	}

	/**
	 * 修改
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("UserManagerControllerMapper.edit", pd);
	}

	/**
	 * 修改
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void changeUserSwitch(PageData pd) throws Exception {
		dao.update("UserManagerControllerMapper.changeUserSwitch", pd);
	}

	/**
	 * 列表
	 *
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.datalistPage", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listDetailTwo(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.datalistPageTwo", page);
	}

	/**
	 * 列表(全部)
	 *
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 *
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserManagerControllerMapper.findById", pd);
	}

	/**
	 * 批量删除
	 *
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("UserManagerControllerMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findByUserId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.findByUserId", pd);
	}

	@SuppressWarnings("unchecked")
	public Double findUserBonusByUserId(PageData pd) throws Exception {
		int userId = Integer.parseInt(pd.getString("user_id"));
		return (Double) dao.findForObject("UserManagerControllerMapper.findUserBonusByUserId", userId);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findAll() throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.findAll", null);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> getMarketList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.marketlist", page);
	}


	public Integer getmarketCountToday(PageData page) throws Exception {
		return (Integer) dao.findForObject("UserManagerControllerMapper.marketCountToday", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> getRemainUserCount(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.remainUserCount", page);
	}

	/**
	 * 根据用户手机号查询用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryUserByMobile(String mobile) throws Exception {
		return (PageData)dao.findForObject("UserManagerControllerMapper.getUserByMobile", mobile);
	}


	/**注册并认证统计（购彩）
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRealAndRegister(Page page) throws Exception {
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.getRealAndRegister", page);
	}
	/**注册并充值
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRegisterAndRecharge(Page page) throws Exception {
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.getRegisterAndRecharge", page);
	}
	/**注册并购彩
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRegisterAndOrder(Page page) throws Exception {
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.getRegisterAndOrder", page);
	}

	/**注册并购彩(复购)
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRegisterAndAgainOrder(Page page) throws Exception {
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.getRegisterAndAgainOrder", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getRealAndOrder(Page page) throws Exception {
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.getRealAndOrder", page);
	}

	@Override
	public List<Integer> getUserIdListByMobileList(List mobileList) throws Exception {
		return (List<Integer>)dao.findForList("UserManagerControllerMapper.getUserIdListByMobileList", mobileList);
	}


	/** 获取销售人员业绩总体查询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerAchieveList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserManagerControllerMapper.sellerAchieveList", page);
	}


	/** 获取销售人员每个月的业绩查询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerAchieveByMonthList(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.sellerAchieveByMonthList", page);
	}

	/** 获取销售人员每个月录入的用户数量
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerWriteUserhList(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.sellerWriteUserhList", page);
	}

	/** 获取销售人员下的人员每个月的购彩量
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerBuyMoneyhList(List<PageData> varList)throws Exception{
		String[] userIdList = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			userIdList[i] = varList.get(i).getString("user_id");
		}
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.sellerBuyMoneyhList", userIdList);
	}


	/** 获取销售人员下的人员每个月的红包量
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> sellerUserBonushList(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.sellerUserBonushList", page);
	}

	/** 获取圣和彩店注册人员
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getShenHeUserList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.datalistPageThree", page);
	}

	/** 获取	用户最新的充值流水
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> queryUserAccountRechargeLatest(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.queryUserAccountRechargeLatest", pd);
	}

	/** 获取销售人员下的人员红包总量
	 * @param pd
	 * @throws Exception
	 */
	public PageData sellerUserBonushTotal(List<String> varList)throws Exception{
		String[] userIdList = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			userIdList[i] = varList.get(i);
		}
		return (PageData)dao.findForObject("UserManagerControllerMapper.sellerUserBonushTotal", userIdList);
	}


	/**
	 * 查询月售彩额度
	 * @param PageData page
	 * @throws Exception
	 */
	public List<PageData> queryBuyByMonth(List<PageData> varList)throws Exception{
		String[] userIdList = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			userIdList[i] = varList.get(i).getString("user_id");
		}
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.queryBuyByMonth", userIdList);
	}

	/**
	 * 查询多人总售彩额度
	 * @param PageData page
	 * @throws Exception
	 */
	public List<PageData> queryBuyTotal(List<PageData> varList)throws Exception{
		String[] userIdList = new String[varList.size()];
		for (int i = 0; i < varList.size(); i++) {
			userIdList[i] = varList.get(i).getString("user_id");
		}
		return (List<PageData>)dao.findForList("UserManagerControllerMapper.queryBuyTotal", userIdList);
	}

	/**
	 * 查询单个销售人员的多个顾客id
	 */
	public List<String> queryUserIdsBySellersId(String sellerId)throws Exception{
		return (List<String>)dao.findForList("UserManagerControllerMapper.querySellersUserIds", sellerId);
	}

	@Override
	public List<PageData> sellerBuyMoneyhList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> queryUserIdsByfirstAddUserId(List<Integer> sellerId) {
		// TODO Auto-generated method stub
		return null;
	}


	public PageData queryUserByMobile(PageData pagedata)throws Exception{
		return (PageData)dao.findForObject("UserManagerControllerMapper.queryUserByMobile", pagedata);
	}

	public PageData refoundToUserMoneyLimit(PageData pagedata)throws Exception{
		return (PageData)dao.findForObject("UserManagerControllerMapper.refoundToUserMoneyLimit", pagedata);
	}

}
