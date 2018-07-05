package com.fh.controller.lottery.datastatistics;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.channel.ChannelDistributorManager;
import com.fh.service.lottery.channel.ChannelManager;
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.service.lottery.channel.impl.ChannelDistributorService;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.service.lottery.useraccountmanager.impl.UserAccountManagerService;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/present")
public class PresentDataController extends BaseController {

	String menuUrl = "present/list.do"; // 西安后台管理数据
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerService useraccountmanagerService;
	@Resource(name = "channeloptionlogService")
	private ChannelOptionLogManager channeloptionlogService;
	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;
	
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
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart+" 00:00:00"));
			pd.put("lastStart",lastStart);
			mv.addObject("now", "今日");
		}
		if (null != lastStart && !"".equals(lastStart)) {
			lastStart = lastStart+" 00:00:00";
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}  
		if (null != lastEnd && !"".equals(lastEnd)) {
			lastEnd = lastEnd+" 23:59:59";
		} else {
			lastEnd = LocalDate.now()+" 23:59:59";
		}
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		//注册统计
		page.setPd(pd);
		List<PageData> registerList = usermanagercontrollerService.listAll(pd);
		//购彩统计
		String process_type = "3"; // 购彩
		pd.put("process_type", process_type);
		page.setPd(pd);
		List<PageData> listBuy = useraccountmanagerService.findByProcessType(page);
		int countBuy = 0;
		BigDecimal amountBuy = new BigDecimal(0);
		if (listBuy.size() > 0) {
			countBuy = Integer.parseInt(listBuy.get(0).getString("userCount"));
			amountBuy = new BigDecimal(listBuy.get(0).getString("amountSum"));
		}
		//充值统计
		process_type = "2"; // 充值
		pd.put("process_type", process_type);
		page.setPd(pd);
		List<PageData> listRecharge = useraccountmanagerService.findByProcessType(page);
		int countRecharge = 0;
		BigDecimal amountRecharge = new BigDecimal(0);
		if (listRecharge.size() > 0) {
			countRecharge = Integer.parseInt(listRecharge.get(0).getString("userCount"));
			amountRecharge = new BigDecimal(listRecharge.get(0).getString("amountSum"));
		}
		
		
		mv.setViewName("lottery/datastatistics/presentdata_list");
		mv.addObject("register", registerList.size());
		mv.addObject("countBuy", countBuy);
		mv.addObject("amountBuy", amountBuy.negate());
		mv.addObject("countRecharge", countRecharge);
		mv.addObject("amountRecharge", amountRecharge);
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
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ChannelXiAn到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("日期"); // 1
		titles.add("用户名"); // 2
		titles.add("手机号"); // 3
		titles.add("所属门店"); // 4
		titles.add("所属店员"); // 5
		titles.add("节点"); // 6
		titles.add("购彩金额"); // 7
		titles.add("门店提成"); // 8
		titles.add("店员提成"); // 9
		dataMap.put("titles", titles);
		Page page = new Page();
		page.setPd(pd);
		BigDecimal optionAmount = new BigDecimal(0);
		BigDecimal optionAmountChl = new BigDecimal(0);
		BigDecimal optionAmountCdt = new BigDecimal(0);
		HashSet<String> consumerSet = new HashSet<String>();
		List<PageData> dataList = useraccountmanagerService.findByProcessType(page);
		List<PageData> varList = new ArrayList<PageData>();
		PageData count = new PageData();
		count.put("var1", "合计"); // 1
		count.put("var2", consumerSet.size()); // 2
		count.put("var3", ""); // 3
		count.put("var4", ""); // 4
		count.put("var5", ""); // 5
		count.put("var7", optionAmount); // 7
		count.put("var8", optionAmountChl); // 8
		count.put("var9", optionAmountCdt); // 9
		varList.add(count);
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

}
