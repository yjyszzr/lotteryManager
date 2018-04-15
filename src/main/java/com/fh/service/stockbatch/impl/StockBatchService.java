package com.fh.service.stockbatch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.goodssku.GoodsSkuManager;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.util.PageData;


/** 
 * 说明： 批次
 * 创建人：FH Q313596790
 * 创建时间：2017-09-14
 * @version
 */
@Service("stockbatchService")
public class StockBatchService implements StockBatchManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	@Resource(name = "goodsskuService")
	private GoodsSkuManager goodsSkuManager;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("StockBatchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("StockBatchMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("StockBatchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StockBatchMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StockBatchMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByParam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StockBatchMapper.listAllByParam", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findBySkuAndStoreId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StockBatchMapper.findBySkuAndStoreId", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StockBatchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StockBatchMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	public PageData findByBatchAndSkuAndStoreId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StockBatchMapper.findByBatchAndSkuAndStoreId", pd);
	}
	@Transactional(rollbackFor=Exception.class)
	public void editQuantity(PageData pd)throws Exception{
		int rst = (int)dao.update("StockBatchMapper.editQuantity", pd);
		if(rst <= 0) {
			throw new Exception("库存不足！");
		}
	}
	@Transactional(rollbackFor=Exception.class)
	public String insertBatchStock(int store_id,String batch_code,int sku_id,double quantity,int direciton)throws Exception
	{
		if(store_id<=0)
		{
			throw new Exception("查询不到仓库信息");
		}
		PageData pdSku=new PageData();
		pdSku.put("sku_id", sku_id);
		PageData  pdSkuInfo=goodsSkuManager.findSkuById(pdSku);
		if(pdSkuInfo==null)
		{
			throw new Exception("查询不到商品信息");
		}
		String sku_name=pdSkuInfo.getString("sku_name");
		String sku_encode=pdSkuInfo.getString("sku_encode");
		String unitname=pdSkuInfo.getString("unit_name");
		//判断是否存在，如果不存在插入，存在更新
		PageData pdBatchStockParameter=new PageData();
		pdBatchStockParameter.put("store_id",store_id);
		pdBatchStockParameter.put("sku_id",sku_id);
		pdBatchStockParameter.put("batch_code",batch_code);
		PageData pdBatchStock=findByBatchAndSkuAndStoreId(pdBatchStockParameter);
		if(pdBatchStock==null)
		{
			if(direciton==0)
			{
				pdBatchStockParameter.put("sku_name",sku_name);
				pdBatchStockParameter.put("sku_encode",sku_encode);
				pdBatchStockParameter.put("quantity",quantity);
				pdBatchStockParameter.put("unit_name",unitname);
				//插入
				save(pdBatchStockParameter);
			}
			if(direciton==1)
			{    //扣减
				// throw new Exception(sku_name+"批次信息不存在");
			}
		}
		else
		{
			if(direciton==0)
			{
				//增加
				pdBatchStockParameter.put("quantity",quantity);
			}
			if(direciton==1)
			{
				//判断库存是否足够
			    double	curquantity=Double.parseDouble(pdBatchStock.getString("quantity"));
				if(curquantity<quantity)
				{
					// throw new Exception(sku_name+"批次库存不足!");
				}
				//扣减
				pdBatchStockParameter.put("quantity",-quantity);
			}
			editQuantity(pdBatchStockParameter);
		}
		return "";
	}
	
}
