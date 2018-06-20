package com.fh.service.lottery.footballmatchlottery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.lottery.footballmatchlottery.FootballMatchLotteryManager;
import com.fh.service.lottery.questionsandanswers.QuestionsAndAnswersManager;
import com.fh.util.PageData;

/**
 * 说明： 足球竞彩 创建人：FH Q313596790 创建时间：2018-05-30
 * 
 * @version
 */
@Service("footballmatchlotteryService")
public class FootballMatchLotteryService implements FootballMatchLotteryManager {

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name = "questionsandanswersService")
	private QuestionsAndAnswersManager questionsandanswersService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("FootballMatchLotteryMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("FootballMatchLotteryMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("FootballMatchLotteryMapper.edit", pd);
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
		List<PageData> list = (List<PageData>) dao.findForList("FootballMatchLotteryMapper.getMatchList", page);
		PageData pageData = new PageData();
		List<PageData> qandaList = questionsandanswersService.listAll(pageData);
		Map<Integer, PageData> qandaMap = new HashMap<Integer, PageData>(qandaList.size());
		qandaList.forEach(item -> qandaMap.put(Integer.parseInt(item.getString("match_id")), item));
		for (int i = 0; i < list.size(); i++) {
			PageData qanda = qandaMap.get(Integer.parseInt(list.get(i).getString("match_id")));
			if (qanda == null) {
				list.get(i).put("qaStatus", 0);
			} else {
				list.get(i).put("qaStatus", 1);
			}
		}
		return list;

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
		return (List<PageData>) dao.findForList("FootballMatchLotteryMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("FootballMatchLotteryMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("FootballMatchLotteryMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateStatus(PageData pd) throws Exception {
		dao.update("FootballMatchLotteryMapper.updateStatus", pd);
	}

}
