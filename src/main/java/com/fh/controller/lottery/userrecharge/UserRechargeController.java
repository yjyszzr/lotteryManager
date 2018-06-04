package com.fh.controller.lottery.userrecharge;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.userrecharge.UserRechargeManager;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
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
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表UserRecharge");
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
		BigDecimal failAmount = new BigDecimal(0);
		BigDecimal successAmount = new BigDecimal(0);
		BigDecimal unfinished = new BigDecimal(0);
		List<PageData> varList = userrechargeService.list(page); // 列出UserRecharge列表
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("status") && !"".equals(pageData.get("status"))) {
				if ("1".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
						BigDecimal Big1 = new BigDecimal(pageData.getString("amount"));
						successAmount = successAmount.add(Big1);
					}
				} else if ("2".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
						BigDecimal Big2 = new BigDecimal(pageData.getString("amount"));
						failAmount = failAmount.add(Big2);
					}
				} else if ("0".equals(pageData.get("status").toString())) {
					if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
						BigDecimal Big0 = new BigDecimal(pageData.getString("amount"));
						unfinished = unfinished.add(Big0);
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

}
