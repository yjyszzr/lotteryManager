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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.fh.service.lottery.channel.ChannelManager;
import com.fh.service.lottery.channel.ChannelOptionLogManager;
import com.fh.service.lottery.usermanagercontroller.UserManagerControllerManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
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

	@Resource(name = "usermanagercontrollerService")
	private UserManagerControllerManager usermanagercontrollerService;

	@Resource(name = "channelService")
	private ChannelManager channelService;

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
		String rate = pd.getString("distributor_commission_rate");
		if (!rate.equals("0")) {
			BigDecimal bg = new BigDecimal(rate);
			BigDecimal bg100 = new BigDecimal(100);
			pd.put("distributor_commission_rate", bg.divide(bg100));
		}
		// pd.put("channel_distributor_id", this.get32UUID()); //主键
		pd.put("channel_distributor_id", "0"); // 分销员Id
		// pd.put("channel_id", "0"); // 渠道Id
		// pd.put("user_id", "0"); // 用户Id
		pd.put("add_time", DateUtilNew.getCurrentTimeLong()); // 添加时间
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
		List<PageData> channeldistributorGroup = channeldistributorService.listGroupUserId(pda); // 按照用户Id分组
		List<PageData> optionlogList = channeloptionlogService.listAll(pda);
		List<PageData> consumerPd = channelconsumerService.listAll(pda);
		for (int i = 0; i < varList.size(); i++) {
			BigDecimal big = new BigDecimal(0);
			PageData distributor = varList.get(i);
			Integer loginNum = 0;
			Integer inputNum = 0;
			Integer receiveNum = 0;
			Integer buyLotteryNum = 0;
			for (int j = 0; j < optionlogList.size(); j++) {
				PageData optionlog = optionlogList.get(j);
				if (distributor.getString("channel_distributor_id").equals(optionlog.getString("distributor_id"))) {
					if (null != optionlog.getString("option_amount") && !"".equals(optionlog.getString("option_amount"))) {
						BigDecimal big_option_amount = new BigDecimal(optionlog.getString("option_amount"));
						big = big.add(big_option_amount);
					}
					if (optionlog.getString("operation_node").equals("1")) {
						loginNum += 1;
					}
				}
			}
			for (int k = 0; k < consumerPd.size(); k++) {
				if (consumerPd.get(k).getString("channel_distributor_id").equals(varList.get(i).getString("channel_distributor_id"))) {
					// 判断用户电话
					inputNum += 1;
					// 判断用户Id为不为空 来计数领没领取优惠券
					if (consumerPd.get(k).getString("user_id") != null) {
						receiveNum += 1;
					}
				}
			}
			for (int s = 0; s < channeldistributorGroup.size(); s++) {
				if (channeldistributorGroup.get(s).getString("distributor_id").equals(varList.get(i).getString("channel_distributor_id"))) {
					buyLotteryNum += 1;
				}
			}
			BigDecimal distributorRate = new BigDecimal(distributor.getString("distributor_commission_rate"));
			BigDecimal bg100 = new BigDecimal(100);
			varList.get(i).put("loginNum", loginNum);// 登录量
			varList.get(i).put("inputNum", inputNum);// 手机号输入量
			varList.get(i).put("receiveNum", receiveNum);// 领取彩金量
			varList.get(i).put("buyLotteryNum", buyLotteryNum);// 购彩量
			varList.get(i).put("distributor_commission_rate", distributorRate.multiply(bg100));
			varList.get(i).put("total_amount", big);
			varList.get(i).put("total_amount_extract", big.multiply(distributorRate).doubleValue() + loginNum);
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
		List<PageData> userAll = usermanagercontrollerService.findAll();
		mv.addObject("userAll", userAll);
		mv.addObject("userListJson", userList2Json(userAll));
		PageData pda = new PageData();
		List<PageData> channelAll = channelService.listAll(pda);
		mv.addObject("channelAll", channelAll);
		mv.setViewName("lottery/channeldistributor/channeldistributor_edit");
		mv.addObject("msg", "save");
		mv.addObject("channel_id", 0);
		mv.addObject("user_id", 0);
		return mv;
	}

	public static JSONArray userList2Json(List<PageData> userAll) {
		JSONArray json = new JSONArray();
		for (PageData user : userAll) {
			JSONObject jo = new JSONObject();
			jo.put("userId", user.getString("user_id"));
			jo.put("userName", user.getString("user_name"));
			jo.put("mobile", user.getString("mobile"));
			json.add(jo);
		}
		return json;
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
		String rate = pd.getString("distributor_commission_rate");
		BigDecimal bg = new BigDecimal(rate);
		BigDecimal bg100 = new BigDecimal(100);
		pd.put("distributor_commission_rate", bg.multiply(bg100));
		List<PageData> userAll = usermanagercontrollerService.findAll();
		mv.addObject("userAll", userAll);
		PageData pda = new PageData();
		List<PageData> channelAll = channelService.listAll(pda);
		mv.addObject("channelAll", channelAll);
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
		titles.add("渠道名称"); // 1
		titles.add("用户名"); // 2
		titles.add("渠道分销号"); // 3
		titles.add("电话"); // 4
		titles.add("领取人数"); // 5
		titles.add("登录人数"); // 6
		titles.add("购彩人数"); // 7
		titles.add("总销售额"); // 8
		titles.add("佣金比例"); // 9
		titles.add("销售提成"); // 10
		titles.add("添加时间"); // 11
		titles.add("备注"); // 12
		dataMap.put("titles", titles);
		List<PageData> varOList = channeldistributorService.listAll(pd);
		PageData pda = new PageData();
		List<PageData> channeldistributorGroup = channeldistributorService.listGroupUserId(pda); // 按照用户Id分组
		List<PageData> optionlogList = channeloptionlogService.listAll(pda);
		List<PageData> consumerPd = channelconsumerService.listAll(pda);
		for (int i = 0; i < varOList.size(); i++) {
			BigDecimal big = new BigDecimal(0);
			PageData distributor = varOList.get(i);
			Integer loginNum = 0;
			Integer inputNum = 0;
			Integer receiveNum = 0;
			Integer buyLotteryNum = 0;
			for (int j = 0; j < optionlogList.size(); j++) {
				PageData optionlog = optionlogList.get(j);
				if (distributor.getString("channel_distributor_id").equals(optionlog.getString("distributor_id"))) {
					if (null != optionlog.getString("option_amount") && !"".equals(optionlog.getString("option_amount"))) {
						BigDecimal big_option_amount = new BigDecimal(optionlog.getString("option_amount"));
						big = big.add(big_option_amount);
					}
					if (optionlog.getString("operation_node").equals("1")) {
						loginNum += 1;
					}
				}
			}
			for (int k = 0; k < consumerPd.size(); k++) {
				if (consumerPd.get(k).getString("channel_distributor_id").equals(varOList.get(i).getString("channel_distributor_id"))) {
					// 判断用户电话
					inputNum += 1;
					// 判断用户Id为不为空 来计数领没领取优惠券
					if (consumerPd.get(k).getString("user_id") != null) {
						receiveNum += 1;
					}
				}
			}
			for (int s = 0; s < channeldistributorGroup.size(); s++) {
				if (channeldistributorGroup.get(s).getString("distributor_id").equals(varOList.get(i).getString("channel_distributor_id"))) {
					buyLotteryNum += 1;
				}
			}
			BigDecimal distributorRate = new BigDecimal(distributor.getString("distributor_commission_rate"));
			BigDecimal bg100 = new BigDecimal(100);
			varOList.get(i).put("loginNum", loginNum);// 登录量
			varOList.get(i).put("inputNum", inputNum);// 手机号输入量
			varOList.get(i).put("receiveNum", receiveNum);// 领取彩金量
			varOList.get(i).put("buyLotteryNum", buyLotteryNum);// 购彩量
			varOList.get(i).put("distributor_commission_rate", distributorRate.multiply(bg100));
			varOList.get(i).put("total_amount", big);
			varOList.get(i).put("total_amount_extract", big.multiply(distributorRate).doubleValue() + loginNum);
		}

		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("channel_name").toString()); // 1
			vpd.put("var2", varOList.get(i).get("user_name").toString()); // 2
			vpd.put("var3", varOList.get(i).get("channel_distributor_num").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("mobile")); // 4
			vpd.put("var5", varOList.get(i).getString("receiveNum")); // 5
			vpd.put("var6", varOList.get(i).getString("loginNum")); // 6
			vpd.put("var7", varOList.get(i).getString("buyLotteryNum")); // 7
			vpd.put("var8", varOList.get(i).getString("total_amount")); // 8
			vpd.put("var9", varOList.get(i).getString("distributor_commission_rate") + "%"); // 9
			vpd.put("var10", varOList.get(i).getString("total_amount_extract")); // 10
			vpd.put("var11", DateUtil.toSDFTime(Integer.parseInt(varOList.get(i).getString("add_time")) * 1000)); // 11
			vpd.put("var12", varOList.get(i).get("remark").toString()); // 12
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
		pd = this.getPageData();
		List<PageData> consumerPd = channelconsumerService.findByChannelId(pd);
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
