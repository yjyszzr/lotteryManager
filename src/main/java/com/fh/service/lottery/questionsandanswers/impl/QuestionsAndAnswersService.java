package com.fh.service.lottery.questionsandanswers.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.fh.controller.lottery.questionsandanswers.QuestionAndAnswersEntity;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.questionsandanswers.QuestionsAndAnswersManager;
import com.fh.util.PageData;

/**
 * 说明： 竞猜答题 创建人：FH Q313596790 创建时间：2018-06-15
 * 
 * @version
 */
@Service("questionsandanswersService")
public class QuestionsAndAnswersService implements QuestionsAndAnswersManager {

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
		// 查询所有该questionId下的用户答题
		dao.update("QuestionsAndAnswersMapper.updateAward", pd);
		PageData questionAndAnswers = (PageData) dao.findForObject("QuestionsAndAnswersMapper.findById", pd);
		JSONArray jsonArray = JSONArray.fromObject(questionAndAnswers.getString("question_and_answer"));
		List<QuestionAndAnswersEntity> questionAndAnswerList = (List<QuestionAndAnswersEntity>) JSONArray.toCollection(jsonArray, QuestionAndAnswersEntity.class);
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
		}
	}
}
