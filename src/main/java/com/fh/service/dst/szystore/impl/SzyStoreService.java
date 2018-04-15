package com.fh.service.dst.szystore.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.util.PageData;

/** 
 * 说明： 仓库
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("szystoreService")
public class SzyStoreService implements SzyStoreManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	@Override
	public PageData findTotalStore(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SzyStoreMapper.findTotalStore", pd);
	}
	public PageData findClubStore(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SzyStoreMapper.findClubStore", pd);
	}
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SzyStoreMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SzyStoreMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SzyStoreMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SzyStoreMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SzyStoreMapper.szyStoreListAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SzyStoreMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SzyStoreMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByStoreSn(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SzyStoreMapper.findByStoreSn", pd);
	}
	@Override
	public PageData findByType(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SzyStoreMapper.findByType", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findByPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SzyStoreMapper.findByPagelistPage", page);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updatestorerel(PageData pd)throws Exception{
		dao.update("SzyStoreMapper.updatestorerel", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listBySupplier(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SzyStoreMapper.listBySupplier", pd);
	}
}

