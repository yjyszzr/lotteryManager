package com.fh.service.dst.outboundnotice;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 发货单列表接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface OutboundNoticeManager{

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
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public Object updateStatus(PageData pd)throws Exception;

	public List<PageData> listByNoticeIds(String[] noticeIdArr)throws Exception;

	public PageData orderConsigeen(PageData pdItem)throws Exception;

	public Object updatePrintCount(String[] noticeIdArr)throws Exception;
	
}

