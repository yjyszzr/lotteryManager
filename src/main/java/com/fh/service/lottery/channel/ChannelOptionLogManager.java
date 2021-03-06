package com.fh.service.lottery.channel;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 渠道分销操作日志接口 创建人：FH Q313596790 创建时间：2018-05-27
 * 
 * @version
 */
public interface ChannelOptionLogManager {

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

	public List<PageData> goConsumerListByTime(PageData pd) throws Exception;

	public int insertList(List<PageData> channelOperationList) throws Exception;

	/**
	 * 列表(西安)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getOptionLogList(Page page) throws Exception;

	public List<PageData> getXACountYesterday(Page page) throws Exception;

	public List<PageData> findUserReal() throws Exception;

}
