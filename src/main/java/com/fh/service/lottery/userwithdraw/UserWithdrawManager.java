package com.fh.service.lottery.userwithdraw;

import java.math.BigDecimal;
import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： 提现模块接口 创建人：FH Q313596790 创建时间：2018-05-02
 * 
 * @version
 */
public interface UserWithdrawManager {

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

	public void withdrawOperation(PageData pd) throws Exception;

	public void saveUserWithdrawLog(PageData pd) throws Exception;
	public PageData findByWithdrawSn(PageData pd) throws Exception;
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 临时提现列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> temporaryWithdrawList(Page page) throws Exception;

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

	public void updateRemarks(PageData pd) throws Exception;

	public List<PageData> findByUserId(int parseInt) throws Exception;

	public BigDecimal findTotalAwardById(int parseInt) throws Exception;
	/**
	 * 总提现金额和人数
	 *  
	 * @throws Exception
	 */
	public PageData findTotalWithDraw(PageData pd) throws Exception;
}
