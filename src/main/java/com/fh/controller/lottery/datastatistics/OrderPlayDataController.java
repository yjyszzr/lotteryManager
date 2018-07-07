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
