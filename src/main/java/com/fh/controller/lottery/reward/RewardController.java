package com.fh.controller.lottery.reward;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 中奖明细相关
 * 
 * @author Mr.Li
 *
 */
@Controller
@RequestMapping(value = "/reward")
public class RewardController extends BaseController {

	String menuUrl = "reward/list.do"; // 菜单地址(权限用)
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;
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
		logBefore(logger, Jurisdiction.getUsername() + "列表RewardController");
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
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}

		pd.put("process_type", 1);
		page.setPd(pd);
		List<PageData> varList = useraccountmanagerService.listForReward(page); // 列出UserAccountManager列表
		double rewardAmount = 0;
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
				rewardAmount += Double.parseDouble(pageData.get("amount").toString());
			}
		}
		mv.setViewName("lottery/reward/reward_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		BigDecimal bg = new BigDecimal(rewardAmount);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		mv.addObject("currentPageTotalAmount", f1);
		double f2 = useraccountmanagerService.totalAwardForAll();
		mv.addObject("rewardAmount", f2);
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
		pd = ordermanagerService.findByOrderSn(pd.getString("orderSn"));
		pd.put("pass_type", MatchBetTypeEnum.getName(pd.getString("pass_type")));
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
		}
		mv.setViewName("lottery/reward/reward_details");
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ChannelConsumer到excel");
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
		titles.add("中奖金额"); // 6
		titles.add("下单时间"); // 7
		titles.add("派奖时间"); // 8
		dataMap.put("titles", titles);
		List<PageData> varOList = useraccountmanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			if (varOList.get(i).getString("process_type").equals("1")) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("order_sn")); // 1
				vpd.put("var2", varOList.get(i).getString("user_name")); // 2
				vpd.put("var3", varOList.get(i).getString("mobile")); // 3
				vpd.put("var4", varOList.get(i).getString("lottery_name")); // 4
				vpd.put("var5", varOList.get(i).getString("ticket_amount") + "元"); // 5
				vpd.put("var6", varOList.get(i).getString("amount") + "元"); // 6
				BigDecimal big7 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("pay_time")) ? "0" : varOList.get(i).getString("pay_time"));
				BigDecimal big1000 = new BigDecimal(1000);
				vpd.put("var7", DateUtil.toSDFTime(Long.parseLong(big7.multiply(big1000).toString()))); // 7
				BigDecimal big8 = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("award_time")) ? "0" : varOList.get(i).getString("award_time"));
				vpd.put("var8", DateUtil.toSDFTime(Long.parseLong(big8.multiply(big1000).toString()))); // 8
				varList.add(vpd);
			}
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
