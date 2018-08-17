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
@RequestMapping(value = "/marketdata")
public class MarketDataController extends BaseController {

	String menuUrl = "marketdata/list.do"; // 菜单地址(权限用)
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;
	@Resource(name = "userrealmanagerService")
	private UserRealManagerService userrealmanagerService;
	@Resource(name = "userbankmanagerService")
	private UserBankManagerService userbankmanagerService;
 

	 

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表MarketDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		page = new Page();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.isEmpty()) {
			pd.put("dateType","0");
		}
		List<PageData> varList = new ArrayList<PageData>();
		if(pd.getString("dateType").equals("0")) {
			pd.put("type","天");
			pd.put("typeForDay","true");
			varList = getDataListForDay(page,pd);
		}
		if(pd.getString("dateType").equals("1")) {
			pd.put("type","周");
			varList = getDataListForWeek(page,pd);
		}
		if(pd.getString("dateType").equals("2")) {
			pd.put("type","月");
			varList = getDataListForMonth(page,pd);
		}
		if(pd.getString("dateType").equals("3")) {
			varList = getDataListForTime(page,pd);
		}
		mv.setViewName("lottery/datastatistics/marketdata_list");
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
		page = new Page();
		logBefore(logger, Jurisdiction.getUsername() + "导出MarketData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<String> titles = new ArrayList<String>();
		titles.add("渠道"); // 1
		titles.add("日期"); // 2
		titles.add("安装量"); // 3
		titles.add("注册数"); // 4
		titles.add("购彩数"); // 5
		titles.add("购彩金额"); // 6
		titles.add("人均购彩金额"); // 7
		List<PageData> list = new ArrayList<PageData>();
		if(pd.getString("dateType").equals("0")) {
			list = getDataListForDay(page,pd);
			titles.add("次日留存"); // 8
			titles.add("3日留存"); // 9
			titles.add("7日留存"); // 10
			titles.add("15日留存"); // 11
			titles.add("30日留存"); // 12
			titles.add("90日留存"); // 13
			titles.add("180日留存"); // 14
			titles.add("360日留存"); // 15
		}
		if(pd.getString("dateType").equals("1")) {
			list = getDataListForWeek(page,pd);
			titles.add("第2天留存"); // 8
			titles.add("第3天留存"); // 9
			titles.add("第4天留存"); // 10
			titles.add("第5天留存"); // 11
			titles.add("第6天留存"); // 12
			titles.add("第7天留存"); // 13
			titles.add("第8天留存"); // 14
			titles.add("第9天留存"); // 15
			titles.add("第10天留存"); // 16
			titles.add("第11天留存"); // 17
			titles.add("第12天留存"); // 18
		}
		if(pd.getString("dateType").equals("2")) {
			list = getDataListForMonth(page,pd);
			titles.add("第2周留存"); // 8
			titles.add("第3周留存"); // 9
			titles.add("第4周留存"); // 10
			titles.add("第5周留存"); // 11
			titles.add("第6周留存"); // 12
			titles.add("第7周留存"); // 13
			titles.add("第8周留存"); // 14
			titles.add("第9周留存"); // 15
			titles.add("第10周留存"); // 16
			titles.add("第11周留存"); // 17
			titles.add("第12周留存"); // 18
		}
		if(pd.getString("dateType").equals("3")) {
			list = getDataListForTime(page,pd);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("titles", titles);
		 
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("phone_channel")); // 1
			if(list.get(i).getString("phone_channel") == "") {
				vpd.put("var1", list.get(i).getString("device_channel")); // 1
			}
			vpd.put("var2", list.get(i).getString("date")); // 2
			vpd.put("var3", "*"); // 3
			String count_user = list.get(i).getString("count_user");
			String amountSum = list.get(i).getString("amountSum");
			vpd.put("var4", count_user); // 4
			vpd.put("var5", list.get(i).getString("count_Order")); // 5
			vpd.put("var6", amountSum); // 6
			if(count_user.equals("0")) { // 7
				vpd.put("var7", "0");
			}else {
				BigDecimal amount = new BigDecimal(amountSum);
				BigDecimal count = new BigDecimal(count_user);
				vpd.put("var7", amount.divide(count, 2,BigDecimal.ROUND_HALF_DOWN));
			}
			
			if(list.get(i).getString("count2")=="") {     // 8
				vpd.put("var8", ""); 
			}else {
				vpd.put("var8", list.get(i).getString("count2")+"%"); 
			}
			if(list.get(i).getString("count3")=="") {     // 9
				vpd.put("var9", ""); 
			}else {
				vpd.put("var9", list.get(i).getString("count3")+"%"); 
			}
			if(list.get(i).getString("count7")=="") {     // 10
				vpd.put("var10", ""); 
			}else {
				vpd.put("var10", list.get(i).getString("count7")+"%"); 
			}
			if(list.get(i).getString("count15")=="") {     // 11
				vpd.put("var11", ""); 
			}else {
				vpd.put("var11", list.get(i).getString("count15")+"%"); 
			}
			if(list.get(i).getString("count30")=="") {     //12
				vpd.put("var12", ""); 
			}else {
				vpd.put("var12", list.get(i).getString("count30")+"%"); 
			}
			if(list.get(i).getString("count90")=="") {     // 13
				vpd.put("var13", ""); 
			}else {
				vpd.put("var13", list.get(i).getString("count90")+"%"); 
			}
			if(list.get(i).getString("count180")=="") {     // 14
				vpd.put("var14", ""); 
			}else {
				vpd.put("var14", list.get(i).getString("count180")+"%"); 
			}
			if(list.get(i).getString("count360")=="") {     // 15
				vpd.put("var15", ""); 
			}else {
				vpd.put("var15", list.get(i).getString("count360")+"%"); 
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
	public List<PageData> getDataListForTime(Page page,PageData pd) throws Exception {
		LocalDate dateE = LocalDate.now();
		LocalDate dateB = LocalDate.now();
		
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} 
		List<PageData> varList = new ArrayList<PageData>();
			PageData pageData = new PageData();
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(dateB+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(dateE+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.getMarketList(page);
			for (int k = 0; k < userList.size(); k++) {
				pageData = userList.get(k);
				if(dateE.compareTo(dateB)==0) {
					pageData.put("date", dateE);
				}else {
					pageData.put("date", dateB+"："+dateE);
				}
				int userCount = Integer.parseInt(pageData.getString("count_Order"));
				varList.add(pageData);
			}
		return varList;
	}
	/**
	 * 获得数据集合
	 * 
	 * @param page  
	 * @param pd   
	 * @throws Exception
	 */
	public List<PageData> getDataListForDay(Page page,PageData pd) throws Exception {
		LocalDate dateE = LocalDate.now();
		LocalDate dateB = LocalDate.now();
		
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}else {
			dateB = dateE.plusDays(-1);
		}
		int days = (int) (dateE.toEpochDay()-dateB.toEpochDay());
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < days+1; i++) {
			PageData pageData = new PageData();
			LocalDate date = dateE.plusDays(-i);//当天的前i天
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(date+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(date+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.getMarketList(page);
			for (int k = 0; k < userList.size(); k++) {
				pageData = userList.get(k);
				pageData.put("date", date);
				int userCount = Integer.parseInt(pageData.getString("count_Order"));
				String device_channel = pageData.getString("device_channel");
				pageData.put("count2", getCount(date, date, 1, 1, userCount, device_channel));
				pageData.put("count3", getCount(date, date, 2, 2, userCount, device_channel));
				pageData.put("count7", getCount(date, date, 3, 6, userCount, device_channel));
				pageData.put("count15", getCount(date, date, 7, 14, userCount, device_channel));
				pageData.put("count30", getCount(date, date, 15, 29, userCount, device_channel));
				pageData.put("count90", getCount(date, date, 30, 89, userCount, device_channel));
				pageData.put("count180", getCount(date, date, 90, 179, userCount, device_channel));
				pageData.put("count360", getCount(date, date, 180, 359, userCount, device_channel));
				pageData.put("nowDate", LocalDate.now());
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
			LocalDate start = LocalDate.parse(lastEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int week = start.getDayOfWeek().getValue();
			weekEnd = start.plusDays(7-week);
		}
		int days = (int) (weekEnd.toEpochDay()-weekStart.toEpochDay())/7;
		if((int) (weekEnd.toEpochDay()-weekStart.toEpochDay())%7>0) {
			days = days + 1;
		}
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < days; i++) {
			LocalDate time = weekStart.plusDays(i*7);//当天的前i天
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(time+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(time.plusDays(6)+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.getMarketList(page);
			for (int k = 0; k < userList.size(); k++) {
				PageData pageData = userList.get(k);
				int userCount = Integer.parseInt(pageData.getString("count_user"));
				pageData.put("date", time+"~"+time.plusDays(6));
				String device_channel = pageData.getString("device_channel");
				int WEEK = 7;
				for (int j = 2; j <15 ; j++) {
					pageData.put("count"+j, getCount(time,time.plusDays(6),WEEK*(j-1),WEEK*j-1,userCount,device_channel));
				}
				varList.add(pageData);	
			}
		}
		return varList;
	}
	
	public List<PageData> getDataListForMonth(Page page,PageData pd) throws Exception {
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
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < months+1; i++) {
			LocalDate time = monthStart.plusMonths(i);//当天的前i天
			LocalDate start = LocalDate.parse(time.toString().substring(0, 7)+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			LocalDate end = LocalDate.parse(time.toString().substring(0, 7)+"-"+time.lengthOfMonth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(start+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(end+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.getMarketList(page);
			for (int k = 0; k < userList.size(); k++) {
				PageData pageData = userList.get(k);
				int userCount = Integer.parseInt(pageData.getString("count_user"));
				int startDay=0;
				int endDay=time.lengthOfMonth();
				String device_channel = pageData.getString("device_channel");
				for (int j = 2; j <15 ; j++) {
					startDay =endDay;
					endDay =startDay + time.plusMonths(j-1).lengthOfMonth();
					pageData.put("count"+j, getCount(start,end,startDay,endDay-1,userCount,device_channel));
				}
			varList.add(pageData);
			}
		}
		return varList;
	}
	/**
	 * 获得留存数据
	 * 
	 * @param regTime 注册日
	 * @param start 留存起（天）
	 * @param end 留存止（天）
	 * @param userCount 注册日总人数
	 * @throws Exception
	 */
	public BigDecimal getCount(LocalDate regTime,LocalDate endTime,int start,int end,int userCount,String device_channel) throws Exception {
		LocalDate startDate = regTime.plusDays(start);
		LocalDate endDate = regTime.plusDays(end);
		LocalDate nowDate = LocalDate.now();
		int days = Period.between(startDate,nowDate).getDays();
		int months = Period.between(startDate,nowDate).getMonths();
		int years = Period.between(startDate,nowDate).getYears();
		if(days<0 || months<0 || years<0) {
			return null;
		}
		PageData pd = new PageData();
		Page page = new Page();
		 
		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(regTime+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(endTime+" 23:59:59"));
		 
		pd.put("lastStart2", DateUtilNew.getMilliSecondsByStr(startDate+" 00:00:00"));
		pd.put("lastEnd2",DateUtilNew.getMilliSecondsByStr(endDate+" 23:59:59"));
		pd.put("device_channel",device_channel);
		page.setPd(pd);
		List<PageData> count = usermanagercontrollerService.getRemainUserCount(page);
		String remainCount = count.get(0).getString("count");
		BigDecimal userc = new BigDecimal(userCount);
		BigDecimal remainc = new BigDecimal(remainCount+"00");
		if(userc.compareTo(BigDecimal.ZERO)==0) {
			return userc;
		}
		return remainc.divide(userc, 2,BigDecimal.ROUND_HALF_UP);
	}
}
