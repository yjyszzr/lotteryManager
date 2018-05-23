package com.fh.controller.lottery.userwithdraw;

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
import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.userwithdraw.UserWithdrawManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ManualAuditUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：提现模块 创建人：FH Q313596790 创建时间：2018-05-02
 */
@Controller
@RequestMapping(value = "/userwithdraw")
public class UserWithdrawController extends BaseController {

	String menuUrl = "userwithdraw/list.do"; // 菜单地址(权限用)
	@Resource(name = "userwithdrawService")
	private UserWithdrawManager userwithdrawService;
	
	@Resource(name = "urlConfig")
	private URLConfig urlConfig;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增UserWithdraw");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // ID
		pd.put("user_id", "0"); // 用户ID
		pd.put("amount", ""); // 提现金额
		pd.put("account_id", "0"); // 账户ID
		pd.put("add_time", "0"); // 添加时间
		pd.put("status", ""); // 状态
		pd.put("real_name", ""); // 支付代码
		pd.put("card_no", ""); // 卡号
		pd.put("pay_time", "0"); // 提现时间
		pd.put("payment_id", ""); // 交易号
		pd.put("bank_name", ""); // 银行名称
		userwithdrawService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除UserWithdraw");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userwithdrawService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改UserWithdraw");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		userwithdrawService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表UserWithdraw");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String account_sn = pd.getString("account_sn");
		if (null != account_sn && !"".equals(account_sn)) {
			pd.put("account_sn", account_sn.trim());
		}
		String mobile = pd.getString("mobile");
		if (null != mobile && !"".equals(mobile)) {
			pd.put("mobile", mobile.trim());
		}
		String user_name = pd.getString("user_name");
		if (null != user_name && !"".equals(user_name)) {
			pd.put("user_name", user_name.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}
		page.setPd(pd);
		double failAmount = 0;
		double successAmount = 0;
		double unfinished = 0;
		List<PageData> varList = userwithdrawService.list(page); // 列出UserWithdraw列表
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("status") && !"".equals(pageData.get("status"))) {
				if ("1".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
						successAmount += Double.parseDouble(pageData.get("amount").toString());
					}
				} else if ("2".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
						failAmount += Double.parseDouble(pageData.get("amount").toString());
					}
				} else if ("0".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
						unfinished += Double.parseDouble(pageData.get("amount").toString());
					}
				}
			}
		}
		mv.setViewName("lottery/userwithdraw/userwithdraw_list");
		mv.addObject("varList", varList);
		mv.addObject("successAmount", successAmount);
		mv.addObject("failAmount", failAmount);
		mv.addObject("unfinished", unfinished);
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
		mv.setViewName("lottery/userwithdraw/userwithdraw_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 手动提现
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/manualAudit")
	public ModelAndView manualAudit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sendData = pd.getString("withdrawSn");
		String status = pd.getString("status");
		ReqCashEntity reqCashEntity = new ReqCashEntity();
		reqCashEntity.withdrawSn = sendData;
		if ("1".equals(status)) { // 状态为1的走审核通过接口
			reqCashEntity.isPass = true;
		} else if ("2".equals(status)) {// 状态为2的拒绝提现,
			reqCashEntity.isPass = false;
			// userwithdrawService.edit(pd);
		}
		String reqStr = JSON.toJSONString(reqCashEntity);
		ManualAuditUtil.ManualAuditUtil(reqStr, urlConfig.getManualAuditUrl(), true);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		pd = userwithdrawService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/userwithdraw/userwithdraw_edit");
		mv.addObject("msg", "manualAudit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除UserWithdraw");
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
			userwithdrawService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出UserWithdraw到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID"); // 1
		titles.add("提现编号"); // 2
		titles.add("用户ID"); // 3
		titles.add("提现金额"); // 4
		titles.add("账户ID"); // 5
		titles.add("添加时间"); // 6
		titles.add("状态"); // 7
		titles.add("支付代码"); // 8
		titles.add("卡号"); // 9
		titles.add("提现时间"); // 10
		titles.add("交易号"); // 11
		titles.add("银行名称"); // 12
		dataMap.put("titles", titles);
		List<PageData> varOList = userwithdrawService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("withdrawal_sn")); // 2
			vpd.put("var3", varOList.get(i).get("user_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("amount")); // 4
			vpd.put("var5", varOList.get(i).get("account_id").toString()); // 5
			vpd.put("var6", varOList.get(i).get("add_time").toString()); // 6
			vpd.put("var7", varOList.get(i).getString("status")); // 7
			vpd.put("var8", varOList.get(i).getString("real_name")); // 8
			vpd.put("var9", varOList.get(i).getString("card_no")); // 9
			vpd.put("var10", varOList.get(i).get("pay_time").toString()); // 10
			vpd.put("var11", varOList.get(i).getString("payment_id")); // 11
			vpd.put("var12", varOList.get(i).getString("bank_name")); // 12
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
