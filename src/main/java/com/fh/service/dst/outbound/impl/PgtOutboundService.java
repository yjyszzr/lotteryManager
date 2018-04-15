package com.fh.service.dst.outbound.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.common.DefetiveConstants;
import com.fh.common.InOutBoundType;
import com.fh.common.OrderShippingStatusEnum;
import com.fh.common.ScrapConstants;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.allocation.AllocationManager;
import com.fh.service.dst.allocationdetail.AllocationDetailManager;
import com.fh.service.dst.otheroutstorage.OtherOutstorageManager;
import com.fh.service.dst.outbound.PgtOutboundManager;
import com.fh.service.dst.outbounddetail.impl.PgtOutboundDetailService;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.outboundnoticestockbatch.OutboundNoticeStockBatchManager;
import com.fh.service.dst.outboundstockbatch.OutboundStockBatchManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.purchasematerial.PurchaseMaterialManager;
import com.fh.service.dst.purchasereturn.PurchaseReturnManager;
import com.fh.service.dst.purchasereturndetail.PurchaseReturnDetailManager;
import com.fh.service.dst.scrap.ScrapManager;
import com.fh.service.dst.szystockgoods.SzyStockGoodsManager;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.service.dst.warehouse.serialconfig.GetSerialConfigUtilManager;
import com.fh.service.erp.defectivegoods.DefectiveGoodsManager;
import com.fh.service.erp.directsales.DirectSalesManager;
import com.fh.service.erp.erporder.ErpOrderManager;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.service.erp.inbounddetail.InboundDetailManager;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.service.inboundstockbatch.InboundStockBatchManager;
import com.fh.service.order.OrderManager;
import com.fh.service.orderstockbatch.OrderStockBatchManager;
import com.fh.service.orderstockbatchdetail.OrderStockBatchDetailManager;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.JsonUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 说明： outbound 创建人：FH Q313596790 创建时间：2017-09-10
 * 
 * @version
 */
@Service("pgtoutboundService")
public class PgtOutboundService implements PgtOutboundManager {

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;

	@Resource(name = "pgtoutboundService")
	private PgtOutboundManager pgtoutboundService;

	@Resource(name = "pgtoutbounddetailService")
	private PgtOutboundDetailService pgtOutboundDetailService;

	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilManager getSerialConfigUtilService;

	@Resource(name = "outboundnoticeService")
	private OutboundNoticeManager outboundNoticeManager;

