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
	
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户操作列表TotalDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> list = getDataList(page,pd);
		 
		mv.setViewName("lottery/datastatistics/totaldata_list");
		mv.addObject("varList", list);
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
		List<PageData> list = getDataList(page,pd);
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
			vpd.put("var2", list.get(i).getString("countBuy")); // 2
			vpd.put("var3", list.get(i).getString("amountBuy")); // 3
			vpd.put("var4", list.get(i).getString("countRecharge")); // 4
			vpd.put("var5", list.get(i).getString("amountRecharge")); // 5
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
	public List<PageData> getDataList(Page page,PageData pd) throws Exception {
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
			List<PageData> listUserAccount = useraccountmanagerService.findByProcessType(page);
			 
			List<PageData> orderList = ordermanagerService.selectSuccessByTime(page);
			int orderCount = orderList.size();
			countPage.put("orderCount", orderCount);
			if (listUserAccount.size() > 0) {
				for (int j = 0; j < listUserAccount.size(); j++) {
					PageData pageData = listUserAccount.get(j);
					String processType = pageData.getString("process_type");
					//购彩
					if(processType.equals("3")) {
						countPage.put("countBuy", Integer.parseInt(pageData.getString("userCount")));
						countPage.put("amountBuy", new BigDecimal(pageData.getString("amountSum")).negate());
					}
					//充值
					if(processType.equals("2")) {
						countPage.put("countRecharge", Integer.parseInt(pageData.getString("userCount")));
						countPage.put("amountRecharge", new BigDecimal(pageData.getString("amountSum")));
					}
					//提现
					if(processType.equals("4")) {
						countPage.put("amountWithDraw", new BigDecimal(pageData.getString("amountSum")).negate());
					}
					//中奖
					if(processType.equals("1")) {
						countPage.put("amountReward", new BigDecimal(pageData.getString("amountSum")));
					}
				}
			}
			countPage.put("register", registerList.size());
			list.add(countPage);
			
			 
		}
		return list;
	}
}
