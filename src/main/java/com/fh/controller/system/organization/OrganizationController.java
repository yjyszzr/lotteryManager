package com.fh.controller.system.organization;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
//import com.fh.service.dst.szystore.impl.SzyStoreService;
import com.fh.service.system.organization.OrganizationManager;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 说明：组织架构管理
 * 创建人：FH Q313596790
 * 创建时间：2017-09-19
 */
@Controller
@RequestMapping(value="/organization")
public class OrganizationController extends BaseController {
	
	String menuUrl = "organization/list.do"; //菜单地址(权限用)
	@Resource(name="organizationService")
	private OrganizationManager organizationService;
	
//	@Resource(name="szystoreService")
//	private SzyStoreService szyStoreService;
	
	@Resource(name="userService")
	private UserService userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("createby", user.getUSER_ID()); // 创建人
		pd.put("create_time", new Date()); // 创建时间
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		organizationService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断组织名称是否存在
	 * @return
	 */
	@RequestMapping(value="/hasOrgName")
	@ResponseBody
	public Object hasOrgName(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(organizationService.findByOrgName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		organizationService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("updateby", user.getUSER_ID()); // 更新人
		pd.put("update_time", new Date()); // 更新时间
		organizationService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 显示仓库列表ztree(关联仓库)
	 * @param model
	 * @return
	 */
//	@RequestMapping(value="/menuqx")
//	public ModelAndView listAllMenu(Model model,String org_id,String org_name)throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		try{
//			List<PageData> stores = szyStoreService.listAll(new PageData());
//			if(CollectionUtils.isNotEmpty(stores)) {
//				PageData pd = new PageData();
//				pd.put("org_id", org_id);
//				List<PageData> pds = organizationService.listByPageData(pd);
//				List<String> list = new ArrayList<String>();
//				if(CollectionUtils.isNotEmpty(pds)) {
//					for (PageData pageData : pds) {
//						list.add(pageData.getString("store_sn"));
//					}
//				}
//				if(CollectionUtils.isNotEmpty(list)) {
//					for (PageData pageData : stores) {
//						if(list.contains(pageData.getString("store_sn"))) {
//							pageData.put("checked", true);
//						}else {
//							pageData.put("checked", false);
//						}
//					}
//				}
//				JSONArray arr = JSONArray.fromObject(stores);
//				String json = arr.toString();
//				json = json.replaceAll("store_sn", "id").replaceAll("store_name", "name");
//				model.addAttribute("zTreeNodes", json);
//			}
//			mv.addObject("org_id",org_id);
//			mv.addObject("org_name",org_name);
//			mv.setViewName("system/organization/menuqx");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
	
	/**
	 * 显示用户列表ztree(关联用户)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuqxUser")
	public ModelAndView menuqxUser(Model model,String org_id,String org_name)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<PageData> users = userService.listAllUser(new PageData());
			if(CollectionUtils.isNotEmpty(users)) {
				PageData pd = new PageData();
				pd.put("org_id", org_id);
				List<PageData> pds = organizationService.listUserByPageData(pd);
				List<String> list = new ArrayList<String>();
				if(CollectionUtils.isNotEmpty(pds)) {
					for (PageData pageData : pds) {
						list.add(pageData.getString("user_id"));
					}
				}
				if(CollectionUtils.isNotEmpty(list)) {
					for (PageData pageData : users) {
						if(list.contains(pageData.getString("USER_ID"))) {
							pageData.put("checked", true);
						}else {
							pageData.put("checked", false);
						}
					}
				}
				JSONArray arr = JSONArray.fromObject(users);
				String json = arr.toString();
				json = json.replaceAll("USER_ID", "id").replaceAll("NAME", "name");
				model.addAttribute("zTreeNodes", json);
			}
			mv.addObject("org_id",org_id);
			mv.addObject("org_name",org_name);
			mv.setViewName("system/organization/menuqxUser");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存关联仓库数据
	 * @throws Exception
	 */
	@RequestMapping(value="/saveMenuqx")
	public void saveMenuqx(@RequestParam String org_id,@RequestParam String org_name,@RequestParam String menuIds,PrintWriter out)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"保存关联仓库数据");
		try{
			String[] stores = Tools.str2StrArray(menuIds);
			String[] orgIds = {org_id};
			organizationService.deleteAllByOrgId(orgIds);
			if(null != menuIds && !"".equals(menuIds.trim())){
				List<PageData> list = new ArrayList<PageData>();
				for (String store : stores) {
					if(StringUtils.isNotEmpty(store)) {
						String[] str = store.split(":");
						PageData pd = new PageData();
						pd.put("org_id", org_id);
						pd.put("org_name", org_name);
						pd.put("store_sn", str[0]);
						pd.put("store_name", str[1]);
						list.add(pd);
					}
				}
				organizationService.saveOrgStore(list);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**保存关联用户数据
	 * @throws Exception
	 */
	@RequestMapping(value="/saveMenuqxUser")
	public void saveMenuqxUser(@RequestParam String org_id,@RequestParam String org_name,@RequestParam String menuIds,PrintWriter out)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"保存关联用户数据");
		try{
			String[] users = Tools.str2StrArray(menuIds);
			String[] orgIds = {org_id};
			organizationService.deleteAllUserByOrgId(orgIds);
			if(null != menuIds && !"".equals(menuIds.trim())){
				List<PageData> list = new ArrayList<PageData>();
				for (String user : users) {
					if(StringUtils.isNotEmpty(user)) {
						String[] str = user.split(":");
						PageData pd = new PageData();
						pd.put("org_id", org_id);
						pd.put("org_name", org_name);
						pd.put("user_id", str[0]);
						pd.put("username", str[1]);
						list.add(pd);
					}
				}
				organizationService.saveOrgUser(list);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Organization");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = organizationService.list(page);	//列出Organization列表
		mv.setViewName("system/organization/organization_list");
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
		mv.setViewName("system/organization/organization_edit");
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
		pd = organizationService.findById(pd);	//根据ID读取
		mv.setViewName("system/organization/organization_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			organizationService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Organization到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("组织名称");	//1
		titles.add("创建人");	//2
		titles.add("创建时间");	//3
		titles.add("更新人");	//4
		titles.add("更新时间");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = organizationService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("org_name"));	    //1
			vpd.put("var2", varOList.get(i).getString("createby"));	    //2
			vpd.put("var3", varOList.get(i).getString("create_time"));	    //3
			vpd.put("var4", varOList.get(i).getString("updateby"));	    //4
			vpd.put("var5", varOList.get(i).getString("update_time"));	    //5
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
