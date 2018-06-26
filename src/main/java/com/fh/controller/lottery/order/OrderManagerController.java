package com.fh.controller.lottery.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

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
		mv.setViewName("lottery/ordermanager/ordermanager_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/toDetail")
	public ModelAndView toDetail() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + " Order详情");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = ordermanagerService.findById(pd);
		pd.put("pass_type", MatchBetTypeEnum.getName(pd.getString("pass_type")));
		List<PageData> orderDetailsList = ordermanagerService.toDetail(pd); // 列出OrderManager列表
		mv.setViewName("lottery/ordermanager/ordermanager_details");
		mv.addObject("varList", orderDetailsList);
		for (int i = 0; i < orderDetailsList.size(); i++) {
			String nameStr = "";
			List<MatchBetCellDTO> list = getString(orderDetailsList.get(i).getString("ticket_data"), orderDetailsList.get(i).getString("order_sn"));
			for (int j = 0; j < list.size(); j++) {
				for (int j2 = 0; j2 < list.get(j).getBetCells().size(); j2++) {
					nameStr += orderDetailsList.get(i).getString("changci") + "&nbsp" + list.get(j).getBetCells().get(j2).getCellName() + "," + list.get(j).getBetCells().get(j2).getCellOdds() + ";";
				}
			}
			orderDetailsList.get(i).put("list", nameStr);
		}
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
			cathecticData = MatchResultHadEnum.getName(Integer.valueOf(cathecticStr));
		} else if (MatchPlayTypeEnum.PLAY_TYPE_CRS.getcode() == playCode) {
			cathecticData = MatchResultCrsEnum.getName(cathecticStr);
		} else if (MatchPlayTypeEnum.PLAY_TYPE_TTG.getcode() == playCode) {
			cathecticData = cathecticStr;
		} else if (MatchPlayTypeEnum.PLAY_TYPE_HAFU.getcode() == playCode) {
			cathecticData = MatchResultHafuEnum.getName(cathecticStr);
		}
		return cathecticData;
	}
}
