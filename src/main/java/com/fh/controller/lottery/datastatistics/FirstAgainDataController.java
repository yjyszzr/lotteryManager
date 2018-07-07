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
import com.fh.service.lottery.userrealmanager.UserRealManagerManager;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：注册购彩
 */
@Controller
@RequestMapping(value = "/firstagaindata")
public class FirstAgainDataController extends BaseController {

	String menuUrl = "firstagaindata/list.do"; 
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerService useraccountmanagerService;
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
		logBefore(logger, Jurisdiction.getUsername() + "列表FirstAgainDataController");
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
			pd.put("type","天");
			pd.put("typeForDay","true");
			varList = getDataListForDay(page,pd);
		}
		if(pd.getString("dateType").endsWith("1")) {
			varList = getDataListForWeek(page,pd);
			pd.put("type","周");
		}
		if(pd.getString("dateType").endsWith("2")) {
			varList = getDataListForMonth(page,pd);
			pd.put("type","月");
		}
		mv.setViewName("lottery/datastatistics/firstagaindata_list");
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
		logBefore(logger, Jurisdiction.getUsername() + "导出FirstAgainData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.isEmpty()) {
			pd.put("dateType","0");
		}
		List<PageData> list = new ArrayList<PageData>();
		if(pd.getString("dateType").endsWith("0")) {
			list = getDataListForDay(page,pd);
			pd.put("type","天");
		}
		if(pd.getString("dateType").endsWith("1")) {
			list = getDataListForWeek(page,pd);
		}
		if(pd.getString("dateType").endsWith("2")) {
			list = getDataListForMonth(page,pd);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("日期"); // 1
		titles.add("注册用户"); // 2
		titles.add("首购用户"); // 3
		titles.add("首购金额"); // 4
		titles.add("复购用户"); // 5
		titles.add("复购金额"); // 6
		titles.add("充值用户"); // 7
		titles.add("充值金额"); // 8
		titles.add("认证用户数"); // 9
		titles.add("新增认证并购彩用户数"); // 10
		titles.add("新增认证并购彩用户的购彩金额"); // 11
		titles.add("注册并认证用户数"); // 12
		titles.add("注册并认证用户的购彩金额"); // 13
		titles.add("注册并充值用户"); // 14
		titles.add("注册并充值金额"); // 15
		titles.add("注册新增购彩用户数"); // 16
		titles.add("注册新增购彩金额"); // 17
		titles.add("注册首购后复购用户数"); // 18
		titles.add("注册首购后复购金额"); // 19
		titles.add("注册-购彩转化率"); // 20
		titles.add("注册首购后复购转化率"); // 21
		dataMap.put("titles", titles);
		 
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("date")); // 1
			vpd.put("var2", list.get(i).getString("registerCount")); // 2
			vpd.put("var3", list.get(i).getString("firstCount")); // 3
			vpd.put("var4", list.get(i).getString("firstAmount")); // 5
			vpd.put("var5", list.get(i).getString("againCount")); // 5
			vpd.put("var6", list.get(i).getString("againAmount")); // 6
			vpd.put("var7", list.get(i).getString("countRecharge")); // 7
			vpd.put("var8", list.get(i).getString("amountRecharge")); // 8
			vpd.put("var9", list.get(i).getString("realCount")); // 9
			vpd.put("var10",list.get(i).getString("realOrderCount")); // 10
			vpd.put("var11", list.get(i).getString("realOrderAmount") ); // 11
			vpd.put("var12", list.get(i).getString("registerRealCount")); // 12
			vpd.put("var13", list.get(i).getString("registerRealAmount")); // 13
			vpd.put("var14", list.get(i).getString("registerRechargeCount")); // 14
			vpd.put("var15", list.get(i).getString("registerRechargeAmount")); // 15
			vpd.put("var16", list.get(i).getString("registerOrderCount")); // 16
			vpd.put("var17", list.get(i).getString("registerOrderAmount")); // 17
			vpd.put("var18", list.get(i).getString("registerAgainOrderCount")); // 18
			vpd.put("var19", list.get(i).getString("registerAgainOrderAmount")); // 19
			vpd.put("var20", list.get(i).getString("percentA")); // 20
			vpd.put("var21", list.get(i).getString("percentB")); // 21
			 
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
	public List<PageData> getDataListForDay(Page page,PageData pd) throws Exception {
		LocalDate dateB = LocalDate.now();
		LocalDate dateE = LocalDate.now();
		
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		int days = (int) (dateE.toEpochDay()-dateB.toEpochDay());
		List<PageData> list = new ArrayList<>();
		for (int i = 0; i < days+1; i++) {
			LocalDate date = dateE.plusDays(-i);//当天的前i天
			PageData pageData = getData(page,pd,date,date,date.toString());
			list.add(pageData);
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
		List<PageData> list = new ArrayList<PageData>();
		for (int i = 0; i < days; i++) {
			LocalDate time = weekStart.plusDays(i*7);//当天的前i天
			PageData pageData = getData(page,pd,time,time.plusDays(6),time+"~"+time.plusDays(6));
			list.add(pageData);
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
		List<PageData> list = new ArrayList<>();
		for (int i = 0; i < months+1; i++) {
			LocalDate time = monthStart.plusMonths(i);//当天的前i天
			LocalDate start = LocalDate.parse(time.toString().substring(0, 7)+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			LocalDate end = LocalDate.parse(time.toString().substring(0, 7)+"-"+time.lengthOfMonth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			PageData pageData = getData(page,pd,start,end.plusDays(6),time.toString().substring(0, 7));
			list.add(pageData);
		}
		return list;
	}
	/**
	 * 数据处理
	 * 
	 * @param
	 * @throws Exception
	 */
	public PageData getData(Page page,PageData pd,LocalDate dateStart,LocalDate dateEnd,String dateName) throws Exception {
		PageData pageData = new PageData(); 
		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(dateStart+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(dateEnd+" 23:59:59"));
		//注册用户
		List<PageData> registerList = usermanagercontrollerService.listAll(pd);
		//首/复购统计
		page.setPd(pd);
		List<PageData> firstList = ordermanagerService.getFirstOrderList(page);
		List<PageData> againList = ordermanagerService.getAgainOrderList(page);
		pageData.put("date", dateName);
		pageData.put("registerCount", registerList.size());
		pageData.put("firstCount", firstList.get(0).getString("count"));
		pageData.put("firstAmount", firstList.get(0).getString("amount"));
		pageData.put("againCount", againList.get(0).getString("count"));
		pageData.put("againAmount", againList.get(0).getString("amount"));
		
		//充值统计
		String process_type = "2"; // 充值
		pd.put("process_type", process_type);
		page.setPd(pd);
		List<PageData> listRecharge = useraccountmanagerService.findByProcessType(page);
		int countRecharge = 0;
		BigDecimal amountRecharge = new BigDecimal(0);
		if (listRecharge.size() > 0) {
			countRecharge = Integer.parseInt(listRecharge.get(0).getString("userCount"));
			amountRecharge = new BigDecimal(listRecharge.get(0).getString("amountSum"));
		}
		pageData.put("countRecharge", countRecharge);
		pageData.put("amountRecharge", amountRecharge);
		//认证统计
		List<PageData> userRealList = userrealmanagerService.listAll(pd);
		pageData.put("realCount", userRealList.size());
		
		//注册并认证用户数
		List<PageData> realOrderList = usermanagercontrollerService.getRealAndOrder(page);
		pageData.put("realOrderCount", realOrderList.get(0).getString("count"));
		pageData.put("realOrderAmount", realOrderList.get(0).getString("amount"));
		
		//注册并认证用户数
		List<PageData> registerRealList = usermanagercontrollerService.getRealAndRegister(page);
		pageData.put("registerRealCount", registerRealList.get(0).getString("count"));
		pageData.put("registerRealAmount", registerRealList.get(0).getString("amount"));
		
		//注册并充值
		List<PageData> registerRechargeList = usermanagercontrollerService.getRegisterAndRecharge(page);
		pageData.put("registerRechargeCount", registerRechargeList.get(0).getString("count"));
		pageData.put("registerRechargeAmount", registerRechargeList.get(0).getString("amount"));
		
		//注册并购彩
		List<PageData> registerOrderList = usermanagercontrollerService.getRegisterAndOrder(page);
		pageData.put("registerOrderCount", registerOrderList.get(0).getString("count"));
		pageData.put("registerOrderAmount", registerOrderList.get(0).getString("amount"));
		
		//注册并购彩(复购)
		List<PageData> registerAgainOrderList = usermanagercontrollerService.getRegisterAndAgainOrder(page);
		pageData.put("registerAgainOrderCount", registerAgainOrderList.get(0).getString("count"));
		pageData.put("registerAgainOrderAmount", registerAgainOrderList.get(0).getString("amount"));
		//注册-购彩转化率
		BigDecimal rA = new BigDecimal(registerOrderList.get(0).getString("count")+"00");
		BigDecimal rB = new BigDecimal(registerList.size());
		if(rB.compareTo(BigDecimal.ZERO)!=0) {
		pageData.put("percentA",rA.divide(rB, 2,BigDecimal.ROUND_HALF_DOWN)+"%");
		}
		//注册首购后复购转化率
		BigDecimal oA = new BigDecimal(registerAgainOrderList.get(0).getString("count")+"00");
		BigDecimal oB = new BigDecimal(registerOrderList.get(0).getString("count"));
		if(oB.compareTo(BigDecimal.ZERO)!=0) {
		pageData.put("percentB",oA.divide(oB, 2,BigDecimal.ROUND_HALF_DOWN)+"%");
		}
		return pageData;
	}
}
