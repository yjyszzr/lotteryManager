package com.fh.service.lottery.worldcupmanager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.worldcupmanager.WorldCupManagerManager;
import com.fh.util.PageData;

/**
 * 说明： 世界杯冠亚军推演模块 创建人：FH Q313596790 创建时间：2018-07-09
 * 
 * @version
 */
@Service("worldcupmanagerService")
public class WorldCupManagerService implements WorldCupManagerManager {

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
		PageData pageData = (PageData) dao.findForObject("WorldCupManagerMapper.findById", pd);
		Integer prizeValue = Integer.parseInt(pageData.getString("prize_value"));
		List<PageData> pageDataList = (List<PageData>) dao.findForList("WorldCupManagerMapper.findWorldCupUserPlan", null);
		for (int i = 0; i < pageDataList.size(); i++) {
			String planJson = pageDataList.get(i).getString("plan_json");
			String[] cells = planJson.split(";");
			PageData pageDataForStatus = new PageData();
			boolean flag = true;
			for (String cell : cells) {
				String[] split = cell.split("\\|");
				// 获取奖项16强||8强||4强||2强||1强
				int prizeInt = Integer.parseInt(split[0]);
				if (prizeValue != 0 && prizeValue == prizeInt) {
					Map<String, Integer> contrysMap = map.get(prizeInt);
					String[] contrys = split[1].split(",");
					for (int j = 0; j < contrys.length; j++) {
						String[] contry = contrys[j].split(":");
						// 拿赢得比赛的国家队Id跟用户投注国家队Id作比较
						if (contrysMap.get(contry[0]) != Integer.parseInt(contry[1])) {
							flag = false;
							break;
						}
					}
					if (flag) {
						pageDataForStatus.put("id", pageDataList.get(i).getString("id"));// 添加用户Id
						pageDataForStatus.put("is_open", 1);// 开奖
						if (prizeInt == 16) {
							pageDataForStatus.put("status_16", 1);
						} else if (prizeInt == 8) {
							pageDataForStatus.put("status_8", 1);
						} else if (prizeInt == 4) {
							pageDataForStatus.put("status_4", 1);
						} else if (prizeInt == 2) {
							pageDataForStatus.put("status_gyj", 1);
						} else if (prizeInt == 1) {
							pageDataForStatus.put("status_gj", 1);
						}
						dao.update("WorldCupManagerMapper.updateWorldUserPlanStatus", pageDataForStatus);
					}
				} else if (prizeValue == 0) {
					// 获取终极大奖的奖项
					Map<String, Integer> contrysMap = map.get(prizeInt);
					String[] contrys = split[1].split(",");
					for (int j = 0; j < contrys.length; j++) {
						String[] contry = contrys[j].split(":");
						// 拿赢得比赛的国家队Id跟用户投注国家队Id作比较
						if (contrysMap.get(contry[0]) == Integer.parseInt(contry[1])) {
							flag = true;
						}
					}
					if (flag) {
						pageDataForStatus.put("id", pageDataList.get(i).getString("id"));
						pageDataForStatus.put("status_all_true", 1);
						pageDataForStatus.put("is_open", 1);
						dao.update("WorldCupManagerMapper.updateWorldUserPlanStatus", pageDataForStatus);
					}
				}
			}
		}
		dao.update("WorldCupManagerMapper.edit", pd);
	}
}
