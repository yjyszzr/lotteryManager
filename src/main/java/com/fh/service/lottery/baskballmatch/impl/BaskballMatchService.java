package com.fh.service.lottery.baskballmatch.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.lottery.baskballmatch.BaskballMatchManager;
import com.fh.util.PageData;

/** 
 * 说明： 篮球彩列表
 * 创建人：FH Q313596790
 * 创建时间：2019-09-20
 * @version
 */
@Service("baskballmatchService")
public class BaskballMatchService implements BaskballMatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BaskballMatchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BaskballMatchMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BaskballMatchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BaskballMatchMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BaskballMatchMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BaskballMatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BaskballMatchMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateStatus(PageData pd) throws Exception {
		dao.update("BaskballMatchMapper.updateStatus", pd);
	}
	@Override
	public void updateDel(PageData pd) throws Exception {
		dao.update("BaskballMatchMapper.updateDel", pd);
	}
	
}

