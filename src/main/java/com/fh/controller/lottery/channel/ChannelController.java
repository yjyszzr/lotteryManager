package com.fh.controller.lottery.channel;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.channel.ChannelManager;
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 说明：渠道模块 创建人：FH Q313596790 创建时间：2018-05-18
 */
@Controller
@RequestMapping(value = "/channel")
public class ChannelController extends BaseController {

	String menuUrl = "channel/list.do"; // 菜单地址(权限用)
	@Resource(name = "channelService")
	private ChannelManager channelService;
	@Resource(name = "channeloptionlogService")
	private ChannelOptionLogManager channeloptionlogService;
	@Resource(name="userActionLogService")
	private UserActionLogService ACLOG;
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Channel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String rate = pd.getString("commission_rate");
		if (!rate.equals("0")) {
			BigDecimal bg = new BigDecimal(rate);
			BigDecimal bg100 = new BigDecimal(100);
			pd.put("commission_rate", bg.divide(bg100));
		}
		// pd.put("channel_id", this.get32UUID()); //主键
		pd.put("channel_id", "0"); // 渠道ID
		pd.put("channel_status", "0"); // 状态
		pd.put("add_time", "0"); // 时间
		pd.put("deleted", "0"); // 是否删除
		channelService.save(pd);
		ACLOG.save("1", "1", "渠道管理："+pd.getString("channel_name"), "渠道号："+pd.getString("channel_num"));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Channel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = channelService.findById(pd);
		channelService.delete(pd);
		ACLOG.save("1","2", "渠道管理:"+pdOld.getString("channel_name"), pdOld.toString());
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Channel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdOld = channelService.findById(pd);
		String rate = pd.getString("commission_rate");
		if (!rate.equals("0")) {
			BigDecimal bg = new BigDecimal(rate);
			BigDecimal bg100 = new BigDecimal(100);
			pd.put("commission_rate", bg.divide(bg100));
		}
		channelService.edit(pd);
		ACLOG.saveByObject("1", "渠道管理:"+pd.getString("channel_name"), pdOld, pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Channel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String channel_name = pd.getString("channel_name");
		if (null != channel_name && !"".equals(channel_name)) {
			pd.put("channel_name", channel_name.trim());
		}
		page.setPd(pd);
		List<PageData> varList = channelService.list(page); // 列出Channel列表

		PageData pda = new PageData();
		List<PageData> optionlogList = channeloptionlogService.listAll(pda);

		for (int i = 0; i < varList.size(); i++) {
			BigDecimal big = new BigDecimal(0);
			PageData channel = varList.get(i);
			for (int j = 0; j < optionlogList.size(); j++) {
				PageData optionlog = optionlogList.get(j);
				if (channel.getString("channel_id").equals(optionlog.getString("channel_id"))) {
					if (null != optionlog.getString("option_amount") && !"".equals(optionlog.getString("option_amount"))) {
						BigDecimal big_option_amount = new BigDecimal(optionlog.getString("option_amount"));
						big = big.add(big_option_amount);
					}
				}
			}
			BigDecimal channelRate = new BigDecimal(channel.getString("commission_rate"));
			BigDecimal bg100 = new BigDecimal(100);
			varList.get(i).put("commission_rate", channelRate.multiply(bg100));
			varList.get(i).put("total_amount", big);
			varList.get(i).put("total_amount_extract", big.multiply(channelRate));
		}

		mv.setViewName("lottery/channel/channel_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lottery/channel/channel_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = channelService.findById(pd); // 根据ID读取
		String rate = pd.getString("commission_rate");
		BigDecimal bg = new BigDecimal(rate);
		BigDecimal bg100 = new BigDecimal(100);
		pd.put("commission_rate", bg.multiply(bg100));
		mv.setViewName("lottery/channel/channel_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Channel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			channelService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Channel到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("渠道名称"); // 1
		titles.add("渠道号"); // 2
		titles.add("总金额"); // 3
		titles.add("佣金比例"); // 4
		titles.add("提成"); // 5
		titles.add("渠道类型"); // 6
		titles.add("渠道联系人"); // 7
		titles.add("电话"); // 8
		titles.add("地址"); // 9
		// titles.add("时间"); // 10
		dataMap.put("titles", titles);
		List<PageData> varList = channelService.listAll(pd); // 列出Channel列表
		List<PageData> excelList = new ArrayList<PageData>();

		PageData pda = new PageData();
		List<PageData> optionlogList = channeloptionlogService.listAll(pda);
		for (int i = 0; i < varList.size(); i++) {
			BigDecimal big = new BigDecimal(0);
			PageData channel = varList.get(i);
			for (int j = 0; j < optionlogList.size(); j++) {
				PageData optionlog = optionlogList.get(j);
				if (channel.getString("channel_id").equals(optionlog.getString("channel_id"))) {
					if (null != optionlog.getString("option_amount") && !"".equals(optionlog.getString("option_amount"))) {
						BigDecimal big_option_amount = new BigDecimal(optionlog.getString("option_amount"));
						big = big.add(big_option_amount);
					}
				}
			}
			BigDecimal channelRate = new BigDecimal(StringUtil.isEmptyStr(channel.getString("commission_rate")) ? "0" : channel.getString("commission_rate"));
			BigDecimal bg100 = new BigDecimal(100);
			varList.get(i).put("commission_rate", channelRate.multiply(bg100));
			varList.get(i).put("total_amount", big);
			varList.get(i).put("total_amount_extract", big.multiply(channelRate));

			PageData vpd = new PageData();
			vpd.put("var1", varList.get(i).get("channel_name")); // 1
			vpd.put("var2", varList.get(i).getString("channel_num")); // 2
			vpd.put("var3", varList.get(i).getString("total_amount")); // 3
			vpd.put("var4", varList.get(i).getString("commission_rate") + "%"); // 4
			vpd.put("var5", varList.get(i).get("total_amount_extract").toString()); // 5
			vpd.put("var6", "西安每一天便利店"); // 6
			vpd.put("var7", varList.get(i).getString("channel_contact")); // 7
			vpd.put("var8", varList.get(i).getString("channel_mobile")); // 8
			vpd.put("var9", varList.get(i).get("channel_address")); // 9
			// vpd.put("var10",
			// DateUtil.toSDFTime(Integer.parseInt(varList.get(i).getString("add_time"))));
			// // 10
			excelList.add(vpd);
		}

		dataMap.put("varList", excelList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
