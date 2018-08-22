package com.fh.controller.lottery.appupdatelog;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.appupdatelog.AppUpdateLogManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.service.switchappconfig.SwitchAppConfigManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：版本升级管理
 * 创建人：FH Q313596790
 * 创建时间：2018-08-06
 */
@Controller
@RequestMapping(value="/appupdatelog")
public class AppUpdateLogController extends BaseController {
	
	String menuUrl = "appupdatelog/list.do"; //菜单地址(权限用)
	@Resource(name="appupdatelogService")
	private AppUpdateLogManager appupdatelogService;
	
	@Resource(name="switchappconfigService")
	private SwitchAppConfigManager switchappconfigService;
	
	@Resource(name = "userActionLogService")
	private UserActionLogService ACLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增AppUpdateLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String version = pd.getString("points1")+"."+pd.getString("points2")+"."+pd.getString("points3");
		String updateLog = this.createUpdateLog(pd);
		pd.put("app_code_name", pd.getString("app_name"));
		pd.put("version", version);
		pd.put("download_url", pd.getString("apk_path"));
		pd.put("update_log", updateLog);
		pd.put("update_time", DateUtilNew.getCurrentTimeLong());
		pd.put("update_install", pd.getString("update_install"));
		pd.put("channel", pd.getString("channel"));
		appupdatelogService.save(pd);
		ACLOG.save("1", "1", "app升级管理,新增操作"+pd.getString("channel"), pd.toString());

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	public String createUpdateLog(PageData pd) {
		String retunStr = "";
		List<String> strList = new ArrayList<>();
		String text1 = pd.getString("text1").trim();
		String text2 = pd.getString("text2").trim();
		String text3 = pd.getString("text3").trim();
		String text4 = pd.getString("text4").trim();
		strList.add(text1);
		strList.add(text2);
		strList.add(text3);
		strList.add(text4);
		for(String str:strList) {
			if(StringUtils.isNotEmpty(str)) {
				retunStr += str+";";
			}
		}
		return retunStr;
	}
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除AppUpdateLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		appupdatelogService.delete(pd);
		ACLOG.save("1", "1", "app升级管理,删除操作"+pd.getString("channel"), pd.toString());
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改AppUpdateLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData oldPd = appupdatelogService.findById(pd);
		
		String version = pd.getString("points1")+"."+pd.getString("points2")+"."+pd.getString("points3");
		String updateLog = this.createUpdateLog(pd);
		PageData newPd = new PageData();
		newPd.put("app_code_name", pd.getString("app_name"));
		newPd.put("channel", pd.getString("channel"));
		newPd.put("version", version);
		newPd.put("download_url", pd.getString("apk_path"));
		newPd.put("update_log", updateLog);
		newPd.put("update_time", DateUtilNew.getCurrentTimeLong());
		newPd.put("update_install", pd.getString("update_install"));
		newPd.put("id", Integer.valueOf(pd.getString("id")));
		appupdatelogService.edit(newPd);
		ACLOG.saveByObject("1", "app升级管理,修改保存:" + pd.getString("channel"), oldPd, newPd);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"列表AppUpdateLog");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();//{app_name=, channel=c10020}
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = appupdatelogService.list(page);	//列出AppUpdateLog列表
		List<PageData>  appList = switchappconfigService.listByAppCodeName(pd);
        Map<String, String> appMap = appList.stream().collect(Collectors.toMap(s->s.getString("app_code_name"), s->s.getString("app_name")));
		List<PageData>  channelList = switchappconfigService.queryChannel();
        Map<String, String> channelMap = channelList.stream().collect(Collectors.toMap(s->s.getString("channel"), s->s.getString("channel_name")));
        
        List<PageData>	newVarList = new ArrayList<>();
		for(PageData pdata:varList) {
			String updateTime = pdata.getString("update_time");
			String updateInstall = pdata.getString("update_install");
			String appCodeName = pdata.getString("app_code_name");
			String channel = pdata.getString("channel");
			String appName = appMap.get(appCodeName);
			String channelName = channelMap.get(channel);
			pdata.put("id", pdata.getString("id"));
			pdata.put("version", pdata.getString("version"));
			pdata.put("app_name", appName);
			pdata.put("channel_name", channelName);
			pdata.put("update_install",updateInstall.equals("1")?"是":"否");
			pdata.put("update_time", DateUtilNew.getTimeString(Integer.valueOf(updateTime),DateUtilNew.datetimeFormat));
			newVarList.add(pdata);
		}
		
		mv.setViewName("lottery/appupdatelog/appupdatelog_list");
		mv.addObject("varList", newVarList);
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
		mv.setViewName("lottery/appupdatelog/appupdatelog_edit");
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
		pd = appupdatelogService.findById(pd);	//根据ID读取
		
		PageData queryPd = new PageData();
		queryPd.put("channel", pd.getString("channel"));
		List<PageData>  appList = switchappconfigService.listByAppCodeName(queryPd);
        Map<String, String> appMap = appList.stream().collect(Collectors.toMap(s->s.getString("app_code_name"), s->s.getString("app_name")));
		List<PageData>  channelList = switchappconfigService.queryChannel();
        Map<String, String> channelMap = channelList.stream().collect(Collectors.toMap(s->s.getString("channel"), s->s.getString("channel_name")));
        pd.put("channel_name", channelMap.get(pd.getString("channel")));
        pd.put("app_name", appMap.get(pd.getString("app_code_name")));
        
        String version = pd.getString("version");
        List<String> vList = Arrays.asList(version.split("\\."));
        for(int i = 0 ; i < vList.size(); i++) {
        	pd.put("points"+(i+1), vList.get(i));
        }
        
        String updateLog = pd.getString("update_log");
        List<String> logList = Arrays.asList(updateLog.split(";"));
        for(int i = 0 ; i < logList.size(); i++) {
        	 pd.put("text"+(i+1), logList.get(i));
        }
        
        String downLoadUrl = pd.getString("download_url");
        pd.put("apk_path", downLoadUrl);
		mv.setViewName("lottery/appupdatelog/appupdatelog_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除AppUpdateLog");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			appupdatelogService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出AppUpdateLog到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = appupdatelogService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString());	//1
			vpd.put("var2", varOList.get(i).get("app_code_name").toString());	//2
			vpd.put("var3", varOList.get(i).getString("version"));	    //3
			vpd.put("var4", varOList.get(i).getString("download_url"));	    //4
			vpd.put("var5", varOList.get(i).getString("update_log"));	    //5
			vpd.put("var6", varOList.get(i).get("update_time").toString());	//6
			vpd.put("var7", varOList.get(i).get("update_install").toString());	//7
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
