package com.fh.controller.dst.scrapdetail;

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

import com.fh.common.ScrapConstants;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.JsonUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.scrapdetail.ScrapDetailManager;

/** 
 * 说明：报损明细管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-12
 */
@Controller
@RequestMapping(value="/scrapdetail")
public class ScrapDetailController extends BaseController {
	
	String menuUrl = "scrapdetail/list.do"; //菜单地址(权限用)
	@Resource(name="scrapdetailService")
	private ScrapDetailManager scrapdetailService;
	
	@RequestMapping(value="/preSave")
	public void checkexists(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"checkexists");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		boolean checkexists = scrapdetailService.checkexists(pd);
		Map<String,Object> map = new HashMap<String,Object>();
		if(checkexists) {
			map.put("msg", "failse");
		}else {
			map.put("msg", "success");
		}
		String jsonStr = JsonUtils.beanToJSONString(map);
		out.write(jsonStr);
		out.close();
		//return AppUtil.returnObject(new PageData(), map);
	}
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ScrapDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("unit", "");	//单位
		User user = getSessionUser();
//		pd.put("auditby", user.getUSERNAME());	//核准人
//		pd.put("audit_time", Tools.date2Str(new Date()));	//核准时间
//		pd.put("status", 0);
		pd.put("audit_quantity", 0);
		pd.put("createby", user.getUSERNAME());	//创建人
		pd.put("create_time", Tools.date2Str(new Date()));	//创建时间
		pd.put("updateby",user.getUSER_ID());	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		scrapdetailService.save(pd);
		mv.addObject("msg","success");
		PageData pd1 = new PageData();
		pd1.put("scrap_code", pd.get("scrap_code"));
		mv.addObject("pd", pd1);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ScrapDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		scrapdetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ScrapDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		pd.put("updateby",user.getUSER_ID());	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		scrapdetailService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	@RequestMapping(value="/check")
	public ModelAndView check() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ScrapDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "check")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		pd.put("status", 1);
		pd.put("auditby", user.getUSER_ID());	//更新人
		pd.put("audit_time", Tools.date2Str(new Date()));	//更新时间
		pd.put("updateby",user.getUSER_ID());	//更新人
		pd.put("update_time", Tools.date2Str(new Date()));	//更新时间
		scrapdetailService.check(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ScrapDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = scrapdetailService.list(pd);	//列出ScrapDetail列表
		RedisUtil.getUserInfoById(varList, "auditby", "NAME", "auditby");
		mv.setViewName("erp/scrapdetail/scrapdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		Object option = pd.get("option");
		mv.addObject("option", option);
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
		pd.put("status", 0);
		mv.setViewName("erp/scrapdetail/scrapdetail_edit");
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
		pd = scrapdetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/scrapdetail/scrapdetail_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	@RequestMapping(value="/goCheck")
	public ModelAndView goCheck()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = scrapdetailService.findById(pd);	//根据ID读取
		mv.setViewName("erp/scrapdetail/scrapdetail_edit");
		mv.addObject("msg", "check");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ScrapDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			scrapdetailService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ScrapDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("报损编码");	//1
		titles.add("物料号");	//2
		titles.add("物料名称");	//3
		titles.add("审批状态");	//4
		titles.add("报损数量 ");	//5
		titles.add("核准数量");	//6
		titles.add("核准人");	//7
		titles.add("核准时间");	//8
		titles.add("单位");	//9
		titles.add("报损说明");	//10
		titles.add("处理建议类型");	//11
		titles.add("处理建议说明");	//12
		titles.add("创建人");	//13
		titles.add("创建时间");	//14
		titles.add("更新人");	//15
		titles.add("更新时间");	//16
		dataMap.put("titles", titles);
		List<PageData> varOList = scrapdetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("scrap_code"));	    //1
			vpd.put("var2", varOList.get(i).get("sku_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("sku_name"));	    //3
			vpd.put("var4", varOList.get(i).get("status").toString());	//4
			vpd.put("var5", varOList.get(i).get("scrap_quantity").toString());	//5
			vpd.put("var6", varOList.get(i).get("audit_quantity").toString());	//6
			vpd.put("var7", varOList.get(i).getString("auditby"));	    //7
			vpd.put("var8", varOList.get(i).getString("audit_time"));	    //8
			vpd.put("var9", varOList.get(i).getString("unit"));	    //9
			vpd.put("var10", varOList.get(i).getString("remark"));	    //10
			vpd.put("var11", varOList.get(i).get("deal_suggest_type").toString());	//11
			vpd.put("var12", varOList.get(i).getString("deal_suggest_des"));	    //12
			vpd.put("var13", varOList.get(i).getString("createby"));	    //13
			vpd.put("var14", varOList.get(i).getString("create_time"));	    //14
			vpd.put("var15", varOList.get(i).getString("updateby"));	    //15
			vpd.put("var16", varOList.get(i).getString("update_time"));	    //16
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
