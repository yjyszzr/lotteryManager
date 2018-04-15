package com.fh.service.erp.inboundnotice;

import java.io.PrintWriter;
import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;

/**
 * 说明： 到货通知单管理接口 创建人：FH Q313596790 创建时间：2017-09-09
 * 
 * @version
 */
public interface InboundNoticeManager {

	/**
	 * 通过Code获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd) throws Exception;

	/**
	 * 更新状态
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception;

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

	public void saveDetail(PageData pd, User user) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param noticeIdArr
	 * @return
	 * @throws Exception
	 */

	public List<PageData> listByNoticesIds(String[] noticeIdArr) throws Exception;

	public List<PageData> listForCheck(Page page) throws Exception;
	
}
