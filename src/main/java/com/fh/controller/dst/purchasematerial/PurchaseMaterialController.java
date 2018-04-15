package com.fh.controller.dst.purchasematerial;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.goodssku.impl.GoodsSkuService;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.impl.OutboundNoticeDetailService;
import com.fh.service.dst.purchasedetail.impl.PurchaseDetailService;
import com.fh.service.dst.purchasematerial.PurchaseMaterialManager;
import com.fh.service.dst.supplierstore.SupplierStoreManager;
import com.fh.service.dst.warehouse.serialconfig.GetSerialConfigUtilManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.inboundnotice.impl.InboundNoticeService;
import com.fh.service.erp.inboundnoticedetail.impl.InboundNoticeDetailService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.express.enums.InboundNoticeType;

/**
 * 说明：用料列表 创建人：FH Q313596790 创建时间：2017-09-09
 */
@Controller
@RequestMapping(value = "/purchasematerial")
public class PurchaseMaterialController extends BaseController {

	String menuUrl = "purchasematerial/list.do"; // 菜单地址(权限用)
	@Resource(name = "purchasematerialService")
	private PurchaseMaterialManager purchasematerialService;

	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilService getserialconfigutilService;

	@Resource(name = "goodsskuService")
	private GoodsSkuService goodsSkuService;

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "inboundnoticeService")
	private InboundNoticeService inboundNoticeService;

	@Resource(name = "inboundnoticedetailService")
	private InboundNoticeDetailService inboundNoticeDetailService;
	
	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilManager getSerialConfigUtilService;
	
	@Resource(name = "outboundnoticeService")
	private OutboundNoticeManager outboundnoticeService;
	
	@Resource(name = "supplierstoreService")
	private SupplierStoreManager supplierStoreManager;
	
	@Resource(name="outboundnoticedetailService")
	private OutboundNoticeDetailService outboundnoticedetailService;
	
	@Resource(name="purchasedetailService")
	private PurchaseDetailService purchaseDetailService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增PurchaseMaterial");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据生产订单编号和生产明细单的skuId，查找生产明细单的id
		PageData purchaseDetailPd = new PageData();
		purchaseDetailPd.put("purchase_code", pd.getString("purchase_code"));
		purchaseDetailPd.put("sku_id", pd.get("sku_id"));
		List<PageData> purchaseDetails = purchaseDetailService.listByParam(purchaseDetailPd);
		if(CollectionUtils.isNotEmpty(purchaseDetails)) {
			pd.put("purchase_detail_id", purchaseDetails.get(0).get("detail_id"));
		}else {
			pd.put("purchase_detail_id", 1);
		}
		// 状态：0-待领料 1-领料中 2-退料中 3-已完成
		pd.put("status", 0);
		pd.put("total_quantity", 0);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("createby", user.getUSER_ID()); // 创建人
		pd.put("create_time", new Date()); // 创建时间
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		purchasematerialService.save(pd);
		//领料
		saveReceiveMaterial(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除PurchaseMaterial");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasematerialService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改PurchaseMaterial");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		purchasematerialService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 查询待生产加工的生产明细
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/productionList")
	public ModelAndView productionList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		//0-标准采购   1-委外加工
		pd.put("business_type", 1);
		//0 待提交 1 已提交待审核  2：已审核 8 驳回 9 完成
		pd.put("status", 2);
		page.setPd(pd);
		List<PageData> varList = purchaseDetailService.dataPage(page); 
		mv.setViewName("erp/purchasematerial/productionorderdetail_list");
		if(CollectionUtils.isNotEmpty(varList)) {
			for (PageData pageData : varList) {
				Date preArrivalTime = (Date)pageData.get("pre_arrival_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				pageData.put("pre_arrival_time", sdf.format(preArrivalTime));
			}
		}
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表PurchaseMaterial");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = purchasematerialService.list(page); // 列出PurchaseMaterial列表
		mv.setViewName("erp/purchasematerial/purchasematerial_list");
/*		if (CollectionUtils.isNotEmpty(varList)) {
			varList.parallelStream().forEach(var -> {
				String createby = var.get("createby").toString();
				String updateby = var.get("updateby").toString();
				try {
					User createUser = userService.getUserAndRoleById(createby);
					User updateUser = userService.getUserAndRoleById(updateby);
					if (createUser != null)
						var.put("createby", createUser.getNAME());
					if (updateUser != null)
						var.put("updateby", updateUser.getNAME());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}*/
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
		mv.setViewName("erp/purchasematerial/purchasematerial_edit");
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
		pd = purchasematerialService.findById(pd); // 根据ID读取
		mv.setViewName("erp/purchasematerial/purchasematerial_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 去领料页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goReceiveMaterial")
	public ModelAndView goReceiveMaterial() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = purchasematerialService.findById(pd); // 根据ID读取
		mv.setViewName("erp/purchasematerial/purchasematerial_receiveMaterial");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 领料
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveReceiveMaterial")
	public void saveReceiveMaterial(PageData pd) throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
		String outBoundCode = getSerialConfigUtilService.getSerialCode(FormType.OUTBOUND_CODE);
		String store_sn = pd.getString("store_sn");
		String store_name = pd.getString("store_name");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date pre_arrival_time = sdf.parse(pd.getString("pre_arrival_time"));
//		pd = purchasematerialService.findById(pd); // 根据ID读取
		Object material_id = pd.get("material_id");
		//向发货通知单中插入数据
		{
			//0:领料 1:调拨
			Integer bill_type = 0;
			PageData pd1 = new PageData();
			pd1.put("outbound_notice_code", outBoundCode);
			pd1.put("store_sn", store_sn);
			pd1.put("store_name", store_name);
			pd1.put("bill_type", bill_type);
			pd1.put("purchase_code", pd.getString("purchase_code"));
			pd1.put("purchase_detail_id", material_id);
			pd1.put("pre_send_time", pre_arrival_time);
			//预留
			pd1.put("status", 0);
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			pd1.put("createby", user.getUSER_ID()); // 创建人
			pd1.put("create_time", Tools.date2Str(new Date())); // 创建时间
			pd1.put("updateby", user.getUSER_ID()); // 更新人
			pd1.put("update_time", Tools.date2Str(new Date())); // 更新时间
			outboundnoticeService.save(pd1);
		}
		//向发货通知明细单中插入数据
		{
			PageData pd1 = new PageData();
			pd1.put("outbound_notice_code", outBoundCode);
			pd1.put("purchase_code", pd.getString("purchase_code"));
			pd1.put("purchase_detail_id", material_id);
			pd1.put("sku_id", pd.get("sku_id"));
			pd1.put("sku_name", pd.getString("sku_name"));
			pd1.put("pre_send_quantity", pd.get("total_apply_quantity"));
			pd1.put("send_quantity", 0);
			//预留
			pd1.put("status", 0);
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			pd1.put("createby", user.getUSER_ID()); // 创建人
			pd1.put("create_time", Tools.date2Str(new Date())); // 创建时间
			pd1.put("updateby", user.getUSER_ID()); // 更新人
			pd1.put("update_time", Tools.date2Str(new Date())); // 更新时间
			outboundnoticedetailService.save(pd1);
		}
		// 状态：0-待领料 1-领料中 2-退料中 3-已完成
		pd.put("status", 1);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		purchasematerialService.update(pd);
//		mv.addObject("msg", "success");
//		mv.setViewName("save_result");
//		return mv;
	}

	/**
	 * 去退料页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goReturnMaterial")
	public ModelAndView goReturnMaterial() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = purchasematerialService.findById(pd); // 根据ID读取
		mv.setViewName("erp/purchasematerial/purchasematerial_returnmaterial");
		mv.addObject("msg", "edit");
		pd.put("total_apply_quantity", 0);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 退料
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveReturnMaterial")
	public ModelAndView saveReturnMaterial() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 退货数量
		String returnMaterial = pd.getString("total_apply_quantity");
		String store_sn = pd.getString("store_sn");
		String store_name = pd.getString("store_name");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date pre_arrival_time = sdf.parse(pd.getString("pre_arrival_time"));
		pd = purchasematerialService.findById(pd);
		String purchase_code = pd.getString("purchase_code");
		String purchase_detail_id = pd.getString("material_id");
		String sku_id = pd.getString("sku_id");
		String sku_name = pd.getString("sku_name");
		String sku_encode = "";
		String spec = "";
		if(StringUtils.isNotEmpty(sku_id)) {
			PageData pageData = new PageData();
			pageData.put("sku_id", sku_id);
			pageData = goodsSkuService.findSkuById(pd);
			if(pageData != null) {
				sku_encode = pageData.getString("sku_encode");
				spec = pageData.getString("spec");
			}
		}
		String pre_arrival_quantity = returnMaterial;

		saveinboundNotice(store_sn, store_name, pre_arrival_time, purchase_code, purchase_detail_id, sku_id, sku_name, sku_encode, spec,
				pre_arrival_quantity);

		// 状态：0-待领料 1-领料中 2-退料中 3-已完成
		pd.put("status", 2);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		purchasematerialService.update(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	private void saveinboundNotice(String store_sn, String store_name, Date pre_arrival_time, String purchase_code,
			String purchase_detail_id, String sku_id, String sku_name, String sku_encode, String spec, String pre_arrival_quantity) throws Exception {
		String inbound_notice_code = getSerialConfigUtilService.getSerialCode(FormType.INBOUND_NOTICE_CODE);
		if (inbound_notice_code == null || inbound_notice_code.trim().length() == 0) {
			inbound_notice_code = "notice_code";
		}
		// 向到货通知单中插入数据
		{
			PageData pdInboundNotice = new PageData();
			pdInboundNotice.put("inbound_notice_code", inbound_notice_code);
			pdInboundNotice.put("bill_type", InboundNoticeType.PRODUCTIONRETURN_IN.getCode());
			pdInboundNotice.put("purchase_code", purchase_code);
			pdInboundNotice.put("purchase_detail_id", purchase_detail_id);
			pdInboundNotice.put("pre_arrival_time", pre_arrival_time);
			// 实际到货时间为0
			pdInboundNotice.put("arrival_time", null);
			int status = 0;

			pdInboundNotice.put("store_sn", store_sn);
			pdInboundNotice.put("store_name", store_name);
			pdInboundNotice.put("status", status);
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			pdInboundNotice.put("createby", user.getUSER_ID()); // 创建人
			pdInboundNotice.put("create_time", new Date()); // 创建时间
			pdInboundNotice.put("updateby", user.getUSER_ID()); // 更新人
			pdInboundNotice.put("update_time", new Date()); // 更新时间
			inboundNoticeService.save(pdInboundNotice);
		}

		{
			PageData pdInboundNoticeDetail = new PageData();
			pdInboundNoticeDetail.put("inbound_code", inbound_notice_code);

			pdInboundNoticeDetail.put("purchase_code", purchase_code);
			pdInboundNoticeDetail.put("purchase_detail_id", purchase_detail_id);
			pdInboundNoticeDetail.put("sku_id", sku_id);
			pdInboundNoticeDetail.put("sku_name", sku_name);
			pdInboundNoticeDetail.put("sku_encode", sku_encode);
			pdInboundNoticeDetail.put("spec", spec);
			pdInboundNoticeDetail.put("pre_arrival_quantity", pre_arrival_quantity);
			pdInboundNoticeDetail.put("good_quantity", 0);
			pdInboundNoticeDetail.put("bad_quantity", 0);
			pdInboundNoticeDetail.put("bad_deal_type", 0);
			pdInboundNoticeDetail.put("quality_status", 0);
			pdInboundNoticeDetail.put("status", 0);
			/*
			 * `good_quantity` int(11) DEFAULT NULL COMMENT '默认0。入库后回写。', `bad_quantity`
			 * int(11) DEFAULT NULL COMMENT '默认0。质检后回写。', `bad_deal_type` tinyint(4) DEFAULT
			 * NULL COMMENT '0 退回 1 入不良品', `quality_status` tinyint(4) DEFAULT NULL COMMENT
			 * '0 未质检 1 已质检',
			 */
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			pdInboundNoticeDetail.put("createby", user.getUSER_ID()); // 创建人
			pdInboundNoticeDetail.put("create_time", new Date()); // 创建时间
			pdInboundNoticeDetail.put("updateby", user.getUSER_ID()); // 更新人
			pdInboundNoticeDetail.put("update_time", new Date()); // 更新时间
			inboundNoticeDetailService.save(pdInboundNoticeDetail);

		}
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除PurchaseMaterial");
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
			purchasematerialService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出PurchaseMaterial到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("生产订单编码"); // 1
		titles.add("生产明细订单Id"); // 2
		titles.add("skuId"); // 3
		titles.add("申请用料数量"); // 4
		titles.add("用料数量"); // 5
		titles.add("状态"); // 6
		titles.add("创建人"); // 7
		titles.add("创建时间"); // 8
		titles.add("更新人"); // 9
		titles.add("更新时间"); // 10
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasematerialService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("purchase_code")); // 1
			vpd.put("var2", varOList.get(i).get("purchase_detail_id").toString()); // 2
			vpd.put("var3", varOList.get(i).get("sku_id").toString()); // 3
			vpd.put("var4", varOList.get(i).get("total_apply_quantity").toString()); // 4
			vpd.put("var5", varOList.get(i).get("total_quantity").toString()); // 5
			vpd.put("var6", varOList.get(i).get("status").toString()); // 6
			vpd.put("var7", varOList.get(i).getString("createby")); // 7
			vpd.put("var8", varOList.get(i).getString("create_time")); // 8
			vpd.put("var9", varOList.get(i).getString("updateby")); // 9
			vpd.put("var10", varOList.get(i).getString("update_time")); // 10
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
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listById")
	public ModelAndView listById() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表PurchaseMaterial");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = purchasematerialService.listById(pd); // 列出PurchaseMaterial列表
		mv.setViewName("erp/purchasematerial/purchasematerial_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
