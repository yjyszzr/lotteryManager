package com.fh.controller.lottery.worldcupgj;

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
import com.fh.service.lottery.worldcupgj.WorldCupGJManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：世界杯冠军竞猜 创建人：FH Q313596790 创建时间：2018-05-31
 */
@Controller
@RequestMapping(value = "/worldcupgj")
public class WorldCupGJController extends BaseController {

	String menuUrl = "worldcupgj/list.do"; // 菜单地址(权限用)
	@Resource(name = "worldcupgjService")
	private WorldCupGJManager worldcupgjService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增WorldCupGJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		pd.put("country_id", "0"); // 国家id
		pd.put("p_id", "0"); // id
		pd.put("league_id", "0"); // 联赛id
		worldcupgjService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除WorldCupGJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupgjService.delete(pd);
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
		worldcupgjService.updateSellStatus(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改WorldCupGJ");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupgjService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表WorldCupGJ");
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
		List<PageData> varList = worldcupgjService.list(page); // 列出WorldCupGJ列表
		mv.setViewName("lottery/worldcupgj/worldcupgj_list");
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
		mv.setViewName("lottery/worldcupgj/worldcupgj_edit");
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
		pd = worldcupgjService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/worldcupgj/worldcupgj_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除WorldCupGJ");
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
			worldcupgjService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出WorldCupGJ到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("国家id"); // 2
		titles.add("国家"); // 3
		titles.add("国家Logo"); // 4
		titles.add("状态"); // 5
		titles.add("奖金"); // 6
		titles.add("概率"); // 7
		titles.add("id"); // 8
		titles.add("编号"); // 9
		titles.add("比赛号"); // 10
		titles.add("联赛id"); // 11
		titles.add("联赛名称"); // 12
		titles.add("玩法"); // 13
		titles.add("期次"); // 14
		titles.add("创建时间"); // 15
		titles.add("更新时间"); // 16
		titles.add("拉取平台"); // 17
		titles.add("备注18"); // 18
		dataMap.put("titles", titles);
		List<PageData> varOList = worldcupgjService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("country_id").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("contry_name")); // 3
			vpd.put("var4", varOList.get(i).getString("contry_pic")); // 4
			vpd.put("var5", varOList.get(i).get("bet_status").toString()); // 5
			vpd.put("var6", varOList.get(i).getString("bet_odds")); // 6
			vpd.put("var7", varOList.get(i).getString("bet_prob")); // 7
			vpd.put("var8", varOList.get(i).get("p_id").toString()); // 8
			vpd.put("var9", varOList.get(i).get("sort_id").toString()); // 9
			vpd.put("var10", varOList.get(i).get("play_code").toString()); // 10
			vpd.put("var11", varOList.get(i).get("league_id").toString()); // 11
			vpd.put("var12", varOList.get(i).getString("league_name")); // 12
			vpd.put("var13", varOList.get(i).getString("game")); // 13
			vpd.put("var14", varOList.get(i).getString("issue")); // 14
			vpd.put("var15", varOList.get(i).get("create_time").toString()); // 15
			vpd.put("var16", varOList.get(i).get("update_time").toString()); // 16
			vpd.put("var17", varOList.get(i).get("league_from").toString()); // 17
			vpd.put("var18", varOList.get(i).get("sell").toString()); // 18
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
