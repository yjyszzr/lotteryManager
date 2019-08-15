package com.fh.controller.lottery.activity;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.activity.ActivityManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
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

/** 
 * 说明：活动管理
 * 创建人：FH Q313596790
 * 创建时间：2018-06-03
 */
@Controller
@RequestMapping(value="/activity")
public class ActivityController extends BaseController {
	
	String menuUrl = "activity/list.do"; //菜单地址(权限用)
	@Resource(name="activityService")
	private ActivityManager activityService;
	@Resource(name="userActionLogService")
	private UserActionLogService ACLOG;
	/**保存xiugai
	 * @param
	 * @throws Exception
	 */  
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Activity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String lastStart = pd.getString("start_time");
		String lastEnd = pd.getString("end_time");
		long start = DateUtilNew.getMilliSecondsByStr(lastStart);
		long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
		pd.put("start_time", start);
		pd.put("end_time", end);

		activityService.save(pd);
		ACLOG.save("1", "1", "活动管理："+pd.getString("act_name"), "活动名称："+pd.getString("act_name"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}


    /**校验是否重复活动类型
     * @param
     * @throws Exception
     */
    @RequestMapping(value="/checkType")
    @ResponseBody
    public Object checkType() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData typePd = new PageData();
        typePd.put("act_type",pd.get("act_type"));
        PageData rstPd = activityService.findByType(typePd);
        if(rstPd != null){
            map.put("flag", false);
            map.put("msg", "不能添加重复的活动");
            return AppUtil.returnObject(new PageData(), map);
        }
        map.put("flag", true);
        map.put("msg", "可以添加");
        return AppUtil.returnObject(new PageData(), map);
    }

	/**
	 * 上线
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/onLine")
	public void isStickOrNot(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "上线操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = activityService.findById(pd);
		activityService.updateByKey(pd);
		if(pd.getString("is_finish").equals("0")) {
			ACLOG.save("1", "0", "活动管理："+pdOld.getString("act_name"), "置为上架");
		}else {
			ACLOG.save("1", "0", "活动管理："+pdOld.getString("act_name"), "置为下架");
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Activity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = activityService.findById(pd);
		activityService.delete(pd);
		ACLOG.save("1", "2", "活动管理："+pdOld.getString("act_name"), pdOld.toString());
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Activity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = activityService.findById(pd);
		String lastStart = pd.getString("start_time");
		String lastEnd = pd.getString("end_time");
		long start = DateUtilNew.getMilliSecondsByStr(lastStart);
		long end = DateUtilNew.getMilliSecondsByStr(lastEnd);
		pd.put("start_time", start);
		pd.put("end_time", end);		
		
		activityService.edit(pd);
		ACLOG.saveByObject("1", "活动管理："+pdOld.getString("act_name"), pdOld, pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Activity");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = activityService.list(page);	//列出Activity列表
		mv.setViewName("lottery/activity/activity_list");
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
		mv.setViewName("lottery/activity/activity_edit");
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
		pd = activityService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/activity/activity_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Activity");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			activityService.deleteAll(ArrayDATA_IDS);
			ACLOG.save("1", "2", "活动管理：批量删除","id："+ DATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Activity到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("活动id");	//1
		titles.add("活动名称");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("活动开始时间");	//7
		titles.add("活动结束时间");	//8
		titles.add("活动状态");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = activityService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("act_id").toString());	//1
			vpd.put("var2", varOList.get(i).getString("act_name"));	    //2
			vpd.put("var3", varOList.get(i).getString("act_title"));	    //3
			vpd.put("var4", varOList.get(i).get("act_type").toString());	//4
			vpd.put("var5", varOList.get(i).getString("act_img"));	    //5
			vpd.put("var6", varOList.get(i).getString("act_url"));	    //6
			vpd.put("var7", varOList.get(i).getString("start_time"));	    //7
			vpd.put("var8", varOList.get(i).getString("end_time"));	    //8
			vpd.put("var9", varOList.get(i).get("is_finish").toString());	//9
			vpd.put("var10", varOList.get(i).get("purchase_num").toString());	//10
			vpd.put("var11", varOList.get(i).get("status").toString());	//11
			vpd.put("var12", varOList.get(i).get("use_range").toString());	//12
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
