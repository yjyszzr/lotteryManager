package com.fh.controller.dst.purchasegroup;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import net.sf.json.JSONArray;

import com.fh.service.dst.purchasegroup.PurchaseGroupManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.user.impl.UserService;

/** 
 * 说明：采购组
 * 创建人：FH Q313596790
 * 创建时间：2017-10-13
 */
@Controller
@RequestMapping(value="/purchasegroup")
public class PurchaseGroupController extends BaseController {
	
	String menuUrl = "purchasegroup/list.do"; //菜单地址(权限用)
	@Resource(name="purchasegroupService")
	private PurchaseGroupManager purchasegroupService;
	
	@Resource(name="userService")
	private UserService userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PurchaseGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData old = purchasegroupService.findByName(pd);
		if(old!=null) {
			mv.addObject("errMess","1");
			mv.setViewName("dst/purchasegroup/purchasegroup_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd",pd);
		}else {
//		pd.put("purchase_group_id", this.get32UUID());	//主键
		pd.put("purchase_group_id", "0");	//id
		
		pd.put("createby", getSessionUser().getUSER_ID());	//创建人
		pd.put("create_time", new Timestamp(System.currentTimeMillis()));	//床建时间
		pd.put("updateby",getSessionUser().getUSER_ID());	//更新人
		pd.put("update_time",new Timestamp(System.currentTimeMillis()));	//更新人
		purchasegroupService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		}
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除PurchaseGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		purchasegroupService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PurchaseGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("updateby",getSessionUser().getUSER_ID());	//更新人
		pd.put("update_time",new Timestamp(System.currentTimeMillis()));	//更新人
		purchasegroupService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PurchaseGroup");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = purchasegroupService.list(page);	//列出PurchaseGroup列表
		if(varList!=null && varList.size()>0) {
			for(PageData var : varList) {
				PageData userPara = new PageData();
				userPara.put("USER_ID", var.getString("createby"));
				PageData user = userService.findById(userPara);
				if(user !=null ) {
					var.put("USERNAME", user.getString("USERNAME"));
				}
			}
		}
		mv.setViewName("dst/purchasegroup/purchasegroup_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**
	 * 保存关联用户
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savePurchaseGPUser")
	public ModelAndView savePurchaseGPUser() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"savePurchaseGPUser");
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String idStr = pd.getString("userIds");
		String[] userIds = idStr.split(",");
		if(userIds.length==0) {
			return null;
		}
		List<String> userIdsList = new ArrayList<>();
		userIdsList.addAll(Arrays.asList(userIds));
		String purchase_group_id= pd.getString("purchase_group_id");
		List<PageData> gpUser = new ArrayList<>();
		List<PageData> oldUsers = purchasegroupService.listAllUserByGP(pd);
		List<String> oldUserIds = new ArrayList<String>();
		List<String> oldUserIds2 = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(oldUsers)) {
			for( PageData oldUser  : oldUsers) {
				String oldUserId= oldUser.getString("user_id");
				
				oldUserIds.add(oldUserId);
				oldUserIds2.add(oldUserId);
			}
			//删除已取消的
			oldUserIds.removeAll(userIdsList);
			for(String oldUserId : oldUserIds) {
				PageData toDelPd = new PageData();
				toDelPd.put("purchase_group_id", purchase_group_id);
				toDelPd.put("user_id", oldUserId);
				purchasegroupService.deleteGpUser(toDelPd);
			}
			
		}
		//需要添加的
		List<PageData> toAdds = new ArrayList<PageData>();
		//去除已存在的
		userIdsList.removeAll(oldUserIds2);
		for(String userid : userIdsList) {
			PageData toAddPd = new PageData();
			toAddPd.put("purchase_group_id", purchase_group_id);
			toAddPd.put("user_id", userid);
			toAdds.add(toAddPd);
		}
		if(toAdds.size()>0) {
			purchasegroupService.batchInsertGpUser(toAdds);
		}
		
		return null;
		
	}
	/**
	 * 显示用户列表ztree(关联用户)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/goUserTree")
	public ModelAndView goUserTree(Model model,String purchase_group_id,String purchase_group_name )throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<PageData> users = userService.listAllUser(new PageData());
			if(CollectionUtils.isNotEmpty(users)) {
				PageData pd = new PageData();
				pd.put("purchase_group_id", purchase_group_id);
				List<PageData> pds = purchasegroupService.listAllUserByGP(pd);
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
			mv.addObject("purchase_group_id",purchase_group_id);
			mv.addObject("purchase_group_name",purchase_group_name);
			mv.setViewName("dst/purchasegroup/purchase_group_user");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		mv.setViewName("dst/purchasegroup/purchasegroup_edit");
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
		pd = purchasegroupService.findById(pd);	//根据ID读取
		mv.setViewName("dst/purchasegroup/purchasegroup_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PurchaseGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasegroupService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	 /**批量删除采购组用户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteGpUsers")
	@ResponseBody
	public Object deleteGpUsers() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+" deleteGpUsers");
		
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			purchasegroupService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PurchaseGroup到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id");	//1
		titles.add("采购组名称");	//2
		titles.add("创建人");	//3
		titles.add("床建时间");	//4
		titles.add("更新人");	//5
		titles.add("更新时间");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = purchasegroupService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("purchase_group_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("purchase_group_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("createby"));	    //3
			vpd.put("var4", varOList.get(i).getString("create_time"));	    //4
			vpd.put("var5", varOList.get(i).getString("updateby"));	    //5
			vpd.put("var6", varOList.get(i).getString("update_time"));	    //6
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
