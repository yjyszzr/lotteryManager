package com.fh.service.dst.purchasegroup;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 采购组接口
 * 创建人：FH Q313596790
 * 创建时间：2017-10-13
 * @version
 */
public interface PurchaseGroupManager{

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
	/**查询采购组下用户
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllUserByGP(PageData pd)throws Exception;
	
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
	/**批量删除采购组用户
	 * @param
	 * @throws Exception
	 */
	public void deleteGpUser(PageData pd) throws Exception;
	/**批量删除采购组用户
	 * @param
	 * @throws Exception
	 */
	public void batchInsertGpUser(List<PageData> pds) throws Exception;
	/**根据用户和组类型查组
	 * @param pd
	 * @throws Exception
	 */
	
	public List<PageData> listAllGPByUser(PageData pd)throws Exception;
	/**通过name获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
}

