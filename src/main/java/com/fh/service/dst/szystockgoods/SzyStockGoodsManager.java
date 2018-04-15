package com.fh.service.dst.szystockgoods;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 库存接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface SzyStockGoodsManager{

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
	
	public List<PageData> stockBySkuIdlistPage(Page page)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 
	 * @param quantity数量
	 * @param storeId仓库ID
	 * @param skuId商品ID
	 * @param direction 0:增加库存 1:减少库存
	 * @throws Exception
	 */
	public void quantityModify(double quantity,String storeSn,int skuId,int direction)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByWhere(PageData pd)throws Exception;
	
	/**更新库存数量
	 * @param pd
	 * @throws Exception
	 */
	public void updateQuantity(PageData pd)throws Exception;
	
	public void quantityModify(double quantity,int storeId,int skuId,int direction)throws Exception;
}

