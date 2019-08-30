package com.fh.controller.lottery.popularizeactivity;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.alibaba.druid.support.json.JSONUtils;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

import oracle.net.aso.f;

import com.fh.util.Jurisdiction;
import com.fh.service.lottery.popularizeactivity.PopularizeActivityManager;

/** 
 * 说明：圣和推广活动
 * 创建人：FH Q313596790
 * 创建时间：2019-08-05
 */
@Controller
@RequestMapping(value="/popularizeactivity")
public class PopularizeActivityController extends BaseController {
	
	String menuUrl = "popularizeactivity/list.do"; //菜单地址(权限用)
	@Resource(name="popularizeactivityService")
	private PopularizeActivityManager popularizeactivityService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PopularizeActivity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("act_id", this.get32UUID());	//主键
		String lastStart = pd.getString("start_time");
		String lastEnd = pd.getString("end_time");
		long start = DateUtilNew.getMilliSecondsByStr(lastStart);
		long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
		pd.put("start_time", start);
		pd.put("end_time", end);
		pd.put("act_id", "0");	//id
		pd.put("is_del", "0");	//is_del
		pd.put("is_finish", "-1");	//活动状态 0-有效 1-无效
		  popularizeactivityService.save(pd);
			if (pd.getString("params")!=null) {
			   Map map = (Map)JSONUtils.parse(pd.getString("params"));
			   List gearList = (ArrayList)JSONUtils.parse(map.get("gear_position").toString());
			   List moneyList = (ArrayList)JSONUtils.parse(map.get("gear_position_money").toString());
				 for (int i = 0; i < gearList.size(); i++) {
					 PageData pdConfig = new PageData();
					 pdConfig.put("id", "0"); 
					 pdConfig.put("act_id", pd.getString("act_id"));
					 pdConfig.put("gear_position",gearList.get(i));
					 pdConfig.put("gear_position_money", moneyList.get(i));
					 pdConfig.put("add_time", DateUtilNew.getCurrentTimeLong()); // 时间
				 popularizeactivityService.saveConfig(pdConfig);
			 	}	
			 }

		 
		  
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**上下架
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/onLineOrOffLine")
	public void onLineOrOffLine(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "置顶或者上线操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData activity = popularizeactivityService.findById(pd);
		if("1".equals(pd.getString("is_finish"))) {//下架操作 清楚活动数据
			if("3".equals(activity.getString("act_type"))) {//伯乐奖
				popularizeactivityService.insertHisToUserInfo();//将现有数据备份到历史表
				popularizeactivityService.updateActivityUserInfoByBl();//清楚此次活动数据
				List<String> aclist = popularizeactivityService.queryActivityConfigList(activity);//查询当前活动档位
				if(aclist!=null && aclist.size()>0) {
					popularizeactivityService.deleteConfigRecByConfigId(aclist);//删除挡位领取记录
				}
			}
			if("4".equals(activity.getString("act_type"))) {//荣耀奖
				popularizeactivityService.insertHisToUserInfo();//将现有数据备份到历史表
				popularizeactivityService.updateActivityUserInfoByRy();//清楚此次活动数据
				List<String> aclist = popularizeactivityService.queryActivityConfigList(activity);//查询当前活动档位
				if(aclist!=null && aclist.size()>0) {
					popularizeactivityService.deleteConfigRecByConfigId(aclist);//删除挡位领取记录
				}
			}
		}
		popularizeactivityService.updateById(pd);
		if("0".equals(pd.getString("is_finish"))) {//上架操作 上架新活动后 将冗余同类过期活动和手动下架活动删除（避免定时器重跑）
			popularizeactivityService.deleteByType(activity.getString("act_type"));
		}
		out.write("success");
		out.close();
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除PopularizeActivity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("is_del", "1");	//is_del
		popularizeactivityService.deleteById(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PopularizeActivity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String lastStart = pd.getString("start_time");
		String lastEnd = pd.getString("end_time");
		long start = DateUtilNew.getMilliSecondsByStr(lastStart);
		long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
		pd.put("start_time", start);
		pd.put("end_time", end);
		pd.put("is_del", "0");	//is_del
		popularizeactivityService.edit(pd);
		
		popularizeactivityService.deleteConfigByActId(pd);
		
		if (pd.getString("params")!=null) {
			   Map map = (Map)JSONUtils.parse(pd.getString("params"));
			   List gearList = (ArrayList)JSONUtils.parse(map.get("gear_position").toString());
			   List moneyList = (ArrayList)JSONUtils.parse(map.get("gear_position_money").toString());
			 for (int i = 0; i < gearList.size(); i++) {
				 PageData pdConfig = new PageData();
					 pdConfig.put("id", "0"); 
					 pdConfig.put("act_id", pd.getString("act_id"));
					 pdConfig.put("gear_position",gearList.get(i));
					 pdConfig.put("gear_position_money", moneyList.get(i));
					 pdConfig.put("add_time", DateUtilNew.getCurrentTimeLong()); // 时间
				 popularizeactivityService.saveConfig(pdConfig);
			}
 
		}
		
		
		
		
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PopularizeActivity");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = popularizeactivityService.list(page);	//列出PopularizeActivity列表
		mv.setViewName("lottery/popularizeactivity/popularizeactivity_list");
		Integer timeLong =	DateUtilNew.getCurrentTimeLong();
		for (int i = 0; i < varList.size(); i++) {
			PageData pda = varList.get(i);
				if (Integer.parseInt(pda.getString("end_time"))<timeLong) {
					pda.put("onLineOrOffLine", 1);
				}else {
					pda.put("onLineOrOffLine", 0);
				}
			}
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
		mv.setViewName("lottery/popularizeactivity/popularizeactivity_edit");
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
		pd = popularizeactivityService.findById(pd);	//根据ID读取
		List<PageData> list =popularizeactivityService.findConfigByActId(pd);
		mv.setViewName("lottery/popularizeactivity/popularizeactivity_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("configList", list);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PopularizeActivity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			popularizeactivityService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PopularizeActivity到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id");	//1
		titles.add("活动名称");	//2
		titles.add("标题");	//3
		titles.add("活动类型");	//4
		titles.add("信息");	//5
		titles.add("URL");	//6
		titles.add("开始时间");	//7
		titles.add("结束时间");	//8
		titles.add("是否结束");	//9
		titles.add("限购数量");	//10
		titles.add("状态");	//11
		titles.add("票种范围");	//12
		titles.add("奖励金");	//13
		titles.add("邀请数量");	//14
		titles.add("是否删除");	//15
		dataMap.put("titles", titles);
		List<PageData> varOList = popularizeactivityService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("act_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("act_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("act_title"));	    //3
			vpd.put("var4", varOList.get(i).get("act_type").toString());	//4
			vpd.put("var5", varOList.get(i).getString("act_img"));	    //5
			vpd.put("var6", varOList.get(i).getString("act_url"));	    //6
			vpd.put("var7", varOList.get(i).get("start_time").toString());	//7
			vpd.put("var8", varOList.get(i).get("end_time").toString());	//8
			vpd.put("var9", varOList.get(i).get("is_finish").toString());	//9
			vpd.put("var10", varOList.get(i).get("purchase_num").toString());	//10
			vpd.put("var11", varOList.get(i).get("status").toString());	//11
			vpd.put("var12", varOList.get(i).get("use_range").toString());	//12
			vpd.put("var13", varOList.get(i).getString("reward_money"));	    //13
			vpd.put("var14", varOList.get(i).get("number").toString());	//14
			vpd.put("var15", varOList.get(i).get("is_del").toString());	//15
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
	
	
	
	
	public static void main(String[] args) {
		   String pd="{\"gear_position\":[\"10\",\"20\"],\"gear_position_money\":[\"20\",\"30\"]}";
		   Map map = (Map)JSONUtils.parse(pd);
		 List list = (ArrayList)JSONUtils.parse(map.get("gear_position").toString());
		 System.out.println();
	}
}
