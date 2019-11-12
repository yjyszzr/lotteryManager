package com.fh.controller.lottery.datastatistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.order.OrderManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.service.lottery.userrecharge.impl.UserRechargeService;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/present")
public class PresentDataController extends BaseController {

	String menuUrl = "present/list.do"; // 西安后台管理数据
	@Resource(name = "orderService")
	private OrderManager ordermanagerService;
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	@Resource(name = "userrechargeService")
	private UserRechargeService userrechargeService;

	private PageData pdt = null;
	private LocalDate DATE = null;

	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户操作列表PresentDataController");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		String lastEnd = pd.getString("lastEnd"); // 结束时间检索条件

		if (pd.isEmpty()) {
			lastStart = LocalDate.now().toString();
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart + " 00:00:00"));
			pd.put("lastStart", lastStart);
			mv.addObject("now", "今日");
		}
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart + " 00:00:00"));
		}
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd + " 23:59:59"));
		} else {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(LocalDate.now() + " 23:59:59"));
		}
		// 注册统计
		page.setPd(pd);
		List<PageData> registerList = usermanagercontrollerService.listAll(pd);
		// 购彩
		PageData pdAM = new PageData();
		Page pageAm = new Page();
		pdAM.put("lastStart1", lastStart);
		pdAM.put("lastEnd1", lastEnd);
		pageAm.setPd(pdAM);
		List<PageData> totalAM = ordermanagerService.getTotalAmountByTime(pageAm);
		int countBuy = Integer.parseInt(totalAM.get(0).getString("userCount"));
		BigDecimal amountBuy = new BigDecimal(totalAM.get(0).getString("amountSum"));
		
		PageData rechargePD = userrechargeService.findTotalRecharge(pd);
		String  countRecharge = rechargePD.getString("userCount");
		String amountRecharge = rechargePD.getString("amountSum");

		PageData pdh = getDataForHour(page, pd);
		mv.setViewName("lottery/datastatistics/presentdata_list");
		mv.addObject("register", registerList.size());
		mv.addObject("countBuy", countBuy);
		mv.addObject("amountBuy", amountBuy);
		mv.addObject("countRecharge", countRecharge);
		mv.addObject("amountRecharge", amountRecharge);
		mv.addObject("pd", pd);
		mv.addObject("pdt", pdt);
		mv.addObject("pdh", pdh);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;

	}

	public PageData getDataForHour(Page page, PageData pd) throws Exception {
		PageData pdh = new PageData();
		LocalDate todayDate = LocalDate.now();
		LocalDate weekDate = todayDate.plusWeeks(-1);
		LocalDate monthDate = todayDate.plusMonths(-1);
		pd.put("dayHour", todayDate.toString() );
		page.setPd(pd);
		List<PageData> listD = ordermanagerService.getAmountForDayHour(page);
		for (int i = 0; i < 24; i++) {
			pdh.put("d"+i, "0");
		}
		listD.forEach(item->{
			int h = Integer.parseInt(item.getString("time"));
			pdh.put("d"+h,item.getString("amount"));
		});
		if (pdt == null || DATE.isBefore(todayDate)) {
			DATE = todayDate;
			pdt = new PageData();
			for (int i = 0; i < 24; i++) {
				pdt.put("w"+i, "0");
				pdt.put("m"+i, "0");
			}
			pd.put("dayHour", weekDate.toString());
			page.setPd(pd);
			List<PageData> listW = ordermanagerService.getAmountForDayHour(page);
			listW.forEach(item->{
				int h = Integer.parseInt(item.getString("time"));
				pdt.put("w"+h,item.getString("amount"));
			}); 
			pd.put("dayHour", monthDate.toString() );
			page.setPd(pd);
			List<PageData> listM = ordermanagerService.getAmountForDayHour(page);
			listM.forEach(item->{
				int h = Integer.parseInt(item.getString("time"));
				pdt.put("m"+h,item.getString("amount"));
			}); 
		}
		return pdh;
	}

}
