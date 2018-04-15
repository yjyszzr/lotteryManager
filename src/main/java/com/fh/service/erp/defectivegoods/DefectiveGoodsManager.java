package com.fh.service.erp.defectivegoods;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： erp接口
 * 创建人：FH Q313596790
 * 创建时间：2017-10-11
 * @version
 */
public interface DefectiveGoodsManager{
	//审核列表
	public List<PageData> checkList(Page page)throws Exception;
	//审核驳回状态
	public void updateCheckStatus(PageData pd)throws Exception;
	//更新状态
	public void updateStatus(PageData pd)throws Exception;
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
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;
	
	public void commit(PageData pd) throws Exception;
	
	public PageData findByCode(PageData query) throws Exception;
	
	
}

