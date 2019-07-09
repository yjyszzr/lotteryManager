package com.fh.controller.lottery.order;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.ProjectConstant;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.dto.DlJcZqMatchCellDTO;
import com.fh.entity.dto.MatchBetCellDTO;
import com.fh.entity.system.User;
import com.fh.enums.BasketBallHILOLeverlEnum;
import com.fh.enums.MatchBasketBallResultHDCEnum;
import com.fh.enums.MatchBasketBallResultHILOEnum;
import com.fh.enums.MatchBasketPlayTypeEnum;
import com.fh.enums.MatchBetTypeEnum;
import com.fh.enums.MatchPlayTypeEnum;
import com.fh.enums.MatchResultCrsEnum;
import com.fh.enums.MatchResultHadEnum;
import com.fh.enums.MatchResultHafuEnum;
import com.fh.service.lottery.artifiprintlottery.ArtifiPrintLotteryManager;
import com.fh.service.lottery.logoperation.LogOperationManager;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 说明：订单管理 创建时间：2018-06-01
 */
@Controller
@RequestMapping(value = "/ordermanager")
public class OrderManagerController extends BaseController {

	String menuUrl = "ordermanager/list.do"; // 菜单地址(权限用)
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;

	@Resource(name = "userService")
	private UserManager userService;
	
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;

	@Resource(name = "logoperationService")
	private LogOperationManager logoperationService;

	@Resource(name="artifiprintlotteryService")
	private ArtifiPrintLotteryManager artifiprintlotteryService;
	
	@Resource(name="useraccountService")
	private UserAccountManagerManager UserAccountService;
	
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表OrderManager");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String order_status = pd.getString("order_status");

