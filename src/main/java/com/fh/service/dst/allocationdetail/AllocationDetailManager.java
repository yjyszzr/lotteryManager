package com.fh.service.dst.allocationdetail;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 调拨明细单列表接口 创建人：FH Q313596790 创建时间：2017-09-09
 * 
 * @version
 */
public interface AllocationDetailManager {

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

	/**
	 * 修改数量
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateQuantity(PageData pd) throws Exception;

	public List<PageData> listByCode(PageData query) throws Exception;

	/**
	 * 通过CODE查询所有
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByAllocationCode(PageData pd) throws Exception;

}
