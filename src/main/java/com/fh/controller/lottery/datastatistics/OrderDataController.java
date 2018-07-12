package com.fh.controller.lottery.datastatistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.enums.MatchBetTypeEnum;
import com.fh.service.lottery.match.MatchManager;
import com.fh.service.lottery.match.impl.MatchService;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrealmanager.UserRealManagerManager;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：市场数据
 */
@Controller
@RequestMapping(value = "/orderdata")
public class OrderDataController extends BaseController {

	String menuUrl = "orderdata/list.do"; 
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	@Resource(name = "matchService")
	private MatchService matchService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表OrderDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> varList = getDataList(page,pd);
		mv.setViewName("lottery/datastatistics/orderdata_list");
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
		logBefore(logger, Jurisdiction.getUsername() + "导出OrderData到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = getDataList(page,pd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户ID"); // 1
		titles.add("购彩彩种"); // 2
		titles.add("赛事种类"); // 3
		titles.add("串关种类"); // 4
		titles.add("倍数"); // 5
		titles.add("投注金额"); // 6
		titles.add("投注状态"); // 7
		titles.add("中奖金额"); // 8
		titles.add("下单时间"); // 9
		titles.add("派奖时间"); // 10
		dataMap.put("titles", titles);
		 
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", list.get(i).getString("user_id")); // 1
			vpd.put("var2", list.get(i).getString("play_name")); // 2
			vpd.put("var3", list.get(i).getString("match_name")); // 3
			vpd.put("var4", list.get(i).getString("pass_type")); // 5
			vpd.put("var5", list.get(i).getString("cathectic")); // 5
			vpd.put("var6", list.get(i).getString("ticket_amount")); // 6
			String order_status = list.get(i).getString("order_status");
			switch (order_status) {
			case "0":
				order_status = "待付款";
				break;
			case "1":
				order_status = "待出票";
				break;
			case "2":
				order_status = "出票失败";
				break;
			case "3":
				order_status = "待开奖";
				break;
			case "4":
				order_status = "未中奖";
				break;
			case "5":
				order_status = "已中奖";
				break;
			case "6":
				order_status = "派奖中";
				break;
			case "7":
				order_status = "已派奖";
				break;
			case "8":
				order_status = "支付失败";
				break;
			default:
				break;
			}
			vpd.put("var7", order_status); // 7
			vpd.put("var8", list.get(i).getString("winning_money")); // 8
			vpd.put("var9", list.get(i).getString("add_time")); // 9
			vpd.put("var10",list.get(i).getString("award_time")); // 10
			 
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
		if (null != lastStart && !"".equals(lastStart)) {
			dateB = LocalDate.parse(lastStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			dateE = LocalDate.parse(lastEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(dateB+" 00:00:00"));
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(dateE+" 23:59:59"));
		page.setPd(pd);
		List<PageData> list = ordermanagerService.getOrderAndDetail(page);
		for (int i = 0; i < list.size(); i++) { 
			String pass_type = "";
			String[] types = list.get(i).getString("pass_type").split(",");
			for(int j = 0; j < types.length; j++) {
				pass_type =pass_type + MatchBetTypeEnum.getName(types[j]);
				if(j+1<types.length) {
					pass_type = pass_type + ",";
				}
			}
			Long add_time = Long.parseLong(list.get(i).getString("add_time"));
			String award_time = list.get(i).getString("award_time");
			if(award_time!="") {
				list.get(i).put("award_time", DateUtil.toSDFTime(Long.parseLong(award_time)*1000));
			}
			String[] matchs = list.get(i).getString("match_id").split(",");
			String matchNM = "";
			for(int j = 0; j < matchs.length; j++) {
				PageData pdm = new PageData();
				pdm.put("match_id", matchs[j]);
				PageData matchDate = matchService.findById(pdm);
				if(matchDate!=null) {
					matchNM = matchNM + matchDate.getString("league_addr");
				}else {
					String matchName = pdm.getString("changci");
					if (matchName.equals("T56")) {
						matchNM =matchNM + "世界杯冠军";
					} else {
						matchNM =matchNM + "世界杯冠亚军";
					}
				}
				if(j+1<matchs.length) {
					matchNM = matchNM + ",";
				}
			}
			list.get(i).put("pass_type", pass_type);
			list.get(i).put("match_name", matchNM);
			list.get(i).put("add_time", DateUtil.toSDFTime(add_time*1000));
			list.get(i).put("order_sn", list.get(i).getString("order_sn"));
		}
		return list;
	}
	 
}
