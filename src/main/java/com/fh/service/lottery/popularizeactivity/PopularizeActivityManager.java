package com.fh.service.lottery.popularizeactivity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 圣和推广活动接口
 * 创建人：FH Q313596790
 * 创建时间：2019-08-05
 * @version
 */
public interface PopularizeActivityManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public int save(PageData pd)throws Exception;
	
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

	public void updateById(PageData pd)throws Exception;
	
	public void deleteById(PageData pd)throws Exception;
	
	public void deleteByType(String type)throws Exception;

	public  List<PageData>  findConfigByActId(PageData pd)throws Exception;

	public void saveConfig(PageData pdConfig)throws Exception;

	public void deleteConfigByActId(PageData pd)throws Exception;
	
	
	//定时器执行方法********************//

	Integer insertHisToUserInfo() throws Exception;
	
	Integer updateActivityUserInfoByBl() throws Exception;
	
	Integer updateActivityUserInfoByRy() throws Exception;
	
	List<String> queryActivityConfigList(PageData pd) throws Exception;
	
	int deleteConfigRecByConfigId(String[] configId) throws Exception;
	
}

