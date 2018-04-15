package com.fh.controller.erp.inboundnoticedetail;

import java.io.File;
import java.io.PrintWriter;
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

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.inboundcheckdetail.InboundCheckDetailManager;
import com.fh.service.dst.purchasedetail.PurchaseDetailManager;
import com.fh.service.dst.qualitycheck.QualityCheckManager;
import com.fh.service.dst.qualitycheckfile.QualityCheckFileManager;
import com.fh.service.erp.hander.SaveInboundManager;
import com.fh.service.erp.inboundnoticedetail.InboundNoticeDetailManager;
import com.fh.service.erp.inboundnoticestockbatch.InboundNoticeStockBatchManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;

/**
 * 说明：到货通知单管理 创建人：FH Q313596790 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value = "/inboundnoticedetail")
public class InboundNoticeDetailController extends BaseController {

	String menuUrl = "inboundnoticedetail/list.do"; // 菜单地址(权限用)
	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailManager inboundnoticedetailService;
	
	@Resource(name = "saveInboundService")
	private SaveInboundManager saveInboundService;

	@Resource(name = "qualitycheckService")
	private QualityCheckManager qualitycheckService;

	@Resource(name = "qualitycheckfileService")
	private QualityCheckFileManager qualitycheckfileService;

	@Resource(name = "purchasedetailService")
	private PurchaseDetailManager purchasedetailService;

	@Resource(name="inboundnoticestockbatchService")
	private InboundNoticeStockBatchManager inboundnoticestockbatchService;
	
	@Resource(name="inboundcheckdetailService")
	private InboundCheckDetailManager inboundCheckDetailManager;
	/**
	 * 其它类型物料入库
	 * 
	 * @param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/inboundall")
	public Map<String, String> inboundall() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改InboundNoticeDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "inbound")) {
			return null;
		} // 校验权限
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String in_code = (String) pd.get("in_code");
		String inbound_notice_code = (String) pd.get("inbound_notice_code");
		try {	
			saveInboundService.saveInboundHandler(inbound_notice_code, in_code);
			map.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "fail");
			map.put("info", e.getMessage());
		}
		return map;
	}

	/**
	 * 采购、生产类型物料入库
	 * 
	 * @param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/inbound")
	public Map<String, String> inbound() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改InboundNoticeDetail");
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			saveInboundService.saveInboundDataHandler(pd);
			map.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "fail");
			map.put("info", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增InboundNoticeDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("notice_detail_id", this.get32UUID()); //主键
		pd.put("inbound_code", ""); // 到货通知单编码
		pd.put("purchase_code", ""); // 业务单据编码
		pd.put("purchase_detail_id", "0"); // 业务单据明细ID
		pd.put("sku_id", "0"); // 商品ID
		pd.put("pre_arrival_quantity", "0"); // 预计到货数量
		pd.put("quality_status", "0"); // 质检状态
		pd.put("quality_time", ""); // 质检时间
		pd.put("quality_id", "0"); // 质检ID
		pd.put("status", "0"); // 备注13
		inboundnoticedetailService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除InboundNoticeDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inboundnoticedetailService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 质检
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改InboundNoticeDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		PageData pageData = this.getPageData();
		saveInboundService.saveQuality(basepath ,request, pageData);

		/**
		 * 更新质检数据到采购单
		 */
/*		PageData pd2 = new PageData();
		pd2.put("good_quantity", good_quantity);
		pd2.put("bad_quantity", bad_quantity);
		pd2.put("detail_id", purchase_detail_id);
		purchasedetailService.qualityCheck(pd2);*/
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}


	/**
	 * 列表
	 * 
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表InboundNoticeDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		List<PageData> varList = new ArrayList<PageData>();
		// bill_type:1-采购订单,2-生产订单     page:2-入库按钮        如果是采购、生产订单入库，取质检表内的数据，其它的取到货单内的数据
		if((pd.getString("bill_type").equals("1") || pd.getString("bill_type").equals("2")) && pd.get("page").equals("2")) {
			PageData pageData = new PageData();
			pageData.put("inbound_notice_code", pd.getString("inbound_notice_code"));
			page.setPd(pageData);
			varList = inboundCheckDetailManager.listByStatus(page); // 列出Inboundcheckdetail列表
			mv.setViewName("erp/inboundnotice/inboundcheckdetail_list");
		}else {
			varList = inboundnoticedetailService.listByWhere(pd); // 列出InboundNoticeDetail列表
			mv.setViewName("erp/inboundnoticedetail/inboundnoticedetail_list");
		}
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("erp/inboundnoticedetail/inboundnoticedetail_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inboundnoticedetailService.findById(pd); // 根据ID读取
		mv.setViewName("erp/inboundnoticedetail/inboundnoticedetail_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除InboundNoticeDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inboundnoticedetailService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出InboundNoticeDetail到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("到货通知单编码"); // 1
		titles.add("业务单据编码"); // 2
		titles.add("业务单据明细ID"); // 3
		titles.add("商品ID"); // 4
		titles.add("预计到货数量"); // 5
		titles.add("入库数量"); // 6
		titles.add("不良品数量"); // 7
		titles.add("不良品处理类型"); // 8
		titles.add("质检状态"); // 9
		titles.add("质检时间"); // 10
		titles.add("质检ID"); // 11
		titles.add("状态"); // 12
		dataMap.put("titles", titles);
		List<PageData> varOList = inboundnoticedetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("inbound_code")); // 1
			vpd.put("var2", varOList.get(i).getString("purchase_code")); // 2
			vpd.put("var3", varOList.get(i).get("purchase_detail_id").toString()); // 3
			vpd.put("var4", varOList.get(i).get("sku_id").toString()); // 4
			vpd.put("var5", varOList.get(i).get("pre_arrival_quantity").toString()); // 5
			vpd.put("var6", varOList.get(i).get("good_quantity").toString()); // 6
			vpd.put("var7", varOList.get(i).get("bad_quantity").toString()); // 7
			vpd.put("var8", varOList.get(i).get("bad_deal_type").toString()); // 8
			vpd.put("var9", varOList.get(i).get("quality_status").toString()); // 9
			vpd.put("var10", varOList.get(i).getString("quality_time")); // 10
			vpd.put("var11", varOList.get(i).get("quality_id").toString()); // 11
			vpd.put("var12", varOList.get(i).get("status").toString()); // 12
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
	
	@RequestMapping(value = "/goBatch")
	public ModelAndView goBatch() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inboundnoticedetailService.findById(pd); // 根据ID读取
		mv.setViewName("erp/inboundnoticedetail/inboundnoticedetail_editbatch");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value = "/savebatch")
	public ModelAndView savebatch() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增InboundNoticeDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		inboundnoticedetailService.editbatch(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
