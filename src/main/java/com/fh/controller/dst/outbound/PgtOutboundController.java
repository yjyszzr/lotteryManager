package com.fh.controller.dst.outbound;

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
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.GetOwnStore;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.dst.outbound.impl.PgtOutboundService;
import com.fh.service.dst.outbounddetail.impl.PgtOutboundDetailService;
import com.fh.util.AppUtil;
import com.fh.util.BarcodeUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Tools;
import com.fh.util.express.enums.InboundType;

/**
 * 说明：outbound 创建人：FH Q313596790 创建时间：2017-09-10
 */
@Controller
@RequestMapping(value = "/pgtoutbound")
public class PgtOutboundController extends BaseController {

	String menuUrl = "pgtoutbound/list.do"; // 菜单地址(权限用)

	@Resource(name = "pgtoutboundService")
	private PgtOutboundService pgtoutboundService;
	@Resource(name = "pgtoutbounddetailService")
	private PgtOutboundDetailService pgtoutbounddetilService;

	/**
	 * 保存,
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public Object create() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增PgtOutbound");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
			// ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		long notice_id = Long.parseLong(pd.getString("noticeId"));
		String outbound_notice_code = pd.getString("noticeCode");
		String stockbatch = pd.getString("stockbatch");
		String sendQuantityStr = pd.getString("sendQuantity");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String code = pgtoutboundService.saveOutBound(notice_id, outbound_notice_code,
					getSessionUser().getUSER_ID(), stockbatch, sendQuantityStr);
			if (code == null || code.equals("")) {
				map.put("msg", "success");
			} else {
				map.put("msg", "fail");
			}
		} catch (Exception e) {
			map.put("msg", "失败" + e.getMessage());
		}

		return AppUtil.returnObject(pd, map);

	}

	/**
	 * 特殊类型直接生成出库单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create1")
	@ResponseBody
	public Object create1() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增PgtOutbound");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
			// ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		long notice_id = Long.parseLong(pd.getString("noticeId"));
		String outbound_notice_code = pd.getString("noticeCode");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String code = pgtoutboundService.saveOutBound1(notice_id, outbound_notice_code,
					getSessionUser().getUSER_ID());
			if (code == null || code.equals("")) {
				map.put("msg", "success");
			} else {
				map.put("msg", "fail");
			}
		} catch (Exception e) {
			map.put("msg", "失败" + e.getMessage());
		}

		return AppUtil.returnObject(pd, map);

	}

	/**
	 * 保存,
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增PgtOutbound");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("out_bound_id", this.get32UUID()); //主键
		pgtoutboundService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除PgtOutbound");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pgtoutboundService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改PgtOutbound");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pgtoutboundService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		logBefore(logger, Jurisdiction.getUsername() + "列表PgtOutbound");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String[] types = { StoreTypeConstants.STORE_GOOD, StoreTypeConstants.STORE_BAD,
				StoreTypeConstants.STORE_EXPRESS, StoreTypeConstants.STORE_GIFT, StoreTypeConstants.STORE_LOSS,
				StoreTypeConstants.STORE_QUARANTINE, StoreTypeConstants.STORE_VIRTUAL };
		List<PageData> list = GetOwnStore.getOwnStore(types);
		if (CollectionUtils.isNotEmpty(list)) {
			mv.addObject("storeList", list);
			if (pd.get("stores") == null) {
				pd.put("store_sn", list.get(0).getString("store_sn"));
			} else {
				pd.put("store_sn", pd.get("stores"));
			}
			page.setPd(pd);
			List<PageData> varList = pgtoutboundService.list(page); // 列出PgtOutbound列表
			mv.addObject("varList", varList);
		} else {
			mv.addObject("varList", "");
		}
		mv.setViewName("erp/outbound/outbound_list");
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
		mv.setViewName("erp/outbound/outbound_edit");
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
		pd = pgtoutboundService.findById(pd); // 根据ID读取
		mv.setViewName("erp/outbound/outbound_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除PgtOutbound");
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
			pgtoutboundService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 打印出库单
	 * 
	 * @return 视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/printOutbound")
	public ModelAndView printOutbound() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String noticeIds = pd.getString("sn");
		List<PageData> listPd = new ArrayList<PageData>();
		if (noticeIds != null && !noticeIds.equals("")) {
			// 查询出库单信息
			String[] noticeIdArr = noticeIds.split(",");
			List<PageData> pagelist = pgtoutboundService.listOutbound(noticeIdArr);
			RedisUtil.getUserInfoById(pagelist, "createby", "NAME", "name");
			if (pagelist != null) {
				for(PageData pdItem:pagelist) {
//pdItem.put("type", InboundType.getByCode(Tools.ifNull(pdItem.getString("inbound_type"))));
					List<PageData> listDetil = pgtoutbounddetilService.findByOutBoudnDetilCode(pdItem);
					pdItem.put("details", listDetil);
				}
			}
			listPd = pagelist;
		}
		
		BarcodeUtil.createBarcode(listPd, "purchase_code", "barcode");
		mv.setViewName("erp/outbound/printOutbound");
		mv.addObject("list",listPd);
		return mv;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出PgtOutbound到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("备注2"); // 2
		titles.add("备注3"); // 3
		titles.add("备注4"); // 4
		titles.add("备注5"); // 5
		titles.add("备注6"); // 6
		titles.add("备注7"); // 7
		titles.add("备注8"); // 8
		titles.add("备注9"); // 9
		titles.add("备注10"); // 10
		titles.add("备注11"); // 11
		titles.add("备注12"); // 12
		titles.add("备注13"); // 13
		titles.add("备注14"); // 14
		titles.add("备注15"); // 15
		dataMap.put("titles", titles);
		List<PageData> varOList = pgtoutboundService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("out_bound_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("out_bound_code")); // 2
			vpd.put("var3", varOList.get(i).get("type").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("purchase_code")); // 4
			vpd.put("var5", varOList.get(i).get("purchase_detail_id").toString()); // 5
			vpd.put("var6", varOList.get(i).getString("outbound_notice_code")); // 6
			vpd.put("var7", varOList.get(i).get("outbound_notice_detail_id").toString()); // 7
			vpd.put("var8", varOList.get(i).getString("business_time")); // 8
			vpd.put("var9", varOList.get(i).getString("store_sn")); // 9
			vpd.put("var10", varOList.get(i).getString("store_name")); // 10
			vpd.put("var11", varOList.get(i).get("status").toString()); // 11
			vpd.put("var12", varOList.get(i).getString("createby")); // 12
			vpd.put("var13", varOList.get(i).getString("create_time")); // 13
			vpd.put("var14", varOList.get(i).getString("updateby")); // 14
			vpd.put("var15", varOList.get(i).getString("update_time")); // 15
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
}
