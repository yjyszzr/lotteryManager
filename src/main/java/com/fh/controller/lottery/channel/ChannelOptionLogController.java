package com.fh.controller.lottery.channel;

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
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：渠道分销操作日志 创建时间：2018-05-27
 */
@Controller
@RequestMapping(value = "/channeloptionlog")
public class ChannelOptionLogController extends BaseController {

	String menuUrl = "channeloptionlog/list.do"; // 菜单地址(权限用)
	@Resource(name = "channeloptionlogService")
	private ChannelOptionLogManager channeloptionlogService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增ChannelOptionLog");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("option_id", this.get32UUID()); //主键
		pd.put("option_id", "0"); // Id
		pd.put("channel_id", "0"); // 渠道Id
		pd.put("distributor_id", "0"); // 分销店员id
		pd.put("user_id", "0"); // 渠道消费者用户Id
		channeloptionlogService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ChannelOptionLog");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		channeloptionlogService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改ChannelOptionLog");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		channeloptionlogService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ChannelOptionLog");
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
		List<PageData> varList = channeloptionlogService.list(page); // 列出ChannelOptionLog列表
		List<PageData> userRealList = channeloptionlogService.findUserReal();
		Map<Integer, PageData> userRealMap = new HashMap<Integer, PageData>(userRealList.size());
		userRealList.forEach(item -> userRealMap.put(Integer.parseInt(item.getString("user_id")), item));
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = userRealMap.get(Integer.parseInt(varList.get(i).getString("user_id")));
			if (pageData != null) {
				varList.get(i).put("id_card_num", pageData.getString("id_code"));
				varList.get(i).put("true_name", pageData.getString("real_name"));
			}
		}
		mv.setViewName("lottery/channeloptionlog/channeloptionlog_details_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("pd", pd);
		mv.addObject("distributor_id", pd.getString("distributor_id"));
		mv.addObject("option_time", pd.getString("option_time"));
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
		mv.setViewName("channel/channeloptionlog/channeloptionlog_edit");
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
		pd = channeloptionlogService.findById(pd); // 根据ID读取
		mv.setViewName("channel/channeloptionlog/channeloptionlog_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ChannelOptionLog");
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
			channeloptionlogService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ChannelOptionLog到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("Id"); // 1
		titles.add("渠道Id"); // 2
		titles.add("分销店员id"); // 3
		titles.add("渠道消费者用户Id"); // 4
		titles.add("渠道消费者用户名称"); // 5
		titles.add("身份证编号"); // 6
		titles.add("真实姓名"); // 7
		titles.add("电话"); // 8
		titles.add("操作节点"); // 9
		titles.add("状态"); // 10
		titles.add("操作金额"); // 11
		titles.add("操作时间"); // 12
		titles.add("来源"); // 13
		dataMap.put("titles", titles);
		List<PageData> varOList = channeloptionlogService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("option_id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("channel_id").toString()); // 2
			vpd.put("var3", varOList.get(i).get("distributor_id").toString()); // 3
			vpd.put("var4", varOList.get(i).get("user_id").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("user_name")); // 5
			vpd.put("var6", varOList.get(i).get("id_card_num").toString()); // 6
			vpd.put("var7", varOList.get(i).getString("true_name")); // 7
			vpd.put("var8", varOList.get(i).getString("mobile")); // 8
			vpd.put("var9", varOList.get(i).getString("operation_node")); // 9
			vpd.put("var10", varOList.get(i).get("status").toString()); // 10
			vpd.put("var11", varOList.get(i).get("option_amount").toString()); // 11
			vpd.put("var12", varOList.get(i).get("option_time").toString()); // 12
			vpd.put("var13", varOList.get(i).getString("source")); // 13
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
