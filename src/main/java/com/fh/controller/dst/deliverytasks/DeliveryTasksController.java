package com.fh.controller.dst.deliverytasks;

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
import com.fh.service.dst.deliverytasks.DeliveryTasksManager;
import com.fh.service.dst.pgtorder.PgtOrderManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.deliverylog.DeliveryLogManager;
import com.fh.service.erp.deliverymember.DeliveryMemberManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/** 
 * 说明：派送任务
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 */
@Controller
@RequestMapping(value="/deliverytasks")
public class DeliveryTasksController extends BaseController {
	
	String menuUrl = "deliverytasks/list.do"; //菜单地址(权限用)
	@Resource(name="deliverytasksService")
	private DeliveryTasksManager deliverytasksService;
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	@Resource(name="deliverymemberService")
	private DeliveryMemberManager deliverymemberService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="pgtorderService")
	private PgtOrderManager pgtorderService;
	@Resource(name="deliverylogService")
	private DeliveryLogManager deliverylogService;

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DeliveryTasks");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		User user = getSessionUser();
		//默认数据
		pd.put("createby",user.getUSER_ID());
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		pd.put("create_time",curTime);
		pd.put("updateby",user.getUSER_ID());
		pd.put("update_time", curTime);
		pd.put("status", "0");
