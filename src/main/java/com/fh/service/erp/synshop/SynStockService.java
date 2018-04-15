package com.fh.service.erp.synshop;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.common.OrderShippingStatusEnum;
import com.fh.service.dst.outbound.PgtOutboundManager;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.erporder.ErpOrderManager;
import com.fh.service.erp.goodsshoperpstock.GoodsShopErpStockManager;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.service.erp.storeshop.StoreShopManager;
import com.fh.service.erp.szydelivery.SzyDeliveryManager;
import com.fh.service.erp.szydeliverygoods.DeliveryGoodsManager;
import com.fh.service.goods.GoodsManager;
import com.fh.sms.SmsFactoySingleton;
import com.fh.util.FormType;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.hazelcast.collection.impl.txnqueue.QueueTransactionLogRecord;

@Service("synStockService")
public class SynStockService implements SynStockManager {

	@Resource(name = "inboundService")
	private InboundManager inboundService;

	@Resource(name = "pgtoutboundService")
	private PgtOutboundManager pgtoutboundService;

	protected Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "goodsshoperpstockService")
	private GoodsShopErpStockManager goodsshoperpstockService;

	@Resource(name = "goodsService")
	private GoodsManager goodsService;
	@Resource(name="erporderService")
	private ErpOrderManager erporderService;
	
	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	
	@Resource(name="szystoreService")
	private SzyStoreManager szystoreService;
	
	@Resource(name="szydeliveryService")
	private SzyDeliveryManager szydeliveryService;
	@Resource(name="deliverygoodsService")
	private DeliveryGoodsManager deliverygoodsService;
	
	@Override
	public void SynInbound() throws Exception {
		PageData pd = new PageData();
		List<PageData> list = inboundService.listAllTimeSpan10(pd);
		for (PageData pageDataInbound : list) {
			try {
				String inbound_code = pageDataInbound.getString("inbound_code");
				String store_id = pageDataInbound.get("store_id").toString();
				//目前默认店铺为北京店,Id=2
				String shop_id = "2";
				String shop_name = "璞谷塘自营店北京站";
				PageData pdInbound = new PageData();
				pdInbound.put("inbound_code", inbound_code);
				List<PageData> listInbound = inboundService.listInboundDetail(pdInbound);
				for (PageData pageDataInboundDetail : listInbound) {
					// pageDataInboundDetail
					String sku_encode = pageDataInboundDetail.getString("sku_encode");
					//预售或组合商品不进行库存同步
					int is_pre_sales = goodsService.findBySkuEncode(sku_encode);
					if (is_pre_sales == 1) {
						continue;
					}
					String strquantity = pageDataInboundDetail.getString("quantity");
					int quantity = Integer.valueOf(strquantity);
					goodsshoperpstockService.updateStock(shop_id, shop_name, sku_encode, quantity, store_id);
				}
				String inbound_id = pageDataInbound.getString("inbound_id");
				PageData pdSynStatus = new PageData();
				pdSynStatus.put("inbound_id", inbound_id);
				inboundService.updateSynStatus(pdSynStatus);
			} catch (Exception ee) {
				logger.info(ee);
			}

		}

	}

	@Override
	public void SynOutbound() throws Exception {
		PageData pd = new PageData();
		List<PageData> list = pgtoutboundService.listAllTimeSpan10(pd);
		for (PageData pageDataOutbound : list) {
			try {
				String outbound_code = pageDataOutbound.getString("out_bound_code");
				String store_id = pageDataOutbound.get("store_id").toString();
				//目前默认店铺为北京店,Id=2
				String shop_id = "2";
				String shop_name = "璞谷塘自营店北京站";
				PageData pdoutbound = new PageData();
				pdoutbound.put("outbound_code", outbound_code);
				List<PageData> listoutbound = pgtoutboundService.listOutboundDetail(pdoutbound);
				for (PageData pageDataOutboundDetail : listoutbound) {
					// pageDataInboundDetail
					String sku_encode = pageDataOutboundDetail.getString("sku_encode");
					int is_pre_sales = goodsService.findBySkuEncode(sku_encode);
					//预售或组合商品不进行库存同步
					if (is_pre_sales == 1) {
						continue;
					}
					String strquantity = pageDataOutboundDetail.getString("quantity");
					int quantity = Integer.valueOf(strquantity);
					quantity = quantity * (-1);
					goodsshoperpstockService.updateStock(shop_id, shop_name, sku_encode, quantity, store_id);
				}
				String out_bound_id = pageDataOutbound.getString("out_bound_id");
				PageData pdSynStatus = new PageData();
				pdSynStatus.put("out_bound_id", out_bound_id);
				pgtoutboundService.updateSynStatus(pdSynStatus);
			} catch (Exception ee) {
				logger.info(ee);
			}
			
		}

	}

	@Override
	public void SynOrderShippingStatus() throws Exception {
		PageData pd = new PageData();
		List<PageData> list = pgtoutboundService.listAllOrder(pd);
		for (PageData pageDataOutbound : list) {
			String out_bound_id = pageDataOutbound.getString("out_bound_id");
			try {
				Object syn_status = pageDataOutbound.get("syn_status");
				if (syn_status!=null && !"0".equals(syn_status.toString())) {
					continue;
				}
				
				String store_sn = pageDataOutbound.getString("store_sn");
				PageData pdQuery = new PageData();
				pdQuery.put("store_sn", store_sn);
				
				PageData pageStore = szystoreService.findByStoreSn(pdQuery);
				Object store_id = pageStore.get("store_id");
				pdQuery = new PageData();
				pdQuery.put("store_id", store_id);
				PageData pageShop = goodsshoperpstockService.findShop(pdQuery);
				if (pageShop == null) {
					PageData pdOrder = new PageData();
					pdOrder.put("out_bound_id", out_bound_id);
					pdOrder.put("syn_status", -2);
					pgtoutboundService.updateSynStatus(pdOrder);
					logger.info(">>>>>>store" + store_sn + "not find shop;");
					continue;
				}
				
				String orderNo = pageDataOutbound.getString("purchase_code");
				PageData queryOrder = new PageData();
				queryOrder.put("order_sn", orderNo);
				PageData order = goodsshoperpstockService.findOrderByCode(queryOrder);
				if (order == null) {
					throw new Exception("找不到订单");
				}
				if (order.get("order_status")!=null && "true".equals(order.get("order_status"))) {
					throw new Exception("订单状态不合理");
				}
				goodsshoperpstockService.updateOrderStatus(orderNo);
				//创建发货单  发货单详情
				//更新 szy_delivery表shipping_id=0，express_sn=0,delivery_status=1(已发货),send_time=当前时间。
				Long time = System.currentTimeMillis() / 1000;
				//szydeliveryService
				//deliverygoodsService
				
				PageData delivery = new PageData();
				delivery.put("delivery_sn", getSerialConfigUtilService.getSerialCode(FormType.DELIVERYGOODS));
				delivery.put("order_id", order.get("order_id"));
				delivery.put("user_id", order.get("user_id"));
				delivery.put("shipping_id", "0");
				delivery.put("shipping_code", "0");
				delivery.put("shipping_name", "");
				delivery.put("shipping_type", "2");
				delivery.put("sender_id", "0");
				delivery.put("region_code", pageStore.get("region_code"));
				delivery.put("name", pageStore.get("contact"));
				delivery.put("address", pageStore.get("address"));
				delivery.put("tel", pageStore.get("tel"));
				delivery.put("express_sn", "0");
				delivery.put("delivery_status", "1");
				delivery.put("add_time", time);
				delivery.put("send_time", time);
				//delivery.put("icode", "");
				delivery.put("is_show", order.get("is_show"));
				szydeliveryService.save(delivery);
				queryOrder = new PageData();
				queryOrder.put("order_id", order.get("order_id"));
				List<PageData> ordergoods = goodsshoperpstockService.findOrderGoodsByCode(queryOrder);
				for (PageData ordergood : ordergoods) {
					PageData deliverygoods = new PageData();
					deliverygoods.put("order_id", ordergood.get("order_id"));
					deliverygoods.put("delivery_id", delivery.get("delivery_id"));
					deliverygoods.put("goods_id", ordergood.get("goods_id"));
					deliverygoods.put("sku_id", ordergood.get("sku_id"));
					deliverygoods.put("send_number", ordergood.get("goods_number"));
					deliverygoods.put("is_gift", ordergood.get("is_gift"));
					deliverygoods.put("parent_id", ordergood.get("parent_id"));
					deliverygoodsService.save(deliverygoods);
				}

				List<PageData> listUserByOrderSn = goodsshoperpstockService.listUserByOrderSn(orderNo);
				if (listUserByOrderSn.size() > 0) {
					PageData pdUser = listUserByOrderSn.get(0);
					String mobile = pdUser.getString("mobile");
					String user_name = pdUser.getString("user_name");
					String msg = String.format("亲爱的顾客%s,您的订单%s已发货，请您注意查收", user_name, orderNo);
					SmsFactoySingleton.getSmsProvider().send(mobile, msg);
					//erporderService.updateErpOrder(orderNo, OrderShippingStatusEnum.TYPE1_OUTBOUND.getCode());
				} else {
					//logger.info(">>>>>> no find user " + store_sn + "");
				}
				
				
				PageData pdOrder = new PageData();
				pdOrder.put("out_bound_id", out_bound_id);
				pdOrder.put("syn_status", 1);
				pgtoutboundService.updateSynStatus(pdOrder);
			} catch (Exception ee) {
				PageData pdOrder = new PageData();
				pdOrder.put("out_bound_id", out_bound_id);
				pdOrder.put("syn_status", -1);
				pgtoutboundService.updateSynStatus(pdOrder);
				logger.info(ee);
			}

		}

	}
}
