package com.fh.service.erp.inboundnoticedetail;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 到货通知单管理接口 创建人：FH Q313596790 创建时间：2017-09-09
 * 
 * @version
 */
public interface InboundNoticeDetailManager {
	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateState(PageData pd) throws Exception;

	/**
	 * 列表listPageByWhere
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listPageByWhere(Page page) throws Exception;

	/**
	 * 列表listByWhere
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listByWhere(PageData pd) throws Exception;

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

	public void saveBatch(PageData pd) throws Exception;

	/**
	 * 编辑批次
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editbatch(PageData pd) throws Exception;

	/**
	 * 修改状态
	 * 
	 * @param pds
	 * @throws Exception
	 */
	public void updateStatus(PageData pd) throws Exception;

	/**
	 * 根据到货通知单号查询
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findlistByNoticeCode(PageData pd) throws Exception;

}
