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
import com.fh.service.lottery.channel.ChannelConsumerManager;
import com.fh.service.lottery.channel.ChannelDistributorManager;
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：渠道分销(店员) 创建人：FH Q313596790 创建时间：2018-05-18
 */
@Controller
@RequestMapping(value = "/channeldistributor")
public class ChannelDistributorController extends BaseController {

	String menuUrl = "channeldistributor/list.do"; // 菜单地址(权限用)
	@Resource(name = "channeldistributorService")
	private ChannelDistributorManager channeldistributorService;
	@Resource(name = "channelconsumerService")
	private ChannelConsumerManager channelconsumerService;

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
		logBefore(logger, Jurisdiction.getUsername() + "新增ChannelDistributor");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("channel_distributor_id", this.get32UUID()); //主键
		pd.put("channel_distributor_id", "0"); // 分销员Id
		pd.put("channel_id", "0"); // 渠道Id
		pd.put("user_id", "0"); // 用户Id
		pd.put("add_time", "0"); // 添加时间
		pd.put("deleted", "0"); // 是否删除
		channeldistributorService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ChannelDistributor");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		channeldistributorService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改ChannelDistributor");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		channeldistributorService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ChannelDistributor");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String channel_id = pd.getString("channel_id");
		if (null != channel_id && !"".equals(channel_id)) {
			pd.put("channel_id", channel_id.trim());
		}
		page.setPd(pd);
		List<PageData> varList = channeldistributorService.list(page); // 列出ChannelDistributor列表
		PageData pda = new PageData();
		List<PageData> optionlogList = channeloptionlogService.listAll(pda);
		for (int i = 0; i < varList.size(); i++) {
			BigDecimal big = new BigDecimal(0);
			PageData distributor = varList.get(i);
			for (int j = 0; j < optionlogList.size(); j++) {
				PageData optionlog = varList.get(i);
				if (distributor.getString("channel_distributor_id").equals(optionlog.getString("distributor_id"))) {
					if (null != optionlog.getString("option_amount") && !"".equals(optionlog.getString("option_amount"))) {
						BigDecimal big_option_amount = new BigDecimal(optionlog.getString("option_amount"));
						big.add(big_option_amount);
					}
				}
			}
			BigDecimal distributorRate = new BigDecimal(distributor.getString("distributor_commission_rate"));
			varList.get(i).put("total_amount", big);
			varList.get(i).put("total_amount_extract", big.multiply(distributorRate));
		}
		mv.setViewName("lottery/channeldistributor/channeldistributor_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("channelId", channel_id);
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
		mv.setViewName("lottery/channeldistributor/channeldistributor_edit");
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
		pd = channeldistributorService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/channeldistributor/channeldistributor_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ChannelDistributor");
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
			channeldistributorService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ChannelDistributor到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("分销员Id"); // 1
		titles.add("渠道Id"); // 2
		titles.add("用户Id"); // 3
		titles.add("用户名"); // 4
		titles.add("渠道分销号"); // 5
		titles.add("电话"); // 6
		titles.add("分销佣金比例"); // 7
		titles.add("渠道名称"); // 8
		titles.add("添加时间"); // 9
		titles.add("备注"); // 10
		titles.add("是否删除"); // 11
		dataMap.put("titles", titles);
		List<PageData> varOList = channeldistributorService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("channel_distributor_id").toString()); // 1
			vpd.put("var2", varOList.get(i).get("channel_id").toString()); // 2
			vpd.put("var3", varOList.get(i).get("user_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("user_name")); // 4
			vpd.put("var5", varOList.get(i).getString("channel_distributor_num")); // 5
			vpd.put("var6", varOList.get(i).getString("mobile")); // 6
			vpd.put("var7", varOList.get(i).getString("distributor_commission_rate")); // 7
			vpd.put("var8", varOList.get(i).getString("channel_name")); // 8
			vpd.put("var9", varOList.get(i).get("add_time").toString()); // 9
			vpd.put("var10", varOList.get(i).getString("remark")); // 10
			vpd.put("var11", varOList.get(i).get("deleted").toString()); // 11
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

	@RequestMapping(value = "/goConsumerListByTime")
	public ModelAndView goConsumerListByTime() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		List<PageData> consumerPd = channelconsumerService.findByChannelId(pd);
		pd = this.getPageData();
		List<PageData> pds = channeloptionlogService.goConsumerListByTime(pd); // 根据ID读取

		if (pd.getString("distributorId") != null) {
			for (int i = 0; i < pds.size(); i++) {
				int a = 0;
				int b = 0;
				for (int j = 0; j < consumerPd.size(); j++) {
					// 判断是哪一天的
					if (DateUtil.fomatDate(pds.get(i).getString("optionTime")).getTime() == DateUtil.fomatDate(consumerPd.get(j).getString("add_time")).getTime()) {
						// 判断电话为不为空 来计数输入手机号人数
						if (consumerPd.get(j).getString("mobile") != null) {
							a += 1;
						}
						// 判断用户Id为不为空 来计数领没领取优惠券
						if (consumerPd.get(j).getString("user_id") != null) {
							b += 1;
						}
					}
				}
				pds.get(i).put("inputNum", a);
				pds.get(i).put("receiveNum", b);
			}
		}
		mv.setViewName("lottery/channeloptionlog/channeloptionlog_list");
		mv.addObject("varList", pds);
		mv.addObject("pd", pd);
		mv.addObject("distributorId", pd.getString("distributorId"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
