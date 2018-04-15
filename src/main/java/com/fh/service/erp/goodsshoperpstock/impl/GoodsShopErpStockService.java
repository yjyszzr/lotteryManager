package com.fh.service.erp.goodsshoperpstock.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport3;
import com.fh.entity.Page;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.express.enums.ShippingStatusEnum;
import com.fh.service.erp.goodsshoperpstock.GoodsShopErpStockManager;
import com.fh.service.erp.storeshop.StoreShopManager;

/**
 * 说明： 商城店铺与ERP仓库对应 创建人：FH Q313596790 创建时间：2017-10-02
 * 
 * @version
 */
@Service("goodsshoperpstockService")
public class GoodsShopErpStockService implements GoodsShopErpStockManager {

	protected Logger logger = Logger.getLogger(this.getClass());
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao;
	@Resource(name = "storeshopService")
	private StoreShopManager storeshopService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("GoodsShopErpStockMapper.save", pd);
	}
	
	public void saveWarehouseStock(PageData pd) throws Exception {
		dao.save("GoodsShopErpStockMapper.saveWarehouseStock", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("GoodsShopErpStockMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("GoodsShopErpStockMapper.edit", pd);
	}
	
	public void updateWarehouseStock(PageData pd) throws Exception {
		dao.update("GoodsShopErpStockMapper.updateWarehouseStock", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("GoodsShopErpStockMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("GoodsShopErpStockMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GoodsShopErpStockMapper.findById", pd);
	}
	
	public PageData findByWarehouseCode(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GoodsShopErpStockMapper.findByWarehouseCode", pd);
	}
	
	public PageData findByStockParam(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GoodsShopErpStockMapper.findByStockParam", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("GoodsShopErpStockMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updateShopStock(PageData pd) throws Exception {
		dao.update("GoodsShopErpStockMapper.updateShopStock", pd);
	}

	@Override
	public void updateStock(String shop_id, String shop_name, String sku_encode, int quantity, String store_Id) throws Exception {
		PageData pdQuerySn = new PageData();
		pdQuerySn.put("shop_id", shop_id);
		pdQuerySn.put("goods_sn", sku_encode);
		List<PageData> list = listShopGoodsAll(pdQuerySn);
		if (list == null || list.size() == 0) {
			logger.info(">>>>>>shop " + shop_name + " can not find goods:" + sku_encode);
			return;
		}
		if (list != null && list.size() >= 2) {
			logger.info(">>>>>>shop " + shop_name + "find mul goods:" + sku_encode);
			return;
		}
		logger.info(">>>>>>shop " + shop_name + " quantity:" + quantity + " goods_id:" + Integer.parseInt(list.get(0).get("goods_id").toString()) + " warehouse_code:" + store_Id);
		//判断szy_warehouse_stock表里是否已经有该数据（根据goods_id和warehouse_code查询）
		PageData pd = new PageData();
		pd.put("shop_id", Integer.parseInt(shop_id));
		pd.put("goods_id", Integer.parseInt(list.get(0).get("goods_id").toString()));
		pd.put("warehouse_code", store_Id);
		PageData stockPd = findByStockParam(pd);
		if(stockPd == null) {
			if(quantity < 0) {
				logger.info(">>>>>>shop " + shop_name + "stock negative goods:" + sku_encode);
				return;
			}
			PageData warehousePd = new PageData();
			warehousePd.put("warehouse_code", store_Id);
			PageData whPd = findByWarehouseCode(warehousePd);
			if(whPd == null) {
				logger.info(">>>>>>shop " + shop_name + " can not find warehousecode:" + store_Id);
				return;
			}
			PageData newStockPd = new PageData();
			newStockPd.put("shop_id", Integer.parseInt(shop_id));
			newStockPd.put("goods_id", Integer.parseInt(list.get(0).get("goods_id").toString()));
			newStockPd.put("warehouse_code", store_Id);
			newStockPd.put("warehouse_id", Integer.parseInt(whPd.get("warehouse_id").toString()));
			newStockPd.put("stock_num", quantity);
			newStockPd.put("freight_id", 0);
			saveWarehouseStock(newStockPd);
		}else {
			Integer oldQuantity = Integer.parseInt(stockPd.get("stock_num").toString());
			int totalQuantity = oldQuantity+quantity;
			if(totalQuantity < 0) {
				logger.info(">>>>>>totalQuantity = " + totalQuantity + "，update quantity is negative");
				totalQuantity = 0;
			}
			stockPd.put("stock_num", totalQuantity);
			updateWarehouseStock(stockPd);
		}
	}

	@Override
	public List<PageData> listShopGoodsAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("GoodsShopErpStockMapper.listShopGoodsAll", pd);
	}

	@Override
	public void updateShopStockByGoodsId(PageData pd) throws Exception {
		dao.update("GoodsShopErpStockMapper.updateShopStockByGoodsId", pd);
	}

	@Override
	public PageData findByIdByShopAndSn(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GoodsShopErpStockMapper.findByIdByShopAndSn", pd);
	}

	public PageData findOrderByCode(PageData pd) throws Exception {
		
		return (PageData)dao.findForObject("SzyOrderMapper.findByCode", pd);
	}
	@Override
	public void updateOrderStatus(String orderNo) throws Exception {
		// TODO Auto-generated method stub
		//更新商城订单未已发货
		PageData pd = new PageData();
		pd.put("code", "receiving_term");
		PageData systemConfig = (PageData)dao.findForObject("SzyOrderMapper.systemConfigByCode", pd);
		Integer day = 7;
		if(null != systemConfig) {
			try {
				String valueStr = (String) systemConfig.get("value");
				if (valueStr!=null || !"".equals(valueStr)) {
					Integer value = Integer.parseInt(valueStr);
					day = (value != null && value > 0)?value:24;
				}
			} catch (Exception e) {

			}
		}
		Long sec = day*24*3600l;
		
		pd = new PageData();
		pd.put("order_sn", orderNo);
		pd.put("shipping_status", ShippingStatusEnum.SS_SHIPPED.getCode());
		Long time = System.currentTimeMillis() / 1000;
		pd.put("shipping_time", time.intValue());
		pd.put("confirm_time", time.intValue() + sec);
		dao.update("GoodsShopErpStockMapper.updateSynOrderStatus", pd);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findOrderGoodsByCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SzyOrderMapper.findOrderGoodsByCode", pd);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listUserByOrderSn(String orderNo) throws Exception {
		PageData pd = new PageData();
		pd.put("order_sn", orderNo);
		List<PageData> list = (List<PageData>) dao.findForList("GoodsShopErpStockMapper.listUserByOrderSn", pd);
		return list;
	}

	@Override
	public PageData findShop(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GoodsShopErpStockMapper.findShop", pd);
	}

	

}
