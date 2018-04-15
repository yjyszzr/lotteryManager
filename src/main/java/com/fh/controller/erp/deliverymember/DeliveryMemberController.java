package com.fh.controller.erp.deliverymember;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

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
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FormType;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.erp.deliverymember.DeliveryMemberManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：配送人员管理
 * 创建人：FH Q313596790
 * 创建时间：2017-10-26
 */
@Controller
@RequestMapping(value="/deliverymember")
public class DeliveryMemberController extends BaseController {
	
	String menuUrl = "deliverymember/list.do"; //菜单地址(权限用)
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
		logBefore(logger, Jurisdiction.getUsername()+"新增DeliveryMember");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData queryHas = new PageData();
		queryHas.put("user_id", pd.get("user_id"));
		queryHas.put("delivery_org_id", pd.get("delivery_org_id"));
		PageData hasData = deliverymemberService.findByUserIdAndOrgId(queryHas);
		if (hasData != null) {
			mv.addObject("msg","fail");
			mv.addObject("info","此配送组织已存在该配送员");
			mv.setViewName("info_fail");
			return mv;
		}
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		
		pd.put("createby", user.getUSER_ID());
		pd.put("create_time", new Date());
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		deliverymemberService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除DeliveryMember");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		deliverymemberService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DeliveryMember");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER); 
		pd.put("updateby", user.getUSER_ID());
		pd.put("update_time", new Date());
		deliverymemberService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryMember");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = deliverymemberService.list(page);	//列出DeliveryMember列表
		if (varList!=null && varList.size()>0) {
			for (PageData object : varList) {
				PageData user = new PageData();
				user.put("USER_ID", object.get("user_id"));
				user = userService.findById(user);
				object.put("user_name", user.get("NAME"));
			}
		}

		mv.setViewName("erp/deliverymember/deliverymember_list");
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
		mv.setViewName("erp/deliverymember/deliverymember_edit");
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
		pd = deliverymemberService.findById(pd);	//根据ID读取
		
		PageData user = new PageData();
		user.put("USER_ID", pd.get("user_id"));
		user = userService.findById(user);
		pd.put("user_name", user.get("NAME"));
		
		mv.setViewName("erp/deliverymember/deliverymember_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DeliveryMember");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			deliverymemberService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出DeliveryMember到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("名称");	//1
		titles.add("配送组织");	//2
		titles.add("人员公司");	//3
		titles.add("人员类型");	//4
		titles.add("岗位类型");	//5
		titles.add("状态");	//6
		titles.add("备注8");	//7
		titles.add("备注9");	//8
		titles.add("备注10");	//9
		titles.add("备注11");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = deliverymemberService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("user_id"));	    //1
			vpd.put("var2", varOList.get(i).get("delivery_org_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("delivery_user_company_id").toString());	//3
			vpd.put("var4", varOList.get(i).get("type").toString());	//4
			vpd.put("var5", varOList.get(i).get("work_type").toString());	//5
			vpd.put("var6", varOList.get(i).get("status").toString());	//6
			vpd.put("var7", varOList.get(i).getString("createby"));	    //7
			vpd.put("var8", varOList.get(i).getString("create_time"));	    //8
			vpd.put("var9", varOList.get(i).getString("updateby"));	    //9
			vpd.put("var10", varOList.get(i).getString("update_time"));	    //10
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
	@RequestMapping(value="/listByOrg")
	public ModelAndView listByOrg() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DeliveryMember");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		List<PageData>	varList = deliverymemberService.listByOrg(pd);	//列出DeliveryMember列表
		if (varList!=null && varList.size()>0) {
			for (PageData object : varList) {
				PageData user = new PageData();
				user.put("USER_ID", object.get("user_id"));
				user = userService.findById(user);
				object.put("user_name", user.get("NAME"));
			}
		}

		mv.setViewName("erp/deliverymember/deliverymember_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
