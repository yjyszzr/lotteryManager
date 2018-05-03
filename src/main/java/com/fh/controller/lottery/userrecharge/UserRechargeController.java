package com.fh.controller.lottery.userrecharge;

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
import com.fh.service.lottery.userrecharge.UserRechargeManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：用充值现模块 创建人：FH Q313596790 创建时间：2018-05-02
 */
@Controller
@RequestMapping(value = "/userrecharge")
public class UserRechargeController extends BaseController {

	String menuUrl = "userrecharge/list.do"; // 菜单地址(权限用)
	@Resource(name = "userrechargeService")
	private UserRechargeManager userrechargeService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增UserRecharge");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("_id", this.get32UUID()); //主键
		pd.put("id", "0"); // ID
		pd.put("recharge_sn", ""); // 充值编号
		pd.put("user_id", "0"); // 用户ID
		pd.put("amount", ""); // 金额
		pd.put("account_id", "0"); // 备注5
		pd.put("add_time", "0"); // 添加时间
		pd.put("status", ""); // 状态
		pd.put("process_type", "0"); // 类型
		pd.put("payment_code", ""); // 支付代码
		pd.put("payment_name", ""); // 支付方式名称
		pd.put("pay_time", "0"); // 支付时间
		pd.put("payment_id", ""); // 交易号
		pd.put("donation_id", ""); // 赠送Id
		userrechargeService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除UserRecharge");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userrechargeService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改UserRecharge");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		userrechargeService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表UserRecharge");
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

		double failAmount = 0;
		double successAmount = 0;
		double unfinished = 0;
		List<PageData> varList = userrechargeService.list(page); // 列出UserRecharge列表
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("pay_time")
					&& !"".equals(pageData.get("pay_time"))) {
				pageData.put("pay_time", DateUtilNew.getCurrentTimeString(
						Long.parseLong(pageData.get("pay_time").toString()),
						DateUtilNew.datetimeFormat));
			}
			if (null != pageData.get("status")
					&& !"".equals(pageData.get("status"))) {
				if ("1".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount")
							&& !"".equals(pageData.get("amount"))) {
						successAmount += Double.parseDouble(pageData.get(
								"amount").toString());
					}
				} else if ("2".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount")
							&& !"".equals(pageData.get("amount"))) {
						failAmount += Double.parseDouble(pageData.get("amount")
								.toString());
					}
				} else if ("0".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount")
							&& !"".equals(pageData.get("amount"))) {
						unfinished += Double.parseDouble(pageData.get("amount")
								.toString());
					}
				}
			}
		}
		mv.setViewName("lottery/userrecharge/userrecharge_list");
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
		mv.setViewName("lottery/userrecharge/userrecharge_edit");
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
		pd = userrechargeService.findById(pd); // 根据ID读取
		mv.setViewName("lottery/userrecharge/userrecharge_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除UserRecharge");
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
			userrechargeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出UserRecharge到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID"); // 1
		titles.add("充值编号"); // 2
		titles.add("用户ID"); // 3
		titles.add("金额"); // 4
		titles.add("备注5"); // 5
		titles.add("添加时间"); // 6
		titles.add("状态"); // 7
		titles.add("类型"); // 8
		titles.add("支付代码"); // 9
		titles.add("支付方式名称"); // 10
		titles.add("支付时间"); // 11
		titles.add("交易号"); // 12
		titles.add("赠送Id"); // 13
		dataMap.put("titles", titles);
		List<PageData> varOList = userrechargeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("id").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("recharge_sn")); // 2
			vpd.put("var3", varOList.get(i).get("user_id").toString()); // 3
			vpd.put("var4", varOList.get(i).getString("amount")); // 4
			vpd.put("var5", varOList.get(i).get("account_id").toString()); // 5
			vpd.put("var6", varOList.get(i).get("add_time").toString()); // 6
			vpd.put("var7", varOList.get(i).getString("status")); // 7
			vpd.put("var8", varOList.get(i).get("process_type").toString()); // 8
			vpd.put("var9", varOList.get(i).getString("payment_code")); // 9
			vpd.put("var10", varOList.get(i).getString("payment_name")); // 10
			vpd.put("var11", varOList.get(i).get("pay_time").toString()); // 11
			vpd.put("var12", varOList.get(i).getString("payment_id")); // 12
			vpd.put("var13", varOList.get(i).getString("donation_id")); // 13
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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}
}
