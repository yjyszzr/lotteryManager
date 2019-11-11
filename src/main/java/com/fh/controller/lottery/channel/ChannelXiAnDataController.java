package com.fh.controller.lottery.channel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/xiandata")
public class ChannelXiAnDataController extends BaseController {

	String menuUrl = "xiandata/list.do"; // 西安后台管理数据
	@Resource(name = "channelService")
	private ChannelManager channelService;
	@Resource(name = "channeloptionlogService")
	private ChannelOptionLogManager channeloptionlogService;
	@Resource(name = "channeldistributorService")
	private ChannelDistributorManager channeldistributorService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户操作列表ChannelOptionLogXiAn");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		List<PageData> channelList = channelService.listAll(pd);
		pd = this.getPageData();
		String channel_id = pd.getString("channel_id"); // 渠道条件
		if (null != channel_id && !"".equals(channel_id)) {
			pd.put("channel_id", channel_id.trim());
		}
		String operation_node = pd.getString("operation_node"); // 节点检索条件
		if (null != operation_node && !"".equals(operation_node)) {
			pd.put("operation_node", operation_node.trim());
		}
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (pd.isEmpty()) {
			lastStart = LocalDate.now().plusDays(-1).toString()+" 00:00:00";
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
			pd.put("lastStart",lastStart);
		}
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		} 
		String lastEnd = pd.getString("lastEnd"); // 结束检索条件
		long dayTime = DateUtilNew.getMilliSecondsByStr(LocalDate.now().plusDays(-1).toString()+" 23:59:59");
		if (null != lastEnd && !"".equals(lastEnd)) {
			long endTime = DateUtilNew.getMilliSecondsByStr(lastEnd);
			if(endTime>dayTime) {
				pd.put("lastEnd1", dayTime);
				pd.put("lastEnd", LocalDate.now().plusDays(-1).toString()+" 23:59:59");
			}else {
				pd.put("lastEnd1", endTime);
			}
		}else {
			pd.put("lastEnd1", dayTime);
			pd.put("lastEnd", LocalDate.now().plusDays(-1).toString()+" 23:59:59");
		}
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		List<PageData> optionlogList = channeloptionlogService.getOptionLogList(page);
		
		HashMap<String, String> channelMap = new HashMap<>();//渠道列表
		for (int i = 0; i < channelList.size(); i++) {
			if (null != channelList.get(i).get("channel_name") && !"".equals(channelList.get(i).get("channel_name"))) {
				if (null != channelList.get(i).get("channel_id") && !"".equals(channelList.get(i).get("channel_id"))) {
					String channel_value = channelList.get(i).getString("channel_id");
					String channel_text = channelList.get(i).getString("channel_name");
					channelMap.put(channel_value, channel_text);
				}
			}
		}
		
		BigDecimal optionAmount = new BigDecimal(0);//购彩金额总计
		BigDecimal optionAmountChl = new BigDecimal(0);//门店提成总计
		BigDecimal optionAmountCdt = new BigDecimal(0);//店员提成总计
		HashSet<String> consumerSet = new HashSet<String>();//用户数总计
		for (int i = 0; i < optionlogList.size(); i++) {
			PageData pageData = new PageData();
			pageData = optionlogList.get(i);
			//金额
			if (null != pageData.get("option_amount") && !"".equals(pageData.get("option_amount"))) {
				BigDecimal Big1 = new BigDecimal(pageData.getString("option_amount"));
				optionAmount = optionAmount.add(Big1);
			}
			if (null != pageData.get("option_amount_chl") && !"".equals(pageData.get("option_amount_chl"))) {
				BigDecimal Big1 = new BigDecimal(pageData.getString("option_amount_chl"));
				optionAmountChl = optionAmountChl.add(Big1);
			}
			if (null != pageData.get("option_amount_cdt") && !"".equals(pageData.get("option_amount_cdt"))) {
				BigDecimal Big1 = new BigDecimal(pageData.getString("option_amount_cdt"));
				optionAmountCdt = optionAmountCdt.add(Big1);
			}
			
			//节点转换
			if (null != pageData.get("operation_node") && !"".equals(pageData.get("operation_node"))) {
				int node = new Integer(pageData.getString("operation_node"));
				if(node==10) {
					pageData.put("operation_node", "领取");
				}
				if(node==1) {
					pageData.put("operation_node", "激活");
				}
				if(node==2) {
					pageData.put("operation_node", "购彩");
				}
			}
			
			//用户统计
			if(null != pageData.get("mobile") && !"".equals(pageData.get("mobile"))){
			consumerSet.add(pageData.getString("mobile"));
			}
			//门店列表
//			if (null != pageData.get("channel_name") && !"".equals(pageData.get("channel_name"))) {
//				if (null != pageData.get("channel_id") && !"".equals(pageData.get("channel_id"))) {
//					String channel_value = pageData.getString("channel_id");
//					String channel_text = pageData.getString("channel_name");
//					channelMap.put(channel_value, channel_text);
//				}
//			}
		}

		mv.setViewName("lottery/channeloptionlog/channeloptionlog_xian_details_list");
		mv.addObject("consumerSum", consumerSet.size());
		mv.addObject("channelMap", channelMap);
		mv.addObject("optionAmount", optionAmount);
		mv.addObject("optionAmountChl", optionAmountChl);
		mv.addObject("optionAmountCdt", optionAmountCdt);
		mv.addObject("varList", optionlogList);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ChannelXiAn到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String channel_id = pd.getString("channel_id"); // 渠道条件
		if (null != channel_id && !"".equals(channel_id)) {
			pd.put("channel_id", channel_id.trim());
		}
		String operation_node = pd.getString("operation_node"); // 节点检索条件
		if (null != operation_node && !"".equals(operation_node)) {
			pd.put("operation_node", operation_node.trim());
		}
		String lastStart = pd.getString("lastStart"); // 开始时间检索条件
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd"); // 结束检索条件
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setShowCount(65000);//单页显示条数，为了全部导出应用
		page.setPd(pd);
		BigDecimal optionAmount = new BigDecimal(0);
		BigDecimal optionAmountChl = new BigDecimal(0);
		BigDecimal optionAmountCdt = new BigDecimal(0);
		HashSet<String> consumerSet = new HashSet<String>();
		List<PageData> varOList = channeloptionlogService.getOptionLogList(page);
		List<PageData> varList = new ArrayList<PageData>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("所属门店"); // 1
		titles.add("日期"); // 2
		titles.add("用户名"); // 3
		titles.add("手机号"); // 4
		titles.add("所属店员"); // 5
		titles.add("节点"); // 6
		titles.add("购彩金额"); // 7
		titles.add("门店提成"); // 8
		titles.add("店员提成"); // 9
		dataMap.put("titles", titles);
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("channel_name").toString()); // 1
			if (null != varOList.get(i).get("option_time") && !"".equals(varOList.get(i).get("option_time"))) {
				vpd.put("var2",DateUtil.toSDFTime(Long.parseLong(varOList.get(i).getString("option_time"))*1000)); // 2
			}
			if (null != varOList.get(i).get("user_name") && !"".equals(varOList.get(i).get("user_name"))) {
				vpd.put("var3", varOList.get(i).get("user_name").toString()); // 3
			}
			if (null != varOList.get(i).get("mobile") && !"".equals(varOList.get(i).get("mobile"))) {
				vpd.put("var4", varOList.get(i).get("mobile").toString()); // 4
			}
			if (null != varOList.get(i).get("dis_name") && !"".equals(varOList.get(i).get("dis_name"))) {
				vpd.put("var5", varOList.get(i).getString("dis_name")); // 5
			}
			//节点转换
			if (null != varOList.get(i).get("operation_node") && !"".equals(varOList.get(i).get("operation_node"))) {
				int node = new Integer(varOList.get(i).getString("operation_node"));
				if(node==11) {
					continue; // 6
				}
				if(node==10) {
					vpd.put("var6", "领取"); // 6
				}
				if(node==1) {
					vpd.put("var6", "激活");
					BigDecimal Big1 = new BigDecimal("1");
					optionAmountCdt = optionAmountCdt.add(Big1);
				}
				if(node==2) {
					vpd.put("var6", "购彩");
				}
			}
			vpd.put("var7", varOList.get(i).getString("option_amount")); // 7
			vpd.put("var8", varOList.get(i).getString("option_amount_chl")); // 8
			vpd.put("var9", varOList.get(i).getString("option_amount_cdt")); // 9
			//金额
			if (null != varOList.get(i).get("option_amount") && !"".equals(varOList.get(i).get("option_amount"))) {
				BigDecimal Big1 = new BigDecimal(varOList.get(i).getString("option_amount"));
				optionAmount = optionAmount.add(Big1);
			}
			if (null != varOList.get(i).get("option_amount_chl") && !"".equals(varOList.get(i).get("option_amount_chl"))) {
				BigDecimal Big1 = new BigDecimal(varOList.get(i).getString("option_amount_chl"));
				optionAmountChl = optionAmountChl.add(Big1);
			}
			if (null != varOList.get(i).get("option_amount_cdt") && !"".equals(varOList.get(i).get("option_amount_cdt"))) {
				BigDecimal Big1 = new BigDecimal(varOList.get(i).getString("option_amount_cdt"));
				optionAmountCdt = optionAmountCdt.add(Big1);
			}
			
			//用户统计
			if(null != varOList.get(i).get("mobile") && !"".equals(varOList.get(i).get("mobile"))){
				consumerSet.add(varOList.get(i).getString("mobile"));
			}
			varList.add(vpd);
		}
		PageData count = new PageData();
		count.put("var1", "合计"); // 1
		count.put("var2", ""); // 2
		count.put("var3", ""); // 3
		count.put("var4", "用户数："+consumerSet.size()); // 4
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

	@RequestMapping(value = "/yesterdayList")
	public ModelAndView yesterdayList(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户操作列表ChannelOptionLogXiAn");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		LocalDate localDate = LocalDate.now();
		//查询条件用
		pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(localDate.plusDays(-1)+" 00:00:00"));//昨天凌晨
		pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(localDate+" 00:00:00"));//今天凌晨
		//查询详情传参用
		pd.put("lastStart", localDate.plusDays(-1)+" 00:00:00");
		pd.put("lastEnd", localDate.plusDays(-1)+" 23:59:59");
		pd.put("registerTime", LocalDate.now().plusDays(-1).toString());
		page.setPd(pd);
		List<PageData> optionlogList = channeloptionlogService.getXACountYesterday(page);
		String optionAmount = "0";//昨日购彩金额总计 
		int h = 0;//领取用户（页面显示注册）
		int l = 0;//激活用户
		int m = 0;//购彩用户
		for (int i = 0; i < optionlogList.size(); i++) {
			PageData pageData = new PageData();
			pageData = optionlogList.get(i);
			  
			if (null != pageData.get("operation_node") && !"".equals(pageData.get("operation_node"))) {
				int node = new Integer(pageData.getString("operation_node"));
				String count = pageData.getString("count");
				count = count.substring(0, count.length()-3);
				if(node==0 ) {
					h=Integer.parseInt(count);
				}
				if(node==1) {
					l=Integer.parseInt(count);
				}
				if(node==2) {
					m=Integer.parseInt(count); 
				}
				if(node==111) {
					optionAmount=pageData.getString("count"); 
				}
			}
			 
		} 
		mv.setViewName("lottery/channeloptionlog/channeloptionlog_xian_list");
		mv.addObject("h", h);
		mv.addObject("l", l);
		mv.addObject("m", m);
		mv.addObject("optionAmount", optionAmount);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;

	}
}
