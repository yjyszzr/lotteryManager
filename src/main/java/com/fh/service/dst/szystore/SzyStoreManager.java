package com.fh.service.dst.szystore;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 仓库接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface SzyStoreManager{
	/**获取俱乐部仓数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findClubStore(PageData pd) throws Exception;
	/**获取总仓数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findTotalStore(PageData pd)throws Exception;

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
	
	/**
	 * 根据编码查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByStoreSn(PageData pd)throws Exception;
	
	public List<PageData> findByPage(Page page)throws Exception;
	
	/**
	 * 修改仓库关系
	 * @param pd
	 * @throws Exception
	 */
	public void updatestorerel(PageData pd)throws Exception;

	/**
	 * 根据编码供应商
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listBySupplier(PageData page) throws Exception;

	public PageData findByType(PageData pd) throws Exception;
}

