package com.fh.controller.lottery.reward;

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
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		pd.put("process_type", 1);
		page.setPd(pd);
		List<PageData> varList = useraccountmanagerService.listForReward(page); // 列出UserAccountManager列表
		double rewardAmount = 0;
		for (int i = 0; i < varList.size(); i++) {
			PageData pageData = new PageData();
			pageData = varList.get(i);
			if (null != pageData.get("pay_time")
					&& !"".equals(pageData.get("pay_time"))) {
				pageData.put("pay_time", DateUtilNew.getCurrentTimeString(
						Long.parseLong(pageData.get("pay_time").toString()),
						DateUtilNew.datetimeFormat));
			}
			if (null != pageData.get("amount")
					&& !"".equals(pageData.get("amount"))) {
				rewardAmount += Double.parseDouble(pageData.get("amount")
						.toString());
			}
		}
		mv.setViewName("lottery/reward/reward_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("rewardAmount", rewardAmount);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

}
