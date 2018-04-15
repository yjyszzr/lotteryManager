package com.fh.service.erp.inbound;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 入库单管理接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface InboundManager{
	
	/**通过inbound_notice_code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByInboundNoticeCode(PageData pd)throws Exception;
	
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
	
	public List<PageData> listInbound(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllTimeSpan10(PageData pd)throws Exception;
	
	public List<PageData> listInboundDetail(PageData pd)throws Exception;
	
	
	
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
	/**更新同步状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateSynStatus(PageData pd)throws Exception;

	public List<PageData> queryByPurchase(PageData pd)throws Exception;
}

