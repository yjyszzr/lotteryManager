package com.fh.controller.dst.allocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.dst.allocation.AllocationManager;
import com.fh.service.dst.allocationdetail.AllocationDetailManager;
import com.fh.service.dst.outbound.PgtOutboundManager;
import com.fh.service.dst.outbounddetail.PgtOutboundDetailManager;
import com.fh.service.dst.outboundstockbatch.OutboundStockBatchManager;
import com.fh.service.erp.inbound.InboundManager;
import com.fh.service.erp.inbounddetail.InboundDetailManager;
import com.fh.service.inboundstockbatch.InboundStockBatchManager;
import com.fh.util.BarcodeUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.MergeUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * 说明：调拨单列表 创建人：FH Q313596790 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value = "/queryallocation")
public class QueryAllocationController extends BaseController {

	String menuUrl = "queryallocation/list.do"; // 菜单地址(权限用)
	@Resource(name = "allocationService")
	private AllocationManager allocationService;
	@Resource(name = "allocationdetailService")
	private AllocationDetailManager allocationdetailService;

	@Resource(name = "inboundService")
	private InboundManager inboundService;
	@Resource(name = "inbounddetailService")
	private InboundDetailManager inbounddetailService;
	@Resource(name = "inboundstockbatchService")
	private InboundStockBatchManager inboundstockbatchService;
	@Resource(name = "pgtoutboundService")
	private PgtOutboundManager pgtoutboundService;
	@Resource(name = "pgtoutbounddetailService")
	private PgtOutboundDetailManager pgtoutbounddetailService;
	@Resource(name = "outboundstockbatchService")
	private OutboundStockBatchManager outboundstockbatchService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Allocation");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String[] types_out = { StoreTypeConstants.STORE_GOOD, StoreTypeConstants.STORE_BAD,
				StoreTypeConstants.STORE_LOSS, StoreTypeConstants.STORE_GIFT };
		List<PageData> list_out = GetOwnStore.getOwnStore(types_out);
		if (CollectionUtils.isNotEmpty(list_out)) {
			mv.addObject("storeList_out", list_out);
			if (pd.get("stores_out") != null) {
				pd.put("store_sn_out", pd.get("stores_out"));
			}
		} else {
			mv.setViewName("erp/query/queryallocation_list");
			mv.addObject("varList", "");
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			return mv;
		}
		String[] types_in = { StoreTypeConstants.STORE_GOOD, StoreTypeConstants.STORE_BAD,
				StoreTypeConstants.STORE_LOSS, StoreTypeConstants.STORE_GIFT };
		List<PageData> list_in = GetOwnStore.getOwnStore(types_in);
		if (CollectionUtils.isNotEmpty(list_in)) {
			mv.addObject("storeList_in", list_in);
			if (pd.get("stores_in") != null) {
				pd.put("store_sn_in", pd.get("stores_in"));
			}
		}
		List<String> storeSnList = new ArrayList<String>();
		for (int i = 0; i < list_in.size(); i++) {
			storeSnList.add(list_in.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		page.setPd(pd);
		List<PageData> varList = allocationService.querylist(page); // 列出Allocation列表
		if (!CollectionUtils.isEmpty(varList)) {
			varList.parallelStream().forEach(var -> {
				Date preSendTime = (Date) var.get("pre_send_time");
				Date sendTime = (Date) var.get("send_time");
				Date preArrivalTime = (Date) var.get("pre_arrival_time");
				Date arrivalTime = (Date) var.get("arrival_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				var.put("pre_send_time", sdf.format(preSendTime));
				if (sendTime != null) {
					var.put("send_time", sdf.format(sendTime));
				}
				var.put("pre_arrival_time", sdf.format(preArrivalTime));
				if (arrivalTime != null) {
					var.put("arrival_time", sdf.format(arrivalTime));
				}
			});
			RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
			RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
		}

		mv.setViewName("erp/query/queryallocation_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryexcel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Allocation到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("调拨编号"); // 1
		titles.add("调出仓库编号"); // 2
		titles.add("调入仓库编号"); // 3
		titles.add("调出仓库名称"); // 4
		titles.add("调入仓库名称"); // 5
		titles.add("预计发货时间"); // 6
		titles.add("实际发货时间"); // 7
		titles.add("预计到货时间"); // 8
		titles.add("实际到货时间"); // 9
		titles.add("状态"); // 10
		titles.add("创建人"); // 11
		titles.add("创建时间"); // 12
		titles.add("更新人"); // 13
		titles.add("更新时间"); // 14
		dataMap.put("titles", titles);
		List<PageData> varOList = allocationService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("allocation_code")); // 1
			vpd.put("var2", varOList.get(i).getString("from_store_sn")); // 2
			vpd.put("var3", varOList.get(i).getString("to_store_sn")); // 3
			vpd.put("var4", varOList.get(i).getString("from_store_name")); // 4
			vpd.put("var5", varOList.get(i).getString("to_store_name")); // 5
			vpd.put("var6", varOList.get(i).getString("pre_send_time")); // 6
			vpd.put("var7", varOList.get(i).getString("send_time")); // 7
			vpd.put("var8", varOList.get(i).getString("pre_arrival_time")); // 8
			vpd.put("var9", varOList.get(i).getString("arrival_time")); // 9
			vpd.put("var10", varOList.get(i).get("status").toString()); // 10
			vpd.put("var11", varOList.get(i).getString("createby")); // 11
			vpd.put("var12", varOList.get(i).getString("create_time")); // 12
			vpd.put("var13", varOList.get(i).getString("updateby")); // 13
			vpd.put("var14", varOList.get(i).getString("update_time")); // 14
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@RequestMapping(value = "/viewlist")
	public ModelAndView viewlist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData query = new PageData();
		query.put("allocation_code", pd.get("allocation_code"));
		List<PageData> varList = allocationdetailService.listByCode(query); // 列出AllocationDetail列表
		mv.setViewName("erp/query/queryallocationdetail_list");

		pd = allocationService.findByCode(query);
		List<PageData> userList = new ArrayList<>();
		userList.add(pd);
		RedisUtil.getUserInfoById(userList, "commitby", "NAME", "commitby");
		RedisUtil.getUserInfoById(userList, "auditby", "NAME", "auditby");
		mv.addObject("varList", varList);
		mv.addObject("pd", userList.get(0));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/inbounddetail")
	public ModelAndView inbounddetail(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData query = new PageData();
		query.put("purchase_code", pd.get("purchase_code"));

		List<PageData> pdList = inboundService.queryByPurchase(pd);
		List<PageData> varList = inbounddetailService.queryByPurchase(pd);

		RedisUtil.getUserInfoById(pdList, "createby", "NAME", "createby");
		mv.setViewName("erp/inbounddetail/queryinbounddetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pdList", pdList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/outbounddetail")
	public ModelAndView outbounddetail(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData query = new PageData();
		query.put("purchase_code", pd.get("purchase_code"));

		List<PageData> pdList = pgtoutboundService.queryByPurchase(pd);
		List<PageData> varList = pgtoutbounddetailService.queryByPurchase(pd);

		RedisUtil.getUserInfoById(pdList, "createby", "NAME", "createby");
		mv.setViewName("erp/outbounddetail/queryoutbounddetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pdList", pdList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/bound")
	public ModelAndView bound(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		int type = 0;
		try {
			type = Integer.valueOf(pd.getString("querytype"));
		} catch (Exception e) {
			if ("allocation".equals(pd.getString("querytype"))) {
				type = MergeUtil.BUSINESS_ALLOCATION;
			} else if ("scrap".equals(pd.getString("querytype"))) {
				type = MergeUtil.BUSINESS_SCRAP;
			} else if ("defectivegoods".equals(pd.getString("querytype"))) {
				type = MergeUtil.BUSINESS_DEFECTIVE_GOODS;
			} else if ("other_instorage_code".equals(pd.getString("querytype"))) {
				type = MergeUtil.BUSINESS_OTHER_INSTORAGE;
			} else if ("other_outstorage_code".equals(pd.getString("querytype"))) {
				type = MergeUtil.BUSINESS_OTHER_OUTSTORAGE;
			}
		}

		PageData query = new PageData();
		query.put("purchase_code", pd.get("purchase_code"));

		List<PageData> outList = pgtoutboundService.queryByPurchase(pd);
		List<PageData> inList = inboundService.queryByPurchase(pd);

		List<PageData> varList = MergeUtil.mergeInBoundOutBound(type, inList, outList);
		mv.setViewName("erp/query/querybound_list");
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/detail")
	public ModelAndView detail(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Object io_type = pd.getString("io_type");
		Object code = pd.get("code");
		List<PageData> varList = null;
		if ("1".equals(io_type)) {// 查询入库单明细
			PageData query = new PageData();
			query.put("inbound_code", code);
			varList = inbounddetailService.queryInboundDetilByCode(query);
		} else {
			PageData query = new PageData();
			query.put("outbound_code", code);
			varList = pgtoutbounddetailService.queryOutboundDetilByCode(query);
		}
		mv.setViewName("erp/query/querybounddetail_list");
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/batch")
	public ModelAndView batch(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Object io_type = pd.getString("io_type");
		Object code = pd.get("code");
		List<PageData> varList = null;
		if ("1".equals(io_type)) {// 查询入库单批次
			PageData query = new PageData();
			query.put("inbound_code", code);
			varList = inboundstockbatchService.queryInboundBatchByCode(query);
		} else {
			PageData query = new PageData();
			query.put("outbound_code", code);
			varList = outboundstockbatchService.queryOutboundBatchByCode(query);
		}

		mv.setViewName("erp/query/queryboundbatch_list");
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	// 打印调拨单
	@RequestMapping(value = "printallocation")
	public ModelAndView printOutboundNotices() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String noticeIds = pd.getString("sn");
		List<PageData> listPd = new ArrayList<PageData>();
		if (noticeIds != null && !noticeIds.equals("")) {
			String[] noticeIdArr = noticeIds.split(",");
			List<PageData> pageList = allocationService.findByCodes(noticeIdArr);
			if (pageList != null) {
				for (PageData pdItem : pageList) {
					List<PageData> listAll = allocationdetailService.findByAllocationCode(pdItem);
					RedisUtil.getGoodsInfoBySkuEncode(listAll, "sku_encode", "unit_name", "unit_name");
					pdItem.put("details", listAll);
				}
				listPd = pageList;
				RedisUtil.getUserInfoById(listPd, "commitby", "NAME", "commitby");
				RedisUtil.getUserInfoById(listPd, "auditby", "NAME", "auditby");
			}
		}

		BarcodeUtil.createBarcode(listPd, "allocation_code", "barcode");
		mv.setViewName("erp/allocation/printallocation");
		mv.addObject("pd", listPd.get(0));
		mv.addObject("list", listPd);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
