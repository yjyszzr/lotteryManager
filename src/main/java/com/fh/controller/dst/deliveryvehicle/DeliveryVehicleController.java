package com.fh.controller.dst.deliveryvehicle;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.fh.service.dst.deliveryvehicle.DeliveryVehicleManager;
import com.fh.service.erp.deliverymember.DeliveryMemberManager;
import com.fh.service.erp.deliveryorg.DeliveryOrgManager;
import com.fh.service.erp.deliveryvehiclecompany.DeliveryVehicleCompanyManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：当天配送车辆
 * 创建人：FH Q313596790
 * 创建时间：2017-10-26
 */
@Controller
@RequestMapping(value="/deliveryvehicle")
public class DeliveryVehicleController extends BaseController {
	
	String menuUrl = "deliveryvehicle/list.do"; //菜单地址(权限用)
	@Resource(name="deliveryvehicleService")
	private DeliveryVehicleManager deliveryvehicleService;
	
	@Resource(name="deliveryorgService")
	private DeliveryOrgManager deliveryorgService;
	
	@Resource(name="deliveryvehiclecompanyService")
	private DeliveryVehicleCompanyManager deliveryvehiclecompanyService;
	
	@Resource(name="deliverymemberService")
	private DeliveryMemberManager deliverymemberService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DeliveryVehicle");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("vehicle_id", "0");	//车辆Id(预留)
		pd.put("status", 0);
		if(Integer.parseInt(pd.get("type").toString())==0) {
			pd.put("delivery_vehicle_company_id", null);
			pd.put("delivery_vehicle_company_name", "璞谷塘");
		}
		User user = getSessionUser();
		pd.put("createby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time",curTime);
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", curTime);
		deliveryvehicleService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除DeliveryVehicle");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		deliveryvehicleService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**提交
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/submit")
	public void submit(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//状态：0 未上岗 1 配送中 2 已完成
		pd.put("status", 1);
		User user = getSessionUser();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", curTime);
		deliveryvehicleService.updateStatus(pd);
		out.write("success");
		out.close();
	}
	
