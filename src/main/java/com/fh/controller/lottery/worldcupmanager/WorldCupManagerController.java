package com.fh.controller.lottery.worldcupmanager;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axis.utils.SessionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.worldcupcontry.WorldCupContryManager;
import com.fh.service.lottery.worldcupmanager.WorldCupManagerManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 说明：世界杯冠亚军推演模块 创建人：FH Q313596790 创建时间：2018-07-09
 */
@Controller
@RequestMapping(value = "/worldcupmanager")
public class WorldCupManagerController extends BaseController {

	String menuUrl = "worldcupmanager/list.do"; // 菜单地址(权限用)
	@Resource(name = "worldcupmanagerService")
	private WorldCupManagerManager worldcupmanagerService;
	@Resource(name = "worldcupcontryService")
	private WorldCupContryManager worldcupcontryService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增WorldCupManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		pd.put("prize", ""); // 奖项
		pd.put("quota", ""); // 额度
		pd.put("average", ""); // 平均值
		pd.put("audit_time", "0"); // 审核时间
		pd.put("auditor", ""); // 审核人
		pd.put("auditor_id", "0"); // 审核人Id
		worldcupmanagerService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除WorldCupManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupmanagerService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 开奖
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/openThePrize")
	public void openThePrize(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "开奖");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> listAll = worldcupcontryService.listAll(pd);
		Map<Integer, Map<String, Integer>> cupMap = new HashMap<Integer, Map<String, Integer>>();
		Map<String, Integer> contry16Map = new HashMap<String, Integer>();
		Map<String, Integer> contry8Map = new HashMap<String, Integer>();
		Map<String, Integer> contry4Map = new HashMap<String, Integer>();
		Map<String, Integer> contry2Map = new HashMap<String, Integer>();
		Map<String, Integer> contry1Map = new HashMap<String, Integer>();
		for (int i = 0; i < listAll.size(); i++) {
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_16"))) {
				contry16Map.put(listAll.get(i).getString("is_16"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_8"))) {
				contry8Map.put(listAll.get(i).getString("is_8"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_4"))) {
				contry4Map.put(listAll.get(i).getString("is_4"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_2"))) {
				contry2Map.put(listAll.get(i).getString("is_2"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
			if (!StringUtil.isEmptyStr(listAll.get(i).getString("is_1"))) {
				contry1Map.put(listAll.get(i).getString("is_1"), Integer.parseInt(listAll.get(i).getString("country_id")));
			}
		}
		cupMap.put(1, contry1Map);
		cupMap.put(2, contry2Map);
		cupMap.put(4, contry4Map);
		cupMap.put(8, contry8Map);
		cupMap.put(16, contry16Map);
		pd.put("audit_time", DateUtilNew.getCurrentTimeLong());
		SessionUtils.generateSession();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USERROL);
		pd.put("auditor_id", user.getUSER_ID());
		pd.put("auditor", user.getUSERNAME());
		worldcupmanagerService.openThePrize(pd, cupMap);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改WorldCupManager");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		worldcupmanagerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表WorldCupManager");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = worldcupmanagerService.list(page); // 列出WorldCupManager列表
		for (int i = 0; i < varList.size(); i++) {
			Integer prizeValue = Integer.parseInt(varList.get(i).getString("prize_value"));
			PageData pada = new PageData();
			pada.put("prize_value", prizeValue);
			logger.info("prizeValue=========================================================================================" + prizeValue);
			Integer peopleNum = worldcupmanagerService.findPeopleNumByStatus(pada);
			varList.get(i).put("people_num", peopleNum);
			BigDecimal big = new BigDecimal(varList.get(i).getString("quota"));
			if (peopleNum == 0 || peopleNum == null) {
				varList.get(i).put("average", 0);
			} else {
				BigDecimal bigPeopleNum = new BigDecimal(peopleNum.toString());
				varList.get(i).put("average", big.divide(bigPeopleNum, 2).doubleValue());
			}
		}
		mv.setViewName("lottery/worldcupmanager/worldcupmanager_list");
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
		mv.setViewName("lottery/worldcupmanager/worldcupmanager_edit");
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
		pd = worldcupmanagerService.findById(pd); // 根据ID读取
		Integer prizeValue = Integer.parseInt(pd.getString("prize_value"));
		PageData pada = new PageData();
		pada.put("prize_value", prizeValue);
		logger.info("prizeValue=========================================================================================" + prizeValue);
		Integer peopleNum = worldcupmanagerService.findPeopleNumByStatus(pada);
		pd.put("people_num", peopleNum);
		BigDecimal big = new BigDecimal(pd.getString("quota"));
		if (peopleNum == 0) {
			pd.put("average", 0);
		} else {
			BigDecimal bigPeopleNum = new BigDecimal(peopleNum.toString());
			pd.put("average", big.divide(bigPeopleNum).doubleValue());
		}
		mv.setViewName("lottery/worldcupmanager/worldcupmanager_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除WorldCupManager");
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
			worldcupmanagerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出WorldCupManager到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("奖项"); // 2
		titles.add("额度"); // 3
		titles.add("人数"); // 4
		titles.add("平均值"); // 5
		titles.add("审核时间"); // 6
		titles.add("审核人"); // 7
		titles.add("审核人Id"); // 8
		dataMap.put("titles", titles);
		List<PageData> varOList = worldcupmanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("prize")); // 2
			vpd.put("var3", varOList.get(i).getString("quota")); // 3
			vpd.put("var4", varOList.get(i).get("people_num").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("average")); // 5
			vpd.put("var6", varOList.get(i).get("audit_time").toString()); // 6
			vpd.put("var7", varOList.get(i).getString("auditor")); // 7
			vpd.put("var8", varOList.get(i).get("auditor_id").toString()); // 8
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
