package com.fh.service.lottery.worldcupmanager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fh.config.URLConfig;
import com.fh.controller.lottery.order.ReqOrdeEntity;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.worldcupmanager.WorldCupManagerManager;
import com.fh.util.DateUtilNew;
import com.fh.util.Logger;
import com.fh.util.ManualAuditUtil;
import com.fh.util.PageData;
import com.fh.util.SNGenerator;

/**
 * 说明： 世界杯冠亚军推演模块 创建人：FH Q313596790 创建时间：2018-07-09
 * 
 * @version
 */
@Service("worldcupmanagerService")
public class WorldCupManagerService implements WorldCupManagerManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("WorldCupManagerMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("WorldCupManagerMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("WorldCupManagerMapper.edit", pd);
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
		return (List<PageData>) dao.findForList("WorldCupManagerMapper.datalistPage", page);
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
		return (List<PageData>) dao.findForList("WorldCupManagerMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorldCupManagerMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("WorldCupManagerMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public Integer findPeopleNumByStatus(PageData pd) throws Exception {
		return (Integer) dao.findForObject("WorldCupManagerMapper.findPeopleNumByStatus", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void openThePrize(PageData pd, Map<Integer, Map<String, Integer>> map) throws Exception {
		Integer prizeValue = Integer.parseInt(pd.getString("prize_value"));
		List<PageData> pageDataList = (List<PageData>) dao.findForList("WorldCupManagerMapper.findWorldCupUserPlan", null);
		List<PageData> pageDataListForStatusAndAmount = new ArrayList<PageData>();
		for (int i = 0; i < pageDataList.size(); i++) {
			String planJson = pageDataList.get(i).getString("plan_json");
			String[] cells = planJson.split(";");
			PageData pageDataForStatusAndAmount = new PageData();
			boolean flag = true;
			for (String cell : cells) {
				String[] split = cell.split("\\|");
				int prizeInt = Integer.parseInt(split[0]);
				if (prizeValue != 0 && prizeValue == prizeInt) { // 对比奖项16强||8强||4强||2强||1强
					Map<String, Integer> contrysMap = map.get(prizeInt);
					String[] contrys = split[1].split(",");
					for (int j = 0; j < contrys.length; j++) {
						String[] contry = contrys[j].split(":");
						if (contrysMap.get(contry[0]) == null || contrysMap.get(contry[0]) != Integer.parseInt(contry[1])) { // 拿赢得比赛的国家队Id跟用户投注国家队Id作比较
							flag = false;
							break;
						}
					}
					if (flag) {
						pageDataForStatusAndAmount.put("id", pageDataList.get(i).getString("id"));// 添加用户Id
						pageDataForStatusAndAmount.put("is_open", 1);// 开奖
						if (prizeInt == 16) {
							pageDataForStatusAndAmount.put("status_16", 1);
							pageDataForStatusAndAmount.put("reward_amount_16", pd.getString("average"));
						} else if (prizeInt == 8) {
							pageDataForStatusAndAmount.put("status_8", 1);
							pageDataForStatusAndAmount.put("reward_amount_8", pd.getString("average"));
						} else if (prizeInt == 4) {
							pageDataForStatusAndAmount.put("status_4", 1);
							pageDataForStatusAndAmount.put("reward_amount_4", pd.getString("average"));
						} else if (prizeInt == 2) {
							pageDataForStatusAndAmount.put("status_gyj", 1);
							pageDataForStatusAndAmount.put("reward_amount_gyj", pd.getString("average"));
						} else if (prizeInt == 1) {
							pageDataForStatusAndAmount.put("status_gj", 1);
							pageDataForStatusAndAmount.put("reward_amount_gj", pd.getString("average"));
						}
						pageDataListForStatusAndAmount.add(pageDataForStatusAndAmount);
						logger.info("更新用户" + prizeInt + "强中奖信息============================================" + pageDataForStatusAndAmount);
						dao.update("WorldCupManagerMapper.updateWorldUserPlanStatusAndAmount", pageDataForStatusAndAmount);
						// 记录用户中奖到wc_user_reward_Account表
						PageData wcUserRewardAccount = new PageData();
						wcUserRewardAccount.put("id", 0);
						wcUserRewardAccount.put("wc_plan_id", pageDataForStatusAndAmount.getString("id"));
						String sn = SNGenerator.nextSN(9);// 生成订单号
						wcUserRewardAccount.put("reward_sn", sn);
						wcUserRewardAccount.put("user_id", pageDataList.get(i).getString("user_id"));
						wcUserRewardAccount.put("status", 0);
						wcUserRewardAccount.put("amount", pd.getString("average"));
						wcUserRewardAccount.put("prize_value", prizeInt);
						wcUserRewardAccount.put("remark", "世界杯" + prizeInt + "强");
						dao.update("WorldCupManagerMapper.addWcUserRewardAccount", wcUserRewardAccount);
						logger.info("记录用户中奖到wc_user_reward_Account===============================" + wcUserRewardAccount);
					}
				} else if (prizeValue == 0) {
					// 获取终极大奖的奖项
					Map<String, Integer> contrysMap = map.get(prizeInt);
					String[] contrys = split[1].split(",");
					for (int j = 0; j < contrys.length; j++) {
						String[] contry = contrys[j].split(":");
						// 拿赢得比赛的国家队Id跟用户投注国家队Id作比较
						if (contrysMap.get(contry[0]) == null && contrysMap.get(contry[0]) == Integer.parseInt(contry[1])) {
							flag = true;
						}
					}
					if (flag) {
						pageDataForStatusAndAmount.put("id", pageDataList.get(i).getString("id"));
						pageDataForStatusAndAmount.put("status_all_true", 1);
						pageDataForStatusAndAmount.put("is_open", 1);
						pageDataForStatusAndAmount.put("reward_amount_all_true", pd.getString("average"));
						pageDataListForStatusAndAmount.add(pageDataForStatusAndAmount);
						logger.info("更新用户终极中奖信息============================================" + pageDataForStatusAndAmount);
						dao.update("WorldCupManagerMapper.updateWorldUserPlanStatusAndAmount", pageDataForStatusAndAmount);
						// 记录用户中奖到wc_user_reward_Account表
						PageData wcUserRewardAccount = new PageData();
						wcUserRewardAccount.put("id", 0);
						wcUserRewardAccount.put("wc_plan_id", pageDataForStatusAndAmount.getString("id"));
						wcUserRewardAccount.put("user_id", pageDataList.get(i).getString("user_id"));
						String sn = SNGenerator.nextSN(9);// 生成订单号
						wcUserRewardAccount.put("reward_sn", sn);
						wcUserRewardAccount.put("status", 0);
						wcUserRewardAccount.put("amount", pd.getString("average"));
						wcUserRewardAccount.put("prize_value", prizeValue);
						wcUserRewardAccount.put("remark", "世界杯终极大奖");
						dao.update("WorldCupManagerMapper.addWcUserRewardAccount", wcUserRewardAccount);
						logger.info("记录用户中奖到wc_user_reward_Account===============================" + wcUserRewardAccount);
					}
				}
			}
		}
		logger.info("中奖用户" + pageDataListForStatusAndAmount.size() + "个,==============中奖用户为" + pageDataListForStatusAndAmount);
		dao.update("WorldCupManagerMapper.edit", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateUserRewardStatus(PageData pd, Map<Integer, Map<String, Integer>> map) throws Exception {
		Integer prizeValue = Integer.parseInt(pd.getString("prize_value"));
		List<PageData> pageDataList = (List<PageData>) dao.findForList("WorldCupManagerMapper.findWorldCupUserPlan", null);
		List<PageData> pageDataListForStatusAndAmount = new ArrayList<PageData>();
		for (int i = 0; i < pageDataList.size(); i++) {
			String planJson = pageDataList.get(i).getString("plan_json");
			String[] cells = planJson.split(";");
			PageData pageDataForStatusAndAmount = new PageData();
			boolean flag = true;
			for (String cell : cells) {
				String[] split = cell.split("\\|");
				int prizeInt = Integer.parseInt(split[0]);
				if (prizeValue != 0 && prizeValue == prizeInt) { // 对比奖项16强||8强||4强||2强||1强
					Map<String, Integer> contrysMap = map.get(prizeInt);
					String[] contrys = split[1].split(",");
					for (int j = 0; j < contrys.length; j++) {
						String[] contry = contrys[j].split(":");
						if (contrysMap.get(contry[0]) == null || contrysMap.get(contry[0]) != Integer.parseInt(contry[1])) { // 拿赢得比赛的国家队Id跟用户投注国家队Id作比较
							flag = false;
							break;
						}
					}
					if (flag) {
						pageDataForStatusAndAmount.put("id", pageDataList.get(i).getString("id"));// 添加用户Id
						if (prizeInt == 16) {
							pageDataForStatusAndAmount.put("status_16", 1);
						} else if (prizeInt == 8) {
							pageDataForStatusAndAmount.put("status_8", 1);
						} else if (prizeInt == 4) {
							pageDataForStatusAndAmount.put("status_4", 1);
						} else if (prizeInt == 2) {
							pageDataForStatusAndAmount.put("status_gyj", 1);
						} else if (prizeInt == 1) {
							pageDataForStatusAndAmount.put("status_gj", 1);
						}
						pageDataListForStatusAndAmount.add(pageDataForStatusAndAmount);
						logger.info("更新用户" + prizeInt + "强中奖状态============================================" + pageDataForStatusAndAmount);
						dao.update("WorldCupManagerMapper.updateWorldUserPlanStatusAndAmount", pageDataForStatusAndAmount);
					}
				} else if (prizeValue == 0) {
					// 获取终极大奖的奖项
					Map<String, Integer> contrysMap = map.get(prizeInt);
					String[] contrys = split[1].split(",");
					for (int j = 0; j < contrys.length; j++) {
						String[] contry = contrys[j].split(":");
						// 拿赢得比赛的国家队Id跟用户投注国家队Id作比较
						if (contrysMap.get(contry[0]) == null && contrysMap.get(contry[0]) == Integer.parseInt(contry[1])) {
							flag = true;
						}
					}
					if (flag) {
						pageDataForStatusAndAmount.put("id", pageDataList.get(i).getString("id"));
						pageDataForStatusAndAmount.put("status_all_true", 1);
						pageDataListForStatusAndAmount.add(pageDataForStatusAndAmount);
						logger.info("更新用户终极中奖状态============================================" + pageDataForStatusAndAmount);
						dao.update("WorldCupManagerMapper.updateWorldUserPlanStatusAndAmount", pageDataForStatusAndAmount);
					}
				}
			}
		}
		logger.info("中奖用户" + pageDataListForStatusAndAmount.size() + "个,==============中奖用户为" + pageDataListForStatusAndAmount);
		pd.put("status", 0);
		dao.update("WorldCupManagerMapper.edit", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void rewardToUser(PageData pd) throws Exception {
		pd = (PageData) dao.findForObject("WorldCupManagerMapper.findById", pd);
		// 增加用户投注方案的派奖时间
		List<PageData> dataList = (List<PageData>) dao.findForList("WorldCupManagerMapper.findUserRewardAccountByPrizeValue", pd);
		List<ReqOrdeEntity> userIdAndRewardList = new ArrayList<ReqOrdeEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			PageData pageData = new PageData();
			pageData.put("id", dataList.get(i).getString("wc_plan_id"));
			if (pd.getString("prize_value").equals("0")) {
				pageData.put("reward_time_all_true", DateUtilNew.getCurrentTimeLong());
			} else if (pd.getString("prize_value").equals("1")) {
				pageData.put("reward_time_gj", DateUtilNew.getCurrentTimeLong());
			} else if (pd.getString("prize_value").equals("2")) {
				pageData.put("reward_time_gyj", DateUtilNew.getCurrentTimeLong());
			} else if (pd.getString("prize_value").equals("4")) {
				pageData.put("reward_time_4", DateUtilNew.getCurrentTimeLong());
			} else if (pd.getString("prize_value").equals("8")) {
				pageData.put("reward_time_8", DateUtilNew.getCurrentTimeLong());
			} else if (pd.getString("prize_value").equals("16")) {
				pageData.put("reward_time_16", DateUtilNew.getCurrentTimeLong());
			}
			dao.update("WorldCupManagerMapper.updateWorldUserPlanRewardTime", pageData);
			// 更新派奖记录中的时间以及状态
			PageData userRewadAccount = new PageData();
			userRewadAccount.put("id", dataList.get(i).getString("id"));
			userRewadAccount.put("status", 1);
			userRewadAccount.put("reward_time", DateUtilNew.getCurrentTimeLong());
			dao.update("WorldCupManagerMapper.updateWorldUserRewadAccountTimeAndStatus", userRewadAccount);
			ReqOrdeEntity reqOrdeEntity = new ReqOrdeEntity();
			reqOrdeEntity.orderSn = dataList.get(i).getString("reward_sn");
			reqOrdeEntity.reward = Double.parseDouble(dataList.get(i).getString("amount"));
			reqOrdeEntity.userId = Integer.valueOf(dataList.get(i).getString("user_id"));
			reqOrdeEntity.userMoney = 0;
			reqOrdeEntity.betMoney = 0;
			String payTime = DateUtilNew.getCurrentTimeString(Long.valueOf(userRewadAccount.getString("reward_time")), DateUtilNew.datetimeFormat);
			reqOrdeEntity.betTime = payTime;
			userIdAndRewardList.add(reqOrdeEntity);
		}
		// 更新该奖设的状态
		PageData rewardPool = new PageData();
		rewardPool.put("id", pd.getString("id"));
		rewardPool.put("status", 2);
		dao.update("WorldCupManagerMapper.updateRewardPoolStatus", rewardPool);
		// 将奖金打入到不可体现余额中
		logger.info("userIdAndRewardList===========================" + JSON.toJSONString(userIdAndRewardList));
		String reqStr = "{'userIdAndRewardList':" + JSON.toJSONString(userIdAndRewardList) + "}";
		logger.info("请求reqStr===========================" + reqStr);
		ManualAuditUtil.ManualAuditUtil(reqStr, urlConfig.getManualRewardToUserMoneyLimitUrl(), true);

	}
}
