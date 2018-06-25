package com.fh.service.lottery.questionsandanswers.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fh.config.URLConfig;
import com.fh.controller.lottery.order.ReqOrdeEntity;
import com.fh.controller.lottery.questionsandanswers.QuestionAndAnswersEntity;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.questionsandanswers.QuestionsAndAnswersManager;
import com.fh.util.DateUtilNew;
import com.fh.util.PageData;
import com.fh.util.SNGenerator;

/**
 * 说明： 竞猜答题 创建人：FH Q313596790 创建时间：2018-06-15
 * 
 * @version
 */
@Service("questionsandanswersService")
public class QuestionsAndAnswersService implements QuestionsAndAnswersManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("QuestionsAndAnswersMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("QuestionsAndAnswersMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("QuestionsAndAnswersMapper.edit", pd);
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
		return (List<PageData>) dao.findForList("QuestionsAndAnswersMapper.datalistPage", page);
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
		return (List<PageData>) dao.findForList("QuestionsAndAnswersMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("QuestionsAndAnswersMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("QuestionsAndAnswersMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByMatchId(Integer matchId) throws Exception {
		return (PageData) dao.findForObject("QuestionsAndAnswersMapper.findByMatchId", matchId);
	}

	@Override
	public void updateQuestionsAndAnswers(PageData pd) throws Exception {
		dao.update("QuestionsAndAnswersMapper.updateQuestionsAndAnswers", pd);
	}

	@Override
	public void updateStatus(PageData pd) throws Exception {
		dao.update("QuestionsAndAnswersMapper.updateStatus", pd);
	}

	@Override
	public Integer findAwardNumByQuestionsId(Integer questionsId) throws Exception {
		return (Integer) dao.findForObject("UserAccountManagerMapper.findAwardNumByQuestionsId", questionsId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateAward(PageData pd) throws Exception {
		// 回填答案
		dao.update("QuestionsAndAnswersMapper.updateAward", pd);
		PageData questionAndAnswers = (PageData) dao.findForObject("QuestionsAndAnswersMapper.findById", pd);
		JSONArray jsonArray = JSONArray.fromObject(questionAndAnswers.getString("question_and_answer"));
		List<QuestionAndAnswersEntity> questionAndAnswerList = (List<QuestionAndAnswersEntity>) JSONArray.toCollection(jsonArray, QuestionAndAnswersEntity.class);
		// 查询所有该questionId下的用户答题
		List<PageData> userAnswersInfoList = (List<PageData>) dao.findForList("QuestionsAndAnswersMapper.findByQuestionId", Integer.parseInt(pd.getString("id")));
		// 比对结果更新答案状态
		for (int i = 0; i < userAnswersInfoList.size(); i++) {
			Integer flag = 1;
			JSONArray userAnswersjsonArray = JSONArray.fromObject(userAnswersInfoList.get(i).get("user_answer"));
			List<QuestionAndAnswersEntity> userAnswersJsonList = (List<QuestionAndAnswersEntity>) JSONArray.toCollection(userAnswersjsonArray, QuestionAndAnswersEntity.class);
			for (int j = 0; j < questionAndAnswerList.size(); j++) {
				if (!(questionAndAnswerList.get(j).getAnswerStatus1() == userAnswersJsonList.get(j).getAnswerStatus1() && questionAndAnswerList.get(j).getAnswerStatus2() == userAnswersJsonList.get(j).getAnswerStatus2())) {
					flag = 0;
					break;
				}
			}
			userAnswersInfoList.get(i).put("get_award", flag);
		}
		List<PageData> awardList = new ArrayList<PageData>();
		awardList = userAnswersInfoList.stream().filter(s -> s.getString("get_award").equals("1")).collect(Collectors.toList());
		// 单个用户的奖金=奖金总金额/总人数
		// 总人数=awardList.size()+questionAndAnswers.getString("prizewinning_num")
		// 计算用户奖金进行派奖
		int totalNum = awardList.size() + Integer.parseInt(questionAndAnswers.getString("prizewinning_num"));
		BigDecimal bonusPool = new BigDecimal(questionAndAnswers.getString("bonus_pool"));
		BigDecimal award = bonusPool.divide(new BigDecimal(totalNum));
		for (int i = 0; i < awardList.size(); i++) {
			awardList.get(i).put("bonus_amount", award);
			String sn = SNGenerator.nextSN(1);// 生成订单号
			// 生成订单号
			awardList.get(i).put("award_sn", sn);
			awardList.get(i).put("get_award", 1);// 状态为中奖
			awardList.get(i).put("award_time", DateUtilNew.getCurrentTimeLong());
			PageData getAwardUser = awardList.get(i);
			// 添加用户中奖记录以及派奖时间

			dao.update("QuestionsAndAnswersMapper.updateUserAwardStatusAndAmount", getAwardUser);
			// 派奖

			ReqOrdeEntity reqOrdeEntity = new ReqOrdeEntity();
			reqOrdeEntity.orderSn = sn;
			reqOrdeEntity.reward = award.doubleValue();
			reqOrdeEntity.userId = Integer.valueOf(awardList.get(i).getString("user_id"));
			reqOrdeEntity.userMoney = 0;
			reqOrdeEntity.betMoney = 0;
			Integer payTimeTmp = Integer.valueOf(awardList.get(i).getString("answer_time"));
			String payTime = DateUtilNew.getCurrentTimeString(Long.valueOf(payTimeTmp), DateUtilNew.datetimeFormat);
			reqOrdeEntity.betTime = payTime;

			List<ReqOrdeEntity> userIdAndRewardList = new ArrayList<ReqOrdeEntity>();
			userIdAndRewardList.add(reqOrdeEntity);
			String value = "{'userIdAndRewardList':";
			String reqStr = JSON.toJSONString(userIdAndRewardList);
			String stra = value + reqStr + "}";
			// ManualAuditUtil.ManualAuditUtil(stra,
			// urlConfig.getManualRewardUrl(), true);

		}
	}
}
