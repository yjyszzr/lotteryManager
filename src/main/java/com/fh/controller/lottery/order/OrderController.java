package com.fh.controller.lottery.order;

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

import com.alibaba.fastjson.JSON;
import com.fh.common.TextConfig;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.order.OrderManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ManualAuditUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：订单模块 创建人：FH Q313596790 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

	String menuUrl = "order/list.do"; // 菜单地址(权限用)
	@Resource(name = "orderService")
	private OrderManager orderService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Order");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("order_id", this.get32UUID()); //主键
		pd.put("order_id", "0"); // 订单Id
		pd.put("order_sn", ""); // 订单编号
		pd.put("parent_sn", ""); // 父编号
		pd.put("user_id", "0"); // 用户Id
		pd.put("order_status", "0"); // 订单状态
		pd.put("pay_status", "0"); // 支付状态
		pd.put("pay_id", "0"); // 支付Id
		pd.put("pay_code", ""); // 支付code
		pd.put("pay_name", ""); // 支付名称
		pd.put("pay_sn", ""); // 支付编码
		pd.put("money_paid", ""); // 实付金额
		pd.put("ticket_amount", ""); // 投注金额
		pd.put("surplus", ""); // 余额支付
		pd.put("user_surplus", ""); // 可提现余额支付
		pd.put("user_surplus_limit", ""); // 不可提现余额支付
		pd.put("third_party_paid", ""); // 第三方支付金额
		pd.put("user_bonus_id", "0"); // 用户红包id
		pd.put("bonus", ""); // 红包金额
		pd.put("give_integral", "0"); // 赠送积分
		pd.put("order_from", ""); // 订单来源
		pd.put("add_time", "0"); // 添加时间
		pd.put("order_type", "0"); // 订单类型
		pd.put("lottery_classify_id", "0"); // 彩票类型Id
		pd.put("lottery_play_classify_id", "0"); // 玩法类型Id
		pd.put("winning_money", ""); // 中奖金额
		pd.put("play_type", ""); // 玩法
		pd.put("pass_type", ""); // 过关方式
		pd.put("bet_num", "0"); // 注数
		pd.put("cathectic", "0"); // 倍数
		pd.put("accept_time", "0"); // 接单时间
		pd.put("ticket_time", "0"); // 出票时间
		pd.put("forecast_money", ""); // 预测奖金
		pd.put("issue", ""); // 最后期次
		pd.put("is_delete", "0"); // 是否删除
		pd.put("ticket_num", "0"); // 彩票数
		orderService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除Order");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		orderService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Order");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		orderService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Order");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String order_sn = pd.getString("order_sn");
		if (null != order_sn && !"".equals(order_sn)) {
			pd.put("order_sn", order_sn.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String amountStart = pd.getString("amountStart");
		if (null != amountStart && !"".equals(amountStart)) {
			pd.put("amountStart", amountStart.trim());
		}
		String amountEnd = pd.getString("amountEnd");
		if (null != amountEnd && !"".equals(amountEnd)) {
			pd.put("amountEnd", amountEnd.trim());
		}

		page.setPd(pd);
		List<PageData> varList = orderService.list(page); // 列出Order列表

		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("pay_time") && !"".equals(pageData.get("pay_time"))) {
				pageData.put("pay_time", DateUtilNew.getCurrentTimeString(Long.parseLong(pageData.get("pay_time").toString()), DateUtilNew.datetimeFormat));
			}
		}
		mv.setViewName("lottery/order/order_list");
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
		mv.setViewName("lottery/order/order_edit");
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
		pd = orderService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/order/order_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Order");
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
			orderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出Order到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("订单Id"); // 1
		titles.add("订单编号"); // 2
		titles.add("父编号"); // 3
		titles.add("用户Id"); // 4
		titles.add("订单状态"); // 5
		titles.add("支付状态"); // 6
		titles.add("支付Id"); // 7
		titles.add("支付code"); // 8
		titles.add("支付名称"); // 9
		titles.add("支付编码"); // 10
		titles.add("实付金额"); // 11
		titles.add("投注金额"); // 12
		titles.add("余额支付"); // 13
		titles.add("可提现余额支付"); // 14
		titles.add("不可提现余额支付"); // 15
		titles.add("第三方支付金额"); // 16
		titles.add("用户红包id"); // 17
		titles.add("红包金额"); // 18
		titles.add("赠送积分"); // 19
		titles.add("订单来源"); // 20
		titles.add("添加时间"); // 21
		titles.add("支付时间"); // 22
		titles.add("订单类型"); // 23
		titles.add("彩票类型Id"); // 24
		titles.add("玩法类型Id"); // 25
		titles.add("备注26"); // 26
		titles.add("中奖金额"); // 27
		titles.add("玩法"); // 28
		titles.add("过关方式"); // 29
		titles.add("注数"); // 30
		titles.add("倍数"); // 31
		titles.add("接单时间"); // 32
		titles.add("出票时间"); // 33
		titles.add("预测奖金"); // 34
		titles.add("最后期次"); // 35
		titles.add("是否删除"); // 36
		titles.add("彩票数"); // 37
		dataMap.put("titles", titles);
		List<PageData> varOList = orderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("order_id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("order_sn")); // 2
			vpd.put("var3", varOList.get(i).getString("parent_sn")); // 3
			vpd.put("var4", varOList.get(i).get("user_id").toString()); // 4
			vpd.put("var5", varOList.get(i).get("order_status").toString()); // 5
			vpd.put("var6", varOList.get(i).get("pay_status").toString()); // 6
			vpd.put("var7", varOList.get(i).get("pay_id").toString()); // 7
			vpd.put("var8", varOList.get(i).getString("pay_code")); // 8
			vpd.put("var9", varOList.get(i).getString("pay_name")); // 9
			vpd.put("var10", varOList.get(i).getString("pay_sn")); // 10
			vpd.put("var11", varOList.get(i).getString("money_paid")); // 11
			vpd.put("var12", varOList.get(i).getString("ticket_amount")); // 12
			vpd.put("var13", varOList.get(i).getString("surplus")); // 13
			vpd.put("var14", varOList.get(i).getString("user_surplus")); // 14
			vpd.put("var15", varOList.get(i).getString("user_surplus_limit")); // 15
			vpd.put("var16", varOList.get(i).getString("third_party_paid")); // 16
			vpd.put("var17", varOList.get(i).get("user_bonus_id").toString()); // 17
			vpd.put("var18", varOList.get(i).getString("bonus")); // 18
			vpd.put("var19", varOList.get(i).get("give_integral").toString()); // 19
			vpd.put("var20", varOList.get(i).getString("order_from")); // 20
			vpd.put("var21", varOList.get(i).get("add_time").toString()); // 21
			vpd.put("var22", varOList.get(i).get("pay_time").toString()); // 22
			vpd.put("var23", varOList.get(i).get("order_type").toString()); // 23
			vpd.put("var24", varOList.get(i).get("lottery_classify_id").toString()); // 24
			vpd.put("var25", varOList.get(i).get("lottery_play_classify_id").toString()); // 25
			vpd.put("var26", varOList.get(i).getString("match_time")); // 26
			vpd.put("var27", varOList.get(i).getString("winning_money")); // 27
			vpd.put("var28", varOList.get(i).getString("play_type")); // 28
			vpd.put("var29", varOList.get(i).getString("pass_type")); // 29
			vpd.put("var30", varOList.get(i).get("bet_num").toString()); // 30
			vpd.put("var31", varOList.get(i).get("cathectic").toString()); // 31
			vpd.put("var32", varOList.get(i).get("accept_time").toString()); // 32
			vpd.put("var33", varOList.get(i).get("ticket_time").toString()); // 33
			vpd.put("var34", varOList.get(i).getString("forecast_money")); // 34
			vpd.put("var35", varOList.get(i).getString("issue")); // 35
			vpd.put("var36", varOList.get(i).get("is_delete").toString()); // 36
			vpd.put("var37", varOList.get(i).get("ticket_num").toString()); // 37
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

	/**
	 * 手动派奖(对接张子荣)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/manualAward")
	public ModelAndView manualAward() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String orderSn = pd.getString("orderSn");
		PageData orderPd = orderService.findByOrderSn(orderSn);
		if (null != orderPd) {
			double reward = Double.parseDouble(orderPd.getString("winning_money"));
			int userId = Integer.parseInt(orderPd.getString("user_id"));
			ReqOrdeEntity reqOrdeEntity = new ReqOrdeEntity();
			reqOrdeEntity.orderSn = orderSn;
			reqOrdeEntity.reward = reward;
			reqOrdeEntity.userId = userId;
			reqOrdeEntity.userMoney = 0;
			List<ReqOrdeEntity> userIdAndRewardList = new ArrayList<ReqOrdeEntity>();
			userIdAndRewardList.add(reqOrdeEntity);
			String reqStr = JSON.toJSONString(userIdAndRewardList);
			ManualAuditUtil.ManualAuditUtil(reqStr, TextConfig.URL_MANUAL_AUDIT_CODE, true);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
			return mv;
		}
		return null;
	}
}
