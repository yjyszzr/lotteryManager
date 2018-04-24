package com.fh.controller.lottery.userrealmanager;

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
import com.fh.service.lottery.userrealmanager.UserRealManagerManager;

/** 
 * 说明：用户实名认证信息
 * 创建人：FH Q313596790
 * 创建时间：2018-04-24
 */
@Controller
@RequestMapping(value="/userrealmanager")
public class UserRealManagerController extends BaseController {
	
	String menuUrl = "userrealmanager/list.do"; //菜单地址(权限用)
	@Resource(name="userrealmanagerService")
	private UserRealManagerManager userrealmanagerService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增UserRealManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("real_id", this.get32UUID());	//主键
		pd.put("real_id", "0");	//主键ID
		pd.put("user_id", "0");	//用户ID
		pd.put("real_name", "");	//真实姓名
		pd.put("id_code", "");	//身份证号
		pd.put("card_pic1", "");	//身份正面照
		pd.put("card_pic2", "");	//身份证反面照
		pd.put("card_pic3", "");	//手持身份证照
		pd.put("address_now", "");	//现居住地址
		pd.put("address_info", "");	//详细地址
		pd.put("status", "");	//状态
		pd.put("add_time", "0");	//添加时间
		pd.put("reason", "");	//原因
		userrealmanagerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除UserRealManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userrealmanagerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改UserRealManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		userrealmanagerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表UserRealManager");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = userrealmanagerService.list(page);	//列出UserRealManager列表
		mv.setViewName("lottery/userrealmanager/userrealmanager_list");
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
		mv.setViewName("lottery/userrealmanager/userrealmanager_edit");
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
		pd = userrealmanagerService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/userrealmanager/userrealmanager_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除UserRealManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userrealmanagerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出UserRealManager到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("主键ID");	//1
		titles.add("用户ID");	//2
		titles.add("真实姓名");	//3
		titles.add("身份证号");	//4
		titles.add("身份正面照");	//5
		titles.add("身份证反面照");	//6
		titles.add("手持身份证照");	//7
		titles.add("现居住地址");	//8
		titles.add("详细地址");	//9
		titles.add("状态");	//10
		titles.add("添加时间");	//11
		titles.add("原因");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = userrealmanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("real_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("user_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("real_name"));	    //3
			vpd.put("var4", varOList.get(i).getString("id_code"));	    //4
			vpd.put("var5", varOList.get(i).getString("card_pic1"));	    //5
			vpd.put("var6", varOList.get(i).getString("card_pic2"));	    //6
			vpd.put("var7", varOList.get(i).getString("card_pic3"));	    //7
			vpd.put("var8", varOList.get(i).getString("address_now"));	    //8
			vpd.put("var9", varOList.get(i).getString("address_info"));	    //9
			vpd.put("var10", varOList.get(i).getString("status"));	    //10
			vpd.put("var11", varOList.get(i).get("add_time").toString());	//11
			vpd.put("var12", varOList.get(i).getString("reason"));	    //12
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
