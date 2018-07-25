package com.fh.controller.lottery.userwithdraw;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axis.utils.SessionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.config.URLConfig;
import com.fh.controller.base.BaseController;
import com.fh.controller.lottery.actionlog.ActionLogController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.lottery.useractionlog.impl.UserActionLogService;
import com.fh.service.lottery.userwithdraw.UserWithdrawManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.JsonUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.ManualAuditUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.google.gson.JsonObject;

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
	@Resource(name = "userActionLogService")
	private UserActionLogService ACLOG;

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
	public ModelAndView list(Page page,ModelAndView mvv) throws Exception {
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

		double failAmount = 0;
		double successAmount = 0;
		double unfinished = 0;

		page.setPd(pd);
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
		String acText = "";
		String sendData = pd.getString("withdrawSn");
		String status = pd.getString("status");
		ReqCashEntity reqCashEntity = new ReqCashEntity();
		reqCashEntity.withdrawSn = sendData;
		if ("1".equals(status)) { // 状态为1的走审核通过接口
			reqCashEntity.isPass = true;
			acText = "通过";
		} else if ("2".equals(status)) {// 状态为2的拒绝提现,
			reqCashEntity.isPass = false;
			acText = "拒绝";
		}
		SessionUtils.generateSession();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USERROL);
		pd.put("remarks", pd.getString("remarks"));
		pd.put("audit_time", DateUtilNew.getCurrentTimeLong());
		pd.put("auditor", user.getUSERNAME());
		pd.put("auditor_id", user.getUSER_ID());
		userwithdrawService.updateRemarks(pd);
		String reqStr = JSON.toJSONString(reqCashEntity);
		String result = ManualAuditUtil.ManualAuditUtil(reqStr, urlConfig.getManualAuditUrl(), true);
		if(!pd.getString("remarks").equals("") && pd.get("remarks") != null) {
			acText = acText+"  备注："+pd.getString("remarks");
		}
		JsonObject json = JsonUtils.NewStringToJsonObject(result);
		if(json.get("code").getAsString().equals("0")) {
			ACLOG.save("1", "0", "提现明细：审核"+sendData, acText);
			mv.addObject("msg","success");
		}else {
			ACLOG.save("0", "0", "提现明细：审核"+sendData, acText);
			mv.addObject("msg",json.get("msg").getAsString());
		}
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
		List<PageData> pageDataList = new ArrayList<PageData>();
		BigDecimal awardAmount = new BigDecimal(BigInteger.ZERO);
		if (!StringUtil.isEmptyStr(pd.getString("user_id"))) {
			pageDataList = userwithdrawService.findByUserId(Integer.parseInt(pd.getString("user_id")));
			awardAmount = userwithdrawService.findTotalAwardById(Integer.parseInt(pd.getString("user_id")));
		}
		mv.addObject("msg", "manualAudit");
		mv.addObject("pd", pd);
		mv.addObject("pageDataList", pageDataList);
		mv.addObject("awardAmount", awardAmount);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
		titles.add("提现编号"); //
		titles.add("真实姓名"); //
		titles.add("电话"); //
		titles.add("提现金额"); //
		titles.add("银行名称"); //
		titles.add("卡号"); //
		titles.add("申请提现时间"); //
		titles.add("状态"); //
		titles.add("备注"); //
		dataMap.put("titles", titles);
		List<PageData> varOList = userwithdrawService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("withdrawal_sn")); // 1
			vpd.put("var2", varOList.get(i).getString("real_name")); // 2
			vpd.put("var3", varOList.get(i).getString("mobile")); // 3
			vpd.put("var4", varOList.get(i).getString("amount")); // 4
			vpd.put("var5", varOList.get(i).getString("bank_name")); // 5
			vpd.put("var6", varOList.get(i).getString("card_no")); // 6
			BigDecimal big = new BigDecimal(StringUtil.isEmptyStr(varOList.get(i).getString("add_time")) ? "0" : varOList.get(i).getString("add_time"));
			BigDecimal big1000 = new BigDecimal(1000);
			vpd.put("var7", DateUtil.toSDFTime(Long.parseLong(big.multiply(big1000).toString())));
			String status = varOList.get(i).getString("status");
			if (status.equals("0")) {
				vpd.put("var8", "待审核"); // 8
			} else if (status.equals("1")) {
				vpd.put("var8", "通过"); // 8
			} else if (status.equals("2")) {
				vpd.put("var8", "拒绝"); // 8
			} else {
				vpd.put("var8", "正在审批"); // 8
			}
			vpd.put("var9", varOList.get(i).getString("remarks")); // 9
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
