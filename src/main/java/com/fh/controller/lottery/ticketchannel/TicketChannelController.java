package com.fh.controller.lottery.ticketchannel;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.fh.service.lottery.ticketchannel.TicketChannelManager;
import com.fh.service.lottery.ticketchannellotteryclassify.TicketChannelLotteryClassifyManager;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：出票渠道管理 创建人：FH Q313596790 创建时间：2018-07-31
 */
@Controller
@RequestMapping(value = "/ticketchannel")
public class TicketChannelController extends BaseController {

	String menuUrl = "ticketchannel/list.do"; // 菜单地址(权限用)
	@Resource(name = "ticketchannelService")
	private TicketChannelManager ticketchannelService;
	@Resource(name = "ticketchannellotteryclassifyService")
	private TicketChannelLotteryClassifyManager ticketchannellotteryclassifyService;
	@Resource(name = "userActionLogService")
	private UserActionLogService userActionLogService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增TicketChannel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // id
		ticketchannelService.save(pd);
		userActionLogService.save("1", "1", "出票渠道管理", "添出票渠道:" + pd.getString("channel_name"));
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
		logBefore(logger, Jurisdiction.getUsername() + "删除TicketChannel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = ticketchannelService.findById(pd);
		ticketchannelService.delete(pd);
		userActionLogService.save("1", "1", "出票渠道管理", "删除出票渠道,出票渠道为:" + pd.toString());
		out.write("success");
		out.close();
	}

	/**
	 * 停用 启用
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "TicketChannel启用停用操作");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		PageData pdOld = new PageData();
		pd = this.getPageData();
		pdOld = ticketchannelService.findById(pd);
		ticketchannelService.updateStatus(pd);
		userActionLogService.saveByObject("1", "出票渠道管理:" + pd.getString("classify_name"), pdOld, pd);
		ticketchannellotteryclassifyService.updateStatusByTicketChannelId(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改TicketChannel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData pdOld = new PageData();
		pdOld = ticketchannelService.findById(pd);
		ticketchannelService.edit(pd);
		userActionLogService.saveByObject("1", "出票渠道管理:" + pd.getString("classify_name"), pdOld, pd);
		ticketchannellotteryclassifyService.updateStatusByTicketChannelId(pd);

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
		logBefore(logger, Jurisdiction.getUsername() + "列表TicketChannel");
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
		List<PageData> varList = ticketchannelService.list(page); // 列出TicketChannel列表
		List<PageData> ticketchannellotteryclassifyList = ticketchannellotteryclassifyService.listAll(pd);
		for (int i = 0; i < varList.size(); i++) {
			String lotteryId = varList.get(i).getString("id");
			List<PageData> lotteryList = ticketchannellotteryclassifyList.stream().filter(item -> item.getString("ticket_channel_id").equals(lotteryId)).collect(Collectors.toList());
			varList.get(i).put("lotteryList", lotteryList);
		}
		mv.setViewName("lottery/ticketchannel/ticketchannel_list");
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
		mv.setViewName("lottery/ticketchannel/ticketchannel_edit");
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
		pd = ticketchannelService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/ticketchannel/ticketchannel_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除TicketChannel");
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
			ticketchannelService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出TicketChannel到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id"); // 1
		titles.add("渠道名称"); // 2
		titles.add("渠道编码"); // 3
		titles.add("出票Url"); // 4
		titles.add("备注5"); // 5
		titles.add("备注6"); // 6
		titles.add("备注7"); // 7
		titles.add("状态"); // 8
		titles.add("备注9"); // 9
		titles.add("备注10"); // 10
		dataMap.put("titles", titles);
		List<PageData> varOList = ticketchannelService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("channel_name")); // 2
			vpd.put("var3", varOList.get(i).getString("channel_code")); // 3
			vpd.put("var4", varOList.get(i).getString("ticket_url")); // 4
			vpd.put("var5", varOList.get(i).getString("ticket_merchant")); // 5
			vpd.put("var6", varOList.get(i).getString("ticket_merchant_password")); // 6
			vpd.put("var7", varOList.get(i).getString("ticket_notify_utl")); // 7
			vpd.put("var8", varOList.get(i).get("channel_status").toString()); // 8
			vpd.put("var9", varOList.get(i).get("add_time").toString()); // 9
			vpd.put("var10", varOList.get(i).get("update_time").toString()); // 10
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
