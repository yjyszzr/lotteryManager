package com.fh.controller.lottery.worldcupgyj;

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
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.service.lottery.worldcupgyj.WorldCupGYJManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：冠亚军竞彩 创建人：FH Q313596790 创建时间：2018-05-31
 */
@Controller
@RequestMapping(value = "/worldcupgyj")
public class WorldCupGYJController extends BaseController {

	String menuUrl = "worldcupgyj/list.do"; // 菜单地址(权限用)
	@Resource(name = "worldcupgyjService")
	private WorldCupGYJManager worldcupgyjService;
	@Resource(name = "userActionLogService")
	private UserActionLogService ACLOG;
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增WorldCupGYJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		pd.put("home_country_id", "0"); // 主队Id
		pd.put("visitor_country_id", "0"); // 客队Id
		pd.put("p_id", "0"); // id
		pd.put("sort_id", "0"); // 编号
		worldcupgyjService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除WorldCupGYJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupgyjService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改状态
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSellStatus")
	public void updateSellStatus(PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupgyjService.updateSellStatus(pd);
		String actionObj ="：一键开/停售"; 
		if(!pd.getString("id").equals("-1")) {
			PageData pdOld = worldcupgyjService.findById(pd);
			actionObj = "："+pdOld.getString("home_contry_name")+"VS"+pdOld.getString("visitor_contry_name");
		}
		if(pd.getString("sell").equals("1")) {
			ACLOG.save("1", "0", "冠亚军竞猜"+actionObj, "置为停售");
		}else {
			ACLOG.save("1", "0", "冠亚军竞猜"+actionObj, "置为开售");
		}
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改WorldCupGYJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupgyjService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表WorldCupGYJ");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("contry_name"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("contry_name", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = worldcupgyjService.list(page); // 列出WorldCupGYJ列表
		mv.setViewName("lottery/worldcupgyj/worldcupgyj_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/worldcupgyj/worldcupgyj_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = worldcupgyjService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/worldcupgyj/worldcupgyj_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除WorldCupGYJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			worldcupgyjService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出WorldCupGYJ到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("主队Id"); // 2
		titles.add("主名称"); // 3
		titles.add("主队Logo"); // 4
		titles.add("客队Id"); // 5
		titles.add("客队名称"); // 6
		titles.add("客队Logo"); // 7
		titles.add("状态"); // 8
		titles.add("概率"); // 9
		titles.add("备注10"); // 10
		titles.add("id"); // 11
		titles.add("编号"); // 12
		titles.add("比赛号"); // 13
		titles.add("联赛Id"); // 14
		titles.add("联赛名称"); // 15
		titles.add("玩法"); // 16
		titles.add("期次号"); // 17
		titles.add("创建时间"); // 18
		titles.add("更新时间"); // 19
		titles.add("拉取平台"); // 20
		titles.add("状态"); // 21
		dataMap.put("titles", titles);
		List<PageData> varOList = worldcupgyjService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("home_country_id").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("home_contry_name")); // 3
			vpd.put("var4", varOList.get(i).getString("home_contry_pic")); // 4
			vpd.put("var5", varOList.get(i).get("visitor_country_id").toString()); // 5
			vpd.put("var6", varOList.get(i).getString("visitor_contry_name")); // 6
			vpd.put("var7", varOList.get(i).getString("visitor_contry_pic")); // 7
			vpd.put("var8", varOList.get(i).get("bet_status").toString()); // 8
			vpd.put("var9", varOList.get(i).getString("bet_odds")); // 9
			vpd.put("var10", varOList.get(i).getString("bet_prob")); // 10
			vpd.put("var11", varOList.get(i).get("p_id").toString()); // 11
			vpd.put("var12", varOList.get(i).get("sort_id").toString()); // 12
			vpd.put("var13", varOList.get(i).get("play_code").toString()); // 13
			vpd.put("var14", varOList.get(i).get("league_id").toString()); // 14
			vpd.put("var15", varOList.get(i).getString("league_name")); // 15
			vpd.put("var16", varOList.get(i).getString("game")); // 16
			vpd.put("var17", varOList.get(i).getString("issue")); // 17
			vpd.put("var18", varOList.get(i).get("create_time").toString()); // 18
			vpd.put("var19", varOList.get(i).get("update_time").toString()); // 19
			vpd.put("var20", varOList.get(i).get("league_from").toString()); // 20
			vpd.put("var21", varOList.get(i).get("sell").toString()); // 21
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
