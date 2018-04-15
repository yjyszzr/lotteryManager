package com.fh.controller.erp.defectivegoodsdetail;

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
import com.fh.util.AppUtil;
import com.fh.util.JsonUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;

import com.fh.service.erp.defectivegoodsdetail.DefectiveGoodsDetailManager;
import com.fh.service.stockbatch.impl.StockBatchService;

/**
 * 说明：不良品明细 创建人：FH Q313596790 创建时间：2017-10-11
 */
@Controller
@RequestMapping(value = "/defectivegoodsdetail")
public class DefectiveGoodsDetailController extends BaseController {

	String menuUrl = "defectivegoodsdetail/list.do"; // 菜单地址(权限用)
	@Resource(name = "defectivegoodsdetailService")
	private DefectiveGoodsDetailManager defectivegoodsdetailService;
	@Resource(name = "stockbatchService")
	private StockBatchService stockbatchService;
	
	@RequestMapping(value = "/preSave")
	public void checkexists(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "checkexists");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		boolean checkexists = defectivegoodsdetailService.checkexists(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if (checkexists) {
			map.put("msg", "failse");
		} else {
			map.put("msg", "success");
		}
		String jsonStr = JsonUtils.beanToJSONString(map);
		out.write(jsonStr);
		out.close();
		// return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增DefectiveGoodsDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		pd.put("createby", user.getUSERNAME());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time", curTime);
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
		// pd.put("defective_detail_id", this.get32UUID()); //主键
		defectivegoodsdetailService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除DefectiveGoodsDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		defectivegoodsdetailService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改DefectiveGoodsDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
		defectivegoodsdetailService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/editStatus")
	public ModelAndView editStatus() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改DefectiveGoodsDetail");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		defectivegoodsdetailService.updateStaus(pd);

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
		logBefore(logger, Jurisdiction.getUsername() + "列表DefectiveGoodsDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		page.setPd(pd);
		List<PageData> varList = defectivegoodsdetailService.list(page);

		mv.setViewName("erp/defectivegoodsdetail/defectivegoodsdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		Object option = pd.get("option");

		mv.addObject("option", option);
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
		mv.setViewName("erp/defectivegoodsdetail/defectivegoodsdetail_edit");
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
		PageData pdquery = new PageData();
		pdquery = this.getPageData();
		PageData pd = defectivegoodsdetailService.findById(pdquery); // 根据ID读取
        
		PageData pdBatchStockParameter=new PageData();
		
		pdBatchStockParameter.put("store_id",pdquery.getString("store_id"));
		pdBatchStockParameter.put("sku_id",pd.getString("sku_id"));
		pdBatchStockParameter.put("batch_code",pd.getString("batch_code"));
		PageData pdBatchStock=stockbatchService.findByBatchAndSkuAndStoreId(pdBatchStockParameter);
		if(pdBatchStock!=null) {
			pd.put("quantity", pdBatchStock.getString("quantity"));
		}
		
		mv.setViewName("erp/defectivegoodsdetail/defectivegoodsdetail_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除DefectiveGoodsDetail");
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
			defectivegoodsdetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出DefectiveGoodsDetail到excel");
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
		List<PageData> varOList = defectivegoodsdetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("defective_detail_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("defective_code")); // 2
			vpd.put("var3", varOList.get(i).get("sku_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("sku_encode")); // 4
			vpd.put("var5", varOList.get(i).getString("batch_code")); // 5
			vpd.put("var6", varOList.get(i).getString("sku_name")); // 6
			vpd.put("var7", varOList.get(i).getString("spec")); // 7
			vpd.put("var8", varOList.get(i).get("status").toString()); // 8
			vpd.put("var9", varOList.get(i).get("defective_quantity").toString()); // 9
			vpd.put("var10", varOList.get(i).getString("unit")); // 10
			vpd.put("var11", varOList.get(i).getString("remark")); // 11
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
	
	/**审核明细列表通过defective_code获取
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listByCode")
	public ModelAndView listByCode(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DefectiveGoodsDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		List<PageData>	varList = defectivegoodsdetailService.listByCode(pd);	//列出DefectiveGoodsDetail列表
		mv.setViewName("erp/defectivegoodsdetail/defectivegoodsdetail_list_check");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
}
