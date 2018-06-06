package com.fh.controller.lottery.userloginlog;

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
import com.fh.service.lottery.userloginlog.UserLoginLogManager;

/** 
 * 说明：用户操作日志
 * 创建人：FH Q313596790
 * 创建时间：2018-06-06
 */
@Controller
@RequestMapping(value="/userloginlog")
public class UserLoginLogController extends BaseController {
	
	String menuUrl = "userloginlog/list.do"; //菜单地址(权限用)
	@Resource(name="userloginlogService")
	private UserLoginLogManager userloginlogService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增UserLoginLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("_id", this.get32UUID());	//主键
		pd.put("id", "0");	//id
		pd.put("user_id", "0");	//用户ID
		pd.put("w", "");	//宽
		pd.put("h", "");	//高
		pd.put("imei", "");	//手机串号
		pd.put("login_source", "");	//登录来源
		pd.put("device_channel", "");	//用户设备渠道
		userloginlogService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除UserLoginLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userloginlogService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改UserLoginLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		userloginlogService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表UserLoginLog");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = userloginlogService.list(page);	//列出UserLoginLog列表
		mv.setViewName("lottery/userloginlog/userloginlog_list");
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
		mv.setViewName("lottery/userloginlog/userloginlog_edit");
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
		pd = userloginlogService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/userloginlog/userloginlog_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除UserLoginLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userloginlogService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出UserLoginLog到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id");	//1
		titles.add("用户ID");	//2
		titles.add("登录类型");	//3
		titles.add("登录状态");	//4
		titles.add("登录IP");	//5
		titles.add("登录时间");	//6
		titles.add("退出时间");	//7
		titles.add("设备型号");	//8
		titles.add("手机厂商");	//9
		titles.add("手机型号/设备型号");	//10
		titles.add("手机系统版本号");	//11
		titles.add("宽");	//12
		titles.add("高");	//13
		titles.add("手机串号");	//14
		titles.add("登录来源");	//15
		titles.add("登录参数");	//16
		titles.add("用户设备渠道");	//17
		titles.add("登录结果");	//18
		dataMap.put("titles", titles);
		List<PageData> varOList = userloginlogService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString());	//1
			vpd.put("var2", varOList.get(i).get("user_id").toString());	//2
			vpd.put("var3", varOList.get(i).get("login_type").toString());	//3
			vpd.put("var4", varOList.get(i).get("login_status").toString());	//4
			vpd.put("var5", varOList.get(i).getString("login_ip"));	    //5
			vpd.put("var6", varOList.get(i).get("login_time").toString());	//6
			vpd.put("var7", varOList.get(i).get("logout_time").toString());	//7
			vpd.put("var8", varOList.get(i).getString("plat"));	    //8
			vpd.put("var9", varOList.get(i).getString("brand"));	    //9
			vpd.put("var10", varOList.get(i).getString("mid"));	    //10
			vpd.put("var11", varOList.get(i).getString("os"));	    //11
			vpd.put("var12", varOList.get(i).getString("w"));	    //12
			vpd.put("var13", varOList.get(i).getString("h"));	    //13
			vpd.put("var14", varOList.get(i).getString("imei"));	    //14
			vpd.put("var15", varOList.get(i).getString("login_source"));	    //15
			vpd.put("var16", varOList.get(i).getString("login_params"));	    //16
			vpd.put("var17", varOList.get(i).getString("device_channel"));	    //17
			vpd.put("var18", varOList.get(i).getString("login_result"));	    //18
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
