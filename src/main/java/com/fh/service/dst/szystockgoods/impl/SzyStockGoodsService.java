package com.fh.service.dst.szystockgoods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.szystockgoods.SzyStockGoodsManager;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.util.PageData;


/** 
 * 说明： 库存
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("szystockgoodsService")
public class SzyStockGoodsService implements SzyStockGoodsManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	@Resource(name = "szystoreService")
	private SzyStoreManager szyStoreManager;
	
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
	
	@SuppressWarnings("unchecked")
	public List<PageData> stockBySkuIdlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SzyStockGoodsMapper.stockBySkuIdlistPage", page);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SzyStockGoodsMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void quantityModify(double quantity, String storeSn, int skuId,
			int direction) throws Exception {
		
		int storeId=0;
		PageData pdStoreParameter=new PageData();
		pdStoreParameter.put("store_sn", storeSn);
		PageData pdStore=szyStoreManager.findByStoreSn(pdStoreParameter);
		
		if(pdStore!=null)
		{
			storeId=Integer.parseInt(pdStore.get("store_id").toString());
		}
	
		//查询商品信息
		if(quantity<=0)
		{
			throw new Exception("更新数量不能为0");
		}
		if(storeId<=0)
		{
			throw new Exception("查询不到仓库信息");
		}
		if(skuId<=0)
		{
			throw new Exception("skuID不能小于0");
		}
		
		if(direction!=0&&direction!=1)
		{
			throw new Exception("出入库方向错误");
		}
		
		
		if(direction==0)
		{
			//库存增加
			//判断数据库中是否存在，存在更新，不存在插入
			PageData pdParameter=new PageData();
			pdParameter.put("sku_id", skuId);
			pdParameter.put("store_id", storeId);
			List<PageData> pd=findByWhere(pdParameter);
			pdParameter.put("goods_number",quantity);
			pdParameter.put("goods_id", 0);
			if(pd==null || pd.size() <= 0)
			{
				
				pdParameter.put("min_number_warn", 0);
				pdParameter.put("max_number_warn", 0);
				pdParameter.put("first_goods_number", 0);
				pdParameter.put("unit_cost", 0);
				pdParameter.put("is_deleted", 0);
				pdParameter.put("admin_id", 0);
				//插入
				save(pdParameter);
			}
			else
			{
				//更新
				updateQuantity(pdParameter);
			}
			
		}
		if(direction==1)
		{
			//库存减少
			PageData pdParameter=new PageData();
			pdParameter.put("sku_id", skuId);
			pdParameter.put("store_id", storeId);
			List<PageData> pd=findByWhere(pdParameter);
			if(pd==null || pd.size() <= 0)
			{
				throw new Exception("查询不到商品库存信息");
			}
			pdParameter.put("goods_number",-quantity);
			updateQuantity(pdParameter);
			
			
		}
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void quantityModify(double quantity, int storeId, int skuId,
			int direction) throws Exception {
		
		
		
	
	
		//查询商品信息
		if(quantity<=0)
		{
			throw new Exception("更新数量不能为0");
		}
		if(storeId<=0)
		{
			throw new Exception("查询不到仓库信息");
		}
		if(skuId<=0)
		{
			throw new Exception("skuID不能小于0");
		}
		
		if(direction!=0&&direction!=1)
		{
			throw new Exception("出入库方向错误");
		}
		
		
		if(direction==0)
		{
			//库存增加
			//判断数据库中是否存在，存在更新，不存在插入
			PageData pdParameter=new PageData();
			pdParameter.put("sku_id", skuId);
			pdParameter.put("store_id", storeId);
			List<PageData> pd=findByWhere(pdParameter);
			pdParameter.put("goods_number",quantity);
			pdParameter.put("goods_id", 0);
			if(pd==null || pd.size() <= 0)
			{
				
				pdParameter.put("min_number_warn", 0);
				pdParameter.put("max_number_warn", 0);
				pdParameter.put("first_goods_number", 0);
				pdParameter.put("unit_cost", 0);
				pdParameter.put("is_deleted", 0);
				pdParameter.put("admin_id", 0);
				//插入
				save(pdParameter);
			}
			else
			{
				//更新
				updateQuantity(pdParameter);
			}
			
		}
		if(direction==1)
		{
			//库存减少
			PageData pdParameter=new PageData();
			pdParameter.put("sku_id", skuId);
			pdParameter.put("store_id", storeId);
			List<PageData> pds=findByWhere(pdParameter);
			if(pds==null || pds.size() <= 0)
			{
				throw new Exception("查询不到商品库存信息");
			}
			else
			{
				PageData pd = pds.get(0);
				double goodNumber=Double.valueOf(pd.getString("goods_number"));
				if(goodNumber<=0)
				{
					throw new Exception("库存不足");
				}
				if(quantity>goodNumber)
				{
					throw new Exception("商品库存不足");
				}
			}
			pdParameter.put("goods_number",-quantity);
			updateQuantity(pdParameter);
			
			
		}
	}


	@Override
	public List<PageData> findByWhere(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SzyStockGoodsMapper.findByWhere", pd);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateQuantity(PageData pd) throws Exception {
		int rst = (int)dao.update("SzyStockGoodsMapper.updateQuantity", pd);
		if(rst <= 0) {
			throw new Exception("库存不足！");
		}
	}
	
}

