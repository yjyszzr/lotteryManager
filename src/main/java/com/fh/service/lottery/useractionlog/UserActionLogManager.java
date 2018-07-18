package com.fh.service.lottery.useractionlog;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 用户操作日志接口
 * 创建人：FH Q313596790
 * 创建时间：2018-06-06
 * @version
 */
public interface UserActionLogManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(String status,String type,String obj,String text)throws Exception;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveByObject(String status,String name,PageData oldPage,PageData newPage)throws Exception;
	
 
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	 
	
}

