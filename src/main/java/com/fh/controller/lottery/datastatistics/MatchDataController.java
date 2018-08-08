package com.fh.controller.lottery.datastatistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.match.impl.MatchService;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.order.impl.OrderService;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：市场数据
 */
@Controller
@RequestMapping(value = "/matchdata")
public class MatchDataController extends BaseController {

	String menuUrl = "matchdata/list.do";
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	@Resource(name = "matchService")
	private MatchService matchService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表MatchDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Map> varList = new ArrayList<Map>();
		if (pd.isEmpty()) {
			pd.put("dateType", "0");
		}
		if (pd.getString("dateType").endsWith("0")) {
			pd.put("groupDay", "true");
			varList = getDataListForDay(page, pd);
		}
		if (pd.getString("dateType").endsWith("1")) {
			pd.put("groupDay", "true");
			varList = getDataListForWeek(page, pd);
		}
		if (pd.getString("dateType").endsWith("2")) {
			pd.put("groupMonth", "true");
			varList = getDataListForMonth(page, pd);
		}
		mv.setViewName("lottery/datastatistics/matchdata_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出OrderPlayData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<String> titles = new ArrayList<String>();
		titles.add("日期"); // 1
		titles.add("赛事种类"); // 2
		titles.add("投注金额"); // 3
		titles.add("总金额"); // 4
		titles.add("占比"); // 5
		List<Map> list = new ArrayList<Map>();
		if (pd.getString("dateType").endsWith("0")) {
			pd.put("groupDay", "true");
			list = getDataListForDay(page, pd);
		}
		if (pd.getString("dateType").endsWith("1")) {
			pd.put("groupDay", "true");
			list = getDataListForWeek(page, pd);
		}
		if (pd.getString("dateType").endsWith("2")) {
			pd.put("groupMonth", "true");
			list = getDataListForMonth(page, pd);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("titles", titles);

		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, PageData> map = list.get(i);
			for (PageData pdt : map.values()) {
				if (pdt.get("date") != null) {
					PageData vpd = new PageData();
					vpd.put("var1", pdt.getString("date")); // 1
					vpd.put("var2", pdt.getString("matchName")); // 2
					vpd.put("var3", new BigDecimal(pdt.getString("amount")).setScale(2,BigDecimal.ROUND_HALF_UP)); // 3
					vpd.put("var4", new BigDecimal(map.get("amountSum").getString("amountSum")).setScale(2,BigDecimal.ROUND_HALF_UP)); // 4
					BigDecimal am = new BigDecimal(pdt.getString("amount")).multiply(new BigDecimal(100));
					BigDecimal amSum = new BigDecimal(map.get("amountSum").getString("amountSum"));
					vpd.put("var5", am.divide(amSum,2,BigDecimal.ROUND_HALF_UP)+"%"); // 5

					varList.add(vpd);
				}
			}
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 获得数据集合
	 * 
	 * @param page
	 * @param pd
	 * @throws Exception
	 */
	public List<Map> getDataListForDay(Page page, PageData pd) throws Exception {
		LocalDate dateB = LocalDate.now();
		LocalDate dateE = LocalDate.now();

		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} else {
			dateB = dateE.plusDays(0);
		}
		pd.put("lastStart", dateB);
		pd.put("lastEnd", dateE);
		int days = (int) (dateE.toEpochDay() - dateB.toEpochDay());
		List<Map> varList = new ArrayList<Map>();

		for (int i = 0; i < days + 1; i++) {
			PageData pageData = new PageData();
			LocalDate time = dateE.plusDays(-i);// 当天的前i天
			pd.put("lastStart1", time.toString());
			pd.put("lastEnd1", time.toString());
			page.setPd(pd);
			List<PageData> list = ordermanagerService.getMatchAmountByTime(page);
			BigDecimal amountSUM = new BigDecimal(0);
			if (list.size() > 0) {
				Map<String, PageData> map = new HashMap<>();
				Map<String, PageData> matchMap = new HashMap<>();
				for (PageData pad : list) {
					PageData matchDate = matchMap.get(pad.getString("match_id"));
					if (matchDate == null) {
						matchDate = matchService.findById(pad);
					}
					BigDecimal padSum = new BigDecimal(pad.getString("amount"));
					if (matchDate != null) {
						matchMap.put(matchDate.getString("match_id"), matchDate);
						String leagueNM = matchDate.getString("league_id");
						PageData pageNew = map.get(leagueNM);
						if (pageNew != null) {
							BigDecimal amBefore = new BigDecimal(pageNew.getString("amount"));
							pageNew.put("amount", amBefore.add(padSum));
							map.put(leagueNM, pageNew);
						} else {
							pad.put("date", time);
							pad.put("matchName", matchDate.getString("league_addr"));
							map.put(leagueNM, pad);
						}

					} else {
						pad.put("date", time);
						String matchName = pad.getString("match_id");
						if (matchName.equals("T56")) {
							matchName = "世界杯冠军";
						} else {
							matchName = "世界杯冠亚军";
						}
						pad.put("matchName", matchName);
						map.put(matchName, pad);
					}
					amountSUM = amountSUM.add(padSum);
					PageData SUM = new PageData();
					SUM.put("amountSum", amountSUM);
					map.put("amountSum", SUM);
				}
				pd.put("amountSum", amountSUM);
				varList.add(map);
			}
		}
		return varList;
	}

	public List<Map> getDataListForWeek(Page page, PageData pd) throws Exception {
		LocalDate dateNow = LocalDate.now();
		int dayWeek = dateNow.getDayOfWeek().getValue();
		LocalDate weekStart = dateNow.plusDays(1 - dayWeek);
		LocalDate weekEnd = dateNow.plusDays(7 - dayWeek);

		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			LocalDate start = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int week = start.getDayOfWeek().getValue();
			weekStart = start.plusDays(1 - week);
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			LocalDate end = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int week = end.getDayOfWeek().getValue();
			weekEnd = end.plusDays(7 - week);
		}
		int days = (int) (weekEnd.toEpochDay() - weekStart.toEpochDay()) / 7;
		if ((int) (weekEnd.toEpochDay() - weekStart.toEpochDay()) % 7 > 0) {
			days = days + 1;
		}
		List<Map> varList = new ArrayList<Map>();
		for (int i = 0; i < days; i++) {
			PageData pageData = new PageData();
			LocalDate time = weekStart.plusDays(i * 7);// 当天的前i天
			pd.put("lastStart1", time.toString());
			pd.put("lastEnd1", time.plusDays(6).toString());
			page.setPd(pd);
			List<PageData> list = ordermanagerService.getMatchAmountByTime(page);
			Map<String, PageData> map = new HashMap<>();
			BigDecimal amountSUM = new BigDecimal(0);
			if (list.size() > 0) {
				for (PageData pad : list) {
					PageData matchDate = matchService.findById(pad);
					BigDecimal padSum = new BigDecimal(pad.getString("amount"));
					if (matchDate != null) {
						String leagueNM = matchDate.getString("league_id");
						PageData pageNew = map.get(leagueNM);
						if (pageNew != null) {
							BigDecimal amBefore = new BigDecimal(pageNew.getString("amount"));
							pageNew.put("amount", amBefore.add(padSum));
							map.put(leagueNM, pageNew);
						} else {
							pad.put("date", time + "：" + time.plusDays(6));
							pad.put("matchName", matchDate.getString("league_addr"));
							map.put(leagueNM, pad);
						}

					} else {
						pad.put("date", time + "：" + time.plusDays(6));
						String matchName = pad.getString("match_id");
						if (matchName.equals("T56")) {
							matchName = "世界杯冠军";
						} else {
							matchName = "世界杯冠亚军";
						}
						pad.put("matchName", matchName);
						map.put(matchName, pad);
					}
					amountSUM = amountSUM.add(padSum);
					PageData SUM = new PageData();
					SUM.put("amountSum", amountSUM);
					map.put("amountSum", SUM);
				}
				pd.put("amountSum", amountSUM);
				varList.add(map);
			}
		}
		return varList;
	}

