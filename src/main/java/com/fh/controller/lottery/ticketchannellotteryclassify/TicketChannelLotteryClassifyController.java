package com.fh.controller.lottery.ticketchannellotteryclassify;

import java.io.PrintWriter;
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
import com.fh.service.lottery.lotteryclassify.LotteryClassifyManager;
import com.fh.service.lottery.ticketchannel.TicketChannelManager;
import com.fh.service.lottery.ticketchannellotteryclassify.TicketChannelLotteryClassifyManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：渠道彩种管理 创建人：FH Q313596790 创建时间：2018-07-31
 */
@Controller
@RequestMapping(value = "/ticketchannellotteryclassify")
public class TicketChannelLotteryClassifyController extends BaseController {

	String menuUrl = "ticketchannellotteryclassify/list.do"; // 菜单地址(权限用)
	@Resource(name = "ticketchannellotteryclassifyService")
	private TicketChannelLotteryClassifyManager ticketchannellotteryclassifyService;
	@Resource(name = "ticketchannelService")
	private TicketChannelManager ticketchannelService;
	@Resource(name = "lotteryclassifyService")
	private LotteryClassifyManager lotteryclassifyService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增TicketChannelLotteryClassify");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		pd.put("status", "1"); // id
		pd.put("add_time", DateUtilNew.getCurrentTimeLong()); // 创建时间
		pd.put("update_time", DateUtilNew.getCurrentTimeLong()); // 更新时间
		ticketchannellotteryclassifyService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除TicketChannelLotteryClassify");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		ticketchannellotteryclassifyService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改状态
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除TicketChannelLotteryClassify");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		ticketchannellotteryclassifyService.updateStatus(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改TicketChannelLotteryClassify");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		ticketchannellotteryclassifyService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表TicketChannelLotteryClassify");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = ticketchannellotteryclassifyService.list(page); // 列出TicketChannelLotteryClassify列表
		mv.setViewName("lottery/ticketchannellotteryclassify/ticketchannellotteryclassify_list");
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
		mv.setViewName("lottery/ticketchannellotteryclassify/ticketchannellotteryclassify_edit");
		List<PageData> ticketchannelList = ticketchannelService.listAll(pd);
		List<PageData> lotteryclassifyList = lotteryclassifyService.listAll(pd);
		mv.addObject("ticketchannelList", ticketchannelList);
		mv.addObject("lotteryclassifyList", lotteryclassifyList);
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
		pd = ticketchannellotteryclassifyService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/ticketchannellotteryclassify/ticketchannellotteryclassify_edit");
		List<PageData> ticketchannelList = ticketchannelService.listAll(pd);
		List<PageData> lotteryclassifyList = lotteryclassifyService.listAll(pd);
		mv.addObject("ticketchannelList", ticketchannelList);
		mv.addObject("lotteryclassifyList", lotteryclassifyList);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除TicketChannelLotteryClassify");
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
			ticketchannellotteryclassifyService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出TicketChannelLotteryClassify到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("出票公司id"); // 2
		titles.add("出票公司名称"); // 3
		titles.add("彩种Id"); // 4
		titles.add("彩种名称"); // 5
		titles.add("彩种编码"); // 6
		titles.add("最小投注金额"); // 7
		titles.add("最大投注金额"); // 8
		titles.add("截售时间"); // 9
		titles.add("开机时间"); // 10
		titles.add("关机时间"); // 11
		titles.add("状态"); // 12
		titles.add("创建时间"); // 13
		titles.add("更新时间"); // 14
		dataMap.put("titles", titles);
		List<PageData> varOList = ticketchannellotteryclassifyService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("ticket_channel_id").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("ticket_channel_name")); // 3
			vpd.put("var4", varOList.get(i).get("lottery_classify_id").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("lottery_classify_name")); // 5
			vpd.put("var6", varOList.get(i).getString("game")); // 6
			vpd.put("var7", varOList.get(i).getString("min_bet_amount")); // 7
			vpd.put("var8", varOList.get(i).getString("max_bet_amount")); // 8
			vpd.put("var9", varOList.get(i).get("sale_end_time").toString()); // 9
			vpd.put("var10", varOList.get(i).get("matchine_open_time").toString()); // 10
			vpd.put("var11", varOList.get(i).get("matchine_close_time").toString()); // 11
			vpd.put("var12", varOList.get(i).get("status").toString()); // 12
			vpd.put("var13", varOList.get(i).get("add_time").toString()); // 13
			vpd.put("var14", varOList.get(i).get("update_time").toString()); // 14
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
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
