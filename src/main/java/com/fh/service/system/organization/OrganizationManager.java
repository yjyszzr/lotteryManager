package com.fh.service.system.organization;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 组织架构管理接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 * @version
 */
public interface OrganizationManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**新增组织机构和仓库关系数据
	 * @param pd
	 * @throws Exception
	 */
	public void saveOrgStore(List<PageData> list)throws Exception;
	
	/**新增组织机构和用户关系数据
	 * @param pd
	 * @throws Exception
	 */
	public void saveOrgUser(List<PageData> list)throws Exception;
	
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
	
	/**通过UserId获取用户数据权限
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listDataRightsByUserId(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过组织名称获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByOrgName(PageData pd)throws Exception;
	
	/**通过组织机构id和仓库编码获取组织机构和仓库的关系数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listByPageData(PageData pd)throws Exception;
	
	/**通过组织机构id和用户Id获取组织机构和用户的关系数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listUserByPageData(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**通过组织机构Id批量删除组织机构和仓库关系表数据
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllByOrgId(String[] ArrayDATA_IDS)throws Exception;
	
	/**通过组织机构Id批量删除组织机构和用户关系表数据
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllUserByOrgId(String[] ArrayDATA_IDS)throws Exception;
}

