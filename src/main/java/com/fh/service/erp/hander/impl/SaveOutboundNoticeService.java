package com.fh.service.erp.hander.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.fh.common.DefetiveConstants;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.outboundnoticestockbatch.OutboundNoticeStockBatchManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.defectivegoods.DefectiveGoodsManager;
import com.fh.service.erp.defectivegoodsdetail.DefectiveGoodsDetailManager;
import com.fh.service.erp.hander.SaveOutboundNoticeManager;
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
@Service("saveoutboundnoticeservice")
public class SaveOutboundNoticeService implements SaveOutboundNoticeManager{
	
	@Resource(name="defectivegoodsService")
	private DefectiveGoodsManager defectivegoodsService;
	@Resource(name="defectivegoodsdetailService")
	private DefectiveGoodsDetailManager defectivegoodsdetailService;
	@Resource(name="outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailManager outboundnoticedetailService;
	@Resource(name="outboundnoticestockbatchService")
	private OutboundNoticeStockBatchManager outboundNoticeStockBatchService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	
	//审核通过
	public void saveCompleteDefective(PageData pd)throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//1 获取不良品审批单
		PageData defective = defectivegoodsService.findById(pd);
		//1.1检查是否已通过审批
		Integer status = (Integer) defective.get("status");
		if (status == null || status != DefetiveConstants.BUSINESS_STATUS_SUBMIT) {
			throw new Exception("申请单不是待审状态，请核实！");
		}
		//1.2更新状态通过
		PageData updateEfective = new PageData();
		updateEfective.put("defective_id", defective.get("defective_id"));
		updateEfective.put("status", DefetiveConstants.BUSINESS_STATUS_AGREE);
		updateEfective.put("auditby", user.getUSER_ID());
		updateEfective.put("audit_time", new Date());
		updateEfective.put("updateby", user.getUSER_ID());
		updateEfective.put("update_time", new Date());
		defectivegoodsService.updateCheckStatus(updateEfective);
		
		//2.获取不良品审批单明细
		PageData queryDefectiveDetail = new PageData();
		queryDefectiveDetail.put("defective_code", defective.get("defective_code"));
		List<PageData> defectiveDetails = defectivegoodsdetailService.listByCode(queryDefectiveDetail);
		//2.1合并相同的商品
		Map<String, List<PageData>> defectiveDetailMap = new HashMap<String, List<PageData>>();
		Map<String, Integer> defectiveNumberMap = new HashMap<String, Integer>();
		for(PageData pageData: defectiveDetails){
			String sku_id = pageData.getString("sku_id");
			Integer defective_quantity = (Integer) pageData.get("defective_quantity");
			if(0 == defective_quantity) continue;
			List<PageData> pds = defectiveDetailMap.get(sku_id);
			Integer number = defectiveNumberMap.get(sku_id);
			if (pds == null) pds = new ArrayList<PageData>();
			if (number == null) number = 0;
			pds.add(pageData);
			defectiveDetailMap.put(sku_id, pds);
			number += defective_quantity;
			defectiveNumberMap.put(sku_id, number);
		}
		if (defectiveNumberMap.size() == 0) {
			throw new Exception("请核实申请的不良品数量！");
		}
		//3.1生成出库通知单
		String outbound_notice_code = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_NOTICE_CODE);
		PageData outbound_notice = new PageData();
		outbound_notice.put("outbound_notice_code", outbound_notice_code);
		outbound_notice.put("store_sn", defective.get("store_sn"));
		outbound_notice.put("store_name", defective.get("store_name"));
		outbound_notice.put("bill_type", 7);
		outbound_notice.put("purchase_code", defective.get("defective_code"));
		outbound_notice.put("pre_send_time", new Date());
		outbound_notice.put("status", 0);
		outbound_notice.put("createby", user.getUSER_ID()); // 更新人
		outbound_notice.put("create_time", new Date()); // 更新时间
		outbound_notice.put("updateby", user.getUSER_ID()); // 更新人
		outbound_notice.put("update_time", new Date()); // 更新时间
		outboundnoticeService.save(outbound_notice);
		//3.2生成出库通知单明细
		for(String sku_id : defectiveDetailMap.keySet()) {
			PageData pageData = defectiveDetailMap.get(sku_id).get(0);
			PageData outbound_notice_detail = new PageData();
			outbound_notice_detail.put("outbound_notice_code", outbound_notice_code);
			outbound_notice_detail.put("purchase_code", defective.get("defective_code"));
			outbound_notice_detail.put("purchase_detail_id", pageData.get("defective_detail_id"));
			outbound_notice_detail.put("sku_id", sku_id);
			outbound_notice_detail.put("sku_name", pageData.get("sku_name"));
			outbound_notice_detail.put("pre_send_quantity", defectiveNumberMap.get(sku_id));
			outbound_notice_detail.put("send_quantity", 0);
			outbound_notice_detail.put("status", 0);
			outbound_notice_detail.put("createby", user.getUSER_ID());	//更新人
			outbound_notice_detail.put("create_time", new Date());	//更新时间
			outbound_notice_detail.put("updateby", user.getUSER_ID());	//更新人
			outbound_notice_detail.put("update_time", new Date());	//更新时间
			outboundnoticedetailService.save(outbound_notice_detail);
			//3.3生产出库通知单批次明细
			for(PageData defectiveDetail: defectiveDetailMap.get(sku_id)) {
				PageData outboundNoticeStockBatch = new PageData();
				outboundNoticeStockBatch.put("notice_detail_id", outbound_notice_detail.getString("outbound_notice_detail_id"));
				outboundNoticeStockBatch.put("outbound_notice_code", outbound_notice_code);
				outboundNoticeStockBatch.put("sku_id", defectiveDetail.getString("sku_id"));
				outboundNoticeStockBatch.put("sku_name", defectiveDetail.getString("sku_name"));
				outboundNoticeStockBatch.put("sku_encode", defectiveDetail.getString("sku_encode"));
				outboundNoticeStockBatch.put("batch_code", defectiveDetail.getString("batch_code"));
				outboundNoticeStockBatch.put("quantity", defectiveDetail.getString("defective_quantity"));
				outboundNoticeStockBatch.put("spec", defectiveDetail.getString("spec"));
				outboundNoticeStockBatch.put("unit_name", defectiveDetail.getString("unit"));
				outboundNoticeStockBatch.put("status", 0);
				outboundNoticeStockBatch.put("createby", user.getUSER_ID());	//更新人
				outboundNoticeStockBatch.put("create_time", new Date());	//更新时间
				outboundNoticeStockBatch.put("updateby", user.getUSER_ID());	//更新人
				outboundNoticeStockBatch.put("update_time", new Date());	//更新时间
				outboundNoticeStockBatchService.save(outboundNoticeStockBatch);
			}
		}
	}
}

