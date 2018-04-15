package com.fh.service.erp.hander.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.fh.common.InboundNoticeConstants;
import com.fh.entity.system.User;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.outboundnoticestockbatch.OutboundNoticeStockBatchManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.purchasematerial.PurchaseMaterialManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.hander.MaterialCommitManager;
import com.fh.service.erp.inboundnotice.InboundNoticeManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.service.erp.purchasematerialcommit.PurchaseMaterialCommitManager;
import com.fh.service.erp.purchasematerialdetail.PurchaseMaterialDetailManager;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明： 入库单管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-09
 * @version
 */
@Service("materialcommitService")
public class MaterialCommitService implements MaterialCommitManager{
	
	
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	@Resource(name="outboundnoticestockbatchService")
	private OutboundNoticeStockBatchManager outboundNoticeStockBatchService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	
	@Resource(name = "purchasematerialService")
	private PurchaseMaterialManager purchasematerialService;
	@Resource(name="purchasematerialcommitService")
	private PurchaseMaterialCommitManager purchasematerialcommitService;
	@Resource(name="purchasematerialdetailService")
	private PurchaseMaterialDetailManager purchasematerialdetailService;
	
	@Resource(name="purchasedetailService")
	private PurchaseDetailManager purchasedetailService;
	
	@Resource(name="inboundnoticeService")
	private InboundNoticeManager inboundnoticeService;
	@Resource(name="inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	
	@Override
	public void saveCommit(PageData pd) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); 
		Object purchase_code = pd.get("purchase_code");
		Object purchase_detail_id = pd.get("purchase_detail_id");
		PageData pdQuery = new PageData();
		pdQuery.put("detail_id", purchase_detail_id);
		PageData pdResult =  purchasedetailService.findById(pdQuery);
		Object purchase_detail_sku_id = pdResult.get("sku_id");
		Object purchase_detail_sku_name = pdResult.get("sku_name");
		//Object supplier_sn = pd.get("supplier_sn");
		String in_code = pd.getString("in_code");
		//查询物料提交明细的
		String[] strArray = in_code.split(",");
		Map<String, Map<String, PageData>> storeDetailMap = new HashMap<String, Map<String, PageData>>();
		for (String material_detail_id : strArray) {
			PageData queryCommit = new PageData();
			queryCommit.put("material_detail_id", material_detail_id);
			PageData commit = purchasematerialcommitService.findById(queryCommit);
			if (commit == null) {// || "1".equals(commit.getString("status"))
				throw new Exception("存在已提交的明细！");
			}
			String store_sn = commit.getString("store_sn");
			Map<String, PageData> detatilMap = storeDetailMap.get(store_sn);
			if (detatilMap == null) {
				detatilMap = new HashMap<>();
			}
			String sku_id = commit.getString("sku_id");
			PageData detail = detatilMap.get(sku_id);
			Integer quantity = (Integer) commit.get("quantity");
			if (detail != null) {
				quantity += (Integer) detail.get("quantity");
				commit.put("quantity", quantity);
			}
			detatilMap.put(sku_id, commit);
			storeDetailMap.put(store_sn, detatilMap);
			purchasematerialcommitService.delete(queryCommit);
		}
		
