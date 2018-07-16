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
import com.fh.service.lottery.order.OrderManager;
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
@RequestMapping(value = "/orderplaydata")
public class OrderPlayDataController extends BaseController {

	String menuUrl = "orderplaydata/list.do";
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表OrderPlayDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = new ArrayList<PageData>();
		if (pd.isEmpty()) {
			pd.put("dateType","0");
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
		mv.setViewName("lottery/datastatistics/orderplaydata_list");
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
		titles.add("时间"); // 1
		titles.add("购彩用户数（胜平负）"); // 2
		titles.add("购彩金额（胜平负）"); // 3
		titles.add("订单数（胜平负）"); // 4
		titles.add("购彩用户数（让球胜平负）"); // 5
		titles.add("购彩金额（让球胜平负）"); // 6
		titles.add("订单数（让球胜平负）"); // 7
		titles.add("购彩用户数（比分）"); // 8
		titles.add("购彩金额（比分）"); // 9
		titles.add("订单数（比分）"); // 10
		titles.add("购彩用户数（总进球）"); // 11
		titles.add("购彩金额（总进球）"); // 12
		titles.add("订单数（总进球）"); // 13
		titles.add("购彩用户数（半全场）"); // 14
		titles.add("购彩金额（半全场）"); // 15
		titles.add("订单数（半全场）"); // 16
		titles.add("购彩用户数（2选1）"); // 17
		titles.add("购彩金额（2选1）"); // 18
		titles.add("订单数（2选1）"); // 19
		titles.add("购彩用户数（混合投注）"); // 20
		titles.add("购彩金额（混合投注）"); // 21
		titles.add("订单数（混合投注）"); // 22
		List<PageData> list = new ArrayList<PageData>();
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
			PageData vpd = new PageData();
			PageData pdt = list.get(i);
			vpd.put("var1", pdt.getString("date")); // 1
			if (null != pdt.getString("userCount2") && !"".equals(pdt.getString("userCount2"))) {
				vpd.put("var2", list.get(i).getString("userCount2")); // 2
			}
			if (null != pdt.getString("amount2") && !"".equals(pdt.getString("amount2"))) {
				vpd.put("var3", list.get(i).getString("amount2")); // 3
			}
			if (null != pdt.getString("orderCount2") && !"".equals(pdt.getString("orderCount2"))) {
				vpd.put("var4", list.get(i).getString("orderCount2")); // 4
			}
			if (null != pdt.getString("userCount1") && !"".equals(pdt.getString("userCount1"))) {
				vpd.put("var5", list.get(i).getString("userCount1")); // 5
			}
			if (null != pdt.getString("amount1") && !"".equals(pdt.getString("amount1"))) {
				vpd.put("var6", list.get(i).getString("amount1")); // 6
			}
			if (null != pdt.getString("orderCount1") && !"".equals(pdt.getString("orderCount1"))) {
				vpd.put("var7", list.get(i).getString("orderCount1")); // 7
			}
			if (null != pdt.getString("userCount3") && !"".equals(pdt.getString("userCount3"))) {
				vpd.put("var8", list.get(i).getString("userCount3")); // 8
			}
			if (null != pdt.getString("amount3") && !"".equals(pdt.getString("amount3"))) {
				vpd.put("var9", list.get(i).getString("amount3")); // 9
			}
			if (null != pdt.getString("orderCount3") && !"".equals(pdt.getString("orderCount3"))) {
				vpd.put("var10", list.get(i).getString("orderCount3")); // 10
			}
			if (null != pdt.getString("userCount4") && !"".equals(pdt.getString("userCount4"))) {
				vpd.put("var11", list.get(i).getString("userCount4")); // 11
			}
			if (null != pdt.getString("amount4") && !"".equals(pdt.getString("amount4"))) {
				vpd.put("var12", list.get(i).getString("amount4")); // 12
			}
			if (null != pdt.getString("orderCount4") && !"".equals(pdt.getString("orderCount4"))) {
				vpd.put("var13", list.get(i).getString("orderCount4")); // 13
			}
			if (null != pdt.getString("userCount5") && !"".equals(pdt.getString("userCount5"))) {
				vpd.put("var14", list.get(i).getString("userCount5")); // 14
			}
			if (null != pdt.getString("amount5") && !"".equals(pdt.getString("amount5"))) {
				vpd.put("var15", list.get(i).getString("amount5")); // 15
			}
			if (null != pdt.getString("orderCount5") && !"".equals(pdt.getString("orderCount5"))) {
				vpd.put("var16", list.get(i).getString("orderCount5")); // 16
			}
			if (null != pdt.getString("userCount7") && !"".equals(pdt.getString("userCount7"))) {
				vpd.put("var17", list.get(i).getString("userCount7")); // 17
			}
			if (null != pdt.getString("amount7") && !"".equals(pdt.getString("amount7"))) {
				vpd.put("var18", list.get(i).getString("amount7")); // 18
			}
			if (null != pdt.getString("orderCount7") && !"".equals(pdt.getString("orderCount7"))) {
				vpd.put("var19", list.get(i).getString("orderCount7")); // 19
			}
			if (null != pdt.getString("userCount6") && !"".equals(pdt.getString("userCount6"))) {
				vpd.put("var20", list.get(i).getString("userCount6")); // 20
			}
			if (null != pdt.getString("amount6") && !"".equals(pdt.getString("amount6"))) {
				vpd.put("var21", list.get(i).getString("amount6")); // 21
			}
			if (null != pdt.getString("orderCount6") && !"".equals(pdt.getString("orderCount6"))) {
				vpd.put("var22", list.get(i).getString("orderCount6")); // 22
			}
			
			varList.add(vpd);
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
	public List<PageData> getDataListForDay(Page page, PageData pd) throws Exception {
		LocalDate dateB = LocalDate.now();
		LocalDate dateE = LocalDate.now();

		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}else {
			dateB = dateE.plusDays(-6);
		}
		pd.put("lastStart", dateB);
		pd.put("lastEnd", dateE);
		int days = (int) (dateE.toEpochDay() - dateB.toEpochDay());
		List<PageData> varList = new ArrayList<PageData>();

		for (int i = 0; i < days + 1; i++) {
			PageData pageData = new PageData();
			LocalDate time = dateE.plusDays(-i);// 当天的前i天
			pd.put("lastStart1", time.toString());
			pd.put("lastEnd1", time.toString());
			page.setPd(pd);
			List<PageData> userList = ordermanagerService.getOrderOfPlay(page);
			if (userList.size() > 0) {
				pageData.put("date", time);
				for (int j = 0; j < userList.size(); j++) {
					pageData.put("userCount" + userList.get(j).getString("classify"),userList.get(j).getString("userCount"));
					pageData.put("orderCount" + userList.get(j).getString("classify"),userList.get(j).getString("orderCount"));
					pageData.put("amount" + userList.get(j).getString("classify"), userList.get(j).getString("amount"));
				}
				varList.add(pageData);
			}
		}
		return varList;
	}
	public List<PageData> getDataListForWeek(Page page,PageData pd) throws Exception {
		LocalDate dateNow = LocalDate.now();
		int dayWeek = dateNow.getDayOfWeek().getValue();
		LocalDate weekStart = dateNow.plusDays(1-dayWeek);
		LocalDate weekEnd = dateNow.plusDays(7-dayWeek);

		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			LocalDate start = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int week = start.getDayOfWeek().getValue();
			weekStart = start.plusDays(1-week);
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			LocalDate end = LocalDate.parse(lastEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int week = end.getDayOfWeek().getValue();
			weekEnd = end.plusDays(7-week);
		}
		int days = (int) (weekEnd.toEpochDay()-weekStart.toEpochDay())/7;
		if((int) (weekEnd.toEpochDay()-weekStart.toEpochDay())%7>0) {
			days = days + 1;
		}
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < days; i++) {
			PageData pageData = new PageData(); 
			LocalDate time = weekStart.plusDays(i*7);//当天的前i天
			pd.put("lastStart1", time.toString());
			pd.put("lastEnd1", time.plusDays(6).toString());
			page.setPd(pd);
			List<PageData> userList = ordermanagerService.getOrderOfPlay(page);
			if (userList.size() > 0) {
				pageData.put("date", time+"："+time.plusDays(6));
				for (int j = 0; j < userList.size(); j++) {
					pageData.put("userCount" + userList.get(j).getString("classify"),userList.get(j).getString("userCount"));
					pageData.put("orderCount" + userList.get(j).getString("classify"),userList.get(j).getString("orderCount"));
					pageData.put("amount" + userList.get(j).getString("classify"), userList.get(j).getString("amount"));
				}
				varList.add(pageData);
			}
		}
		return varList;
	}
	
	public List<PageData> getDataListForMonth(Page page,PageData pd) throws Exception {
		List<PageData> varList = new ArrayList<PageData>();
		LocalDate monthStart = LocalDate.now();
		LocalDate monthEnd = LocalDate.now();
		 
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			monthStart =  LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			monthEnd = LocalDate.parse(lastEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		int months = monthEnd.getMonthValue() - monthStart.getMonthValue();
		int years = monthEnd.getYear() - monthStart.getYear();
		if(years>0) {
			months = months + 12*years;
		}
		for (int i = 0; i < months+1; i++) {
			PageData pageData = new PageData(); 
			LocalDate time = monthStart.plusMonths(i);//当天的前i天
			pd.put("lastStart1", time.toString().substring(0, 7)+"-01");
			pd.put("lastEnd1", time.toString().substring(0, 7)+"-"+time.lengthOfMonth());
			page.setPd(pd);
			List<PageData> userList = ordermanagerService.getOrderOfPlay(page);
			if (userList.size() > 0) {
				pageData.put("date", time.toString().substring(0, 7));
				for (int j = 0; j < userList.size(); j++) {
					pageData.put("userCount" + userList.get(j).getString("classify"),userList.get(j).getString("userCount"));
					pageData.put("orderCount" + userList.get(j).getString("classify"),userList.get(j).getString("orderCount"));
					pageData.put("amount" + userList.get(j).getString("classify"), userList.get(j).getString("amount"));
				}
				varList.add(pageData);
			}
		}
		return varList;
	}
}
