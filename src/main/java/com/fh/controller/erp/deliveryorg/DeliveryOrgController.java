package com.fh.controller.erp.deliveryorg;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.erp.deliveryorg.DeliveryOrgManager;
import com.fh.service.erp.deliveryusercompany.DeliveryUserCompanyManager;
import com.fh.service.erp.deliveryvehiclecompany.DeliveryVehicleCompanyManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：配送组织
 * 创建人：FH Q313596790
 * 创建时间：2017-10-24
 */
@Controller
@RequestMapping(value="/deliveryorg")
public class DeliveryOrgController extends BaseController {
	
	String menuUrl = "deliveryorg/list.do"; //菜单地址(权限用)
	@Resource(name="deliveryorgService")
	private DeliveryOrgManager deliveryorgService;
	
	@Resource(name="deliveryusercompanyService")
	private DeliveryUserCompanyManager deliveryusercompanyService;
	
	@Resource(name="deliveryvehiclecompanyService")
	private DeliveryVehicleCompanyManager deliveryvehiclecompanyService;
	
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService  getSerialConfigUtilService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	/***/
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DeliveryOrg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		String delivery_org_code = getSerialConfigUtilService.getSerialCode(FormType.LOGISTICS_COMPANY_CODE);
		pd.put("delivery_org_code", delivery_org_code);
		pd.put("createby", user.getUSER_ID());
		pd.put("create_time", new Date());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		deliveryorgService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public Map<String, String> delete() throws Exception{
		Map<String, String> retMap = new HashMap<>();
		logBefore(logger, Jurisdiction.getUsername()+"删除DeliveryOrg");
		retMap.put("info", "没有权限");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return retMap;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	vehicleList = deliveryvehiclecompanyService.listByOrg(pd);
		if (vehicleList != null && vehicleList.size() > 0) {
			retMap.put("info", "存在车辆公司不能删除");
			return retMap;
		}
		List<PageData> userList = deliveryusercompanyService.listByOrg(pd);
		if (userList != null && userList.size() > 0) {
			retMap.put("info", "存在人员公司不能删除");
			return retMap;
		}
		deliveryorgService.delete(pd);
		retMap.put("msg", "success");
		retMap.put("info", "删除成功");
		return retMap;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DeliveryOrg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		deliveryorgService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryOrg");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = deliveryorgService.list(page);	//列出DeliveryOrg列表
		mv.setViewName("erp/deliveryorg/deliveryorg_list");
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
		mv.setViewName("erp/deliveryorg/deliveryorg_edit");
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
		pd = deliveryorgService.findById(pd);	//根据ID读取
		mv.setViewName("erp/deliveryorg/deliveryorg_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DeliveryOrg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			deliveryorgService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DeliveryOrg到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("编码");	//1
		titles.add("名称");	//2
		titles.add("人员ID");	//3
		titles.add("联系人");	//4
		titles.add("电话");	//5
		titles.add("地址");	//6
		titles.add("类型");	//7
		titles.add("状态");	//8
		titles.add("配送仓库");	//9
		titles.add("备货仓库");	//10
		titles.add("创建人");	//11
		titles.add("创建时间");	//12
		titles.add("更新人");	//13
		titles.add("更新时间");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = deliveryorgService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("delivery_org_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("delivery_org_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("user_id"));	    //3
			vpd.put("var4", varOList.get(i).getString("user_name"));	    //4
			vpd.put("var5", varOList.get(i).getString("user_tel"));	    //5
			vpd.put("var6", varOList.get(i).getString("user_address"));	    //6
			vpd.put("var7", varOList.get(i).get("type").toString());	//7
			vpd.put("var8", varOList.get(i).get("status").toString());	//8
			vpd.put("var9", varOList.get(i).getString("store_sn"));	    //9
			vpd.put("var10", varOList.get(i).getString("delivery_store_sn"));	    //10
			vpd.put("var11", varOList.get(i).getString("createby"));	    //11
			vpd.put("var12", varOList.get(i).getString("create_time"));	    //12
			vpd.put("var13", varOList.get(i).getString("updateby"));	    //13
			vpd.put("var14", varOList.get(i).getString("update_time"));	    //14
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
	
	/**显示用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userListSelect")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	userList = userService.listUsersSelect(pd);	//列出用户列表
		mv.setViewName("erp/deliveryorg/user_list_select");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**配送人员添加组织
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listSelect")
	public ModelAndView listSelect(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryOrg");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = deliveryorgService.list(page);	//列出DeliveryOrg列表
		mv.setViewName("erp/deliveryorg/deliveryorg_list_select");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@RequestMapping(value="/listSelectForOrder")
	public ModelAndView listSelectForOrder(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryOrg");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = getSessionUser();
		if(user != null) {
			String userId = user.getUSER_ID();
			if(StringUtils.isNotEmpty(userId)) {
				PageData pageData = new PageData();
				pageData.put("user_id", userId);
				List<PageData> orgList = deliveryorgService.listAllByUserId(pageData);
				mv.addObject("varList", orgList);
			}
		}
		mv.setViewName("erp/deliveryorg/deliveryorg_list_select_order");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
}
