package com.fh.service.dst.goodssku.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.goodssku.GoodsSkuManager;
import com.fh.util.PageData;

/** 
 * 说明： 商品sku关系列表
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("goodsskuService")
public class GoodsSkuService implements GoodsSkuManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GoodsSkuMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("GoodsSkuMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GoodsSkuMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsSkuMapper.datalistPage", page);
	}
	/**
	 * 报损选择列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list2Scrap(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsSkuMapper.datalistPage2Scrap", page);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByStore(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsSkuMapper.dataByStorelistPage", page);
	}
	
	/**列表(通过供应商)
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBySupplier(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsSkuMapper.dataBySupplierlistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GoodsSkuMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsSkuMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GoodsSkuMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**模糊查询物料名称，返回物料对象
	 * @param skuName
	 * @throws Exception
	 */
	public List<PageData> findByLikeSkuName(String skuName) throws Exception {
		PageData pd = new PageData();
		pd.put("keywords", skuName.trim());
		List<PageData> pds = this.listAll(pd);
		return pds;
	}
	
	public PageData findSkuById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsSkuMapper.findSkuById", pd);
	}

	@Override
	public PageData findByGoodsId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsSkuMapper.findByGoodsId", pd);
	}

	@Override
	public List<PageData> listJoinGoods(Page page) throws Exception {
		return (List<PageData>)dao.findForList("GoodsSkuMapper.goodsdatalistPage", page);
	}

	@Override
	public PageData findByEncode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsSkuMapper.findByEncode", pd);
	}

	@Override
	public PageData findByEncodeAndGoodsId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsSkuMapper.findByEncodeAndGoodsId", pd);
	}

	@Override
	public PageData findByBarcode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsSkuMapper.findByBarcode", pd);
	}

	@Override
	public PageData findByBarcodeAndGoodsId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsSkuMapper.findByBarcodeAndGoodsId", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listSkuNotComb(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsSkuMapper.listSkuNotComb", page);
	}
}

