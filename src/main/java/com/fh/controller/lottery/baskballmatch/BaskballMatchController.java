package com.fh.controller.lottery.baskballmatch;

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
import com.fh.service.lottery.baskballmatch.BaskballMatchManager;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;

/** 
 * 说明：篮球彩列表
 * 创建人：FH Q313596790
 * 创建时间：2019-09-20
 */
@Controller
@RequestMapping(value="/baskballmatch")
public class BaskballMatchController extends BaseController {
	
	String menuUrl = "baskballmatch/list.do"; //菜单地址(权限用)
	@Resource(name="baskballmatchService")
	private BaskballMatchManager baskballmatchService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增BaskballMatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("match_id", this.get32UUID());	//主键
		pd.put("match_id", "0");	//matchId
		pd.put("league_id", "0");	//联赛Id
		pd.put("changci_id", "0");	//场次Id
		pd.put("home_team_id", "0");	//主队Id
		pd.put("visiting_team_id", "0");	//客队Id
		baskballmatchService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除BaskballMatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		baskballmatchService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改BaskballMatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		baskballmatchService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表BaskballMatch");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = baskballmatchService.list(page);	//列出BaskballMatch列表
		mv.setViewName("lottery/baskballmatch/baskballmatch_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	

	/**
	 * 是否热门
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		baskballmatchService.updateStatus(pd);
		out.write("success");
		out.close();
	}
	
	
	
	/**
	 * 是否下架
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDel")
	public void updateDel(PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		baskballmatchService.updateDel(pd);
		out.write("success");
		out.close();
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
		mv.setViewName("lottery/baskballmatch/baskballmatch_edit");
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
		pd = baskballmatchService.findById(pd);	//根据ID读取
		mv.setViewName("lottery/baskballmatch/baskballmatch_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除BaskballMatch");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			baskballmatchService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出BaskballMatch到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("matchId");	//1
		titles.add("联赛Id");	//2
		titles.add("联赛名称");	//3
		titles.add("联赛简称");	//4
		titles.add("场次Id");	//5
		titles.add("场次");	//6
		titles.add("主队Id");	//7
		titles.add("主队名称");	//8
		titles.add("主队简称");	//9
		titles.add("主队排名");	//10
		titles.add("客队Id");	//11
		titles.add("客队名称");	//12
		titles.add("客队简称");	//13
		titles.add("客队排名");	//14
		titles.add("比赛时间");	//15
		titles.add("展示时间");	//16
		titles.add("创建时间");	//17
		titles.add("是否展示");	//18
		titles.add("是否删除");	//19
		titles.add("比赛编号");	//20
		titles.add("全场");	//21
		titles.add("状态");	//22
		titles.add("是否热门");	//23
		dataMap.put("titles", titles);
		List<PageData> varOList = baskballmatchService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("match_id").toString());	//1
			vpd.put("var2", varOList.get(i).get("league_id").toString());	//2
			vpd.put("var3", varOList.get(i).getString("league_name"));	    //3
			vpd.put("var4", varOList.get(i).getString("league_abbr"));	    //4
			vpd.put("var5", varOList.get(i).get("changci_id").toString());	//5
			vpd.put("var6", varOList.get(i).getString("changci"));	    //6
			vpd.put("var7", varOList.get(i).get("home_team_id").toString());	//7
			vpd.put("var8", varOList.get(i).getString("home_team_name"));	    //8
			vpd.put("var9", varOList.get(i).getString("home_team_abbr"));	    //9
			vpd.put("var10", varOList.get(i).getString("home_team_rank"));	    //10
			vpd.put("var11", varOList.get(i).get("visiting_team_id").toString());	//11
			vpd.put("var12", varOList.get(i).getString("visiting_team_name"));	    //12
			vpd.put("var13", varOList.get(i).getString("visiting_team_abbr"));	    //13
			vpd.put("var14", varOList.get(i).getString("visiting_team_rank"));	    //14
			vpd.put("var15", varOList.get(i).getString("match_time"));	    //15
			vpd.put("var16", varOList.get(i).getString("show_time"));	    //16
			vpd.put("var17", varOList.get(i).getString("create_time"));	    //17
			vpd.put("var18", varOList.get(i).get("is_show").toString());	//18
			vpd.put("var19", varOList.get(i).get("is_del").toString());	//19
			vpd.put("var20", varOList.get(i).getString("match_sn"));	    //20
			vpd.put("var21", varOList.get(i).getString("whole"));	    //21
			vpd.put("var22", varOList.get(i).getString("status"));	    //22
			vpd.put("var23", varOList.get(i).get("is_hot").toString());	//23
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