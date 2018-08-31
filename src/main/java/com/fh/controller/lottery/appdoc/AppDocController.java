package com.fh.controller.lottery.appdoc;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.fh.service.lottery.appdoc.AppDocManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明：app文案说明
 * 创建人：zhangzirong
 * 创建时间：2018-08-29
 */
@Controller
@RequestMapping(value="/appdoc")
public class AppDocController extends BaseController {
	
	String menuUrl = "appdoc/list.do"; //菜单地址(权限用)
	@Resource(name="appdocService")
	private AppDocManager appdocService;
	@Resource(name="userActionLogService")
	private UserActionLogService ACLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增AppDoc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("create_time", DateUtilNew.getCurrentTimeLong());
		appdocService.save(pd);
		ACLOG.save("1", "1", "APP文案信息保存",pd.getString("classify")+"-"+pd.getString("content"));
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
		logBefore(logger, Jurisdiction.getUsername()+"删除AppDoc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} 
		PageData pd = new PageData();
		pd = this.getPageData();
		appdocService.delete(pd);
		ACLOG.save("1", "1", "APP文案信息删除",pd.getString("classify")+"-"+pd.getString("content"));
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改AppDoc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData oldpd = appdocService.findById(pd);
		appdocService.edit(pd);
		ACLOG.saveByObject("1", "APP文案信息删除", oldpd, pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表AppDoc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");			//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = appdocService.list(page);	//列出AppDoc列表
		for(PageData pdata:varList){
			pdata.put("create_time", DateUtilNew.getTimeString(Integer.valueOf(pdata.getString("create_time")), DateUtilNew.ymd_sdf));
		}
		//查询文案分类
		mv.setViewName("lottery/appdoc/appdoc_list");
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
		mv.setViewName("lottery/appdoc/appdoc_edit");
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
		pd = appdocService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/appdoc/appdoc_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 后端预览
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toPreShow")
	@ResponseBody
	public Object toPreShow() throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		pd = appdocService.findById(pd);	//根据ID读取
		map.put("pd", pd);
		return map;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
