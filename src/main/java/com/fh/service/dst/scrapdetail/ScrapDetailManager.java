package com.fh.service.dst.scrapdetail;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 报损明细管理接口 创建人：FH Q313596790 创建时间：2017-09-12
 * 
 * @version
 */
public interface ScrapDetailManager {

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	public boolean checkexists(PageData pd) throws Exception;

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

	public void check(PageData pd) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(PageData pd) throws Exception;

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
