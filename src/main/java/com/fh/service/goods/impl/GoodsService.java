package com.fh.service.goods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.service.dst.goodssku.GoodsSkuManager;
import com.fh.service.erp.skugroup.SkuGroupManager;
import com.fh.service.goods.GoodsManager;
import com.fh.util.PageData;
import com.fh.util.express.enums.PreSalesType;

/** 
 * 说明： 商品管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-14
 * @version
 */
@Service("goodsService")
public class GoodsService implements GoodsManager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	@Resource(name="goodsskuService")
	private GoodsSkuManager goodsSkuManager;
	@Resource(name="skugroupService")
	private SkuGroupManager skugroupService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		String supplier_id=pd.getString("supplier_id");
		if(supplier_id==null||supplier_id.trim().length()==0) {
			supplier_id="0";
		}
		pd.put("supplier_id", supplier_id);
		PageData pdQuery=goodsSkuManager.findByEncode(pd);
		if(pdQuery!=null) {
			throw new Exception("error_400");
		}
		PageData pdQuerybarcode=goodsSkuManager.findByBarcode(pd);
		if(pdQuerybarcode!=null) {
			throw new Exception("error_500");
		}
		pd.put("shop_goods_barcode", pd.getString("goods_barcode"));
		dao.save("GoodsMapper.save", pd);
		//sku
		pd.put("sku_name", pd.get("goods_name"));
		pd.put("sku_barcode",pd.get("goods_barcode"));
		//pd.put("sku_barcode", "");
		//				pd.put("goods_price", value);
		pd.put("cost_price", "0");
		pd.put("member_price", "0");
		pd.put("wholesale_price", "0");
		pd.put("is_enable", "1");
		pd.put("sku_image", "");
		pd.put("min_warn", "0");
		pd.put("spec_ids", "");
		pd.put("spec_vids", "");
		pd.put("discount_rate", "0");
		pd.put("goods_number", "0");
		pd.put("transit_number", "0");
		pd.put("admin_id", "0");
		goodsSkuManager.save(pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		PageData pddel=new PageData();
		pddel.put("goods_id", pd.getString("goods_id").trim());
		PageData re= goodsSkuManager.findByGoodsId(pddel);
		Object sku_id = re.get("sku_id");
		
		PageData queryComb = new PageData();
		queryComb.put("parent_sku_id", sku_id);
		List<PageData> res = skugroupService.listByParentId(queryComb);
		if (res!=null && res.size()>0) {
			throw new Exception("组合中存在的关联商品");
		}
		queryComb = new PageData();
		queryComb.put("sku_id", sku_id);
		res = skugroupService.listBySkuId(queryComb);
		if (res!=null && res.size()>0) {
			throw new Exception("作为组合商品的一部分禁止删除!");
		}
		
		dao.delete("GoodsMapper.delete", pddel);
		goodsSkuManager.delete(pddel);
		
		
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		PageData pdQuery=goodsSkuManager.findByEncodeAndGoodsId(pd);
		if(pdQuery!=null)  {
			throw new Exception("error_400");
		}
		PageData pdQuerybarcode=goodsSkuManager.findByBarcodeAndGoodsId(pd);
		if(pdQuerybarcode!=null) {
			throw new Exception("error_500");
		}
		pd.put("shop_goods_barcode", pd.getString("goods_barcode"));
		dao.update("GoodsMapper.edit", pd);
		pd.put("sku_barcode",pd.get("goods_barcode"));
		goodsSkuManager.edit(pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsMapper.datalistPage", page);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsMapper.querylistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GoodsMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GoodsMapper.deleteAll", ArrayDATA_IDS);
	}

	public List<PageData> listUnits(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("GoodsMapper.listUnits", pd);
	}

	public void updateSynState(PageData pd) throws Exception {
		dao.update("GoodsMapper.updateSynState", pd);
		
	}

	public PageData findByGoodsId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsMapper.findByGoodsId", pd);
	}
	
	public PageData findBySkuId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GoodsMapper.findBySkuId", pd);
	}

	public PageData findBySkuEncode(PageData pd) throws Exception {
		 return (PageData)dao.findForObject("GoodsMapper.findBySkuEncode", pd);
	}

	public int findBySkuEncode(String sku_encode) throws Exception {
		PageData pdSku = new PageData();
		pdSku.put("sku_encode", sku_encode);
		PageData pageSku=this.findBySkuEncode(pdSku);
		if(pageSku==null) {
			return 1;
		}
		String is_pre_sales = pageSku.getString("is_pre_sales");
		String is_combination_goods = pageSku.get("is_combination_goods").toString();
		//如果是预售或组合商品，返回1
		if(PreSalesType.IS_YES.getCode().equals(is_pre_sales) || PreSalesType.IS_CB_YES.getCode().equals(is_combination_goods)) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}

