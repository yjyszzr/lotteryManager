package com.fh.controller.erp.storeshop;

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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.fh.service.erp.storeshop.StoreShopManager;

/**
 * 说明：仓库店铺关联管理-仓库列表 创建人：FH Q313596790 创建时间：2017-09-19
 */
@Controller
@RequestMapping(value = "/storeshop")
public class StoreShopController extends BaseController {

	String menuUrl = "storeshop/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeshopService")
	private StoreShopManager storeshopService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(PrintWriter pw) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增StoreShop");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("store_shop_id", this.get32UUID()); //主键
		// pd.put("store_name", ""); //仓库名称
		// pd.put("shop_name", ""); //店铺名称

		pd.put("status", "0"); // 状态
		pd.put("createby", getSessionUser().getUSER_ID()); // 创建人
		pd.put("create_time", new Date()); // 创建时间
		pd.put("updateby", getSessionUser().getUSER_ID()); // 修改人
		pd.put("update_time", new Date()); // 修改时间

		try {
			storeshopService.save(pd);
		} catch (Exception e) {
			System.out.print("Exception add");
			pw.print("saveone");
			// pw.close();
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
			return mv;
		}
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
		logBefore(logger, Jurisdiction.getUsername() + "删除StoreShop");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		storeshopService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改StoreShop");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			storeshopService.edit(pd);
		} catch (Exception e) {
			System.out.print("Exception edit");
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
			return mv;
		}
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
		logBefore(logger, Jurisdiction.getUsername() + "列表StoreShop");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = storeshopService.list(page); // 列出StoreShop列表
		mv.setViewName("erp/storeshop/storeshop_list");
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
		List storeList = storeshopService.storeList(pd);
		if (CollectionUtils.isNotEmpty(storeList)) {
			mv.addObject("varList", storeList);
		}
		List shopList = storeshopService.shopList(pd);
		if (CollectionUtils.isNotEmpty(shopList)) {
			mv.addObject("shopList", shopList);
		}
		mv.setViewName("erp/storeshop/storeshop_edit");
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
		pd = storeshopService.findById(pd); // 根据ID读取		
		mv.addObject("store_id", pd.get("store_id"));
		mv.addObject("shop_id", pd.get("shop_id"));
		List storeList = storeshopService.storeList(pd);
		if (CollectionUtils.isNotEmpty(storeList)) {
			mv.addObject("varList", storeList);
		}
		List shopList = storeshopService.shopList(pd);
		if (CollectionUtils.isNotEmpty(shopList)) {
			mv.addObject("shopList", shopList);
		}
		mv.setViewName("erp/storeshop/storeshop_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除StoreShop");
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
			storeshopService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出StoreShop到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("仓库id"); // 1
		titles.add("仓库名称"); // 2
		titles.add("店铺id"); // 3
		titles.add("店铺名称"); // 4
		titles.add("状态"); // 5
		titles.add("创建人"); // 6
		titles.add("创建时间"); // 7
		titles.add("修改人"); // 8
		titles.add("修改时间"); // 9
		dataMap.put("titles", titles);
		List<PageData> varOList = storeshopService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("store_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("store_name")); // 2
			vpd.put("var3", varOList.get(i).get("shop_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("shop_name")); // 4
			vpd.put("var5", varOList.get(i).get("status").toString()); // 5
			vpd.put("var6", varOList.get(i).getString("createby")); // 6
			vpd.put("var7", varOList.get(i).getString("create_time")); // 7
			vpd.put("var8", varOList.get(i).getString("updateby")); // 8
			vpd.put("var9", varOList.get(i).getString("update_time")); // 9
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	/**
	 * 判断是否存在
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/isSel")
	public void isSel(PrintWriter pw) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "判断是否存在StoreShop");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "sel")) {
//			return;
//		} // 校验权限
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd1 = storeshopService.findByStoreId(pd);
		PageData pd2 = storeshopService.findByShopId(pd);
		if(pd1==null && pd2 ==null) {
			pw.print("0");
			pw.close();
		}else {
			pw.print("1");
			pw.close();
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
