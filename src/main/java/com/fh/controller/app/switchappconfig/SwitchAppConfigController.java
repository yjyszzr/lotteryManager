package com.fh.controller.app.switchappconfig;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.dto.AppSelectDTO;
import com.fh.entity.dto.ChannelDTO;
import com.fh.entity.dto.SystemDTO;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.service.switchappconfig.SwitchAppConfigManager;
import com.fh.util.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/** 
 * 说明：app配置
 * 创建人：FH Q313596790
 * 创建时间：2018-04-16
 */
@Controller
@RequestMapping(value="/switchappconfig")
public class SwitchAppConfigController extends BaseController {
	
	String menuUrl = "switchappconfig/list.do"; //菜单地址(权限用)
	@Resource(name="switchappconfigService")
	private SwitchAppConfigManager switchappconfigService;
	@Resource(name="userActionLogService")
	private UserActionLogService ACLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SwitchAppConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String points1 = pd.getString("points1");
		String points2 = pd.getString("points2");
		String points3 = pd.getString("points3");
		pd.put("version", points1+"."+points2+"."+points3);
		
		List<PageData> pageDataList = switchappconfigService.querySwitchAppConfig(pd);
		if(pageDataList == null || pageDataList.size() <= 0) {
			pd.put("id", "0");	//备注1
			switchappconfigService.save(pd);
			ACLOG.save("1", "1", "APP开关",pd.getString("version")+"-"+pd.getString("channel"));
			mv.addObject("msg","success");
			mv.setViewName("save_result");
		}else {
			mv.addObject("msg","保存的app开关有重复,请检查");
			mv.setViewName("save_result");
		}
		return mv;
	}
	
	/**获取连级数据
	 * @return
	 */
