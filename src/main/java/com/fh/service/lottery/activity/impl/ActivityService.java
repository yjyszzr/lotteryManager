package com.fh.service.lottery.activity.impl;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.service.lottery.activity.ActivityManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 活动管理
 * 创建人：FH Q313596790
 * 创建时间：2018-06-03
 * @version
 */
@Service("activityService")
public class ActivityService implements ActivityManager{

	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ActivityMapper.save", pd);
	}

    /**根据类型查询
     * @param pd
     * @throws Exception
     */
    public PageData findByType(PageData pd)throws Exception{
        return (PageData)dao.findForObject("ActivityMapper.findByType", pd);
    }
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ActivityMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ActivityMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ActivityMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ActivityMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ActivityMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ActivityMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateByKey(PageData pd) throws Exception {
		dao.update("ActivityMapper.updateByKey", pd);
	}
	
}

