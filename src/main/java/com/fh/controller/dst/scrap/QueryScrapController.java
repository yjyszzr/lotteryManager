package com.fh.controller.dst.scrap;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.GetOwnStore;
import com.fh.common.PurchaseConstants;
import com.fh.common.ScrapConstants;
import com.fh.common.StoreTypeConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.dst.outboundnotice.OutboundNoticeManager;
import com.fh.service.dst.outboundnoticedetail.OutboundNoticeDetailManager;
import com.fh.service.dst.scrap.ScrapManager;
import com.fh.service.dst.scrapdetail.ScrapDetailManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.BarcodeUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Tools;

/**
 * 说明：报损单管理 创建人：FH Q313596790 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value = "/queryscrap")
public class QueryScrapController extends BaseController {

	String menuUrl = "queryscrap/list.do"; // 菜单地址(权限用)
	@Resource(name = "scrapService")
	private ScrapManager scrapService;
	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name = "scrapdetailService")
	private ScrapDetailManager scrapdetailService;
	@Resource(name = "userService")
	private UserManager userService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Scrap");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String[] types = { StoreTypeConstants.STORE_GOOD };
		List<PageData> list = GetOwnStore.getOwnStore(types);
		/** 自身仓库权限 start **/
		List<String> storeSnList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			storeSnList.add(list.get(i).getString("store_sn"));
		}
		pd.put("my_store", storeSnList);
		/** 自身仓库权限 end **/

		if (CollectionUtils.isNotEmpty(list)) {
			mv.addObject("storeList", list);
			if (pd.get("stores") == null || "".equals(pd.get("stores"))) {
				pd.put("store_sn", "");
			} else {
				pd.put("store_sn", pd.get("stores"));
			}
			page.setPd(pd);
			List<PageData> varList = scrapService.querylist(page); // 列出Scrap列表
			if (CollectionUtils.isNotEmpty(varList)) {
				RedisUtil.getUserInfoById(varList, "commitby", "NAME", "commitby");
				RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
				RedisUtil.getUserInfoById(varList, "verifyby", "NAME", "verifyby");
			}
			mv.addObject("varList", varList);
		} else {
			mv.addObject("varList", "");
		}
		mv.setViewName("erp/query/queryscrap_list");
		mv.addObject("pd", pd);
		mv.addObject("bsType", 0);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	@RequestMapping(value = "/detail")
	public ModelAndView detail(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表AllocationDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData query = new PageData();
		query.put("scrap_code", pd.get("scrap_code"));
		List<PageData> varList = scrapdetailService.listByCode(query); // 列出AllocationDetail列表
		mv.setViewName("erp/query/queryscrapdetail_list");

		pd = scrapService.findByCode(query);
		List<PageData> userList = new ArrayList<>();
		userList.add(pd);
		RedisUtil.getUserInfoById(userList, "commitby", "NAME", "commitby");
		RedisUtil.getUserInfoById(userList, "auditby", "NAME", "auditby");
		RedisUtil.getUserInfoById(userList, "verifyby", "NAME", "verifyby");
		mv.addObject("varList", varList);
		mv.addObject("pd", userList.get(0));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "printscrap")
	public ModelAndView printScrap() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String noticeIds = pd.getString("sn");
		List<PageData> listPd = new ArrayList<PageData>();
		if (noticeIds != null && !noticeIds.equals("")) {
			String[] noticeIdArr = noticeIds.split(",");
			List<PageData> pageList = scrapService.findByCodes(noticeIdArr);
			if (pageList != null) {
				for (PageData pdItem : pageList) {
					List<PageData> listAll = scrapdetailService.findByAllocationCode(pdItem);
					pdItem.put("details", listAll);
				}
				listPd = pageList;
			}
			RedisUtil.getUserInfoById(listPd, "commitby", "NAME", "commitby");
			RedisUtil.getUserInfoById(listPd, "verifyby", "NAME", "verifyby");
			RedisUtil.getUserInfoById(listPd, "auditby", "NAME", "auditby");
		}

		BarcodeUtil.createBarcode(listPd, "scrap_code", "barcode");
		mv.setViewName("erp/scrap/printscrap");
		mv.addObject("list", listPd);
		return mv;
	}
}
