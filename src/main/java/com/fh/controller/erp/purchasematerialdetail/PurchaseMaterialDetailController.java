package com.fh.controller.erp.purchasematerialdetail;

import java.io.PrintWriter;
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
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.erp.hander.MaterialCommitManager;
import com.fh.service.erp.purchasematerialdetail.PurchaseMaterialDetailManager;

/** 
 * 说明：生产领料明细
 * 创建人：FH Q313596790
 * 创建时间：2017-10-13
 */
@Controller
@RequestMapping(value="/purchasematerialdetail")
public class PurchaseMaterialDetailController extends BaseController {
	
	String menuUrl = "purchasematerialdetail/list.do"; //菜单地址(权限用)
	@Resource(name="purchasematerialdetailService")
	private PurchaseMaterialDetailManager purchasematerialdetailService;
	
	@Resource(name="materialcommitService")
	private MaterialCommitManager materialcommitService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PurchaseMaterialDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("material_detail_id", this.get32UUID());	//主键
		purchasematerialdetailService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除PurchaseMaterialDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasematerialdetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PurchaseMaterialDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasematerialdetailService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseMaterialDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = purchasematerialdetailService.list(page);	//列出PurchaseMaterialDetail列表
		mv.setViewName("erp/purchasematerialdetail/purchasematerialdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("erp/purchasematerialdetail/purchasematerialdetail_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = purchasematerialdetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/purchasematerialdetail/purchasematerialdetail_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PurchaseMaterialDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasematerialdetailService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出PurchaseMaterialDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("生产编码");	//1
		titles.add("生产明细ID");	//2
		titles.add("生产领料ID");	//3
		titles.add("仓库编码");	//4
		titles.add("仓库名称");	//5
		titles.add("物料ID");	//6
		titles.add("物料编码");	//7
		titles.add("物料名称");	//8
		titles.add("物料规格");	//9
		titles.add("物料单位");	//10
		titles.add("物料批次码");	//11
		titles.add("领取数量");	//12
		titles.add("状态");	//13
		titles.add("备注15");	//14
		titles.add("备注16");	//15
		titles.add("备注17");	//16
		titles.add("备注18");	//17
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasematerialdetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("purchase_code"));	    //1
			vpd.put("var2", varOList.get(i).get("purchase_detail_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("purchase_material_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("store_sn"));	    //4
			vpd.put("var5", varOList.get(i).getString("store_name"));	    //5
			vpd.put("var6", varOList.get(i).get("sku_id").toString());	//6
			vpd.put("var7", varOList.get(i).getString("sku_encode"));	    //7
			vpd.put("var8", varOList.get(i).getString("sku_name"));	    //8
			vpd.put("var9", varOList.get(i).getString("spec"));	    //9
			vpd.put("var10", varOList.get(i).getString("unit"));	    //10
			vpd.put("var11", varOList.get(i).getString("batch_code"));	    //11
			vpd.put("var12", varOList.get(i).get("quantity").toString());	//12
			vpd.put("var13", varOList.get(i).get("status").toString());	//13
			vpd.put("var14", varOList.get(i).getString("createby"));	    //14
			vpd.put("var15", varOList.get(i).getString("create_time"));	    //15
			vpd.put("var16", varOList.get(i).getString("updateby"));	    //16
			vpd.put("var17", varOList.get(i).getString("update_time"));	    //17
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listById")
	public ModelAndView listById() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseMaterialDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = purchasematerialdetailService.listById(pd);	//列出PurchaseMaterialDetail列表
		mv.setViewName("erp/purchasematerialdetail/purchasematerialdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	//
	/**退料
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/returnMaterials")
	public Map<String, String> returnMaterials() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		logBefore(logger, Jurisdiction.getUsername()+"returnMaterials");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return map;} //校验权限
		
		
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			materialcommitService.saveReturnMaterial(pd);
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "fail");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
