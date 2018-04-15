package com.fh.service.erp.storeshop;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 仓库店铺关联管理接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 * @version
 */
public interface StoreShopManager{

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
	
	
	/**通过storesn获取
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByStore(PageData pd)throws Exception;
	
	public PageData findByStoreIdOnly(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过Storeid获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByStoreId(PageData pd)throws Exception;
	
	/**通过Shopid获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByShopId(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**仓库id列表
	 * @param page
	 * @throws Exception
	 */
	public List storeList(PageData pd)throws Exception;
	
	/**店铺id列表
	 * @param page
	 * @throws Exception
	 */
	public List shopList(PageData pd)throws Exception;
	public List<PageData> listAllByUserId(PageData pd) throws Exception;
}

