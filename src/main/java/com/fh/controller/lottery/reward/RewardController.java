package com.fh.controller.lottery.reward;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.lottery.useraccountmanager.UserAccountManagerManager;
import com.fh.util.DateUtilNew;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 中奖明细相关
 * 
 * @author Mr.Li
 *
 */
@Controller
@RequestMapping(value = "/reward")
public class RewardController extends BaseController {

	String menuUrl = "reward/list.do"; // 菜单地址(权限用)
	@Resource(name = "useraccountmanagerService")
	private UserAccountManagerManager useraccountmanagerService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表RewardController");
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
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart1", DateUtilNew.getMilliSecondsByStr(lastStart));
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd1", DateUtilNew.getMilliSecondsByStr(lastEnd));
		}

		pd.put("process_type", 1);
		page.setPd(pd);
		List<PageData> varList = useraccountmanagerService.listForReward(page); // 列出UserAccountManager列表
		double rewardAmount = 0;
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("amount") && !"".equals(pageData.get("amount"))) {
				rewardAmount += Double.parseDouble(pageData.get("amount").toString());
			}
		}
		mv.setViewName("lottery/reward/reward_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		BigDecimal bg = new BigDecimal(rewardAmount);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		mv.addObject("rewardAmount", f1);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
