package com.fh.service.lottery.questionsandanswers.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

}