	public List<Map> getDataListForMonth(Page page, PageData pd) throws Exception {
		List<Map> varList = new ArrayList<Map>();
		LocalDate monthStart = LocalDate.now();
		LocalDate monthEnd = LocalDate.now();

		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			monthStart = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			monthEnd = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		int months = monthEnd.getMonthValue() - monthStart.getMonthValue();
		int years = monthEnd.getYear() - monthStart.getYear();
		if (years > 0) {
			months = months + 12 * years;
		}
		for (int i = 0; i < months + 1; i++) {
			PageData pageData = new PageData();
			LocalDate time = monthStart.plusMonths(i);// 当天的前i天
			pd.put("lastStart1", time.toString().substring(0, 7) + "-01");
			pd.put("lastEnd1", time.toString().substring(0, 7) + "-" + time.lengthOfMonth());
			page.setPd(pd);
			List<PageData> list = ordermanagerService.getMatchAmountByTime(page);
			Map<String, PageData> map = new HashMap<>();
			BigDecimal amountSUM = new BigDecimal(0);
			if (list.size() > 0) {
				for (PageData pad : list) {
					PageData matchDate = matchService.findById(pad);
					BigDecimal padSum = new BigDecimal(pad.getString("amount"));
					if (matchDate != null) {
						String leagueNM = matchDate.getString("league_id");
						PageData pageNew = map.get(leagueNM);
						if (pageNew != null) {
							BigDecimal amBefore = new BigDecimal(pageNew.getString("amount"));
							pageNew.put("amount", amBefore.add(padSum));
							map.put(leagueNM, pageNew);
						} else {
							pad.put("date", time.toString().substring(0, 7));
							pad.put("matchName", matchDate.getString("league_addr"));
							map.put(leagueNM, pad);
						}

					} else {
						pad.put("date", time.toString().substring(0, 7));
						String matchName = pad.getString("match_id");
						if (matchName.equals("T56")) {
							matchName = "世界杯冠军";
						} else {
							matchName = "世界杯冠亚军";
						}
						pad.put("matchName", matchName);
						map.put(matchName, pad);
					}
					amountSUM = amountSUM.add(padSum);
					PageData SUM = new PageData();
					SUM.put("amountSum", amountSUM);
					map.put("amountSum", SUM);
				}
				varList.add(map);
			}
		}
		return varList;
	}

	// public PageData getMatchPageData(PageData pd) throws Exception {
	// List<PageData> list = MATCHLIST;
	// if (pd.getString("match_id").equals("T56") ||
	// pd.getString("match_id").equals("T57")) {
	// return null;
	// }
	// for (PageData data : list) {
	// if (data.getString("match_id").equals(pd.getString("match_id"))) {
	// return data;
	// }
	// }
	// return null;
	// }
}
