package com.fh.controller.lottery.datastatistics;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrecharge.impl.UserRechargeService;
import com.fh.service.lottery.userwithdraw.impl.UserWithdrawService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/totaldata")
public class TotalDataController extends BaseController {

	String menuUrl = "totaldata/list.do"; // 
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerService useraccountmanagerService;
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	@Resource(name = "userwithdrawService")
	private UserWithdrawService userwithdrawService;
	@Resource(name = "userrechargeService")
	private UserRechargeService userrechargeService;
	
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户操作列表TotalDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.isEmpty()) {
			pd.put("dateType","0");
		}
		List<PageData> varList = new ArrayList<PageData>();
		if(pd.getString("dateType").endsWith("0")) {
			varList = getDataListForDay(page,pd);
		}
		if(pd.getString("dateType").endsWith("1")) {
			varList = getDataListForWeek(page,pd);
		}
		if(pd.getString("dateType").endsWith("2")) {
			varList = getDataListForMonth(page,pd);
		}
		if(pd.getString("dateType").endsWith("3")) {
			varList = getDataListForTime(page,pd);
		}
		
		 
		mv.setViewName("lottery/datastatistics/totaldata_list");
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
		logBefore(logger, Jurisdiction.getUsername() + "导出TotalData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = new ArrayList<PageData>();
		if(pd.getString("dateType").endsWith("0")) {
			list = getDataListForDay(page,pd);
		}
		if(pd.getString("dateType").endsWith("1")) {
			list = getDataListForWeek(page,pd);
		}
		if(pd.getString("dateType").endsWith("2")) {
			list = getDataListForMonth(page,pd);
		}
		if(pd.getString("dateType").endsWith("3")) {
			list = getDataListForTime(page,pd);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("日期"); // 1
		titles.add("总购彩金额"); // 2
		titles.add("总购彩用户"); // 3
		titles.add("总充值金额"); // 4
		titles.add("总充值用户"); // 5
		titles.add("总提现金额"); // 6
		titles.add("总中奖金额"); // 7
		titles.add("总订单量"); // 8
		titles.add("总注册用户"); // 9
		titles.add("总安装数"); // 10
		dataMap.put("titles", titles);
		 
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("data")); // 1
			vpd.put("var2", list.get(i).getString("amountBuy")); // 2
			vpd.put("var3", list.get(i).getString("countBuy")); // 3
			vpd.put("var4", list.get(i).getString("amountRecharge")); // 4
			vpd.put("var5", list.get(i).getString("countRecharge")); // 5
			vpd.put("var6", list.get(i).getString("amountWithDraw")); // 6
			vpd.put("var7", list.get(i).getString("amountReward")); // 7
			vpd.put("var8", list.get(i).getString("orderCount")); // 8
			vpd.put("var9", list.get(i).getString("register")); // 9
			vpd.put("var10", "*"); // 10
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	public List<PageData> getDataListForDay(Page page,PageData pd) throws Exception {
		LocalDate dateB = LocalDate.now();
		LocalDate dateE = LocalDate.now();
		
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}else {
			String day = pd.getString("fastCheck");
			if(null == day || "".equals(day)) {
				day = "7";
			}
			dateB = dateE.plusDays(0-Integer.parseInt(day));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		int days = (int) (dateE.toEpochDay()-dateB.toEpochDay());
		List<PageData> list = new ArrayList<>();
		for (int i = 0; i < days+1; i++) {
			PageData countPage = new PageData();
			LocalDate date = dateE.plusDays(-i);//当天的前i天
			countPage.put("data", date);
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(date+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(date+" 23:59:59"));
			page.setPd(pd);
			List<PageData> registerList = usermanagercontrollerService.listAll(pd);
			 
			//购彩
			PageData pdAM = new PageData();
			Page pageAm = new Page();
			pdAM.put("lastStart1", date.toString());
			pdAM.put("lastEnd1", date.toString());
			pageAm.setPd(pdAM);
			List<PageData> totalAM = ordermanagerService.getTotalAmountByTime(pageAm);
			countPage.put("countBuy", Integer.parseInt(totalAM.get(0).getString("userCount")));
			countPage.put("amountBuy", new BigDecimal(totalAM.get(0).getString("amountSum")));
			countPage.put("amountReward", new BigDecimal(totalAM.get(0).getString("winningSum")));
				
			List<PageData> orderList = ordermanagerService.selectSuccessByTime(page);
			int orderCount = orderList.size();
			countPage.put("orderCount", orderCount);
			
			PageData withdrawPD = userwithdrawService.findTotalWithDraw(pd);
			countPage.put("amountWithDraw", new BigDecimal(withdrawPD.getString("amountSum")));
			PageData rechargePD = userrechargeService.findTotalRecharge(pd);
			countPage.put("countRecharge", Integer.parseInt(rechargePD.getString("userCount")));
			countPage.put("amountRecharge", new BigDecimal(rechargePD.getString("amountSum")));
			countPage.put("register", registerList.size());
			list.add(countPage);
			
			 
		}
		return list;
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
		List<PageData> list = new ArrayList<>();
		for (int i = 0; i < days; i++) {
			PageData countPage = new PageData(); 
			LocalDate time = weekStart.plusDays(i*7);//当天的前i天
			countPage.put("data",  time+"~"+time.plusDays(6));
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(time+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(time.plusDays(6)+" 23:59:59"));
			page.setPd(pd);
			List<PageData> registerList = usermanagercontrollerService.listAll(pd);
			 
			//购彩
			PageData pdAM = new PageData();
			Page pageAm = new Page();
			pdAM.put("lastStart1", time.toString());
			pdAM.put("lastEnd1", time.plusDays(6).toString());
			pageAm.setPd(pdAM);
			List<PageData> totalAM = ordermanagerService.getTotalAmountByTime(pageAm);
			countPage.put("countBuy", Integer.parseInt(totalAM.get(0).getString("userCount")));
			countPage.put("amountBuy", new BigDecimal(totalAM.get(0).getString("amountSum")));
			countPage.put("amountReward", new BigDecimal(totalAM.get(0).getString("winningSum")));
				
			List<PageData> orderList = ordermanagerService.selectSuccessByTime(page);
			int orderCount = orderList.size();
			countPage.put("orderCount", orderCount);
			
			PageData withdrawPD = userwithdrawService.findTotalWithDraw(pd);
			countPage.put("amountWithDraw", new BigDecimal(withdrawPD.getString("amountSum")));
			PageData rechargePD = userrechargeService.findTotalRecharge(pd);
			countPage.put("countRecharge", Integer.parseInt(rechargePD.getString("userCount")));
			countPage.put("amountRecharge", new BigDecimal(rechargePD.getString("amountSum")));
			countPage.put("register", registerList.size());
			list.add(countPage);
			
		}
		return list;
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
			PageData countPage = new PageData(); 
			LocalDate time = monthStart.plusMonths(i);//当天的前i天
			LocalDate start = LocalDate.parse(time.toString().substring(0, 7)+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			LocalDate end = LocalDate.parse(time.toString().substring(0, 7)+"-"+time.lengthOfMonth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(start+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(end+" 23:59:59"));
			countPage.put("data",  time.toString().substring(0, 7));
			page.setPd(pd);
			List<PageData> registerList = usermanagercontrollerService.listAll(pd);
			 
			//购彩
			PageData pdAM = new PageData();
			Page pageAm = new Page();
			pdAM.put("lastStart1", start.toString());
			pdAM.put("lastEnd1", end.toString());
			pageAm.setPd(pdAM);
			List<PageData> totalAM = ordermanagerService.getTotalAmountByTime(pageAm);
			countPage.put("countBuy", Integer.parseInt(totalAM.get(0).getString("userCount")));
			countPage.put("amountBuy", new BigDecimal(totalAM.get(0).getString("amountSum")));
			countPage.put("amountReward", new BigDecimal(totalAM.get(0).getString("winningSum")));
				
			List<PageData> orderList = ordermanagerService.selectSuccessByTime(page);
			int orderCount = orderList.size();
			countPage.put("orderCount", orderCount);
			
			PageData withdrawPD = userwithdrawService.findTotalWithDraw(pd);
			countPage.put("amountWithDraw", new BigDecimal(withdrawPD.getString("amountSum")));
			PageData rechargePD = userrechargeService.findTotalRecharge(pd);
			countPage.put("countRecharge", Integer.parseInt(rechargePD.getString("userCount")));
			countPage.put("amountRecharge", new BigDecimal(rechargePD.getString("amountSum")));
			countPage.put("register", registerList.size());
			varList.add(countPage);
		}
		return varList;
	}
	public List<PageData> getDataListForTime(Page page,PageData pd) throws Exception {
		LocalDate timeStart = LocalDate.now();
		LocalDate timeEnd = LocalDate.now();
		
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			timeStart =  LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			timeEnd = LocalDate.parse(lastEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		 
		List<PageData> varList = new ArrayList<PageData>();
		 
		PageData countPage = new PageData(); 
		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(timeStart+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(timeEnd+" 23:59:59"));
		countPage.put("data", timeStart+":"+ timeEnd);
		page.setPd(pd);
		List<PageData> registerList = usermanagercontrollerService.listAll(pd);
		
		//购彩
		PageData pdAM = new PageData();
		Page pageAm = new Page();
		pdAM.put("lastStart1", timeStart.toString());
		pdAM.put("lastEnd1", timeEnd.toString());
		pageAm.setPd(pdAM);
		List<PageData> totalAM = ordermanagerService.getTotalAmountByTime(pageAm);
		countPage.put("countBuy", Integer.parseInt(totalAM.get(0).getString("userCount")));
		countPage.put("amountBuy", new BigDecimal(totalAM.get(0).getString("amountSum")));
		countPage.put("amountReward", new BigDecimal(totalAM.get(0).getString("winningSum")));
		
		List<PageData> orderList = ordermanagerService.selectSuccessByTime(page);
		int orderCount = orderList.size();
		countPage.put("orderCount", orderCount);
		
		PageData withdrawPD = userwithdrawService.findTotalWithDraw(pd);
		countPage.put("amountWithDraw", new BigDecimal(withdrawPD.getString("amountSum")));
		PageData rechargePD = userrechargeService.findTotalRecharge(pd);
		countPage.put("countRecharge", Integer.parseInt(rechargePD.getString("userCount")));
		countPage.put("amountRecharge", new BigDecimal(rechargePD.getString("amountSum")));
		countPage.put("register", registerList.size());
		varList.add(countPage);
		 
		return varList;
	}
}
