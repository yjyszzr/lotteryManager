package com.fh.controller.lottery.usermanagercontroller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.http.util.TextUtils;
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
import com.fh.util.DateUtilNew;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.UserRealManagerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;

/** 
 * 说明：用户列表
 * 创建人：FH Q313596790
 * 创建时间：2018-04-23
 */
@Controller
@RequestMapping(value="/usermanagercontroller")
public class UserManagerControllerController extends BaseController {
	
	String menuUrl = "usermanagercontroller/list.do"; //菜单地址(权限用)
	@Resource(name="usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name="useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;
	@Resource(name="userrealmanagerService")
	private UserRealManagerService userrealmanagerService;
	@Resource(name="userbankmanagerService")
	private UserBankManagerService userbankmanagerService;
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增UserManagerController");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("user_id", this.get32UUID());	//主键
		pd.put("user_id", "0");	//备注1
		pd.put("user_name", "");	//备注2
		pd.put("mobile", "");	//备注3
		pd.put("email", "");	//备注4
		pd.put("reg_time", "0");	//备注17
		pd.put("last_time", "0");	//备注19
		pd.put("reg_from", "");	//备注24
		pd.put("user_status", "0");	//备注27
		usermanagercontrollerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除UserManagerController");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		usermanagercontrollerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改UserManagerController");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		usermanagercontrollerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表UserManagerController");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String nameChosen = pd.getString("name_chosen");
		if(!TextUtils.isEmpty(nameChosen) && !nameChosen.equals("0")) {
			pd.put("name_chosen", nameChosen);
		}
		String lastStart = pd.getString("lastStart");
		String lastEnd = pd.getString("lastEnd");
		if(!TextUtils.isEmpty(lastStart) && !TextUtils.isEmpty(lastEnd)) {
			long start = DateUtilNew.getMilliSecondsByStr(lastStart);
			long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
			pd.put("lastStart",start);
			pd.put("lastEnd", end);
		}
		page.setPd(pd);
		List<PageData>	varList = usermanagercontrollerService.list(page);	//列出UserManagerController列表
		if(varList != null) {
			for(int i = 0;i < varList.size();i++) {
				PageData pData = varList.get(i);
				Integer userId = (int) pData.get("user_id");
				Double val = useraccountmanagerService.getTotalConsumByUserId(userId);
				if(val == null) {
					val = 0d;
				}
				pData.put("total",val);	
				//获取个人充值总金额
				Double valR = useraccountmanagerService.getTotalRechargeByUserId(userId);
				if(valR == null) {
					valR = 0d;
				}
				pData.put("rtotal",valR);
				//获取个人获奖总金额
				Double valA = useraccountmanagerService.getTotalAwardByUserId(userId);
				if(valA == null) {
					valA = 0d;
				}
				pData.put("atotal", valA);
				//获取个人总金额
				Double valRest = useraccountmanagerService.getTotalRestByUserId(userId);
				if(valRest == null) {
					valRest = 0d;
				}
				pData.put("resttotal",valRest);
//				System.out.println("用户：" + userId + " 总资金额："  + val + " 充值金额:" +valR);
			}
		}
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_list");
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
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**detail详情页
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = usermanagercontrollerService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	@RequestMapping(value="/godetail")
	public ModelAndView goDetail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = usermanagercontrollerService.findById(pd);
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_detail");
		mv.addObject("msg","detail");
		mv.addObject("pd",pd);
		return mv;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除UserManagerController");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			usermanagercontrollerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出UserManagerController到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		titles.add("备注19");	//19
		titles.add("备注20");	//20
		titles.add("备注21");	//21
		titles.add("备注22");	//22
		titles.add("备注23");	//23
		titles.add("备注24");	//24
		titles.add("备注25");	//25
		titles.add("备注26");	//26
		titles.add("备注27");	//27
		titles.add("备注28");	//28
		titles.add("备注29");	//29
		titles.add("备注30");	//30
		titles.add("备注31");	//31
		dataMap.put("titles", titles);
		List<PageData> varOList = usermanagercontrollerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("user_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("user_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("mobile"));	    //3
			vpd.put("var4", varOList.get(i).getString("email"));	    //4
			vpd.put("var5", varOList.get(i).getString("password"));	    //5
			vpd.put("var6", varOList.get(i).getString("salt"));	    //6
			vpd.put("var7", varOList.get(i).getString("nickname"));	    //7
			vpd.put("var8", varOList.get(i).get("sex").toString());	//8
			vpd.put("var9", varOList.get(i).get("birthday").toString());	//9
			vpd.put("var10", varOList.get(i).getString("detail_address"));	    //10
			vpd.put("var11", varOList.get(i).getString("headimg"));	    //11
			vpd.put("var12", varOList.get(i).getString("user_money"));	    //12
			vpd.put("var13", varOList.get(i).getString("user_money_limit"));	    //13
			vpd.put("var14", varOList.get(i).getString("frozen_money"));	    //14
			vpd.put("var15", varOList.get(i).get("pay_point").toString());	//15
			vpd.put("var16", varOList.get(i).get("rank_point").toString());	//16
			vpd.put("var17", varOList.get(i).get("reg_time").toString());	//17
			vpd.put("var18", varOList.get(i).getString("reg_ip"));	    //18
			vpd.put("var19", varOList.get(i).get("last_time").toString());	//19
			vpd.put("var20", varOList.get(i).getString("last_ip"));	    //20
			vpd.put("var21", varOList.get(i).getString("mobile_supplier"));	    //21
			vpd.put("var22", varOList.get(i).getString("mobile_province"));	    //22
			vpd.put("var23", varOList.get(i).getString("mobile_city"));	    //23
			vpd.put("var24", varOList.get(i).getString("reg_from"));	    //24
			vpd.put("var25", varOList.get(i).getString("surplus_password"));	    //25
			vpd.put("var26", varOList.get(i).getString("pay_pwd_salt"));	    //26
			vpd.put("var27", varOList.get(i).get("user_status").toString());	//27
			vpd.put("var28", varOList.get(i).get("pass_wrong_count").toString());	//28
			vpd.put("var29", varOList.get(i).get("user_type").toString());	//29
			vpd.put("var30", varOList.get(i).getString("is_real"));	    //30
			vpd.put("var31", varOList.get(i).getString("user_remark"));	    //31
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
	
	@RequestMapping(value="/toDetail")
	public ModelAndView toDetail() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData rEntity = userrealmanagerService.findById(pd);
		PageData entity = usermanagercontrollerService.findById(pd);
		List<PageData> list = userbankmanagerService.listAllByUser(pd);
		mv.setViewName("lottery/usermanagercontroller/usermanagercontroller_detail");
		mv.addObject("entity",entity);
		mv.addObject("rentity",rEntity);
		mv.addObject("list",list);
		return mv;
	}
}
