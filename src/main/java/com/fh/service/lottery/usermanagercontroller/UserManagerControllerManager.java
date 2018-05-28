package com.fh.service.lottery.usermanagercontroller;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 用户列表接口 创建人：FH Q313596790 创建时间：2018-04-23
 * 
 * @version
 */
public interface UserManagerControllerManager {
	public static final int STATUS_NORMAL = 0; // 正常状态
	public static final int STATUS_LOCK = 1; // 被锁定
	public static final int STATUS_FREEN = 2; // 被冻结

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

	public List<PageData> findByUserId(PageData pd) throws Exception;

	public Double findUserBonusByUserId(PageData pd) throws Exception;

	public List<PageData> findAll() throws Exception;

}