//	@RequestMapping(value="/getSwitchAppLevels")
//	@ResponseBody
//	public Object getLevels(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String errInfo = "success";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String DICTIONARIES_ID = pd.getString("DICTIONARIES_ID");
//			DICTIONARIES_ID = Tools.isEmpty(DICTIONARIES_ID)?"0":DICTIONARIES_ID;
//			List<Dictionaries>	varList = dictionariesService.listSubDictByParentId(DICTIONARIES_ID); //用传过来的ID获取此ID下的子列表数据
//			List<PageData> pdList = new ArrayList<PageData>();
//			for(Dictionaries d :varList){
//				PageData pdf = new PageData();
//				pdf.put("DICTIONARIES_ID", d.getDICTIONARIES_ID());
//				pdf.put("NAME", d.getNAME());
//				pdList.add(pdf);
//			}
//			map.put("list", pdList);	
//		} catch(Exception e){
//			errInfo = "error";
//			logger.error(e.toString(), e);
//		}
//		map.put("result", errInfo);				//返回结果
//		return AppUtil.returnObject(new PageData(), map);
//	}
//	
	
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除SwitchAppConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = switchappconfigService.findById(pd);
		switchappconfigService.delete(pd);
		ACLOG.save("1", "2", "APP开关",pdOld.getString("version")+"-"+pdOld.getString("channel_name"));
		out.write("success");
		out.close();
	}

	
	@RequestMapping(value="/changeChannelSwitch")
	public ModelAndView changeChannelSwitch() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改开关SwitchAppConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = switchappconfigService.findById(pd);
		switchappconfigService.edit(pd);
		if(pd.getString("turn_on").equals("1")) {
			ACLOG.save("1", "0", "APP开关："+pdOld.getString("version")+"-"+pdOld.getString("channel_name"),"开启");
		}else {
			ACLOG.save("1", "0", "APP开关："+pdOld.getString("version")+"-"+pdOld.getString("channel_name"),"关闭");
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改SwitchAppConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = switchappconfigService.findById(pd);
		switchappconfigService.edit(pd);
		ACLOG.saveByObject("1", "APP开关："+pdOld.getString("version")+"-"+pdOld.getString("channel_name"), pdOld, pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**获取连级数据
	 * @return
	 */
	@RequestMapping(value="/getLevels")
	@ResponseBody
	public Object getLevels(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		List<PageData>	varList = new ArrayList<>();
		List<PageData> pdList = new ArrayList<PageData>();
		try{
			pd = this.getPageData();
			String DICTIONARIES_ID = pd.getString("DICTIONARIES_ID");
			if(Tools.isEmpty(DICTIONARIES_ID)) {
				varList = switchappconfigService.listByAppCodeName(pd);
				for(PageData d :varList){
					PageData pdf = new PageData();
					pdf.put("DICTIONARIES_ID", d.getString("app_code_name"));
					pdf.put("NAME", d.getString("app_name"));
					pdList.add(pdf);
				}
			}else {
				varList = switchappconfigService.listSubDictByParentId(DICTIONARIES_ID); //用传过来的ID获取此ID下的子列表数据
				for(PageData d :varList){
					PageData pdf = new PageData();
					pdf.put("DICTIONARIES_ID", d.getString("channel"));
					pdf.put("NAME", d.getString("channel_name"));
					pdList.add(pdf);
				}
			}

			map.put("list", pdList);	
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	/**获取连级数据
	 * @return
	 */
	@RequestMapping(value="/getQiuDuoDuoLevels")
	@ResponseBody
	public Object getQiuDuoDuoLevels(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		List<PageData>	varList = new ArrayList<>();
		List<PageData> pdList = new ArrayList<PageData>();
		try{
			pd = this.getPageData();
			String DICTIONARIES_ID = "10";
			varList = switchappconfigService.listSubDictByParentId(DICTIONARIES_ID); //用传过来的ID获取此ID下的子列表数据
			for(PageData d :varList){
				PageData pdf = new PageData();
				pdf.put("DICTIONARIES_ID", d.getString("channel"));
				pdf.put("NAME", d.getString("channel_name"));
				pdList.add(pdf);
			}

			map.put("list", pdList);
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表SwitchAppConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String version = pd.getString("version");
		if(null != version && !"".equals(version)){
			pd.put("version", version.trim());
		}
		String platform = pd.getString("platform");
		if(null != platform && !"".equals(platform)){
			pd.put("platform", platform.trim());
		}
		String channel = pd.getString("channel");
		if(null != channel && !"".equals(channel)){
			pd.put("channel", channel.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = switchappconfigService.list(page);	//列出SwitchAppConfig列表
		
		AppSelectDTO appSelectDTO = this.querySelectData();
		
		mv.setViewName("switchappconfig/switchappconfig_list");
		mv.addObject("varList", varList);
		mv.addObject("versionList", appSelectDTO.getVersionList());
		mv.addObject("paltformList", appSelectDTO.getPaltformList());
		mv.addObject("channelList", appSelectDTO.getChannelList());
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 查询下拉数据
	 * @throws Exception
	 */
	public AppSelectDTO querySelectData() throws Exception {
		Page page = new Page();
		AppSelectDTO appSelectDTO = new AppSelectDTO();
		List<PageData>	varList = switchappconfigService.queryList(page);
		List<String> versionList = varList.stream().map(s->s.getString("version")).distinct().collect(Collectors.toList());
		List<SystemDTO> paltformList = new ArrayList<>();
		SystemDTO sysDtoiOS = new SystemDTO();
		sysDtoiOS.setSysName("苹果");
		sysDtoiOS.setPlatform("0");
		SystemDTO sysDtoAndroid = new SystemDTO();
		sysDtoAndroid.setSysName("安卓");
		sysDtoAndroid.setPlatform("1");
		paltformList.add(sysDtoiOS);
		paltformList.add(sysDtoAndroid);
		
		List<String> channelList = varList.stream().map(s->s.getString("channel")).distinct().collect(Collectors.toList());
		appSelectDTO.setVersionList(versionList);
		appSelectDTO.setChannelList(channelList);
		appSelectDTO.setPaltformList(paltformList);
		return appSelectDTO;
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
		mv.setViewName("switchappconfig/switchappconfig_edit");
		List<PageData> list = switchappconfigService.queryChannel();
		
		List<ChannelDTO> channelDTOList = new ArrayList<ChannelDTO>();
		list.forEach(s->{
			ChannelDTO channelDTO = new ChannelDTO();
			channelDTO.setChannel(s.get("channel").toString());
			channelDTO.setChannelName(s.get("channel_name").toString());
			channelDTOList.add(channelDTO);
		});
		
		mv.addObject("channelDTOList",channelDTOList);
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
		pd = switchappconfigService.findById(pd);	//根据ID读取
		
		List<PageData> list = switchappconfigService.queryChannel();
		
		List<ChannelDTO> channelDTOList = new ArrayList<ChannelDTO>();
		list.forEach(s->{
			ChannelDTO channelDTO = new ChannelDTO();
			channelDTO.setChannel(s.get("channel").toString());
			channelDTO.setChannelName(s.get("channel_name").toString());
			channelDTOList.add(channelDTO);
		});
		
		mv.addObject("channelDTOList",channelDTOList);
		
		mv.setViewName("switchappconfig/switchappconfig_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SwitchAppConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			switchappconfigService.deleteAll(ArrayDATA_IDS);
			ACLOG.save("1", "2", "APP开关：批量删除","id："+ DATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出SwitchAppConfig到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("app版本号");	//2
		titles.add("平台:0-ios 1-android");	//3
		titles.add("业务版本:1-交易版 2-资讯版");	//4
		titles.add("0- 关 1- 开");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = switchappconfigService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("version"));	    //2
			vpd.put("var3", varOList.get(i).getString("platform"));	    //3
			vpd.put("var4", varOList.get(i).getString("business_type"));	    //4
			vpd.put("var5", varOList.get(i).getString("turn_on"));	    //5
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