	/**完成
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/finished")
	public void finished(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pageData = deliveryvehicleService.findById(pd);
		if(pageData != null) {
			if(pageData.get("start_time") != null && pageData.get("end_time") != null && pageData.get("total_time") != null
				&& pageData.get("start_mileage") != null && pageData.get("end_mileage") != null && pageData.get("total_mileage") != null) {
				//状态：0 未上岗 1 配送中 2 已完成
				pd.put("status", 2);
				User user = getSessionUser();
				Timestamp curTime = new Timestamp(System.currentTimeMillis());
				pd.put("updateby", user.getUSER_ID());
				pd.put("update_time", curTime);
				deliveryvehicleService.updateStatus(pd);
				out.write("success");
			}else {
				out.write("unFinished");
			}
		}else {
			out.write("none");
		}
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DeliveryVehicle");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("status", 0);
		if(Integer.parseInt(pd.get("type").toString())==0) {
			pd.put("delivery_vehicle_company_id", null);
			pd.put("delivery_vehicle_company_name", "璞谷塘");
		}
		User user = getSessionUser();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", curTime);
		deliveryvehicleService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**修改使用记录
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveUseLog")
	public ModelAndView saveUseLog() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", curTime);
		deliveryvehicleService.updateUseLog(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryVehicle");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		mv.setViewName("erp/deliveryvehicle/deliveryvehicle_list");
		//获取配送组织
		User user = getSessionUser();
		if(user != null) {
			String userId = user.getUSER_ID();
			if(StringUtils.isNotEmpty(userId)) {
				PageData pageData = new PageData();
				pageData.put("user_id", userId);
				List<PageData> orgList = deliveryorgService.listAllByUserId(pageData);
				if(CollectionUtils.isNotEmpty(orgList)) {
					mv.addObject("deliveryOrg", orgList);
					if(pd.get("delivery_org_id")==null) {
						pd.put("delivery_org_id", orgList.get(0).get("delivery_org_id"));
					}
					page.setPd(pd);
					List<PageData>	varList = deliveryvehicleService.list(page);	//列出DeliveryVehicle列表
					mv.addObject("varList", varList);
				}
			}
		}
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listForSelect")
	public ModelAndView listForSelect(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryVehicle");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		PageData orgPara = new PageData();
		orgPara.put("user_id", getSessionUser().getUSER_ID());
		List<PageData> orgs = deliverymemberService.listAll(orgPara);
		mv.addObject("deliveryOrgs", orgs);
		List<PageData>	varList = deliveryvehicleService.list(page);	//列出DeliveryVehicle列表
		mv.setViewName("erp/deliveryvehicle/deliveryvehicle_list_for_select");
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
		mv.setViewName("erp/deliveryvehicle/deliveryvehicle_edit");
		mv.addObject("msg", "save");
		//获取配送组织
		User user = getSessionUser();
		if(user != null) {
			String userId = user.getUSER_ID();
			if(StringUtils.isNotEmpty(userId)) {
				PageData pageData = new PageData();
				pageData.put("user_id", userId);
				List<PageData> orgList = deliveryorgService.listAllByUserId(pageData);
				pd.put("orgList", orgList);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(new Date());  
		pd.put("work_day", dateNowStr);
		List<PageData> types = new LinkedList<PageData>();
		PageData t0 = new PageData();
		t0.put("type_id", 0);
		t0.put("type_name", "本公司");
		PageData t1 = new PageData();
		t1.put("type_id", 1);
		t1.put("type_name", "第三方");
		types.add(t0);
		types.add(t1);
		pd.put("types", types);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**获取所属公司列表
	 * @return
	 */
	@RequestMapping(value="/getCompanyList")
	@ResponseBody
	public Object getCompanyList(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> companys = deliveryvehiclecompanyService.listByOrg(pd);
			List<PageData> pdList = new ArrayList<PageData>();
			for(PageData pageData :companys){
				PageData pdf = new PageData();
				pdf.put("delivery_vehicle_company_id", pageData.get("delivery_vehicle_company_id"));
				pdf.put("delivery_vehicle_company_name", pageData.getString("name"));
				pdList.add(pdf);
			}
			map.put("companyList", pdList);	
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**获取所属公司数据
	 * @return
	 */
	@RequestMapping(value="/getCompanyData")
	@ResponseBody
	public Object getCompanyData(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData company = deliveryvehiclecompanyService.findById(pd);
			map.put("company", company);	
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
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
		pd = deliveryvehicleService.findById(pd);	//根据ID读取
		mv.setViewName("erp/deliveryvehicle/deliveryvehicle_edit");
		mv.addObject("msg", "edit");
		//获取配送组织
		User user = getSessionUser();
		if(user != null) {
			String userId = user.getUSER_ID();
			if(StringUtils.isNotEmpty(userId)) {
				PageData pageData = new PageData();
				pageData.put("user_id", userId);
				List<PageData> orgList = deliveryorgService.listAllByUserId(pageData);
				pd.put("orgList", orgList);
				
				Integer delivery_org_id = Integer.parseInt(pd.get("delivery_org_id").toString());
				if(delivery_org_id != null) {
					PageData orgPageData = new PageData();
					orgPageData.put("delivery_org_id", delivery_org_id);
					List<PageData> companys = deliveryvehiclecompanyService.listByOrg(orgPageData);
					List<PageData> pdList = new ArrayList<PageData>();
					for(PageData comPageData :companys){
						PageData pdf = new PageData();
						pdf.put("delivery_vehicle_company_id", comPageData.get("delivery_vehicle_company_id"));
						pdf.put("delivery_vehicle_company_name", comPageData.getString("name"));
						pdList.add(pdf);
					}
					pd.put("companyList", pdList);
				}
			}
		}
		List<PageData> types = new LinkedList<PageData>();
		PageData t0 = new PageData();
		t0.put("type_id", 0);
		t0.put("type_name", "本公司");
		PageData t1 = new PageData();
		t1.put("type_id", 1);
		t1.put("type_name", "第三方");
		types.add(t0);
		types.add(t1);
		pd.put("types", types);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去使用记录页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goUseLog")
	public ModelAndView goUseLog()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = deliveryvehicleService.findById(pd);	//根据ID读取
		mv.setViewName("erp/deliveryvehicle/deliveryvehicle_edit_uselog");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DeliveryVehicle");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			deliveryvehicleService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DeliveryVehicle到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("配送组织Id");	//1
		titles.add("配送组织名称");	//2
		titles.add("配送日期");	//3
		titles.add("车牌号");	//4
		titles.add("联系人");	//5
		titles.add("联系电话");	//6
		titles.add("车辆Id");	//7
		titles.add("所属公司Id");	//8
		titles.add("所属公司名称");	//9
		titles.add("标准金额");	//10
		titles.add("标准时间");	//11
		titles.add("标准里程");	//12
		titles.add("标准时间外金额");	//13
		titles.add("标准里程外金额");	//14
		titles.add("类型");	//15
		titles.add("状态");	//16
		titles.add("出勤时间");	//17
		titles.add("退勤时间");	//18
		titles.add("工作时间");	//19
		titles.add("出勤里程");	//20
		titles.add("退勤里程");	//21
		titles.add("行驶里程");	//22
		titles.add("创建人");	//23
		titles.add("创建时间");	//24
		titles.add("更新人");	//25
		titles.add("更新时间");	//26
		dataMap.put("titles", titles);
		List<PageData> varOList = deliveryvehicleService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("delivery_org_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("delivery_org_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("work_day"));	    //3
			vpd.put("var4", varOList.get(i).getString("vehicle_number"));	    //4
			vpd.put("var5", varOList.get(i).getString("contact"));	    //5
			vpd.put("var6", varOList.get(i).getString("tel"));	    //6
			vpd.put("var7", varOList.get(i).get("vehicle_id").toString());	//7
			vpd.put("var8", varOList.get(i).get("delivery_vehicle_company_id").toString());	//8
			vpd.put("var9", varOList.get(i).getString("delivery_vehicle_company_name"));	    //9
			vpd.put("var10", varOList.get(i).getString("standard_amount"));	    //10
			vpd.put("var11", varOList.get(i).getString("standard_time"));	    //11
			vpd.put("var12", varOList.get(i).getString("standard_mileage"));	    //12
			vpd.put("var13", varOList.get(i).getString("out_time_amount"));	    //13
			vpd.put("var14", varOList.get(i).getString("out_mileage_amount"));	    //14
			vpd.put("var15", varOList.get(i).get("type").toString());	//15
			vpd.put("var16", varOList.get(i).get("status").toString());	//16
			vpd.put("var17", varOList.get(i).getString("start_time"));	    //17
			vpd.put("var18", varOList.get(i).getString("end_time"));	    //18
			vpd.put("var19", varOList.get(i).getString("total_time"));	    //19
			vpd.put("var20", varOList.get(i).getString("start_mileage"));	    //20
			vpd.put("var21", varOList.get(i).getString("end_mileage"));	    //21
			vpd.put("var22", varOList.get(i).getString("total_mileage"));	    //22
			vpd.put("var23", varOList.get(i).getString("createby"));	    //23
			vpd.put("var24", varOList.get(i).getString("create_time"));	    //24
			vpd.put("var25", varOList.get(i).getString("updateby"));	    //25
			vpd.put("var26", varOList.get(i).getString("update_time"));	    //26
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
