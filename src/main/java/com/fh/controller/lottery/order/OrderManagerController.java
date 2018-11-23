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
import com.fh.enums.MatchBetTypeEnum;
import com.fh.enums.MatchPlayTypeEnum;
import com.fh.enums.MatchResultCrsEnum;
import com.fh.enums.MatchResultHadEnum;
import com.fh.enums.MatchResultHafuEnum;
import com.fh.service.lottery.artifiprintlottery.ArtifiPrintLotteryManager;
import com.fh.service.lottery.logoperation.LogOperationManager;
import com.fh.service.lottery.order.OrderManager;
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

	@Resource(name = "logoperationService")
	private LogOperationManager logoperationService;

	@Resource(name="artifiprintlotteryService")
	private ArtifiPrintLotteryManager artifiprintlotteryService;
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
			Map<String, PageData> payLogMap = new HashMap<String, PageData>(payLogList.size());
			payLogList.forEach(item -> payLogMap.put(item.getString("order_sn"), item));
			for (int i = 0; i < varList.size(); i++) {
				PageData pageData = payLogMap.get(varList.get(i).getString("order_sn"));
				varList.get(i).put("pay_order_sn", pageData == null ? "--" : pageData.getString("pay_order_sn"));
				allAmountD += Double.parseDouble(varList.get(i).getString("surplus").equals("") ? "0" : varList.get(i).getString("surplus"));
				allAmountD += Double.parseDouble(varList.get(i).getString("third_party_paid").equals("") ? "0" : varList.get(i).getString("third_party_paid"));
			}
		}
		int printNum = 0;
		int payNum = 0;
		Long   mm=Long.parseLong(DateUtilNew.getCurrentTimeLong().toString())  ;
		List<PageData> payLogList = artifiprintlotteryService.findByTime(DateUtilNew.getCurrentTimeString( mm-60*60*24,DateUtilNew.date_sdf));
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
		pd.put("pass_type", passTypeStr);
		List<PageData> orderDetailsList = ordermanagerService.toDetail(pd); // 列出OrderManager列表
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
				orderDetailsList.get(i).put("redTowingList", redTowingList);
				orderDetailsList.get(i).put("blueBileList", blueBileList);
				orderDetailsList.get(i).put("blueTowingList", blueTowingList);
			}
			mv.setViewName("lottery/ordermanager/ordermanager_dlt_details");
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
		titles.add("彩种"); // 4
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
		titles.add("用户昵称"); // 2
		titles.add("电话"); // 3
		titles.add("投注金额"); // 4
		titles.add("中奖金额"); // 5
		titles.add("购彩时间"); // 6
		titles.add("订单状态"); // 7
		titles.add("手动出票状态"); // 8
		titles.add("手动出票时间"); // 9
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
		List<PageData> varOList =new 		ArrayList<PageData> ();
		if (null != idsStr && !"".equals(idsStr)) {
			String ArrayDATA_IDS[] = idsStr.split(",");
			  varOList = ordermanagerService.exportExcelForMOByIds(ArrayDATA_IDS);
		}else {
			varOList = ordermanagerService.exportExcelForMO(pd);
		}
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("order_sn")); // 1
			vpd.put("var2", varOList.get(i).getString("user_name")); // 2
			vpd.put("var3", varOList.get(i).getString("mobile")); // 3
			vpd.put("var4", varOList.get(i).getString("ticket_amount")); // 5
			vpd.put("var5", varOList.get(i).getString("winning_money")); // 6
			BigDecimal big1000 = new BigDecimal(1000);
			BigDecimal big6 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("add_time")) ? "0" : varOList.get(i).getString("add_time"));
			vpd.put("var6", DateUtil.toSDFTime(Long.parseLong(big6.multiply(big1000).toString()))); // 8
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
			} else if (orderStatus.equals("9")) {
				orderStatusStr = "已派奖";
			} else if (orderStatus.equals("10")) {
				orderStatusStr = "已退款";
			}
			vpd.put("var7", orderStatusStr); // 7
			
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
			vpd.put("var8", moStatusStr); // 8
			BigDecimal bigmo1000 = new BigDecimal(1000);
			BigDecimal big9 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("mo_add_time")) ? "0" : varOList.get(i).getString("mo_add_time"));
			BigDecimal bigmo0 = new BigDecimal(0);
			if (big9.equals(bigmo0)) {
				vpd.put("var9", "--- ---"); // 9
			}else {
				vpd.put("var9", DateUtil.toSDFTime(Long.parseLong(big9.multiply(bigmo1000).toString()))); // 9
			}
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
		if (payStatus.equals("1")) {
			opType = "1";
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
		pdForlogoperation.put("phone", pduser.getString("PHONE"));
		logoperationService.save(pdForlogoperation);
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
