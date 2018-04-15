package com.fh.service.dst.allocation;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 调拨单列表接口 创建人：FH Q313596790 创建时间：2017-09-09
 * 
 * @version
 */
public interface AllocationManager {

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
	 * 审核通过/驳回
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception;

	public void updateStatusWithIn(PageData pd) throws Exception;

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

	/*
	 * 修改状态
	 */
	public void updateStatus(PageData pd) throws Exception;

	public PageData findByCode(PageData pd) throws Exception;

	public List<PageData> querylist(Page page) throws Exception;

	public void updateReceiveTime(PageData pd) throws Exception;

	/**
	 * 查询所有数据
	 * 
	 * @param noticeIdArr
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByCodes(String[] noticeIdArr) throws Exception;

}
