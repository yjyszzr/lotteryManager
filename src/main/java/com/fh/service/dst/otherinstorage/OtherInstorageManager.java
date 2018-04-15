package com.fh.service.dst.otherinstorage;

import java.io.PrintWriter;
import java.util.List;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;

/** 
 * 说明： 其他入库单接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 * @version
 */
public interface OtherInstorageManager{

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
	
	public void updateStatus(PageData pd)throws Exception;
	
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
	 * 生成通知单
	 * @param pd
	 * @param out
	 * @param user
	 * @throws Exception
	 */
	public void saveToNotice(PageData pd,User user) throws Exception ;

	public PageData findByCode(PageData query) throws Exception ;

	public List<PageData> querylist(Page page) throws Exception ;
	
}