	@Resource(name = "outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundNoticeDetailService;

	@Resource(name = "allocationdetailService")
	private AllocationDetailManager allocationDetailManager;

	@Resource(name = "allocationService")
	private AllocationManager allocationManager;

	@Resource(name = "purchasematerialService")
	private PurchaseMaterialManager purchaseMaterialManager;

	@Resource(name = "szystockgoodsService")
	private SzyStockGoodsManager szyStockGoodsManager;

	@Resource(name = "inboundnoticeService")
	private InboundNoticeManager inboundNoticeService;

	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailManager inboundNoticeDetailManager;

	@Resource(name = "inboundService")
	private InboundManager inboundManager;

	@Resource(name = "inbounddetailService")
	private InboundDetailManager inboundDetailManager;

	@Resource(name = "szystoreService")
	private SzyStoreManager szyStoreManager;

	@Resource(name = "purchasereturndetailService")
	private PurchaseReturnDetailManager purchaseReturnDetailManager;

	@Resource(name = "purchasereturnService")
	private PurchaseReturnManager purchaseReturnManager;

	@Resource(name = "outboundstockbatchService")
	private OutboundStockBatchManager outboundStockBatchService;

	// 出库批次
	@Resource(name = "stockbatchService")
	private StockBatchManager stockBatchManager;

	@Resource(name = "orderstockbatchService")
	private OrderStockBatchManager orderStockBatchManager;

	@Resource(name = "orderstockbatchdetailService")
	private OrderStockBatchDetailManager orderStockBatchDetailManager;

	@Resource(name = "orderService")
	private OrderManager orderService;

	@Resource(name = "outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;

	@Resource(name = "inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;

	@Resource(name = "inboundstockbatchService")
	private InboundStockBatchManager inboundStockBatchManager;

	@Resource(name = "purchasedetailService")
	private PurchaseDetailManager purchaseDetailManager;
	@Resource(name = "outboundnoticestockbatchService")
	private OutboundNoticeStockBatchManager outboundNoticeStockBatchService;

	@Resource(name = "defectivegoodsService")
	private DefectiveGoodsManager defectivegoodsService;
	@Resource(name = "scrapService")
	private ScrapManager scrapService;
	@Resource(name = "otheroutstorageService")
	private OtherOutstorageManager otheroutstorageService;

	@Resource(name = "erporderService")
	private ErpOrderManager erporderService;
	
	@Resource(name="directsalesService")
	private DirectSalesManager directsalesService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("PgtOutboundMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("PgtOutboundMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("PgtOutboundMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PgtOutboundMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PgtOutboundMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listOutboundInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PgtOutboundMapper.listOutboundInfo", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PgtOutboundMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("PgtOutboundMapper.deleteAll", ArrayDATA_IDS);
	}

	public void noticeShop(PageData pdnotice) {
		try {
			// 发货通知单
			if (pdnotice.get("bill_type").toString().equals("4")) {
				SortedMap<String, Object> pdOrder = new TreeMap<String, Object>();
				pdOrder.put("order_no", pdnotice.get("purchase_code"));
				String strjson = JsonUtils.beanToJSONString(pdOrder);
				// 推信息到消息队列

			}
		} catch (Exception ee) {
			/// logBefore(logger, Jurisdiction.getUsername()+"新增Allocation");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public String saveOutBound1(long notice_id, String outbound_notice_code, String userName) throws Exception {

		PageData pdNoticeMap = new PageData();
		pdNoticeMap.put("outbound_notice_id", notice_id);
		// 查询发货通知单
		PageData pdnotice = outboundNoticeManager.findById(pdNoticeMap);
		if (pdnotice == null) {
			throw new Exception("未查询到出库通知单！");
		}
		Integer status = (Integer) pdnotice.get("status");
		if (1 == status) {
			throw new Exception("出库通知单已取消！");
		}
		if (10 == status) {
			throw new Exception("出库通知单已出库！");
		}
		// 查询发货同单明细
		PageData pdNoticeDetailMap = new PageData();
		pdNoticeDetailMap.put("outbound_notice_code", outbound_notice_code);
		List<PageData> pdnoticeDetailList = outboundNoticeDetailService.findAllByOutBoundNoticeCode(pdNoticeDetailMap);
		String outBoundCode = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
		String storeSn = pdnotice.getString("store_sn");
		// 出库单
		PageData pdOutBound = new PageData();
		if (pdnotice.get("bill_type").toString().equals("0")) {
			pdOutBound.put("type", InOutBoundType.LINGLIAO_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("1")) {
			pdOutBound.put("type", InOutBoundType.ALLOCATION_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("2")) {
			pdOutBound.put("type", InOutBoundType.PURCHASE_OUTBOUND_RETURN);
		}
		if (pdnotice.get("bill_type").toString().equals("8")) {
			pdOutBound.put("type", InOutBoundType.PRODUCT_OUTBOUND_RETURN);
		}
		if (pdnotice.get("bill_type").toString().equals("3")) {
			pdOutBound.put("type", InOutBoundType.SCRAP_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("4")) {
			pdOutBound.put("type", InOutBoundType.DIRECT_SALES_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("5")) {
			pdOutBound.put("type", InOutBoundType.ORDER_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("7")) {
			pdOutBound.put("type", InOutBoundType.BAD_GOODS_OUTBOUND);
		}

		if (pdnotice.get("bill_type").toString().equals("6")) {
			pdOutBound.put("type", InOutBoundType.OTHER_OUTSTORAGE_OUTBOUND);
		}
		pdOutBound.put("out_bound_code", outBoundCode);
		pdOutBound.put("purchase_code", pdnotice.get("purchase_code"));
		pdOutBound.put("purchase_detail_id", 0);
		pdOutBound.put("outbound_notice_code", pdnotice.get("outbound_notice_code"));
		pdOutBound.put("outbound_notice_detail_id", 0);
		// pdOutBound.put("business_time", "");
		pdOutBound.put("store_sn", pdnotice.get("store_sn"));
		pdOutBound.put("store_name", pdnotice.get("store_name"));
		// pdOutBound.put("status", "");
		pdOutBound.put("createby", userName);
		pdOutBound.put("updateby", userName);
		pgtoutboundService.save(pdOutBound);
		PageData pd = new PageData();
		pd.put("outbound_notice_code", pdnotice.get("outbound_notice_code"));
		List<PageData> list = outboundNoticeStockBatchService.list(pd);

		for (PageData pdItem : pdnoticeDetailList) {

			// 更新出库通知单发货数量
			PageData pdNoticeDetailUpdateQuantity = new PageData();
			pdNoticeDetailUpdateQuantity.put("send_quantity", pdItem.get("pre_send_quantity"));
			pdNoticeDetailUpdateQuantity.put("updateby", userName);
			pdNoticeDetailUpdateQuantity.put("outbound_notice_detail_id", pdItem.get("outbound_notice_detail_id"));
			outboundnoticedetailService.updateSendQuantity(pdNoticeDetailUpdateQuantity);
			pdItem.put("send_quantity", pdItem.get("pre_send_quantity"));

			// 创建出库单明细
			PageData pdDetailItem = new PageData();
			pdOutBound.put("type", InOutBoundType.OTHER_OUTSTORAGE_OUTBOUND);
			pdDetailItem.put("outbound_code", outBoundCode);
			pdDetailItem.put("purchase_code", pdItem.get("purchase_code"));
			pdDetailItem.put("purchase_detail_id", pdItem.get("purchase_detail_id"));
			pdDetailItem.put("outbound_notice_code", pdItem.get("outbound_notice_code"));
			pdDetailItem.put("outbound_notice_detail_id", pdItem.get("outbound_notice_detail_id"));
			pdDetailItem.put("sku_id", pdItem.get("sku_id"));
			pdDetailItem.put("sku_name", pdItem.get("sku_name"));
			pdDetailItem.put("sku_encode", pdItem.get("sku_encode"));
			pdDetailItem.put("spec", pdItem.get("spec"));
			pdDetailItem.put("quantity", pdItem.get("send_quantity"));
			pdDetailItem.put("status", "");
			pdDetailItem.put("createby", userName);
			pdDetailItem.put("updateby", userName);
			pgtOutboundDetailService.save(pdDetailItem);
			if (null != list) {
				Long outboundNoticeDetailId = (Long) pdItem.get("outbound_notice_detail_id");
				for (PageData pd1 : list) {
					Long notice_detail_id = (Long) pd1.get("notice_detail_id");
					if (!outboundNoticeDetailId.equals(notice_detail_id))
						continue;
					Double quantity = (Double) pd1.get("quantity");
					if (0 == quantity)
						continue;
					PageData nstockbatch = new PageData();
					nstockbatch.put("outbound_code", outBoundCode);
					nstockbatch.put("outbound_detail_id", pdDetailItem.get("outbound_detail_id"));
					nstockbatch.put("sku_id", pd1.get("sku_id"));
					nstockbatch.put("sku_name", pd1.get("sku_name"));
					nstockbatch.put("sku_encode", pd1.get("sku_encode"));
					nstockbatch.put("batch_code", pd1.get("batch_code"));
					nstockbatch.put("quantity", pd1.get("quantity"));
					nstockbatch.put("spec", pd1.get("spec"));
					nstockbatch.put("unit_name", pd1.get("unit_name"));
					outboundStockBatchService.save(nstockbatch);
				}
			}
			// 扣库存
			// szyStockGoodsManager.quantityModify(((Integer)pdItem.get("send_quantity")).doubleValue(),
			// storeSn, (Integer)pdItem.get("sku_id"), 1);
		}

		// 更改发货通知单状态
		PageData pdStatus = new PageData();
		pdStatus.put("status", 10);
		pdStatus.put("updateby", userName);
		pdStatus.put("outbound_notice_id", notice_id);
		// outboundNoticeManager.updateStatus(pdStatus);
		int rst = (int) outboundNoticeManager.updateStatus(pdStatus);
		if (rst <= 0) {
			throw new Exception("出库取消或已出库，本次出库失败！");
		}
		// 入库
		if (pdnotice.get("bill_type").toString().equals("3")) {
			// 报损
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String curData = df.format(new Date());
			PageData pdStoreParameter = new PageData();
			pdStoreParameter.put("store_sn", pdnotice.get("store_sn"));
			PageData pdStore = szyStoreManager.findByStoreSn(pdStoreParameter);
			Integer storeId = Integer.parseInt(pdStore.getString("store_id"));
			String scrap_store_id = pdStore.getString("scrap_store_id");

			PageData pdBadStoreParameter = new PageData();
			pdBadStoreParameter.put("store_id", scrap_store_id);
			PageData pdBadStore = szyStoreManager.findById(pdBadStoreParameter);
			if (pdBadStore == null) {
				throw new Exception("查询不到报损仓信息");
			}
			// 报损单号
			String scrap_code = pdnotice.getString("purchase_code");
			// 更新报损单状态-完成
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			PageData updateScrap = new PageData();
			updateScrap.put("scrap_code", scrap_code);
			updateScrap.put("status", ScrapConstants.BUSINESS_STATUS_COMPLETE);
			updateScrap.put("updateby", user.getUSER_ID());
			updateScrap.put("update_time", new Date());
			scrapService.editStatus(updateScrap);

			String inboundCode = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			PageData pdinbound = new PageData();
			pdinbound.put("inbound_code", inboundCode);
			// pd.put("inbound_type",4);
			pdinbound.put("inbound_type", InOutBoundType.SCRAP_INBOUND);
			pdinbound.put("purchase_code", scrap_code);
			pdinbound.put("purchase_detail_id", 0);
			pdinbound.put("inbound_notice_code", "");
			pdinbound.put("business_time", curData);
			pdinbound.put("store_sn", pdBadStore.get("store_sn"));
			pdinbound.put("store_name", pdBadStore.get("store_name"));
			pdinbound.put("status", 0);
			pdinbound.put("createby", userName);
			pdinbound.put("create_time", curData);
			pdinbound.put("updateby", userName);
			pdinbound.put("update_time", curData);
			inboundManager.save(pdinbound);
			// 批量插入入库单明细
			for (PageData pdItem : pdnoticeDetailList) {
				PageData pdInboundDetail = new PageData();
				pdInboundDetail.put("scrap_code", scrap_code);
				pdInboundDetail.put("inbound_code", inboundCode);
				pdInboundDetail.put("createby", userName);
				pdInboundDetail.put("updateby", userName);
				pdInboundDetail.put("purchase_code", pdItem.get("purchase_code"));
				pdInboundDetail.put("purchase_detail_id", pdItem.get("purchase_detail_id"));
				pdInboundDetail.put("inbound_notice_code", "");
				pdInboundDetail.put("notice_detail_id", 0);
				pdInboundDetail.put("sku_id", pdItem.get("sku_id"));
				pdInboundDetail.put("sku_name", pdItem.get("sku_name"));
				pdInboundDetail.put("sku_encode", pdItem.get("sku_encode"));
				pdInboundDetail.put("spec", pdItem.get("spec"));
				pdInboundDetail.put("quantity", pdItem.get("send_quantity"));
				pdInboundDetail.put("status", 0);
				inboundDetailManager.save(pdInboundDetail);
			}

			// inboundDetailManager.saveBatch(pdInboundDetail);

			PageData pdInboundCode = new PageData();
			pdInboundCode.put("inbound_code", inboundCode);
			List<PageData> pdInboundDetailList = inboundDetailManager.listByInboundCode(pdInboundCode);

			PageData pdStockBatch = new PageData();
			pdStockBatch.put("outbound_code", outBoundCode);
			List<PageData> pdOutboundStockBatchList = outboundStockBatchService.listByOutboundCode(pdStockBatch);
			// 入库批次
			if (pdInboundDetailList != null && pdInboundDetailList.size() > 0) {
				for (PageData pdDetail : pdInboundDetailList) {
					String inbound_detail_id = pdDetail.getString("inbound_detail_id");

					if (pdOutboundStockBatchList != null) {
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {

							int skuId = Integer.parseInt(pdoutboundStockBatch.getString("sku_id"));
							if (!String.valueOf(skuId).equals(pdDetail.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 入库批次表
							PageData pdInboundLogisticStockBatch = new PageData();
							pdInboundLogisticStockBatch.put("inbound_code", inboundCode);
							pdInboundLogisticStockBatch.put("inbound_detail_id", inbound_detail_id);
							pdInboundLogisticStockBatch.put("sku_id", pdDetail.getString("sku_id"));
							pdInboundLogisticStockBatch.put("sku_name", pdDetail.getString("sku_name"));
							pdInboundLogisticStockBatch.put("sku_encode", pdDetail.getString("sku_encode"));
							pdInboundLogisticStockBatch.put("batch_code", batchNo);
							pdInboundLogisticStockBatch.put("quantity", batchquantity);
							pdInboundLogisticStockBatch.put("spec", pdDetail.getString("spec"));
							pdInboundLogisticStockBatch.put("unit_name", pdDetail.getString("unit_name"));
							inboundStockBatchManager.save(pdInboundLogisticStockBatch);
						}
					}
				}
			}

			// 扣减批次库存
			for (PageData pdItem : pdnoticeDetailList) {
				// 增加报损库存
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					if (null != pdStore.getString("bad_store_id") && !pdStore.getString("bad_store_id").equals("")) {
						// 扣减源仓库库存
						szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
						// 增加报损仓库存
						szyStockGoodsManager.quantityModify(quantity, Integer.parseInt(scrap_store_id), skuId, 0);
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 扣减源仓批次库存
							stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity, 1);
							// 增加报损仓的库存
							stockBatchManager.insertBatchStock(Integer.parseInt(scrap_store_id), batchNo, skuId,
									batchquantity, 0);
						}
					} else {
						throw new Exception("查询不到报损仓信息");
					}
				}
			}
		}

		// 不良品入库
		if (pdnotice.get("bill_type").toString().equals("7")) {
			// 不良品
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String curData = df.format(new Date());
			PageData pdStoreParameter = new PageData();
			pdStoreParameter.put("store_sn", pdnotice.get("store_sn"));
			PageData pdStore = szyStoreManager.findByStoreSn(pdStoreParameter);
			Integer storeId = Integer.parseInt(pdStore.getString("store_id"));
			String bad_store_id = pdStore.getString("bad_store_id");

			PageData pdBadStoreParameter = new PageData();
			pdBadStoreParameter.put("store_id", bad_store_id);
			PageData pdBadStore = szyStoreManager.findById(pdBadStoreParameter);
			if (pdBadStore == null) {
				throw new Exception("查询不到不良品仓信息");
			}
			// 不良品单号
			String bad_code = pdnotice.getString("purchase_code");
			// 更新不良品状态-完成
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			PageData updateEfective = new PageData();
			updateEfective.put("defective_code", bad_code);
			updateEfective.put("status", DefetiveConstants.BUSINESS_STATUS_COMPLETE);
			updateEfective.put("updateby", user.getUSER_ID());
			updateEfective.put("update_time", new Date());
			defectivegoodsService.updateCheckStatus(updateEfective);

			String inboundCode = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			PageData pdinbound = new PageData();
			pdinbound.put("inbound_code", inboundCode);
			pdinbound.put("inbound_type", InOutBoundType.BAD_INBOUND_REJECT);
			pdinbound.put("purchase_code", bad_code);
			pdinbound.put("purchase_detail_id", 0);
			pdinbound.put("inbound_notice_code", "");
			pdinbound.put("business_time", curData);
			pdinbound.put("store_sn", pdBadStore.get("store_sn"));
			pdinbound.put("store_name", pdBadStore.get("store_name"));
			pdinbound.put("status", 0);
			pdinbound.put("createby", userName);
			pdinbound.put("create_time", curData);
			pdinbound.put("updateby", userName);
			pdinbound.put("update_time", curData);
			inboundManager.save(pdinbound);
			// 批量插入入库单明细
			// 批量插入入库单明细
			for (PageData pdItem : pdnoticeDetailList) {
				Integer quantity = (Integer) pdItem.get("send_quantity");
				if (quantity == null || quantity == 0)
					continue;
				PageData pdInboundDetail = new PageData();
				pdInboundDetail.put("scrap_code", bad_code);
				pdInboundDetail.put("inbound_code", inboundCode);
				pdInboundDetail.put("createby", userName);
				pdInboundDetail.put("updateby", userName);
				pdInboundDetail.put("purchase_code", pdItem.get("purchase_code"));
				pdInboundDetail.put("purchase_detail_id", pdItem.get("purchase_detail_id"));
				pdInboundDetail.put("inbound_notice_code", "");
				pdInboundDetail.put("notice_detail_id", 0);
				pdInboundDetail.put("sku_id", pdItem.get("sku_id"));
				pdInboundDetail.put("sku_name", pdItem.get("sku_name"));
				pdInboundDetail.put("sku_encode", pdItem.get("sku_encode"));
				pdInboundDetail.put("spec", pdItem.get("spec"));
				pdInboundDetail.put("quantity", quantity);
				pdInboundDetail.put("status", 0);
				inboundDetailManager.save(pdInboundDetail);
			}

			PageData pdInboundCode = new PageData();
			pdInboundCode.put("inbound_code", inboundCode);
			List<PageData> pdInboundDetailList = inboundDetailManager.listByInboundCode(pdInboundCode);

			PageData pdStockBatch = new PageData();
			pdStockBatch.put("outbound_code", outBoundCode);
			List<PageData> pdOutboundStockBatchList = outboundStockBatchService.listByOutboundCode(pdStockBatch);
			// 入库批次
			if (pdInboundDetailList != null && pdInboundDetailList.size() > 0) {
				for (PageData pdDetail : pdInboundDetailList) {
					String inbound_detail_id = pdDetail.getString("inbound_detail_id");
					if (pdOutboundStockBatchList != null) {
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {

							int skuId = Integer.parseInt(pdoutboundStockBatch.getString("sku_id"));
							if (!String.valueOf(skuId).equals(pdDetail.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							if (batchquantity == 0)
								continue;
							// 入库批次表
							PageData pdInboundLogisticStockBatch = new PageData();
							pdInboundLogisticStockBatch.put("inbound_code", inboundCode);
							pdInboundLogisticStockBatch.put("inbound_detail_id", inbound_detail_id);
							pdInboundLogisticStockBatch.put("sku_id", pdDetail.getString("sku_id"));
							pdInboundLogisticStockBatch.put("sku_name", pdDetail.getString("sku_name"));
							pdInboundLogisticStockBatch.put("sku_encode", pdDetail.getString("sku_encode"));
							pdInboundLogisticStockBatch.put("batch_code", batchNo);
							pdInboundLogisticStockBatch.put("quantity", batchquantity);
							pdInboundLogisticStockBatch.put("spec", pdDetail.getString("spec"));
							pdInboundLogisticStockBatch.put("unit_name", pdDetail.getString("unit_name"));
							inboundStockBatchManager.save(pdInboundLogisticStockBatch);
						}
					}
				}
			}

			// 扣减批次库存
			for (PageData pdItem : pdnoticeDetailList) {
				// 增加不良品库存
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					if (null != pdStore.getString("bad_store_id") && !pdStore.getString("bad_store_id").equals("")) {
						// 扣减源仓库库存
						szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
						// 增加不良品仓库存
						szyStockGoodsManager.quantityModify(quantity, Integer.parseInt(bad_store_id), skuId, 0);
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 扣减源仓批次库存
							stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity, 1);
							// 增加不良品仓的库存
							stockBatchManager.insertBatchStock(Integer.parseInt(bad_store_id), batchNo, skuId,
									batchquantity, 0);
						}
					} else {
						throw new Exception("查询不到不良仓信息");
					}
				}
			}
		}
		// 如果是不良品出库，在出库后回写不良品单状态为9:已完成
		if (pdnotice.get("bill_type").toString().equals("7")) {
			PageData pageData = new PageData();
			pageData.put("defective_code", pdnotice.get("purchase_code"));
			pageData.put("status", 9);
			pageData.put("updateby", userName);
			pageData.put("update_time", new Date());
			defectivegoodsService.updateStatus(pageData);
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public String saveOutBound(long notice_id, String outbound_notice_code, String userName, String stockbatchJson,
			String sendQuantityStr) throws Exception {
		PageData pdNoticeMap = new PageData();
		pdNoticeMap.put("outbound_notice_id", notice_id);
		// 查询发货通知单
		PageData pdnotice = outboundNoticeManager.findById(pdNoticeMap);
		if (pdnotice == null) {
			throw new Exception("未查询到出库通知单！");
		}
		Integer status = (Integer) pdnotice.get("status");
		if (1 == status) {
			throw new Exception("出库通知单已取消！");
		}
		if (10 == status) {
			throw new Exception("出库通知单已出库！");
		}
		// 查询发货同单明细
		PageData pdNoticeDetailMap = new PageData();
		pdNoticeDetailMap.put("outbound_notice_code", outbound_notice_code);
		List<PageData> pdnoticeDetailList = outboundNoticeDetailService.findAllByOutBoundNoticeCode(pdNoticeDetailMap);
		String outBoundCode = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
		String storeSn = pdnotice.getString("store_sn");

		// 查询仓库信息
		PageData pdStoreParameter = new PageData();
		pdStoreParameter.put("store_sn", storeSn);
		PageData pdStore = szyStoreManager.findByStoreSn(pdStoreParameter);

		PageData pdOutBound = new PageData();
		if (pdnotice.get("bill_type").toString().equals("0")) {
			pdOutBound.put("type", InOutBoundType.LINGLIAO_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("1")) {
			pdOutBound.put("type", InOutBoundType.ALLOCATION_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("2")) {
			pdOutBound.put("type", InOutBoundType.PURCHASE_OUTBOUND_RETURN);
		}
		if (pdnotice.get("bill_type").toString().equals("8")) {
			pdOutBound.put("type", InOutBoundType.PRODUCT_OUTBOUND_RETURN);
		}
		if (pdnotice.get("bill_type").toString().equals("3")) {
			pdOutBound.put("type", InOutBoundType.SCRAP_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("4")) {
			pdOutBound.put("type", InOutBoundType.DIRECT_SALES_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("5")) {
			pdOutBound.put("type", InOutBoundType.ORDER_GOOD_OUTBOUND);
		}
		if (pdnotice.get("bill_type").toString().equals("6")) {
			pdOutBound.put("type", InOutBoundType.OTHER_OUTSTORAGE_OUTBOUND);
		}
		pdOutBound.put("out_bound_code", outBoundCode);
		pdOutBound.put("purchase_code", pdnotice.get("purchase_code"));
		pdOutBound.put("purchase_detail_id", 0);
		pdOutBound.put("outbound_notice_code", pdnotice.get("outbound_notice_code"));
		pdOutBound.put("outbound_notice_detail_id", 0);
		pdOutBound.put("store_sn", pdnotice.get("store_sn"));
		pdOutBound.put("store_name", pdnotice.get("store_name"));
		pdOutBound.put("createby", userName);
		pdOutBound.put("updateby", userName);
		pgtoutboundService.save(pdOutBound);
		// 查找明细数量
		JsonObject jsonObjSendQuantity = JsonUtils.NewStringToJsonObject(sendQuantityStr);
		// 先更新出库通知单明细
		// 创建出库单主表
		int detailsRst = 0;
		JsonObject jsonObj = JsonUtils.NewStringToJsonObject(stockbatchJson);
		for (PageData pdItem : pdnoticeDetailList) {
			// 更新发货通知单数量
			if (jsonObjSendQuantity.get(pdItem.get("outbound_notice_detail_id").toString()) == null) {
				continue;
			}
			String sendQuantityValue = jsonObjSendQuantity.get(pdItem.get("outbound_notice_detail_id").toString())
					.toString();
			int sendQuantity = 0;
			if (sendQuantityValue != null) {
				sendQuantity = Integer.parseInt(sendQuantityValue);
			}
			// 更新出库通知单发货数量
			PageData pdNoticeDetailUpdateQuantity = new PageData();
			pdNoticeDetailUpdateQuantity.put("send_quantity", sendQuantity);
			pdNoticeDetailUpdateQuantity.put("updateby", userName);
			pdNoticeDetailUpdateQuantity.put("outbound_notice_detail_id", pdItem.get("outbound_notice_detail_id"));
			outboundnoticedetailService.updateSendQuantity(pdNoticeDetailUpdateQuantity);
			pdItem.put("send_quantity", sendQuantity);

			// 创建出库单明细
			int send_quantity = (int) pdItem.get("send_quantity");
			if (sendQuantity == 0)
				continue;
			PageData pdDetailItem = new PageData();
			pdDetailItem.put("outbound_code", outBoundCode);
			pdDetailItem.put("purchase_code", pdItem.get("purchase_code"));
			pdDetailItem.put("purchase_detail_id", pdItem.get("purchase_detail_id"));
			pdDetailItem.put("outbound_notice_code", pdItem.get("outbound_notice_code"));
			pdDetailItem.put("outbound_notice_detail_id", pdItem.get("outbound_notice_detail_id"));
			pdDetailItem.put("sku_id", pdItem.get("sku_id"));
			pdDetailItem.put("sku_name", pdItem.get("sku_name"));
			pdDetailItem.put("sku_encode", pdItem.get("sku_encode"));
			pdDetailItem.put("spec", pdItem.get("spec"));
			pdDetailItem.put("quantity", send_quantity);
			pdDetailItem.put("status", "");
			pdDetailItem.put("createby", userName);
			pdDetailItem.put("updateby", userName);
			int rst = (int) pgtOutboundDetailService.save(pdDetailItem);
			detailsRst += rst;
			// 创建出库批次
			{
				String detailId = pdItem.getString("outbound_notice_detail_id");
				JsonArray jsonArray = jsonObj.getAsJsonArray(detailId);
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.size(); i++) {
						JsonElement jsonElement = jsonArray.get(i);
						JsonObject jsonObj1 = jsonElement.getAsJsonObject();
						int asInt = jsonObj1.get("quantity").getAsInt();
						// if(asInt == 0)continue;
						PageData nstockbatch = new PageData();
						nstockbatch.put("outbound_code", outBoundCode);
						nstockbatch.put("outbound_detail_id", pdDetailItem.get("outbound_detail_id"));
						nstockbatch.put("sku_id", jsonObj1.get("sku_id").getAsString());
						nstockbatch.put("sku_name", jsonObj1.get("sku_name").getAsString());
						nstockbatch.put("sku_encode", jsonObj1.get("sku_encode").getAsString());
						nstockbatch.put("batch_code", jsonObj1.get("batch_code").getAsString());
						nstockbatch.put("quantity", asInt);
						nstockbatch.put("spec", jsonObj1.get("spec").getAsString());
						nstockbatch.put("unit_name", jsonObj1.get("unit_name").getAsString());
						outboundStockBatchService.save(nstockbatch);
					}
				}
			}
		}
		if (detailsRst <= 0) {
			throw new Exception("出库单明细创建失败，本次出库失败！");
		}
		// 更改发货通知单状态
		PageData pdStatus = new PageData();
		pdStatus.put("status", 10);
		pdStatus.put("updateby", userName);
		pdStatus.put("outbound_notice_id", notice_id);
		int rst = (int) outboundNoticeManager.updateStatus(pdStatus);
		if (rst <= 0) {
			throw new Exception("出库取消或已出库，本次出库失败！");
		}
		// 领料出库
		if (pdnotice.get("bill_type").toString().equals("0")) {
			// 领料，回写领料表数量
			PageData pd = new PageData();
			pd.put("outbound_code", outBoundCode);
			List<PageData> outboundStockBatchList = outboundStockBatchService.listByOutboundCode(pd);
			for (PageData pdItem : pdnoticeDetailList) {
				// String purchase_code= pdItem.getString("purchase_code");
				long detailId = Long.parseLong(pdItem.get("purchase_detail_id").toString());
				// 回写领料表数量
				PageData pdMaterial = new PageData();
				pdMaterial.put("material_id", detailId);
				pdMaterial.put("updateby", userName);
				pdMaterial.put("total_apply_quantity", pdItem.get("pre_send_quantity"));
				pdMaterial.put("total_quantity", pdItem.get("send_quantity"));
				purchaseMaterialManager.updateQuantity(pdMaterial);
				// 采购物料对象
				PageData findById = purchaseMaterialManager.findById(pdMaterial);
				if (outboundStockBatchList != null) {
					// 采购物料批次
					for (PageData pageData : outboundStockBatchList) {
						if (!pageData.get("sku_id").equals(pdItem.get("sku_id"))) {
							continue;
						}
						PageData npd = new PageData();
						npd.put("purchase_code", findById.get("purchase_code"));
						npd.put("purchase_detail_id", findById.get("purchase_detail_id"));
						npd.put("purchase_material_id", detailId);
						npd.put("store_sn", pdnotice.get("store_sn"));
						npd.put("store_name", pdnotice.get("store_name"));
						npd.put("sku_id", pageData.get("sku_id"));
						npd.put("unit", pageData.get("unit_name"));
						npd.put("sku_name", pageData.get("sku_name"));
						npd.put("sku_encode", pageData.get("sku_encode"));
						npd.put("batch_code", pageData.get("batch_code"));
						npd.put("quantity", pageData.get("quantity"));
						npd.put("spec", pageData.get("spec"));
						npd.put("status", 0);
						npd.put("createby", userName);
						npd.put("create_time", new Date());
						npd.put("updateby", userName);
						npd.put("update_time", new Date());
						purchaseMaterialManager.saveMaterialDetail(npd);
					}
				}
				// 更新库存
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					if (pdStore != null) {
						String materialStoreid = pdStore.getString("store_id");
						if (null != pdStore.getString("store_id") && !pdStore.getString("store_id").equals("")) {
							// 更改库存
							szyStockGoodsManager.quantityModify(quantity, Integer.parseInt(materialStoreid), skuId, 1);
							//
							for (PageData pdoutboundStockBatch : outboundStockBatchList) {
								if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
									continue;
								}
								String batchNo = pdoutboundStockBatch.getString("batch_code");
								double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
								int batchquantity = (int) d;
								// 扣减源仓批次库存
								stockBatchManager.insertBatchStock(Integer.parseInt(materialStoreid), batchNo, skuId,
										batchquantity, 1);
							}
						}
					}
				}
			}
		}

		if (pdnotice.get("bill_type").toString().equals("1")) {
			// 调拨，回写调拨明细表数量
			String allocation_code = pdnotice.getString("purchase_code");
			String inboundCode = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			// 查询对应的仓库信息
			/*
			 * PageData pdAllocStoreInfoParameter=new PageData();
			 * pdAllocStoreInfoParameter.put("store_sn", storeSn); PageData
			 * pdAllocStoreinfo=szyStoreManager.findByStoreSn(pdAllocStoreInfoParameter);
			 */

			Integer store_id = Integer.parseInt(pdStore.getString("store_id"));
			Integer logistic_storeId = 0;
			String logistic_storesn = "";
			String logistic_storeName = "";
			if (pdStore != null) {
				if (pdStore.getString("logistic_store_id") == null
						|| pdStore.getString("logistic_store_id").equals("")) {
					throw new Exception("查询不到物流仓信息");
				}
				logistic_storeId = Integer.parseInt(pdStore.getString("logistic_store_id"));
				PageData pdLogisticStoreInfoParameter = new PageData();
				pdLogisticStoreInfoParameter.put("store_id", logistic_storeId);
				PageData pdLogisticStoreInfo = szyStoreManager.findById(pdLogisticStoreInfoParameter);
				if (pdLogisticStoreInfo != null) {
					logistic_storesn = pdLogisticStoreInfo.getString("store_sn");// 物流仓库编码
					logistic_storeName = pdLogisticStoreInfo.getString("store_name");
				} else {
					throw new Exception("查询不到物流仓信息");
				}
			}

			for (PageData pdItem : pdnoticeDetailList) {
				long detailId = Long.parseLong(pdItem.get("purchase_detail_id").toString());
				// 更新调拨明细数量
				PageData pdAllItem = new PageData();
				pdAllItem.put("quantity", pdItem.get("send_quantity"));
				pdAllItem.put("updateby", userName);
				pdAllItem.put("allocation_detail_id", detailId);
				allocationDetailManager.updateQuantity(pdAllItem);
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					// 更改调拨出库库存
					szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
					// 增加物流仓库库存
					szyStockGoodsManager.quantityModify(quantity, logistic_storesn, skuId, 0);
				}
			}
			// 创建物流入库的入库单
			PageData pd = new PageData();
			pd.put("inbound_code", inboundCode);
			pd.put("inbound_type", InOutBoundType.ALLOCATION_INBOUND_LOGISTIC);
			pd.put("purchase_code", allocation_code);
			pd.put("purchase_detail_id", 0);
			pd.put("inbound_notice_code", "");
			pd.put("business_time", new Date());
			pd.put("store_sn", logistic_storesn);
			pd.put("store_name", logistic_storeName);
			pd.put("status", 0);
			pd.put("createby", userName);
			pd.put("create_time", new Date());
			pd.put("updateby", userName);
			pd.put("update_time", new Date());
			inboundManager.save(pd);
			// 批量插入物流入库单明细
			PageData pdInboundDetail = new PageData();
			pdInboundDetail.put("allocation_code", allocation_code);
			pdInboundDetail.put("inbound_code", inboundCode);
			pdInboundDetail.put("createby", userName);
			pdInboundDetail.put("updateby", userName);
			inboundDetailManager.saveBatchAllocLogistic(pdInboundDetail);

			PageData pdInboundCode = new PageData();
			pdInboundCode.put("inbound_code", inboundCode);
			List<PageData> pdInboundDetailList = inboundDetailManager.listByInboundCode(pdInboundCode);
			// 获取目标仓库信息
			PageData pdAllocationParameter = new PageData();
			pdAllocationParameter.put("allocation_code", allocation_code);
			PageData pdAllocation = allocationManager.findByCode(pdAllocationParameter);
			String toStoreSn = pdAllocation.getString("to_store_sn");
			String toStoreName = pdAllocation.getString("to_store_name");
			String noticeCode = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
			// 创建调拨目标仓库到货通知单
			PageData pdInboundNotice = new PageData();
			pdInboundNotice.put("inbound_notice_code", noticeCode);
			pdInboundNotice.put("bill_type", 3);
			pdInboundNotice.put("purchase_code", allocation_code);
			pdInboundNotice.put("purchase_detail_id", 0);
			pdInboundNotice.put("pre_arrival_time", pdAllocation.get("pre_arrival_time"));
			pdInboundNotice.put("arrival_time", pdAllocation.get("arrival_time"));
			pdInboundNotice.put("store_sn", toStoreSn);
			pdInboundNotice.put("store_name", toStoreName);
			pdInboundNotice.put("status", 0);
			pdInboundNotice.put("createby", userName);
			pdInboundNotice.put("updateby", userName);
			inboundNoticeService.save(pdInboundNotice);
			// 创建调拨目标仓库通知单明细
			PageData pdInboundNoticeDetail = new PageData();
			pdInboundNoticeDetail.put("inbound_code", noticeCode);
			pdInboundNoticeDetail.put("allocation_code", allocation_code);
			pdInboundNoticeDetail.put("createby", userName);
			pdInboundNoticeDetail.put("updateby", userName);
			inboundNoticeDetailManager.saveBatch(pdInboundNoticeDetail);
			// 获取生成的到货通知单明细
			PageData inboundNoticePd = new PageData();
			inboundNoticePd.put("inbound_notice_code", noticeCode);
			List<PageData> inboundNoticeList = inboundNoticeDetailManager.findlistByNoticeCode(inboundNoticePd);
			// 更新调拨单状态
			PageData pdAll = new PageData();
			pdAll.put("allocation_code", allocation_code);
			pdAll.put("updateby", userName);
			pdAll.put("status", 4);
			allocationManager.updateStatus(pdAll);
			// 插入到货通知单批次表
			PageData pdStockBatch = new PageData();
			pdStockBatch.put("outbound_code", outBoundCode);
			List<PageData> pdOutboundStockBatchList = outboundStockBatchService.listByOutboundCode(pdStockBatch);
			// 创建物流批次表
			if (pdInboundDetailList != null && pdInboundDetailList.size() > 0) {
				for (PageData pdDetail : pdInboundDetailList) {
					String inbound_detail_id = pdDetail.getString("inbound_detail_id");
					if (pdOutboundStockBatchList != null) {
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							int skuId = Integer.parseInt(pdoutboundStockBatch.getString("sku_id"));
							if (!String.valueOf(skuId).equals(pdDetail.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 物流仓库批次表
							PageData pdInboundLogisticStockBatch = new PageData();
							pdInboundLogisticStockBatch.put("inbound_code", inboundCode);
							pdInboundLogisticStockBatch.put("inbound_detail_id", inbound_detail_id);
							pdInboundLogisticStockBatch.put("sku_id", pdDetail.getString("sku_id"));
							pdInboundLogisticStockBatch.put("sku_name", pdDetail.getString("sku_name"));
							pdInboundLogisticStockBatch.put("sku_encode", pdDetail.getString("sku_encode"));
							pdInboundLogisticStockBatch.put("batch_code", batchNo);
							pdInboundLogisticStockBatch.put("quantity", batchquantity);
							pdInboundLogisticStockBatch.put("spec", pdDetail.getString("spec"));
							pdInboundLogisticStockBatch.put("unit_name", pdDetail.getString("unit_name"));
							inboundStockBatchManager.save(pdInboundLogisticStockBatch);
						}
					}
				}
			}
			if (inboundNoticeList != null) {
				for (PageData pdInboundNoticeItem : inboundNoticeList) {
					if (pdOutboundStockBatchList != null) {
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							int skuId = Integer.parseInt(pdoutboundStockBatch.getString("sku_id"));
							if (!String.valueOf(skuId).equals(pdInboundNoticeItem.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 扣减正品仓库批次
							stockBatchManager.insertBatchStock(store_id, batchNo, skuId, batchquantity, 1);
							// 增加物流仓批次库存
							stockBatchManager.insertBatchStock(logistic_storeId, batchNo, skuId, batchquantity, 0);
							// 插入入库通知单批次表
							PageData pdInboundNoticeStockBatch = new PageData();
							pdInboundNoticeStockBatch.put("inbound_notice_code", noticeCode);
							pdInboundNoticeStockBatch.put("notice_detail_id",
									pdInboundNoticeItem.get("notice_detail_id"));
							pdInboundNoticeStockBatch.put("sku_id", pdoutboundStockBatch.getString("sku_id"));
							pdInboundNoticeStockBatch.put("sku_name", pdoutboundStockBatch.getString("sku_name"));
							pdInboundNoticeStockBatch.put("sku_encode", pdoutboundStockBatch.getString("sku_encode"));
							pdInboundNoticeStockBatch.put("batch_code", pdoutboundStockBatch.getString("batch_code"));
							pdInboundNoticeStockBatch.put("quantity", pdoutboundStockBatch.get("quantity"));
							pdInboundNoticeStockBatch.put("spec", pdoutboundStockBatch.get("spec"));
							pdInboundNoticeStockBatch.put("unit_name", pdoutboundStockBatch.get("unit_name"));
							pdInboundNoticeStockBatch.put("status", '2');// 默认已质检lw20171010
							inboundnoticestockbatchService.save(pdInboundNoticeStockBatch);
						}
					}
				}
			}
		}
		if (pdnotice.get("bill_type").toString().equals("2")) {
			// 采购退货出库 更改主单状态
			PageData pdReturn = new PageData();
			pdReturn.put("purchase_return_code", pdnotice.get("purchase_code"));
			pdReturn.put("status", 9);
			pdReturn.put("updateby", userName);
			purchaseReturnManager.updateStatusByCode(pdReturn);
			PageData pdReturnResult = purchaseReturnManager.findByCode(pdReturn);
			// 采购单号
			String purchase_code = pdReturnResult.get("purchase_code").toString();
			PageData pdPurchaseDetail = new PageData();
			pdPurchaseDetail.put("purchase_code", purchase_code);
			// 查询采购单明细
			List<PageData> pdPurchaseDetailList = purchaseDetailManager.listByParam(pdPurchaseDetail);
			if (pdPurchaseDetailList != null) {
				for (PageData pdItem : pdnoticeDetailList) {
					// 发货数量
					int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
					if (quantity > 0) {
						for (PageData pdPurDetail : pdPurchaseDetailList) {
							if (pdItem.getString("sku_id").equals(pdPurDetail.getString("sku_id"))) {
								String detail_id = pdPurDetail.getString("detail_id");
								PageData pdDetailParameter = new PageData();
								pdDetailParameter.put("detail_id", detail_id);
								pdDetailParameter.put("bad_quantity", quantity);
								// 回写采购单退货数量
								purchaseDetailManager.updateBadQuantityForAdd(pdDetailParameter);
							}
						}
					}
				}
			}
			PageData pdStockBatch = new PageData();
			pdStockBatch.put("outbound_code", outBoundCode);
			List<PageData> pdOutboundStockBatchList = outboundStockBatchService.listByOutboundCode(pdStockBatch);
			// 回写退货单数量
			for (PageData pdItem : pdnoticeDetailList) {
				long detailId = Long.parseLong(pdItem.get("purchase_detail_id").toString());
				PageData pdReturnDetail = new PageData();
				pdReturnDetail.put("return_detail_id", detailId);
				pdReturnDetail.put("updateby", userName);
				pdReturnDetail.put("quantity", pdItem.get("send_quantity"));
				// 回写数量
				purchaseReturnDetailManager.editQuantity(pdReturnDetail);
				// 扣除库存
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					/*
					 * PageData pdStoreParameter=new PageData(); pdStoreParameter.put("store_sn",
					 * storeSn); PageData pdStore=szyStoreManager.findByStoreSn(pdStoreParameter);
					 */
					if (pdStore != null) {
						String returnStoreid = pdStore.getString("store_id");
						if (null != pdStore.getString("store_id") && !pdStore.getString("store_id").equals("")) {
							// 更改库存
							szyStockGoodsManager.quantityModify(quantity, Integer.parseInt(returnStoreid), skuId, 1);
							if (null != pdOutboundStockBatchList && pdOutboundStockBatchList.size() > 0) {
								for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
									if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
										continue;
									}
									String batchNo = pdoutboundStockBatch.getString("batch_code");
									double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
									int batchquantity = (int) d;
									// 扣减源仓批次库存
									stockBatchManager.insertBatchStock(Integer.parseInt(returnStoreid), batchNo, skuId,
											batchquantity, 1);
								}
							}
						}
					}
				}
			}
		}
		if (pdnotice.get("bill_type").toString().equals("3")) {
			// 报损
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String curData = df.format(new Date());
			/*
			 * PageData pdStoreParameter=new PageData(); pdStoreParameter.put("store_sn",
			 * pdnotice.get("store_sn")); PageData
			 * pdStore=szyStoreManager.findByStoreSn(pdStoreParameter);
			 */
			Integer storeId = Integer.parseInt(pdStore.getString("store_id"));
			String scrap_store_id = pdStore.getString("scrap_store_id");

			PageData pdBadStoreParameter = new PageData();
			pdBadStoreParameter.put("store_id", scrap_store_id);
			PageData pdBadStore = szyStoreManager.findById(pdBadStoreParameter);
			if (pdBadStore == null) {
				throw new Exception("查询不到报损仓信息");
			}
			// 报损单号
			String scrap_code = pdnotice.getString("purchase_code");
			String inboundCode = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			PageData pd = new PageData();
			pd.put("inbound_code", inboundCode);
			pd.put("inbound_type", InOutBoundType.SCRAP_INBOUND);
			pd.put("purchase_code", scrap_code);
			pd.put("purchase_detail_id", 0);
			pd.put("inbound_notice_code", "");
			pd.put("business_time", curData);
			pd.put("store_sn", pdBadStore.get("store_sn"));
			pd.put("store_name", pdBadStore.get("store_name"));
			pd.put("status", 0);
			pd.put("createby", userName);
			pd.put("create_time", curData);
			pd.put("updateby", userName);
			pd.put("update_time", curData);
			inboundManager.save(pd);
			// 批量插入入库单明细
			PageData pdInboundDetail = new PageData();
			pdInboundDetail.put("scrap_code", scrap_code);
			pdInboundDetail.put("inbound_code", inboundCode);
			pdInboundDetail.put("createby", userName);
			pdInboundDetail.put("updateby", userName);
			inboundDetailManager.saveBatch(pdInboundDetail);

			PageData pdInboundCode = new PageData();
			pdInboundCode.put("inbound_code", inboundCode);
			List<PageData> pdInboundDetailList = inboundDetailManager.listByInboundCode(pdInboundCode);

			PageData pdStockBatch = new PageData();
			pdStockBatch.put("outbound_code", outBoundCode);
			List<PageData> pdOutboundStockBatchList = outboundStockBatchService.listByOutboundCode(pdStockBatch);
			// 创建物流批次表
			if (pdInboundDetailList != null && pdInboundDetailList.size() > 0) {
				for (PageData pdDetail : pdInboundDetailList) {
					String inbound_detail_id = pdDetail.getString("inbound_detail_id");
					if (pdOutboundStockBatchList != null) {
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							int skuId = Integer.parseInt(pdoutboundStockBatch.getString("sku_id"));
							if (!String.valueOf(skuId).equals(pdDetail.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 物流仓库批次表
							PageData pdInboundLogisticStockBatch = new PageData();
							pdInboundLogisticStockBatch.put("inbound_code", inboundCode);
							pdInboundLogisticStockBatch.put("inbound_detail_id", inbound_detail_id);
							pdInboundLogisticStockBatch.put("sku_id", pdDetail.getString("sku_id"));
							pdInboundLogisticStockBatch.put("sku_name", pdDetail.getString("sku_name"));
							pdInboundLogisticStockBatch.put("sku_encode", pdDetail.getString("sku_encode"));
							pdInboundLogisticStockBatch.put("batch_code", batchNo);
							pdInboundLogisticStockBatch.put("quantity", batchquantity);
							pdInboundLogisticStockBatch.put("spec", pdDetail.getString("spec"));
							pdInboundLogisticStockBatch.put("unit_name", pdDetail.getString("unit_name"));
							inboundStockBatchManager.save(pdInboundLogisticStockBatch);
						}
					}
				}
			}
			// 扣减批次库存
			for (PageData pdItem : pdnoticeDetailList) {
				// 增加不良品库存
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					if (null != pdStore.getString("bad_store_id") && !pdStore.getString("bad_store_id").equals("")) {
						// 扣减源仓库库存
						szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
						// 增加报损仓库存
						szyStockGoodsManager.quantityModify(quantity, Integer.parseInt(scrap_store_id), skuId, 0);
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 扣减源仓批次库存
							stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity, 1);
							// 增加报损仓的库存
							stockBatchManager.insertBatchStock(Integer.parseInt(scrap_store_id), batchNo, skuId,
									batchquantity, 0);
						}
					} else {
						throw new Exception("查询不到报损仓信息");
					}
				}
			}
		}
		if (pdnotice.get("bill_type").toString().equals("4")) {
			// 内部销售 更改库存
			/*
			 * PageData pdStoreInfoParameter=new PageData();
			 * pdStoreInfoParameter.put("store_sn", storeSn); PageData
			 * pdStoreinfo=szyStoreManager.findByStoreSn(pdStoreInfoParameter);
			 */
			Integer storeId = Integer.parseInt(pdStore.getString("store_id"));
			for (PageData pdItem : pdnoticeDetailList) {
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					// 更改库存
					szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
					// 更改批次库存
					PageData pdStockBatch = new PageData();
					pdStockBatch.put("outbound_code", outBoundCode);
					List<PageData> pdOutboundStockBatchList = outboundStockBatchService
							.listByOutboundCode(pdStockBatch);
					for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
						if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
							continue;
						}
						String batchNo = pdoutboundStockBatch.getString("batch_code");
						double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
						int batchquantity = (int) d;
						// 扣减批次库存
						stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity, 1);
					}
				}
			}
			
			//回写内销状态
			PageData pd = new PageData();
			pd.put("direct_sales_code", pdnotice.get("purchase_code"));
			pd.put("status", 9);
			pd.put("updateby", userName);
			pd.put("update_time", new Date());
			directsalesService.outboundRewrite(pd);
		}
		if (pdnotice.get("bill_type").toString().equals("6")) {
			// 其他出库
			// 回写其它出库单状态-完成
			String other_outstorage_code = pdnotice.getString("purchase_code");
			PageData pd = new PageData();
			pd.put("other_outstorage_code", other_outstorage_code);
			pd.put("status", 9);
			pd.put("updateby", userName);
			pd.put("update_time", new Date());
			otheroutstorageService.updateStatus(pd);
			/*
			 * PageData pdStoreInfoParametDataer=new PageData();
			 * pdStoreInfoParameter.put("store_sn", storeSn); PageData
			 * pdStoreinfo=szyStoreManager.findByStoreSn(pdStoreInfoParameter);
			 */
			Integer storeId = Integer.parseInt(pdStore.getString("store_id"));
			for (PageData pdItem : pdnoticeDetailList) {
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					// 更改库存
					szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
					PageData pdStockBatch = new PageData();
					pdStockBatch.put("outbound_code", outBoundCode);
					List<PageData> pdOutboundStockBatchList = outboundStockBatchService
							.listByOutboundCode(pdStockBatch);
					for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
						if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
							continue;
						}
						String batchNo = pdoutboundStockBatch.getString("batch_code");
						double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
						int batchquantity = (int) d;
						// 扣减批次库存
						stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity, 1);
					}
				}
			}
		}
		if (pdnotice.get("bill_type").toString().equals("5")) {
			// 销售出库，更改批次库存，增加物理仓库库存
			String logistic_store_id = "";
			String logisticStoreSn = "";
			String logisticStoreName = "";// 物流仓库
			PageData pdStoreParameter1 = new PageData();
			pdStoreParameter1.put("store_type_id", 7);
			PageData pdStore1 = szyStoreManager.findByType(pdStoreParameter1);
			Integer storeId = Integer.parseInt(pdStore.getString("store_id"));
			if (pdStore1 != null) {
				// 查询物流仓信息
				logistic_store_id = pdStore1.getString("store_id");
				logisticStoreSn = pdStore1.getString("store_sn");
				logisticStoreName = pdStore1.getString("store_name");
			} else {
				throw new Exception("查询不到俱乐部仓信息");
			}
			// 回写订单批次表和订单批次明细表
			String orderSn = pdnotice.getString("purchase_code");
			PageData pdOrderParameter = new PageData();
			pdOrderParameter.put("order_sn", orderSn);
			PageData pdOrder = orderService.findByOrderSn(pdOrderParameter);
			if (pdOrder == null) {
				// 查询明细
				throw new Exception("查询不到订单信息");
			}
			// 订单更新已出库
			erporderService.updateErpOrder(orderSn, OrderShippingStatusEnum.TYPE1_OUTBOUND.getCode());

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String curData = df.format(new Date());
			// 创建销售物流入库单
			String inboundCode = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			PageData pd = new PageData();
			pd.put("inbound_code", inboundCode);
			pd.put("inbound_type", InOutBoundType.ORDER_PURCHASE_INBOUND);
			pd.put("purchase_code", orderSn);
			pd.put("purchase_detail_id", 0);
			pd.put("inbound_notice_code", "");
			pd.put("business_time", curData);
			pd.put("store_sn", logisticStoreSn);
			pd.put("store_name", logisticStoreName);
			pd.put("status", 0);
			pd.put("createby", userName);
			pd.put("create_time", curData);
			pd.put("updateby", userName);
			pd.put("update_time", curData);
			inboundManager.save(pd);
			// 创建入库明细
			for (PageData pdItem : pdnoticeDetailList) {
				// 创建物流仓库的入库明细
				PageData pdInboudDetail = new PageData();
				pdInboudDetail.put("inbound_code", inboundCode);
				pdInboudDetail.put("purchase_code", orderSn);
				pdInboudDetail.put("purchase_detail_id", 0);
				pdInboudDetail.put("inbound_notice_code", "");
				pdInboudDetail.put("notice_detail_id", 0);
				pdInboudDetail.put("sku_id", pdItem.get("sku_id").toString());
				pdInboudDetail.put("sku_name", pdItem.get("sku_name").toString());
				pdInboudDetail.put("sku_encode", pdItem.getString("sku_encode"));
				pdInboudDetail.put("spec", pdItem.getString("spec"));
				pdInboudDetail.put("quantity", pdItem.get("send_quantity"));
				pdInboudDetail.put("status", 0);
				pdInboudDetail.put("createby", userName);
				pdInboudDetail.put("create_time", curData);
				pdInboudDetail.put("updateby", userName);
				pdInboudDetail.put("update_time", curData);
				inboundDetailManager.save(pdInboudDetail);
				int skuId = Integer.parseInt(pdItem.get("sku_id").toString());
				int quantity = Integer.parseInt(pdItem.get("send_quantity").toString());
				if (quantity > 0) {
					// 更改库存
					szyStockGoodsManager.quantityModify(quantity, storeSn, skuId, 1);
					// 增加物流仓库库存
					szyStockGoodsManager.quantityModify(quantity, logisticStoreSn, skuId, 0);
					// 扣除批次库存
					PageData pdStockBatch = new PageData();
					pdStockBatch.put("outbound_code", outBoundCode);
					List<PageData> pdOutboundStockBatchList = outboundStockBatchService
							.listByOutboundCode(pdStockBatch);
					if (pdOutboundStockBatchList != null) {
						for (PageData pdoutboundStockBatch : pdOutboundStockBatchList) {
							if (!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
								continue;
							}
							String batchNo = pdoutboundStockBatch.getString("batch_code");
							double d = Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
							int batchquantity = (int) d;
							// 扣减批次库存
							stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity, 1);
							// 增加物流仓批次库存
							stockBatchManager.insertBatchStock(Integer.parseInt(logistic_store_id), batchNo, skuId,
									batchquantity, 0);
							// 插入订单批次库存表
							PageData pdOrderStockBatch = new PageData();
							pdOrderStockBatch.put("order_sn", orderSn);
							pdOrderStockBatch.put("record_id", pdItem.getString("purchase_detail_id"));
							pdOrderStockBatch.put("sku_id", pdItem.getString("sku_id"));
							pdOrderStockBatch.put("sku_name", pdItem.getString("sku_name"));
							pdOrderStockBatch.put("sku_encode", pdItem.getString("sku_encode"));
							pdOrderStockBatch.put("batch_code", batchNo);
							pdOrderStockBatch.put("quantity", batchquantity);
							pdOrderStockBatch.put("spec", pdItem.getString("spec"));
							pdOrderStockBatch.put("unit_name", pdItem.getString("unit_name"));
							orderStockBatchManager.save(pdOrderStockBatch);
							// 插入物流入库批次
							PageData pdInboundLogisticStockBatch = new PageData();
							pdInboundLogisticStockBatch.put("inbound_code", inboundCode);
							pdInboundLogisticStockBatch.put("inbound_detail_id",
									pdInboudDetail.get("inbound_detail_id"));
							pdInboundLogisticStockBatch.put("sku_id", pdItem.getString("sku_id"));
							pdInboundLogisticStockBatch.put("sku_name", pdItem.getString("sku_name"));
							pdInboundLogisticStockBatch.put("sku_encode", pdItem.getString("sku_encode"));
							pdInboundLogisticStockBatch.put("batch_code", batchNo);
							pdInboundLogisticStockBatch.put("quantity", batchquantity);
							pdInboundLogisticStockBatch.put("spec", pdItem.getString("spec"));
							pdInboundLogisticStockBatch.put("unit_name", pdItem.getString("unit_name"));
							inboundStockBatchManager.save(pdInboundLogisticStockBatch);
							String detailId = pdItem.getString("outbound_notice_detail_id");
							JsonArray jsonArray = jsonObj.getAsJsonArray(detailId);
							if (jsonArray != null) {
								for (int i = 0; i < jsonArray.size(); i++) {
									JsonElement jsonElement = jsonArray.get(i);
									JsonObject jsonObj1 = jsonElement.getAsJsonObject();
									String barcode_ = jsonObj1.get("barcode_").getAsString();
									if (!String.valueOf(skuId).equals(jsonObj1.get("sku_id").getAsString())) {
										continue;
									}
									if (barcode_ != null && barcode_ != "") {
										String[] barCodeArr = barcode_.split(",");
										if (barCodeArr != null && barCodeArr.length > 0) {
											// 插入订单出库批次明细表
											List<PageData> pdOrderStockBatchDetailList = new ArrayList<PageData>();
											for (int m = 0; m < barCodeArr.length; m++) {
												PageData pdOrderStockBatchDetail = new PageData();
												pdOrderStockBatchDetail.put("order_stock_batch_id",
														pdOrderStockBatch.get("order_stock_batch_id"));
												pdOrderStockBatchDetail.put("order_sn", orderSn);
												pdOrderStockBatchDetail.put("record_id",
														pdItem.getString("purchase_detail_id"));
												pdOrderStockBatchDetail.put("sku_id", pdItem.getString("sku_id"));
												pdOrderStockBatchDetail.put("sku_name", pdItem.getString("sku_name"));
												pdOrderStockBatchDetail.put("sku_encode",
														pdItem.getString("sku_encode"));
												pdOrderStockBatchDetail.put("batch_code", batchNo);
												pdOrderStockBatchDetail.put("spec", pdItem.getString("spec"));
												pdOrderStockBatchDetail.put("unit_name", pdItem.getString("unit_name"));
												pdOrderStockBatchDetail.put("qrcode", barCodeArr[m]);
												pdOrderStockBatchDetailList.add(pdOrderStockBatchDetail);
											}
											orderStockBatchDetailManager.saveBatch(pdOrderStockBatchDetailList);
										}
									}
								}
							}
						}
					}
				}
			}
			// 出库
			{
				String outBoundCode1 = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
				PageData pdOutBound1 = new PageData();
				pdOutBound1.put("type", InOutBoundType.ORDER_GOOD_OUTBOUND);
				pdOutBound1.put("out_bound_code", outBoundCode1);
				pdOutBound1.put("purchase_code", orderSn);
				pdOutBound1.put("purchase_detail_id", 0);
				pdOutBound1.put("outbound_notice_code", "");
				pdOutBound1.put("outbound_notice_detail_id", 0);
				pdOutBound1.put("store_sn", logisticStoreSn);
				pdOutBound1.put("store_name", logisticStoreName);
				pdOutBound1.put("createby", userName);
				pdOutBound1.put("updateby", userName);
				pgtoutboundService.save(pdOutBound1);
				for (PageData pdItem : pdnoticeDetailList) {
					// 创建出库单明细
					int send_quantity = (int) pdItem.get("send_quantity");
					if (send_quantity == 0)
						continue;
					PageData pdDetailItem = new PageData();
					pdDetailItem.put("outbound_code", outBoundCode1);
					pdDetailItem.put("purchase_code", pdItem.get("purchase_code"));
					pdDetailItem.put("purchase_detail_id", pdItem.get("purchase_detail_id"));
					pdDetailItem.put("outbound_notice_code", "");
					pdDetailItem.put("outbound_notice_detail_id", 0);
					pdDetailItem.put("sku_id", pdItem.get("sku_id"));
					pdDetailItem.put("sku_name", pdItem.get("sku_name"));
					pdDetailItem.put("sku_encode", pdItem.get("sku_encode"));
					pdDetailItem.put("spec", pdItem.get("spec"));
					pdDetailItem.put("quantity", send_quantity);
					pdDetailItem.put("status", "");
					pdDetailItem.put("createby", userName);
					pdDetailItem.put("updateby", userName);
					int rst1 = (int) pgtOutboundDetailService.save(pdDetailItem);
					// detailsRst+=rst1;
					// 创建出库批次
					{
						String detailId = pdItem.getString("outbound_notice_detail_id");
						JsonArray jsonArray = jsonObj.getAsJsonArray(detailId);
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.size(); i++) {
								JsonElement jsonElement = jsonArray.get(i);
								JsonObject jsonObj1 = jsonElement.getAsJsonObject();
								int asInt = jsonObj1.get("quantity").getAsInt();
								// if(asInt == 0)continue;
								PageData nstockbatch = new PageData();
								nstockbatch.put("outbound_code", outBoundCode1);
								nstockbatch.put("outbound_detail_id", pdDetailItem.get("outbound_detail_id"));
								nstockbatch.put("sku_id", jsonObj1.get("sku_id").getAsString());
								nstockbatch.put("sku_name", jsonObj1.get("sku_name").getAsString());
								nstockbatch.put("sku_encode", jsonObj1.get("sku_encode").getAsString());
								nstockbatch.put("batch_code", jsonObj1.get("batch_code").getAsString());
								nstockbatch.put("quantity", asInt);
								nstockbatch.put("spec", jsonObj1.get("spec").getAsString());
								nstockbatch.put("unit_name", jsonObj1.get("unit_name").getAsString());
								outboundStockBatchService.save(nstockbatch);
							}
						}
					}
				}
			}
		}
		/*
		 * if(pdnotice.get("bill_type").toString().equals("5")) { //销售出库，更改批次库存，增加物理仓库库存
		 * String logistic_store_id=""; String logisticStoreSn=""; String
		 * logisticStoreName="";//物流仓库 PageData pdStorePara=new PageData();
		 * pdStorePara.put("store_sn",storeSn); PageData
		 * pdGoodStore=szyStoreManager.findByStoreSn(pdStorePara); int
		 * storeId=Integer.parseInt(pdStore.getString("store_id")); if(pdStore!=null) {
		 * storeId=Integer.parseInt(pdStore.getString("store_id")); //查询物流仓信息
		 * logistic_store_id=pdStore.getString("logistic_store_id"); PageData
		 * pdLogisticStoreParameter=new PageData();
		 * pdLogisticStoreParameter.put("store_id", logistic_store_id); PageData
		 * pdLogisticStore=szyStoreManager.findById(pdLogisticStoreParameter);
		 * if(pdLogisticStore!=null) {
		 * logisticStoreSn=pdLogisticStore.getString("store_sn");
		 * logisticStoreName=pdLogisticStore.getString("store_name"); } else { throw new
		 * Exception("查询不到物流仓信息"); } } //回写订单批次表和订单批次明细表 String
		 * orderSn=pdnotice.getString("purchase_code"); PageData pdOrderParameter=new
		 * PageData(); pdOrderParameter.put("order_sn",orderSn); PageData
		 * pdOrder=orderService.findByOrderSn(pdOrderParameter); if(pdOrder==null) {
		 * //查询明细 throw new Exception("查询不到订单信息"); } //订单更新已出库
		 * erporderService.updateErpOrder(orderSn,
		 * OrderShippingStatusEnum.TYPE1_OUTBOUND.getCode());
		 * 
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		 * String curData=df.format(new Date()); //创建销售物流入库单 String
		 * inboundCode=getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
		 * PageData pd=new PageData(); pd.put("inbound_code",inboundCode);
		 * pd.put("inbound_type",InOutBoundType.ORDER_INBOUND_LOGISTIC);
		 * pd.put("purchase_code",orderSn); pd.put("purchase_detail_id",0);
		 * pd.put("inbound_notice_code",""); pd.put("business_time",curData);
		 * pd.put("store_sn",logisticStoreSn); pd.put("store_name",logisticStoreName);
		 * pd.put("status",0); pd.put("createby",userName);
		 * pd.put("create_time",curData); pd.put("updateby",userName);
		 * pd.put("update_time",curData); inboundManager.save(pd); //创建入库明细 for(PageData
		 * pdItem:pdnoticeDetailList) { //创建物流仓库的入库明细 PageData pdInboudDetail=new
		 * PageData(); pdInboudDetail.put("inbound_code",inboundCode);
		 * pdInboudDetail.put("purchase_code",orderSn);
		 * pdInboudDetail.put("purchase_detail_id",0);
		 * pdInboudDetail.put("inbound_notice_code","");
		 * pdInboudDetail.put("notice_detail_id",0);
		 * pdInboudDetail.put("sku_id",pdItem.get("sku_id").toString());
		 * pdInboudDetail.put("sku_name",pdItem.get("sku_name").toString());
		 * pdInboudDetail.put("sku_encode",pdItem.getString("sku_encode"));
		 * pdInboudDetail.put("spec",pdItem.getString("spec"));
		 * pdInboudDetail.put("quantity",pdItem.get("send_quantity"));
		 * pdInboudDetail.put("status",0); pdInboudDetail.put("createby",userName);
		 * pdInboudDetail.put("create_time",curData);
		 * pdInboudDetail.put("updateby",userName);
		 * pdInboudDetail.put("update_time",curData);
		 * inboundDetailManager.save(pdInboudDetail); int
		 * skuId=Integer.parseInt(pdItem.get("sku_id").toString()); int
		 * quantity=Integer.parseInt(pdItem.get("send_quantity").toString());
		 * if(quantity>0) { //更改库存 szyStockGoodsManager.quantityModify(quantity,
		 * storeSn, skuId, 1); //增加物流仓库库存 szyStockGoodsManager.quantityModify(quantity,
		 * logisticStoreSn, skuId, 0); //扣除批次库存 PageData pdStockBatch=new PageData();
		 * pdStockBatch.put("outbound_code",outBoundCode); List<PageData>
		 * pdOutboundStockBatchList=outboundStockBatchService.listByOutboundCode(
		 * pdStockBatch); if(pdOutboundStockBatchList!=null) { for(PageData
		 * pdoutboundStockBatch:pdOutboundStockBatchList) {
		 * if(!String.valueOf(skuId).equals(pdoutboundStockBatch.getString("sku_id"))) {
		 * continue; } String batchNo=pdoutboundStockBatch.getString("batch_code");
		 * double d=Double.parseDouble(pdoutboundStockBatch.get("quantity").toString());
		 * int batchquantity=(int)d; //扣减批次库存
		 * stockBatchManager.insertBatchStock(storeId, batchNo, skuId, batchquantity,
		 * 1); //增加物流仓批次库存
		 * stockBatchManager.insertBatchStock(Integer.parseInt(logistic_store_id),
		 * batchNo, skuId, batchquantity, 0); // 插入订单批次库存表 PageData
		 * pdOrderStockBatch=new PageData(); pdOrderStockBatch.put("order_sn",orderSn);
		 * pdOrderStockBatch.put("record_id",pdItem.getString("purchase_detail_id"));
		 * pdOrderStockBatch.put("sku_id", pdItem.getString("sku_id"));
		 * pdOrderStockBatch.put("sku_name", pdItem.getString("sku_name"));
		 * pdOrderStockBatch.put("sku_encode",pdItem.getString("sku_encode"));
		 * pdOrderStockBatch.put("batch_code",batchNo);
		 * pdOrderStockBatch.put("quantity", batchquantity);
		 * pdOrderStockBatch.put("spec", pdItem.getString("spec"));
		 * pdOrderStockBatch.put("unit_name",pdItem.getString("unit_name"));
		 * orderStockBatchManager.save(pdOrderStockBatch); //插入物流入库批次 PageData
		 * pdInboundLogisticStockBatch=new PageData();
		 * pdInboundLogisticStockBatch.put("inbound_code",inboundCode);
		 * pdInboundLogisticStockBatch.put("inbound_detail_id",
		 * pdInboudDetail.get("inbound_detail_id"));
		 * pdInboundLogisticStockBatch.put("sku_id", pdItem.getString("sku_id"));
		 * pdInboundLogisticStockBatch.put("sku_name", pdItem.getString("sku_name"));
		 * pdInboundLogisticStockBatch.put("sku_encode",
		 * pdItem.getString("sku_encode"));
		 * pdInboundLogisticStockBatch.put("batch_code", batchNo);
		 * pdInboundLogisticStockBatch.put("quantity",batchquantity);
		 * pdInboundLogisticStockBatch.put("spec", pdItem.getString("spec"));
		 * pdInboundLogisticStockBatch.put("unit_name", pdItem.getString("unit_name"));
		 * inboundStockBatchManager.save(pdInboundLogisticStockBatch); String detailId =
		 * pdItem.getString("outbound_notice_detail_id"); JsonArray jsonArray =
		 * jsonObj.getAsJsonArray(detailId); if(jsonArray != null) { for(int i=0;
		 * i<jsonArray.size(); i++ ) { JsonElement jsonElement = jsonArray.get(i);
		 * JsonObject jsonObj1 = jsonElement.getAsJsonObject(); String
		 * barcode_=jsonObj1.get("barcode_").getAsString();
		 * if(!String.valueOf(skuId).equals(jsonObj1.get("sku_id").getAsString())) {
		 * continue; } if(barcode_!=null&&barcode_!="") { String[]
		 * barCodeArr=barcode_.split(","); if(barCodeArr!=null&&barCodeArr.length>0) {
		 * //插入订单出库批次明细表 List<PageData> pdOrderStockBatchDetailList=new
		 * ArrayList<PageData>(); for(int m=0;m<barCodeArr.length;m++) { PageData
		 * pdOrderStockBatchDetail=new PageData();
		 * pdOrderStockBatchDetail.put("order_stock_batch_id",pdOrderStockBatch.get(
		 * "order_stock_batch_id")); pdOrderStockBatchDetail.put("order_sn",orderSn);
		 * pdOrderStockBatchDetail.put("record_id",pdItem.getString("purchase_detail_id"
		 * )); pdOrderStockBatchDetail.put("sku_id",pdItem.getString("sku_id"));
		 * pdOrderStockBatchDetail.put("sku_name", pdItem.getString("sku_name"));
		 * pdOrderStockBatchDetail.put("sku_encode", pdItem.getString("sku_encode"));
		 * pdOrderStockBatchDetail.put("batch_code",batchNo);
		 * pdOrderStockBatchDetail.put("spec",pdItem.getString("spec"));
		 * pdOrderStockBatchDetail.put("unit_name",pdItem.getString("unit_name"));
		 * pdOrderStockBatchDetail.put("qrcode",barCodeArr[m]);
		 * pdOrderStockBatchDetailList.add(pdOrderStockBatchDetail); }
		 * orderStockBatchDetailManager.saveBatch(pdOrderStockBatchDetailList); } } } }
		 * } } } } }
		 */
		// 报损出库，修改报损单状态为9：已完成
		if (pdnotice.get("bill_type").toString().equals("3")) {
			PageData pd = new PageData();
			pd.put("scrap_code", pdnotice.get("purchase_code"));
			pd.put("status", 9);
			pd.put("updateby", userName);
			pd.put("updatetime", new Date());
			scrapService.editStatus(pd);
		}
		return "";
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 *//*
		 * @SuppressWarnings("unchecked") public List<PageData> listAll(PageData
		 * pd)throws Exception{ return
		 * (List<PageData>)dao.findForList("PgtOutboundMapper.listAll", pd); }
		 */

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllTimeSpan10(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("PgtOutboundMapper.listAllTimeSpan10", pd);
		return list;
	}

	@Override
	public List<PageData> listOutboundDetail(PageData pd) throws Exception {
		@SuppressWarnings("unchecked")
		List<PageData> list = (List<PageData>) dao.findForList("PgtOutboundMapper.listOutboundDetail", pd);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAllOrder(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("PgtOutboundMapper.listAllOrder", pd);
		return list;
	}

	@Override
	public void updateSynStatus(PageData pd) throws Exception {
		dao.update("PgtOutboundMapper.updateSynStatus", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listOutbound(String[] noticeIdArr) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> list = (List<PageData>) dao.findForList("PgtOutboundMapper.listOutbound", noticeIdArr);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryByPurchase(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PgtOutboundMapper.queryByPurchaseCode", pd);
	}
}
