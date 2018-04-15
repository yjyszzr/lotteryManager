package com.fh.controller.dst.szystore;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.fh.service.dst.szystore.SzyStoreManager;
import com.fh.service.dst.warehouse.serialconfig.GetSerialConfigUtilManager;
import com.fh.service.erp.szywarehouse.SzyWareHouseManager;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * 说明：仓库管理 创建人：FH Q313596790 创建时间：2017-09-10
 */
@Controller
@RequestMapping(value = "/szystore")
public class SzyStoreController extends BaseController {

	String menuUrl = "szystore/list.do"; // 菜单地址(权限用)
	@Resource(name = "szystoreService")
	private SzyStoreManager szystoreService;

	@Resource(name = "getserialconfigutilService")
	private GetSerialConfigUtilManager getSerialConfigUtilService;
	
	@Resource(name="szyWareHouseService")
	private SzyWareHouseManager szyWareHouseService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增SzyStore");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("store_id", this.get32UUID()); //主键
		pd.put("store_type_id", "0"); // 仓库类型Id
		pd.put("is_deleted", "0"); // 是否删除
		pd.put("admin_id", "0"); // 管理员id
		szystoreService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value = "/synchStore")
	public void synchStore(HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "同步SzyStore");
		PageData pd = new PageData();
		pd = this.getPageData();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String store_id = pd.getString("store_id");
		if(StringUtils.isBlank(store_id)) {
			out.write("参数有误！");
			out.close();
		}
		String store_name = pd.getString("store_name");
		if(StringUtils.isBlank(store_name)) {
			out.write("参数有误！");
			out.close();
		}
		int rst = 0;
		try {
			rst = szyWareHouseService.save(store_id, store_name);
		} catch (Exception e) {
			logger.error(e);
		}
		if(rst == 1) {
			out.write("同步成功！");
		}else {
			out.write("同步失败！");
		}
		out.close();
	}
	
	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除SzyStore");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		szystoreService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public  Map<String, String> edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改SzyStore");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, String> map = new HashMap<String, String>();
	    if(pd.getString("store_type_id").equals("1"))
	    {
	    	
	    	PageData pdType = new PageData();
			pdType.put("store_type_id", 1);
			Page page = new Page();
			page.setPd(pdType);
			List<PageData> pgStoreList = szystoreService.findByPage(page);
			if (pgStoreList != null && pgStoreList.size() >= 1) {
				if(!pd.getString("store_id").equals(pgStoreList.get(0).getString("store_id")))
				{
					map.put("msg", "总仓已经存在");
					return map;
				}
			
			}
			
	    }
	    if(pd.getString("store_type_id").equals("7"))
	    {
	    	PageData pdType = new PageData();
			pdType.put("store_type_id", 7);
			Page page = new Page();
			page.setPd(pdType);
			List<PageData> pgStoreList = szystoreService.findByPage(page);
			if (pgStoreList != null && pgStoreList.size() >= 1) {
				if(!pd.getString("store_id").equals(pgStoreList.get(0).getString("store_id")))
				{
					map.put("msg", "俱乐部虚拟仓已经存在");
					return map;
				}
			
			}
	    }
		
		szystoreService.edit(pd);
		map.put("msg", "success");
		return map;
	
	}

	/**
	 * 调出仓库列表列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表SzyStore");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String[] types = {};
		if (StringUtils.isEmpty(pd.getString("types"))) {
			String[] types_ = { StoreTypeConstants.STORE_GOOD,
					StoreTypeConstants.STORE_BAD,
					StoreTypeConstants.STORE_EXPRESS,
					StoreTypeConstants.STORE_GIFT,
					StoreTypeConstants.STORE_LOSS,
					StoreTypeConstants.STORE_QUARANTINE,
					StoreTypeConstants.STORE_VIRTUAL };
			types = types_;
		} else {
			types = pd.getString("types").split(",");
		}
		List<PageData> list = GetOwnStore.getOwnStore(types);
		if (CollectionUtils.isNotEmpty(list)) {
			pd.put("store_sns", list);
			page.setPd(pd);
			List<PageData> varList = szystoreService.list(page); // 列出SzyStore列表
			mv.addObject("varList", varList);
		} else {
			mv.addObject("varList", "");
		}
		mv.setViewName("erp/szystore/szystore_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 调出仓库列表列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/storelist")
	public ModelAndView storelist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表SzyStore");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("store_name"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("store_name", keywords.trim());
		}

		String type = pd.getString("type");
		if (null != type && !"".equals(type)) {
			pd.put("store_type_id", type);
		}
		// String[] types = {};
		// if(StringUtils.isEmpty(pd.getString("types"))) {
		// String[] types_ =
		// {StoreTypeConstants.STORE_GOOD,StoreTypeConstants.STORE_BAD,StoreTypeConstants.STORE_EXPRESS,StoreTypeConstants.STORE_GIFT,StoreTypeConstants.STORE_LOSS,StoreTypeConstants.STORE_QUARANTINE,StoreTypeConstants.STORE_VIRTUAL};
		// types = types_;
		// }else {
		// types = pd.getString("types").split(",");
		// }
		// List<PageData> list = GetOwnStore.getOwnStore(types);
		// if(CollectionUtils.isNotEmpty(list)) {
		// pd.put("store_sns", list);
		// page.setPd(pd);
		// List<PageData> varList = szystoreService.findByPage(page);
		// //列出SzyStore列表
		// mv.addObject("varList", varList);
		// }else {
		// mv.addObject("varList", "");
		// }
		page.setPd(pd);
		List<PageData> varList = szystoreService.findByPage(page); // 列出SzyStore列表
//		RedisUtil.setStoreInfo(varList);
//		RedisUtil.getStoreInfoById(varList, "store_id", "store_name", "store_name_lq");
//		for(int i = 0; i < varList.size(); i++) {
//			System.out.println("store_name_liuqi" + varList.get(i).getString("store_name_lq"));
//		}
		
		mv.addObject("varList", varList);
		mv.setViewName("erp/szystore/store_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 调入仓库列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listTo")
	public ModelAndView listTo(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表SzyStore");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String[] types = pd.getString("types").split(",");
		List<PageData> list = GetOwnStore.getOwnStore(types);
		if (CollectionUtils.isNotEmpty(list)) {
			pd.put("store_sns", list);
			page.setPd(pd);
			List<PageData> varList = szystoreService.list(page); // 列出SzyStore列表
			mv.addObject("varList", varList);
		} else {
			mv.addObject("varList", "");
		}
		mv.setViewName("erp/szystore/szystore_list_to");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 调入仓库列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listForSelect")
	public ModelAndView listForSelect(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表SzyStore");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String[] types = pd.getString("types").split(",");
		List<PageData> list = GetOwnStore.getOwnStore(types);
		if (CollectionUtils.isNotEmpty(list)) {
			pd.put("store_sns", list);
			page.setPd(pd);
			List<PageData> varList = szystoreService.list(page); // 列出SzyStore列表
			mv.addObject("varList", varList);
		} else {
			mv.addObject("varList", "");
		}
		mv.setViewName("erp/szystore/szystore_list_forSelect");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 领料仓库列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listBySupplier")
	public ModelAndView listBySupplier(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表SzyStore");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
	
		List<PageData> varList = szystoreService.listBySupplier(pd); // 列出SzyStore列表
		mv.addObject("varList", varList);

		mv.setViewName("erp/szystore/szystore_list_forSelect");
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
		mv.setViewName("erp/szystore/szystore_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("erp/szystore/store_edit");
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
		pd = szystoreService.findById(pd); // 根据ID读取
		mv.setViewName("erp/szystore/szystore_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除SzyStore");
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
			szystoreService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出SzyStore到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("仓库编码"); // 1
		titles.add("仓库名称"); // 2
		titles.add("仓库类型Id"); // 3
		titles.add("联系人"); // 4
		titles.add("联系方式"); // 5
		titles.add("地区代码"); // 6
		titles.add("地址"); // 7
		titles.add("仓库状态"); // 8
		titles.add("备注"); // 9
		titles.add("是否删除"); // 10
		titles.add("管理员id"); // 11
		dataMap.put("titles", titles);
		List<PageData> varOList = szystoreService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("store_sn")); // 1
			vpd.put("var2", varOList.get(i).getString("store_name")); // 2
			vpd.put("var3", varOList.get(i).get("store_type_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("contact")); // 4
			vpd.put("var5", varOList.get(i).getString("tel")); // 5
			vpd.put("var6", varOList.get(i).getString("region_code")); // 6
			vpd.put("var7", varOList.get(i).getString("address")); // 7
			vpd.put("var8", varOList.get(i).get("store_status").toString()); // 8
			vpd.put("var9", varOList.get(i).getString("store_remark")); // 9
			vpd.put("var10", varOList.get(i).get("is_deleted").toString()); // 10
			vpd.put("var11", varOList.get(i).get("admin_id").toString()); // 11
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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}

	@ResponseBody
	@RequestMapping(value = "/create")
	public Map<String, String> create() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增SzyStore");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		String ckCode = getSerialConfigUtilService
				.getSerialCode(FormType.WAREHOUSE_CODE);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("region_code", "");
		pd.put("store_sn", ckCode);
		pd.put("is_deleted", "0"); // 是否删除
		pd.put("admin_id", "0"); // 管理员id
		pd.put("store_status", 0);

		// 判断是否意见有总仓
		Map<String, String> map = new HashMap<String, String>();
		PageData pdMsg = new PageData();
		if (pd.getString("store_type_id") != null
				&& pd.getString("store_type_id").equals("1")) {
			PageData pdType = new PageData();
			pdType.put("store_type_id", 1);
			Page page = new Page();
			page.setPd(pdType);
			List<PageData> pgStoreList = szystoreService.findByPage(page);
			if (pgStoreList != null && pgStoreList.size() >= 1) {
				map.put("msg", "总仓已经存在");
				return map;
			}
		}
		
		if (pd.getString("store_type_id") != null
				&& pd.getString("store_type_id").equals("7")) {
			PageData pdType = new PageData();
			pdType.put("store_type_id", 7);
			Page page = new Page();
			page.setPd(pdType);
			List<PageData> pgStoreList = szystoreService.findByPage(page);
			if (pgStoreList != null && pgStoreList.size() >= 1) {
				map.put("msg", "俱乐部虚拟仓已经存在");
				return map;
			}
		}
		szystoreService.save(pd);
		map.put("msg", "success");
		return map;
	}

	/**
	 * 设置仓库关系
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setwarerel")
	public ModelAndView setwarerel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData pdStore = szystoreService.findById(pd);

		// 更新关系
		PageData pdAll = new PageData();

		List<PageData> pgList = szystoreService.listAll(pdAll);
		mv.setViewName("erp/szystore/store_relation");
		mv.addObject("varList", pgList);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("store_id", pd.get("store_id"));
		mv.addObject("pdstore", pdStore);
		return mv;
	}

	/**
	 * 设置仓库关系
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savewarerel")
	public ModelAndView savewarerel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		if (pd.get("parent_store_id") != null
				&& !pd.get("parent_store_id").equals("")) {
			PageData pdParentParameter = new PageData();
			pdParentParameter.put("store_id", pd.get("parent_store_id"));
			PageData pdParent = szystoreService.findById(pdParentParameter);
			String parent_store_name = pdParent.getString("store_name");
			pd.put("parent_store_name", parent_store_name);
		} else {
			pd.put("parent_store_id", 0);
			pd.put("parent_store_name", "");
		}

		if (pd.get("bad_store_id") != null
				&& !pd.get("bad_store_id").equals("")) {
			PageData pdBadParameter = new PageData();
			pdBadParameter.put("store_id", pd.get("bad_store_id"));
			PageData pdBad = szystoreService.findById(pdBadParameter);
			String bad_store_name = pdBad.getString("store_name");
			pd.put("bad_store_name", bad_store_name);
		} else {
			pd.put("bad_store_id", 0);
			pd.put("bad_store_name", "");
		}

		if (pd.get("logistic_store_id") != null
				&& !pd.get("logistic_store_id").equals("")) {
			PageData pdLogicParameter = new PageData();
			pdLogicParameter.put("store_id", pd.get("logistic_store_id"));
			PageData pdLogic = szystoreService.findById(pdLogicParameter);
			String logistic_store_name = pdLogic.getString("store_name");
			pd.put("logistic_store_name", logistic_store_name);
		} else {
			pd.put("logistic_store_id", 0);
			pd.put("logistic_store_name", "");
		}

		if (pd.get("scrap_store_id") != null
				&& !pd.get("scrap_store_id").equals("")) {
			PageData pdScrapParameter = new PageData();
			pdScrapParameter.put("store_id", pd.get("scrap_store_id"));
			PageData pdScrap = szystoreService.findById(pdScrapParameter);
			String scrap_store_name = pdScrap.getString("store_name");
			pd.put("scrap_store_name", scrap_store_name);
		} else {
			pd.put("scrap_store_id", 0);
			pd.put("scrap_store_name", "");
		}

		if (pd.get("virtual_store_id") != null
				&& !pd.get("virtual_store_id").equals("")) {
			PageData pdVirtualParameter = new PageData();
			pdVirtualParameter.put("store_id", pd.get("virtual_store_id"));
			PageData pdVirtual = szystoreService.findById(pdVirtualParameter);
			String virtual_store_name = pdVirtual.getString("store_name");
			pd.put("virtual_store_name", virtual_store_name);
		} else {
			pd.put("virtual_store_id", 0);
			pd.put("virtual_store_name", "");
		}

		if (pd.get("quality_store_id") != null
				&& !pd.get("quality_store_id").equals("")) {
			PageData pdQualityParameter = new PageData();
			pdQualityParameter.put("store_id", pd.get("quality_store_id"));
			PageData pdQuality = szystoreService.findById(pdQualityParameter);
			String quality_store_name = pdQuality.getString("store_name");
			pd.put("quality_store_name", quality_store_name);
		} else {
			pd.put("quality_store_id", 0);
			pd.put("quality_store_name", "");
		}

		szystoreService.updatestorerel(pd);

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/refreshcache")
	public Map<String, String> refreshCache() throws Exception {
		// 判断是否意见有总仓
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			RedisUtil.setStoreInfo(szystoreService.listAll(pd));
		} catch (Exception e) {
			map.put("info", "刷新失败请稍后再试、或联系管理员");
			map.put("msg", "fail");
			return map;
		}
		map.put("info", "刷新成功");
		map.put("msg", "success");
		return map;
	}
}
