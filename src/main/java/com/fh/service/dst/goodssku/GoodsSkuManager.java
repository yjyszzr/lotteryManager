package com.fh.service.dst.goodssku;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 商品sku关系列表接口
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
public interface GoodsSkuManager{

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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**报损选择列表 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list2Scrap(Page page)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listByStore(Page page)throws Exception;
	
	/**列表（通过供应商）
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listBySupplier(Page page)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listJoinGoods(Page page)throws Exception;
	
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
	
	public PageData findByGoodsId(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 查询SKU
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findSkuById(PageData pd)throws Exception;
	
	/**
	 * 查询SKU 通过物料编码
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByEncode(PageData pd)throws Exception;
	
	/**
	 * 查询SKU 通过物料编码和物料id
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByEncodeAndGoodsId(PageData pd)throws Exception;
	
	/**
	 * 查询SKU 通过物料编码
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBarcode(PageData pd)throws Exception;
	
	/**
	 * 查询SKU 通过物料编码和物料id
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBarcodeAndGoodsId(PageData pd)throws Exception;
	
	public List<PageData> listSkuNotComb(Page page)throws Exception;
}