		String order_sn = pd.getString("order_sn");
		if (null != order_sn && !"".equals(order_sn)) {
			pd.put("order_sn", order_sn.trim());
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String amountStart = pd.getString("amountStart");
		if (null != amountStart && !"".equals(amountStart)) {
			pd.put("amountStart", amountStart.trim());
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String amountEnd = pd.getString("amountEnd");
		if (null != amountEnd && !"".equals(amountEnd)) {
			pd.put("amountEnd", amountEnd.trim());
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		String lottery_classify_id = pd.getString("lottery_classify_id");
		if (null != lottery_classify_id && !"".equals(lottery_classify_id)) {
			pd.put("lottery_classify_id", lottery_classify_id);
			if (null == order_status || "".equals(order_status)) {
				pd.put("order_status", "-1");
			}
		}
		page.setPd(pd);
		List<PageData> varList = ordermanagerService.getOrderList(page); // 列出OrderManager列表
		Double allAmountD = 0D;
		if (varList.size() > 0) {
			List<PageData> payLogList = ordermanagerService.findPayLogList(varList);
			Map<String, PageData> payLogMap = new HashMap<String, PageData>(payLogList.size());
			payLogList.forEach(item -> payLogMap.put(item.getString("order_sn"), item));
			for (int i = 0; i < varList.size(); i++) {
				PageData pageData = payLogMap.get(varList.get(i).getString("order_sn"));
				varList.get(i).put("pay_order_sn", pageData == null ? "--" : pageData.getString("pay_order_sn"));
				allAmountD += Double.parseDouble(varList.get(i).getString("surplus").equals("") ? "0" : varList.get(i).getString("surplus"));
				allAmountD += Double.parseDouble(varList.get(i).getString("third_party_paid").equals("") ? "0" : varList.get(i).getString("third_party_paid"));
				
				
			}
		}
		mv.setViewName("lottery/ordermanager/ordermanager_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("allAmount", allAmountD);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 手动操作订单manual operation order
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/moOrder")
	public ModelAndView moOrder(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "manual operation order");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String order_sn = pd.getString("order_sn");
		if (null != order_sn && !"".equals(order_sn)) {
			pd.put("order_sn", order_sn.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String amountStart = pd.getString("amountStart");
		if (null != amountStart && !"".equals(amountStart)) {
			pd.put("amountStart", amountStart.trim());
		}
		String amountEnd = pd.getString("amountEnd");
		if (null != amountEnd && !"".equals(amountEnd)) {
			pd.put("amountEnd", amountEnd.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		String lottery_classify_id = pd.getString("lottery_classify_id");
		if (null != lottery_classify_id && !"".equals(lottery_classify_id)) {
			pd.put("lottery_classify_id", lottery_classify_id);
		}
		page.setPd(pd);
		List<PageData> varList = ordermanagerService.getOrderListForMO(page); // 列出手工出票OrderManager列表
		Double allAmountD = 0D;
		if (varList.size() > 0) {
			List<PageData> payLogList = ordermanagerService.findPayLogList(varList);    
			List<PageData> payOperationList = ordermanagerService.findPayOperationList(varList);
			Map<String, String> orderSnMap =new HashMap<String, String>();
			for (int i = 0; i < payOperationList.size(); i++) {
			    orderSnMap.put(payOperationList.get(i).getString("order_sn"), payOperationList.get(i).getString("name"));
            }
			
			Map<String, PageData> payLogMap = new HashMap<String, PageData>(payLogList.size());
			payLogList.forEach(item -> payLogMap.put(item.getString("order_sn"), item));
			for (int i = 0; i < varList.size(); i++) {
 			    varList.get(i).put("store_name",orderSnMap.get(varList.get(i).getString("order_sn")));
				PageData pageData = payLogMap.get(varList.get(i).getString("order_sn"));
				varList.get(i).put("pay_order_sn", pageData == null ? "--" : pageData.getString("pay_order_sn"));
				allAmountD += Double.parseDouble(varList.get(i).getString("surplus").equals("") ? "0" : varList.get(i).getString("surplus"));
				allAmountD += Double.parseDouble(varList.get(i).getString("third_party_paid").equals("") ? "0" : varList.get(i).getString("third_party_paid"));
				
				try {
					String store_id = varList.get(i).getString("store_id");
					String user_id = varList.get(i).getString("user_id");
					if (null != store_id && !"".equals(store_id) 
						&& new Long(store_id) > 0	
					) { 
						PageData _pd = new PageData(); 
						_pd.put("user_id", user_id);
						PageData user = this.UserAccountService.getUserByUserId(_pd);
						varList.get(i).put("mobile", user.getString("mobile"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
		
		int printNum = 0;
		int payNum = 0;
		Long   mm=Long.parseLong(DateUtilNew.getCurrentTimeLong().toString())  ;
		List<PageData> payLogList = artifiprintlotteryService.findByTime(DateUtilNew.getCurrentTimeString( mm-60*60*24,DateUtilNew.date_sdf),null);
		for (int i = 0; i < payLogList.size(); i++) {
			PageData pageData =new PageData();
			pageData = payLogList.get(i);
			payNum += 1;
			if (pageData.getString("order_status").equals("1")) {
				printNum += 1;
			}
		}
		pd.put("printNum", printNum);
		pd.put("payNum", payNum);
		
		mv.setViewName("lottery/ordermanager/manual_operation_order");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("allAmount", allAmountD);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	/**
	 * 财务手动操作订单manual operation order
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/moOrderForHTCD")
	public ModelAndView moOrderForHTCD(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "manual operation order");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("phone", "13722300002");
		
		String order_sn = pd.getString("order_sn");
		if (null != order_sn && !"".equals(order_sn)) {
			pd.put("order_sn", order_sn.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String amountStart = pd.getString("amountStart");
		if (null != amountStart && !"".equals(amountStart)) {
			pd.put("amountStart", amountStart.trim());
		}
		String amountEnd = pd.getString("amountEnd");
		if (null != amountEnd && !"".equals(amountEnd)) {
			pd.put("amountEnd", amountEnd.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		String lottery_classify_id = pd.getString("lottery_classify_id");
		if (null != lottery_classify_id && !"".equals(lottery_classify_id)) {
			pd.put("lottery_classify_id", lottery_classify_id);
		}
		page.setPd(pd);
		List<PageData> varList = ordermanagerService.getOrderListForMO(page); // 列出手工出票OrderManager列表
		Double allAmountD = 0D;
		if (varList.size() > 0) {
			List<PageData> payLogList = ordermanagerService.findPayLogList(varList);    
			List<PageData> payOperationList = ordermanagerService.findPayOperationList(varList);
			Map<String, String> orderSnMap =new HashMap<String, String>();
			for (int i = 0; i < payOperationList.size(); i++) {
				orderSnMap.put(payOperationList.get(i).getString("order_sn"), payOperationList.get(i).getString("name"));
			}
			
			Map<String, PageData> payLogMap = new HashMap<String, PageData>(payLogList.size());
			payLogList.forEach(item -> payLogMap.put(item.getString("order_sn"), item));
			for (int i = 0; i < varList.size(); i++) {
				varList.get(i).put("store_name",orderSnMap.get(varList.get(i).getString("order_sn")));
				PageData pageData = payLogMap.get(varList.get(i).getString("order_sn"));
				varList.get(i).put("pay_order_sn", pageData == null ? "--" : pageData.getString("pay_order_sn"));
				allAmountD += Double.parseDouble(varList.get(i).getString("surplus").equals("") ? "0" : varList.get(i).getString("surplus"));
				allAmountD += Double.parseDouble(varList.get(i).getString("third_party_paid").equals("") ? "0" : varList.get(i).getString("third_party_paid"));
				
				try {
					String store_id = varList.get(i).getString("store_id");
					String user_id = varList.get(i).getString("user_id");
					if (null != store_id && !"".equals(store_id) 
							&& new Long(store_id) > 0	
							) { 
						PageData _pd = new PageData(); 
						_pd.put("user_id", user_id);
						PageData user = this.UserAccountService.getUserByUserId(_pd);
						varList.get(i).put("mobile", user.getString("mobile"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
		
		int printNum = 0;
		int payNum = 0;
		double money = 0;
		Long   mm=Long.parseLong(DateUtilNew.getCurrentTimeLong().toString())  ;
		List<PageData> payLogList = artifiprintlotteryService.findByTime(DateUtilNew.getCurrentTimeString( mm-60*60*24,DateUtilNew.date_sdf),pd.getString("phone"));
		for (int i = 0; i < payLogList.size(); i++) {
			PageData pageData =new PageData();
			pageData = payLogList.get(i);
			payNum += 1;
			money += Double.parseDouble(pageData.getString("money_paid"));
			if (pageData.getString("order_status").equals("1")) {
				printNum += 1;
			}
		}
		pd.put("printNum", printNum);
		pd.put("payNum", payNum);
		pd.put("img", "moOrderForHTCD");
		pd.put("money", money);
		
		mv.setViewName("lottery/ordermanager/manual_operation_order_for_finance");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("allAmount", allAmountD);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	/**
	 * 财务手动操作订单manual operation order
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/moOrderForSHCD")
	public ModelAndView moOrderForSHCD(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "manual operation order");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("phone", "13722300001");
		
		String order_sn = pd.getString("order_sn");
		if (null != order_sn && !"".equals(order_sn)) {
			pd.put("order_sn", order_sn.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String amountStart = pd.getString("amountStart");
		if (null != amountStart && !"".equals(amountStart)) {
			pd.put("amountStart", amountStart.trim());
		}
		String amountEnd = pd.getString("amountEnd");
		if (null != amountEnd && !"".equals(amountEnd)) {
			pd.put("amountEnd", amountEnd.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		String lottery_classify_id = pd.getString("lottery_classify_id");
		if (null != lottery_classify_id && !"".equals(lottery_classify_id)) {
			pd.put("lottery_classify_id", lottery_classify_id);
		}
		page.setPd(pd);
		List<PageData> varList = ordermanagerService.getOrderListForMO(page); // 列出手工出票OrderManager列表
		Double allAmountD = 0D;
		if (varList.size() > 0) {
			List<PageData> payLogList = ordermanagerService.findPayLogList(varList);    
			List<PageData> payOperationList = ordermanagerService.findPayOperationList(varList);
			Map<String, String> orderSnMap =new HashMap<String, String>();
			for (int i = 0; i < payOperationList.size(); i++) {
				orderSnMap.put(payOperationList.get(i).getString("order_sn"), payOperationList.get(i).getString("name"));
			}
			
			Map<String, PageData> payLogMap = new HashMap<String, PageData>(payLogList.size());
			payLogList.forEach(item -> payLogMap.put(item.getString("order_sn"), item));
			for (int i = 0; i < varList.size(); i++) {
				varList.get(i).put("store_name",orderSnMap.get(varList.get(i).getString("order_sn")));
				PageData pageData = payLogMap.get(varList.get(i).getString("order_sn"));
				varList.get(i).put("pay_order_sn", pageData == null ? "--" : pageData.getString("pay_order_sn"));
				allAmountD += Double.parseDouble(varList.get(i).getString("surplus").equals("") ? "0" : varList.get(i).getString("surplus"));
				allAmountD += Double.parseDouble(varList.get(i).getString("third_party_paid").equals("") ? "0" : varList.get(i).getString("third_party_paid"));
				
				try {
					String store_id = varList.get(i).getString("store_id");
					String user_id = varList.get(i).getString("user_id");
					if (null != store_id && !"".equals(store_id) 
							&& new Long(store_id) > 0	
							) { 
						PageData _pd = new PageData(); 
						_pd.put("user_id", user_id);
						PageData user = this.UserAccountService.getUserByUserId(_pd);
						varList.get(i).put("mobile", user.getString("mobile"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
		
		int printNum = 0;
		int payNum = 0;
		double money = 0;
		Long   mm=Long.parseLong(DateUtilNew.getCurrentTimeLong().toString())  ;
		List<PageData> payLogList = artifiprintlotteryService.findByTime(DateUtilNew.getCurrentTimeString( mm-60*60*24,DateUtilNew.date_sdf),pd.getString("phone"));
		for (int i = 0; i < payLogList.size(); i++) {
			PageData pageData =new PageData();
			pageData = payLogList.get(i);
			payNum += 1;
			money += Double.parseDouble(pageData.getString("money_paid"));
			if (pageData.getString("order_status").equals("1")) {
				printNum += 1;
			}
		}
		pd.put("printNum", printNum);
		pd.put("payNum", payNum);
		pd.put("money", money);
		pd.put("img", "moOrderForSHCD");
		mv.setViewName("lottery/ordermanager/manual_operation_order_for_finance");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("allAmount", allAmountD);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/toDetail")
	@ResponseBody
	public ModelAndView toDetail() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + " Order详情");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String statusType = pd.getString("moStatus_type");
		pd = ordermanagerService.findById(pd);
		String passType = pd.getString("pass_type");
		String orderSN = pd.getString("order_sn");
		PageData pdOrderSN = new PageData();
		pdOrderSN.put("order_sn", orderSN);
		List<PageData> orderSnPageDataList =	logoperationService.findByOrderSn(pdOrderSN);
		String[] passTypeArr = passType.split(",");
		String passTypeStr = "";
		for (int i = 0; i < passTypeArr.length; i++) {
			passTypeStr += MatchBetTypeEnum.getName(passTypeArr[i]) + ",";
		}
		int indx = passTypeStr.lastIndexOf(",");
		if (indx != -1) {
			passTypeStr = passTypeStr.substring(0, indx) + passTypeStr.substring(indx + 1, passTypeStr.length());
		}
		if (passTypeStr == null || passTypeStr.equals("null")) passTypeStr = "";
		pd.put("pass_type", passTypeStr);
		List<PageData> orderDetailsList = ordermanagerService.toDetail(pd); // 列出OrderManager列表
		logBefore(logger,    " orderDetailsList详情列表==============" + orderDetailsList);
		if (pd.getString("lottery_classify_id").equals("1")) {
			for (int i = 0; i < orderDetailsList.size(); i++) {
				String nameStr = "";
				if (orderDetailsList.get(i).getString("changci").equals("T56") || orderDetailsList.get(i).getString("changci").equals("T57")) {
					String ticketData = orderDetailsList.get(i).getString("ticket_data");
					String[] splitsjb = ticketData.split("@");
					nameStr += "<div style='margin:10px'>" + orderDetailsList.get(i).getString("match_team") + "&nbsp" + "【" + splitsjb[1] + "】  </div>";
				} else {
					List<MatchBetCellDTO> list = getString(orderDetailsList.get(i).getString("ticket_data"), orderDetailsList.get(i).getString("order_sn"));
					for (int j = 0; j < list.size(); j++) {
						for (int j2 = 0; j2 < list.get(j).getBetCells().size(); j2++) {
							String fixOdds = orderDetailsList.get(i).getString("fix_odds");
							if (list.get(j).getPlayType().equals("01")) {// 判断是不是<让球胜平负>,是的话添加上让球个数
								nameStr += "<div style='margin:10px'>" + orderDetailsList.get(i).getString("changci") + " [" + fixOdds + "]" + list.get(j).getBetCells().get(j2).getCellName() + "【" + list.get(j).getBetCells().get(j2).getCellOdds() + "】  </div>";
							} else {
								nameStr += "<div style='margin:10px'>" + orderDetailsList.get(i).getString("changci") + "&nbsp" + list.get(j).getBetCells().get(j2).getCellName() + "【" + list.get(j).getBetCells().get(j2).getCellOdds() + "】  </div>";
							}
						}
					}
				}
				orderDetailsList.get(i).put("list", nameStr);
				String matchResult = orderDetailsList.get(i).getString("match_result");
				String matchResultStr = "";
				List<String> matchResults = null;
				if (StringUtils.isNotEmpty(matchResult) && !ProjectConstant.ORDER_MATCH_RESULT_CANCEL.equals(matchResult)) {
					matchResults = Arrays.asList(matchResult.split(";"));
					for (String matchStr : matchResults) {
						String rstPlayType = matchStr.substring(0, matchStr.indexOf("|"));
						String rstPlayCells = matchStr.substring(matchStr.lastIndexOf("|") + 1);
						matchResultStr = getCathecticData(rstPlayType, rstPlayCells);
					}
				}
				orderDetailsList.get(i).put("matchResultStr", matchResultStr);
			}

			if (statusType.equals("1")) {
				mv.setViewName("lottery/ordermanager/ordermanager_details_for_mo");
				mv.addObject("orderSnList",orderSnPageDataList);
			}else	if (statusType.equals("0"))  {
				mv.setViewName("lottery/ordermanager/ordermanager_details");
			}
		} else if (pd.getString("lottery_classify_id").equals("2")) {
			for (int i = 0; i < orderDetailsList.size(); i++) {
				String tikcket = orderDetailsList.get(i).getString("ticket_data");
				String[] ball = tikcket.split("\\|");
				List<String> redBileList = new ArrayList<String>();
				List<String> redTowingList = new ArrayList<String>();
				List<String> blueBileList = new ArrayList<String>();
				List<String> blueTowingList = new ArrayList<String>();
				// 标准投注 ||复式投注
				if (orderDetailsList.get(i).getString("bet_type").equals("00") || orderDetailsList.get(i).getString("bet_type").equals("01")) {
					if (ball.length > 1) {
						redBileList = Arrays.asList(ball[0].split(","));
						blueBileList = Arrays.asList(ball[1].split(","));
					}

					// 胆拖投注
				} else if (orderDetailsList.get(i).getString("bet_type").equals("02")) {
					if (ball.length > 1) {
						String[] redbull = ball[0].split("\\$");
						if (redbull.length > 1) {
							redBileList = Arrays.asList(redbull[0].split(","));
							redTowingList = Arrays.asList(redbull[1].split(","));
						}
						String[] bluebull = ball[1].split("\\$");
						if (bluebull.length > 1) {
							blueBileList = Arrays.asList(bluebull[0].split(","));
							blueTowingList = Arrays.asList(bluebull[1].split(","));
						}
					}

				}
				orderDetailsList.get(i).put("redBileList", redBileList);
				logBefore(logger,    " redBileList列表==============" + redBileList);
				orderDetailsList.get(i).put("redTowingList", redTowingList);
				logBefore(logger,    "redTowingList列表==============" + redTowingList);
				orderDetailsList.get(i).put("blueBileList", blueBileList);
				logBefore(logger,    " blueBileList列表==============" + blueBileList);
				orderDetailsList.get(i).put("blueTowingList", blueTowingList);
				logBefore(logger,    " blueTowingList列表==============" + blueTowingList);
			}
			
			mv.addObject("orderSnList",orderSnPageDataList);
			mv.setViewName("lottery/ordermanager/ordermanager_dlt_details");
		} else if (pd.getString("lottery_classify_id").equals("3")) {
			//篮球详情
			for (int i = 0; i < orderDetailsList.size(); i++) {
				String nameStr = "";
				if (orderDetailsList.get(i).getString("changci").equals("T56") || orderDetailsList.get(i).getString("changci").equals("T57")) {
					String ticketData = orderDetailsList.get(i).getString("ticket_data");
					String[] splitsjb = ticketData.split("@");
					nameStr += "<div style='margin:10px'>" + orderDetailsList.get(i).getString("match_team") + "&nbsp" + "【" + splitsjb[1] + "】  </div>";
				} else {
					List<MatchBetCellDTO> list = getStringBasket(orderDetailsList.get(i).getString("ticket_data"), orderDetailsList.get(i).getString("order_sn"));
					for (int j = 0; j < list.size(); j++) {
						for (int j2 = 0; j2 < list.get(j).getBetCells().size(); j2++) {
							String fixOdds = StringUtil.isEmptyStr(orderDetailsList.get(i).getString("fix_odds"))?"0":orderDetailsList.get(i).getString("fix_odds");
							if (list.get(j).getPlayType().equals("01")) {// 判断是不是<让分胜负>,是的话添加上让分数
								nameStr += "<div style='margin:10px'>" + orderDetailsList.get(i).getString("changci") + " [" + fixOdds + "]" + list.get(j).getBetCells().get(j2).getCellName() + "【" + list.get(j).getBetCells().get(j2).getCellOdds() + "】  </div>";
							} else {
								nameStr += "<div style='margin:10px'>" + orderDetailsList.get(i).getString("changci") + "&nbsp" + list.get(j).getBetCells().get(j2).getCellName() + "【" + list.get(j).getBetCells().get(j2).getCellOdds() + "】  </div>";
							}
						}
					}
				}
				orderDetailsList.get(i).put("list", nameStr);
				String matchResult = orderDetailsList.get(i).getString("match_result");
				String matchResultStr = "";
				List<String> matchResults = null;
				if (StringUtils.isNotEmpty(matchResult) && !ProjectConstant.ORDER_MATCH_RESULT_CANCEL.equals(matchResult)) {
					matchResults = Arrays.asList(matchResult.split(";"));
					for (String matchStr : matchResults) {
						String rstPlayType = matchStr.substring(0, matchStr.indexOf("|"));
						String rstPlayCells = matchStr.substring(matchStr.lastIndexOf("|") + 1);
						matchResultStr = getBasketCathecticData(rstPlayType, rstPlayCells);
					}
				}
				orderDetailsList.get(i).put("matchResultStr", matchResultStr);
			}
			mv.addObject("orderSnList",orderSnPageDataList);
			mv.setViewName("lottery/ordermanager/ordermanager_jclq_details_for_mo");
		}
		
		mv.addObject("varList", orderDetailsList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@SuppressWarnings("unused")
	private List<MatchBetCellDTO> getString(String ticketsStr, String orderSn) {
		List<MatchBetCellDTO> matchBetCells = new ArrayList<MatchBetCellDTO>();
		String[] tickets = ticketsStr.split(";");
		String playCode = null;
		for (String tikcket : tickets) {
			String[] split = tikcket.split("\\|");
			if (split.length != 3) {
				logger.error("getBetInfoByOrderInfo ticket has error, orderSn=" + orderSn + " ticket=" + tikcket);
				continue;
			}
			String playType = split[0];
			if (null == playCode) {
				playCode = split[1];
			}
			String[] split2 = split[2].split(",");
			List<DlJcZqMatchCellDTO> betCells = Arrays.asList(split2).stream().map(str -> {
				String[] split3 = str.split("@");
				String matchResult = getCathecticData(split[0], split3[0]);
				DlJcZqMatchCellDTO dto = new DlJcZqMatchCellDTO(split3[0], matchResult, split3[1]);
				return dto;
			}).collect(Collectors.toList());
			MatchBetCellDTO matchBetCell = new MatchBetCellDTO();
			matchBetCell.setPlayType(playType);
			matchBetCell.setBetCells(betCells);
			matchBetCells.add(matchBetCell);
		}
		return matchBetCells;
	}
	@SuppressWarnings("unused")
	private List<MatchBetCellDTO> getStringBasket(String ticketsStr, String orderSn) {
		List<MatchBetCellDTO> matchBetCells = new ArrayList<MatchBetCellDTO>();
		String[] tickets = ticketsStr.split(";");
		String playCode = null;
		for (String tikcket : tickets) {
			String[] split = tikcket.split("\\|");
			if (split.length != 3) {
				logger.error("getBetInfoByOrderInfo ticket has error, orderSn=" + orderSn + " ticket=" + tikcket);
				continue;
			}
			String playType = split[0];
			if (null == playCode) {
				playCode = split[1];
			}
			String[] split2 = split[2].split(",");
			List<DlJcZqMatchCellDTO> betCells = Arrays.asList(split2).stream().map(str -> {
				String[] split3 = str.split("@");
				String matchResult = getBasketCathecticData(split[0], split3[0]);
				DlJcZqMatchCellDTO dto = new DlJcZqMatchCellDTO(split3[0], matchResult, split3[1]);
				return dto;
			}).collect(Collectors.toList());
			MatchBetCellDTO matchBetCell = new MatchBetCellDTO();
			matchBetCell.setPlayType(playType);
			matchBetCell.setBetCells(betCells);
			matchBetCells.add(matchBetCell);
		}
		return matchBetCells;
	}
	
	private String getCathecticData(String playType, String cathecticStr) {
		int playCode = Integer.parseInt(playType);
		String cathecticData = "";
		if (MatchPlayTypeEnum.PLAY_TYPE_HHAD.getcode() == playCode || MatchPlayTypeEnum.PLAY_TYPE_HAD.getcode() == playCode) {
			if (playCode == 1) {
				cathecticData += "【让球胜平负】";
			} else {
				cathecticData += "【胜平负】";
			}
			cathecticData += MatchResultHadEnum.getName(Integer.valueOf(cathecticStr));
		} else if (MatchPlayTypeEnum.PLAY_TYPE_CRS.getcode() == playCode) {
			cathecticData += "【比分】";
			cathecticData += MatchResultCrsEnum.getName(cathecticStr);
		} else if (MatchPlayTypeEnum.PLAY_TYPE_TTG.getcode() == playCode) {
			cathecticData += "【总进球】";
			cathecticData += cathecticStr + "球";
		} else if (MatchPlayTypeEnum.PLAY_TYPE_HAFU.getcode() == playCode) {
			cathecticData += "【半全场】";
			cathecticData += MatchResultHafuEnum.getName(cathecticStr);
		}
		return cathecticData;
	}

	private String getBasketCathecticData(String playType, String cathecticStr) {
		int playCode = Integer.parseInt(playType);
		String cathecticData = "";
		if (MatchBasketPlayTypeEnum.PLAY_TYPE_MNL.getcode() == playCode) {
			cathecticData += "【胜负】";
			cathecticData += MatchBasketBallResultHDCEnum.getName(Integer.valueOf(cathecticStr));
		} else if (MatchBasketPlayTypeEnum.PLAY_TYPE_HDC.getcode() == playCode) {
			cathecticData += "【让分胜负】";
			cathecticData += MatchBasketBallResultHDCEnum.getName(Integer.valueOf(cathecticStr));
		} else if (MatchBasketPlayTypeEnum.PLAY_TYPE_HILO.getcode() == playCode) {
			cathecticData += "【大小分】";
			cathecticData += MatchBasketBallResultHILOEnum.getName(cathecticStr);
		} else if (MatchBasketPlayTypeEnum.PLAY_TYPE_WNM.getcode() == playCode) {
			cathecticData += "【胜分差】";
			cathecticData += BasketBallHILOLeverlEnum.getName(cathecticStr);
		}
		return cathecticData;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出订单到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("订单编号"); // 1
		titles.add("用户昵称"); // 2
		titles.add("电话"); // 3
		titles.add("购买彩种"); // 4
		titles.add("店铺"); // 4
		titles.add("投注金额"); // 5
		titles.add("余额支付"); // 6
		titles.add("红包支付"); // 7
		titles.add("第三方支付"); // 8
		titles.add("中奖金额"); // 9
		titles.add("购彩时间"); // 10
		titles.add("订单状态"); // 11
		titles.add("回执单号"); // 12
		titles.add("出票公司"); // 13
		dataMap.put("titles", titles);
		String selectionTime = pd.getString("selectionTime");
		if (null == selectionTime || "".equals(selectionTime)) {
			pd.put("selectionTime", DateUtilNew.getCurrentyyyyMMdd());
		}

		List<PageData> varOList = ordermanagerService.exportExcel(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("order_sn")); // 1
			vpd.put("var2", varOList.get(i).getString("user_name")); // 2
			vpd.put("var3", varOList.get(i).getString("mobile")); // 3
			vpd.put("var4", varOList.get(i).getString("lottery_name")); // 4
			vpd.put("var5", varOList.get(i).getString("ticket_amount") + "元"); // 5
			vpd.put("var6", varOList.get(i).getString("surplus") + "元"); // 6
			vpd.put("var7", varOList.get(i).getString("bonus") + "元"); // 6
			String payName = varOList.get(i).getString("pay_name");
			String thirdPartyPaid = varOList.get(i).getString("third_party_paid");
			vpd.put("var8", thirdPartyPaid.equals("") ? "0元" : payName.equals("") ? "第三方:" + varOList.get(i).getString("third_party_paid") + "元" : payName + varOList.get(i).getString("third_party_paid") + "元"); // 6
			vpd.put("var9", varOList.get(i).getString("winning_money") + "元"); // 6
			BigDecimal big1000 = new BigDecimal(1000);
			BigDecimal big8 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("add_time")) ? "0" : varOList.get(i).getString("add_time"));
			vpd.put("var10", DateUtil.toSDFTime(Long.parseLong(big8.multiply(big1000).toString()))); // 8
			String orderStatus = varOList.get(i).getString("order_status");
			String orderStatusStr = "";
			if (orderStatus.equals("0")) {
				orderStatusStr = "待付款";
			} else if (orderStatus.equals("1")) {
				orderStatusStr = "待出票";
			} else if (orderStatus.equals("2")) {
				orderStatusStr = "出票失败";
			} else if (orderStatus.equals("3")) {
				orderStatusStr = "待开奖";
			} else if (orderStatus.equals("4")) {
				orderStatusStr = "未中奖";
			} else if (orderStatus.equals("5")) {
				orderStatusStr = "已中奖";
			} else if (orderStatus.equals("6")) {
				orderStatusStr = "派奖中";
			} else if (orderStatus.equals("7")) {
				orderStatusStr = "审核中";
			} else if (orderStatus.equals("8")) {
				orderStatusStr = "支付失败";
				
				continue;
			}
			vpd.put("var11", orderStatusStr); // 11
			vpd.put("var12", varOList.get(i).getString("pay_order_sn")); // 12
			vpd.put("var13", varOList.get(i).getString("channel_name")); // 13
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelForMO")
	public ModelAndView exportExcelForMO() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出订单到excel");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
//			return null;
//		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("订单编号"); // 1
		titles.add("手机号"); // 2
		titles.add("购买彩种");  // 3
		titles.add("平台来源"); // 4
		titles.add("支付方式"); // 5
		titles.add("投注金额"); // 6
		titles.add("中奖金额"); // 7
		titles.add("购彩时间"); // 8
//		titles.add("支付倒计时"); // 9
		titles.add("订单状态"); // 10
		titles.add("手动出票状态"); // 11
		titles.add("手动出票时间"); // 12
		titles.add("代金卷金额");
		titles.add("店铺名称");
		dataMap.put("titles", titles);
		String idsStr = pd.getString("idsStr");
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		String lottery_classify_id = pd.getString("lottery_classify_id");
		if (null != lottery_classify_id && !"".equals(lottery_classify_id)) {
			pd.put("lottery_classify_id", lottery_classify_id);
		}
		List<PageData> varOList =new 		ArrayList<PageData> ();
		if (null != idsStr && !"".equals(idsStr)) {
			String ArrayDATA_IDS[] = idsStr.split(",");
			  varOList = ordermanagerService.exportExcelForMOByIds(ArrayDATA_IDS);
		}else {
			varOList = ordermanagerService.exportExcelForMO(pd);
		}
		List<PageData> payOperationList = ordermanagerService.findPayOperationList(varOList);
		  Map<String, String> orderSnMap =new HashMap<String, String>();
          for (int i = 0; i < payOperationList.size(); i++) {
              orderSnMap.put(payOperationList.get(i).getString("order_sn"), payOperationList.get(i).getString("name"));
          }
		
		
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
//			vpd.put("var1", varOList.get(i).getString("order_id")); // 1
			vpd.put("var1", varOList.get(i).getString("order_sn")); // 1
			
			vpd.put("var2", varOList.get(i).getString("mobile")); // 2
			try {
				String mobile = " ";
				String store_id = varOList.get(i).getString("store_id");
				String user_id = varOList.get(i).getString("user_id");
				if (null != store_id && !"".equals(store_id)
					&& new Long(store_id) > 0	
				) {
					PageData _pd = new PageData();
					_pd.put("user_id", user_id);
					PageData user = this.UserAccountService.getUserByUserId(_pd);
					vpd.put("var2", user.getString("mobile")); // 2
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			vpd.put("var3", varOList.get(i).getString("lottery_name")); // 3
			String appCode = varOList.get(i).getString("app_code_name");
			String appCodeStr = "";
			if (appCode.equals("10")) {
				appCodeStr = "球多多";
			}else 	if (appCode.equals("11")) {
				appCodeStr = "圣和APP";
			}else {
				appCodeStr = "球多多";
			}
			vpd.put("var4", appCodeStr); // 5
			String surplusStr = " ";
			Double surplus = new Double(varOList.get(i).getString("surplus"));
			Double bonus = new Double(varOList.get(i).getString("bonus"));
			Double third_party_paid = new Double(varOList.get(i).getString("third_party_paid"));
			if (surplus > 0) {
				surplusStr += "余额支付、";
			}
			if(third_party_paid > 0) {
				String payName = varOList.get(i).getString("pay_name");
				if(StringUtil.isEmptyStr(payName)) {
					surplusStr +="第三方、";
				} else {
					surplusStr += payName+"、";
				}
				
			} else {
				if (surplus == 0) {
					surplusStr += "模拟支付、";
				}
			}
			if(bonus > 0) {
				surplusStr += "代金券支付、";
			}
			surplusStr = surplusStr.substring(0, surplusStr.length()-1);
			vpd.put("var5", surplusStr); // 5
			vpd.put("var6", varOList.get(i).getString("ticket_amount")); // 6
			vpd.put("var7", varOList.get(i).getString("winning_money")); // 7
			
			vpd.put("var8", DateUtil.toSDFTime(new Long(varOList.get(i).getString("add_time"))*1000)); // 8
			
//			BigDecimal big1000 = new BigDecimal(1000);
//			BigDecimal big6 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("add_time")) ? "0" : varOList.get(i).getString("add_time"));
//			vpd.put("var9", DateUtil.toSDFTime(Long.parseLong(big6.multiply(big1000).toString()))); // 9
			
			String orderStatus = varOList.get(i).getString("order_status");
			String orderStatusStr = "";
			if (orderStatus.equals("0")) {
				orderStatusStr = "待付款";
			} else if (orderStatus.equals("1")) {
				orderStatusStr = "待出票";
			} else if (orderStatus.equals("2")) {
				orderStatusStr = "出票失败";
			} else if (orderStatus.equals("3")) {
				orderStatusStr = "待开奖";
			} else if (orderStatus.equals("4")) {
				orderStatusStr = "未中奖";
			} else if (orderStatus.equals("5")) {
				orderStatusStr = "已中奖";
			} else if (orderStatus.equals("6")) {
				orderStatusStr = "派奖中";
			} else if (orderStatus.equals("7")) {
				orderStatusStr = "审核中";
			} else if (orderStatus.equals("8")) {
				orderStatusStr = "支付失败";
				
				continue;
				
			} else if (orderStatus.equals("9")) {
				orderStatusStr = "已派奖";
			} else if (orderStatus.equals("10")) {
				orderStatusStr = "已退款";
			}
			vpd.put("var9", orderStatusStr); // 10
			
			String moStatus = varOList.get(i).getString("mo_status");
			String moStatusStr = "";
			if (moStatus.equals("0")) {
				moStatusStr ="待出票";
			} else if(moStatus.equals("1")) {
				moStatusStr ="出票成功";
			} else if(moStatus.equals("2")) {
				moStatusStr ="出票失败";
			}else {
				moStatusStr ="--- ---";
			}
			vpd.put("var10", moStatusStr); // 11
			
//			BigDecimal bigmo1000 = new BigDecimal(1000);
//			BigDecimal big9 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("mo_add_time")) ? "0" : varOList.get(i).getString("mo_add_time"));
//			BigDecimal bigmo0 = new BigDecimal(0);
//			if (big9.equals(bigmo0)) {
//				vpd.put("var11", "--- ---"); // 12
//			}else {
//				vpd.put("var11", DateUtil.toSDFTime(Long.parseLong(big9.multiply(bigmo1000).toString()))); // 9
//			}
			try {
				if (null != varOList.get(i).getString("mo_add_time") && !varOList.get(i).getString("mo_add_time").equals("0")) {
					vpd.put("var11", DateUtil.toSDFTime(new Long(varOList.get(i).getString("mo_add_time")) * 1000));
				} else {
					vpd.put("var11", "--- ---");

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
//			DateUtil.toSDFTime(var.add_time*1000)
			
			vpd.put("var12", varOList.get(i).getString("bonus")); 
			vpd.put("var13", orderSnMap.get(varOList.get(i).getString("order_sn"))); 
			
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePayStatus")
	public void updatePayStatus(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "updatePayStatus");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		String payStatus = pd.getString("pay_status");
		String opType = "";
		boolean flag = false;
		if (payStatus.equals("1")) {
			opType = "1";
			flag = true;
		} else if (payStatus.equals("2")) {
			opType = "3";
		} else if (payStatus.equals("9")) {
			artifiprintlotteryService.updateRewardStatusByOrderSn(pd);
			opType = "2";
		}
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);// 操作人
		ordermanagerService.updatePayStatus(pd);
		PageData pdForlogoperation = new PageData();
		pdForlogoperation.put("order_sn", pd.getString("id"));
		pdForlogoperation.put("type", "1");
		pdForlogoperation.put("add_time", DateUtilNew.getCurrentTimeLong());
		pdForlogoperation.put("op_type", opType);
		PageData pduser = new PageData();
		pduser.put("USER_ID", user.getUSER_ID());
		pduser = userService.findById(pduser);
		PageData orderPd = new PageData();
		orderPd.put("order_sn", pd.getString("id"));
		PageData orderRes = ordermanagerService.findByOrderSn(pd.getString("id"));
		String lotteryClassifyId = orderRes.getString("lottery_classify_id");
		pdForlogoperation.put("phone", pduser.getString("PHONE"));
		pdForlogoperation.put("lottery_classify_id", lotteryClassifyId);
		logoperationService.save(pdForlogoperation);
		
		
		try {
			System.out.println("[customer] start ================================= ");
			if (flag) { 
				String userId = "";
				String mobile = "";
				String firstPayTime = "";
			 
				PageData _order =  this.ordermanagerService.findByOrderSn(pd.getString("id"));
				if(_order != null) {
					firstPayTime = _order.getString("pay_time");
					userId = _order.getString("user_id");
					mobile = _order.getString("mobile").trim();
				}
				
//				PageData _user = new PageData();
//				_user.put("user_id", userId);
//				_user = this.usermanagercontrollerService.findById(_user);
//				mobile = _user.getString("mobile");
//				if (mobile!= null) mobile = mobile.trim();
				
//				System.out.println("[customer] userId:" + userId); 
//				System.out.println("[customer] mobile:" + mobile);
//				System.out.println("[customer] firstPayTime:" + firstPayTime);
				
				System.out.println("customer|userId:" + userId + "|mobile:" + mobile + "|firstPayTime:" + firstPayTime);
				
				if (null != userId
					&& !StringUtil.isEmptyStr(mobile)
					&& !StringUtil.isEmptyStr(firstPayTime)
				) {
					this.ordermanagerService.setFirstPayTime(userId + "", mobile, firstPayTime);
					System.out.println("[customer] to db");
				}
			
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("[customer] end ================================= ");
		}
		
		
		out.write("success");
		out.close();
	}

	/**
	 * 后端预览
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOrderStatus")
	@ResponseBody
	public Object toPreShow() throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		Integer result = ordermanagerService.checkOrderStatus(pd);
		map.put("updateCount", result);
		return map;
	}
	/**添加退款备注
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/addRefundRemark")
	public void addRefundRemark(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除LogOperation");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		logoperationService.addRefundRemark(pd);
		//修改订单状态 置为已退款
		ordermanagerService.updateOrderStatusByOrderSn(pd);
		out.write("success");
		out.close();
	}
	
}
