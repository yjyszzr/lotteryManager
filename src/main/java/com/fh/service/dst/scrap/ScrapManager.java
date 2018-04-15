package com.fh.service.dst.scrap;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 报损单管理接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 * @version
 */
public interface ScrapManager{

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
	/**
	 * 更新状态
	 * @param pd
	 * @throws Exception
	 */
	public void editStatus(PageData pd)throws Exception;
	/**
	 * 品控审核结束 
	 * @param pd
	 * @throws Exception
	 */
	public void completeCheck(PageData pd)throws Exception;
	/**
	 * 完成
	 * @param pd
	 * @throws Exception
	 */
	public void complete(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	public List<PageData> list1(Page page)throws Exception;
	public List<PageData> list2(Page page)throws Exception;
	public List<PageData> list3(Page page)throws Exception;
	
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

	public void commit(PageData pd)throws Exception;

	public void verify(PageData pd)throws Exception;

	public PageData findByCode(PageData query)throws Exception;

	public List<PageData> querylist(Page page) throws Exception;
	
	/**
	 * 查询所有数据
	 * 
	 * @param noticeIdArr
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByCodes(String[] noticeIdArr) throws Exception;
	
}

