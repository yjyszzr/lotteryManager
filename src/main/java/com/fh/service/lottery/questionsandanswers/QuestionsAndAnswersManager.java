package com.fh.service.lottery.questionsandanswers;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 竞猜答题接口 创建人：FH Q313596790 创建时间：2018-06-15
 * 
 * @version
 */
public interface QuestionsAndAnswersManager {

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

	public PageData findByMatchId(Integer matchId) throws Exception;

	public void updateQuestionsAndAnswers(PageData pd) throws Exception;

	public void updateStatus(PageData pd) throws Exception;

	public Integer findAwardNumByQuestionsId(Integer questionsId) throws Exception;

	public void updateAward(PageData pd) throws Exception;

}