//		pd.put("delivery_tasks_id", this.get32UUID());	//主键
		deliverytasksService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除DeliveryTasks");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		deliverytasksService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DeliveryTasks");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		deliverytasksService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**修改状态
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/upState")
	public ModelAndView upState(PrintWriter pw,Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"upState");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//没有明细不允许提交
		if("1".equals(pd.getString("status"))) {
			page.setPd(pd);
			List<PageData> orders   = pgtorderService.list(page);
			if(orders.size()==0) {
				pw.print("2");
				pw.flush();
				pw.close();
				return null;
			}else {
				//记录
				for(PageData order : orders) {
					PageData recorde = new PageData();
					recorde.put("order_sn",order.getString("order_sn") );
					Timestamp curTime = new Timestamp(System.currentTimeMillis());
					recorde.put("delivery_log_time", curTime);
					User user = getSessionUser();
					//默认数据
					recorde.put("createby",user.getUSER_ID());
					recorde.put("create_time",curTime);
					recorde.put("updateby",user.getUSER_ID());
					recorde.put("update_time", curTime);
					recorde.put("delivery_log_status", 0);
//订单已派件，配送员｛配送任务的配送员名称｝，配送员电话｛配送任务的配送员电话｝ 
					String content = "订单已派件，配送员"+order.getString("user_name")+"，配送员电话"+order.getString("user_tel");
					recorde.put("delivery_log_content", content);
					deliverylogService.save(recorde);
				}
			}
			
		}
		deliverytasksService.edit(pd);
		
		pw.print("1");
		pw.flush();
		pw.close();
		return null;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryTasks");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList;	//列出DeliveryTasks列表
		List<PageData> orgs = fillDeliveryOrgs(mv	);
		if(orgs==null || orgs.size()==0) {
			varList = new ArrayList<>();
		}else {
			String delivery_org_id = pd.getString("delivery_org_id");
			if(StringUtil.isEmptyStr(delivery_org_id)) {
				pd.put("delivery_org_id", orgs.get(0).getString("delivery_org_id"));
			}else {
				boolean isValible = false;
				//判断是否越权
				for(int i=0;i<orgs.size();i++) {
					if(delivery_org_id.equals(orgs.get(i).getString("delivery_org_id"))) {
						isValible = true;
						break;
					}
				}
				if(!isValible) {
					pd.put("delivery_org_id", orgs.get(0).getString("delivery_org_id"));
				}
			}
			varList = deliverytasksService.list(page);	
		}
		
		mv.setViewName("dst/deliverytasks/deliverytasks_list");
		fillUserName(varList,"createby",userService);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listUserForSelect")
	public ModelAndView listUserForSelect(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryTasks");
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
		List<PageData>	varList; //users 
		mv.addObject("deliveryOrgs", orgs);
		if(orgs==null || orgs.size()==0) {
			varList = new ArrayList<>();
		}else {
			String delivery_org_id = pd.getString("delivery_org_id");
			if(StringUtil.isEmptyStr(delivery_org_id)) {
				pd.put("delivery_org_id", orgs.get(0).getString("delivery_org_id"));
			}else {
				boolean isValible = false;
				//判断是否越权
				for(int i=0;i<orgs.size();i++) {
					if(delivery_org_id.equals(orgs.get(i).getString("delivery_org_id"))) {
						isValible = true;
						break;
					}
				}
				if(!isValible) {
					pd.put("delivery_org_id", orgs.get(0).getString("delivery_org_id"));
				}
			}
			varList = deliverymemberService.listAll(pd);	
		}
		List<PageData> users= new ArrayList<>();
		
		for(int i=0;i<varList.size();i++) {
			PageData user = new PageData();
			user.put("USER_ID", varList.get(i).get("user_id"));

			user = userService.findById(user);
			users.add(user);
		}
		
		//----------
	//	List<PageData>	varList = deliverytasksService.list(page);	//列出DeliveryTasks列表
		mv.setViewName("dst/deliverytasks/user_list_for_select_byorg");
					  //dst/deliverytasks/deliverytasks_list
		mv.addObject("userList", users);
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
		String code=getSerialConfigUtilService.getSerialCode(FormType.DELIVERY_TASK_CODE);
		pd.put("delivery_tasks_code", code);
		fillDeliveryOrgs(mv);
		List<PageData>	varList; //列出PgtOrder列表
		mv.setViewName("dst/deliverytasks/deliverytasks_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	/**
	 * 向request中添加所属组织信息
	 * @param mv
	 * @throws Exception
	 */
	public List<PageData> fillDeliveryOrgs(ModelAndView mv ) throws Exception {
		PageData orgPara = new PageData();
		orgPara.put("user_id", getSessionUser().getUSER_ID());
		List<PageData> orgs = deliverymemberService.listAll(orgPara);
		if(orgs!=null) {
			for(int i=orgs.size()-1;i>=0;i--) {
				if(StringUtil.isEmptyStr(orgs.get(i).getString("delivery_org_name"))){
					orgs.remove(i);
				}
			}
		}
		mv.addObject("deliveryOrgs", orgs);
		return orgs;
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
		pd = deliverytasksService.findById(pd);	//根据ID读取
		fillDeliveryOrgs(mv);
		mv.setViewName("dst/deliverytasks/deliverytasks_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DeliveryTasks");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			deliverytasksService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DeliveryTasks到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//1
		titles.add("派送编码");	//2
		titles.add("派送组织ID");	//3
		titles.add("工作日");	//4
		titles.add("车辆工作ID");	//5
		titles.add("车牌号");	//6
		titles.add("车辆联系人");	//7
		titles.add("车辆电话");	//8
		titles.add("userid");	//9
		titles.add("人员名称");	//10
		titles.add("人员电话");	//11
		titles.add("类型");	//12
		titles.add("状态");	//13
		titles.add("创建人");	//14
		titles.add("创建时间");	//15
		titles.add("更新人");	//16
		titles.add("更新时间");	//17
		dataMap.put("titles", titles);
		List<PageData> varOList = deliverytasksService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("delivery_tasks_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("delivery_tasks_code"));	    //2
			vpd.put("var3", varOList.get(i).get("delivery_org_id").toString());	//3
			vpd.put("var4", varOList.get(i).getString("work_day"));	    //4
			vpd.put("var5", varOList.get(i).get("delivery_vehicle_work_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("vehicle_number"));	    //6
			vpd.put("var7", varOList.get(i).getString("vehicle_contact"));	    //7
			vpd.put("var8", varOList.get(i).getString("vehicle_tel"));	    //8
			vpd.put("var9", varOList.get(i).getString("user_id"));	    //9
			vpd.put("var10", varOList.get(i).getString("user_name"));	    //10
			vpd.put("var11", varOList.get(i).getString("user_tel"));	    //11
			vpd.put("var12", varOList.get(i).get("type").toString());	//12
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
