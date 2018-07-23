package com.fh.controller.lottery.order;

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
import com.fh.enums.MatchBetTypeEnum;
import com.fh.enums.MatchPlayTypeEnum;
import com.fh.enums.MatchResultCrsEnum;
import com.fh.enums.MatchResultHadEnum;
import com.fh.enums.MatchResultHafuEnum;
import com.fh.service.lottery.order.OrderManager;
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
		page.setPd(pd);
		List<PageData> varList = ordermanagerService.getOrderList(page); // 列出OrderManager列表
		List<PageData> payLogList = ordermanagerService.findPayLogList(varList);
		Map<String, PageData> payLogMap = new HashMap<String, PageData>(payLogList.size());
		payLogList.forEach(item -> payLogMap.put(item.getString("order_sn"), item));
		Double allAmountD = 0D;
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = payLogMap.get(varList.get(i).getString("order_sn"));
			varList.get(i).put("pay_order_sn", pageData == null ? "--" : pageData.getString("pay_order_sn"));
			allAmountD += Double.parseDouble(varList.get(i).getString("surplus").equals("") ? "0" : varList.get(i).getString("surplus"));
			allAmountD += Double.parseDouble(varList.get(i).getString("third_party_paid").equals("") ? "0" : varList.get(i).getString("third_party_paid"));
		}
		mv.setViewName("lottery/ordermanager/ordermanager_list");
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
		pd = ordermanagerService.findById(pd);
		String passType = pd.getString("pass_type");
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
		mv.setViewName("lottery/ordermanager/ordermanager_details");
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
			cathecticData += "【混合过关】";
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
		dataMap.put("titles", titles);
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}

		String orderStatusFor = pd.getString("order_status");
		if ("".equals(orderStatusFor) && "".equals(lastEnd) && "".equals(lastStart)) {
			pd.put("lastStart1", DateUtil.toTimeSubtraction30Day(DateUtilNew.getCurrentTimeLong()));
		}

		List<PageData> varOList = ordermanagerService.listAll(pd);
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
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
