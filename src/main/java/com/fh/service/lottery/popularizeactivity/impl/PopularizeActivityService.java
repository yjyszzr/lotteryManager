package com.fh.service.lottery.popularizeactivity.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.lottery.popularizeactivity.PopularizeActivityManager;

/** 
 * 说明： 圣和推广活动
 * 创建人：FH Q313596790
 * 创建时间：2019-08-05
 * @version
 */
@Service("popularizeactivityService")
public class PopularizeActivityService implements PopularizeActivityManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public int save(PageData pd)throws Exception{
	return	(int) dao.save("PopularizeActivityMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PopularizeActivityMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PopularizeActivityMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PopularizeActivityMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PopularizeActivityMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PopularizeActivityMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PopularizeActivityMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateById(PageData pd)throws Exception{
		dao.update("PopularizeActivityMapper.updateById", pd);
	}
	@Override
	public void deleteById(PageData pd)throws Exception{
		dao.update("PopularizeActivityMapper.deleteById", pd);
	}

	@Override
	public  List<PageData>  findConfigByActId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PopularizeActivityMapper.findConfigByActId", pd);
		
	}

	@Override
	public void saveConfig(PageData pdConfig) throws Exception {
    dao.save("PopularizeActivityMapper.saveConfig", pdConfig);
	}

	@Override
	public void deleteConfigByActId(PageData pd) throws Exception {
		dao.delete("PopularizeActivityMapper.deleteConfigByActId", pd);
		
	}
	
}

