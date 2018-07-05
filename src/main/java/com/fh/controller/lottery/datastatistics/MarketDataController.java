package com.fh.controller.lottery.datastatistics;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@RequestMapping(value = "/marketdata")
public class MarketDataController extends BaseController {

	private final static String USER_SESSION_PREFIX = "US:";

	String menuUrl = "marketdata/list.do"; // 菜单地址(权限用)
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;
	@Resource(name = "userrealmanagerService")
	private UserRealManagerService userrealmanagerService;
	@Resource(name = "userbankmanagerService")
	private UserBankManagerService userbankmanagerService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	@Resource(name = "redisDaoImpl")
	private RedisDaoImpl redisDaoImpl;

	 

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
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> varList = getDataList(page,pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出MarketData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = getDataList(page,pd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("渠道"); // 1
		titles.add("日期"); // 2
		titles.add("安装量"); // 3
		titles.add("注册数"); // 4
		titles.add("购彩数"); // 5
		titles.add("购彩金额"); // 6
		titles.add("人均购彩金额"); // 7
		titles.add("次日留存"); // 8
		titles.add("3日留存"); // 9
		titles.add("7日留存"); // 10
		titles.add("15日留存"); // 11
		titles.add("30日留存"); // 12
		titles.add("90日留存"); // 13
		titles.add("180日留存"); // 14
		titles.add("360日留存"); // 15
		dataMap.put("titles", titles);
		 
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("phone_channel")); // 1
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
				amount.divide(count, 2,BigDecimal.ROUND_HALF_DOWN);
				vpd.put("var7", amount);
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
	public List<PageData> getDataList(Page page,PageData pd) throws Exception {
		LocalDate dateB = LocalDate.now();
		LocalDate dateE = LocalDate.now();
		
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (pd.isEmpty()) {
			lastStart = LocalDate.now().toString();
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart+" 00:00:00"));
			pd.put("lastStart",lastStart);
		}
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(dateB+" 00:00:00"));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(dateE+" 23:59:59"));
		String operation_node = "1";
		pd.put("operation_node", operation_node);
		page.setPd(pd);
		List<PageData> userList = usermanagercontrollerService.getMarketList(page);
		for (int i = 0; i < userList.size(); i++) {
			PageData pageData = new PageData();
			pageData = userList.get(i);
			String date = pageData.getString("date");
			String userCount = pageData.getString("count_user");
			
			pageData.put("count2", getCount(date,"1","1",userCount));
			pageData.put("count3", getCount(date,"2","2",userCount));
			pageData.put("count7", getCount(date,"3","6",userCount));
			pageData.put("count15", getCount(date,"7","14",userCount));
			pageData.put("count30", getCount(date,"15","29",userCount));
			pageData.put("count90", getCount(date,"30","89",userCount));
			pageData.put("count180", getCount(date,"90","179",userCount));
			pageData.put("count360", getCount(date,"180","359",userCount));
			pageData.put("nowDate", LocalDate.now());
			
			userList.set(i, pageData);
		}
		return userList;
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
	public BigDecimal getCount(String regTime,String start,String end,String userCount) throws Exception {
		LocalDate regDate = LocalDate.parse(regTime).plusDays(Integer.parseInt(start));
		LocalDate nowDate = LocalDate.now();
		int days = Period.between(regDate,nowDate).getDays();
		int months = Period.between(regDate,nowDate).getMonths();
		int years = Period.between(regDate,nowDate).getYears();
		if(days<0 || months<0 || years<0) {
			return null;
		}
		PageData pd = new PageData();
		Page page = new Page();
		pd.put("regTime", regTime);
		pd.put("dayStart", start);
		pd.put("dayEnd",end);
		page.setPd(pd);
		List<PageData> count = usermanagercontrollerService.getRemainUserCount(page);
		String remainCount = count.get(0).getString("count");
		BigDecimal userc = new BigDecimal(userCount);
		BigDecimal remainc = new BigDecimal(remainCount+"00");
		
		return remainc.divide(userc, 2,BigDecimal.ROUND_HALF_DOWN);
	}
}
