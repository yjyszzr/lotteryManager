package com.fh.service.erp.erporder;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 订单状态确认接口
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 * @version
 */
public interface ErpOrderManager{

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
	
	public void updateErpOrder(String order_sn,Integer shipping_status)throws Exception;
	
	public void updateErpOrder(String order_sn,Integer shipping_status,Integer delivery_org_id )throws Exception;
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
	
	public List<PageData> listAllOrderGoods(PageData pd)throws Exception;
	public List<PageData> listAllShop()throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	public List<PageData> listDeliveryByStoreId(PageData pd)throws Exception;
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	public void updateErpOrderDeliveryOrg(String order_sn, String delivery_org_id)throws Exception;
	
}

