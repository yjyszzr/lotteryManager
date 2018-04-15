package com.fh.service.dst.purchasedetail;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 采购单详情接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface PurchaseDetailManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**查询待生产加工的生产明细
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> dataPage(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**根据条件查询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listByParam(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	/**
	 * 质检数据更新
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void qualityCheck(PageData pd)throws Exception;
	/**
	 *  增量修改数据，发货说量，可发货数量
	 * @param pd
	 * @throws Exception
	 */
	public void updateQuantityForAdd(PageData pd) throws Exception ;
	
	/**
	 * 更新退货数量
	 * @param pd
	 * @throws Exception
	 */
	public void updateBadQuantityForAdd(PageData pd) throws Exception;
	
}

