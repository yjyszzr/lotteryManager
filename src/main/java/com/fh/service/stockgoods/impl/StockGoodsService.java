package com.fh.service.stockgoods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.stockgoods.StockGoodsManager;
import com.fh.util.PageData;


/** 
 * 说明： 库存
 * 创建人：FH Q313596790
 * 创建时间：2017-09-13
 * @version
 */
@Service("stockgoodsService")
public class StockGoodsService implements StockGoodsManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SzyStockGoodsMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SzyStockGoodsMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SzyStockGoodsMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SzyStockGoodsMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SzyStockGoodsMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SzyStockGoodsMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SzyStockGoodsMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listByStore(Page page) throws Exception {
		return (List<PageData>)dao.findForList("SzyStockGoodsMapper.storedatalistPage", page);
	}

	@Override
	public List<PageData> listALLByStore(PageData pd) throws Exception {
		List<PageData> list=(List<PageData>)dao.findForList("SzyStockGoodsMapper.listALLByStore", pd);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> excelList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SzyStockGoodsMapper.excelList", pd);
	}
	
}

