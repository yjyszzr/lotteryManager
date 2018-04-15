package com.fh.service.dst.purchasematerial;

import java.util.Date;
import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 用料列表接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface PurchaseMaterialManager{

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
	
	/**回写资料
	 * @param pd
	 * @throws Exception
	 */
	public void editCount(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
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
	
	public void updateQuantity(PageData pd)throws Exception;
	
	public void increaseQuantity(PageData pd)throws Exception;
	
	public void increaseReturnQuantity(PageData pd)throws Exception;
	
	public void increaseReturnAndTotal(PageData pd)throws Exception;
	
	public void callbackMaterial(Integer material_id, Integer count, String updateby, Date updateTime)
			throws Exception;

	/**通过明细id与sku_id查询
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public PageData findByMaterial(PageData pd) throws Exception;

	/**通过明细id查询
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public List<PageData> listById(PageData pd) throws Exception;

	/**
	 * 保存采购明细
	 * @param npd
	 */
	public void saveMaterialDetail(PageData pd)throws Exception;

}

