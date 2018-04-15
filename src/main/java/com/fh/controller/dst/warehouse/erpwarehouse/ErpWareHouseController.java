package com.fh.controller.dst.warehouse.erpwarehouse;

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
import com.fh.entity.system.User;
import com.fh.service.dst.warehouse.districtmanager.impl.DistrictManagerService;
import com.fh.service.dst.warehouse.erpwarehouse.ErpWareHouseManager;
import com.fh.service.dst.warehouse.serialconfig.impl.GetSerialConfigUtilService;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FormType;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：仓库管理
 * 创建人：FH Q313596790
 * 创建时间：2017-08-16
 */
@Controller
@RequestMapping(value="/erpwarehouse")
public class ErpWareHouseController extends BaseController {
	
	String menuUrl = "erpwarehouse/list.do"; //菜单地址(权限用)
	@Resource(name="erpwarehouseService")
	private ErpWareHouseManager erpwarehouseService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="districtmanagerService")
	private DistrictManagerService districtmanagerService;
	
	@Resource(name="getserialconfigutilService")
	private GetSerialConfigUtilService getSerialConfigUtilService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ErpWareHouse");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("warehouse_id", this.get32UUID());	//主键
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		if(user != null) {
			pd.put("create_user", user.getUSER_ID());	//创建人
			pd.put("update_user", user.getUSER_ID());	//修改人
		}else {
			pd.put("create_user", "");	//创建人
			pd.put("update_user", "");	//修改人
		}
		pd.put("create_time", DateUtil.getTime());	//创建时间
		pd.put("update_time", DateUtil.getTime());	//修改时间
		erpwarehouseService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除ErpWareHouse");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		erpwarehouseService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ErpWareHouse");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		pd.put("update_user", user.getUSER_ID());
		pd.put("update_time", DateUtil.getTime());
		erpwarehouseService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ErpWareHouse");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		String lastStart = pd.getString("lastStart");				
		String lastEnd = pd.getString("lastEnd");				
		String type = pd.getString("type");				
		String status = pd.getString("status");				
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != lastStart && !"".equals(lastStart)){
			pd.put("lastStart", lastStart.trim()+" 00:00:00");
		}
		if(null != lastEnd && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd.trim()+" 23:59:59");
		}
		if(null != type && !"".equals(type)){
			pd.put("type", type.trim());
		}
		if(null != status && !"".equals(status)){
			pd.put("status", status.trim());
		}
		page.setPd(pd);
		
	   String a=getSerialConfigUtilService.getSerialCode(FormType.PURCHASE_CODE);
		/*PageData a=new PageData();
		a.put("type_code", "s");
		PageData pdtemp=getSerialConfigUtilService.findByPd(a);
	    */
		
		List<PageData>	varList = erpwarehouseService.list(page);	//列出ErpWareHouse列表
		if(CollectionUtils.isNotEmpty(varList)) {
			varList.parallelStream().forEach(pageData->{
				String userId = pageData.getString("create_user");
				if(StringUtils.isNotEmpty(userId)) {
					try {
						User user = userService.getUserAndRoleById(userId);
						if(user != null) {
							pageData.put("create_user", user.getNAME());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		mv.setViewName("warehouse/erpwarehouse/erpwarehouse_list");
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
		mv.setViewName("warehouse/erpwarehouse/erpwarehouse_edit");
		List<PageData> districts = districtmanagerService.listAll(new PageData());
		mv.addObject("districts", districts);
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
		pd = erpwarehouseService.findById(pd);	//根据ID读取
		mv.setViewName("warehouse/erpwarehouse/erpwarehouse_edit");
		List<PageData> districts = districtmanagerService.listAll(new PageData());
		mv.addObject("districts", districts);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ErpWareHouse");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			erpwarehouseService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ErpWareHouse到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("仓库编码");	//1
		titles.add("仓库名称");	//2
		titles.add("上级仓库编码");	//3
		titles.add("上级仓库名称");	//3.2
		titles.add("仓库类型");	//4
		titles.add("仓库地址");	//5
		titles.add("状态");	//6
		titles.add("经度");	//7
		titles.add("纬度");	//8
		titles.add("区域");	//9
		titles.add("创建人");	//10
		titles.add("创建时间");	//11
		titles.add("修改人");	//12
		titles.add("修改时间");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = erpwarehouseService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("warehouse_code"));	    //1
			vpd.put("var2", varOList.get(i).getString("warehouse_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("parent_code"));	    //3
			vpd.put("var4", varOList.get(i).getString("parent_name"));	    //3.2
			String warehouse_type = varOList.get(i).get("warehouse_type").toString();
			if("0".equals(warehouse_type)) {
				vpd.put("var5","总仓");          //4
			}else if("1".equals(warehouse_type)) {
				vpd.put("var5","大区仓库");          //4
			}else if("2".equals(warehouse_type)) {
				vpd.put("var5","城市仓库");          //4
			}else if("3".equals(warehouse_type)) {
				vpd.put("var5","门店仓库");          //4
			}
			vpd.put("var6", varOList.get(i).getString("address"));	    //5
			String status = varOList.get(i).get("status").toString();
			if("0".equals(status)) {
				vpd.put("var7", "正常");   //6
			}else if("1".equals(status)) {
				vpd.put("var7", "冻结");   //6
			}
			vpd.put("var8", varOList.get(i).getString("longitude"));	    //7
			vpd.put("var9", varOList.get(i).getString("latitude"));	    //8
			vpd.put("var10", varOList.get(i).getString("district_name"));	    //9
			String userId = varOList.get(i).getString("create_user");
			if(StringUtils.isNotEmpty(userId)) {
				try {
					User user = userService.getUserAndRoleById(userId);
					if(user != null) {
						vpd.put("var11", user.getNAME());	    //10
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			vpd.put("var12", sdf.format(varOList.get(i).get("create_time")));	    //11
			String upUserId = varOList.get(i).getString("update_user");
			if(StringUtils.isNotEmpty(upUserId)) {
				try {
					User user = userService.getUserAndRoleById(upUserId);
					if(user != null) {
						vpd.put("var13", user.getNAME());	    //12
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			vpd.put("var14", sdf.format(varOList.get(i).get("update_time")));	    //13
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**判断仓库编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasCode")
	@ResponseBody
	public Object hasCode(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(erpwarehouseService.findByPd(pd) == null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断仓库是否存在
	 * @return
	 */
	@RequestMapping(value="/hasCodeAndParentCode")
	@ResponseBody
	public Object hasCodeAndParentCode(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(erpwarehouseService.findByPd(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
