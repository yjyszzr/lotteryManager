package com.fh.service.lottery.channel.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.util.PageData;

/**
 * 说明： 渠道分销操作日志 创建人：FH Q313596790 创建时间：2018-05-27
 * 
 * @version
 */
@Service("channeloptionlogService")
public class ChannelOptionLogService implements ChannelOptionLogManager {

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ChannelOptionLogMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ChannelOptionLogMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ChannelOptionLogMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ChannelOptionLogMapper.datalistPage", page);
	}
	
	/**
	 * 列表(西安)
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getOptionLogList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ChannelOptionLogMapper.datalistPageXA", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> getXACountYesterday(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ChannelOptionLogMapper.getXACountYesterday", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChannelOptionLogMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ChannelOptionLogMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ChannelOptionLogMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> goConsumerListByTime(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChannelOptionLogMapper.goConsumerListByTime", pd);
	}

	@Override
	public int insertList(List<PageData> channelOperationList) throws Exception {
		return (int) dao.save("ChannelOptionLogMapper.insertList", channelOperationList);
	}

	

}
