package com.fh.service.erp.hander.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.common.InOutBoundType;
import com.fh.entity.system.User;
import com.fh.service.batchgenerate.BatchGenerateManager;
import com.fh.service.dst.allocation.AllocationManager;
import com.fh.service.dst.inboundcheckdetail.InboundCheckDetailManager;
import com.fh.service.dst.inboundprenotice.InboundPreNoticeManager;
import com.fh.service.dst.otherinstorage.OtherInstorageManager;
import com.fh.service.dst.outbound.PgtOutboundManager;
import com.fh.service.dst.outbounddetail.impl.PgtOutboundDetailService;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.outboundstockbatch.OutboundStockBatchManager;
import com.fh.service.dst.pgtinboundprenoticedetail.PgtInboundPreNoticeDetailManager;
import com.fh.service.dst.purchase.PurchaseManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.purchasematerial.PurchaseMaterialManager;
import com.fh.service.dst.qualitycheck.QualityCheckManager;
import com.fh.service.dst.qualitycheckfile.QualityCheckFileManager;
import com.fh.service.dst.szystockgoods.SzyStockGoodsManager;
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.directsales.DirectSalesManager;
import com.fh.service.erp.directsalesdetail.DirectSalesDetailManager;
import com.fh.service.erp.hander.SaveInboundManager;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.service.erp.inbounddetail.InboundDetailManager;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.service.inboundstockbatch.InboundStockBatchManager;
import com.fh.service.stockbatch.StockBatchManager;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 说明： 入库单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("saveInboundService")
public class SaveInboundService implements SaveInboundManager{
	
	@Resource(name="allocationService")
	private AllocationManager allocationService;
	
	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	
	@Resource(name = "inboundnoticeService")
	private InboundNoticeManager inboundnoticeService;

	@Resource(name = "inboundService")
	private InboundManager inboundManagerService;

	@Resource(name = "inbounddetailService")
	private InboundDetailManager inbounddetailService;

	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;

	@Resource(name = "szystockgoodsService")
	private SzyStockGoodsManager szystockgoodsService;

	@Resource(name = "qualitycheckService")
	private QualityCheckManager qualitycheckService;

	@Resource(name = "qualitycheckfileService")
	private QualityCheckFileManager qualitycheckfileService;

	@Resource(name = "pgtoutboundService")
	private PgtOutboundManager pgtoutboundService;

	@Resource(name = "pgtoutbounddetailService")
	private PgtOutboundDetailService pgtOutboundDetailService;

	@Resource(name = "purchasedetailService")
	private PurchaseDetailManager purchasedetailService;

	@Resource(name = "purchasematerialService")
	private PurchaseMaterialManager purchasematerialService;

	@Resource(name="directsalesService")
	private DirectSalesManager directsalesService;
	
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	
	@Resource(name="szystoreService")
	private SzyStoreManager szystoreService;
	
	@Resource(name="stockbatchService")
	private StockBatchManager stockBatchManager;
	
	@Resource(name="directsalesdetailService")
	private DirectSalesDetailManager directsalesdetailService;
	
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	
	@Resource(name="inboundstockbatchService")
	private InboundStockBatchManager inboundstockbatchService;
	
	@Resource(name="batchgenerateService")
	private BatchGenerateManager batchgenerateService;
	
	@Resource(name="outboundstockbatchService")
	private OutboundStockBatchManager outboundstockbatchService;
	
	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundCheckDetailManager;
	
	@Resource(name="otherinstorageService")
	private OtherInstorageManager otherinstorageService;

	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundcheckdetailService;
	
	@Resource(name="pgtinboundprenoticedetailService")
	private PgtInboundPreNoticeDetailManager pgtinboundprenoticedetailService;
	
	@Resource(name="inboundprenoticeService")
	private InboundPreNoticeManager inboundprenoticeService;
	
	@Resource(name="purchaseService")
	private PurchaseManager purchaseService;
	
	private static final DateFormat dayformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private PageData saveOutboundDetail(User user, String inbound_notice_code, String notice_detail_id,
			String purchase_code, String purchase_detail_id, String sku_id, String sku_name, 
			String sku_encode,String spec,String inbound_quantity,
			String outbound_code_sum) throws Exception {
		PageData outboundDetail = new PageData();
		outboundDetail.put("outbound_code", outbound_code_sum);
		outboundDetail.put("purchase_code", purchase_code);
		outboundDetail.put("purchase_detail_id", purchase_detail_id);
		outboundDetail.put("outbound_notice_code", inbound_notice_code);
		outboundDetail.put("outbound_notice_detail_id", notice_detail_id);
		outboundDetail.put("sku_id", sku_id);
		outboundDetail.put("sku_name", sku_name);

		outboundDetail.put("sku_encode", sku_encode);
		outboundDetail.put("spec", spec);
		outboundDetail.put("quantity", inbound_quantity);
		outboundDetail.put("status", "0");// 预留
		outboundDetail.put("updateby", user.getUSER_ID());
		outboundDetail.put("update_time", new Date());
		outboundDetail.put("createby", user.getUSER_ID());
		outboundDetail.put("create_time", new Date());
		pgtOutboundDetailService.save(outboundDetail);
		return outboundDetail;
	}

	private void saveOutbound(User user, String inbound_notice_code, String bill_type, String purchase_code,
			String purchase_detail_id, String total_store_sn, String total_store_name, String outbound_code_sum)
			throws Exception {
		PageData outbound = new PageData();
		outbound.put("out_bound_code", outbound_code_sum);
		outbound.put("type", bill_type);// 1、采购出库单、2、生产出库单、3、调拨出库单、4、客户退货出库单,
		outbound.put("purchase_code", purchase_code);
		outbound.put("purchase_detail_id", purchase_detail_id);
		outbound.put("outbound_notice_code", inbound_notice_code);
		outbound.put("outbound_notice_detail_id", '0');
		outbound.put("business_time", new Date());
		outbound.put("store_sn", total_store_sn);
		outbound.put("store_name", total_store_name);
		outbound.put("status", "0");
		outbound.put("createby", user.getUSER_ID());
		outbound.put("create_time", new Date());
		outbound.put("updateby", user.getUSER_ID());
		outbound.put("update_time", new Date());
		pgtoutboundService.save(outbound);
	}

	private PageData saveInboundDetail(User user, String inbound_notice_code, String notice_detail_id, String purchase_code,
			String purchase_detail_id, String sku_id, String sku_name, String sku_encode, String spec, String inbound_quantity, String inbound_code_sum)
			throws Exception {
		PageData inboundDetail = new PageData();
		inboundDetail.put("inbound_code", inbound_code_sum);
		inboundDetail.put("purchase_code", purchase_code);
		inboundDetail.put("purchase_detail_id", purchase_detail_id);
		inboundDetail.put("inbound_notice_code", inbound_notice_code);
		inboundDetail.put("notice_detail_id", notice_detail_id);
		inboundDetail.put("sku_id", sku_id);
		inboundDetail.put("sku_name", sku_name);
		inboundDetail.put("sku_encode", sku_encode);
		inboundDetail.put("spec", spec);
		inboundDetail.put("quantity", inbound_quantity);
		inboundDetail.put("status", "0");// 预留
		inboundDetail.put("updateby", user.getUSER_ID());
		inboundDetail.put("update_time", new Date());
		inboundDetail.put("createby", user.getUSER_ID());
		inboundDetail.put("create_time", new Date());
		inbounddetailService.save(inboundDetail);
		return inboundDetail;
	}

