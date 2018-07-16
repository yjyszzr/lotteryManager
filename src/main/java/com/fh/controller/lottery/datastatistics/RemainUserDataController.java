package com.fh.controller.lottery.datastatistics;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.util.TextUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.Page;
import com.fh.entity.sms.RspSmsCodeEntity;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.userbankmanager.impl.UserBankManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.impl.UserRealManagerService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.RandomUtil;
import com.fh.util.SmsUtil;

/**
 * 说明：市场数据
 */
@Controller
@RequestMapping(value = "/remainuserdata")
public class RemainUserDataController extends BaseController {


	String menuUrl = "remainuser/list.do";  
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	 

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表RemainUserDataController");
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
			pd.put("type","天");
			pd.put("typeForDay","true");
		}
		if(pd.getString("dateType").endsWith("1")) {
			varList = getDataListForWeek(page,pd);
			pd.put("type","周");
		}
		if(pd.getString("dateType").endsWith("2")) {
			varList = getDataListForMonth(page,pd);
			pd.put("type","月");
		}
		
		mv.setViewName("lottery/datastatistics/remainuserdata_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	**
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
			dateB = dateE.plusDays(-6);
		}
		pd.put("lastStart", dateB);
		pd.put("lastEnd", dateE);
		int days = (int) (dateE.toEpochDay()-dateB.toEpochDay());
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < days; i++) {
			PageData pageData = new PageData(); 
			LocalDate time = dateE.plusDays(-i);//当天的前i天
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(time+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(time+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.listAll(pd);
			int userCount = userList.size();
			if(userCount!=0) {
			pageData.put("date", time);
			pageData.put("count_user", userCount);
			
			pageData.put("count2", getCount(time,null,1,1,userCount));
			pageData.put("count3", getCount(time,null,2,2,userCount));
			pageData.put("count4", getCount(time,null,3,3,userCount));
			pageData.put("count5", getCount(time,null,4,4,userCount));
			pageData.put("count6", getCount(time,null,5,5,userCount));
			pageData.put("count7", getCount(time,null,6,6,userCount));
			pageData.put("count15", getCount(time,null,7,14,userCount));
			pageData.put("count30", getCount(time,null,15,29,userCount));
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
			PageData pageData = new PageData(); 
			LocalDate time = weekStart.plusDays(i*7);//当天的前i天
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(time+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(time.plusDays(6)+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.listAll(pd);
			int userCount = userList.size();
			if(userCount!=0) {
				pageData.put("date", time+"~"+time.plusDays(6));
				pageData.put("count_user", userCount);
				int WEEK = 7;
				for (int j = 2; j <15 ; j++) {
					pageData.put("count"+j, getCount(time,time.plusDays(6),WEEK*(j-1),WEEK*j-1,userCount));
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
			PageData pageData = new PageData(); 
			LocalDate time = monthStart.plusMonths(i);//当天的前i天
			LocalDate start = LocalDate.parse(time.toString().substring(0, 7)+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			LocalDate end = LocalDate.parse(time.toString().substring(0, 7)+"-"+time.lengthOfMonth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(start+" 00:00:00"));
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(end+" 23:59:59"));
			page.setPd(pd);
			List<PageData> userList = usermanagercontrollerService.listAll(pd);
			int userCount = userList.size();
			if(userCount!=0) {
				pageData.put("date", time.toString().substring(0, 7));
				pageData.put("count_user", userCount);
				int startDay=0;
				int endDay=time.lengthOfMonth();
				for (int j = 2; j <15 ; j++) {
					startDay =endDay;
					endDay =startDay + time.plusMonths(j-1).lengthOfMonth();
					pageData.put("count"+j, getCount(start,end,startDay,endDay-1,userCount));
					
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
	public BigDecimal getCount(LocalDate regTime,LocalDate endTime,int start,int end,int userCount) throws Exception {
		LocalDate regDate = regTime.plusDays(start);
		LocalDate nowDate = LocalDate.now();
		int days = Period.between(regDate,nowDate).getDays();
		int months = Period.between(regDate,nowDate).getMonths();
		int years = Period.between(regDate,nowDate).getYears();
		if(days<0 || months<0 || years<0) {
			return null;
		}
		PageData pd = new PageData();
		Page page = new Page();
		if(endTime==null) {
			pd.put("regTime", regTime.toString());
		}else {
		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(regTime+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(endTime+" 23:59:59"));
		}
		pd.put("dayStart", start);
		pd.put("dayEnd",end);
		page.setPd(pd);
		List<PageData> count = usermanagercontrollerService.getRemainUserCount(page);
		String remainCount = count.get(0).getString("count");
		BigDecimal userc = new BigDecimal(userCount);
		BigDecimal remainc = new BigDecimal(remainCount+"00");
		
		return remainc.divide(userc, 2,BigDecimal.ROUND_HALF_UP);
	}
}
