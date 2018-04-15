package com.fh.service.dst.outbound;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 说明： outbound接口 创建人：FH Q313596790 创建时间：2017-09-10
 * 
 * @version
 */
public interface PgtOutboundManager {

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;
	
	public List<PageData> listOutboundInfo(PageData pd) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

	/**
	 * 创建出库单
	 * 
	 * @param noticeId
	 * @param noticecode
	 * @return
	 */
	public String saveOutBound(long noticeId, String noticecode, String userName, String stockbatchJson,
			String sendQuantityStr) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllTimeSpan10(PageData pd) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllOrder(PageData pd) throws Exception;

	/**
	 * 修改同步状态
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateSynStatus(PageData pd) throws Exception;

	public List<PageData> listOutboundDetail(PageData pd) throws Exception;

	/**
	 * 查询出库单信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listOutbound(String[] noticeIdArr) throws Exception;

	public List<PageData> queryByPurchase(PageData pd) throws Exception;

}