	private void inboundBatch(String notice_detail_id, String inbound_quantity, PageData inboundDetail, int store_id)
			throws Exception {
		Double sum = Double.valueOf(inbound_quantity);
		PageData query = new PageData();
		query.put("notice_detail_id", notice_detail_id);
		List<PageData> results = inboundnoticestockbatchService.listAll(query);
		if(results!=null && results.size()>0) {
			for (PageData pd : results) {
				Double quantity = (Double) pd.get("quantity");
				sum -= quantity;
				PageData inbound_stock_batch = new PageData();
				inbound_stock_batch.put("inbound_code", inboundDetail.get("inbound_code"));
				inbound_stock_batch.put("inbound_detail_id", inboundDetail.get("inbound_detail_id"));
				inbound_stock_batch.put("sku_id", pd.get("sku_id"));
				inbound_stock_batch.put("sku_name", pd.get("sku_name"));
				inbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				inbound_stock_batch.put("batch_code", pd.get("batch_code"));
				
				if(pd.getString("batch_code").length() == 6) {
					String batchNo = pd.getString("sku_barcode")+pd.getString("batch_code");
					String result = batchgenerateService.generateBatchNo(batchNo);
					if(result.length()!=20) {
						String info = "生成了错误的批次码:"+result+",请检查!";
						throw new Exception(info);
					}
					inbound_stock_batch.put("batch_code", result);
					pd.put("batch_code", result);
					inboundnoticestockbatchService.edit(pd);
				}
				
				inbound_stock_batch.put("quantity", pd.get("quantity"));
				inbound_stock_batch.put("spec", pd.get("spec"));
				inbound_stock_batch.put("unit_name", pd.get("unit_name"));
				inboundstockbatchService.save(inbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get("quantity").toString()), 0);
			}
			if(sum != 0.0) {
				throw new Exception("批次数量与实际入库数量不符");
			}
		}

	}


	private void inboundDataBatch(String notice_detail_id, String inbound_quantity, PageData inboundDetail, int store_id)
			throws Exception {
		Double sum = Double.valueOf(inbound_quantity);
		PageData query = new PageData();
		query.put("notice_detail_id", notice_detail_id);
		List<PageData> results = inboundnoticestockbatchService.listAll(query);
		if(results!=null && results.size()>0) {
			for (PageData pd : results) {
				Double quantity = (Double) pd.get("quantity");
				sum -= quantity;
				PageData inbound_stock_batch = new PageData();
				inbound_stock_batch.put("inbound_code", inboundDetail.get("inbound_code"));
				inbound_stock_batch.put("inbound_detail_id", inboundDetail.get("inbound_detail_id"));
				inbound_stock_batch.put("sku_id", pd.get("sku_id"));
				inbound_stock_batch.put("sku_name", pd.get("sku_name"));
				inbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				inbound_stock_batch.put("batch_code", pd.get("batch_code"));
				
				if(pd.getString("batch_code").length() == 6) {
					String batchNo = pd.getString("sku_barcode")+pd.getString("batch_code");
					String result = batchgenerateService.generateBatchNo(batchNo);
					if(result.length()!=20) {
						String info = "生成了错误的批次码:"+result+",请检查!";
						throw new Exception(info);
					}
					inbound_stock_batch.put("batch_code", result);
					pd.put("batch_code", result);
					inboundnoticestockbatchService.edit(pd);
				}
				
				inbound_stock_batch.put("quantity", pd.get("quantity"));
				inbound_stock_batch.put("spec", pd.get("spec"));
				inbound_stock_batch.put("unit_name", pd.get("unit_name"));
				inboundstockbatchService.save(inbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get("quantity").toString()), 0);
			}
		}

	}

	private void outboundBatch(String notice_detail_id, String inbound_quantity, PageData inboundDetail, int store_id)
			throws Exception {

		PageData query = new PageData();
		query.put("notice_detail_id", notice_detail_id);
		List<PageData> results = inboundnoticestockbatchService.listAll(query);
		if(results!=null && results.size()>0) {
			for (PageData pd : results) {
				PageData outbound_stock_batch = new PageData();
				outbound_stock_batch.put("outbound_code", inboundDetail.get("outbound_code"));
				outbound_stock_batch.put("outbound_detail_id", inboundDetail.get("outbound_detail_id"));
				outbound_stock_batch.put("sku_id", pd.get("sku_id"));
				outbound_stock_batch.put("sku_name", pd.get("sku_name"));
				outbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				outbound_stock_batch.put("batch_code", pd.get("batch_code"));
				outbound_stock_batch.put("quantity", pd.get("quantity"));
				outbound_stock_batch.put("spec", pd.get("spec"));
				outbound_stock_batch.put("unit_name", pd.get("unit_name"));
				outboundstockbatchService.save(outbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get("quantity").toString()), 1);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void outboundDataBatch(String notice_detail_id, String inbound_quantity, PageData inboundDetail, int store_id)
			throws Exception {

		PageData query = new PageData();
		query.put("notice_detail_id", notice_detail_id);
		List<PageData> results = inboundnoticestockbatchService.listAll(query);
		if(results!=null && results.size()>0) {
			for (PageData pd : results) {
				PageData outbound_stock_batch = new PageData();
				outbound_stock_batch.put("outbound_code", inboundDetail.get("outbound_code"));
				outbound_stock_batch.put("outbound_detail_id", inboundDetail.get("outbound_detail_id"));
				outbound_stock_batch.put("sku_id", pd.get("sku_id"));
				outbound_stock_batch.put("sku_name", pd.get("sku_name"));
				outbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				outbound_stock_batch.put("batch_code", pd.get("batch_code"));
				outbound_stock_batch.put("quantity", pd.get("quantity"));
				outbound_stock_batch.put("spec", pd.get("spec"));
				outbound_stock_batch.put("unit_name", pd.get("unit_name"));
				outboundstockbatchService.save(outbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get("quantity").toString()), 1);
			}
		}
	}
	
	private void saveInbound(User user, String inbound_notice_code, String bill_type, String purchase_code,
			String purchase_detail_id, String total_store_sn, String total_store_name, String inbound_code_sum)
			throws Exception {
		PageData inbound = new PageData();
		inbound.put("inbound_code", inbound_code_sum);
		inbound.put("inbound_type", bill_type);// 1、采购入库单、2、生产入库单、3、调拨入库单、4、客户退货入库单,
		inbound.put("purchase_code", purchase_code);// ----------------------采购单编码
		inbound.put("purchase_detail_id", purchase_detail_id);// ----------------------采购单明细id
		inbound.put("inbound_notice_code", inbound_notice_code);// ----------------------到货通知单编码
		inbound.put("business_time", new Date());
		inbound.put("store_sn", total_store_sn);// ----------------------总仓编码
		inbound.put("store_name", total_store_name);// --------------------总仓名称
		inbound.put("status", "0");// ----------------------------预留
		inbound.put("createby", user.getUSER_ID());
		inbound.put("create_time", new Date());
		inbound.put("updateby", user.getUSER_ID());
		inbound.put("update_time", new Date());
		inboundManagerService.save(inbound);
	}

	//生产采购批次提交--入质检库
	public void saveSubmit(JSONArray jsonArray) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); 
		
		PageData query_inbound_notice = new PageData();
		 PageData query_notice_detail = new PageData();
		 Double sum = 0.0;
		for(int i=0;i<jsonArray.size();i++){
		    JSONObject job = jsonArray.getJSONObject(i);
		    sum +=job.getDouble("quantity");
		    query_inbound_notice.put("inbound_notice_code", job.get("inbound_notice_code"));
		    query_notice_detail.put("notice_detail_id", job.get("notice_detail_id"));
		    System.out.println(job);
		    PageData pageData = new PageData();
		    pageData.put("inbound_notice_stock_batch_id", job.get("inbound_notice_stock_batch_id"));
		    pageData.put("status", 1);
		    String batchNo = job.getString("sku_barcode")+job.getString("batch_code");
			String result = batchgenerateService.generateBatchNo(batchNo);
		    pageData.put("batch_code", result);
		    inboundnoticestockbatchService.updateStatus(pageData);
		}
		
		
		//入待检库
		//1.获取入库通知单信息
		PageData result_inbound_notice = inboundnoticeService.findByCode(query_inbound_notice);
		//2.获取入库通知单明细单条信息
		PageData result_notice_detail = inboundnoticedetailService.findById(query_notice_detail);
		String status = result_notice_detail.getString("status");
		if ("2".equals(status)) {
			throw new Exception("存在已提交的批次");
		}
		//3.获取如同通知单批次明细
		//4.入待检库
		String bill_type = result_inbound_notice.get("bill_type").toString();
		
		//String store_name = (String) resultInbound.get("store_name");
		String query_sn = result_inbound_notice.get("store_sn").toString();
		String purchase_code = result_inbound_notice.getString("purchase_code");
		
		PageData queryStore = new PageData();
		queryStore.put("store_sn", query_sn);
		PageData resultStore = szystoreService.findByStoreSn(queryStore);
		String inbound_notice_code = result_inbound_notice.getString("inbound_notice_code");
		saveBatchHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore, result_notice_detail, sum);
		
		PageData pageData = new PageData();
		//已批次
		pageData.put("status", 2);
		pageData.put("notice_detail_id", jsonArray.getJSONObject(0).get("notice_detail_id"));
		pageData.put("updateby", user.getUSER_ID());	//更新人
		pageData.put("update_time", new Date());	//更新时间
		inboundnoticedetailService.updateState(pageData);
		
	}
	
	private void saveBatchHandler(String inbound_notice_code, User user, String purchase_code, String bill_type,
			PageData resultStore, PageData result_notice_detail, Double sum) throws Exception {
		String quality_store_id = resultStore.getString("quality_store_id");
		if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
			throw new Exception("未关联质检仓");
		}
		Integer intype = InOutBoundType.PURCHASE_INBOUND_QUALITY;
		if("2".equals(bill_type)) {
			intype = InOutBoundType.PRODUCTION_INBOUND_QUALITY;
		}
		
		PageData queryStore2 = new PageData();
		queryStore2.put("store_id", quality_store_id);
		PageData store_data = szystoreService.findById(queryStore2);
		String store_sn = store_data.getString("store_sn");
		String store_name = store_data.getString("store_name");
		
		String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
		saveInbound(user, inbound_notice_code, intype.toString(), purchase_code, null, store_sn, store_name, inbound_code);

		PageData resultDetail = result_notice_detail;
		{
			String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
			String sku_name = resultDetail.getString("sku_name");
			String sku_encode = resultDetail.getString("sku_encode");
			String spec = resultDetail.getString("spec");
			String purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
			String inbound_quantity =  sum.toString();
			String notice_detail_id = resultDetail.get("notice_detail_id").toString();
			
			PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id, sku_id,
					sku_name, sku_encode,spec, inbound_quantity, inbound_code);
			qualityInboundBatch(notice_detail_id, inboundDetail, new Integer(quality_store_id), sum);
			// 增加分仓库存
			szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
		}
	}
	private Double qualityInboundBatch(String notice_detail_id, PageData inboundDetail, int store_id, Double number)
			throws Exception {
		Double sum = 0.0;
		PageData query = new PageData();
		query.put("notice_detail_id", notice_detail_id);
		List<PageData> results = inboundnoticestockbatchService.listAll(query);
		if(results!=null && results.size()>0) {
			for (PageData pd : results) {
				Double quantity = (Double) pd.get("quantity");
				sum += quantity;
				PageData inbound_stock_batch = new PageData();
				inbound_stock_batch.put("inbound_code", inboundDetail.get("inbound_code"));
				inbound_stock_batch.put("inbound_detail_id", inboundDetail.get("inbound_detail_id"));
				inbound_stock_batch.put("sku_id", pd.get("sku_id"));
				inbound_stock_batch.put("sku_name", pd.get("sku_name"));
				inbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				inbound_stock_batch.put("batch_code", pd.get("batch_code"));
				
				if(pd.getString("batch_code").length() == 6) {
					String batchNo = pd.getString("sku_barcode")+pd.getString("batch_code");
					String result = batchgenerateService.generateBatchNo(batchNo);
					if(result.length()!=20) {
						String info = "生成了错误的批次码:"+result+",请检查!";
						throw new Exception(info);
					}
					inbound_stock_batch.put("batch_code", result);
					pd.put("batch_code", result);
					inboundnoticestockbatchService.edit(pd);
				}
				
				inbound_stock_batch.put("quantity", pd.get("quantity"));
				inbound_stock_batch.put("spec", pd.get("spec"));
				inbound_stock_batch.put("unit_name", pd.get("unit_name"));
				inboundstockbatchService.save(inbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get("quantity").toString()), 0);
			}
		}
		if(sum.intValue() != number.intValue()) {
			throw new Exception("批次数量与实际入库数量不符");
		}
		return sum;
	}
	/**
	 * 2017-11-14 非生产采购入库
	 * */
	public void saveInboundHandler(String inbound_notice_code, String in_code) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); 
		/**
		 * 创建入库单+更新库存
		 * 注意 采购&生产走 入总仓--出总仓--入分仓
		 * */
		
		PageData queryInboundNotice = new PageData();
		queryInboundNotice.put("inbound_notice_code", inbound_notice_code);
		PageData resultInbound = inboundnoticeService.findByCode(queryInboundNotice);
		String bill_type = resultInbound.get("bill_type").toString();
		//String store_name = (String) resultInbound.get("store_name");
		String store_sn = resultInbound.get("store_sn").toString();
		// 1、采购到货通知、2、生产到货通知、3、调拨到货通知、4、客户退货到货通知 5、领料退货到货通知单 6、拒收到货通知单 7、其他入库单
		String purchase_code = resultInbound.getString("purchase_code");
		String purchase_detail_id = null;
		
		PageData pdStoreParameter=new PageData();
		pdStoreParameter.put("store_sn",store_sn);
		PageData pdStore=szystoreService.findByStoreSn(pdStoreParameter);
		
		if (("1".equals(bill_type) || "2".equals(bill_type))) {
			//良品入库：待检库（采购待检出库）采购总仓库（采购总仓入库）采购总仓库（采购总仓出库）正品库（采购正品入库）
			//不良品入库：待检库（采购待检出库）采购总仓库（采购总仓入库）采购总仓库（采购总仓出库）正品库（采购正品入库）
			bussionQualityHandler(inbound_notice_code, in_code, user, bill_type, purchase_code, purchase_detail_id, pdStore);
			//bussionInboundQualityHandler(inbound_notice_code, in_code, user, bill_type, purchase_code, purchase_detail_id, pdStore);
		}else if("3".equals(bill_type)){
			//调拨入库
			bussionAllocationHandler(inbound_notice_code, in_code, user, bill_type, purchase_code, purchase_detail_id, pdStore,
					InOutBoundType.ALLOCATION_OUTBOUND_QUALITY,
					InOutBoundType.ALLOCATION_INBOUND);
		}else if("4".equals(bill_type)){
			//退货入库
			bussionReturnHandler(inbound_notice_code, in_code, user, bill_type, purchase_code, purchase_detail_id, pdStore,
					InOutBoundType.ORDER_OUTBOUND_RETURN,
					InOutBoundType.ORDER_INBOUND_RETURN);
		}else if("6".equals(bill_type)){
			//拒收入库
			bussionAllocationHandler(inbound_notice_code, in_code, user, bill_type, purchase_code, purchase_detail_id, pdStore,
					InOutBoundType.ORDER_OUTBOUND_REJECT_QUALITY,
					InOutBoundType.ORDER_INBOUND_REJECT);
		} else {
			bussionHandler(inbound_notice_code, in_code, user, bill_type, purchase_code, purchase_detail_id, pdStore);
		}
	}
	
	private void bussionReturnHandler(String inbound_notice_code, String in_code, User user, String bill_type,
			String purchase_code, String purchase_detail_id, PageData pdStore, int outType, int inType) throws Exception {
		String[] strArray = in_code.split("__");
		if(strArray.length<1 || "".equals(in_code)) {
			throw new Exception("请选中要入库的商品");
		}
		
		int store_id=Integer.parseInt(pdStore.getString("store_id"));
		String store_name = pdStore.getString("store_name");
		String store_sn =  pdStore.getString("store_sn");
		
		PageData totalStoreDB = szystoreService.findClubStore(new PageData());
		if(totalStoreDB == null) {
			throw new Exception("找不到俱乐部仓库");
		}
		String total_store_sn = totalStoreDB.getString("store_sn");
		String total_store_name = totalStoreDB.getString("store_name");
		int total_store_id = Integer.parseInt(totalStoreDB.getString("store_id"));
		
		String inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
		this.saveInbound(user, inbound_notice_code, inType + "", purchase_code, purchase_detail_id, total_store_sn,
				total_store_name, inbound_code_sum);
		String outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
		this.saveOutbound(user, inbound_notice_code, outType + "", purchase_code, purchase_detail_id, total_store_sn,
				total_store_name, outbound_code_sum);
		// 入库单
		String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
		this.saveInbound(user, inbound_notice_code, inType + "", purchase_code, purchase_detail_id, store_sn, store_name,
				inbound_code);

		for (String str : strArray) {
			String notice_detail_id = str;
			
			PageData queryNoticeDetail = new PageData();
			queryNoticeDetail.put("notice_detail_id", notice_detail_id);
			PageData resultDetail = inboundnoticedetailService.findById(queryNoticeDetail);
			String status = resultDetail.getString("status");
			if ("9".equals(status)) {
				throw new Exception("存在已入库的明细");
			}
			String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
			String sku_name = resultDetail.getString("sku_name");
			String sku_encode = resultDetail.getString("sku_encode");
			String spec = resultDetail.getString("spec");
			purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
			Object gq = resultDetail.get("good_quantity");
			String inbound_quantity =  gq.toString();// 6入库数量
			
			PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
					purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
			inboundBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id);
			PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
					purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
			outboundBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id);
			
			//入库单明细
			PageData inboundDetail2 = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id, sku_id,
					sku_name, sku_encode,spec, inbound_quantity, inbound_code);
			//增加批次库存
			inboundBatch(notice_detail_id, inbound_quantity, inboundDetail2, store_id);
			// 增加分仓库存
			szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			{//更新入库状态
				PageData pd1 = new PageData();
				pd1.put("notice_detail_id", Integer.valueOf(notice_detail_id));
				//已入库
				pd1.put("status", 9);
				pd1.put("updateby", user.getUSER_ID());
				pd1.put("update_time", new Date());
				inboundnoticedetailService.updateState(pd1);
			}
		}
	}

	/**
	 * 采购、生产订单入库
	 * @throws Exception 
	 */
	public void saveInboundDataHandler(PageData pd) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); 
		String bill_type = pd.get("bill_type").toString();
		String store_sn = pd.get("store_sn").toString();
		PageData pdStoreParameter=new PageData();
		pdStoreParameter.put("store_sn",store_sn);
		PageData pdStore=szystoreService.findByStoreSn(pdStoreParameter);
		//bussionQualityDataHandler(pd, user, pdStore, bill_type);
		bussionQuality(pd, user, pdStore, bill_type);
	}
	private void bussionQuality(PageData pd, User user, PageData pdStore, String bill_type) throws Exception {
		String in_code = pd.getString("in_code");
		String[] strArray = in_code.split(",");
		String inbound_notice_code = pd.getString("inbound_notice_code");
		String purchase_code = null;
		Map<String, PageData> inBadMap = new HashMap<>();
		Map<String, PageData> outBadMap = new HashMap<>();
		Map<String, PageData> inGoodMap = new HashMap<>();
		Map<String, PageData> inScrapMap = new HashMap<>();
		
		Map<String, PageData> inBadBatchMap = new HashMap<>();
		Map<String, PageData> outBadBatchMap = new HashMap<>();
		Map<String, PageData> inGoodBatchMap = new HashMap<>();
		Map<String, PageData> inScrapBatchMap = new HashMap<>();
		for (String pgt_inbound_check_detail_id : strArray) {
			PageData idPd = new PageData();
			idPd.put("pgt_inbound_check_detail_id", Integer.parseInt(pgt_inbound_check_detail_id));
			PageData pageData = inboundCheckDetailManager.findById(idPd);
			String status = pageData.getString("status");
			if ("2".equals(status)) {
				throw new Exception("存在已入库的明细");
			}
			purchase_code = pageData.getString("purchase_code");
			String batch_code = pageData.getString("batch_code");
			String notice_detail_id = pageData.get("notice_detail_id").toString();
			Integer good_quantity = (Integer) pageData.get("good_quantity");
			Integer bad_quantity = (Integer) pageData.get("bad_quantity");
			Integer scrap_quantity = (Integer) pageData.get("scrap_quantity");
			Integer bad_deal_type = (Integer) pageData.get("bad_deal_type");
			Integer in_quantity = good_quantity+scrap_quantity;
			if (bad_deal_type == 1) {
				in_quantity += bad_quantity;
			}
			//获取与到货通知
			PageData prePd = new PageData();
			prePd.put("notice_detail_id", pageData.get("purchase_detail_id"));
			PageData preReslut = pgtinboundprenoticedetailService.findById(prePd);
			/**
			 * 更新退货数量到采购单
			 */
			String purchase_detail_id = preReslut.getString("purchase_detail_id");
			PageData pd2 = new PageData();
			pd2.put("good_quantity", in_quantity);
			pd2.put("bad_quantity", 0);
			pd2.put("detail_id", purchase_detail_id);
			purchasedetailService.qualityCheck(pd2);
			if(bad_quantity > 0) {
				if (bad_deal_type == 1) {
					if(inBadBatchMap.containsKey(batch_code)) {
						PageData tempData = inBadBatchMap.get(batch_code);
						Integer bad = (Integer) tempData.get("bad_quantity_Batch");
						pageData.put("bad_quantity_Batch",bad+bad_quantity);
					}else {
						pageData.put("bad_quantity_Batch",bad_quantity);
					}
					inBadBatchMap.put(batch_code, pageData);
					if(inBadMap.containsKey(notice_detail_id)) {
						PageData tempData = inBadMap.get(notice_detail_id);
						Integer bad = (Integer) tempData.get("bad_quantity");
						pageData.put("bad_quantity",bad+bad_quantity);
					}
					inBadMap.put(notice_detail_id, pageData);
				}else {
					if(outBadBatchMap.containsKey(batch_code)) {
						PageData tempData = outBadBatchMap.get(batch_code);
						Integer bad = (Integer) tempData.get("bad_quantity_Batch");
						pageData.put("bad_quantity_Batch",bad+bad_quantity);
					}else {
						pageData.put("bad_quantity_Batch",bad_quantity);
					}
					outBadBatchMap.put(batch_code, pageData);
					if(outBadMap.containsKey(notice_detail_id)) {
						PageData tempData = outBadMap.get(notice_detail_id);
						Integer bad = (Integer) tempData.get("bad_quantity");
						pageData.put("bad_quantity",bad+bad_quantity);
					}
					outBadMap.put(notice_detail_id, pageData);
				}
			}
			if(good_quantity > 0) {
				if(inGoodBatchMap.containsKey(batch_code)) {
					PageData tempData = inGoodBatchMap.get(batch_code);
					Integer good = (Integer) tempData.get("good_quantity_Batch");
					pageData.put("good_quantity_Batch",good+good_quantity);
				}else {
					pageData.put("good_quantity_Batch",good_quantity);
				}
				inGoodBatchMap.put(batch_code, pageData);
				if(inGoodMap.containsKey(notice_detail_id)) {
					PageData tempData = inGoodMap.get(notice_detail_id);
					Integer good = (Integer) tempData.get("good_quantity");
					pageData.put("good_quantity",good+good_quantity);
				}
				inGoodMap.put(notice_detail_id, pageData);
			}
			if (scrap_quantity > 0) {
				if(inScrapBatchMap.containsKey(batch_code)) {
					PageData tempData = inScrapBatchMap.get(batch_code);
					Integer scrap = (Integer) tempData.get("scrap_quantity_Batch");
					pageData.put("scrap_quantity_Batch",scrap+scrap_quantity);
				}else {
					pageData.put("scrap_quantity_Batch",scrap_quantity);
				}
				inScrapBatchMap.put(batch_code, pageData);
				if(inScrapMap.containsKey(notice_detail_id)) {
					PageData tempData = inScrapMap.get(notice_detail_id);
					Integer scrap = (Integer) tempData.get("scrap_quantity");
					pageData.put("scrap_quantity",scrap+scrap_quantity);
				}
				inScrapMap.put(notice_detail_id, pageData);
			}
		}
		
		
		String store_name = pdStore.getString("store_name");
		String store_sn =  pdStore.getString("store_sn");
		String quality_store_id = pdStore.getString("quality_store_id");
		if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
			throw new Exception("未关联质检仓");
		}
		PageData queryStore = new PageData();
		queryStore.put("store_id", quality_store_id);
		PageData store_data = szystoreService.findById(queryStore);
		String quality_store_sn = store_data.getString("store_sn");
		String quality_store_name = store_data.getString("store_name");
		PageData totalStoreDB = szystoreService.findTotalStore(new PageData());
		if(totalStoreDB == null) {
			throw new Exception("找不到生产采购入库的总仓");
		}
		String total_store_sn = totalStoreDB.getString("store_sn");
		String total_store_name = totalStoreDB.getString("store_name");
		int total_store_id = Integer.parseInt(totalStoreDB.getString("store_id"));
		if (inGoodMap.size() > 0) {
			int store_id=Integer.parseInt(pdStore.getString("store_id"));
			String purchase_detail_id = null;
			//出待检仓 进总仓 出总仓 进良品仓
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_GOOD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_GOOD;
			}
			String quality_outbound_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code);
			String inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, inbound_code_sum);
			String outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, outbound_code_sum);
			String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, store_sn,
					store_name, inbound_code);

			for (PageData resultDetail : inGoodMap.values()) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString();
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Object gq = (Integer)resultDetail.get("good_quantity");
				String inbound_quantity = gq.toString();
				//出待检仓(无批次) 进总仓(带批次) 出总仓(带批次) 进良品仓(带批次)
				PageData outboundDetailStore = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetailStore, Integer.parseInt(quality_store_id), "good_quantity_Batch", inGoodBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id, "good_quantity_Batch", inGoodBatchMap);
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id, "good_quantity_Batch", inGoodBatchMap);
				PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id, "good_quantity_Batch", inGoodBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			}
		}
		
		if(inBadMap.size() > 0) {
			
			String bad_store_id = pdStore.getString("bad_store_id");
			if(bad_store_id==null || "".equals(bad_store_id.trim()) || "0".equals(bad_store_id.trim())) {
				throw new Exception("未关联不良品仓");
			}
			int store_id=Integer.parseInt(bad_store_id);
			PageData queryBadStore = new PageData();
			queryBadStore.put("store_id", bad_store_id);
			PageData bad_store_data = szystoreService.findById(queryBadStore);
			String bad_store_sn = bad_store_data.getString("store_sn");
			String bad_store_name = bad_store_data.getString("store_name");
			
			String inbound_code_sum = null;
			String outbound_code_sum = null;
			String inbound_code = null;
			String purchase_detail_id = null;
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_BAD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_BAD;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);

				inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
				inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, inbound_code_sum);
				this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, outbound_code_sum);
				this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, bad_store_sn, bad_store_name,
						inbound_code);
			
			for (PageData resultDetail : inBadMap.values()) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
				String inbound_quantity = bad_quantity.toString();// 6入库数量

				PageData outboundDetailStore = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetailStore, Integer.parseInt(quality_store_id), "bad_quantity_Batch", inBadBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id, "bad_quantity_Batch", inBadBatchMap);
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id, "bad_quantity_Batch", inBadBatchMap);
				PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id, "bad_quantity_Batch", inBadBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), bad_store_sn, new Integer(sku_id), 0);
				
			}
		}
		if(outBadMap.size() > 0) {
			String purchase_detail_id = null;
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;

			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);
			
			for (PageData resultDetail : outBadMap.values()) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
				String inbound_quantity = bad_quantity.toString();// 6入库数量

				PageData outboundDetailStore = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetailStore, Integer.parseInt(quality_store_id), "bad_quantity_Batch", outBadBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
			}
		}
		
		if(inScrapMap.size() > 0) {
			String scrap_store_id = pdStore.getString("scrap_store_id");
			if(scrap_store_id==null || "".equals(scrap_store_id.trim()) || "0".equals(scrap_store_id.trim())) {
				throw new Exception("未关联报损仓");
			}
			int store_id=Integer.parseInt(scrap_store_id);
			PageData queryScrapStore = new PageData();
			queryScrapStore.put("store_id", scrap_store_id);
			PageData scrap_store_data = szystoreService.findById(queryScrapStore);
			String scrap_store_sn = scrap_store_data.getString("store_sn");
			String scrap_store_name = scrap_store_data.getString("store_name");
			
			String inbound_code_sum = null;
			String outbound_code_sum = null;
			String inbound_code = null;
			String purchase_detail_id = null;
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.SCRAP_INBOUND;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.SCRAP_INBOUND;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);
			inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, inbound_code_sum);
			this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, outbound_code_sum);
			this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, scrap_store_sn, scrap_store_name,
					inbound_code);
			
			for (PageData resultDetail : inScrapMap.values()) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer scrap_quantity = (Integer)resultDetail.get("scrap_quantity");
				String inbound_quantity = scrap_quantity.toString();// 6入库数量

				PageData outboundDetailStore = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetailStore, Integer.parseInt(quality_store_id), "scrap_quantity_Batch", inScrapBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id, "scrap_quantity_Batch", inScrapBatchMap);
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id, "scrap_quantity_Batch", inScrapBatchMap);
				PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id, "scrap_quantity_Batch", inScrapBatchMap);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), scrap_store_sn, new Integer(sku_id), 0);
			}
		}
		
		for (String pgt_inbound_check_detail_id : strArray) {
			PageData pdCheckDetail = new PageData();
			pdCheckDetail.put("pgt_inbound_check_detail_id", Integer.parseInt(pgt_inbound_check_detail_id));
			//0-未提交 1-已提交 2-已入库
			pdCheckDetail.put("status", 2);
			inboundCheckDetailManager.updateById(pdCheckDetail);
		}
	}
	private void outboundDataBatch(String notice_detail_id, String inbound_quantity, PageData outboundDetail,
			int store_id, String quantityKey, Map<String, PageData> results) throws Exception {
			for (PageData pd : results.values()) {
				if (!notice_detail_id.equals(pd.get("notice_detail_id").toString())) {
					continue;
				}
				PageData outbound_stock_batch = new PageData();
				outbound_stock_batch.put("outbound_code", outboundDetail.get("outbound_code"));
				outbound_stock_batch.put("outbound_detail_id", outboundDetail.get("outbound_detail_id"));
				outbound_stock_batch.put("sku_id", pd.get("sku_id"));
				outbound_stock_batch.put("sku_name", pd.get("sku_name"));
				outbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				outbound_stock_batch.put("batch_code", pd.get("batch_code"));
				outbound_stock_batch.put("quantity", pd.get(quantityKey));
				outbound_stock_batch.put("spec", pd.get("spec"));
				outbound_stock_batch.put("unit_name", pd.get("unit_name"));
				outboundstockbatchService.save(outbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get(quantityKey).toString()), 1);
			}
	}

	private void inboundDataBatch(String notice_detail_id, String inbound_quantity, PageData inboundDetail,
			int store_id, String quantityKey, Map<String, PageData> results) throws Exception {
			for (PageData pd : results.values()) {
				if (!notice_detail_id.equals(pd.get("notice_detail_id").toString())) {
					continue;
				}
				PageData inbound_stock_batch = new PageData();
				inbound_stock_batch.put("inbound_code", inboundDetail.get("inbound_code"));
				inbound_stock_batch.put("inbound_detail_id", inboundDetail.get("inbound_detail_id"));
				inbound_stock_batch.put("sku_id", pd.get("sku_id"));
				inbound_stock_batch.put("sku_name", pd.get("sku_name"));
				inbound_stock_batch.put("sku_encode", pd.get("sku_encode"));
				inbound_stock_batch.put("batch_code", pd.get("batch_code"));
				
				if(pd.getString("batch_code").length() != 20) {
						String info = "错误的批次码:"+pd.getString("batch_code")+",请检查!";
						throw new Exception(info);
				}
				inbound_stock_batch.put("quantity", pd.get(quantityKey));
				inbound_stock_batch.put("spec", pd.get("spec"));
				inbound_stock_batch.put("unit_name", pd.get("unit_name"));
				inboundstockbatchService.save(inbound_stock_batch);
				String batchCode=pd.getString("batch_code");//商品批号
				//更新批次库存
	            stockBatchManager.insertBatchStock(store_id, batchCode, Integer.parseInt(pd.get("sku_id").toString()), new Double(pd.get(quantityKey).toString()), 0);
			}
	}

	private void bussionQualityDataHandler(PageData pd, User user, PageData pdStore, String bill_type) throws Exception {
		String in_code = pd.getString("in_code");
		String[] strArray = in_code.split(",");
		String inbound_notice_code = pd.getString("inbound_notice_code");
		PageData queryDetail = new PageData();
		queryDetail.put("inbound_notice_code", inbound_notice_code);
		queryDetail.put("status", 1);
		List<PageData> varList = inboundCheckDetailManager.listByWhere(queryDetail);
		Map<String, Boolean> batchMap = new HashMap<String, Boolean>();
		for(PageData var : varList) {
			String id = var.getString("inbound_notice_stock_batch_id");
			String isbatch = var.getString("isopen_batch");
			batchMap.put(id, "1".equals(isbatch));
		}
		
		List<PageData> allBad = new ArrayList<PageData>();
		List<PageData> inboundBad = new ArrayList<PageData>();
		List<PageData> inboundGood = new ArrayList<PageData>();
		List<PageData> allScrap = new ArrayList<PageData>();
		
		for (String pgt_inbound_check_detail_id : strArray) {
			PageData idPd = new PageData();
			idPd.put("pgt_inbound_check_detail_id", Integer.parseInt(pgt_inbound_check_detail_id));
			PageData pageData = inboundCheckDetailManager.findById(idPd);
			String notice_detail_id = pageData.get("notice_detail_id").toString();
//			PageData pd1 = new PageData();
//			pd1.put("notice_detail_id", Integer.valueOf(notice_detail_id));
//			pd1.put("status", 1);
//			pd1.put("updateby", user.getUSER_ID());
//			pd1.put("update_time", new Date());
//			inboundnoticedetailService.updateState(pd1);

			Integer good_quantity = (Integer) pageData.get("good_quantity");
			Integer bad_quantity = (Integer) pageData.get("bad_quantity");
			Integer scrap_quantity = (Integer) pageData.get("scrap_quantity");
			
			Integer in_quantity = good_quantity+scrap_quantity;
			Integer out_quantity = bad_quantity;
			
			if (bad_quantity > 0) {
				allBad.add(pageData);
				Integer bad_deal_type = (Integer) pageData.get("bad_deal_type");
				if (bad_deal_type == 1) {
					inboundBad.add(pageData);
					in_quantity += out_quantity;
					out_quantity = 0;
				}
			}
			
			if (scrap_quantity > 0) {
				allScrap.add(pageData);
			}
			/**
			 * 更新退货数量到采购单
			 */
			String purchase_detail_id = pageData.getString("purchase_detail_id");
			PageData pd2 = new PageData();
			pd2.put("good_quantity", in_quantity);
			pd2.put("bad_quantity", 0);
			pd2.put("detail_id", purchase_detail_id);
			purchasedetailService.qualityCheck(pd2);
			
			if (good_quantity == 0) {
				continue;
			}
			
			inboundGood.add(pageData);
			if (batchMap.get(notice_detail_id) != null && batchMap.get(notice_detail_id)) {
				PageData query = new PageData();
				query.put("notice_detail_id", notice_detail_id);
				List<PageData> results = inboundnoticestockbatchService.listAll(query);
				if (results == null || results.size() == 0) {
					throw new Exception("存在缺少批次的商品");
				}
			}
		}
		
		int store_id=Integer.parseInt(pdStore.getString("store_id"));
		String store_name = pdStore.getString("store_name");
		String store_sn =  pdStore.getString("store_sn");
		String quality_store_id = pdStore.getString("quality_store_id");
		if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
			throw new Exception("未关联质检仓");
		}
		PageData queryStore = new PageData();
		queryStore.put("store_id", quality_store_id);
		PageData store_data = szystoreService.findById(queryStore);
		String quality_store_sn = store_data.getString("store_sn");
		String quality_store_name = store_data.getString("store_name");
		PageData totalStoreDB = szystoreService.findTotalStore(new PageData());
		if(totalStoreDB == null) {
			throw new Exception("找不到生产采购入库的总仓");
		}
		String total_store_sn = totalStoreDB.getString("store_sn");
		String total_store_name = totalStoreDB.getString("store_name");
		int total_store_id = Integer.parseInt(totalStoreDB.getString("store_id"));
		
		if (inboundGood.size() > 0) {
			String purchase_code = inboundGood.get(0).getString("purchase_code");
			String purchase_detail_id = inboundGood.get(0).get("purchase_detail_id").toString();
			//出待检仓 进总仓 出总仓 进良品仓
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_GOOD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_GOOD;
			}
			String quality_outbound_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code);
			String inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, inbound_code_sum);
			String outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, outbound_code_sum);
			String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, store_sn,
					store_name, inbound_code);

			for (PageData resultDetail : inboundGood) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString();
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Object gq = (Integer)resultDetail.get("good_quantity");
				String inbound_quantity = gq.toString();
				//出待检仓(无批次) 进总仓(带批次) 出总仓(带批次) 进良品仓(带批次)
				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id);
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				outboundDataBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id);
				PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundDataBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			}
		}
		
		if(allBad.size() > 0) {
			String bad_store_id = pdStore.getString("bad_store_id");
			if(bad_store_id==null || "".equals(bad_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联不良品仓");
			}
			PageData queryBadStore = new PageData();
			queryBadStore.put("store_id", bad_store_id);
			PageData bad_store_data = szystoreService.findById(queryBadStore);
			String bad_store_sn = bad_store_data.getString("store_sn");
			String bad_store_name = bad_store_data.getString("store_name");
			
			String inbound_code_sum = null;
			String outbound_code_sum = null;
			String inbound_code = null;
			String purchase_code = allBad.get(0).getString("purchase_code");
			String purchase_detail_id = allBad.get(0).get("purchase_detail_id").toString();
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_BAD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_BAD;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);
			if(inboundBad.size() > 0) {
				inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
				inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, inbound_code_sum);
				this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, outbound_code_sum);
				this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, bad_store_sn, bad_store_name,
						inbound_code);
			}
			
			for (PageData resultDetail : allBad) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
				Integer bad_deal_type = (Integer) resultDetail.get("bad_deal_type");
				String inbound_quantity = bad_quantity.toString();// 6入库数量

				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				if (bad_deal_type == 1) {
					saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
					saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
					saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code);
					szystockgoodsService.quantityModify(new Double(inbound_quantity), bad_store_sn, new Integer(sku_id), 0);
				}
			}
		}
		
		if(allScrap.size() > 0) {
			String scrap_store_id = pdStore.getString("scrap_store_id");
			if(scrap_store_id==null || "".equals(scrap_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联报损仓");
			}
			PageData queryScrapStore = new PageData();
			queryScrapStore.put("store_id", scrap_store_id);
			PageData scrap_store_data = szystoreService.findById(queryScrapStore);
			String scrap_store_sn = scrap_store_data.getString("store_sn");
			String scrap_store_name = scrap_store_data.getString("store_name");
			
			String inbound_code_sum = null;
			String outbound_code_sum = null;
			String inbound_code = null;
			String purchase_code = allBad.get(0).getString("purchase_code");
			String purchase_detail_id = allBad.get(0).get("purchase_detail_id").toString();
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.SCRAP_INBOUND;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.SCRAP_INBOUND;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);
			inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, inbound_code_sum);
			this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, outbound_code_sum);
			this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, scrap_store_sn, scrap_store_name,
					inbound_code);
			
			for (PageData resultDetail : allScrap) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer scrap_quantity = (Integer)resultDetail.get("scrap_quantity");
				String inbound_quantity = scrap_quantity.toString();// 6入库数量

				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), scrap_store_sn, new Integer(sku_id), 0);
			}
		}
		
		for (String pgt_inbound_check_detail_id : strArray) {
			PageData pdCheckDetail = new PageData();
			pdCheckDetail.put("pgt_inbound_check_detail_id", Integer.parseInt(pgt_inbound_check_detail_id));
			//0-未提交 1-已提交 2-已入库
			pdCheckDetail.put("status", 2);
			inboundCheckDetailManager.updateById(pdCheckDetail);
		}
	}
	
	private void bussionAllocationHandler(String inbound_notice_code, String in_code, User user, String bill_type,
			String purchase_code, String purchase_detail_id, PageData pdStore,
			Integer outType, Integer inType) throws Exception {

		String[] strArray = in_code.split("__");
		if (strArray.length < 1 || "".equals(in_code)) {
			throw new Exception("请选中要入库的商品");
		}

		int store_id = Integer.parseInt(pdStore.getString("store_id"));
		String store_name = pdStore.getString("store_name");
		String store_sn = pdStore.getString("store_sn");
		String quality_store_id = pdStore.getString("quality_store_id");
		if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
			throw new Exception("未关联质检仓");
		}
		PageData queryStore = new PageData();
		queryStore.put("store_id", quality_store_id);
		PageData store_data = szystoreService.findById(queryStore);
		String quality_store_sn = store_data.getString("store_sn");
		String quality_store_name = store_data.getString("store_name");
		
		// 质检出库
		String quality_outbound_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
		this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
				quality_store_name, quality_outbound_code);
		// 调拨入库
		String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
		this.saveInbound(user, inbound_notice_code, inType.toString(), purchase_code, purchase_detail_id, store_sn, store_name,
				inbound_code);

		for (String notice_detail_id : strArray) {
			PageData queryNoticeDetail = new PageData();
			queryNoticeDetail.put("notice_detail_id", notice_detail_id);
			PageData resultDetail = inboundnoticedetailService.findById(queryNoticeDetail);
			String status = resultDetail.getString("status");
			if ("9".equals(status)) {
				throw new Exception("存在已入库的明细");
			}
			
			String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
			String sku_name = resultDetail.getString("sku_name");
			String sku_encode = resultDetail.getString("sku_encode");
			String spec = resultDetail.getString("spec");
			purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
			Object gq = resultDetail.get("good_quantity");
			String inbound_quantity = gq.toString();// 6入库数量

			PageData qualityOutboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id,
					purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
					quality_outbound_code);
			szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
			outboundBatch(notice_detail_id, inbound_quantity, qualityOutboundDetail,
					Integer.parseInt(quality_store_id));

			PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
					purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code);
			inboundBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id);
			szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			
			PageData pd1 = new PageData();
			pd1.put("notice_detail_id", Integer.valueOf(notice_detail_id));
			//已入库
			pd1.put("status", 9);
			pd1.put("updateby", user.getUSER_ID());
			pd1.put("update_time", new Date());
			inboundnoticedetailService.updateState(pd1);
		}
		//如果是调拨入库，回写调拨单状态为9:已完成
		//2017-10-13 入库完成是回写
		/*PageData pd = new PageData();
		pd.put("allocation_code", purchase_code);
		pd.put("status", 9);
		pd.put("updateby", user.getUSER_ID());
		allocationService.updateStatusWithIn(pd);*/
	}

	/**
	  * 需质检的出入库
	  * 2017-10-09 采购生产业务变更
	  * 添加报损入库 详见bussionInboundQualityHandler
	  * */
	@Deprecated
	public void bussionQualityHandler(String inbound_notice_code, String in_code, User user, String bill_type,
			String purchase_code, String purchase_detail_id, PageData pdStore) throws Exception {
		
		String[] strArray = in_code.split("__");
		if(strArray.length<1 || "".equals(in_code)) {
			throw new Exception("请选中要入库的商品");
		}
		//粗壮批次管理的产品
		PageData queryDetail = new PageData();
		queryDetail.put("inbound_notice_code", inbound_notice_code);
		List<PageData> varList = inboundnoticedetailService.listByWhere(queryDetail);
		Map<String, Boolean> batchMap = new HashMap<String, Boolean>();
		for(PageData var : varList) {
			String id = var.getString("notice_detail_id");
			String isbatch = var.getString("isopen_batch");
			batchMap.put(id, "1".equals(isbatch));
		}
		
		List<PageData> allBad = new ArrayList<PageData>();
		List<PageData> inboundBad = new ArrayList<PageData>();
		List<PageData> inboundGood = new ArrayList<PageData>();
		
		for (String notice_detail_id : strArray) {
			PageData pd1 = new PageData();
			pd1.put("notice_detail_id", Integer.valueOf(notice_detail_id));
			pd1.put("status", 1);
			pd1.put("updateby", user.getUSER_ID());
			pd1.put("update_time", new Date());
			inboundnoticedetailService.updateState(pd1);

			PageData queryNoticeDetail = new PageData();
			queryNoticeDetail.put("notice_detail_id", notice_detail_id);
			PageData resultDetail = inboundnoticedetailService.findById(queryNoticeDetail);
			Integer good_quantity = (Integer) resultDetail.get("good_quantity");
			Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
			
			Integer in_quantity = good_quantity;
			Integer out_quantity = bad_quantity;
			Integer quality_status = (Integer) resultDetail.get("quality_status");
			if(quality_status==null || quality_status !=1) {
				throw new Exception("未质检不能入库");
			}
			
			if (bad_quantity > 0) {
				allBad.add(resultDetail);
				Integer bad_deal_type = (Integer) resultDetail.get("bad_deal_type");
				if (bad_deal_type == 1) {
					inboundBad.add(resultDetail);
					in_quantity += out_quantity;
					out_quantity = 0;
				}
			}
			/**
			 * 更新退货数量到采购单
			 */
			purchase_detail_id = resultDetail.getString("purchase_detail_id");
			PageData pd2 = new PageData();
			pd2.put("good_quantity", in_quantity);
			pd2.put("bad_quantity", 0);
			pd2.put("detail_id", purchase_detail_id);
			purchasedetailService.qualityCheck(pd2);
			
			if (good_quantity == 0) {
				continue;
			}
			inboundGood.add(resultDetail);
			if (batchMap.get(notice_detail_id) != null && batchMap.get(notice_detail_id)) {
				PageData query = new PageData();
				query.put("notice_detail_id", notice_detail_id);
				List<PageData> results = inboundnoticestockbatchService.listAll(query);
				if (results == null || results.size() == 0) {
					throw new Exception("存在缺少批次的商品");
				}
			}
		}
		
		int store_id=Integer.parseInt(pdStore.getString("store_id"));
		String store_name = pdStore.getString("store_name");
		String store_sn =  pdStore.getString("store_sn");
		String quality_store_id = pdStore.getString("quality_store_id");
		if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
			throw new Exception("未关联质检仓");
		}
		PageData queryStore = new PageData();
		queryStore.put("store_id", quality_store_id);
		PageData store_data = szystoreService.findById(queryStore);
		String quality_store_sn = store_data.getString("store_sn");
		String quality_store_name = store_data.getString("store_name");
		PageData totalStoreDB = szystoreService.findTotalStore(new PageData());
		if(totalStoreDB == null) {
			throw new Exception("找不到生产采购入库的总仓");
		}
		String total_store_sn = totalStoreDB.getString("store_sn");
		String total_store_name = totalStoreDB.getString("store_name");
		int total_store_id = Integer.parseInt(totalStoreDB.getString("store_id"));
		
		
		if (inboundGood.size() > 0) {
			//出待检仓 进总仓 出总仓 进良品仓
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_GOOD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_GOOD;
			}
			String quality_outbound_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code);
			String inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, inbound_code_sum);
			String outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, outbound_code_sum);
			String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, store_sn,
					store_name, inbound_code);

			for (PageData resultDetail : inboundGood) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString();
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Object gq = resultDetail.get("good_quantity");
				String inbound_quantity = gq.toString();
				//出待检仓(无批次) 进总仓(带批次) 出总仓(带批次) 进良品仓(带批次)
				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				inboundBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id);
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				outboundBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id);
				PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			}
		}
		
		if(allBad.size() > 0) {
			String bad_store_id = pdStore.getString("bad_store_id");
			if(bad_store_id==null || "".equals(bad_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联不良品仓");
			}
			PageData queryBadStore = new PageData();
			queryBadStore.put("store_id", bad_store_id);
			PageData bad_store_data = szystoreService.findById(queryBadStore);
			String bad_store_sn = bad_store_data.getString("store_sn");
			String bad_store_name = bad_store_data.getString("store_name");
			
			String inbound_code_sum = null;
			String outbound_code_sum = null;
			String inbound_code = null;
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_BAD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_BAD;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);
			if(inboundBad.size() > 0) {
				inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
				inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, inbound_code_sum);
				this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, outbound_code_sum);
				this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, bad_store_sn, bad_store_name,
						inbound_code);
			}
			
			for (PageData resultDetail : allBad) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
				Integer bad_deal_type = (Integer) resultDetail.get("bad_deal_type");
				String inbound_quantity = bad_quantity.toString();// 6入库数量

				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				if (bad_deal_type == 1) {
					saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
					saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
					saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code);
					szystockgoodsService.quantityModify(new Double(inbound_quantity), bad_store_sn, new Integer(sku_id), 0);
				}
			}
		}
	}
	
	public void bussionInboundQualityHandler(String inbound_notice_code, String in_code, User user, String bill_type,
			String purchase_code, String purchase_detail_id, PageData pdStore) throws Exception {
		
		String[] strArray = in_code.split("__");
		if(strArray.length<1 || "".equals(in_code)) {
			throw new Exception("请选中要入库的商品");
		}
		//粗壮批次管理的产品
		PageData queryDetail = new PageData();
		queryDetail.put("inbound_notice_code", inbound_notice_code);
		List<PageData> varList = inboundnoticedetailService.listByWhere(queryDetail);
		Map<String, Boolean> batchMap = new HashMap<String, Boolean>();
		for(PageData var : varList) {
			String id = var.getString("notice_detail_id");
			String isbatch = var.getString("isopen_batch");
			batchMap.put(id, "1".equals(isbatch));
		}
		
		List<PageData> allBad = new ArrayList<PageData>();
		List<PageData> inboundBad = new ArrayList<PageData>();
		List<PageData> inboundGood = new ArrayList<PageData>();
		
		for (String notice_detail_id : strArray) {
			PageData pd1 = new PageData();
			pd1.put("notice_detail_id", Integer.valueOf(notice_detail_id));
			pd1.put("status", 1);
			pd1.put("updateby", user.getUSER_ID());
			pd1.put("update_time", new Date());
			inboundnoticedetailService.updateState(pd1);

			PageData queryNoticeDetail = new PageData();
			queryNoticeDetail.put("notice_detail_id", notice_detail_id);
			PageData resultDetail = inboundnoticedetailService.findById(queryNoticeDetail);
			Integer good_quantity = (Integer) resultDetail.get("good_quantity");
			Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
			
			Integer in_quantity = good_quantity;
			Integer out_quantity = bad_quantity;
			Integer quality_status = (Integer) resultDetail.get("quality_status");
			if(quality_status==null || quality_status !=1) {
				throw new Exception("未质检不能入库");
			}
			
			if (bad_quantity > 0) {
				allBad.add(resultDetail);
				Integer bad_deal_type = (Integer) resultDetail.get("bad_deal_type");
				if (bad_deal_type == 1) {
					inboundBad.add(resultDetail);
					in_quantity += out_quantity;
					out_quantity = 0;
				}
			}
			/**
			 * 更新退货数量到采购单
			 */
			purchase_detail_id = resultDetail.getString("purchase_detail_id");
			PageData pd2 = new PageData();
			pd2.put("good_quantity", in_quantity);
			pd2.put("bad_quantity", 0);
			pd2.put("detail_id", purchase_detail_id);
			purchasedetailService.qualityCheck(pd2);
			
			if (good_quantity == 0) {
				continue;
			}
			inboundGood.add(resultDetail);
			if (batchMap.get(notice_detail_id) != null && batchMap.get(notice_detail_id)) {
				PageData query = new PageData();
				query.put("notice_detail_id", notice_detail_id);
				List<PageData> results = inboundnoticestockbatchService.listAll(query);
				if (results == null || results.size() == 0) {
					throw new Exception("存在缺少批次的商品");
				}
			}
		}
		
		int store_id=Integer.parseInt(pdStore.getString("store_id"));
		String store_name = pdStore.getString("store_name");
		String store_sn =  pdStore.getString("store_sn");
		String quality_store_id = pdStore.getString("quality_store_id");
		if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
			throw new Exception("未关联质检仓");
		}
		PageData queryStore = new PageData();
		queryStore.put("store_id", quality_store_id);
		PageData store_data = szystoreService.findById(queryStore);
		String quality_store_sn = store_data.getString("store_sn");
		String quality_store_name = store_data.getString("store_name");
		PageData totalStoreDB = szystoreService.findTotalStore(new PageData());
		if(totalStoreDB == null) {
			throw new Exception("找不到生产采购入库的总仓");
		}
		String total_store_sn = totalStoreDB.getString("store_sn");
		String total_store_name = totalStoreDB.getString("store_name");
		int total_store_id = Integer.parseInt(totalStoreDB.getString("store_id"));
		
		
		if (inboundGood.size() > 0) {
			//出待检仓 进总仓 出总仓 进良品仓
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_GOOD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_GOOD;
			}
			String quality_outbound_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code);
			String inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, inbound_code_sum);
			String outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
					total_store_name, outbound_code_sum);
			String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, store_sn,
					store_name, inbound_code);

			for (PageData resultDetail : inboundGood) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString();
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Object gq = resultDetail.get("good_quantity");
				String inbound_quantity = gq.toString();
				//出待检仓(无批次) 进总仓(带批次) 出总仓(带批次) 进良品仓(带批次)
				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
				inboundBatch(notice_detail_id, inbound_quantity, inboundDetail, total_store_id);
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code,
						purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
				outboundBatch(notice_detail_id, inbound_quantity, outboundDetail, total_store_id);
				PageData inboundDetailStore = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundBatch(notice_detail_id, inbound_quantity, inboundDetailStore, store_id);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			}
		}
		
		if(allBad.size() > 0) {
			String bad_store_id = pdStore.getString("bad_store_id");
			if(bad_store_id==null || "".equals(bad_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联不良品仓");
			}
			PageData queryBadStore = new PageData();
			queryBadStore.put("store_id", bad_store_id);
			PageData bad_store_data = szystoreService.findById(queryBadStore);
			String bad_store_sn = bad_store_data.getString("store_sn");
			String bad_store_name = bad_store_data.getString("store_name");
			
			String inbound_code_sum = null;
			String outbound_code_sum = null;
			String inbound_code = null;
			//出待检仓(无批次) 进总仓(无批次) 出总仓(无批次) 进不良品仓(无批次)
			Integer outType = InOutBoundType.PURCHASE_OUTBOUND_QUALITY;
			Integer inTypeSum = InOutBoundType.PURCHASE_INBOUND_CENTERWARE;
			Integer outTypeSum = InOutBoundType.PURCHASE_OUTBOUND_CENTERWARE;
			Integer intType = InOutBoundType.PURCHASE_INBOUND_BAD;
			if("2".equals(bill_type)){
				outType = InOutBoundType.PRODUCTION_OUTBOUND_QUALITY;
				inTypeSum = InOutBoundType.PRODUCTION_INBOUND_CENTERWARE;
				outTypeSum = InOutBoundType.PRODUCTION_OUTBOUND_CENTERWARE;
				intType = InOutBoundType.PRODUCTION_INBOUND_BAD;
			}
			String quality_outbound_code_bad = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, purchase_detail_id, quality_store_sn,
					quality_store_name, quality_outbound_code_bad);
			if(inboundBad.size() > 0) {
				inbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				outbound_code_sum = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
				inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
				this.saveInbound(user, inbound_notice_code, inTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, inbound_code_sum);
				this.saveOutbound(user, inbound_notice_code, outTypeSum.toString(), purchase_code, purchase_detail_id, total_store_sn,
						total_store_name, outbound_code_sum);
				this.saveInbound(user, inbound_notice_code, intType.toString(), purchase_code, purchase_detail_id, bad_store_sn, bad_store_name,
						inbound_code);
			}
			
			for (PageData resultDetail : allBad) {
				String notice_detail_id = resultDetail.getString("notice_detail_id");
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				Integer bad_quantity = (Integer) resultDetail.get("bad_quantity");
				Integer bad_deal_type = (Integer) resultDetail.get("bad_deal_type");
				String inbound_quantity = bad_quantity.toString();// 6入库数量

				saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
						sku_id, sku_name, sku_encode, spec, inbound_quantity, quality_outbound_code_bad);
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 1);
				if (bad_deal_type == 1) {
					saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code_sum);
					saveOutboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, outbound_code_sum);
					saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id,
							sku_id, sku_name, sku_encode, spec, inbound_quantity, inbound_code);
					szystockgoodsService.quantityModify(new Double(inbound_quantity), bad_store_sn, new Integer(sku_id), 0);
				}
			}
		}
	}
	
	/**
	  * 非质检类型处理
	  * */
	private void bussionHandler(String inbound_notice_code, String in_code, User user, String bill_type,
			String purchase_code, String purchase_detail_id, PageData pdStore) throws Exception {
		
		String[] strArray = in_code.split("__");
		if(strArray.length<1 || "".equals(in_code)) {
			throw new Exception("请选中要入库的商品");
		}
		
		int store_id=Integer.parseInt(pdStore.getString("store_id"));
		
		String store_name = pdStore.getString("store_name");
		String store_sn =  pdStore.getString("store_sn");
		Integer inType = InOutBoundType.CUSTOMER_INBOUND_RETURN;
		if("4".equals(bill_type)) {
			inType = InOutBoundType.CUSTOMER_INBOUND_RETURN;
		} 
		if("5".equals(bill_type)) {
			inType = InOutBoundType.LINGLIAO_INBOUND_RETURN;
				}
		if("6".equals(bill_type)) {
			inType = InOutBoundType.ORDER_INBOUND_REJECT;
		}
		if("7".equals(bill_type)) {
			inType = InOutBoundType.OTHER_INSTORAGE_INBOUND;
		}

		// 入库单
		String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
		this.saveInbound(user, inbound_notice_code, inType.toString(), purchase_code, purchase_detail_id, store_sn, store_name,
				inbound_code);

		for (String str : strArray) {
			String notice_detail_id = str;
			
			PageData queryNoticeDetail = new PageData();
			queryNoticeDetail.put("notice_detail_id", notice_detail_id);
			PageData resultDetail = inboundnoticedetailService.findById(queryNoticeDetail);
			String status = resultDetail.getString("status");
			if ("9".equals(status)) {
				throw new Exception("存在已入库的明细");
			}
			String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
			String sku_name = resultDetail.getString("sku_name");
			String sku_encode = resultDetail.getString("sku_encode");
			String spec = resultDetail.getString("spec");
			purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
			Object gq = resultDetail.get("good_quantity");
			String inbound_quantity =  gq.toString();// 6入库数量
			//入库单明细
			PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id, sku_id,
					sku_name, sku_encode,spec, inbound_quantity, inbound_code);
			//增加批次库存
			inboundBatch(notice_detail_id, inbound_quantity, inboundDetail, store_id);
			// 回写领料单
			if ("5".equals(bill_type)) {
				inbound_quantity = resultDetail.get("pre_arrival_quantity").toString();
				purchasematerialService.callbackMaterial(Integer.valueOf(purchase_detail_id), Integer.valueOf(inbound_quantity), user.getUSER_ID(), new Date());
			}
			// 增加分仓库存
			szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			{//更新入库状态
				PageData pd1 = new PageData();
				pd1.put("notice_detail_id", Integer.valueOf(notice_detail_id));
				//已入库
				pd1.put("status", 9);
				pd1.put("updateby", user.getUSER_ID());
				pd1.put("update_time", new Date());
				inboundnoticedetailService.updateState(pd1);
			}
		}
		//如果是其他入库，回写其他入库状态为9:已完成
		//2017-11-13 其他入库修改未 入库完成时修改状态。
		/*if("7".equals(bill_type)) {
			PageData pd = new PageData();
			pd.put("other_instorage_code", purchase_code);
			pd.put("status", 9);
			pd.put("updateby", user.getUSER_ID());
			pd.put("update_time", Tools.date2Str(new Date()));
			otherinstorageService.updateStatus(pd);
		}*/
	}

	//审批后 生成发货单
		public void saveDirectSales(String direct_sales_code, User user) throws Exception {
			PageData queryDirect = new PageData();
			queryDirect.put("direct_sales_code", direct_sales_code);
			PageData direct_sales = directsalesService.findByCode(queryDirect);
				
			String outbound_notice_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
			Object store_sn = direct_sales.get("store_sn");
			Object store_name = direct_sales.get("store_name");
			// 审批时间

			PageData outbound_notice = new PageData();
			outbound_notice.put("outbound_notice_code", outbound_notice_code);
			outbound_notice.put("store_sn", store_sn);
			outbound_notice.put("store_name", store_name);
			outbound_notice.put("bill_type", 4);
			outbound_notice.put("purchase_code", direct_sales_code);
			outbound_notice.put("pre_send_time", new Date());
			outbound_notice.put("status", 0);
			outbound_notice.put("createby", user.getUSER_ID()); // 更新人
			outbound_notice.put("create_time", new Date()); // 更新时间
			outbound_notice.put("updateby", user.getUSER_ID()); // 更新人
			outbound_notice.put("update_time", new Date()); // 更新时间
			outboundnoticeService.save(outbound_notice);
			
			List<PageData> list = directsalesdetailService.listByCode(queryDirect);
			for(PageData pageData: list){
				PageData pd1 = new PageData();
				pd1.put("outbound_notice_code", outbound_notice_code);
				pd1.put("purchase_code", direct_sales_code);
				Object direct_sales_detail_id = pageData.get("direct_sales_detail_id");
				pd1.put("purchase_detail_id", direct_sales_detail_id);
				Object sku_id = pageData.get("sku_id");
				pd1.put("sku_id", sku_id);
				Object sku_name = pageData.get("sku_name");
				pd1.put("sku_name", sku_name);
				pd1.put("sku_encode", pageData.get("sku_encode"));
				pd1.put("spec", pageData.get("spec"));
				Object quantity = pageData.get("quantity");
				pd1.put("pre_send_quantity", quantity);
				pd1.put("send_quantity", 0);
				pd1.put("status", 0);
				pd1.put("createby", user.getUSER_ID());	//更新人
				pd1.put("create_time", new Date());	//更新时间
				pd1.put("updateby", user.getUSER_ID());	//更新人
				pd1.put("update_time", new Date());	//更新时间
				outboundnoticedetailService.save(pd1);
			}
		}

		/**
		 * 2017-10-09 修改到货逻辑
		 * */
		@Override
		@Deprecated
		public void saveQualityStoreHandler(PageData pd, String inbound_notice_code) throws Exception {
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			
			PageData queryInboundNotice = new PageData();
			queryInboundNotice.put("inbound_notice_code", inbound_notice_code);
			PageData resultInbound = inboundnoticeService.findByCode(queryInboundNotice);
			String bill_type = resultInbound.get("bill_type").toString();
			
			//String store_name = (String) resultInbound.get("store_name");
			String query_sn = resultInbound.get("store_sn").toString();
			String purchase_code = resultInbound.getString("purchase_code");
			
			PageData queryStore = new PageData();
			queryStore.put("store_sn", query_sn);
			PageData resultStore = szystoreService.findByStoreSn(queryStore);
			
			
			switch(bill_type) {
			case "1":
				//采购到货处理 待检库（采购待检入库）
				receivedHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore);
				break;
			case "2":
				//采购到货处理 待检库（生产待检入库）
				receivedHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore);
				break;
			case "3":
				//调拨到货处理 	物流库（调拨物流出库）--待检库（调拨待检入库）
				allocationAndrejectHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore,
						InOutBoundType.ALLOCATION_OUTBOUND_LOGISTIC, 
						InOutBoundType.ALLOCATION_INBOUND_QUALITY);
				break;
			case "6":
				//拒收到货处理	 物流库（拒收出库）--待检库（拒收待检入库）
				allocationAndrejectHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore,
						InOutBoundType.ORDER_OUTBOUND_REJECT, 
						InOutBoundType.ORDER_INBOUND_REJECT_QUALITY);
				break;
				default:
			}
			
			pd.put("status", 1);
			pd.put("arrival_time", new Date());
			pd.put("updateby", user.getUSER_ID());
			pd.put("update_time", new Date());
			inboundnoticeService.update(pd);
			
			updateStatus(inbound_notice_code, bill_type);
		}
		
		/**
		 * --2017-10-09
		 * --更新到货状态
		 * --点击到货不如待检仓、只更新状态
		 * */
		public void saveArrivedHandler(PageData pd, String inbound_notice_code) throws Exception {
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			
			PageData queryInboundNotice = new PageData();
			queryInboundNotice.put("inbound_notice_code", inbound_notice_code);
			PageData resultInbound = inboundnoticeService.findByCode(queryInboundNotice);
			String status = resultInbound.getString("status");
			if ("1".equals(status)) {
				throw new Exception("此到货通知单已到货");
			}
			String bill_type = resultInbound.get("bill_type").toString();
			//String store_name = (String) resultInbound.get("store_name");
			String query_sn = resultInbound.get("store_sn").toString();
			String purchase_code = resultInbound.getString("purchase_code");
			
			PageData queryStore = new PageData();
			queryStore.put("store_sn", query_sn);
			PageData resultStore = szystoreService.findByStoreSn(queryStore);
			
			
			switch(bill_type) {
			case "3":
				//调拨到货处理 	物流库（调拨物流出库）--待检库（调拨待检入库）
				allocationAndrejectHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore,
						InOutBoundType.ALLOCATION_OUTBOUND_LOGISTIC, 
						InOutBoundType.ALLOCATION_INBOUND_QUALITY);
				break;
			case "6":
				//拒收到货处理	 物流库（拒收出库）--待检库（拒收待检入库）
				allocationAndrejectHandler(inbound_notice_code, user, purchase_code, bill_type, resultStore,
						InOutBoundType.ORDER_OUTBOUND_REJECT, 
						InOutBoundType.ORDER_INBOUND_REJECT_QUALITY);
				break;
				default:
			}
			pd.put("status", 1);
			pd.put("arrival_time", new Date());
			pd.put("updateby", user.getUSER_ID());
			pd.put("update_time", new Date());
			inboundnoticeService.update(pd);
			
			updateStatus(inbound_notice_code, bill_type);
		}
		
		/**
		 * 根据单子类型，统一修改各表数据状态
		 * @param bill_type
		 * @throws Exception 
		 */
		private void updateStatus(String inbound_notice_code, String bill_type) throws Exception {
			/**
			 * 到货：
			 *     1-采购订单 2-生产订单 ：入库通知单-已到货，入库通知明细单-已到货
			 *     其他订单 ：入库通知单-已到货，入库通知明细单-已质检
			 */
			if(StringUtils.isNotEmpty(inbound_notice_code) && StringUtils.isNotEmpty(bill_type)) {
				PageData pd = new PageData();
				pd.put("inbound_notice_code", inbound_notice_code);
				List<PageData> inboundNoticeDetails = inboundnoticedetailService.findlistByNoticeCode(pd);
				if(CollectionUtils.isNotEmpty(inboundNoticeDetails)) {
					PageData returnPd = new PageData();
					List<PageData> pds = new ArrayList<PageData>();
					if("1".equals(bill_type) || "2".equals(bill_type)) {
						//已到货
						returnPd.put("status", 1);
					    for (PageData inboundNoticeDetail : inboundNoticeDetails) {
					    	PageData pageData = new PageData();
					    	pageData.put("notice_detail_id", inboundNoticeDetail.get("notice_detail_id"));
					    	pds.add(pageData);
						}	
					    returnPd.put("list", pds);
					}else {
						//已质检
						returnPd.put("status", 3);
						for (PageData inboundNoticeDetail : inboundNoticeDetails) {
							PageData pageData = new PageData();
					    	pageData.put("notice_detail_id", inboundNoticeDetail.get("notice_detail_id"));
					    	pds.add(pageData);
						}
						returnPd.put("list", pds);
					}
					if(CollectionUtils.isNotEmpty(pds)) {
						inboundnoticedetailService.updateStatus(returnPd);
					}
				}
			}
		}
		
		//调拨到货处理
		private void allocationAndrejectHandler(String inbound_notice_code, User user, String purchase_code, String bill_typ,
				PageData resultStore, Integer outType, Integer inType) throws Exception {
			PageData allocationQuery = new PageData();
			allocationQuery.put("allocation_code", purchase_code);
			PageData allocation = allocationService.findByCode(allocationQuery);
			
			PageData from_store = resultStore;
			if("3".equals(bill_typ)) {
				String from_store_sn = allocation.getString("from_store_sn");
				PageData queryStore = new PageData();
				queryStore.put("store_sn", from_store_sn);
				from_store = szystoreService.findByStoreSn(queryStore);
				allocationQuery = new PageData();
				allocationQuery.put("allocation_code", purchase_code);
				allocationQuery.put("updateby", user.getUSER_ID());
				allocationService.updateReceiveTime(allocationQuery);
			}
			
			
			String quality_store_id = resultStore.getString("quality_store_id");
			if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联质检仓");
			}
			String logistic_store_id = from_store.getString("logistic_store_id");
			if(logistic_store_id==null || "".equals(logistic_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联物流仓");
			}
			PageData logistic = new PageData();
			logistic.put("store_id", logistic_store_id);
			//物流出库
			String outbound_code = null;
			PageData logistic_store = szystoreService.findById(logistic);
			String logistic_store_sn = logistic_store.getString("store_sn");
			String logistic_store_name = logistic_store.getString("store_name");
			if("3".equals(bill_typ)) {
			outbound_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
			this.saveOutbound(user, inbound_notice_code, outType.toString(), purchase_code, null, logistic_store_sn,
					logistic_store_name, outbound_code);
			}
			//质检入库

			PageData quality = new PageData();
			quality.put("store_id", quality_store_id);
			PageData quality_store = szystoreService.findById(quality);
			String quality_store_sn = quality_store.getString("store_sn");
			String quality_store_name = quality_store.getString("store_name");
			String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			this.saveInbound(user, inbound_notice_code, inType.toString(), purchase_code, null, quality_store_sn,
					quality_store_name, inbound_code);
			
			PageData queryInboundNoticeDetail = new PageData();
			queryInboundNoticeDetail.put("inbound_notice_code", inbound_notice_code);
			List<PageData> varList = inboundnoticedetailService.listByWhere(queryInboundNoticeDetail);
			for (PageData resultDetail : varList) {
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				String purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				//Object gq = resultDetail.get("good_quantity");
				Object pre_arrival_quantity = resultDetail.get("pre_arrival_quantity");
				String inbound_quantity =  pre_arrival_quantity.toString();
				String notice_detail_id = resultDetail.get("notice_detail_id").toString();
				if("3".equals(bill_typ)) {
				PageData outboundDetail = saveOutboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						outbound_code);
				outboundBatch(notice_detail_id, inbound_quantity, outboundDetail, Integer.parseInt(logistic_store_id));				
				szystockgoodsService.quantityModify(new Double(inbound_quantity), logistic_store_sn, new Integer(sku_id), 1);
				}
				PageData inboundDetail = saveInboundDetail(user, inbound_notice_code, notice_detail_id,
						purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec, inbound_quantity,
						inbound_code);
				inboundBatch(notice_detail_id, inbound_quantity, inboundDetail, Integer.parseInt(quality_store_id));
				szystockgoodsService.quantityModify(new Double(inbound_quantity), quality_store_sn, new Integer(sku_id), 0);
			}
		}
		
		private void receivedHandler(String inbound_notice_code, User user, String purchase_code, String bill_type,
				PageData resultStore) throws Exception {
			String quality_store_id = resultStore.getString("quality_store_id");
			if(quality_store_id==null || "".equals(quality_store_id.trim()) || "0".equals(quality_store_id.trim())) {
				throw new Exception("未关联质检仓");
			}
			Integer intype = InOutBoundType.PURCHASE_INBOUND_QUALITY;
			if("2".equals(bill_type)) {
				intype = InOutBoundType.PRODUCTION_INBOUND_QUALITY;
			}
			
			PageData queryStore2 = new PageData();
			queryStore2.put("store_id", quality_store_id);
			PageData store_data = szystoreService.findById(queryStore2);
			String store_sn = store_data.getString("store_sn");
			String store_name = store_data.getString("store_name");
			
			String inbound_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_CODE);
			saveInbound(user, inbound_notice_code, intype.toString(), purchase_code, null, store_sn, store_name, inbound_code);

			PageData queryInboundNoticeDetail = new PageData();
			queryInboundNoticeDetail.put("inbound_notice_code", inbound_notice_code);
			List<PageData> varList = inboundnoticedetailService.listByWhere(queryInboundNoticeDetail);
			
			for (PageData resultDetail : varList) {
				String sku_id = resultDetail.get("sku_id").toString(); // 4商品id
				String sku_name = resultDetail.getString("sku_name");
				String sku_encode = resultDetail.getString("sku_encode");
				String spec = resultDetail.getString("spec");
				String purchase_detail_id = resultDetail.get("purchase_detail_id").toString();
				//Object gq = resultDetail.get("good_quantity");
				Object pre_arrival_quantity = resultDetail.get("pre_arrival_quantity");
				String inbound_quantity =  pre_arrival_quantity.toString();
				String notice_detail_id = resultDetail.get("notice_detail_id").toString();
				
				saveInboundDetail(user, inbound_notice_code, notice_detail_id, purchase_code, purchase_detail_id, sku_id,
						sku_name, sku_encode,spec, inbound_quantity, inbound_code);
				// 增加分仓库存
				szystockgoodsService.quantityModify(new Double(inbound_quantity), store_sn, new Integer(sku_id), 0);
			}
		}
		public void saveQuality(String basepath, HttpServletRequest request, PageData pageData) throws Exception {
			// 生成质检单
			String purchase_detail_id = request.getParameter("purchase_detail_id");
			String type = request.getParameter("type");
			String purchase_code = request.getParameter("purchase_code");
			String quality_check_id = null;
			{
				PageData pd = new PageData();
				pd = pageData;
				pd.put("purchase_code", purchase_code); // 业务数据编码
				pd.put("purchase_detail_id", purchase_detail_id); // 业务数据编码
				pd.put("type", type); // 质检类型
				pd.put("status", 1); // 已质检
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				pd.put("createby", user.getUSER_ID()); // 创建人
				pd.put("create_time", Tools.date2Str(new Date())); // 创建时间
				pd.put("updateby", user.getUSER_ID()); // 更新人
				pd.put("update_time", Tools.date2Str(new Date())); // 更新时间
				qualitycheckService.save(pd);
				quality_check_id = String.valueOf(pd.get("quality_check_id"));
			}
			if (null == quality_check_id) {
				return ;
			}
			// 添加质检文件
			{
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				String parentPath = basepath + quality_check_id + "/";
				File dir = new File(parentPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				while (iter.hasNext()) {
					try {
						MultipartFile file = multiRequest.getFile(iter.next());
						if (null != file) {
							String fileName = file.getOriginalFilename();
							if (null != fileName && fileName.trim().length() > 0) {
								String dest = parentPath + fileName;
								File file2 = new File(dest);
								if (file2.exists()) {
									if (fileName.contains(".")) {
										int index = fileName.lastIndexOf(".");
										fileName = fileName.substring(0, index) + System.currentTimeMillis() + "."
												+ fileName.substring(index + 1);
										file2 = new File(parentPath + fileName);
									}
								}
								file.transferTo(file2);
								savePageData(quality_check_id, fileName);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 更新质检数据
			String good_quantity = request.getParameter("good_quantity");
			String bad_quantity = request.getParameter("bad_quantity");
			String bad_deal_type = request.getParameter("bad_deal_type");
			String notice_detail_id = request.getParameter("notice_detail_id");
			
			PageData pd = new PageData();

			pd.put("notice_detail_id", notice_detail_id);
			pd.put("good_quantity", good_quantity);
			pd.put("bad_quantity", bad_quantity);
			pd.put("bad_deal_type", bad_deal_type);
			pd.put("quality_status", 1);
			pd.put("quality_time", new Date());
			pd.put("quality_id", quality_check_id);

			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			pd.put("updateby", user.getUSER_ID());
			pd.put("update_time", new Date());
			inboundnoticedetailService.edit(pd);
		}
		
		private void savePageData(String quality_check_id, String url) throws Exception {
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			PageData pd = new PageData();
			// pd = this.getPageData();
			pd.put("quality_check_id", quality_check_id);
			pd.put("createby", user.getUSER_ID()); // 创建人
			pd.put("create_time", Tools.date2Str(new Date())); // 创建时间
			pd.put("updateby", user.getUSER_ID()); // 更新人
			pd.put("update_time", Tools.date2Str(new Date())); // 更新时间
			pd.put("url", url);
			qualitycheckfileService.save(pd);
		}
		
		public Map<String, String> saveFinishInboundHandler(Map<String, String> map, PageData pd) throws Exception {
			String bill_type = pd.getString("bill_type");
			String inbound_code = pd.getString("inbound_code");
			PageData queryDetail = new PageData();
			queryDetail.put("inbound_notice_code", inbound_code);
			List<PageData> results = inboundnoticedetailService.findlistByNoticeCode(queryDetail);
			for(PageData data : results) {
				if(!"9".equals(data.getString("status")) && 
						!("3".equals(data.getString("status")) &&( "1".equals(bill_type) || "2".equals(bill_type)))) {
					map.put("info", "明细操作未完成！");
					return map;
				}else {
					PageData pageData = new PageData();
					pageData.put("notice_detail_id", data.get("notice_detail_id"));
					pageData.put("inbound_notice_code", inbound_code);
					List<PageData> insbs = inboundnoticestockbatchService.listAll(pageData);
					for (PageData insb : insbs) {
						if(!"2".equals(insb.getString("status"))) {
							map.put("info", "批次操作未完成！");
							return map;
						}else {
							PageData icdPd = new PageData();
							icdPd.put("inbound_notice_stock_batch_id", insb.get("inbound_notice_stock_batch_id"));
							List<PageData> icds = inboundcheckdetailService.listAll(icdPd);
							for (PageData icd : icds) {
								if(!"2".equals(icd.getString("status"))) {
									map.put("info", "质检或入库操作未完成！");
									return map;
								}
							}
						}
					}
				}
			}
			pd.put("status", 9);
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER); 
			pd.put("updateby", user.getUSER_ID());
			pd.put("update_time", new Date());
			inboundnoticeService.update(pd);
			//回写生产和采购
			if ("1".equals(bill_type) || "2".equals(bill_type)) {
				pd = new PageData();
				pd.put("inbound_notice_code", inbound_code);
				PageData inboundnotice = inboundnoticeService.findByCode(pd);
				
				PageData update = new PageData();
				update.put("inbound_pre_notice_code", inboundnotice.get("purchase_code"));
				update.put("status", 9);
				update.put("updateby", user.getUSER_ID());	//更新人
				update.put("update_time", dayformat.format(new Date()));	//更新时间
				inboundprenoticeService.edit(update);
			}
			//回写调拨入库
			if ("3".equals(bill_type)) {
				pd = new PageData();
				pd.put("inbound_notice_code", inbound_code);
				PageData inboundnotice = inboundnoticeService.findByCode(pd);
				PageData update = new PageData();
				update.put("allocation_code", inboundnotice.get("purchase_code"));
				update.put("status", 9);
				update.put("updateby", user.getUSER_ID());	//更新人
				update.put("update_time", dayformat.format(new Date()));	//更新时间
				allocationService.updateStatusWithIn(update);
			}
			//回写其他入库
			if ("7".equals(bill_type)) {
				pd = new PageData();
				pd.put("inbound_notice_code", inbound_code);
				PageData inboundnotice = inboundnoticeService.findByCode(pd);
				PageData update = new PageData();
				update.put("other_instorage_code", inboundnotice.get("purchase_code"));
				update.put("status", 9);
				update.put("updateby", user.getUSER_ID());	//更新人
				update.put("update_time", dayformat.format(new Date()));	//更新时间
				otherinstorageService.updateStatus(update);
			}
			map.put("msg", "success");
			return map;
		}
}