		for (String store_sn : storeDetailMap.keySet()) {
			Map<String, PageData> detatilMap = storeDetailMap.get(store_sn);
			String store_name = null;
			//增加物料明细
			for (PageData detail : detatilMap.values()) {
				store_name = detail.getString("store_name");
				String sku_id = detail.getString("sku_id");
				//明细若	if不存在此物料	插入	else存在	增加数量
				PageData queryDetail = new PageData();
				queryDetail.put("sku_id", sku_id);
				queryDetail.put("purchase_detail_id", purchase_detail_id);
				PageData result = purchasematerialService.findByMaterial(queryDetail);
				Object material_id = null;
				if (result != null) {
					material_id = result.get("material_id");
					PageData material = new PageData();
					material.put("material_id", result.get("material_id"));
					material.put("total_apply_quantity", detail.get("quantity"));
					material.put("createby", user.getUSER_ID()); // 更新人
					material.put("create_time", new Date()); // 更新时间
					purchasematerialService.increaseQuantity(material);
				} else {
					PageData material = new PageData();
					material.put("purchase_code", purchase_code);
					material.put("purchase_detail_id", purchase_detail_id);
					material.put("sku_id", detail.get("sku_id"));
					material.put("sku_name", detail.get("sku_name"));
					material.put("total_apply_quantity", detail.get("quantity"));
					material.put("total_quantity", 0);
					material.put("total_return_quantity", 0);
					material.put("createby", user.getUSER_ID()); // 更新人
					material.put("create_time", new Date()); // 更新时间
					material.put("updateby", user.getUSER_ID()); // 更新人
					material.put("update_time", new Date()); // 更新时间
					
					material.put("purchase_detail_sku_id", purchase_detail_sku_id);
					material.put("purchase_detail_sku_name", purchase_detail_sku_name);
					purchasematerialService.save(material);
					material_id = material.get("material_id");
				}
				detail.put("purchase_detail_id", material_id);
			}
			//生产到货通知单
			//3.1生成出库通知单
			String outbound_notice_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
			PageData outbound_notice = new PageData();
			outbound_notice.put("outbound_notice_code", outbound_notice_code);
			outbound_notice.put("store_sn", store_sn);
			outbound_notice.put("store_name", store_name);
			outbound_notice.put("bill_type", 0);
			outbound_notice.put("purchase_code", purchase_code);
			outbound_notice.put("pre_send_time", new Date());
			outbound_notice.put("status", 0);
			outbound_notice.put("createby", user.getUSER_ID()); // 更新人
			outbound_notice.put("create_time", new Date()); // 更新时间
			outbound_notice.put("updateby", user.getUSER_ID()); // 更新人
			outbound_notice.put("update_time", new Date()); // 更新时间
			outboundnoticeService.save(outbound_notice);
			//3.2生成出库通知单明细
			for(PageData detail : detatilMap.values()) {
				PageData outbound_notice_detail = new PageData();
				outbound_notice_detail.put("outbound_notice_code", outbound_notice_code);
				outbound_notice_detail.put("purchase_code", purchase_code);
				outbound_notice_detail.put("purchase_detail_id", detail.get("purchase_detail_id"));
				outbound_notice_detail.put("sku_id", detail.get("sku_id"));
				outbound_notice_detail.put("sku_name", detail.get("sku_name"));
				outbound_notice_detail.put("pre_send_quantity", detail.get("quantity"));
				outbound_notice_detail.put("send_quantity", 0);
				outbound_notice_detail.put("status", 0);
				outbound_notice_detail.put("createby", user.getUSER_ID());	//更新人
				outbound_notice_detail.put("create_time", new Date());	//更新时间
				outbound_notice_detail.put("updateby", user.getUSER_ID());	//更新人
				outbound_notice_detail.put("update_time", new Date());	//更新时间
				outboundnoticedetailService.save(outbound_notice_detail);
			}
		}
	}

	@Override
	public void saveReturnMaterial(PageData pd) throws Exception {
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); 
		Object purchase_code = pd.get("purchase_code");
		Object purchase_detail_id = pd.get("purchase_detail_id");
		
		String details = pd.getString("details");
		String[] array = details.split(";");
		Map<String, Map<String, List<PageData>>> storeDetailMap = new HashMap<String, Map<String, List<PageData>>>();
		Map<String, Map<String, Integer>> storeNumberMap = new HashMap<String, Map<String, Integer>>();
		//数据归并
		for (String detail : array) {
			String[] args = detail.split(",");
			String material_id = args[0];
			String return_quantity = args[1];
			PageData queryDetail = new PageData();
			queryDetail.put("material_detail_id", material_id);
			PageData queryResult =purchasematerialdetailService.findById(queryDetail);
			queryResult.put("return_quantity", Integer.valueOf(return_quantity));
			purchase_code = queryResult.get("purchase_code");
			purchase_detail_id = queryResult.get("purchase_detail_id");
			//减少批次数量quantity
			queryDetail = new PageData();
			queryDetail.put("material_detail_id", material_id);
			queryDetail.put("quantity", (0-Integer.valueOf(return_quantity)));
			queryDetail.put("updateby", user.getUSER_ID()); // 更新人
			queryDetail.put("update_time", new Date()); // 更新时间
			purchasematerialdetailService.increaseQuantity(queryDetail);
			//减少领取数量增加退中数量
			PageData updateDetail = new PageData();
			updateDetail.put("material_id", queryResult.get("purchase_material_id"));
			updateDetail.put("total_return_quantity", Integer.valueOf(return_quantity));
			updateDetail.put("total_quantity", (0-Integer.valueOf(return_quantity)));
			updateDetail.put("updateby", user.getUSER_ID()); // 更新人
			updateDetail.put("update_time", new Date()); // 更新时间
			purchasematerialService.increaseReturnAndTotal(updateDetail);
			
			String store_sn = queryResult.getString("store_sn");
			String sku_id = queryResult.getString("sku_id");
			Map<String, List<PageData>> storeMap = storeDetailMap.get(store_sn);
			if (storeMap == null) {
				storeMap = new HashMap<>();
			}
			List<PageData> materials = storeMap.get(sku_id);
			if (materials == null) {
				materials = new ArrayList<>();
			}
			materials.add(queryResult);
			storeMap.put(sku_id, materials);
			storeDetailMap.put(store_sn, storeMap);
			Map<String, Integer> numberMap = storeNumberMap.get(store_sn);
			if (numberMap == null) {
				numberMap = new HashMap<>();
			}
			Integer number = numberMap.get(sku_id);
			if (number == null) {
				number = 0;
			}
			number += Integer.valueOf(return_quantity);
			numberMap.put(sku_id, number);
			storeNumberMap.put(store_sn, numberMap);
		}
		
		//生产发货通知单
		for (String store_sn : storeDetailMap.keySet()) {
			Map<String, List<PageData>> detatilMap = storeDetailMap.get(store_sn);
			String store_name = null;
			//增加物料明细
			for (String sku_id : detatilMap.keySet()) {
				PageData material = detatilMap.get(sku_id).get(0);
				store_name = material.getString("store_name");
			}
			//生产到货通知单
			//3.1生成出库通知单
			String inbound_notice_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
			PageData inbound_notice = new PageData();
			inbound_notice.put("inbound_notice_code", inbound_notice_code);
			inbound_notice.put("store_sn", store_sn);
			inbound_notice.put("store_name", store_name);
			inbound_notice.put("bill_type", InboundNoticeConstants.TYPE_MATERIAL);
			inbound_notice.put("purchase_code", purchase_code);
			inbound_notice.put("purchase_detail_id", purchase_detail_id);
			inbound_notice.put("pre_arrival_time", new Date());
			inbound_notice.put("status", 0);
			inbound_notice.put("createby", user.getUSER_ID()); // 更新人
			inbound_notice.put("create_time", new Date()); // 更新时间
			inbound_notice.put("updateby", user.getUSER_ID()); // 更新人
			inbound_notice.put("update_time", new Date()); // 更新时间
			inboundnoticeService.save(inbound_notice);
			//3.2生成出库通知单明细
			for(String sku_id : detatilMap.keySet()) {
				List<PageData> materials = detatilMap.get(sku_id);
				PageData detail = materials.get(0);
				Integer pre_send_quantity = storeNumberMap.get(store_sn).get(sku_id);
				PageData inbound_notice_detail = new PageData();				
				inbound_notice_detail.put("inbound_code", inbound_notice_code);
				inbound_notice_detail.put("purchase_code", purchase_code);
				inbound_notice_detail.put("purchase_detail_id", detail.getString("purchase_material_id"));
				inbound_notice_detail.put("sku_id", detail.get("sku_id"));
				inbound_notice_detail.put("sku_name", detail.get("sku_name"));
				inbound_notice_detail.put("spec", detail.get("spec"));
				inbound_notice_detail.put("sku_encode", detail.get("sku_encode"));
				inbound_notice_detail.put("pre_arrival_quantity", pre_send_quantity);
				inbound_notice_detail.put("good_quantity", pre_send_quantity);
				inbound_notice_detail.put("bad_quantity", "0");
				inbound_notice_detail.put("status", 0);
				inbound_notice_detail.put("createby",user.getUSERNAME());
				inbound_notice_detail.put("updateby", user.getUSERNAME());
				inbound_notice_detail.put("create_time", new Date());
				inbound_notice_detail.put("update_time", new Date());
				inboundnoticedetailService.save(inbound_notice_detail);
				for (PageData pageData : materials) {
					PageData inbound_notice_batch = new PageData();
					inbound_notice_batch.put("inbound_notice_code",inbound_notice_code);
					inbound_notice_batch.put("notice_detail_id", inbound_notice_detail.get("notice_detail_id"));
					inbound_notice_batch.put("sku_id", pageData.get("sku_id"));
					inbound_notice_batch.put("sku_name", pageData.get("sku_name"));
					inbound_notice_batch.put("sku_encode", pageData.get("sku_encode"));
					inbound_notice_batch.put("batch_code", pageData.get("batch_code"));
					inbound_notice_batch.put("quantity",pageData.get("return_quantity"));
					inbound_notice_batch.put("spec", pageData.get("spec"));
					inbound_notice_batch.put("unit_name", pageData.get("unit"));
					inbound_notice_batch.put("status", 2);
					inbound_notice_batch.put("createby",user.getUSERNAME());
					inbound_notice_batch.put("updateby", user.getUSERNAME());
					inbound_notice_batch.put("create_time", new Date());
					inbound_notice_batch.put("update_time", new Date());
					inboundnoticestockbatchService.save(inbound_notice_batch);
				}
			}
		}
	}
}

