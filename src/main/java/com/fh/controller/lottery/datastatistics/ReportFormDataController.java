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
import com.fh.service.lottery.usermanagercontroller.impl.UserManagerControllerService;
import com.fh.service.lottery.userrealmanager.UserRealManagerManager;
import com.fh.service.lottery.userrecharge.impl.UserRechargeService;
import com.fh.service.lottery.userwithdraw.impl.UserWithdrawService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.google.inject.Key;

/**
 * 说明：市场数据
 */
@Controller
@RequestMapping(value = "/reportformdata")
public class ReportFormDataController extends BaseController {

	String menuUrl = "reportformdata/list.do";
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	@Resource(name = "userrechargeService")
	private UserRechargeService userrechargeService;
	@Resource(name = "userwithdrawService")
	private UserWithdrawService userwithdrawService;
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerService usermanagercontrollerService;
	@Resource(name="userrealmanagerService")
	private UserRealManagerManager userrealmanagerService; 
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
			pd.put("day", "日");
			varList = getDataListForDay(page, pd);
		}
		if (pd.getString("dateType").endsWith("1")) {
			pd.put("groupDay", "true");
			pd.put("day", "周");
			varList = getDataListForWeek(page, pd);
		}
		if (pd.getString("dateType").endsWith("2")) {
			pd.put("groupMonth", "true");
			pd.put("day", "月");
			varList = getDataListForMonth(page, pd);
		}
		mv.setViewName("lottery/datastatistics/reportformdata_list");
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
		List<PageData> list = new ArrayList<PageData>();
		String day = "";
		if (pd.getString("dateType").endsWith("0")) {
			pd.put("groupDay", "true");
			day = "日";
			list = getDataListForDay(page, pd);
		}
		if (pd.getString("dateType").endsWith("1")) {
			pd.put("groupDay", "true");
			day = "周";
			list = getDataListForWeek(page, pd);
		}
		if (pd.getString("dateType").endsWith("2")) {
			pd.put("groupMonth", "true");
			day = "月";
			list = getDataListForMonth(page, pd);
		}
		List<String> titles = new ArrayList<String>();
		titles.add("日期"); //  1
		titles.add("当"+day+"实付购彩额"); //  2
		titles.add("当"+day+"优惠券金额"); //  3
		titles.add("当"+day+"订单金额"); //  4
		titles.add("胜平负"); //  5
		titles.add("让球胜平负"); //  6
		titles.add("比分"); //  7
		titles.add("总进球"); //  8
		titles.add("半全场"); //  9
		titles.add("2选1"); //  10
		titles.add("混合投注"); //  11
		titles.add("世界杯"); //  12
		titles.add("胜平负比例"); //  13
		titles.add("让球胜平负比例"); //  14
		titles.add("比分比例"); //  15
		titles.add("总进球比例"); //  16
		titles.add("半全场比例"); //  17
		titles.add("2选1比例"); //  18
		titles.add("混合投注比例"); //  19
		titles.add("世界杯比例"); //  20
		titles.add("当"+day+"购彩用户数"); //  21
		titles.add("当"+day+"提现金额"); //  22 
		titles.add("当"+day+"充值金额"); //  23
		titles.add("当"+day+"新增的注册用户数"); //  24
		titles.add("当"+day+"新增的注册用户数"); //  25
		titles.add("当"+day+"新增注册并认证用户数"); //  26
		titles.add("当"+day+"新增注册并购彩用户的购彩金额"); //  27
		titles.add("当"+day+"新增的认证用户数"); //  28
		titles.add("当"+day+"新增认证并购彩用户数"); //  29
		titles.add("当"+day+"新增认证并购彩用户的购彩金额"); //  30
		titles.add("当"+day+"注册到购彩的转化率"); //  31
		titles.add("当"+day+"新增的购彩用户数"); //  32
		titles.add("当"+day+"新增购彩用户的购彩金额"); //  33
		titles.add("当"+day+"新增购彩用户人均购彩金额"); //  34
		titles.add("老用户购彩人数"); //  35
		titles.add("老用户的购彩金额"); //  36
		titles.add("老用户人均购彩金额"); //  37
		titles.add("累计新增购彩用户数"); //  38
		titles.add("总购彩金额"); //  39
		titles.add("出票失败金额"); //  40
		titles.add("支付失败金额"); //  41
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("titles", titles);
		 
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			PageData pdt = list.get(i);
			vpd.put("var1", pdt.getString("date")); // 1
			vpd.put("var2", pdt.getString("data1")); // 2
			vpd.put("var3", pdt.getString("data2")); // 3
			vpd.put("var4", pdt.getString("data3")); // 4
			vpd.put("var5", pdt.getString("ply2")); // 5
			vpd.put("var6", pdt.getString("ply1")); // 6
			vpd.put("var7", pdt.getString("ply3")); // 7
			vpd.put("var8", pdt.getString("ply4")); // 8
			vpd.put("var9", pdt.getString("ply5")); // 9
			vpd.put("var10", pdt.getString("ply6")); // 10
			vpd.put("var11", pdt.getString("ply7")); // 11
			vpd.put("var12", pdt.getString("ply8")); // 12
			vpd.put("var13", getPercent(pdt.getString("ply2"),pdt.getString("data3"))); // 13
			vpd.put("var14", getPercent(pdt.getString("ply1"),pdt.getString("data3"))); // 14
			vpd.put("var15", getPercent(pdt.getString("ply3"),pdt.getString("data3"))); // 15
			vpd.put("var16", getPercent(pdt.getString("ply4"),pdt.getString("data3"))); // 16
			vpd.put("var17", getPercent(pdt.getString("ply5"),pdt.getString("data3"))); // 17
			vpd.put("var18", getPercent(pdt.getString("ply6"),pdt.getString("data3"))); // 18
			vpd.put("var19", getPercent(pdt.getString("ply7"),pdt.getString("data3"))); // 19
			vpd.put("var20", getPercent(pdt.getString("ply8"),pdt.getString("data3"))); // 20
			
			vpd.put("var21", pdt.getString("data20")); // 21
			vpd.put("var22", pdt.getString("data21")); // 22
			vpd.put("var23", pdt.getString("data22")); // 23
			vpd.put("var24", pdt.getString("data23")); // 24
			vpd.put("var25", pdt.getString("data24")); // 25
			vpd.put("var26", pdt.getString("data25")); // 26
			vpd.put("var27", pdt.getString("data26")); // 27
			vpd.put("var28", pdt.getString("data27")); // 28
			vpd.put("var29", pdt.getString("data28")); // 29
			vpd.put("var30", pdt.getString("data29")); // 30
			vpd.put("var31", pdt.getString("data30")); // 31
			vpd.put("var32", pdt.getString("data31")); // 32
			vpd.put("var33", pdt.getString("data32")); // 33
			BigDecimal a = new BigDecimal(pdt.getString("data32"));
			BigDecimal b = new BigDecimal(pdt.getString("data31"));
			vpd.put("var34", ""); // 34
			if(!pdt.getString("data31").equals("0")) {
				vpd.put("var34", a.divide(b,2,BigDecimal.ROUND_HALF_UP)); // 34
			}
			vpd.put("var35", pdt.getString("data34")); // 35
			vpd.put("var36", pdt.getString("data35")); // 36
			BigDecimal c = new BigDecimal(pdt.getString("data35"));
			BigDecimal d = new BigDecimal(pdt.getString("data34"));
			vpd.put("var37", ""); // 34
			if(!pdt.getString("data34").equals("0")) {
				vpd.put("var37", c.divide(d,2,BigDecimal.ROUND_HALF_UP)); // 34
			}
			vpd.put("var38", pdt.getString("data37")); // 38
			vpd.put("var39", pdt.getString("data38")); // 39
			vpd.put("var40", pdt.getString("data39")); // 40
			vpd.put("var41", pdt.getString("data40")); // 41
			for(Object o :vpd.keySet()) {
				if(vpd.get(o)==null || vpd.get(o).equals("")) {
					vpd.put(o, "0.00");
				}
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
			LocalDate time = dateE.plusDays(-i);// 当天的前i天
			PageData pageData = getData(page,pd,time.toString(),time.toString());
			pageData.put("date", time);
			varList.add(pageData);
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
			LocalDate time = weekStart.plusDays(i*7);//当天的前i天
			PageData pageData = getData(page,pd,time.toString(),time.plusDays(6).toString());
			pageData.put("date", time+"~"+time.plusDays(6));
			varList.add(pageData);
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
			LocalDate time = monthStart.plusMonths(i);//当天的前i天
			String start =  time.toString().substring(0, 7)+"-01";
			String end =  time.toString().substring(0, 7)+"-"+time.lengthOfMonth();
			PageData pageData = getData(page,pd,start,end);
			pageData.put("date", time.toString().substring(0, 7));
			varList.add(pageData);
		}
		return varList;
	}

	public PageData getData(Page page,PageData pd,String start,String end) throws Exception{
		PageData pageData = new PageData();
		pd.put("lastStart1", start);
		pd.put("lastEnd1", end);
		page.setPd(pd);
		
		List<PageData> totalAM = ordermanagerService.getTotalAmountByTime(page);
		pageData.put("data1", totalAM.get(0).getString("paidSum"));
		pageData.put("data2", totalAM.get(0).getString("bonusSum"));
		pageData.put("data3", totalAM.get(0).getString("amountSum"));
		pageData.put("data20", totalAM.get(0).getString("userCount"));
		
		List<PageData> plyList = ordermanagerService.getOrderOfPlay(page); 
		for (int j = 0; j < plyList.size(); j++) {
			pageData.put("ply" + plyList.get(j).getString("classify"),plyList.get(j).getString("amount"));
		}

		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(start+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(end+" 23:59:59"));
		PageData withdrawPD = userwithdrawService.findTotalWithDraw(pd);
		PageData rechargePD = userrechargeService.findTotalRecharge(pd);
		pageData.put("data21", new BigDecimal(withdrawPD.getString("amountSum")));
		pageData.put("data22", new BigDecimal(rechargePD.getString("amountSum")));
		//注册用户
		List<PageData> registerList = usermanagercontrollerService.listAll(pd);
		pageData.put("data23", registerList.size());
		//注册并购彩
		List<PageData> registerOrderList = usermanagercontrollerService.getRegisterAndOrder(page);
		pageData.put("data24", registerOrderList.get(0).getString("count"));
		pageData.put("data26", registerOrderList.get(0).getString("amount"));	
		//注册并认证用户数
		List<PageData> registerRealList = usermanagercontrollerService.getRealAndRegister(page);
		pageData.put("data25", registerRealList.get(0).getString("count"));
//		pageData.put("registerRealAmount", registerRealList.get(0).getString("amount"));	 
		//认证统计
		List<PageData> userRealList = userrealmanagerService.listAll(pd);
		pageData.put("data27", userRealList.size());
		//认证并购彩用户数
		List<PageData> realOrderList = usermanagercontrollerService.getRealAndOrder(page);
		pageData.put("data28", realOrderList.get(0).getString("count"));
		pageData.put("data29", realOrderList.get(0).getString("amount"));
		//注册-购彩转化率
		BigDecimal rA = new BigDecimal(registerOrderList.get(0).getString("count")+"00");
		BigDecimal rB = new BigDecimal(registerList.size());
		if(rB.compareTo(BigDecimal.ZERO)!=0) {
		pageData.put("data30",rA.divide(rB, 2,BigDecimal.ROUND_HALF_DOWN)+"%");
		}
		List<PageData> firstList = ordermanagerService.getFirstOrderList(page);
		pageData.put("data31", firstList.get(0).getString("count"));
		pageData.put("data32", firstList.get(0).getString("amount"));
		List<PageData> againList = ordermanagerService.getAgainOrderList(page);
		pageData.put("data34", againList.get(0).getString("count"));
		pageData.put("data35", againList.get(0).getString("amount"));
		
		pd.put("lastStart1", null);
		pd.put("lastEnd1", end);
		page.setPd(pd);
		List<PageData> statusList = ordermanagerService.getGroupByOrderStatus(page);
		for(PageData statusPD : statusList) {
			if(statusPD.getString("status").equals("2")) {
				pageData.put("data39", statusPD.getString("amountSum"));
			}
			if(statusPD.getString("status").equals("8")) {
				pageData.put("data40", statusPD.getString("amountSum"));
			}
		}
		List<PageData> totalAMAll = ordermanagerService.getTotalAmountByTime(page);
		pageData.put("data37", totalAMAll.get(0).getString("userCount"));
		pageData.put("data38", totalAMAll.get(0).getString("amountSum"));
		return pageData;
	}

	public String getPercent(String objA,String objB) {
		if(!objB.equals("0")) {
			if(objA!=null && !objA.equals("")) {
				BigDecimal a = new BigDecimal(objA).multiply(new BigDecimal(100));;
				BigDecimal b = new BigDecimal(objB);
				return a.divide(b,2,BigDecimal.ROUND_HALF_UP)+"%";
			}else {
				return "0.00%";
			}
		}
		return "0.00%";
	}
}
