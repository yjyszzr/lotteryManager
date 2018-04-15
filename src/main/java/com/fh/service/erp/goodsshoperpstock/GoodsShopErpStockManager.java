package com.fh.service.erp.goodsshoperpstock;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.express.enums.ShippingStatusEnum;

/** 
 * 说明： 商城店铺与ERP仓库对应接口
 * 创建人：FH Q313596790
 * 创建时间：2017-10-02
 * @version
 */
public interface GoodsShopErpStockManager{

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
	
	public PageData findByIdByShopAndSn(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 根据物品Id修改店铺商品中的库存
	 * 
	 * @goods_number  数量
	 * @goods_id  数量
	 * 
	 * @throws Exception
	 */
	public void updateShopStockByGoodsId(PageData pd) throws Exception;
	
	
	public void updateShopStock(PageData pd) throws Exception;
	/**
	 * 根据仓库修改店铺商品中的库存
	 * 
	 * @shop_id  仓库编码
	 * @shop_name 店铺名称
	 * @sku_encode 商品编码  
	 * @quantity  数量
	 * 
	 * @throws Exception
	 */
	public void updateStock(String shop_id,String shop_name, String sku_encode, int quantity, String store_Id)throws Exception;
	public void updateOrderStatus(String orderNo)throws Exception;
	public List<PageData> listShopGoodsAll(PageData pd)throws Exception;
	public List<PageData> listUserByOrderSn(String orderNo)throws Exception;
	public PageData findOrderByCode(PageData pd) throws Exception;
	public PageData findShop(PageData pdQuery)throws Exception;

	public List<PageData> findOrderGoodsByCode(PageData queryOrder)throws Exception;
}

